package Problems.Greedy.Easy;

public class LemonadeChange {
    // Problem: https://leetcode.com/problems/lemonade-change/


    // Brute Force: Try out all possible combinations
    // Time Complexity: O(2^k) time | O(1) space
    public static boolean lemonadeChange(int[] bills) {
        return canServe(0, bills, 0, 0);
    }

    public static boolean canServe(int index, int[] bills, int five, int ten) {
        if(index == bills.length) return true;

        int bill = bills[index];

        if(bill == 5)
            return canServe(index + 1, bills, five + 1, ten);
        else if(bill == 10) {
            if(five > 0)
                return canServe(index + 1, bills, five - 1, ten + 1);
            else return false;
        }else{
            // bill == 20
            // Option 1: give 10 + 5
            boolean option1 = false, option2 = false;
            if (ten > 0 && five > 0) {
                option1 = canServe(index + 1, bills, five - 1, ten - 1);
            }
            // Option 2: give 3 fives
            if (five >= 3) {
                option2 = canServe(index + 1, bills, five - 3, ten);
            }
            return option1 || option2;
        }
    }

    // Optimal Solution: Greedy
    // Time Complexity: O(n) time | O(1) space
    public static boolean lemonadeChangeOptimal(int[] bills) {
        int five = 0, ten = 0;
        for (int bill : bills) {
            if (bill == 5) five++;
            else if (bill == 10) {
                if (five == 0) return false;
                five--; ten++;
            } else {
                if (five > 0 && ten > 0) {
                    five--; ten--;
                } else if (five >= 3) {
                    five -= 3;
                } else return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] bills = {5, 5, 10, 10, 20};
        System.out.println(lemonadeChange(bills));
        System.out.println(lemonadeChangeOptimal(bills));

    }
}
