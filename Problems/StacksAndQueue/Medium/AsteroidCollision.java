package Problems.StacksAndQueue.Medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class AsteroidCollision {
    // Problem: https://leetcode.com/problems/asteroid-collision/description/

    // Brute Force: Simulate collision
    // Time Complexity: O(n^2)
    // Space Complexity: O(n)
    public static int[] asteroidCollisionBruteI(int[] asteroids) {
        List<Integer> list = new ArrayList<>();
        for (int ast : asteroids) list.add(ast);

        boolean collisionHappened = true;
        while (collisionHappened) {
            collisionHappened = false;
            for (int i = 0; i < list.size() - 1; i++) {
                int curr = list.get(i);
                int next = list.get(i + 1);

                // Collision condition: right-moving meets left-moving
                if (curr > 0 && next < 0) {
                    collisionHappened = true;
                    if (Math.abs(curr) > Math.abs(next)) {
                        list.remove(i + 1);  // next explodes
                    } else if (Math.abs(curr) < Math.abs(next)) {
                        list.remove(i);      // current explodes
                    } else {
                        list.remove(i + 1);  // both explode
                        list.remove(i);
                    }
                    break;  // restart scan after any collision
                }
            }
        }

        // Convert List<Integer> to int[]
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    // Another way to write Brute Force:
    public static int[] asteroidCollisionBruteII(int[] asteroids) {
        List<Integer> list = new ArrayList<>();
        for (int a : asteroids) list.add(a);

        int i = 0;
        while (i < list.size() - 1) {
            int curr = list.get(i);
            int next = list.get(i + 1);

            if (curr > 0 && next < 0) { // collision
                if (Math.abs(curr) > Math.abs(next)) {
                    list.remove(i + 1);      // next explodes
                } else if (Math.abs(curr) < Math.abs(next)) {
                    list.remove(i);          // current explodes
                    if (i > 0) i--;          // step back to check new collision
                } else {
                    list.remove(i + 1);      // both same size → remove both
                    list.remove(i);
                    if (i > 0) i--;          // step back
                }
            } else {
                i++;
            }
        }

        // Convert to int[]
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    // Optimal Solution: Using Stack
    public static int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();
        for (int a : asteroids) {
            if (a > 0) {
                stack.push(a);
            } else {
                while (!stack.isEmpty() && stack.peek() > 0 && stack.peek() < Math.abs(a)) {
                    stack.pop();
                }

                // ✅ Fix: handle equal-size collision properly
                if (!stack.isEmpty() && stack.peek() == Math.abs(a)) {
                    stack.pop(); // both explode, do NOT push current
                    continue;
                }

                if (stack.isEmpty() || stack.peek() < 0) {
                    stack.push(a);
                }
            }
        }
        int[] res = new int[stack.size()];
        for (int i = res.length - 1; i >= 0; i--) {
            res[i] = stack.pop();
        }
        return res;
    }


    // Same approach but using List
    public static int[] asteroidCollisionList(int[] asteroids) {
        List<Integer> stack = new ArrayList<>();
        for (int a : asteroids) {
            if (a > 0) {
                stack.add(a);
            } else {
                while (!stack.isEmpty() && stack.getLast() > 0 && stack.getLast() < Math.abs(a)) {
                    stack.removeLast();
                }

                // ✅ Fix: handle equal-size collision properly
                if (!stack.isEmpty() && stack.getLast() == Math.abs(a)) {
                    stack.removeLast(); // both explode, do NOT push current
                    continue;
                }

                if (stack.isEmpty() || stack.getLast() < 0) {
                    stack.add(a);
                }
            }
        }
        return stack.stream().mapToInt(Integer::intValue).toArray();
    }

    public static void main(String[] args) {
        int[] asteroids = {5, 10, -5};
        System.out.println(Arrays.toString(asteroidCollisionBruteI(asteroids)));
        System.out.println(Arrays.toString(asteroidCollisionBruteII(asteroids)));
        System.out.println(Arrays.toString(asteroidCollision(asteroids)));
        System.out.println(Arrays.toString(asteroidCollisionList(asteroids)));

    }

}
