package InterviewPrep.IBM.Problems.ProducerConsumer;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

// ─────────────────────────────────────────────────────────────────────────────
// Variant 1: Manual blocking queue using wait / notifyAll
// ─────────────────────────────────────────────────────────────────────────────

class BlockingBoundedQueue {

    private final Object[] buffer;
    private final int capacity;
    private int head;
    private int tail;
    private int size;

    public BlockingBoundedQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.buffer = new Object[capacity];
    }

    /**
     * Inserts an element, blocking until space is available.
     */
    public synchronized void put(Object value) throws InterruptedException {
        while (size == capacity) {
            wait();
        }
        buffer[tail] = value;
        tail = (tail + 1) % capacity;
        size++;
        notifyAll();
    }

    /**
     * Removes and returns the head element, blocking until one is available.
     */
    @SuppressWarnings("unchecked")
    public synchronized Object take() throws InterruptedException {
        while (size == 0) {
            wait();
        }
        Object value = buffer[head];
        buffer[head] = null;
        head = (head + 1) % capacity;
        size--;
        notifyAll();
        return value;
    }

    /**
     * Non-blocking insert: returns false if full instead of blocking.
     */
    public synchronized boolean offer(Object value) {
        if (size == capacity) {
            return false;
        }
        buffer[tail] = value;
        tail = (tail + 1) % capacity;
        size++;
        notifyAll();
        return true;
    }

    /**
     * Non-blocking remove: returns null if empty instead of blocking.
     */
    public synchronized Object poll() {
        if (size == 0) {
            return null;
        }
        Object value = buffer[head];
        buffer[head] = null;
        head = (head + 1) % capacity;
        size--;
        notifyAll();
        return value;
    }

    public synchronized int size() { return size; }
    public synchronized boolean isEmpty() { return size == 0; }
    public synchronized boolean isFull() { return size == capacity; }
}

// ─────────────────────────────────────────────────────────────────────────────
// Variant 2: ArrayBlockingQueue wrapper (library version, for discussion)
// ─────────────────────────────────────────────────────────────────────────────

class LibraryBlockingQueue {

    private final BlockingQueue<Integer> queue;

    public LibraryBlockingQueue(int capacity) {
        this.queue = new ArrayBlockingQueue<>(capacity);
    }

    public void put(int value) throws InterruptedException {
        queue.put(value);
    }

    public int take() throws InterruptedException {
        return queue.take();
    }

    public boolean offer(int value) {
        return queue.offer(value);
    }

    public Integer poll() {
        return queue.poll();
    }

    public int size() { return queue.size(); }
}

// ─────────────────────────────────────────────────────────────────────────────
// Variant 3: ReentrantLock + two Condition objects
//
// notFull  — producers await() here when the buffer is full.
// notEmpty — consumers await() here when the buffer is empty.
//
// put signals only notEmpty; take signals only notFull.
// This avoids waking producers when only a consumer slot opened up (and vice
// versa), which is the main advantage over a single notifyAll.
// ─────────────────────────────────────────────────────────────────────────────

class ReentrantLockBlockingQueue {

    private final Object[] buffer;
    private final int capacity;
    private int head;
    private int tail;
    private int size;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull  = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public ReentrantLockBlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.buffer = new Object[capacity];
    }

    /**
     * Blocks until a slot is free, then inserts the value.
     */
    public void put(Object value) throws InterruptedException {
        lock.lock();
        try {
            while (size == capacity) {
                notFull.await();
            }
            buffer[tail] = value;
            tail = (tail + 1) % capacity;
            size++;
            notEmpty.signal(); // wake exactly one waiting consumer
        } finally {
            lock.unlock();
        }
    }

    /**
     * Blocks until an item is available, then removes and returns it.
     */
    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (size == 0) {
                notEmpty.await();
            }
            Object value = buffer[head];
            buffer[head] = null;
            head = (head + 1) % capacity;
            size--;
            notFull.signal(); // wake exactly one waiting producer
            return value;
        } finally {
            lock.unlock();
        }
    }

    public boolean offer(Object value) {
        lock.lock();
        try {
            if (size == capacity) return false;
            buffer[tail] = value;
            tail = (tail + 1) % capacity;
            size++;
            notEmpty.signal();
            return true;
        } finally {
            lock.unlock();
        }
    }

    public Object poll() {
        lock.lock();
        try {
            if (size == 0) return null;
            Object value = buffer[head];
            buffer[head] = null;
            head = (head + 1) % capacity;
            size--;
            notFull.signal();
            return value;
        } finally {
            lock.unlock();
        }
    }

    public int size()        { lock.lock(); try { return size; }       finally { lock.unlock(); } }
    public boolean isEmpty() { lock.lock(); try { return size == 0; }  finally { lock.unlock(); } }
    public boolean isFull()  { lock.lock(); try { return size == capacity; } finally { lock.unlock(); } }
}

// ─────────────────────────────────────────────────────────────────────────────
// Variant 4: Semaphore-based blocking queue
//
// canProduce (permits = capacity) — a producer acquires one before writing.
// canConsume (permits = 0)        — a consumer acquires one before reading.
// mutex      (permits = 1)        — guards the ring buffer itself.
//
// No wait/notifyAll needed: the semaphore counts replace the condition checks.
// ─────────────────────────────────────────────────────────────────────────────

class SemaphoreBlockingQueue {

    private final Object[] buffer;
    private final int capacity;
    private int head;
    private int tail;

    private final Semaphore canProduce;
    private final Semaphore canConsume;
    private final Semaphore mutex;

    public SemaphoreBlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.buffer = new Object[capacity];
        this.canProduce = new Semaphore(capacity); // starts with all slots free
        this.canConsume = new Semaphore(0);         // starts with no items to consume
        this.mutex      = new Semaphore(1);         // binary semaphore = mutex
    }

    /**
     * Blocks until a slot is free, then inserts the value.
     */
    public void put(Object value) throws InterruptedException {
        canProduce.acquire(); // decrements free-slot count; blocks at 0
        mutex.acquire();
        buffer[tail] = value;
        tail = (tail + 1) % capacity;
        mutex.release();
        canConsume.release(); // increments item count; unblocks a waiting consumer
    }

    /**
     * Blocks until an item is available, then removes and returns it.
     */
    public Object take() throws InterruptedException {
        canConsume.acquire(); // decrements item count; blocks at 0
        mutex.acquire();
        Object value = buffer[head];
        buffer[head] = null;
        head = (head + 1) % capacity;
        mutex.release();
        canProduce.release(); // increments free-slot count; unblocks a waiting producer
        return value;
    }

    public int availableSlots()  { return canProduce.availablePermits(); }
    public int availableItems()  { return canConsume.availablePermits(); }
    public boolean isEmpty()     { return canConsume.availablePermits() == 0; }
    public boolean isFull()      { return canProduce.availablePermits() == 0; }
}

// ─────────────────────────────────────────────────────────────────────────────
// Main: functional tests for both variants
// ─────────────────────────────────────────────────────────────────────────────

public class ProducerConsumerQueue {

    private static int passed = 0;
    private static int failed = 0;

    private static void check(String name, boolean condition) {
        if (condition) {
            System.out.println("PASS " + name);
            passed++;
        } else {
            System.out.println("FAIL " + name);
            failed++;
        }
    }

    // ── Variant 1 single-threaded sanity ─────────────────────────────────────

    private static void testSingleThreaded() throws InterruptedException {
        System.out.println("\n── Variant 1: single-threaded sanity ──");

        BlockingBoundedQueue q = new BlockingBoundedQueue(3);

        check("initially empty", q.isEmpty());
        check("offer 1", q.offer(1));
        check("offer 2", q.offer(2));
        check("offer 3", q.offer(3));
        check("full after 3 inserts", q.isFull());
        check("offer on full returns false", !q.offer(4));

        check("poll returns 1", Integer.valueOf(1).equals(q.poll()));
        check("not full after poll", !q.isFull());

        q.put(4);
        check("size 3 after put", q.size() == 3);
        check("take returns 2", Integer.valueOf(2).equals(q.take()));
        check("take returns 3", Integer.valueOf(3).equals(q.take()));
        check("take returns 4", Integer.valueOf(4).equals(q.take()));
        check("empty after all takes", q.isEmpty());
    }

    // ── Variant 1 multi-threaded: one producer, one consumer ─────────────────

    private static void testMultiThreaded() throws InterruptedException {
        System.out.println("\n── Variant 1: multi-threaded producer-consumer ──");

        final int ITEMS = 20;
        BlockingBoundedQueue q = new BlockingBoundedQueue(4);
        int[] received = new int[ITEMS];

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < ITEMS; i++) {
                    q.put(i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < ITEMS; i++) {
                    received[i] = (Integer) q.take();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer");

        producer.start();
        consumer.start();
        producer.join(3000);
        consumer.join(3000);

        check("producer finished", !producer.isAlive());
        check("consumer finished", !consumer.isAlive());

        boolean ordered = true;
        for (int i = 0; i < ITEMS; i++) {
            if (received[i] != i) { ordered = false; break; }
        }
        check("items received in order", ordered);
        check("queue empty after run", q.isEmpty());
    }

    // ── Variant 1 multi-threaded: multiple producers and consumers ────────────

    private static void testMultipleProducersConsumers() throws InterruptedException {
        System.out.println("\n── Variant 1: multiple producers and consumers ──");

        final int PRODUCERS = 3;
        final int CONSUMERS = 2;
        final int ITEMS_PER_PRODUCER = 10;
        final int TOTAL = PRODUCERS * ITEMS_PER_PRODUCER;

        BlockingBoundedQueue q = new BlockingBoundedQueue(5);
        int[] counter = {0};

        Thread[] producers = new Thread[PRODUCERS];
        Thread[] consumers = new Thread[CONSUMERS];

        for (int p = 0; p < PRODUCERS; p++) {
            final int base = p * ITEMS_PER_PRODUCER;
            producers[p] = new Thread(() -> {
                try {
                    for (int i = 0; i < ITEMS_PER_PRODUCER; i++) {
                        q.put(base + i);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Producer-" + p);
        }

        for (int c = 0; c < CONSUMERS; c++) {
            final int share = TOTAL / CONSUMERS;
            consumers[c] = new Thread(() -> {
                try {
                    for (int i = 0; i < share; i++) {
                        q.take();
                        synchronized (counter) { counter[0]++; }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Consumer-" + c);
        }

        for (Thread t : producers) t.start();
        for (Thread t : consumers) t.start();
        for (Thread t : producers) t.join(3000);
        for (Thread t : consumers) t.join(3000);

        check("all items consumed", counter[0] == TOTAL);
        check("queue empty after run", q.isEmpty());
    }

    // ── Variant 2: ArrayBlockingQueue wrapper ─────────────────────────────────

    private static void testLibraryVariant() throws InterruptedException {
        System.out.println("\n── Variant 2: ArrayBlockingQueue wrapper ──");

        LibraryBlockingQueue q = new LibraryBlockingQueue(3);

        check("offer 10", q.offer(10));
        check("offer 20", q.offer(20));
        check("offer 30", q.offer(30));
        check("offer on full returns false", !q.offer(40));
        check("size 3", q.size() == 3);

        check("take returns 10", q.take() == 10);
        q.put(40);
        check("take returns 20", q.take() == 20);
        check("take returns 30", q.take() == 30);
        check("take returns 40", q.take() == 40);
        check("empty after all takes", q.size() == 0);
    }

    // ── Variant 3: ReentrantLock + two Conditions ──────────────────────────────

    private static void testReentrantLockVariant() throws InterruptedException {
        System.out.println("\n── Variant 3: ReentrantLock + two Conditions (single-threaded) ──");

        ReentrantLockBlockingQueue q = new ReentrantLockBlockingQueue(3);

        check("initially empty", q.isEmpty());
        check("offer 1", q.offer(1));
        check("offer 2", q.offer(2));
        check("offer 3", q.offer(3));
        check("full after 3 inserts", q.isFull());
        check("offer on full returns false", !q.offer(4));
        check("poll returns 1", Integer.valueOf(1).equals(q.poll()));
        q.put(4);
        check("take returns 2", Integer.valueOf(2).equals(q.take()));
        check("take returns 3", Integer.valueOf(3).equals(q.take()));
        check("take returns 4", Integer.valueOf(4).equals(q.take()));
        check("empty after all takes", q.isEmpty());

        System.out.println("\n── Variant 3: ReentrantLock + two Conditions (multi-threaded) ──");

        final int ITEMS = 20;
        ReentrantLockBlockingQueue rq = new ReentrantLockBlockingQueue(4);
        int[] received = new int[ITEMS];

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < ITEMS; i++) rq.put(i);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "RLProducer");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < ITEMS; i++) received[i] = (Integer) rq.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "RLConsumer");

        producer.start();
        consumer.start();
        producer.join(3000);
        consumer.join(3000);

        check("producer finished", !producer.isAlive());
        check("consumer finished", !consumer.isAlive());

        boolean ordered = true;
        for (int i = 0; i < ITEMS; i++) {
            if (received[i] != i) { ordered = false; break; }
        }
        check("items received in order", ordered);
        check("queue empty after run", rq.isEmpty());
    }

    // ── Variant 4: Semaphore-based queue ─────────────────────────────────────

    private static void testSemaphoreVariant() throws InterruptedException {
        System.out.println("\n── Variant 4: Semaphore-based queue (single-threaded) ──");

        SemaphoreBlockingQueue q = new SemaphoreBlockingQueue(3);

        check("initially empty", q.isEmpty());
        q.put(10);
        q.put(20);
        q.put(30);
        check("full after 3 puts", q.isFull());
        check("take returns 10", Integer.valueOf(10).equals(q.take()));
        check("not full after take", !q.isFull());
        q.put(40);
        check("take returns 20", Integer.valueOf(20).equals(q.take()));
        check("take returns 30", Integer.valueOf(30).equals(q.take()));
        check("take returns 40", Integer.valueOf(40).equals(q.take()));
        check("empty after all takes", q.isEmpty());

        System.out.println("\n── Variant 4: Semaphore-based queue (multi-threaded) ──");

        final int ITEMS = 20;
        SemaphoreBlockingQueue sq = new SemaphoreBlockingQueue(4);
        int[] received = new int[ITEMS];

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < ITEMS; i++) sq.put(i);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "SemProducer");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < ITEMS; i++) received[i] = (Integer) sq.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "SemConsumer");

        producer.start();
        consumer.start();
        producer.join(3000);
        consumer.join(3000);

        check("producer finished", !producer.isAlive());
        check("consumer finished", !consumer.isAlive());

        boolean ordered = true;
        for (int i = 0; i < ITEMS; i++) {
            if (received[i] != i) { ordered = false; break; }
        }
        check("items received in order", ordered);
        check("queue empty after run", sq.isEmpty());
    }

    public static void main(String[] args) throws InterruptedException {
        testSingleThreaded();
        testMultiThreaded();
        testMultipleProducersConsumers();
        testLibraryVariant();
        testReentrantLockVariant();
        testSemaphoreVariant();

        System.out.println("\n── Summary ──");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
    }
}
