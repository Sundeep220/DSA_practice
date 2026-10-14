import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TtlKVStoreWithAverage {

    private static final class Entry {
        final long timestamp;
        final String key;
        final double value;

        Entry(long timestamp, String key, double value) {
            this.timestamp = timestamp;
            this.key = key;
            this.value = value;
        }
    }

    private final long ttlMillis;
    private final Map<String, Deque<Entry>> entriesByKey = new HashMap<>();
    private final Deque<Entry> globalQueue = new ArrayDeque<>();
    private double runningSum = 0.0;

    public TtlKVStoreWithAverage(long ttlMillis) {
        this.ttlMillis = ttlMillis;
    }

    public void put(long timestamp, String key, double value) {
        Entry entry = new Entry(timestamp, key, value);
        entriesByKey.computeIfAbsent(key, k -> new ArrayDeque<>()).addLast(entry);
        globalQueue.addLast(entry);
        runningSum += value;
    }

    public Double get(long queryTimestamp, String key) {
        Deque<Entry> keyEntries = entriesByKey.get(key);
        if (keyEntries == null) {
            return null;
        }

        Iterator<Entry> newestFirst = keyEntries.descendingIterator();
        while (newestFirst.hasNext()) {
            Entry entry = newestFirst.next();
            if (entry.timestamp <= queryTimestamp) {
                boolean notExpired = queryTimestamp - entry.timestamp < ttlMillis;
                return notExpired ? entry.value : null;
            }
        }
        return null;
    }

    public double getAverage(long queryTimestamp) {
        while (!globalQueue.isEmpty()
                && queryTimestamp - globalQueue.peekFirst().timestamp >= ttlMillis) {
            Entry expired = globalQueue.pollFirst();
            runningSum -= expired.value;
            entriesByKey.get(expired.key).pollFirst();
        }

        if (globalQueue.isEmpty()) {
            throw new IllegalStateException("No active entries, cannot compute average");
        }
        return runningSum / globalQueue.size();
    }

    public static void main(String[] args) {
        TtlKVStoreWithAverage store = new TtlKVStoreWithAverage(10);
        store.put(1, "a", 10);
        store.put(2, "b", 20);
        store.put(5, "a", 30);
        System.out.println(store.getAverage(5));
        System.out.println(store.get(5, "a"));
        System.out.println(store.getAverage(11));
        System.out.println(store.get(11, "a"));
    }
}
