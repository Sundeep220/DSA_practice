## **1. Linear Data Structures**

**Array**
Fixed-size, contiguous memory storage.
**Java:** `int[]`, `String[]`

**ArrayList (Dynamic Array)**
Resizable array for sequential access.
**Java:** `ArrayList<E>`

**Linked List**
Dynamic size, nodes connected via pointers.
**Java:** `LinkedList<E>` (Singly/Doubly/Circular)

**Stack (LIFO)**
Insert/remove from the top.
**Java:** `Stack<E>` or `Deque<E>`

**Queue (FIFO)**
Insert at rear, remove from front.
**Java:** `Queue<E>` (`LinkedList`, `ArrayDeque`)

**Deque (Double-Ended Queue)**
Insert/remove from both ends.
**Java:** `ArrayDeque<E>`, `LinkedList<E>`

---

### **2. Hash-Based Structures**

**HashMap**
Key-value pairs, fast lookup (O(1) avg).
**Java:** `HashMap<K,V>`

**HashSet**
Stores unique elements using hashing.
**Java:** `HashSet<E>`

**LinkedHashMap / LinkedHashSet**
Preserves insertion order.
**Java:** `LinkedHashMap<K,V>`, `LinkedHashSet<E>`

**Hashtable**
Legacy synchronized hash table.
**Java:** `Hashtable<K,V>`

---

### **3. Heaps (Priority Queues)**

**Min Heap**
Retrieves smallest element first.
**Java:** `PriorityQueue<E>`

**Max Heap**
Retrieves largest element first.
**Java:**

```java
PriorityQueue<E> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
```

**Custom Heap**
Order defined by comparator.
**Java:** `PriorityQueue<E>(Comparator)`

---

### **4. Tree Data Structures**

**Binary Tree**
Each node has up to two children.
**Java:** Custom `TreeNode` class

**Binary Search Tree (BST)**
Ordered binary tree for fast search.
**Java:** Manual or use `TreeMap` / `TreeSet`

**Trie (Prefix Tree)**
Stores strings for prefix search.
**Java:** Custom `Map<Character,Node>` implementation

**Segment Tree / Fenwick Tree (BIT)**
Range queries and updates.
**Java:** Manual implementation

---

### **5. Graph Data Structures**

**Adjacency List**
Vertex → List of neighbors.
**Java:** `Map<Integer, List<Integer>>`

**Adjacency Matrix**
2D matrix representation of edges.
**Java:** `int[][]` or `boolean[][]`

**Edge List**
Stores all edges as pairs.
**Java:** `List<int[]>`

**Weighted Graph**
Vertex → List of (neighbor, weight).
**Java:** `Map<Integer, List<Pair<Integer,Integer>>>`

---

### **6. Disjoint Set (Union-Find / DSU)**

Manages connected components, supports union and find.
**Java:** Manual implementation with arrays + path compression.

---

### **7. Specialized Structures**

**LRU Cache**
Stores items with eviction of least recently used.
**Java:** `LinkedHashMap`

**Skip List**
Multi-level linked list for fast search.
**Java:** `ConcurrentSkipListMap`, `ConcurrentSkipListSet`

**Bloom Filter**
Probabilistic membership testing.
**Java:** `BitSet`

**Multiset / Bag**
Counts occurrences of elements.
**Java:** `HashMap<E, Integer>`


