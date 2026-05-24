package example.study_material.DSA;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Definition for a binary tree node.
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

class Solution {
    public TreeNode deleteHelper(TreeNode root, Set<Integer> st, List<TreeNode> treeNodeList) {
        if (root == null)
            return null;
        root.left = deleteHelper(root.left, st, treeNodeList);
        root.right = deleteHelper(root.right, st, treeNodeList);
        if (st.contains(root.val)) {
            if (root.left != null)
                treeNodeList.add(root.left);
            if (root.right != null)
                treeNodeList.add(root.right);
            return null;

        } else return null;
    }

    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        List<TreeNode> treeNodeList = new ArrayList<>();
        Set<Integer> st = new HashSet<>();
        for (int num : to_delete) {
            st.add(num);
        }
        deleteHelper(root, st, treeNodeList);
        if (!st.contains(root.val))
            treeNodeList.add(root);

        return treeNodeList;

    }
}