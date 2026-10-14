import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TimeBasedKeyValueStoreUnordered {

    private final Map<String, TreeMap<Integer, String>> store = new HashMap<>();

    public void set(String key, String value, int timestamp) {
        store.computeIfAbsent(key, k -> new TreeMap<>()).put(timestamp, value);
    }

    public String get(String key, int timestamp) {
        TreeMap<Integer, String> entries = store.get(key);

        if (entries == null) {
            return "";
        }

        Map.Entry<Integer, String> floor = entries.floorEntry(timestamp);
        return floor == null ? "" : floor.getValue();
    }

    public static void main(String[] args) {
        TimeBasedKeyValueStoreUnordered store = new TimeBasedKeyValueStoreUnordered();
        store.set("foo", "bar", 5);
        store.set("foo", "bar2", 1);
        System.out.println(store.get("foo", 3));
        System.out.println(store.get("foo", 5));
        System.out.println(store.get("foo", 0));
    }
}
