package example.java_learning.multithreading.read_write_lock;

class Solution {

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {

        int[] result = new int[nums1.length];

        for (int i = 0; i < nums1.length; i++) {

            int current = nums1[i];

            int index = -1;

            // Find current element inside nums2
            for (int j = 0; j < nums2.length; j++) {

                if (nums2[j] == current) {
                    index = j;
                    break;
                }
            }

            // Default answer
            result[i] = -1;

            // Find first greater element on right
            for (int j = index + 1; j < nums2.length; j++) {

                if (nums2[j] > current) {
                    result[i] = nums2[j];
                    break;
                }
            }
        }

        return result;
    }
}