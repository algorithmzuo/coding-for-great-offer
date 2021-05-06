package class05;

import java.util.Stack;

// 本题测试链接 : https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/
public class Code01_ConstructBinarySearchTreeFromPreorderTraversal {

	// 不用提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode() {
		}

		public TreeNode(int val) {
			this.val = val;
		}

		public TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}

	// 提交下面的方法
	public static TreeNode bstFromPreorder1(int[] pre) {
		if (pre == null || pre.length == 0) {
			return null;
		}
		return process1(pre, 0, pre.length - 1);
	}

	public static TreeNode process1(int[] pre, int L, int R) {
		if (L > R) {
			return null;
		}
		int firstBig = L + 1;
		for (; firstBig <= R; firstBig++) {
			if (pre[firstBig] > pre[L]) {
				break;
			}
		}
		TreeNode head = new TreeNode(pre[L]);
		head.left = process1(pre, L + 1, firstBig - 1);
		head.right = process1(pre, firstBig, R);
		return head;
	}

	// 已经是时间复杂度最优的方法了，但是常数项还能优化
	public static TreeNode bstFromPreorder2(int[] pre) {
		if (pre == null || pre.length == 0) {
			return null;
		}
		int N = pre.length;
		int[] nearBig = new int[N];
		for (int i = 0; i < N; i++) {
			nearBig[i] = -1;
		}
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < N; i++) {
			while (!stack.isEmpty() && pre[stack.peek()] < pre[i]) {
				nearBig[stack.pop()] = i;
			}
			stack.push(i);
		}
		return process2(pre, 0, N - 1, nearBig);
	}

	public static TreeNode process2(int[] pre, int L, int R, int[] nearBig) {
		if (L > R) {
			return null;
		}
		int firstBig = nearBig[L] == -1 ? R + 1 : nearBig[L];
		TreeNode head = new TreeNode(pre[L]);
		head.left = process2(pre, L + 1, firstBig - 1, nearBig);
		head.right = process2(pre, firstBig, R, nearBig);
		return head;
	}

	// 最优解
	public static TreeNode bstFromPreorder3(int[] pre) {
		if (pre == null || pre.length == 0) {
			return null;
		}
		int N = pre.length;
		int[] nearBig = new int[N];
		for (int i = 0; i < N; i++) {
			nearBig[i] = -1;
		}
		int[] stack = new int[N];
		int size = 0;
		for (int i = 0; i < N; i++) {
			while (size != 0 && pre[stack[size - 1]] < pre[i]) {
				nearBig[stack[--size]] = i;
			}
			stack[size++] = i;
		}
		return process3(pre, 0, N - 1, nearBig);
	}

	public static TreeNode process3(int[] pre, int L, int R, int[] nearBig) {
		if (L > R) {
			return null;
		}
		int firstBig = nearBig[L] == -1 ? R + 1 : nearBig[L];
		TreeNode head = new TreeNode(pre[L]);
		head.left = process3(pre, L + 1, firstBig - 1, nearBig);
		head.right = process3(pre, firstBig, R, nearBig);
		return head;
	}

}
