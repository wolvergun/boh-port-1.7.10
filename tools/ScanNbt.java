import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.zip.GZIPInputStream;

/** Lists palette block names (+ property keys) and entity ids used by 1.20 structure .nbt files. */
public class ScanNbt {

    public static void main(String[] a) throws Exception {
        Map<String, Integer> counts = new TreeMap<>();
        Map<String, Set<String>> props = new TreeMap<>();
        Map<String, Integer> ents = new TreeMap<>();
        Set<String> beIds = new TreeSet<>();
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(Paths.get(a[0]), "*.nbt")) {
            for (Path p : ds) {
                DataInputStream in = new DataInputStream(new BufferedInputStream(new GZIPInputStream(Files.newInputStream(p))));
                in.readByte();
                in.readUTF();
                Map<String, Object> root = (Map<String, Object>) read(in, 10);
                List<Object> palette = (List<Object>) root.get("palette");
                if (palette == null && root.get("palettes") != null) palette = (List<Object>) ((List<Object>) root.get("palettes")).get(0);
                if (palette != null) for (Object o : palette) {
                    Map<String, Object> s = (Map<String, Object>) o;
                    String n = (String) s.get("Name");
                    counts.merge(n, 1, Integer::sum);
                    if (s.get("Properties") != null) props.computeIfAbsent(n, k -> new TreeSet<>()).addAll(((Map<String, Object>) s.get("Properties")).keySet());
                }
                List<Object> blocks = (List<Object>) root.get("blocks");
                if (blocks != null) for (Object o : blocks) {
                    Map<String, Object> b = (Map<String, Object>) o;
                    if (b.get("nbt") != null) beIds.add(String.valueOf(((Map<String, Object>) b.get("nbt")).get("id")));
                }
                List<Object> es = (List<Object>) root.get("entities");
                if (es != null) for (Object o : es) {
                    Map<String, Object> e = (Map<String, Object>) o;
                    Map<String, Object> nbt = (Map<String, Object>) e.get("nbt");
                    ents.merge(nbt == null ? "?" : String.valueOf(nbt.get("id")), 1, Integer::sum);
                }
            }
        }
        for (Map.Entry<String, Integer> e : counts.entrySet())
            System.out.println(e.getKey() + " " + props.getOrDefault(e.getKey(), Collections.emptySet()));
        System.out.println("BLOCK ENTITIES " + beIds);
        System.out.println("ENTITIES " + ents);
    }

    static Object read(DataInputStream in, int type) throws IOException {
        switch (type) {
            case 1: return in.readByte();
            case 2: return in.readShort();
            case 3: return in.readInt();
            case 4: return in.readLong();
            case 5: return in.readFloat();
            case 6: return in.readDouble();
            case 7: { int n = in.readInt(); byte[] b = new byte[n]; in.readFully(b); return b; }
            case 8: return in.readUTF();
            case 9: { int t = in.readByte(); int n = in.readInt(); List<Object> l = new ArrayList<>(); for (int i = 0; i < n; i++) l.add(read(in, t)); return l; }
            case 10: { Map<String, Object> m = new LinkedHashMap<>(); while (true) { int t = in.readByte(); if (t == 0) break; String k = in.readUTF(); m.put(k, read(in, t)); } return m; }
            case 11: { int n = in.readInt(); int[] v = new int[n]; for (int i = 0; i < n; i++) v[i] = in.readInt(); return v; }
            case 12: { int n = in.readInt(); long[] v = new long[n]; for (int i = 0; i < n; i++) v[i] = in.readLong(); return v; }
            default: throw new IOException("bad tag " + type);
        }
    }
}
