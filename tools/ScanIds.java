import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;
import java.util.zip.GZIPInputStream;

/** Collects modern block ids (+ property keys) from structure .nbt palettes and item/block ids from data JSON. */
public class ScanIds {
    static Map<String, Integer> blocks = new TreeMap<>();
    static Map<String, Set<String>> props = new TreeMap<>();

    public static void main(String[] a) throws Exception {
        Path data = Paths.get(a[0]);
        try (Stream<Path> s = Files.walk(data)) {
            for (Path p : s.filter(f -> f.toString().endsWith(".nbt")).collect(Collectors.toList())) {
                try (DataInputStream in = new DataInputStream(new BufferedInputStream(new GZIPInputStream(Files.newInputStream(p))))) {
                    int type = in.readByte();
                    in.readUTF();
                    readPayload(in, type, "");
                }
            }
        }
        Map<String, Integer> json = new TreeMap<>();
        Pattern id = Pattern.compile("\"(minecraft:[a-z0-9_/]+)\"");
        try (Stream<Path> s = Files.walk(data)) {
            for (Path p : s.filter(f -> f.toString().endsWith(".json")).collect(Collectors.toList())) {
                Matcher m = id.matcher(new String(Files.readAllBytes(p)));
                while (m.find()) json.merge(m.group(1), 1, Integer::sum);
            }
        }
        System.out.println("== structure palette blocks (" + blocks.size() + ")");
        blocks.forEach((k, v) -> System.out.println(v + " " + k + " " + props.getOrDefault(k, Collections.emptySet())));
        System.out.println("== json minecraft ids (" + json.size() + ")");
        json.forEach((k, v) -> System.out.println(v + " " + k));
    }

    static String lastName;

    static Object readPayload(DataInputStream in, int type, String path) throws IOException {
        switch (type) {
            case 1: return in.readByte();
            case 2: return in.readShort();
            case 3: return in.readInt();
            case 4: return in.readLong();
            case 5: return in.readFloat();
            case 6: return in.readDouble();
            case 7: { int n = in.readInt(); in.skipBytes(n); return null; }
            case 8: return in.readUTF();
            case 9: {
                int et = in.readByte(); int n = in.readInt();
                for (int i = 0; i < n; i++) readPayload(in, et, path + "[]");
                return null;
            }
            case 10: {
                String name = null; Map<String, String> pr = new TreeMap<>();
                while (true) {
                    int t = in.readByte();
                    if (t == 0) break;
                    String key = in.readUTF();
                    Object v = readPayload(in, t, path + "/" + key);
                    if (path.endsWith("palette[]") && key.equals("Name")) name = (String) v;
                }
                if (name != null) blocks.merge(name, 1, Integer::sum);
                return null;
            }
            case 11: { int n = in.readInt(); in.skipBytes(n * 4); return null; }
            case 12: { int n = in.readInt(); in.skipBytes(n * 8); return null; }
            default: throw new IOException("bad tag " + type);
        }
    }
}
