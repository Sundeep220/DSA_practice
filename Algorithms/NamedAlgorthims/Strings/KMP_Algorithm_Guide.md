# KMP (Knuth-Morris-Pratt) Algorithm

## When to Use

Use KMP when: - Find a pattern in a text. - Many repeated searches. -
Worst-case linear time is required. - Problems asking if one string is a
substring of another.

## Pattern Recognition

Look for: - "Find pattern in text" - "Substring search" - "Repeated
String Match" - "Implement strStr()"

## Intuition

Naive matching restarts from the next text position after a mismatch.
KMP preprocesses the pattern using the **LPS (Longest Prefix Suffix)**
array so it can reuse previous matches instead of comparing again.

## Time & Space

-   Build LPS: O(M)
-   Search: O(N)
-   Overall: O(N+M)
-   Space: O(M)

## Java Implementation

``` java
public boolean kmpSearch(String text, String pattern){
    int[] lps = buildLPS(pattern);
    int i=0,j=0;
    while(i<text.length()){
        if(text.charAt(i)==pattern.charAt(j)){
            i++; j++;
            if(j==pattern.length()) return true;
        }else if(j!=0){
            j=lps[j-1];
        }else{
            i++;
        }
    }
    return false;
}

private int[] buildLPS(String p){
    int[] lps=new int[p.length()];
    int len=0;
    for(int i=1;i<p.length();){
        if(p.charAt(i)==p.charAt(len)){
            lps[i++]=++len;
        }else if(len!=0){
            len=lps[len-1];
        }else{
            lps[i++]=0;
        }
    }
    return lps;
}
```
