# Rabin-Karp Algorithm

## When to Use

-   Pattern matching using hashing.
-   Search multiple patterns.
-   Rolling hash problems.
-   Duplicate substring detection.

## Pattern Recognition

Keywords: - Rolling hash - String hashing - Many pattern searches -
Duplicate substring

## Intuition

Instead of comparing characters every time, compare hashes. If hashes
match, verify character by character to avoid collisions.

## Time & Space

Average: O(N+M) Worst: O(N\*M) because of hash collisions. Space: O(1)

## Java Implementation

``` java
public int rabinKarp(String text,String pat){
    int n=text.length(),m=pat.length();
    if(m>n) return -1;

    long base=31,mod=1_000_000_007L;
    long power=1,patHash=0,txtHash=0;

    for(int i=0;i<m;i++){
        patHash=(patHash*base+pat.charAt(i))%mod;
        txtHash=(txtHash*base+text.charAt(i))%mod;
        if(i<m-1) power=(power*base)%mod;
    }

    for(int i=0;i<=n-m;i++){
        if(txtHash==patHash && text.substring(i,i+m).equals(pat))
            return i;

        if(i<n-m){
            txtHash=(txtHash-text.charAt(i)*power)%mod;
            if(txtHash<0) txtHash+=mod;
            txtHash=(txtHash*base+text.charAt(i+m))%mod;
        }
    }
    return -1;
}
```
