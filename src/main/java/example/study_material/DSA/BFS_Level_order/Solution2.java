package example.study_material.DSA.BFS_Level_order;

import java.util.HashMap;
import java.util.Map;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */
class Solution2 {
    public TreeNode solve(int [] inorder, int [] postorder, int inStart, int inEnd, int postStart, int postEnd, Map<Integer, Integer> inMap){
        if(inStart>inEnd || postStart> postEnd){
            return  null;
        }
        TreeNode root = new TreeNode(postorder[postEnd]);

        int inRoot = inMap.get(postorder[postEnd]);
        int numsLeft = inRoot-inStart;
        root.left = solve(inorder,postorder,inStart, inRoot-1,postStart,postStart+numsLeft-1,inMap);
        root.right = solve(inorder,postorder,inRoot+1   , inEnd, postStart+numsLeft,postEnd-1,inMap);
        return  root;
    }
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        Map<Integer, Integer> inMap = new HashMap<>();
        int m = inorder.length;
        int n = postorder.length;
        for (int i = 0; i < inorder.length; i++) {
            inMap.put(inorder[i], i);
        }
        TreeNode root = solve(inorder,postorder,0,m-1,0,n-1,inMap);

return root;

    }
}