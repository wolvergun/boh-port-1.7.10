import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.zip.GZIPInputStream;

/** Prints the FML registry names stored in a 1.7.10 level.dat (FML/ItemData K entries). */
public class DumpRegistry {
    public static void main(String[] a) throws Exception {
        List<String> out = new ArrayList<>();
        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new GZIPInputStream(Files.newInputStream(Paths.get(a[0])))))) {
            int t = in.readByte();
            in.readUTF();
            read(in, t, "", out);
        }
        Collections.sort(out);
        Files.write(Paths.get(a[1]), out);
        System.out.println(out.size() + " names");
    }

    static void read(DataInputStream in, int type, String path, List<String> out) throws IOException {
        switch (type) {
            case 1: in.readByte(); return;
            case 2: in.readShort(); return;
            case 3: in.readInt(); return;
            case 4: in.readLong(); return;
            case 5: in.readFloat(); return;
            case 6: in.readDouble(); return;
            case 7: { int n = in.readInt(); in.skipBytes(n); return; }
            case 8: {
                String s = in.readUTF();
                if (path.endsWith("/K")) out.add(s);
                return;
            }
            case 9: { int et = in.readByte(); int n = in.readInt(); for (int i = 0; i < n; i++) read(in, et, path + "[]", out); return; }
            case 10: {
                while (true) {
                    int t = in.readByte();
                    if (t == 0) return;
                    String k = in.readUTF();
                    read(in, t, path + "/" + k, out);
                }
            }
            case 11: { int n = in.readInt(); in.skipBytes(n * 4); return; }
            case 12: { int n = in.readInt(); in.skipBytes(n * 8); return; }
            default: throw new IOException("tag " + type);
        }
    }
}
