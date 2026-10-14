import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeBasedKeyValueStore {

    private static final class TimestampedValue {
        final int timestamp;
        final String value;

        TimestampedValue(int timestamp, String value) {
            this.timestamp = timestamp;
            this.value = value;
        }
    }

    private final Map<String, List<TimestampedValue>> store = new HashMap<>();

    public void set(String key, String value, int timestamp) {
        store.computeIfAbsent(key, k -> new ArrayList<>())
             .add(new TimestampedValue(timestamp, value));
    }

    public String get(String key, int timestamp) {
        List<TimestampedValue> entries = store.get(key);

        if (entries == null || entries.isEmpty()) {
            return "";
        }

        int low = 0;
        int high = entries.size() - 1;
        String bestValue = "";

        while (low <= high) {
            int mid = low + (high - low) / 2;
            TimestampedValue midEntry = entries.get(mid);

            if (midEntry.timestamp <= timestamp) {
                bestValue = midEntry.value;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return bestValue;
    }

    public static void main(String[] args) {
        TimeBasedKeyValueStore store = new TimeBasedKeyValueStore();
        store.set("foo", "bar", 1);
        store.set("foo", "bar2", 4);
        System.out.println(store.get("foo", 3));
        System.out.println(store.get("foo", 4));
        System.out.println(store.get("foo", 0));
    }
}
