package Problems.Greedy.Hard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NMeetingsInRoom {
    // Problem: https://www.naukri.com/code360/problems/maximum-meetings_1062658

    // Brute Force: Try out all possible combinations
    // Time Complexity: O(2^k + nlogn) time | O(1) space

    static class Meeting implements Comparable<Meeting> {
        int start, end;
        Meeting(int start, int end) {
            this.start = start;
            this.end = end;
        }
        // To be able to sort the meetings by end time
        public int compareTo(Meeting m) {
            return this.end - m.end;
        }

        public String toString() {
            return "(" + start + ", " + end + ")";
        }
    }

    public static int NMeetingsInARoom(int[] start, int[] end){
        List<Meeting> meetings = new ArrayList<>();
        for(int i = 0; i < start.length; i++){
            meetings.add(new Meeting(start[i], end[i]));
        }

        Collections.sort(meetings);
        System.out.println(meetings);
        return helper(0, -1, meetings);
    }


    public static int helper(int index, int lastEnd, List<Meeting> meetings) { // index is the current meeting, lastEnd is the end time of the last meeting>)
        if(index == meetings.size())
            return 0; //no meetings left

        int notTake = helper(index + 1, lastEnd, meetings);

        int take = 0;
        if(meetings.get(index).start > lastEnd){
            take = 1 + helper(index + 1, meetings.get(index).end, meetings);
        }

        return Math.max(take, notTake);
    }


    // Optimal Solution: Greedy
    // Time Complexity: O(nlogn) time | O(1) space
    public static int NMeetingsInARoomOptimal(int[] start, int[] end){
        List<Meeting> meetings = new ArrayList<>();
        for(int i = 0; i < start.length; i++){
            meetings.add(new Meeting(start[i], end[i]));
        }

        Collections.sort(meetings);
        int count = 1;
        int last = meetings.getFirst().end;
        for(int i = 1; i < meetings.size(); i++){
            if(meetings.get(i).start > last){
                count++;
                last = meetings.get(i).end;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int[] start = {1, 3, 0, 5, 8, 5};
        int[] end = {2, 4, 6, 7, 9, 9};
        System.out.println(NMeetingsInARoom(start, end)); // 4
        System.out.println(NMeetingsInARoomOptimal(start, end)); // 4
    }
}
