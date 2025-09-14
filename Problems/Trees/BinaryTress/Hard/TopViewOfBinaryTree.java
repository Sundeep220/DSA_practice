package Problems.Trees.BinaryTress.Hard;

import java.util.*;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;

public class TopViewOfBinaryTree {
    // PRoblem: https://www.naukri.com/code360/problems/top-view-of-binary-tree_799401?leftPanelTabValue=SUBMISSION

    static class Pair{
        TreeNode<Integer> node;
        int col;

        Pair(int col, TreeNode<Integer> node){
            this.node = node;
            this.col = col;
        }
    }

    public static List<Integer> getTopView(TreeNode<Integer> root) {
        // Write your code here.
        TreeMap<Integer, Integer> map = new TreeMap<>();
        Queue<Pair> q = new LinkedList<>();
        q.offer(new Pair(0, root));

        while(!q.isEmpty()){
            Pair p = q.poll();
            TreeNode<Integer> curr = p.node;
            int line = p.col;

            if(!map.containsKey(line)){
                map.put(line, curr.data);
            }

            if(curr.left != null)
                q.offer(new Pair(line - 1, curr.left));

            if(curr.right != null)
                q.offer(new Pair(line + 1, curr.right));
        }

//        List<Integer> res = new ArrayList<>();
//        for(Integer value: map.values()){
//            res.add(value);
//        }
        return new ArrayList<>(map.values());
    }
}
