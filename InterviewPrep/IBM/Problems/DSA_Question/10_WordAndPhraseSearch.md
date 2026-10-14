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

Positions are added in increasing order during construction, so each position list is already sorted and `Collections.binarySearch` can check membership in O(log f).

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

- **Build:** O(W), where W is the total number of words across all documents.
- **`searchWord`:** O(1) to find the postings, plus O(d) to copy d matching document indices.
- **`searchPhrase`:** O(f · L · log f), where f is the number of occurrences of the first word and L is the phrase length.
- **Space:** O(W) for the index.

## Edge cases

- Empty phrase: returns an empty list.
- Phrase with one word: behaves like `searchWord`.
- Phrase longer than a document: no match.
- Word appearing several times in one document: every position is indexed, so a phrase can match at any occurrence.
- Phrase that spans two documents: no match, because positions are stored per document.
- Different capitalisation: matched, because both sides are lowercased.
- Very large document sets: shard the index by document range or word hash.

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

"The index stores each word's positions per document. For a phrase, I take candidate documents from the first word and check that each later word sits at the next position. That's O(f · L · log f), where f is the first word's occurrence count."
