package Problems.Graphs.SpanningTreeAndDisjointSetProbs.Hard;

import java.util.*;

public class AccountsMerge {
    // Problem: https://leetcode.com/problems/accounts-merge/description/

    // Intuition:
    // Two accounts belong to the same person if and only if:
    //they share at least one common email
    //So:
        //Emails are the true identifiers
        //Names are not unique
    //If two accounts share an email → they must be merged
    //👉 This becomes a connected components problem, where:
        //emails = nodes
        //accounts = connections (edges)

    // Algorithm:
    //High-Level Algorithm
    //Create DSU for emails
    //Map each email → unique integer id
    //Union all emails within the same account
    //Group emails by DSU root
    //Sort emails in each group
    //Attach the corresponding name
    
    // Time
        //DSU operations: O(N α(N))
        //Sorting emails: O(N log N)
    //✅ Overall: O(N log N)
    //Space
        //DSU arrays
        //HashMaps
    //✅ Overall: O(N)
    class DisjointSet {
        int[] parent, rank;

        DisjointSet(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        int find(int x) {
            if (parent[x] != x)
                parent[x] = find(parent[x]);
            return parent[x];
        }

        void union(int x, int y) {
            int px = find(x);
            int py = find(y);
            if (px == py) return;

            if (rank[px] < rank[py]) parent[px] = py;
            else if (rank[px] > rank[py]) parent[py] = px;
            else {
                parent[py] = px;
                rank[px]++;
            }
        }
    }
    public List<List<String>> accountsMerge(List<List<String>> accounts) {

        Map<String, Integer> emailToId = new HashMap<>();
        Map<String, String> emailToName = new HashMap<>();
        int id = 0;

        // Step 1: Map emails to ids
        for (List<String> acc : accounts) {
            String name = acc.get(0);
            for (int i = 1; i < acc.size(); i++) {
                String email = acc.get(i);
                if (!emailToId.containsKey(email)) {
                    emailToId.put(email, id++);
                    emailToName.put(email, name);
                }
            }
        }

        DisjointSet ds = new DisjointSet(id);

        // Step 2: Union emails within same account
        for (List<String> acc : accounts) {
            int firstEmailId = emailToId.get(acc.get(1));
            for (int i = 2; i < acc.size(); i++) {
                ds.union(firstEmailId, emailToId.get(acc.get(i)));
            }
        }

        // Step 3: Group emails by root
        Map<Integer, List<String>> components = new HashMap<>();
        for (String email : emailToId.keySet()) {
            int root = ds.find(emailToId.get(email));
            components.computeIfAbsent(root, k -> new ArrayList<>()).add(email);
        }

        // Step 4: Build result
        List<List<String>> result = new ArrayList<>();
        for (List<String> emails : components.values()) {
            Collections.sort(emails);
            List<String> account = new ArrayList<>();
            account.add(emailToName.get(emails.get(0)));
            account.addAll(emails);
            result.add(account);
        }

        return result;
    }


}
