# 10. Word and Phrase Search in Documents

**Tier:** 5 (Confluent scenario problems)
**Confluent tag:** Yes (reported in multiple rounds)
**Link:** No LeetCode equivalent.

## Problem

Given a list of documents, where each document is a list of words, support two operations:

- `searchWord(word)`: return the indices of all documents that contain the word.
- `searchPhrase(phrase)`: return the indices of all documents that contain the phrase's words consecutively and in order.

## Intuition

**Word search** needs a map from each word to the documents containing it. That is an inverted index.

**Phrase search** needs more than document membership. Knowing that a document contains `"new"` and `"york"` does not show they are adjacent. So the index also stores each word's positions inside each document. A phrase matches at a start position `p` if word `i` of the phrase appears at position `p + i` in the same document.

Positions are added in increasing order during construction, so each position list is already sorted and `Collections.binarySearch` can check membership in O(log p), where p is the number of positions of that word in that document.

## Approach

1. Build `Map<String, TreeMap<Integer, List<Integer>>>`: word, then document index, then sorted positions.
2. Lowercase words during indexing and lookup.
3. `searchWord`: return the document keys for the word, or an empty list if it is unknown.
4. `searchPhrase`:
   - Return an empty list for an empty phrase.
   - Take the postings for the first phrase word. If none, return an empty list.
   - For each candidate document, try each start position of the first word. Check that every later word appears at `start + offset` in that document.
   - Add the document index on the first full match.

## Complexity

**Notation**

- `W`: total number of words across all documents.
- `D`: number of documents.
- `V`: number of distinct words.
- `d_w`: number of documents containing word `w`.
- `p`: number of occurrences of a word in one document.
- `f`: number of occurrences of the first phrase word.
- `L`: phrase length.

**Build: O(W log D)**

- Per word occurrence:
  - `toLowerCase()` and `HashMap.computeIfAbsent` on the word: O(k) to hash a word of length k, treated as O(1) on average.
  - `TreeMap.computeIfAbsent(docId)`: O(log d_w), which is at most O(log D).
  - `ArrayList.add(position)`: O(1) amortized.
- Positions and documents are visited in increasing order, so no sorting is needed.

**`searchWord`: O(1) + O(d_w)**

- `HashMap.get`: O(1) on average.
- `new ArrayList<>(postings.keySet())`: O(d_w) to copy the document keys.

**`searchPhrase`: O(L + f · L · (log D + log p))**

1. Lowercase the phrase: O(L).
2. `invertedIndex.get(phrase.get(0))`: O(1) on average.
3. Outer loop: visits each document containing the first word once. Over all documents, the inner loop tries each occurrence of the first word once, so there are f starts in total.
4. Each start calls `matchesAt`, which runs up to L - 1 calls to `hasWordAt` and stops at the first mismatch.
5. Each `hasWordAt` call costs:
   - `HashMap.get(word)`: O(1) on average.
   - `TreeMap.get(docId)`: O(log d_w), which is at most O(log D).
   - `Collections.binarySearch(positions)`: O(log p), where p is that word's positions in that document.
6. Worst case: f · (L - 1) checks, each O(log D + log p), so O(f · L · (log D + log p)).
7. The `break` after a match skips the remaining starts in that document. It saves time but does not change the worst case.
8. Result size is at most D, and each `add` is O(1) amortized.

Since D and p are both at most W, the worst case is O(f · L · log W). The binary search runs over the other word's positions in one document, so the bound uses `p`, not `f`.

**Space: O(W)**

- Each occurrence is stored once as an `Integer` in a position list.
- The map has O(V) keys and at most O(W) `TreeMap` entries, one per (word, document) pair.
- The original `documents` list is also O(W).

| Operation | Time | Dominant cost |
|---|---|---|
| Build | O(W log D) | `TreeMap` insert per occurrence |
| `searchWord` | O(1) + O(d_w) | Copying document keys |
| `searchPhrase` | O(L + f · L · (log D + log p)) | Binary search per start per phrase word |
| Space | O(W) | Position lists |

## Edge cases

- Empty phrase: returns an empty list.
- Phrase with one word: behaves like `searchWord`.
- Phrase longer than a document: no match.
- Word appearing several times in one document: every position is indexed, so a phrase can match at any occurrence.
- Phrase that spans two documents: no match, because positions are stored per document.
- Different capitalisation: matched, because both sides are lowercased.
- Very large document sets: shard the index by document range or word hash.

## Dry run

### Input

```java
List<List<String>> documents = List.of(
    List.of("New", "York", "is", "big", "New", "York"),   // docId 0
    List.of("I", "love", "new", "york", "city"),          // docId 1
    List.of("new", "jersey", "york"),                     // docId 2
    List.of("new", "jersey", "new", "york")               // docId 3
);
```

### Step 1: Build the index in the constructor

For each word, the constructor runs:

- `invertedIndex.computeIfAbsent(word, ...)`: creates the word's `TreeMap` if it is missing.
- `.computeIfAbsent(docId, ...)`: creates the document's `ArrayList` if it is missing.
- `.add(position)`: appends the position, so each list stays sorted.

Trace for docId 0 (`New York is big New York`):

| position | word (lowercased) | update |
|---|---|---|
| 0 | `new` | `new` -> `{0:[0]}` |
| 1 | `york` | `york` -> `{0:[1]}` |
| 2 | `is` | `is` -> `{0:[2]}` |
| 3 | `big` | `big` -> `{0:[3]}` |
| 4 | `new` | `new` -> `{0:[0,4]}` |
| 5 | `york` | `york` -> `{0:[1,5]}` |

Docs 1 to 3 are processed the same way. The final index is:

| word | postings (docId -> positions) |
|---|---|
| `new` | `{0:[0,4], 1:[2], 2:[0], 3:[0,2]}` |
| `york` | `{0:[1,5], 1:[3], 2:[2], 3:[3]}` |
| `is` | `{0:[2]}` |
| `big` | `{0:[3]}` |
| `i` | `{1:[0]}` |
| `love` | `{1:[1]}` |
| `city` | `{1:[4]}` |
| `jersey` | `{2:[1], 3:[1]}` |

### Step 2: `searchPhrase(["New", "York"])`

1. The phrase is not empty. Lowercasing gives `phrase = ["new", "york"]`.
2. `firstWordPostings = invertedIndex.get("new")` is `{0:[0,4], 1:[2], 2:[0], 3:[0,2]}`. It is not null, so `results = []`.
3. The outer loop visits each document in `firstWordPostings` in ascending key order.

**Iteration A: docId 0, positions `[0, 4]`**

- `start = 0`: call `matchesAt(0, 0, phrase)`.
  - `offset = 1`: call `hasWordAt(0, 1, "york")`.
    - `postings = {0:[1,5], ...}`, not null.
    - `positions = [1, 5]`, not null.
    - `binarySearch([1, 5], 1)`: the middle index is 0, the value is 1, so it returns `0`. This is `>= 0`, so it returns `true`.
  - `offset` is now 2, which is not less than `phrase.size()`, so the loop ends and `matchesAt` returns `true`.
- Add 0 to `results`: `[0]`. `break` exits the position loop.
- `start = 4` is never checked.

**Iteration B: docId 1, positions `[2]`**

- `start = 2`: `matchesAt(1, 2, phrase)`.
  - `hasWordAt(1, 3, "york")`: `positions = [3]`, `binarySearch([3], 3)` returns `0`, so `true`.
  - `matchesAt` returns `true`.
- Add 1: `results = [0, 1]`. `break`.

**Iteration C: docId 2, positions `[0]`**

- `start = 0`: `matchesAt(2, 0, phrase)`.
  - `hasWordAt(2, 1, "york")`: `positions = [2]`. `binarySearch([2], 1)` checks index 0, sees 2 > 1, and returns `-1`. This is negative, so it returns `false`.
  - `matchesAt` returns `false`.
- No more starts, so docId 2 is not added.

**Iteration D: docId 3, positions `[0, 2]`**

- `start = 0`: `matchesAt(3, 0, phrase)`.
  - `hasWordAt(3, 1, "york")`: `positions = [3]`. `binarySearch([3], 1)` returns `-1`, so `false`.
  - `matchesAt` returns `false`.
- `start = 2`: `matchesAt(3, 2, phrase)`.
  - `hasWordAt(3, 3, "york")`: `positions = [3]`, `binarySearch` returns `0`, so `true`.
  - `matchesAt` returns `true`.
- Add 3: `results = [0, 1, 3]`. `break`.

**Return value: `[0, 1, 3]`**

### Step 3: `searchPhrase(["new", "jersey", "york"])`

Phrase length is 3, so `matchesAt` checks offsets 1 and 2. `firstWordPostings` is the same as in step 2.

| docId | start | `hasWordAt` calls | Result |
|---|---|---|---|
| 0 | 0 | `hasWordAt(0, 1, "jersey")`: `jersey` has no entry for doc 0, so `postings.get(0)` is null | `false` |
| 0 | 4 | `hasWordAt(0, 5, "jersey")`: same null lookup | `false` |
| 1 | 2 | `hasWordAt(1, 3, "jersey")`: no entry for doc 1 | `false` |
| 2 | 0 | `hasWordAt(2, 1, "jersey")`: `[1]` contains 1, so `true`. Then `hasWordAt(2, 2, "york")`: `[2]` contains 2, so `true` | `true`, doc 2 added |
| 3 | 0 | `hasWordAt(3, 1, "jersey")`: `[1]` contains 1, so `true`. Then `hasWordAt(3, 2, "york")`: `[3]` does not contain 2, so `false` | `false` |
| 3 | 2 | `hasWordAt(3, 3, "jersey")`: `[1]` does not contain 3, so `false`. Offset 2 is not checked | `false` |

**Return value: `[2]`**

### Key points

- `matchesAt` checks one word per offset and stops at the first failure.
- `hasWordAt` returns `false` if the word is not indexed, if the document has no entry for it, or if the position is not in the sorted list.
- `searchPhrase` `break`s after the first matching start in each document, so later starts in that document are skipped.

## Code

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DocumentSearch {

    private final List<List<String>> documents;
    private final Map<String, TreeMap<Integer, List<Integer>>> invertedIndex = new HashMap<>();

    public DocumentSearch(List<List<String>> documents) {
        this.documents = documents;

        for (int docId = 0; docId < documents.size(); docId++) {
            List<String> document = documents.get(docId);
            for (int position = 0; position < document.size(); position++) {
                String word = document.get(position).toLowerCase();
                invertedIndex
                    .computeIfAbsent(word, w -> new TreeMap<>())
                    .computeIfAbsent(docId, d -> new ArrayList<>())
                    .add(position);
            }
        }
    }

    public List<Integer> searchWord(String word) {
        TreeMap<Integer, List<Integer>> postings = invertedIndex.get(word.toLowerCase());
        return postings == null ? List.of() : new ArrayList<>(postings.keySet());
    }

    public List<Integer> searchPhrase(List<String> phraseWords) {
        if (phraseWords.isEmpty()) {
            return List.of();
        }

        List<String> phrase = phraseWords.stream()
            .map(String::toLowerCase)
            .toList();

        TreeMap<Integer, List<Integer>> firstWordPostings = invertedIndex.get(phrase.get(0));
        if (firstWordPostings == null) {
            return List.of();
        }

        List<Integer> results = new ArrayList<>();

        for (Map.Entry<Integer, List<Integer>> doc : firstWordPostings.entrySet()) {
            int docId = doc.getKey();

            for (int start : doc.getValue()) {
                if (matchesAt(docId, start, phrase)) {
                    results.add(docId);
                    break;
                }
            }
        }

        return results;
    }

    private boolean matchesAt(int docId, int start, List<String> phrase) {
        for (int offset = 1; offset < phrase.size(); offset++) {
            if (!hasWordAt(docId, start + offset, phrase.get(offset))) {
                return false;
            }
        }
        return true;
    }

    private boolean hasWordAt(int docId, int position, String word) {
        TreeMap<Integer, List<Integer>> postings = invertedIndex.get(word);
        if (postings == null) {
            return false;
        }

        List<Integer> positions = postings.get(docId);
        return positions != null && Collections.binarySearch(positions, position) >= 0;
    }
}
```

## Interview one-liner

"The index stores each word's positions per document. For a phrase, I take candidate documents from the first word and check that each later word sits at the next position. That's O(f · L · log W) in the worst case, where f is the first word's occurrence count and each binary search runs over one document's positions."
