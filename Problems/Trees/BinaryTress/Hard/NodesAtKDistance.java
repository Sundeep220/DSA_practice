package Problems.Trees.BinaryTress.Hard;

import java.util.*;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;

public class NodesAtKDistance {
    // Problem: https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/description/

    public void markParents(TreeNode<Integer> root, Map<TreeNode<Integer>, TreeNode<Integer>> parent_map){
        Queue<TreeNode<Integer>> q = new LinkedList<>();
        q.offer(root);

        while(!q.isEmpty()){
            TreeNode<Integer> c = q.poll();

            if(c.left != null){
                q.offer(c.left);
                parent_map.put(c.left, c);
            }

            if(c.right != null){
                q.offer(c.right);
                parent_map.put(c.right, c);
            }
        }
    }

    public List<Integer> distanceK(TreeNode<Integer> root, TreeNode<Integer> target, int k) {
        if (root == null) return new ArrayList<>();

        Map<TreeNode<Integer>, TreeNode<Integer>> parentMap = new HashMap<>();
        markParents(root, parentMap);

        Set<TreeNode<Integer>> visited = new HashSet<>();
        Queue<TreeNode<Integer>> q = new LinkedList<>();
        q.offer(target);        // Start BFS from target
        visited.add(target);

        int level = 0;

        while(!q.isEmpty()){
            int size = q.size();
            if(level == k) break;

            for(int i = 0; i < size; i++){
                TreeNode<Integer> curr = q.poll();

                if(curr.left != null && !visited.contains(curr.left)){
                    q.offer(curr.left);
                    visited.add(curr.left);
                }

                if(curr.right != null && !visited.contains(curr.right)){
                    q.offer(curr.right);
                    visited.add(curr.right);
                }

                TreeNode<Integer> parent = parentMap.get(curr);
                if(parent != null && !visited.contains(parent)){
                    q.offer(parent);
                    visited.add(parent);
                }
            }

            level++;
        }

        List<Integer> res = new ArrayList<>();
        while(!q.isEmpty()){
            res.add(q.poll().data);
        }

        return res;
    }
}
