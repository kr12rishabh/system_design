package example.service.impl;


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

    class Solution {
        String ans = null;

        public String smallestFromLeaf(TreeNode root) {

            StringBuilder path = new StringBuilder();
            solve(root, path);
            return ans;


        }

        void solve(TreeNode root, StringBuilder path) {
            if (root == null)
                return;
            char ch = (char) ('a' + root.val);
            path.append(ch);
            if (root.left == null && root.right == null) {
                String current = path.reverse().toString();
                if (ans == null || current.compareTo(ans) < 0) {
                    ans = current;
                }
                path.reverse();
            }
            solve(root.left, path);
            solve(root.right, path);
            path.deleteCharAt(path.length() - 1);

        }
    }


}

