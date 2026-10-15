package InterviewPrep.IBM.Problems;

import java.util.ArrayList;
import java.util.Arrays;
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

    public static void main(String[] args) {
        DocumentSearch search = new DocumentSearch(List.of(
                List.of("New", "York", "is", "big", "New", "York"),
                List.of("I", "love", "new", "york", "city"),
                List.of("new", "jersey", "york"),
                List.of("new", "jersey", "new", "york")
        ));

        check("searchWord(new)", search.searchWord("new"), List.of(0, 1, 2, 3));
        check("searchWord(NEW) case-insensitive", search.searchWord("NEW"), List.of(0, 1, 2, 3));
        check("searchWord(banana) missing", search.searchWord("banana"), List.of());
        check("searchPhrase(New York)", search.searchPhrase(List.of("New", "York")), List.of(0, 1, 3));
        check("searchPhrase(new jersey york)", search.searchPhrase(List.of("new", "jersey", "york")), List.of(2));
        check("searchPhrase(york new) reversed", search.searchPhrase(List.of("york", "new")), List.of());
        check("searchPhrase(jersey) single word", search.searchPhrase(List.of("jersey")), List.of(2, 3));
        check("searchPhrase(new banana) missing word", search.searchPhrase(List.of("new", "banana")), List.of());
        check("searchPhrase(empty)", search.searchPhrase(List.of()), List.of());

        String sentence = "Java is fun and Java is powerful";
        DocumentSearch sentenceSearch = new DocumentSearch(List.of(Arrays.asList(sentence.split(" "))));
        String phrase = "java is";
        check("searchPhrase(sentence split into list)", sentenceSearch.searchPhrase(Arrays.asList(phrase.split(" "))), List.of(0));
    }

    private static void check(String name, List<Integer> actual, List<Integer> expected) {
        boolean passed = actual.equals(expected);
        System.out.println((passed ? "PASS " : "FAIL ") + name + " -> " + actual + (passed ? "" : " expected " + expected));
    }
}