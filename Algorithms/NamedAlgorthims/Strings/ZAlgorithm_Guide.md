# Z Algorithm

## When to Use

-   Fast pattern matching.
-   Prefix matching.
-   Border/prefix problems.
-   String preprocessing.

## Pattern Recognition

Look for: - Prefix equals suffix - Pattern occurrences - Linear string
matching

## Intuition

Build a Z-array where Z\[i\] is the longest substring starting at i that
matches the prefix.

To search: Create: pattern + "\$" + text

Any Z value equal to pattern length means a match.

## Time & Space

Time: O(N+M) Space: O(N+M)

## Java Implementation

``` java
public boolean zSearch(String text,String pattern){
    String s=pattern+"$"+text;
    int[] z=new int[s.length()];
    int l=0,r=0;

    for(int i=1;i<s.length();i++){
        if(i<=r)
            z[i]=Math.min(r-i+1,z[i-l]);

        while(i+z[i]<s.length() &&
              s.charAt(z[i])==s.charAt(i+z[i]))
            z[i]++;

        if(i+z[i]-1>r){
            l=i;
            r=i+z[i]-1;
        }

        if(z[i]==pattern.length())
            return true;
    }
    return false;
}
```
