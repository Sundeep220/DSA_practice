import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;

public class TailN {

    private static final int BLOCK_SIZE = 4096;

    public List<String> tail(Path path, int n) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(path.toFile(), "r")) {
            long length = file.length();
            if (n <= 0 || length == 0) {
                return List.of();
            }

            file.seek(length - 1);
            long end = file.read() == '\n' ? length - 1 : length;

            long start = findStart(file, end, n);
            byte[] bytes = new byte[(int) (length - start)];
            file.seek(start);
            file.readFully(bytes);

            String text = new String(bytes, StandardCharsets.UTF_8);
            if (text.endsWith("\n")) {
                text = text.substring(0, text.length() - 1);
            }
            return List.of(text.split("\n", -1));
        }
    }

    private long findStart(RandomAccessFile file, long end, int n) throws IOException {
        byte[] block = new byte[BLOCK_SIZE];
        int newlines = 0;
        long pos = end;
        while (pos > 0) {
            int size = (int) Math.min(BLOCK_SIZE, pos);
            long blockStart = pos - size;
            file.seek(blockStart);
            file.readFully(block, 0, size);
            for (int i = size - 1; i >= 0; i--) {
                if (block[i] == '\n' && ++newlines == n) {
                    return blockStart + i + 1;
                }
            }
            pos = blockStart;
        }
        return 0;
    }

    public static void main(String[] args) throws IOException {
        TailN tailN = new TailN();

        check("trailing newline, n=2", tailOf(tailN, "a\nb\nc\n", 2), List.of("b", "c"));
        check("no trailing newline, n=2", tailOf(tailN, "a\nb\nc", 2), List.of("b", "c"));
        check("n larger than line count", tailOf(tailN, "a\nb\nc\n", 5), List.of("a", "b", "c"));
        check("n=0", tailOf(tailN, "a\nb\nc\n", 0), List.of());
        check("empty file", tailOf(tailN, "", 3), List.of());
        check("single newline", tailOf(tailN, "\n", 1), List.of(""));
        check("empty line at end", tailOf(tailN, "a\n\n", 1), List.of(""));
        check("UTF-8 multibyte", tailOf(tailN, "h\u00e9llo\nw\u00f6rld\n", 1), List.of("w\u00f6rld"));

        StringBuilder large = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            large.append("line ").append(i).append('\n');
        }
        List<String> expectedLarge = IntStream.range(9000, 10000).mapToObj(i -> "line " + i).toList();
        check("file larger than one block, n=1000", tailOf(tailN, large.toString(), 1000), expectedLarge);
    }

    private static List<String> tailOf(TailN tailN, String content, int n) throws IOException {
        Path file = Files.createTempFile("tail", ".txt");
        try {
            Files.writeString(file, content, StandardCharsets.UTF_8);
            return tailN.tail(file, n);
        } finally {
            Files.deleteIfExists(file);
        }
    }

    private static void check(String name, List<String> actual, List<String> expected) {
        boolean passed = actual.equals(expected);
        System.out.println((passed ? "PASS " : "FAIL ") + name + " -> " + actual + (passed ? "" : " expected " + expected));
    }
}
