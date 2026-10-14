# 08. LRU Cache

**Tier:** 4 (Design problems)
**Confluent tag:** Yes
**Link:** https://leetcode.com/problems/lru-cache/

## Problem

Design a fixed-capacity LRU cache. `get(key)` returns the value or `-1` if the key is absent. `put(key, value)` inserts or updates a key. If the cache is full, evict the least recently used key before inserting.

## Intuition

Two structures are needed together:

- **HashMap** (`key -> node`): O(1) lookup of any key.
- **Doubly linked list** ordered by recency: the head is most recently used, the tail is least recently used. Given a node reference, removing and reinserting it is O(1).

Every `get` or `put` moves the touched node to the head. Eviction removes the node before the tail sentinel.

Sentinel head and tail nodes remove null checks at the list boundaries.

## Approach

1. `get`: look up the node. If absent, return `-1`. Otherwise move it to the front and return its value.
2. `put` on an existing key: update the value and move the node to the front.
3. `put` on a new key: create a node, add it to the front and to the map. If the map size exceeds capacity, remove the tail's previous node from both the list and the map.

## Complexity

- **`get`:** O(1).
- **`put`:** O(1).
- **Space:** O(capacity) for the map and list.

## Edge cases

- Capacity 1: every new key evicts the only existing key.
- `put` on an existing key: updates the value and does not evict.
- `get` on an evicted key: returns `-1`.
- Interleaved `get` and `put`: each `get` refreshes recency, which changes the eviction order.

## Code

```java
import java.util.HashMap;
import java.util.Map;

public class LRUCache {

    private static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<Integer, Node> keyToNode = new HashMap<>();

    private final Node dummyHead = new Node(0, 0);
    private final Node dummyTail = new Node(0, 0);

    public LRUCache(int capacity) {
        this.capacity = capacity;
        dummyHead.next = dummyTail;
        dummyTail.prev = dummyHead;
    }

    public int get(int key) {
        Node node = keyToNode.get(key);
        if (node == null) {
            return -1;
        }
        moveToFront(node);
        return node.value;
    }

    public void put(int key, int value) {
        Node existingNode = keyToNode.get(key);

        if (existingNode != null) {
            existingNode.value = value;
            moveToFront(existingNode);
            return;
        }

        Node newNode = new Node(key, value);
        keyToNode.put(key, newNode);
        addToFront(newNode);

        if (keyToNode.size() > capacity) {
            Node lruNode = dummyTail.prev;
            removeNode(lruNode);
            keyToNode.remove(lruNode.key);
        }
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void addToFront(Node node) {
        node.next = dummyHead.next;
        node.prev = dummyHead;
        dummyHead.next.prev = node;
        dummyHead.next = node;
    }

    private void moveToFront(Node node) {
        removeNode(node);
        addToFront(node);
    }
}
```

## Function explanations

Runnable file: `LRUCache.java` (includes a `main` demo).

### `Node`

- **Stores `key` as well as `value`:** when the tail is evicted, its key is needed to remove the map entry. Without it, the map would have no way to find the evicted node.
- **`prev` and `next` pointers:** a doubly linked list lets a node be unlinked in O(1) given only the node itself.

### Constructor

- **Sentinels `dummyHead` and `dummyTail`:** they always exist, so inserting and removing at the ends never needs a null check.
- **Links the sentinels:** an empty list is `dummyHead <-> dummyTail`.

### `get`

- **Map lookup first:** returns `-1` in O(1) when the key is absent.
- **`moveToFront`:** accessing a key makes it most recently used, so it moves away from the eviction end.

### `put`

- **Existing key branch:** updates the value and refreshes recency. The size does not change, so no eviction is needed and the method returns early.
- **New key:** the node goes into the map and to the front of the list.
- **Size check after insert:** the cache is over capacity by at most one entry, so one eviction is enough.
- **Evicts `dummyTail.prev`:** the least recently used node is always just before the tail sentinel.

### `removeNode`

- **Relinks neighbours:** unlinks the node by pointing its neighbours at each other. The node's own pointers are left alone, because the caller either discards it or reinserts it.

### `addToFront`

- **Sets the node's pointers before the neighbours:** `dummyHead.next` is overwritten on the last line, so the old first node must be read before that. Reversing these lines would lose the rest of the list.

### `moveToFront`

- **Remove then add:** the simplest correct way to move a node, since both operations are O(1).

## Interview one-liner

"A HashMap gives O(1) lookup, and a doubly linked list ordered by recency gives O(1) move and evict. Every access moves the node to the front, and the tail is always the eviction candidate."
