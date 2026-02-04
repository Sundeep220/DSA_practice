# 🌳 Trie Data Structure (Beginner → Pro Level)

![Image](https://www.boardinfinity.com/blog/content/images/2023/02/Trie-1.png)

![Image](https://media2.dev.to/dynamic/image/width%3D1000%2Cheight%3D500%2Cfit%3Dcover%2Cgravity%3Dauto%2Cformat%3Dauto/https%3A%2F%2Fdev-to-uploads.s3.amazonaws.com%2Fuploads%2Farticles%2Fbd16ijx307a358tvcz4c.png)

![Image](https://static.takeuforward.org/content/-MA5Rrp7w)

![Image](https://carshen.github.io/assets/trie1.png)

---

## 1️⃣ What is a Trie? (In Very Simple Words)

A **Trie** (pronounced *try*) is a **tree-like data structure** used to **store strings efficiently**, especially when:

* You care about **prefixes**
* You need **fast search**
* You work with **dictionary-like data**

👉 Think of a Trie as a **tree of characters**.

---

### 🧠 Real-Life Analogy

Imagine this word list:

```
apple
app
apply
bat
ball
```

Instead of storing these words separately, Trie stores **common prefixes only once**.

```
         root
          |
          a
          |
          p
          |
          p
        /   \
      l       (end: "app")
      |
      e   y
   (apple) (apply)

         b
         |
         a
       /   \
      t     l
           |
           l
```

---

## 2️⃣ Why Do We Need Trie?

### ❌ Problem with Normal Storage (List / Set)

If you store words in a list:

* Searching = **O(n × word_length)**

If you store in HashSet:

* Prefix search ❌ (not supported)

---

### ✅ Trie Solves This

| Operation     | Time Complexity     |
| ------------- | ------------------- |
| Insert        | O(length of word)   |
| Search        | O(length of word)   |
| Prefix Search | O(length of prefix) |

🚀 **Time depends only on word length, not number of words**

---

## 3️⃣ Where is Trie Used in Real Life / Software?

### 🔥 Real-World Use Cases

#### 1. **Autocomplete / Search Suggestions**

* Google search
* IDE auto-completion
* Phone keyboard suggestions

#### 2. **Spell Checkers**

* Dictionary matching
* Finding closest valid words

#### 3. **Prefix Matching APIs**

* URL routing
* Command matching (`git ch`, `git checkout`)

#### 4. **Search Engines**

* Fast word lookup
* Indexing text

#### 5. **IP Routing**

* Longest prefix matching (networking)

---

## 4️⃣ Core Idea of Trie (Very Important)

> **Each node represents ONE character**
> **A path from root to a node represents a prefix**

---

## 5️⃣ Trie Node Structure

Each Trie node contains:

1. **Children** → links to next characters
2. **End flag** → tells if a word ends here

### Example for lowercase English letters:

```java
class TrieNode {
    TrieNode[] children = new TrieNode[26];
    boolean isEnd;
}
```

---

## 6️⃣ Visualizing a Trie Node

![Image](https://nullprogram.com/img/trie/trie.svg)

![Image](https://www.francofernando.com/assets/img/blog/data_structures/trie-example.png)

* Index `0` → `'a'`
* Index `1` → `'b'`
* …
* Index `25` → `'z'`

---

## 7️⃣ Trie Class Design

```java
class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }
}
```

📌 Root does **NOT** store a character.

---

## 8️⃣ Insert Operation (Step-by-Step)

### Insert `"apple"`

1. Start at root
2. For each character:

    * If child does NOT exist → create it
    * Move to child
3. After last character → mark `isEnd = true`

---

### 🧠 Dry Run: insert("apple")

```
root
 |
 a
 |
 p
 |
 p
 |
 l
 |
 e (isEnd = true)
```

---

### ✅ Java Code: Insert

```java
public void insert(String word) {
    TrieNode node = root;

    for (char ch : word.toCharArray()) {
        int index = ch - 'a';

        if (node.children[index] == null) {
            node.children[index] = new TrieNode();
        }

        node = node.children[index];
    }

    node.isEnd = true;
}
```

---

## 9️⃣ Search Operation

### What does `search(word)` mean?

👉 Return **true only if the full word exists**

---

### Steps

1. Start from root
2. For each character:

    * If missing → return false
3. After traversal:

    * Check `isEnd == true`

---

### ❌ Why `"app"` fails before inserting `"app"`?

Because:

* `"app"` is a **prefix**
* End flag is `false`

---

### ✅ Java Code: Search

```java
public boolean search(String word) {
    TrieNode node = root;

    for (char ch : word.toCharArray()) {
        int index = ch - 'a';

        if (node.children[index] == null) {
            return false;
        }

        node = node.children[index];
    }

    return node.isEnd;
}
```

---

## 🔟 Prefix Search (`startsWith`)

### Meaning

👉 Check if **any word starts with given prefix**

---

### Steps

1. Traverse prefix characters
2. If path exists → return true
3. No need to check `isEnd`

---

### ✅ Java Code: startsWith

```java
public boolean startsWith(String prefix) {
    TrieNode node = root;

    for (char ch : prefix.toCharArray()) {
        int index = ch - 'a';

        if (node.children[index] == null) {
            return false;
        }

        node = node.children[index];
    }

    return true;
}
```

---

## 🔁 Full Working Code (End-to-End)

```java
class TrieNode {
    TrieNode[] children = new TrieNode[26];
    boolean isEnd;
}

public class Trie {

    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode node = root;

        for (char ch : word.toCharArray()) {
            int index = ch - 'a';

            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }

            node = node.children[index];
        }

        node.isEnd = true;
    }

    public boolean search(String word) {
        TrieNode node = root;

        for (char ch : word.toCharArray()) {
            int index = ch - 'a';

            if (node.children[index] == null) {
                return false;
            }

            node = node.children[index];
        }

        return node.isEnd;
    }

    public boolean startsWith(String prefix) {
        TrieNode node = root;

        for (char ch : prefix.toCharArray()) {
            int index = ch - 'a';

            if (node.children[index] == null) {
                return false;
            }

            node = node.children[index];
        }

        return true;
    }
}
```

---

## 🧪 Example Walkthrough (Your Example)

```java
Trie trie = new Trie();
trie.insert("apple");
trie.search("apple");    // true
trie.search("app");      // false
trie.startsWith("app");  // true
trie.insert("app");
trie.search("app");      // true
```

---

## ⚠️ Common Beginner Mistakes

❌ Forgetting `isEnd`
❌ Thinking prefix = word
❌ Using HashMap before understanding array approach
❌ Expecting alphabetical traversal automatically

---

## 🧠 Mental Model (Remember This)

> **Trie = Prefix Tree**
> **Path = Word**
> **Node = Character**
> **isEnd = Valid Word**

---

