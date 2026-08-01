package Problems.Strings.Hard;


class CountAndSay{
    /*
    Problem:
    countAndSay(1) = "1"
    countAndSay(n) = Run-Length Encoding (RLE) of countAndSay(n-1)

    Sequence:
    1
    11
    21
    1211
    111221
    312211

    Key Idea:
    Generate the sequence by repeatedly applying Run-Length Encoding (RLE).

    ================================================================================
    APPROACH 1 : RECURSION
    ================================================================================

    Intuition:
    - The problem is recursively defined.
    - Compute countAndSay(n-1), then apply RLE once.

    Time Complexity:
    O(L1 + L2 + ... + Ln)

    Space Complexity:
    O(L + n)
    L = final string length
    n = recursion stack
    ================================================================================
    */

    public String countAndSay(int n) {
        if (n == 1) return "1";
        return encode(countAndSay(n - 1));
    }

    private String encode(String s) {
        StringBuilder ans = new StringBuilder();
        int i = 0;

        while (i < s.length()) {
            char current = s.charAt(i);
            int count = 0;

            while (i < s.length() && s.charAt(i) == current) {
                count++;
                i++;
            }

            ans.append(count).append(current);
        }

        return ans.toString();
    }

    /*
    ================================================================================
    APPROACH 2 : ITERATIVE (OPTIMAL)
    ================================================================================

    Intuition:
    - Start with "1".
    - Apply RLE repeatedly until reaching the nth string.

    Example:
    1
    -> 11
    -> 21
    -> 1211
    -> 111221

    Time Complexity:
    O(L1 + L2 + ... + Ln)

    Space Complexity:
    O(L)

    L = length of final generated string.
    ================================================================================
    */

    public String countAndSayIterative(int n) {
        String current = "1";
        for (int i = 2; i <= n; i++) {
            current = encode(current);
        }

        return current;
    }

}
