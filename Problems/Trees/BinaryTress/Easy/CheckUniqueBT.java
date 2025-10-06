package Problems.Trees.BinaryTress.Easy;

public class CheckUniqueBT {
    // Problem: https://www.naukri.com/code360/problems/unique-binary-tree_8180906?leftPanelTabValue=PROBLEM
    public static boolean uniqueBinaryTree(int a, int b){
        // preorder = 1, inorder = 2, postorder = 3

        if ((a == 2 && (b == 1 || b == 3)) ||
                (b == 2 && (a == 1 || a == 3))) {
            return true;
        }

        return false;
    }
}
