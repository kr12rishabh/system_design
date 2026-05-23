package example.study_material.DSA.BFS_Level_order;


/**
 * Definition for a binary tree node.
 * <p>
 * Example tree:
 * <p>
 * 1
 * /   \
 * 2     3
 * / \     \
 * 4   5     6
 * <p>
 * Level order output:
 * <p>
 * [
 * [1],
 * [2, 3],
 * [4, 5, 6]
 * ]
 * <p>
 * Why?
 * First we visit level 1  -> 1
 * Then we visit level 2   -> 2, 3
 * Then we visit level 3   -> 4, 5, 6
 */
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

