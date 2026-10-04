import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;

import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import java.util.Optional;

/**
 * Source-to-source translator from the decompiled 1.20.1 MCreator code to the 1.7.10 compat layer.
 * Usage: Translate <decompRoot> <outRoot> <configDir>
 */
public class Translate {

    static final String M = "net.mcreator.boh.compat.M";

    static Map<String, String> typeMap = new HashMap<>();
    static Map<String, String> extendsMap = new HashMap<>();
    static Set<String> mojangMethods = new HashSet<>();
    static Set<String> mojangNested = new HashSet<>();
    static Set<String> keep = new HashSet<>();
    static Set<String> force = new HashSet<>();
    static Set<String> ownDeclared = new HashSet<>();
    /** mod class simple name -> methods it declares, and its superclass simple name */
    static Map<String, Set<String>> classMethods = new HashMap<>();
    static Map<String, String> classSuper = new HashMap<>();
    static Map<String, String> fields = new HashMap<>();
    static Set<String> ctors = new HashSet<>();
    static Map<String, String> statics = new HashMap<>();
    static Map<String, String> renames = new HashMap<>();
    static List<String> subscribers = new ArrayList<>();

    static Map<String, String> fqn;

    /**
     * Fully qualifies simple names listed in fqn.txt (shim types that 1.7.10 superclasses shadow with inherited
     * member types, e.g. Block.SoundType) wherever the file imports the shim.
     */
    static String qualify(String src, Path cfg) throws Exception {
        if (fqn == null) {
            fqn = new LinkedHashMap<>();
            Path f = cfg.resolve("fqn.txt");
            if (Files.exists(f)) for (String l : lines(f)) fqn.put(l.substring(l.lastIndexOf('.') + 1), l.trim());
        }
        Path rf = cfg.resolve("replace.txt");
        if (Files.exists(rf)) for (String l : lines(rf)) {
            if (l.contains(" =~> ")) {
                String[] rx = l.split(" =~> ", 2);
                src = src.replaceAll(rx[0], rx[1]);
                continue;
            }
            String[] kv = l.split(" => ", 2);
            if (kv.length == 2) src = src.replace(kv[0], kv[1]);
        }
        StringBuilder out = new StringBuilder();
        for (String line : src.split("\n", -1)) {
            if (!line.startsWith("import ") && !line.startsWith("package ")) {
                for (Map.Entry<String, String> e : fqn.entrySet()) {
                    if (!line.contains(e.getKey()) || !src.contains("import " + e.getValue() + ";")) continue;
                    line = line.replaceAll("(?<![\\w.])" + e.getKey() + "(?=[\\s.<>\\[\\],)(])", java.util.regex.Matcher.quoteReplacement(e.getValue()));
                }
            }
            out.append(line).append('\n');
        }
        return out.substring(0, out.length() - 1);
    }

    /** Drops single-type imports whose simple name no longer occurs in the body. */
    static void pruneImports(CompilationUnit cu) {
        String body = cu.getTypes().toString();
        for (ImportDeclaration id : new ArrayList<>(cu.getImports())) {
            if (id.isAsterisk() || id.isStatic()) continue;
            String n = id.getName().getIdentifier();
            if (!java.util.regex.Pattern.compile("\\b" + n + "\\b").matcher(body).find()) id.remove();
        }
    }

    public static void main(String[] a) throws Exception {
        Path in = Paths.get(a[0]), out = Paths.get(a[1]), cfg = Paths.get(a[2]);
        for (String l : lines(cfg.resolve("types.map"))) {
            String[] p = l.split("\\s*->\\s*");
            typeMap.put(p[0].trim(), p[1].trim());
        }
        for (String l : lines(cfg.resolve("extends.map"))) {
            String[] p = l.split("\\s*->\\s*");
            extendsMap.put(p[0].trim(), p[1].trim());
        }
        mojangMethods.addAll(lines(cfg.resolve("mojang_methods.txt")));
        for (String n : lines(cfg.resolve("mojang_nested.txt"))) mojangNested.add(n.replace('$', '.'));
        keep.addAll(lines(cfg.resolve("keep.txt")));
        force.addAll(lines(cfg.resolve("force.txt")));
        for (String l : lines(cfg.resolve("fields.map"))) {
            String[] p = l.trim().split("\\s+");
            fields.put(p[0], p.length > 1 ? p[1] : "always");
        }
        ctors.addAll(lines(cfg.resolve("ctors.txt")));
        for (String l : lines(cfg.resolve("statics.map"))) {
            String[] p = l.split("\s*->\s*");
            statics.put(p[0].trim(), p[1].trim());
        }
        for (String l : lines(cfg.resolve("renames.map"))) {
            String[] p = l.split("\s*->\s*");
            renames.put(p[0].trim(), p[1].trim());
        }

        ParserConfiguration pc = new ParserConfiguration().setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_17);
        JavaParser parser = new JavaParser(pc);
        List<Path> files;
        try (Stream<Path> s = Files.walk(in)) {
            files = s.filter(f -> f.toString().endsWith(".java")).sorted().collect(Collectors.toList());
        }
        List<CompilationUnit> units = new ArrayList<>();
        for (Path f : files) units.add(parser.parse(f).getResult().get());
        // methods the mod declares itself that do not exist in the vanilla API keep their call form
        for (CompilationUnit cu : units)
            for (MethodDeclaration md : cu.findAll(MethodDeclaration.class))
                if (!mojangMethods.contains(md.getNameAsString())) ownDeclared.add(md.getNameAsString());
        ownDeclared.removeAll(force);
        for (CompilationUnit cu : units)
            for (ClassOrInterfaceDeclaration cd : cu.findAll(ClassOrInterfaceDeclaration.class)) {
                Set<String> ms = classMethods.computeIfAbsent(cd.getNameAsString(), k -> new HashSet<>());
                cd.getMethods().forEach(m -> ms.add(m.getNameAsString()));
                if (!cd.getExtendedTypes().isEmpty()) classSuper.put(cd.getNameAsString(), cd.getExtendedTypes().get(0).getNameAsString());
            }

        int n = 0;
        for (CompilationUnit cu : units) {
            translate(cu);
            String pkg = cu.getPackageDeclaration().map(p -> p.getNameAsString()).orElse("");
            String name = cu.getPrimaryTypeName().orElse("Unknown");
            Path dest = out.resolve(pkg.replace('.', '/')).resolve(name + ".java");
            Files.createDirectories(dest.getParent());
            pruneImports(cu);
            Files.write(dest, qualify(cu.toString(), cfg).getBytes("UTF-8"));
            n++;
        }
        Files.write(cfg.resolve("subscribers.txt"), subscribers);
        System.out.println("translated " + n + " files, " + subscribers.size() + " event subscriber classes");
    }

    static List<String> lines(Path p) throws Exception {
        if (!Files.exists(p)) return new ArrayList<>();
        return Files.readAllLines(p).stream().map(String::trim).filter(l -> !l.isEmpty() && !l.startsWith("#"))
            .collect(Collectors.toList());
    }

    /** Where an external modern type lands in the port. */
    static String mapType(String fqn) {
        if (typeMap.containsKey(fqn)) return typeMap.get(fqn);
        String[] prefixes = { "net.minecraft.", "net.minecraftforge.", "com.mojang." };
        String[] targets = { "net.mcreator.boh.compat.mc.", "net.mcreator.boh.compat.forge.", "net.mcreator.boh.compat.mojang." };
        for (int i = 0; i < prefixes.length; i++) {
            if (!fqn.startsWith(prefixes[i])) continue;
            String rest = fqn.substring(prefixes[i].length());
            // nested type (Outer.Inner) becomes a top-level type in the outer's package
            String[] parts = rest.split("\\.");
            int firstUpper = -1;
            for (int j = 0; j < parts.length; j++) if (Character.isUpperCase(parts[j].charAt(0))) { firstUpper = j; break; }
            if (firstUpper >= 0 && firstUpper < parts.length - 1) {
                String pkg = String.join(".", Arrays.copyOfRange(parts, 0, firstUpper));
                return targets[i] + pkg + "." + parts[parts.length - 1];
            }
            return targets[i] + rest;
        }
        return null;
    }

    static String simple(String fqn) {
        return fqn.substring(fqn.lastIndexOf('.') + 1);
    }

    /**
     * The decompiler flattens MCreator's "{ final Vec3 _center = ...; ... }" blocks, leaving one variable that is
     * reassigned and captured by lambdas. Each reassignment of such a temp becomes a fresh declaration.
     */
    static void splitReassignedTemps(CompilationUnit cu) {
        for (Node callable : cu.findAll(Node.class, n -> n instanceof MethodDeclaration || n instanceof ConstructorDeclaration
            || n instanceof com.github.javaparser.ast.body.InitializerDeclaration)) {
            for (com.github.javaparser.ast.stmt.BlockStmt b : callable.findAll(com.github.javaparser.ast.stmt.BlockStmt.class)) {
                if (outerBlock(b) == null && isAncestor(callable, b)) {
                    // names captured by lambdas were effectively final in the original, so reassignments are merged scopes
                    Set<String> captured = new HashSet<>();
                    for (LambdaExpr l : callable.findAll(LambdaExpr.class)) {
                        Set<String> own = new HashSet<>();
                        for (Parameter p : l.getParameters()) own.add(p.getNameAsString());
                        for (com.github.javaparser.ast.body.VariableDeclarator v : l.findAll(com.github.javaparser.ast.body.VariableDeclarator.class)) own.add(v.getNameAsString());
                        for (NameExpr ne : l.findAll(NameExpr.class)) if (!own.contains(ne.getNameAsString())) captured.add(ne.getNameAsString());
                    }
                    splitBlock(b, new HashMap<>(), new HashMap<>(), captured);
                }
            }
        }
    }

    /** nearest enclosing BlockStmt of a node (excluding itself) */
    static com.github.javaparser.ast.stmt.BlockStmt outerBlock(Node n) {
        for (Node p = n.getParentNode().orElse(null); p != null; p = p.getParentNode().orElse(null)) {
            if (p instanceof com.github.javaparser.ast.stmt.BlockStmt) return (com.github.javaparser.ast.stmt.BlockStmt) p;
            if (p instanceof MethodDeclaration || p instanceof ConstructorDeclaration || p instanceof com.github.javaparser.ast.body.TypeDeclaration) return null;
        }
        return null;
    }

    /** crossable: inherited names whose block lies outside an enclosing lambda (they cannot be assigned from here). */
    static void splitBlock(com.github.javaparser.ast.stmt.BlockStmt block, Map<String, Type> declaredIn, Map<String, String> currentIn,
        Set<String> crossable) {
        Map<String, Type> declared = new HashMap<>(declaredIn);
        Set<String> local = new HashSet<>();
        Map<String, String> current = new HashMap<>(currentIn);
        NodeList<com.github.javaparser.ast.stmt.Statement> st = block.getStatements();
        for (int i = 0; i < st.size(); i++) {
            com.github.javaparser.ast.stmt.Statement s = st.get(i);
            if (s.isExpressionStmt() && s.asExpressionStmt().getExpression().isAssignExpr()) {
                AssignExpr ae = s.asExpressionStmt().getExpression().asAssignExpr();
                if (ae.getOperator() == AssignExpr.Operator.ASSIGN && ae.getTarget().isNameExpr()) {
                    String n = ae.getTarget().asNameExpr().getNameAsString();
                    String cur = current.getOrDefault(n, n);
                    Type t = declared.get(n);
                    if (t != null && n.startsWith("_") && (local.contains(n) || crossable.contains(n))) {
                        String fresh = n.replaceAll("_r[0-9]+$", "") + "_r" + (++tempCounter);
                        Expression value = ae.getValue().clone();
                        renameIn(value, current);
                        VariableDeclarationExpr vd = new VariableDeclarationExpr(t.clone(), fresh);
                        vd.getVariable(0).setInitializer(value);
                        st.set(i, new ExpressionStmt(vd));
                        declared.put(fresh, t.clone());
                        local.add(fresh);
                        current.put(n, fresh);
                        continue;
                    }
                }
            }
            if (s.isExpressionStmt() && s.asExpressionStmt().getExpression().isVariableDeclarationExpr()) {
                for (com.github.javaparser.ast.body.VariableDeclarator v : s.asExpressionStmt().getExpression().asVariableDeclarationExpr().getVariables()) {
                    if (!v.getType().isVarType()) declared.put(v.getNameAsString(), v.getType());
                    local.add(v.getNameAsString());
                    current.remove(v.getNameAsString());
                }
            }
            // rename uses outside nested blocks, then recurse into the nested blocks with the current mapping
            List<com.github.javaparser.ast.stmt.BlockStmt> nested = new ArrayList<>();
            for (com.github.javaparser.ast.stmt.BlockStmt nb : s.findAll(com.github.javaparser.ast.stmt.BlockStmt.class))
                if (nb != s && outerBlock(nb) == block) nested.add(nb);
            for (NameExpr ne : s.findAll(NameExpr.class)) {
                String r = current.get(ne.getNameAsString());
                if (r == null) continue;
                boolean inNested = false;
                for (com.github.javaparser.ast.stmt.BlockStmt nb : nested) if (isAncestor(nb, ne)) inNested = true;
                if (!inNested && !shadowed(ne, s)) ne.setName(r);
            }
            for (com.github.javaparser.ast.stmt.BlockStmt nb : nested) {
                Map<String, Type> d = new HashMap<>(declared);
                LambdaExpr lam = nb.getParentNode().orElse(null) instanceof LambdaExpr ? (LambdaExpr) nb.getParentNode().get() : null;
                Map<String, String> c = new HashMap<>(current);
                if (lam != null) for (Parameter p : lam.getParameters()) {
                    d.remove(p.getNameAsString());
                    c.remove(p.getNameAsString());
                }
                Set<String> cross = new HashSet<>(crossable);
                if (lam != null) cross.addAll(d.keySet());
                splitBlock(nb, d, c, cross);
            }
        }
    }


    static int tempCounter;

    static void renameIn(Node n, Map<String, String> current) {
        if (current.isEmpty()) return;
        for (NameExpr ne : n.findAll(NameExpr.class)) {
            String r = current.get(ne.getNameAsString());
            if (r != null && !shadowed(ne, n)) ne.setName(r);
        }
    }

    /** true if the name is re-declared in a scope between the use and the statement being renamed. */
    static boolean shadowed(NameExpr ne, Node top) {
        String name = ne.getNameAsString();
        int line = ne.getBegin().map(p -> p.line).orElse(0), col = ne.getBegin().map(p -> p.column).orElse(0);
        for (Node p = ne.getParentNode().orElse(null); p != null && p != top; p = p.getParentNode().orElse(null)) {
            if (p instanceof LambdaExpr) {
                for (Parameter prm : ((LambdaExpr) p).getParameters()) if (prm.getNameAsString().equals(name)) return true;
            }
            if (p instanceof com.github.javaparser.ast.stmt.BlockStmt) {
                for (com.github.javaparser.ast.body.VariableDeclarator v : p.findAll(com.github.javaparser.ast.body.VariableDeclarator.class)) {
                    if (!v.getNameAsString().equals(name) || !v.getBegin().isPresent()) continue;
                    if (v.getParentNode().flatMap(Node::getParentNode).flatMap(Node::getParentNode).orElse(null) != p) continue;
                    int vl = v.getBegin().get().line, vc = v.getBegin().get().column;
                    if (vl < line || vl == line && vc < col) return true;
                }
            }
        }
        return false;
    }

    /**
     * A local declared inside a lambda may not reuse the name of a local of the enclosing method; the decompiler
     * produces that when it flattens MCreator's scoped blocks. Such lambda locals are renamed.
     */
    static void renameLambdaShadows(CompilationUnit cu) {
        for (com.github.javaparser.ast.body.VariableDeclarator v : cu.findAll(com.github.javaparser.ast.body.VariableDeclarator.class)) {
            if (!(v.getParentNode().orElse(null) instanceof VariableDeclarationExpr)) continue;
            LambdaExpr lam = v.findAncestor(LambdaExpr.class).orElse(null);
            if (lam == null) continue;
            Node callable = lam;
            while (callable != null && !(callable instanceof MethodDeclaration || callable instanceof ConstructorDeclaration
                || callable instanceof com.github.javaparser.ast.body.InitializerDeclaration || callable instanceof com.github.javaparser.ast.body.FieldDeclaration))
                callable = callable.getParentNode().orElse(null);
            if (callable == null) continue;
            String name = v.getNameAsString();
            boolean clash = false;
            for (com.github.javaparser.ast.body.VariableDeclarator o : callable.findAll(com.github.javaparser.ast.body.VariableDeclarator.class)) {
                if (o == v || !o.getNameAsString().equals(name)) continue;
                if (isAncestor(lam, o)) continue; // inside the same lambda: normal scoping applies
                if (isAncestorOfLambdaChain(o, lam)) {
                    clash = true;
                    break;
                }
            }
            if (!clash) continue;
            String fresh = name + "_l" + (++tempCounter);
            Node scope = v.findAncestor(com.github.javaparser.ast.stmt.BlockStmt.class).orElse(null);
            v.setName(fresh);
            if (scope == null) continue;
            for (NameExpr ne : scope.findAll(NameExpr.class)) if (ne.getNameAsString().equals(name)) ne.setName(fresh);
        }
    }

    static boolean isAncestor(Node anc, Node n) {
        for (Node p = n.getParentNode().orElse(null); p != null; p = p.getParentNode().orElse(null)) if (p == anc) return true;
        return false;
    }

    /** true if the declarator o is in a block that encloses the lambda (i.e. o is visible inside lam). */
    static boolean isAncestorOfLambdaChain(Node o, LambdaExpr lam) {
        Node block = o.getParentNode().flatMap(Node::getParentNode).flatMap(Node::getParentNode).orElse(null);
        if (block == null) return false;
        if (o.getParentNode().flatMap(Node::getParentNode).orElse(null) instanceof com.github.javaparser.ast.stmt.ForEachStmt) return false;
        return isAncestor(block, lam);
    }

    static void translate(CompilationUnit cu) {
        renameLambdaShadows(cu);
        splitReassignedTemps(cu);
        // ---- imports
        Map<String, String> rename = new HashMap<>(); // old simple -> new simple
        Map<String, String> importedModern = new HashMap<>(); // simple -> modern fqn
        Set<String> newImports = new LinkedHashSet<>();
        Set<String> types = new HashSet<>();
        for (ImportDeclaration id : new ArrayList<>(cu.getImports())) {
            String fqn = id.getNameAsString();
            if (id.isStatic() || id.isAsterisk()) continue;
            String mapped = mapType(fqn);
            if (mapped == null) { types.add(simple(fqn)); continue; }
            importedModern.put(simple(fqn), fqn);
            id.remove();
            if (mapped.equals("DROP")) { rename.put(simple(fqn), null); continue; }
            if (!mapped.equals("java.lang.Object")) newImports.add(mapped);
            if (!simple(mapped).equals(simple(fqn))) rename.put(simple(fqn), simple(mapped));
            types.add(simple(mapped));
        }
        for (String imp : newImports) cu.addImport(imp);
        cu.findAll(TypeDeclaration.class).forEach(t -> types.add(t.getNameAsString()));

        // ---- scoped nested type references (Entity.RemovalReason, AnimationController.State...)
        for (ClassOrInterfaceType t : cu.findAll(ClassOrInterfaceType.class)) {
            if (!t.getScope().isPresent()) continue;
            String outer = t.getScope().get().getNameAsString();
            String modern = importedModern.get(outer);
            if (modern == null) continue;
            String nested = modern + "." + t.getNameAsString();
            String mapped = mapType(nested);
            if (mapped != null && !mapped.equals("DROP")) {
                t.removeScope();
                t.setName(simple(mapped));
                cu.addImport(mapped);
            }
        }
        for (FieldAccessExpr fa : cu.findAll(FieldAccessExpr.class)) {
            if (!fa.getScope().isNameExpr()) continue;
            String outer = fa.getScope().asNameExpr().getNameAsString();
            String modern = importedModern.get(outer);
            if (modern == null || !Character.isUpperCase(fa.getNameAsString().charAt(0))) continue;
            String nested = modern + "." + fa.getNameAsString();
            if (!mojangNested.contains(nested) && !typeMap.containsKey(nested)) continue;
            String mapped = mapType(nested);
            if (mapped == null || mapped.equals("DROP")) continue;
            cu.addImport(mapped);
            fa.replace(new NameExpr(simple(mapped)));
        }

        // ---- renames of simple type names
        for (ClassOrInterfaceType t : cu.findAll(ClassOrInterfaceType.class)) {
            if (t.getScope().isPresent()) continue;
            String r = rename.get(t.getNameAsString());
            if (r != null) t.setName(r);
        }
        for (NameExpr ne : cu.findAll(NameExpr.class)) {
            String r = rename.get(ne.getNameAsString());
            if (r != null) ne.setName(r);
        }
        for (MarkerAnnotationExpr an : cu.findAll(MarkerAnnotationExpr.class)) {
            if (rename.containsKey(an.getNameAsString()) && rename.get(an.getNameAsString()) == null) an.remove();
        }
        for (NormalAnnotationExpr an : cu.findAll(NormalAnnotationExpr.class)) {
            if (rename.containsKey(an.getNameAsString()) && rename.get(an.getNameAsString()) == null) an.remove();
        }
        for (SingleMemberAnnotationExpr an : cu.findAll(SingleMemberAnnotationExpr.class)) {
            if (an.getNameAsString().equals("OnlyIn") || an.getNameAsString().equals("SideOnly")) an.setName("SideOnly");
            if (rename.containsKey(an.getNameAsString()) && rename.get(an.getNameAsString()) == null) an.remove();
        }

        // ---- casts to ItemLike (mapped to Object) are dropped
        for (CastExpr c : cu.findAll(CastExpr.class)) {
            if (c.getType().asString().equals("ItemLike") || c.getType().asString().equals("Object")) {
                if (c.getParentNode().isPresent()) c.replace(c.getExpression().clone());
            }
        }

        // ---- superclasses
        boolean isSubscriber = false;
        for (ClassOrInterfaceDeclaration cd : cu.findAll(ClassOrInterfaceDeclaration.class)) {
            for (ClassOrInterfaceType ext : cd.getExtendedTypes()) {
                String repl = extendsMap.get(ext.getNameAsString());
                if (repl != null && !cd.isInterface()) {
                    ext.setName(simple(repl));
                    cu.addImport(repl);
                }
            }
        }

        // ---- event subscribers: 1.7.10 buses need instance methods
        for (MethodDeclaration md : cu.findAll(MethodDeclaration.class)) {
            if (md.getAnnotationByName("SubscribeEvent").isPresent()) {
                md.removeModifier(Modifier.Keyword.STATIC);
                md.removeModifier(Modifier.Keyword.PRIVATE);
                md.removeModifier(Modifier.Keyword.PROTECTED);
                md.addModifier(Modifier.Keyword.PUBLIC);
                // FML's generated ASM handlers need public enclosing classes
                for (Node p = md.getParentNode().orElse(null); p != null; p = p.getParentNode().orElse(null)) {
                    if (p instanceof ClassOrInterfaceDeclaration) {
                        ClassOrInterfaceDeclaration cd = (ClassOrInterfaceDeclaration) p;
                        cd.removeModifier(Modifier.Keyword.PRIVATE);
                        cd.removeModifier(Modifier.Keyword.PROTECTED);
                        cd.addModifier(Modifier.Keyword.PUBLIC);
                    }
                }
                isSubscriber = true;
            }
        }
        if (isSubscriber) subscribers.add(cu.getPackageDeclaration().get().getNameAsString() + "." + cu.getPrimaryTypeName().get());

        // ---- variable name -> declared type (simple), good enough for MCreator's uniquely named locals
        Map<String, String> varTypes = new HashMap<>();
        cu.findAll(com.github.javaparser.ast.body.VariableDeclarator.class)
            .forEach(v -> varTypes.put(v.getNameAsString(), baseTypeName(v.getType())));
        cu.findAll(Parameter.class).forEach(p -> varTypes.put(p.getNameAsString(), baseTypeName(p.getType())));
        cu.findAll(TypePatternExpr.class).forEach(p -> varTypes.put(p.getNameAsString(), baseTypeName(p.getType())));

        // ---- network spawn packets are handled by FML in 1.7.10
        for (MethodDeclaration md : cu.findAll(MethodDeclaration.class))
            if (md.getNameAsString().equals("getAddEntityPacket") && md.getParameters().isEmpty()) md.remove();

        // ---- block entities need a no-arg constructor for 1.7.10 tile loading
        for (ClassOrInterfaceDeclaration cd : cu.findAll(ClassOrInterfaceDeclaration.class)) {
            boolean hasPosStateCtor = false, hasNoArg = false;
            for (ConstructorDeclaration c : cd.getConstructors()) {
                if (c.getParameters().isEmpty()) hasNoArg = true;
                if (c.getParameters().size() == 2 && c.getParameter(0).getType().asString().equals("BlockPos")
                    && c.getParameter(1).getType().asString().equals("BlockState")) hasPosStateCtor = true;
            }
            if (hasPosStateCtor && !hasNoArg) {
                ConstructorDeclaration nc = cd.addConstructor(Modifier.Keyword.PUBLIC);
                nc.setBody(StaticJavaParser.parseBlock("{ this(BlockPos.ZERO, null); }"));
            }
        }

        // ---- entity network-spawn constructors become the (World) constructor 1.7.10 needs
        for (ConstructorDeclaration ctor : cu.findAll(ConstructorDeclaration.class)) {
            if (ctor.getParameters().size() == 2 && ctor.getParameter(0).getType().asString().equals("SpawnEntity")) {
                ctor.getParameters().remove(0);
            }
        }
        // ---- Builder.of(X::new, ...) also gets X.class so registration knows the entity / tile class
        for (MethodCallExpr mc : cu.findAll(MethodCallExpr.class)) {
            if (!mc.getNameAsString().equals("of") || mc.getArguments().isEmpty()) continue;
            if (!mc.getScope().isPresent() || !mc.getScope().get().toString().endsWith("Builder")) continue;
            Expression first = mc.getArgument(0);
            if (first.isMethodReferenceExpr() && first.asMethodReferenceExpr().getIdentifier().equals("new")) {
                String cls = first.asMethodReferenceExpr().getScope().toString();
                mc.getArguments().add(1, new ClassExpr(new ClassOrInterfaceType(null, cls)));
            }
        }

        // ---- constructors with modern signatures
        for (ObjectCreationExpr oc : cu.findAll(ObjectCreationExpr.class)) {
            String tn = oc.getType().getNameAsString();
            if (ctors.contains(tn) && !oc.getAnonymousClassBody().isPresent()) {
                MethodCallExpr call = new MethodCallExpr(new NameExpr("M"), "new_" + tn, oc.getArguments());
                oc.replace(call);
            }
        }

        // ---- field accesses on vanilla objects
        for (FieldAccessExpr fa : cu.findAll(FieldAccessExpr.class)) {
            String mode = fields.get(fa.getNameAsString());
            if (mode == null) continue;
            Expression sc = fa.getScope();
            if (isTypeScope(sc, types)) continue;
            if (sc.isThisExpr() && mode.equals("other")) continue;
            Node parent = fa.getParentNode().orElse(null);
            if (parent instanceof AssignExpr && ((AssignExpr) parent).getTarget() == fa) {
                AssignExpr ae = (AssignExpr) parent;
                Expression value = ae.getValue().clone();
                if (ae.getOperator() != AssignExpr.Operator.ASSIGN) {
                    BinaryExpr.Operator op = ae.getOperator().toBinaryOperator().get();
                    value = new BinaryExpr(new MethodCallExpr(new NameExpr("M"), fa.getNameAsString(), new NodeList<>(sc.clone())),
                        new EnclosedExpr(value), op);
                }
                ae.replace(new MethodCallExpr(new NameExpr("M"), "set_" + fa.getNameAsString(), new NodeList<>(sc.clone(), value)));
            } else if (parent instanceof UnaryExpr) {
                UnaryExpr ue = (UnaryExpr) parent;
                String op = ue.getOperator().asString();
                if (op.equals("++") || op.equals("--")) {
                    Expression v = new BinaryExpr(new MethodCallExpr(new NameExpr("M"), fa.getNameAsString(), new NodeList<>(sc.clone())),
                        new IntegerLiteralExpr("1"), op.equals("++") ? BinaryExpr.Operator.PLUS : BinaryExpr.Operator.MINUS);
                    ue.replace(new MethodCallExpr(new NameExpr("M"), "set_" + fa.getNameAsString(), new NodeList<>(sc.clone(), v)));
                } else {
                    fa.replace(new MethodCallExpr(new NameExpr("M"), fa.getNameAsString(), new NodeList<>(sc.clone())));
                }
            } else {
                fa.replace(new MethodCallExpr(new NameExpr("M"), fa.getNameAsString(), new NodeList<>(sc.clone())));
            }
        }

        for (MethodDeclaration md : cu.findAll(MethodDeclaration.class))
            if (renames.containsKey(md.getNameAsString())) md.setName(renames.get(md.getNameAsString()));
        for (MethodCallExpr mc : cu.findAll(MethodCallExpr.class))
            if (renames.containsKey(mc.getNameAsString())) mc.setName(renames.get(mc.getNameAsString()));

        // ---- static members of vanilla-mapped classes that moved or never existed in 1.7.10
        for (FieldAccessExpr fa : cu.findAll(FieldAccessExpr.class)) {
            if (!fa.getScope().isNameExpr()) continue;
            String repl = statics.get(fa.getScope().asNameExpr().getNameAsString() + "." + fa.getNameAsString());
            if (repl == null) continue;
            fa.replace(StaticJavaParser.parseExpression(repl));
        }
        for (MethodCallExpr mc : cu.findAll(MethodCallExpr.class)) {
            if (!mc.getScope().isPresent() || !mc.getScope().get().isNameExpr()) continue;
            String repl = statics.get(mc.getScope().get().asNameExpr().getNameAsString() + "." + mc.getNameAsString());
            if (repl == null) continue;
            int dot = repl.lastIndexOf('.');
            mc.setScope(StaticJavaParser.parseExpression(repl.substring(0, dot)));
            mc.setName(repl.substring(dot + 1));
        }
        for (MethodReferenceExpr mr : cu.findAll(MethodReferenceExpr.class)) {
            String repl = statics.get(mr.getScope().toString() + "." + mr.getIdentifier());
            if (repl == null) continue;
            int dot = repl.lastIndexOf('.');
            mr.setScope(new TypeExpr(new ClassOrInterfaceType(null, repl.substring(0, dot))));
            mr.setIdentifier(repl.substring(dot + 1));
        }

        // ---- method calls on vanilla objects -> static helpers (innermost first)
        List<MethodCallExpr> calls = cu.findAll(MethodCallExpr.class);
        Collections.reverse(calls);
        for (MethodCallExpr mc : calls) {
            if (!mc.getScope().isPresent()) continue;
            Expression sc = mc.getScope().get();
            String name = mc.getNameAsString();
            if (sc.isSuperExpr()) continue;
            if (sc.isNameExpr() && sc.asNameExpr().getNameAsString().equals("M")) continue;
            if (isTypeScope(sc, types)) continue;
            Expression inner = sc;
            while (inner.isEnclosedExpr()) inner = inner.asEnclosedExpr().getInner();
            if (inner.isObjectCreationExpr() && inner.asObjectCreationExpr().getAnonymousClassBody().isPresent()) continue;
            if (!shouldRewrite(name)) continue;
            String scopeType = staticTypeOf(sc, mc, varTypes);
            if (scopeType != null && declaresMethod(scopeType, name)) continue;
            NodeList<Expression> args = new NodeList<>();
            args.add(sc.clone());
            for (Expression e : mc.getArguments()) args.add(e.clone());
            MethodCallExpr repl = new MethodCallExpr(new NameExpr("M"), name, args);
            mc.getTypeArguments().ifPresent(repl::setTypeArguments);
            mc.replace(repl);
        }

        if (cu.toString().contains("M.")) cu.addImport(M);
    }

    static String baseTypeName(Type t) {
        if (t.isClassOrInterfaceType()) return t.asClassOrInterfaceType().getNameAsString();
        return t.asString();
    }

    /** Best-effort static type of a call scope, only needed to recognise the mod's own classes. */
    static String staticTypeOf(Expression sc, Node at, Map<String, String> varTypes) {
        while (sc.isEnclosedExpr()) sc = sc.asEnclosedExpr().getInner();
        if (sc.isCastExpr()) return baseTypeName(sc.asCastExpr().getType());
        if (sc.isThisExpr()) {
            if (sc.asThisExpr().getTypeName().isPresent()) return sc.asThisExpr().getTypeName().get().asString();
            Optional<ClassOrInterfaceDeclaration> cd = at.findAncestor(ClassOrInterfaceDeclaration.class);
            return cd.map(ClassOrInterfaceDeclaration::getNameAsString).orElse(null);
        }
        if (sc.isNameExpr()) return varTypes.get(sc.asNameExpr().getNameAsString());
        if (sc.isFieldAccessExpr() && sc.asFieldAccessExpr().getScope().isThisExpr()) {
            Optional<ClassOrInterfaceDeclaration> cd = at.findAncestor(ClassOrInterfaceDeclaration.class);
            if (cd.isPresent()) {
                String fname = sc.asFieldAccessExpr().getNameAsString();
                for (com.github.javaparser.ast.body.FieldDeclaration fd : cd.get().getFields())
                    for (com.github.javaparser.ast.body.VariableDeclarator v : fd.getVariables())
                        if (v.getNameAsString().equals(fname)) return baseTypeName(v.getType());
            }
        }
        return null;
    }

    static boolean declaresMethod(String cls, String method) {
        for (int depth = 0; cls != null && depth < 10; depth++) {
            Set<String> ms = classMethods.get(cls);
            if (ms == null) return false;
            if (ms.contains(method)) return true;
            cls = classSuper.get(cls);
        }
        return false;
    }

    static boolean shouldRewrite(String name) {
        if (force.contains(name)) return true;
        if (keep.contains(name)) return false;
        if (ownDeclared.contains(name)) return false;
        return true;
    }

    static boolean isTypeScope(Expression sc, Set<String> types) {
        if (sc.isNameExpr()) {
            String n = sc.asNameExpr().getNameAsString();
            return Character.isUpperCase(n.charAt(0)) && (types.contains(n) || !n.equals(n.toUpperCase()) || n.length() == 1);
        }
        if (sc.isFieldAccessExpr()) {
            // Outer.Inner static scopes and fully qualified names
            String s = sc.toString();
            String last = sc.asFieldAccessExpr().getNameAsString();
            if (s.matches("([a-z_][a-z0-9_]*\\.)+[A-Z]\\w*")) return true;
            if (Character.isUpperCase(last.charAt(0)) && !last.equals(last.toUpperCase())
                && isTypeScope(sc.asFieldAccessExpr().getScope(), types)) return true;
        }
        return false;
    }
}
