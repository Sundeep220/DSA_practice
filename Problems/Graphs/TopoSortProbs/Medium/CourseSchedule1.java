package Problems.Graphs.TopoSortProbs.Medium;
import java.util.*;


public class CourseSchedule1 {
    // Problem: https://leetcode.com/problems/course-schedule/


    // BFS: Kahn's Algorithm
    // Time Complexity: O(V + E)
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }

        int[] indegree = new int[numCourses];

        // Build graph
        for (int[] p : prerequisites) {
            int course = p[0];
            int prereq = p[1];
            adj.get(prereq).add(course);
            indegree[course]++;
        }

        // Queue for BFS
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        int completed = 0;

        // BFS Topological traversal
        while (!queue.isEmpty()) {
            int node = queue.poll();
            completed++;

            for (int neighbor : adj.get(node)) {
                indegree[neighbor]--;
                if (indegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        return completed == numCourses;
    }

}
