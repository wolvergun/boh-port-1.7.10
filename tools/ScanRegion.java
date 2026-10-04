import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.zip.InflaterInputStream;
import java.util.zip.GZIPInputStream;

/** Debug: counts blocks (by registry name via level.dat FML ItemData) per y level in a dimension's region files. */
public class ScanRegion {

    public static void main(String[] a) throws Exception {
        Path save = Paths.get(a[0]);
        String dim = a[1];
        Map<Integer, String> names = new HashMap<>();
        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new GZIPInputStream(Files.newInputStream(save.resolve("level.dat")))))) {
            in.readByte();
            in.readUTF();
            Map<String, Object> root = (Map<String, Object>) ScanNbt.read(in, 10);
            Map<String, Object> fml = (Map<String, Object>) root.get("FML");
            List<Object> items = fml == null ? null : (List<Object>) fml.get("ItemData");
            if (items != null) for (Object o : items) {
                Map<String, Object> m = (Map<String, Object>) o;
                String k = (String) m.get("K");
                if (k.charAt(0) == '\u0001') names.put((Integer) m.get("V"), k.substring(1));
            }
        }
        Map<String, Integer> counts = new TreeMap<>();
        Map<Integer, Map<String, Integer>> byY = new TreeMap<>();
        int chunks = 0, populated = 0;
        Set<String> keys = new TreeSet<>();
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(save.resolve(dim).resolve("region"), "*.mca")) {
            for (Path p : ds) {
                byte[] f = Files.readAllBytes(p);
                for (int i = 0; i < 1024; i++) {
                    int loc = ((f[i * 4] & 255) << 16) | ((f[i * 4 + 1] & 255) << 8) | (f[i * 4 + 2] & 255);
                    if (loc == 0) continue;
                    int off = loc * 4096;
                    int len = ((f[off] & 255) << 24) | ((f[off + 1] & 255) << 16) | ((f[off + 2] & 255) << 8) | (f[off + 3] & 255);
                    DataInputStream in = new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(f, off + 5, len - 1))));
                    in.readByte();
                    in.readUTF();
                    Map<String, Object> root = (Map<String, Object>) ScanNbt.read(in, 10);
                    Map<String, Object> level = (Map<String, Object>) root.get("Level");
                    chunks++;
                    Object tp = level.get("TerrainPopulated");
                    if (tp instanceof Byte && (Byte) tp != 0) populated++;
                    for (Object so : (List<Object>) level.get("Sections")) {
                        Map<String, Object> s = (Map<String, Object>) so;
                        keys.addAll(s.keySet());
                        int sy = ((Byte) s.get("Y")) * 16;
                        byte[] blocks = (byte[]) s.get("Blocks");
                        byte[] add = (byte[]) s.get("Add");
                        if (blocks == null) continue;
                        for (int j = 0; j < 4096; j++) {
                            int id = blocks[j] & 255;
                            if (add != null) id |= ((add[j >> 1] >> ((j & 1) * 4)) & 15) << 8;
                            if (id == 0) continue;
                            String n = names.getOrDefault(id, "#" + id);
                            counts.merge(n, 1, Integer::sum);
                            byY.computeIfAbsent(sy + (j >> 8), k -> new TreeMap<>()).merge(n, 1, Integer::sum);
                        }
                    }
                }
            }
        }
        System.out.println("chunks=" + chunks + " populated=" + populated + " section keys=" + keys);
        System.out.println(counts);
        for (Map.Entry<Integer, Map<String, Integer>> e : byY.entrySet()) if (e.getKey() < 12) System.out.println("y" + e.getKey() + " " + e.getValue());
    }
}
