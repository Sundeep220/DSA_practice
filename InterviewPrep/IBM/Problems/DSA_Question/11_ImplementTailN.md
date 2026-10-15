# 11. Implement `tail -n`

**Tier:** 5 (Confluent scenario problems)
**Link:** No LeetCode equivalent.

## Problem

Given the path to a file that may be larger than memory, and an integer `n`, return the last `n` lines of the file without loading the whole file into memory. Lines are separated by `\n`.

## Intuition

The only line separator is `\n`, so the answer starts right after the n-th `\n` counting from the end of the file. Scan backwards in fixed-size blocks and count newlines until you have seen `n` of them. Then read only the bytes from that offset to the end.

A `\n` at the very end of the file terminates the last line. It does not start a new line, so it must not be counted. That is why the scan starts one byte before EOF in that case.

## Approach

1. If `n <= 0` or the file is empty, return an empty list.
2. Read the last byte. If it is `\n`, set `end = length - 1`. Otherwise set `end = length`.
3. Walk backwards from `end` in blocks of `BLOCK_SIZE` bytes. Each `\n` found increments `newlines`. When `newlines == n`, the answer starts at that index + 1.
4. If the start of the file is reached first, the answer starts at 0.
5. Read the bytes from `start` to EOF, decode them as UTF-8, remove the final `\n` if present, and split on `\n`.

## Complexity

Let `F` be the file size in bytes, `T` the number of bytes from the answer's start to EOF, and `B` the block size.

- **Block reads:** about `(T + B) / B` reads, because the scan stops at the block that contains the n-th newline from the end.
- **Time:** O(T + B) to scan backwards, plus O(T) to decode and split the result. In the worst case, where the file has fewer than `n` lines, the scan reads the whole file, so the time is O(F).
- **Space:** O(B + T). One `B`-byte block buffer, one `T`-byte array for the answer, and the output list. The output must fit in memory.

## Dry run

Input file content `"alpha\nbeta\ngamma\n"`, with `n = 2`. Bytes: `alpha` is 0 to 4, `\n` at 5, `beta` is 6 to 9, `\n` at 10, `gamma` is 11 to 15, `\n` at 16. The file length is 17.

| Step | State | Action |
|---|---|---|
| 1 | `length = 17`, last byte at index 16 is `\n` | Set `end = 16` |
| 2 | `pos = 16` | Read block `[0, 16)`, scan from index 15 down |
| 3 | Index 10 is `\n` | `newlines = 1` |
| 4 | Index 5 is `\n` | `newlines = 2 == n`, return `start = 5 + 1 = 6` |
| 5 | Read bytes `[6, 17)` = `beta\ngamma\n` | Strip final `\n`, split: `["beta", "gamma"]` |

The example fits in one block. A larger file uses the same logic, with `pos` moving back one block at a time.

## Edge cases

- `n <= 0`: returns an empty list.
- `n` larger than the number of lines: returns the whole file.
- Empty file: returns an empty list.
- No trailing newline: the last line is returned as is.
- File containing only `\n`: returns one empty line.
- Consecutive newlines: each empty line counts as one line.
- Multi-byte UTF-8: the byte `0x0A` never appears inside a multi-byte UTF-8 sequence, so byte-level scanning is safe.
- CRLF files: a `\r` stays at the end of each line.
- Very large `n`: the output may not fit in memory. Stream lines to a writer instead of building a list.

## Code

```java
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

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
}
```

## Interview one-liner

"I read backwards in fixed-size blocks with `RandomAccessFile` and count newlines. Once I've seen n of them, I read from that offset to EOF. The work is proportional to the tail's size, not the file's, and memory is one block plus the output."
