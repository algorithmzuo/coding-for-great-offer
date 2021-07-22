package class29;

public class Problem_0108_ConvertSortedArrayToBinarySearchTree {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int val) {
			this.val = val;
		}
	}

	public TreeNode sortedArrayToBST(int[] nums) {
		return process(nums, 0, nums.length - 1);
	}

	public static TreeNode process(int[] nums, int L, int R) {
		if (L > R) {
			return null;
		}
		if (L == R) {
			return new TreeNode(nums[L]);
		}
		int M = (L + R) / 2;
		TreeNode head = new TreeNode(nums[M]);
		head.left = process(nums, L, M - 1);
		head.right = process(nums, M + 1, R);
		return head;
	}

}
