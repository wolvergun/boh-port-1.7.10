import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

/** Rewrites SRG member names (m_123_/f_123_) in decompiled sources to Mojang names. */
public class Remap {
    public static void main(String[] a) throws Exception {
        Path tsrg = Paths.get(a[0]), moj = Paths.get(a[1]), root = Paths.get(a[2]);

        // Mojang: obfClass -> named class, and (obfClass, obfName, obfDescOrField) -> named
        Map<String, String> obfToNamedCls = new HashMap<>(), namedToObfCls = new HashMap<>();
        List<String> mojLines = Files.readAllLines(moj);
        for (String l : mojLines) {
            if (l.startsWith("#") || l.startsWith(" ")) continue;
            String[] p = l.substring(0, l.length() - 1).split(" -> ");
            obfToNamedCls.put(p[1], p[0]);
            namedToObfCls.put(p[0], p[1]);
        }
        Map<String, String> memberNamed = new HashMap<>();
        String cur = null;
        for (String l : mojLines) {
            if (l.startsWith("#")) continue;
            if (!l.startsWith(" ")) { cur = l.substring(0, l.length() - 1).split(" -> ")[1]; continue; }
            String t = l.trim();
            String[] p = t.split(" -> ");
            String obf = p[1];
            String left = p[0].replaceFirst("^\\d+:\\d+:", "");
            int sp = left.indexOf(' ');
            String type = left.substring(0, sp), rest = left.substring(sp + 1);
            int par = rest.indexOf('(');
            if (par < 0) {
                memberNamed.put(cur + " " + obf + " F", rest);
            } else {
                String name = rest.substring(0, par);
                String args = rest.substring(par + 1, rest.length() - 1);
                StringBuilder d = new StringBuilder("(");
                if (!args.isEmpty()) for (String x : args.split(",")) d.append(desc(x, namedToObfCls));
                d.append(")").append(desc(type, namedToObfCls));
                memberNamed.put(cur + " " + obf + " " + d, name);
            }
        }

        Map<String, String> srg = new HashMap<>();
        String cls = null;
        for (String l : Files.readAllLines(tsrg)) {
            if (l.startsWith("tsrg2")) continue;
            if (l.startsWith("\t\t")) continue;
            String[] p = l.trim().split(" ");
            if (!l.startsWith("\t")) { cls = p[0]; continue; }
            String named;
            if (p.length == 3) { named = memberNamed.get(cls + " " + p[0] + " F"); if (named != null) srg.put(p[1], named); }
            else if (p.length == 4) { named = memberNamed.get(cls + " " + p[0] + " " + p[1]); if (named != null) srg.put(p[2], named); }
        }
        System.out.println("srg entries: " + srg.size());

        Pattern pat = Pattern.compile("\\b[mf]_\\d+_\\b");
        Set<String> missing = new TreeSet<>();
        List<Path> files;
        try (Stream<Path> s = Files.walk(root)) { files = s.filter(f -> f.toString().endsWith(".java")).collect(Collectors.toList()); }
        for (Path f : files) {
            String src = new String(Files.readAllBytes(f));
            Matcher m = pat.matcher(src);
            StringBuffer sb = new StringBuffer();
            while (m.find()) {
                String r = srg.get(m.group());
                if (r == null) { missing.add(m.group()); r = m.group(); }
                m.appendReplacement(sb, Matcher.quoteReplacement(r));
            }
            m.appendTail(sb);
            Files.write(f, sb.toString().getBytes());
        }
        System.out.println("files: " + files.size() + ", unmapped tokens: " + missing.size() + " " + missing.stream().limit(20).collect(Collectors.toList()));
    }

    static String desc(String t, Map<String, String> n2o) {
        int dims = 0;
        while (t.endsWith("[]")) { dims++; t = t.substring(0, t.length() - 2); }
        String b;
        switch (t) {
            case "int": b = "I"; break; case "long": b = "J"; break; case "float": b = "F"; break;
            case "double": b = "D"; break; case "boolean": b = "Z"; break; case "byte": b = "B"; break;
            case "char": b = "C"; break; case "short": b = "S"; break; case "void": b = "V"; break;
            default: b = "L" + n2o.getOrDefault(t, t).replace('.', '/') + ";";
        }
        return "[".repeat(dims) + b;
    }
}
