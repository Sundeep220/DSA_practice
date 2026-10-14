package InterviewPrep.IBM.Problems.LRUCache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Development history
 *
 * 1. Simple LRU cache: a HashMap gives O(1) lookup, and a doubly linked list
 *    with head and tail sentinels keeps entries ordered from most to least
 *    recently used. get and put are O(1). put evicts the least recently used
 *    entry when the cache is full.
 *
 * 2. Thread safety: get, put, and printCache are synchronized on the instance,
 *    so the map, the list, and the size check change together under one lock.
 *    printCache returns a read-only copy so callers never see a half-updated map.
 *
 * 3. TTL: each node stores an expiresAt timestamp taken from now()
 *    (System.nanoTime by default, overridden in tests). get removes expired entries lazily and reports
 *    them as misses. When the cache is over capacity, expired entries are purged
 *    before the least recently used live entry is evicted.
 */
public class LRUCache {

    static class Node{
        int key;
        int val;
        Node prev, next;
        long expiresAt = NO_EXPIRY;

        public Node(int key, int val){
            this.key = key;
            this.val = val;
        }

        public String toString(){
            return "(" + "key: " + this.key + ", " + "value: " + this.val + ")";
        }
    }

    private static final long NO_EXPIRY = Long.MAX_VALUE;

    Map<Integer, Node> nodeMap;
    Node head, tail;
    int capacity;

    public LRUCache(int capacity){
        this.nodeMap = new HashMap<>();
        this.capacity = capacity;
        this.head = new Node(-1, -1);
        this.tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;
    }

    // Time source for TTL. Tests override this to control time.
    protected long now(){
        return System.nanoTime();
    }

    public synchronized int get(int key){
        if(nodeMap.containsKey(key)){
            Node node = nodeMap.get(key);
            // Lazy expiry: an expired entry is removed on access and reported as a miss.
            if(isExpired(node, now())){
                removeKey(node);
                return -1;
            }
            moveNodeToFront(node);
            return node.val;
        }else return -1;
    }

    // Plain put: the entry never expires.
    public synchronized void put(int key, int val){
        write(key, val, NO_EXPIRY, now());
    }

    // TTL put: the entry expires ttlMillis after this write. A non-positive TTL removes the key instead of storing it.
    public synchronized void put(int key, int val, long ttlMillis){
        if(ttlMillis <= 0){
            if(nodeMap.containsKey(key)) removeKey(nodeMap.get(key));
            return;
        }
        long currentTime = now();
        long expiresAt = currentTime + TimeUnit.MILLISECONDS.toNanos(ttlMillis);
        // If the addition wrapped past Long.MAX_VALUE, the expiry is out of range, so treat the entry as never expiring.
        if(expiresAt < currentTime) expiresAt = NO_EXPIRY;
        write(key, val, expiresAt, currentTime);
    }

    // Shared write path: insert or update, move to front, then enforce capacity.
    private void write(int key, int val, long expiresAt, long now){
        if(nodeMap.containsKey(key)){
            Node node = nodeMap.get(key);
            node.val = val;
            node.expiresAt = expiresAt;
            moveNodeToFront(node);
        }else{
            Node node = new Node(key, val);
            node.expiresAt = expiresAt;
            nodeMap.put(key, node);
            addNodeToFront(node);
        }

        // Over capacity: drop expired entries first, so a live entry is evicted only when nothing has expired.
        if(nodeMap.size() > capacity){
            purgeExpired(now);
        }
        if(nodeMap.size() > capacity){
            Node lru = removeLastNode();
            nodeMap.remove(lru.key);
        }
    }

    private Node removeLastNode(){
        Node last = tail.prev;
        tail.prev = last.prev;
        last.prev.next = tail;
        last.next = null;
        return last;
    }

    private void addNodeToFront(Node node){
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private void moveNodeToFront(Node node){
        removeNode(node);
        addNodeToFront(node);
    }

    private void removeNode(Node node){
        Node prev = node.prev;
        Node next = node.next;
        prev.next = next;
        next.prev = prev;
    }

    // Removes every expired entry. Walks the whole list, so it costs O(n).
    private void purgeExpired(long now){
        Node node = head.next;
        while(node != tail){
            Node next = node.next;
            if(isExpired(node, now)) removeKey(node);
            node = next;
        }
    }

    // Wraparound-safe comparison: the difference stays correct even if the clock's counter overflows.
    private boolean isExpired(Node node, long now){
        return node.expiresAt != NO_EXPIRY && now - node.expiresAt >= 0;
    }

    private void removeKey(Node node){
        removeNode(node);
        nodeMap.remove(node.key);
    }

    public synchronized Map<Integer, Node> printCache(){
        return Collections.unmodifiableMap(new HashMap<>(nodeMap));
    }

    private static void expectEquals(int expected, int actual){
        if(expected != actual){
            throw new AssertionError("expected " + expected + " but was " + actual);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        expectEquals(1, cache.get(1));
        cache.put(3, 3);
        expectEquals(-1, cache.get(2));
        cache.put(4, 4);
        expectEquals(-1, cache.get(1));
        expectEquals(3, cache.get(3));
        expectEquals(4, cache.get(4));

        cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        expectEquals(2, cache.get(2));
        cache.put(4, 4);
        expectEquals(2, cache.get(2));
        expectEquals(-1, cache.get(3));

        cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(1, 10);
        cache.put(3, 3);
        expectEquals(-1, cache.get(2));
        expectEquals(10, cache.get(1));

        cache = new LRUCache(1);
        cache.put(2, 1);
        expectEquals(1, cache.get(2));
        cache.put(3, 2);
        expectEquals(-1, cache.get(2));
        expectEquals(2, cache.get(3));

        cache = new LRUCache(3);
        expectEquals(-1, cache.get(1));
        cache.put(1, 10);
        expectEquals(10, cache.get(1));
        cache.put(3, 5);
        cache.put(4, 9);
        cache.put(6, 11);
        expectEquals(-1, cache.get(1));
        expectEquals(11, cache.get(6));
        expectEquals(5, cache.get(3));
        expectEquals(9, cache.get(4));

        // TTL checks use a fake clock, so expiry is deterministic.
        long[] nowNanos = {0};
        LRUCache ttlCache = new LRUCache(2) {
            @Override
            protected long now(){
                return nowNanos[0];
            }
        };
        ttlCache.put(1, 10, 10);
        nowNanos[0] += TimeUnit.MILLISECONDS.toNanos(9);
        expectEquals(10, ttlCache.get(1));
        nowNanos[0] += TimeUnit.MILLISECONDS.toNanos(1);
        expectEquals(-1, ttlCache.get(1));

        ttlCache.put(2, 20, 10);
        nowNanos[0] += TimeUnit.MILLISECONDS.toNanos(5);
        ttlCache.put(2, 21, 10);
        nowNanos[0] += TimeUnit.MILLISECONDS.toNanos(7);
        expectEquals(21, ttlCache.get(2));

        ttlCache.put(3, 30, 10);
        nowNanos[0] += TimeUnit.MILLISECONDS.toNanos(6);
        expectEquals(30, ttlCache.get(3));
        nowNanos[0] += TimeUnit.MILLISECONDS.toNanos(5);
        expectEquals(-1, ttlCache.get(3));

        ttlCache.put(4, 40, 0);
        expectEquals(-1, ttlCache.get(4));

        LRUCache evictCache = new LRUCache(2) {
            @Override
            protected long now(){
                return nowNanos[0];
            }
        };
        evictCache.put(1, 1);
        evictCache.put(2, 2, 5);
        nowNanos[0] += TimeUnit.MILLISECONDS.toNanos(6);
        evictCache.put(3, 3);
        expectEquals(1, evictCache.get(1));
        expectEquals(3, evictCache.get(3));

        LRUCache shared = new LRUCache(3);
        Thread[] workers = new Thread[4];
        for(int t = 0; t < workers.length; t++){
            workers[t] = new Thread(() -> {
                for(int i = 0; i < 10000; i++){
                    int key = (int)(Math.random() * 10);
                    if(Math.random() < 0.5) shared.put(key, i);
                    else shared.get(key);
                }
            });
            workers[t].start();
        }
        for(Thread worker : workers) worker.join();

        if(shared.nodeMap.size() > 3) throw new AssertionError("cache exceeded capacity");
        int listSize = 0;
        for(Node n = shared.head.next; n != shared.tail; n = n.next) listSize++;
        expectEquals(shared.nodeMap.size(), listSize);

        System.out.println("All LRUCache checks passed");
    }
}
