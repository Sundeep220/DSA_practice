package Problems.GoodProblems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PowerSet {
    // Using Functional Backtracking to solve this problem
    public List<String> AllPossibleStrings(String s) {
        // Code here
        List<String> a = new ArrayList<>();
        generate(s, 0, "", a);
        Collections.sort(a);
        return a;
    }

    void generate(String s, int index, String currentSubseq, List<String> result){
        if(index == s.length()){
            // if we came at the end of string
            if(!currentSubseq.isEmpty()){
                // If this is not empty, then add it
                result.add(currentSubseq);
            }
            return;
        }

        // include the current character and go to next step
        generate(s, index + 1, currentSubseq + s.charAt(index), result);

        // exclude the current character and go to next step
        generate(s, index + 1, currentSubseq, result);
    }
}
