package Problems.Graphs.Basics;
import java.util.*;

/**
 * Complete Graph Representation Examples in Java
 */
public class GraphRepresentation {

    /* ============================
       1️⃣ UNDIRECTED GRAPH
       ============================ */

    static class UndirectedGraph {
        int V;
        List<List<Integer>> adj;

        UndirectedGraph(int V) {
            this.V = V;
            adj = new ArrayList<>();
            for (int i = 0; i < V; i++) {
                adj.add(new ArrayList<>());
            }
        }

        void addEdge(int u, int v) {
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        void printGraph() {
            System.out.println("Undirected Graph:");
            for (int i = 0; i < V; i++) {
                System.out.print(i + " -> ");
                for (int neighbor : adj.get(i)) {
                    System.out.print(neighbor + " ");
                }
                System.out.println();
            }
        }
    }

    /* ============================
       2️⃣ DIRECTED GRAPH
       ============================ */

    static class DirectedGraph {
        int V;
        List<List<Integer>> adj;

        DirectedGraph(int V) {
            this.V = V;
            adj = new ArrayList<>();
            for (int i = 0; i < V; i++) {
                adj.add(new ArrayList<>());
            }
        }

        void addEdge(int u, int v) {
            adj.get(u).add(v); // u → v
        }

        void printGraph() {
            System.out.println("Directed Graph:");
            for (int i = 0; i < V; i++) {
                System.out.print(i + " -> ");
                for (int neighbor : adj.get(i)) {
                    System.out.print(neighbor + " ");
                }
                System.out.println();
            }
        }
    }

    /* ============================
       3️⃣ WEIGHTED GRAPH
       ============================ */

    static class Edge {
        int to;
        int weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static class WeightedGraph {
        int V;
        List<List<Edge>> adj;

        WeightedGraph(int V) {
            this.V = V;
            adj = new ArrayList<>();
            for (int i = 0; i < V; i++) {
                adj.add(new ArrayList<>());
            }
        }

        void addEdge(int u, int v, int w) {
            adj.get(u).add(new Edge(v, w));
            adj.get(v).add(new Edge(u, w)); // undirected
        }

        void printGraph() {
            System.out.println("Weighted Graph:");
            for (int i = 0; i < V; i++) {
                System.out.print(i + " -> ");
                for (Edge edge : adj.get(i)) {
                    System.out.print("(" + edge.to + ", " + edge.weight + ") ");
                }
                System.out.println();
            }
        }
    }

    /* ============================
       4️⃣ ADJACENCY MATRIX
       ============================ */

    static class AdjacencyMatrixGraph {
        int V;
        int[][] matrix;

        AdjacencyMatrixGraph(int V) {
            this.V = V;
            matrix = new int[V][V];
        }

        void addEdge(int u, int v) {
            matrix[u][v] = 1;
            matrix[v][u] = 1;
        }

        void printGraph() {
            System.out.println("Adjacency Matrix:");
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    System.out.print(matrix[i][j] + " ");
                }
                System.out.println();
            }
        }
    }

    /* ============================
       5️⃣ MAIN METHOD (TESTING)
       ============================ */

    public static void main(String[] args) {

        // Undirected Graph
        UndirectedGraph ug = new UndirectedGraph(4);
        ug.addEdge(0, 1);
        ug.addEdge(0, 2);
        ug.addEdge(1, 3);
        ug.addEdge(2, 3);
        ug.printGraph();

        System.out.println();

        // Directed Graph
        DirectedGraph dg = new DirectedGraph(4);
        dg.addEdge(0, 1);
        dg.addEdge(1, 2);
        dg.addEdge(2, 3);
        dg.printGraph();

        System.out.println();

        // Weighted Graph
        WeightedGraph wg = new WeightedGraph(4);
        wg.addEdge(0, 1, 5);
        wg.addEdge(0, 2, 3);
        wg.addEdge(1, 3, 2);
        wg.addEdge(2, 3, 7);
        wg.printGraph();

        System.out.println();

        // Adjacency Matrix
        AdjacencyMatrixGraph am = new AdjacencyMatrixGraph(4);
        am.addEdge(0, 1);
        am.addEdge(0, 2);
        am.addEdge(1, 3);
        am.addEdge(2, 3);
        am.printGraph();
    }
}
