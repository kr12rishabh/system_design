package example.coding_practice;


import java.util.Stack;

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

//
//class Solution {
//    public List<Integer> largestValues(TreeNode root) {
//        List<Integer> ans = new ArrayList<>();
//        Queue<TreeNode> q = new LinkedList<>();
//        q.add(root);
//        if (root == null)
//            return ans;
//        int maxi = Integer.MAX_VALUE;
//        while (!q.isEmpty()) {
//            int size = q.size();
//            for (int i = 0; i < size; i++) {
//                TreeNode node = q.poll();
//                maxi = Math.max(maxi,node.val);
//                if(node.left!=null)
//                    q.add(node.left);
//                if(node.right!=null)
//                    q.add(node.right);
//
//
//            }
//            ans.add(maxi);
//        }
//
//
//
//        return ans;
//    }
//}


class Solution {
    public boolean isValid(String s) {
        Stack<Character> st = new Stack<>();
        for (char ch : s.toCharArray()) {
            if (st.isEmpty() || ch == '(' || ch == '{' || ch == '[') {
                st.push(ch);
                continue;
            }
            if (ch == ')') {
                if (st.peek() == '(') {
                    st.pop();
                } else {
                    return false;
                }
            } else if (ch == '}') {
                if (st.peek() == '{') {
                    st.pop();
                } else {
                    return false;
                }
            } else if (ch == ']') {
                if (st.peek() == '[') {
                    st.pop();
                } else {
                    return false;
                }
            }
        }
        return st.isEmpty();

    }
}