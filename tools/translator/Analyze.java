import com.github.javaparser.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.expr.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

/** Counts scoped method calls, field accesses and constructor calls in the decompiled sources. */
public class Analyze {
    public static void main(String[] a) throws Exception {
        ParserConfiguration cfg = new ParserConfiguration().setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_17);
        JavaParser p = new JavaParser(cfg);
        Map<String, Integer> calls = new TreeMap<>(), fields = new TreeMap<>(), news = new TreeMap<>(), statics = new TreeMap<>();
        List<Path> files;
        try (Stream<Path> s = Files.walk(Paths.get(a[0]))) { files = s.filter(f -> f.toString().endsWith(".java")).collect(Collectors.toList()); }
        int fail = 0;
        for (Path f : files) {
            ParseResult<CompilationUnit> r = p.parse(f);
            if (!r.isSuccessful()) { fail++; System.err.println("PARSE FAIL " + f + " " + r.getProblems().get(0)); continue; }
            CompilationUnit cu = r.getResult().get();
            Set<String> types = new HashSet<>();
            cu.getImports().forEach(i -> types.add(i.getName().getIdentifier()));
            cu.findAll(com.github.javaparser.ast.body.TypeDeclaration.class).forEach(t -> types.add(t.getNameAsString()));
            for (MethodCallExpr m : cu.findAll(MethodCallExpr.class)) {
                if (!m.getScope().isPresent()) continue;
                Expression sc = m.getScope().get();
                if (sc.isSuperExpr()) continue;
                boolean isType = sc.isNameExpr() && Character.isUpperCase(sc.asNameExpr().getNameAsString().charAt(0));
                if (sc.isFieldAccessExpr() && Character.isUpperCase(sc.asFieldAccessExpr().getNameAsString().charAt(0)) && sc.toString().matches("[A-Z][A-Za-z]*[.][A-Z][A-Za-z]*")) isType = true;
                (isType ? statics : calls).merge(isType ? sc + "." + m.getNameAsString() : m.getNameAsString(), 1, Integer::sum);
            }
            for (FieldAccessExpr fa : cu.findAll(FieldAccessExpr.class)) {
                if (fa.getParentNode().get() instanceof MethodCallExpr && ((MethodCallExpr) fa.getParentNode().get()).getScope().map(x -> x == fa).orElse(false) && false) continue;
                Expression sc = fa.getScope();
                if (sc.isNameExpr() && Character.isUpperCase(sc.asNameExpr().getNameAsString().charAt(0))) continue;
                if (Character.isUpperCase(fa.getNameAsString().charAt(0))) continue;
                if (sc.toString().contains(".") && Character.isUpperCase(sc.toString().charAt(0))) continue;
                fields.merge(fa.getNameAsString(), 1, Integer::sum);
            }
            for (ObjectCreationExpr o : cu.findAll(ObjectCreationExpr.class)) news.merge(o.getType().getNameAsString(), 1, Integer::sum);
        }
        dump(a[1] + "/calls.txt", calls); dump(a[1] + "/fields.txt", fields); dump(a[1] + "/news.txt", news); dump(a[1] + "/statics.txt", statics);
        System.out.println("files " + files.size() + " parse failures " + fail + " calls " + calls.size() + " fields " + fields.size() + " news " + news.size() + " statics " + statics.size());
    }
    static void dump(String file, Map<String, Integer> m) throws Exception {
        List<String> lines = m.entrySet().stream().sorted((x, y) -> y.getValue() - x.getValue()).map(e -> e.getValue() + " " + e.getKey()).collect(Collectors.toList());
        Files.write(Paths.get(file), lines);
    }
}
