package Problems.Heaps.Easy;

import java.util.PriorityQueue;

public class ConnectNRopes {
    // https://www.geeksforgeeks.org/connect-n-ropes-minimum-cost/
    //https://www.naukri.com/code360/problems/connect-n-ropes-with-minimum-cost_625783?leftPanelTabValue=PROBLEM

    static int minCost(int arr[], int n) {
        /*
         * Your class should be named Solution.Don't write main().Don't take
         * input, it is passed as function argument.Don't print output.Taking
         * input and printing output is handled automatically.
         */
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int num: arr)
            pq.offer(num);

        int cost = 0;
        while(!pq.isEmpty()){
            int first = pq.poll();
            if(!pq.isEmpty()){
                int second = pq.poll();
                cost += first + second;
                pq.offer(first+second);
            }else break;
        }
        return cost;
    }

    public static void main(String[] args) {
        int[] arr = {4, 3, 2, 6};
        System.out.println(minCost(arr, arr.length)); // 29
    }
}
