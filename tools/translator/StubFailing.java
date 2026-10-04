import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.*;

/**
 * Temporary safety net: for every compile error in a stage file, replaces the body of the enclosing method
 * (or field initializer / initializer block) with a default, and drops broken imports. Records what was stubbed.
 * args: errorsFile stageRoot stubLog
 */
public class StubFailing {

    public static void main(String[] a) throws Exception {
        StaticJavaParser.getParserConfiguration().setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_17);
        Pattern p = Pattern.compile("^(stage[\\\\/][^:]+\\.java):(\\d+): error:");
        Map<String, TreeSet<Integer>> errs = new LinkedHashMap<>();
        for (String l : Files.readAllLines(Paths.get(a[0]))) {
            Matcher m = p.matcher(l);
            if (m.find()) errs.computeIfAbsent(m.group(1).replace('\\', '/'), k -> new TreeSet<>()).add(Integer.parseInt(m.group(2)));
        }
        List<String> log = new ArrayList<>();
        Path logFile = Paths.get(a[2]);
        if (Files.exists(logFile)) log.addAll(Files.readAllLines(logFile));
        int stubbed = 0, unfixable = 0;
        for (Map.Entry<String, TreeSet<Integer>> e : errs.entrySet()) {
            Path f = Paths.get(e.getKey());
            CompilationUnit cu;
            try {
                cu = StaticJavaParser.parse(f);
            } catch (Exception ex) {
                System.out.println("parse failed: " + f);
                continue;
            }
            boolean changed = false;
            for (int line : e.getValue()) {
                String what = stub(cu, line);
                if (what == null) {
                    unfixable++;
                    System.out.println("unfixable: " + f + ":" + line);
                } else if (!what.isEmpty()) {
                    changed = true;
                    stubbed++;
                    String entry = f.toString().replace('\\', '/') + " :: " + what;
                    if (!log.contains(entry)) log.add(entry);
                }
            }
            if (changed) Files.write(f, cu.toString().getBytes("UTF-8"));
        }
        Files.write(logFile, log);
        System.out.println("stubbed " + stubbed + ", unfixable " + unfixable);
    }

    static boolean covers(Node n, int line) {
        return n.getRange().isPresent() && n.getRange().get().begin.line <= line && n.getRange().get().end.line >= line;
    }

    /** returns a description, "" if already handled, or null if nothing could be stubbed */
    static String stub(CompilationUnit cu, int line) {
        for (ImportDeclaration id : new ArrayList<>(cu.getImports())) {
            if (covers(id, line)) {
                id.remove();
                return "import " + id.getNameAsString();
            }
        }
        // innermost callable or initializer containing the line
        Node best = null;
        for (Node n : cu.findAll(Node.class)) {
            if (!(n instanceof MethodDeclaration || n instanceof ConstructorDeclaration || n instanceof InitializerDeclaration
                || n instanceof FieldDeclaration || n instanceof LambdaExpr)) continue;
            if (!covers(n, line)) continue;
            if (best == null || isInside(n, best)) best = n;
        }
        if (best instanceof LambdaExpr) {
            // stub the method/field that holds the lambda instead (lambdas cannot be typed reliably here)
            Node up = best.getParentNode().orElse(null);
            while (up != null && !(up instanceof MethodDeclaration || up instanceof ConstructorDeclaration || up instanceof FieldDeclaration
                || up instanceof InitializerDeclaration)) up = up.getParentNode().orElse(null);
            best = up;
        }
        if (best == null) return null;
        if (best instanceof MethodDeclaration) {
            MethodDeclaration md = (MethodDeclaration) best;
            if (!md.getBody().isPresent()) return null;
            if (md.getType().getRange().isPresent() && md.getType().getRange().get().begin.line == line
                && md.getBody().get().getRange().get().begin.line != line) return null;
            if (isStub(md.getBody().get())) return "";
            md.setBody(defaultBody(md.getType()));
            return "method " + owner(md) + "." + md.getNameAsString();
        }
        if (best instanceof ConstructorDeclaration) {
            ConstructorDeclaration cd = (ConstructorDeclaration) best;
            BlockStmt b = cd.getBody();
            Statement first = b.getStatements().isEmpty() ? null : b.getStatement(0);
            if (first instanceof ExplicitConstructorInvocationStmt && covers(first, line)) return null;
            BlockStmt nb = new BlockStmt();
            if (first instanceof ExplicitConstructorInvocationStmt) nb.addStatement(first.clone());
            // keep assignments to final fields so the class still compiles
            for (Statement s : b.getStatements()) {
                if (s == first || covers(s, line)) continue;
                if (s.isExpressionStmt() && s.asExpressionStmt().getExpression().isAssignExpr()) nb.addStatement(s.clone());
            }
            if (nb.toString().equals(b.toString())) return "";
            cd.setBody(nb);
            return "constructor " + owner(cd);
        }
        if (best instanceof InitializerDeclaration) {
            ((InitializerDeclaration) best).setBody(new BlockStmt());
            return "initializer " + owner(best);
        }
        if (best instanceof FieldDeclaration) {
            FieldDeclaration fd = (FieldDeclaration) best;
            boolean any = false;
            for (VariableDeclarator v : fd.getVariables()) {
                if (v.getInitializer().isPresent() && covers(v.getInitializer().get(), line)) {
                    v.setInitializer(defaultValue(v.getType()));
                    any = true;
                }
            }
            return any ? "field " + owner(fd) + "." + fd.getVariable(0).getNameAsString() : null;
        }
        return null;
    }

    static boolean isInside(Node inner, Node outer) {
        for (Node n = inner.getParentNode().orElse(null); n != null; n = n.getParentNode().orElse(null)) if (n == outer) return true;
        return false;
    }

    static boolean isStub(BlockStmt b) {
        return b.getStatements().size() <= 1 && b.getAllContainedComments().stream().anyMatch(c -> c.getContent().trim().equals("stub"));
    }

    static String owner(Node n) {
        StringBuilder s = new StringBuilder();
        for (Node p = n.getParentNode().orElse(null); p != null; p = p.getParentNode().orElse(null))
            if (p instanceof TypeDeclaration) s.insert(0, ((TypeDeclaration<?>) p).getNameAsString() + (s.length() > 0 ? "." : ""));
        return s.toString();
    }

    static BlockStmt defaultBody(Type t) {
        String ret = t.isVoidType() ? "" : "return " + defaultValue(t) + ";";
        return StaticJavaParser.parseBlock("{ /* stub */ " + ret + " }");
    }

    static Expression defaultValue(Type t) {
        if (t.isPrimitiveType()) {
            switch (t.asPrimitiveType().getType()) {
                case BOOLEAN:
                    return new BooleanLiteralExpr(false);
                case CHAR:
                    return new CharLiteralExpr("\\0");
                case LONG:
                    return new LongLiteralExpr("0L");
                case FLOAT:
                    return new DoubleLiteralExpr("0.0F");
                case DOUBLE:
                    return new DoubleLiteralExpr("0.0");
                default:
                    return new CastExpr(t.clone(), new IntegerLiteralExpr("0"));
            }
        }
        return new NullLiteralExpr();
    }
}
