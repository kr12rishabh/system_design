package example.study_material.DSA.BFS_Level_order;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Definition for a binary tree node.
 *
 * Example tree:
 *
 *          1
 *        /   \
 *       2     3
 *      / \     \
 *     4   5     6
 *
 * Level order output:
 *
 * [
 *   [1],
 *   [2, 3],
 *   [4, 5, 6]
 * ]
 *
 * Why?
 * First we visit level 1  -> 1
 * Then we visit level 2   -> 2, 3
 * Then we visit level 3   -> 4, 5, 6
 *
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */

class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {

        // This will store our final answer.
        // Each inner list will contain one level of the tree.
        //
        // Example:
        // For tree:
        //
        //        1
        //       / \
        //      2   3
        //
        // ans will become:
        // [[1], [2, 3]]
        List<List<Integer>> ans = new ArrayList<>();

        // We use a queue because we want to process nodes level by level.
        //
        // Queue works like a line.
        // The node that enters first will be processed first.
        //
        // This is perfect for BFS / level order traversal.
        Queue<TreeNode> q = new ArrayDeque<>();

        // If the tree is empty, there is nothing to traverse.
        // So we simply return the empty answer list.
        if (root == null) {
            return ans;
        }

        // First, we add the root node into the queue.
        //
        // Example:
        // Tree:
        //        1
        //       / \
        //      2   3
        //
        // Queue initially:
        // [1]
        q.add(root);

        // Keep running until there are no nodes left in the queue.
        //
        // Every time this while loop runs,
        // we process one complete level of the tree.
        while (!q.isEmpty()) {

            // This list will store values of the current level.
            //
            // Example:
            // If we are processing level 2:
            //
            //        1
            //       / \
            //      2   3
            //
            // temp will become:
            // [2, 3]
            List<Integer> temp = new ArrayList<>();

            // This is very important.
            //
            // q.size() tells how many nodes are currently present
            // in this level.
            //
            // We store it first because later we will add children
            // into the same queue.
            //
            // Example:
            // Queue before processing level 2:
            // [2, 3]
            //
            // levelSize = 2
            //
            // So we will process only 2 nodes in this level.
            int levelSize = q.size();

            // Process all nodes of the current level.
            for (int i = 0; i < levelSize; i++) {

                // Remove the front node from the queue.
                //
                // poll() gives us the first node and also removes it.
                //
                // Example:
                // Queue: [2, 3]
                // tops = 2
                // Queue after poll: [3]
                TreeNode tops = q.poll();

                // Add the current node value into temp.
                //
                // Example:
                // If tops is node 2,
                // temp becomes [2]
                temp.add(tops.val);

                // If the current node has a left child,
                // add it into the queue.
                //
                // Why?
                // Because this child belongs to the next level.
                if (tops.left != null) {
                    q.add(tops.left);
                }

                // If the current node has a right child,
                // add it into the queue.
                //
                // We add left first, then right,
                // because level order traversal goes left to right.
                if (tops.right != null) {
                    q.add(tops.right);
                }
            }

            // After the for loop ends,
            // temp contains all values of the current level.
            //
            // So we add it into our final answer.
            //
            // Example:
            // After level 1:
            // temp = [1]
            // ans = [[1]]
            //
            // After level 2:
            // temp = [2, 3]
            // ans = [[1], [2, 3]]
            ans.add(temp);
        }

        // Finally return the complete level order traversal.
        return ans;
    }
}