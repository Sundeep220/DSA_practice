package Problems.Arrays.Medium;

import java.util.*;

public class LeadersInArray {

    //Problem Statement: Given an array of integers, find the leaders in the array. An element is leader if it is greater than all the elements to its right side. The rightmost element is always a leader.
    //https://www.geeksforgeeks.org/leaders-in-an-array/
    // Ex: arr[] = {16, 17, 4, 3, 5, 2}, the output should be {17, 5, 2}.
    //Explanation: The first leader is 17 as it is greater than all the elements to its right. Next leader is 5. The rightmost element 2 is always a leader.

    public static List<Integer> leaders(int[] arr) {
        List<Integer> leaders = new ArrayList<>();
        int max = Integer.MIN_VALUE;
        for(int i=arr.length-1; i>=0; i--){
            if(arr[i] > max) {
                leaders.add(arr[i]);
                max = arr[i];
            }
        }
//        Collections.reverse(leaders);
        return leaders.reversed();
    }

    public static void main(String[] args) {
        int[] arr = {16, 17, 4, 3, 5, 2};
        System.out.println(leaders(arr));
    }
}
