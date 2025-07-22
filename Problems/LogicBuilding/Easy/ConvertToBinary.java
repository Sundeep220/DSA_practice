package Problems.LogicBuilding.Easy;

public class ConvertToBinary {

    public static String convertToBinary(int n) {
        // code here
        String ans = "";
        System.out.println("Ans: " + Integer.toBinaryString(n));
        while (n > 0) {
            int rem = n % 2;
            ans = rem + ans;
            n = n / 2;
        }
        return ans;
    }

    public static String convertBinaryToDecimal(String s) {
        int ans = 0;
        int pow = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            ans += (int) ((s.charAt(i) - '0') * Math.pow(2, pow++));
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        System.out.println(convertToBinary(14));
        System.out.println(convertBinaryToDecimal("1110"));
    }
}
