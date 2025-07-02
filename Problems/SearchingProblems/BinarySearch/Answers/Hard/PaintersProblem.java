package Problems.SearchingProblems.BinarySearch.Answers.Hard;

public class PaintersProblem {
    // Problem Statement: https://www.naukri.com/code360/problems/painter-s-partition-problem_1089557

    // Brute Force
    public static int minTimeToPaint(int[] boards, int k) {
        int n = boards.length;
        if(n < k) return -1;
        int low = 0, high = 0;
        for(int i: boards) {
            low = Math.max(low, i);
            high += i;
        }
        for(int i = low; i <= high; i++) {
            if(countPainters(boards, i) <= k) {
                return i;
            }
        }
        return -1;
    }

    public static int countPainters(int[] boards, int time) {
        int painterCount = 1;
        int currentTime = 0;
        for(int i: boards) {
            if(currentTime + i > time) {
                painterCount++;
                currentTime = i;
            } else {
                currentTime += i;
            }
        }
        return painterCount;
    }

    // Optimal Solution: Using Binary Search
    public static int minTimeToPaintOptimal(int[] boards, int k) {
        int n = boards.length;
        if(n < k) return -1;
        int low = 0, high = 0;
        for(int i: boards) {
            low = Math.max(low, i);
            high += i;
        }
        while(low < high) {
            int mid = low + (high - low) / 2;
            if(countPainters(boards, mid) <= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    public static void main(String[] args) {
        int[] boards = {10, 10, 10, 10};
        int k = 2;
        System.out.println(minTimeToPaint(boards, k));
        System.out.println(minTimeToPaintOptimal(boards, k));
    }
}
