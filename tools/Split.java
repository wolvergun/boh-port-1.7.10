import java.nio.file.*;
import java.util.*;

/** Splits a bundle file with "//// FILE: relative/path" markers into files under a root directory. */
public class Split {
    public static void main(String[] a) throws Exception {
        Path root = Paths.get(a[1]);
        List<String> lines = Files.readAllLines(Paths.get(a[0]));
        Path cur = null;
        StringBuilder sb = new StringBuilder();
        int n = 0;
        for (String l : lines) {
            if (l.startsWith("//// FILE: ")) {
                if (cur != null) { write(cur, sb); n++; }
                cur = root.resolve(l.substring(11).trim());
                sb.setLength(0);
            } else if (cur != null) {
                sb.append(l).append('\n');
            }
        }
        if (cur != null) { write(cur, sb); n++; }
        System.out.println("wrote " + n + " files");
    }

    static void write(Path p, StringBuilder sb) throws Exception {
        Files.createDirectories(p.getParent());
        String s = sb.toString();
        while (s.endsWith("\n\n")) s = s.substring(0, s.length() - 1);
        Files.write(p, s.getBytes("UTF-8"));
    }
}
