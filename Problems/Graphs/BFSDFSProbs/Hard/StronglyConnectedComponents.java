package Problems.Graphs.BFSDFSProbs.Hard;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class StronglyConnectedComponents {
    // Problem: https://www.geeksforgeeks.org/strongly-connected-components/
    // Alogrithm: Kosaraju's Algorithm
    // Time Complexity: O(V + E)
    // Space Complexity: O(V + E)

    /*
     * ===========================
     * STEP 1: DFS ON ORIGINAL GRAPH
     * ===========================
     *
     * Goal:
     *  - Perform DFS and record vertices in a stack
     *    based on their FINISHING TIME.
     *
     * Key Intuition:
     *  - In a directed graph, SCCs when compressed form a DAG.
     *  - Nodes that finish LAST in DFS are the "sources" of this SCC-DAG.
     *  - Starting from such nodes ensures we do not mix SCCs later.
     *
     * Why push AFTER exploring all neighbors?
     *  - This guarantees that a node is pushed only when:
     *      → all paths starting from it are fully explored
     *  - Hence, stack top = highest finishing time.
     */
    private static void dfs1(int node, boolean[] visited, ArrayList<ArrayList<Integer>> adj, Stack<Integer> stack) {

        // Mark current node as visited
        // → ensures we don't revisit and loop infinitely
        visited[node] = true;

        // Explore all outgoing edges from this node
        for (int nei : adj.get(node)) {

            // DFS deeper only if neighbor is not visited
            if (!visited[nei]) {
                dfs1(nei, visited, adj, stack);
            }
        }

        /*
         * VERY IMPORTANT LINE
         *
         * We push the node ONLY AFTER all its neighbors are processed.
         *
         * Intuition:
         *  - This node has now finished completely
         *  - Its finishing time is later than all nodes reachable from it
         *  - Helps us process SCC "entry points" first in Step 3
         */
        stack.push(node);
    }

    /*
     * ===========================
     * STEP 3: DFS ON REVERSED GRAPH
     * ===========================
     *
     * Goal:
     *  - Traverse exactly ONE strongly connected component.
     *
     * Key Intuition:
     *  - Reversing the graph keeps SCCs intact
     *  - But breaks outgoing connections between SCCs
     *
     * Why does this DFS give exactly ONE SCC?
     *  - Because we start DFS from the node with the highest
     *    finishing time (from stack)
     *  - In reversed graph, there is no path to another SCC
     */
    private static void dfs2(int node, boolean[] visited, ArrayList<ArrayList<Integer>> revAdj, List<Integer> currentSCC) {

        // Mark node as visited
        // → this node belongs to the current SCC
        visited[node] = true;

        // Add node to current SCC
        currentSCC.add(node);

        // Traverse all reversed edges
        for (int nei : revAdj.get(node)) {

            // Continue DFS only for unvisited nodes
            if (!visited[nei]) {
                dfs2(nei, visited, revAdj, currentSCC);
            }
        }

        /*
         * No stack here.
         *
         * We only care about marking all nodes
         * reachable in this DFS as part of the same SCC.
         */
    }

    /*
     * ===========================
     * MAIN FUNCTION: KOSARAJU
     * ===========================
     *
     * This function coordinates the 3 steps:
     *  1) DFS + stack (finishing times)
     *  2) Reverse the graph
     *  3) DFS in stack order to count SCCs
     */
    public static int kosaraju(int V, ArrayList<ArrayList<Integer>> adj, List<List<Integer>> allSCCs) {

        /*
         * ---------- STEP 1 ----------
         * Perform DFS on original graph
         * and store nodes by finishing time.
         */
        boolean[] visited = new boolean[V];
        Stack<Integer> stack = new Stack<>();

        // Graph may be disconnected,
        // so we run DFS from every unvisited node
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                dfs1(i, visited, adj, stack);
            }
        }

        /*
         * ---------- STEP 2 ----------
         * Reverse the graph.
         *
         * Intuition:
         *  - SCC internal connectivity remains
         *  - Direction between SCCs is reversed
         *  - Prevents DFS from leaking into other SCCs
         */
        ArrayList<ArrayList<Integer>> revAdj = new ArrayList<>();

        // Initialize adjacency list for reversed graph
        for (int i = 0; i < V; i++) {
            revAdj.add(new ArrayList<>());
        }

        // Reverse every edge u → v into v → u
        for (int u = 0; u < V; u++) {
            for (int v : adj.get(u)) {
                revAdj.get(v).add(u);
            }
        }

        /*
         * ---------- STEP 3 ----------
         * DFS on reversed graph in stack order.
         *
         * Key Rule:
         *  - Each DFS call = exactly ONE SCC
         */
        Arrays.fill(visited, false);
        int sccCount = 0;
//        List<List<Integer>> allSCCs = new ArrayList<>();

        // Process nodes in decreasing finishing time
        while (!stack.isEmpty()) {

            int node = stack.pop();

            // If node is not visited,
            // it starts a NEW SCC
            if (!visited[node]) {
                List<Integer> currentSCC = new ArrayList<>();
                dfs2(node, visited, revAdj, currentSCC);
                allSCCs.add(currentSCC);
                sccCount++; // one full SCC found
            }
        }

        return sccCount;
    }

    public static void main(String[] args) {

        // Number of vertices
        int V = 5;

        // Create adjacency list
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        /*
         * Graph:
         * 0 -> 2, 3
         * 1 -> 0
         * 2 -> 1
         * 3 -> 4
         * 4 -> none
         *
         * SCCs:
         * [0, 1, 2]
         * [3]
         * [4]
         */

        adj.get(0).add(2);
        adj.get(0).add(3);
        adj.get(1).add(0);
        adj.get(2).add(1);
        adj.get(3).add(4);



        // Get all SCCs
        List<List<Integer>> sccs = new ArrayList<>();
        int sccCount = kosaraju(V, adj, sccs);

        // Print SCCs
        System.out.println("Total SCC count: " + sccCount);
        System.out.println("Strongly Connected Components:" );
        int idx = 1;
        for (List<Integer> scc : sccs) {
            System.out.println("SCC " + idx + ": " + scc);
            idx++;
        }
    }

}
