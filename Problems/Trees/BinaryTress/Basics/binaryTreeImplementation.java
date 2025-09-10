package Problems.Trees.BinaryTress.Basics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class binaryTreeImplementation<T extends Comparable<T>> {
    public static class TreeNode<T>{
        public T data;
        public TreeNode<T> left;
        public TreeNode<T> right;

        TreeNode(T data){
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private TreeNode<T> root;

    public boolean contains(T key){
        return contains(root, key);
    }

    private boolean contains(TreeNode<T> root, T key){
        if(root == null){
            return false;
        }
        if(root.data.equals(key)){
            return true;
        }
        return contains(root.left, key) || contains(root.right, key);
    }

    public void insert(T key){
        root = insert(root, key);
    }

    private TreeNode<T> insert(TreeNode<T> root, T key){
        if(root == null) return new TreeNode<>(key);
        if(root.data.compareTo(key) > 0){
            root.left = insert(root.left, key);
        } else {
            root.right = insert(root.right, key);
        }
        return root;
    }

    // Delete (recursive) - handles 3 cases
    public void delete(T key) {
        root = delete(root, key);
    }
    private TreeNode<T> delete(TreeNode<T> node, T key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.data);
        if (cmp < 0) node.left = delete(node.left, key);
        else if (cmp > 0) node.right = delete(node.right, key);
        else {
            // node to delete found
            if (node.left == null) return node.right;   // no left child
            if (node.right == null) return node.left;   // no right child
            // two children: replace node with inorder successor (min in right subtree)
            TreeNode<T> successor = min(node.right);
            successor.right = deleteMin(node.right);
            successor.left = node.left;
            node = successor;
        }
        return node;
    }
    private TreeNode<T> min(TreeNode<T> node) {
        while (node.left != null) node = node.left;
        return node;
    }
    private TreeNode<T> deleteMin(TreeNode<T> node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        return node;
    }

    private void inOrderTraversal(TreeNode<T> root){
        if(root == null) return;

        inOrderTraversal(root.left);
        System.out.print(root.data + " ");
        inOrderTraversal(root.right);
    }

    private void preOrderTraversal(TreeNode<T> root){
        if(root == null) return;

        System.out.print(root.data + " ");
        preOrderTraversal(root.left);
        preOrderTraversal(root.right);
    }

    public void postOrderTraversal(TreeNode<T> root){
        if(root == null) return;

        postOrderTraversal(root.left);
        postOrderTraversal(root.right);
        System.out.print(root.data + " ");
    }

    public void levelOrderTraversal(TreeNode<T> root){
        if(root == null) return;

        Queue<TreeNode<T>> nodes = new LinkedList<>();
        nodes.add(root);

        while(!nodes.isEmpty()){
            TreeNode<T> currNode = nodes.poll();
            System.out.print(currNode.data + " ");
            if(currNode.left != null) nodes.add(currNode.left);
            if(currNode.right != null) nodes.add(currNode.right);
        }
    }

    public void printTreeHierarchy(){
        if (root == null) return;

        Queue<TreeNode<T>> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            int size = q.size(); // number of nodes at this level
            for (int i = 0; i < size; i++) {
                TreeNode<T> node = q.poll();
                System.out.print(node.data + " ");
                if (node.left != null) q.add(node.left);
                if (node.right != null) q.add(node.right);
            }
            System.out.println(); // move to next line after each level
        }
    }

    public static void main(String[] args) {
        binaryTreeImplementation<Integer> tree = new binaryTreeImplementation<>();
        tree.insert(5);
        tree.insert(3);
        tree.insert(8);
        tree.insert(1);
        tree.insert(4);
        tree.insert(6);
        tree.insert(9);
        tree.delete(3); // delete node with key 3
        tree.inOrderTraversal(tree.root);
        System.out.println();
        tree.preOrderTraversal(tree.root);
        System.out.println();
        tree.postOrderTraversal(tree.root);
        System.out.println();
        tree.levelOrderTraversal(tree.root);
        System.out.println();
        tree.printTreeHierarchy();
    }
}
