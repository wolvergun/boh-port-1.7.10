import com.github.javaparser.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.expr.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

/** Lists every M.name(...) call in translated sources as "name/arity count example". */
public class MCalls {
    public static void main(String[] a) throws Exception {
        JavaParser p = new JavaParser(new ParserConfiguration().setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_17));
        Map<String, Integer> counts = new TreeMap<>();
        Map<String, String> example = new HashMap<>();
        List<Path> files;
        try (Stream<Path> s = Files.walk(Paths.get(a[0]))) { files = s.filter(f -> f.toString().endsWith(".java")).collect(Collectors.toList()); }
        for (Path f : files) {
            CompilationUnit cu = p.parse(f).getResult().get();
            for (MethodCallExpr m : cu.findAll(MethodCallExpr.class)) {
                if (!m.getScope().isPresent() || !m.getScope().get().toString().equals("M")) continue;
                String key = m.getNameAsString() + "/" + m.getArguments().size();
                counts.merge(key, 1, Integer::sum);
                String ex = m.toString();
                if (ex.length() < 160) example.putIfAbsent(key, ex);
            }
        }
        List<String> out = counts.entrySet().stream().map(e -> e.getKey() + " " + e.getValue() + "   " + example.getOrDefault(e.getKey(), "")).collect(Collectors.toList());
        Files.write(Paths.get(a[1]), out);
        System.out.println(counts.size() + " distinct M name/arity");
    }
}
