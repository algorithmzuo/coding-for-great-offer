package class40;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Problem_0272_ClosestBinarySearchTreeValueII {

	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(int val) {
			this.val = val;
		}
	}

	// 这个解法来自讨论区的回答，最优解实现的很易懂且漂亮
	public static List<Integer> closestKValues(TreeNode root, double target, int k) {
		List<Integer> ret = new LinkedList<>();
		Stack<TreeNode> backUpRight = new Stack<>();
		Stack<TreeNode> backUpLeft = new Stack<>();
		goLeft(root, target, backUpRight);
		goRight(root, target, backUpLeft);
		if (!backUpRight.isEmpty() && !backUpLeft.isEmpty() && backUpRight.peek().val == backUpLeft.peek().val) {
			getPredecessor(backUpLeft);
		}
		while (k-- > 0) {
			if (backUpRight.isEmpty()) {
				ret.add(getPredecessor(backUpLeft));
			} else if (backUpLeft.isEmpty()) {
				ret.add(getSuccessor(backUpRight));
			} else {
				double diffs = Math.abs((double) backUpRight.peek().val - target);
				double diffp = Math.abs((double) backUpLeft.peek().val - target);
				if (diffs < diffp) {
					ret.add(getSuccessor(backUpRight));
				} else {
					ret.add(getPredecessor(backUpLeft));
				}
			}
		}
		return ret;
	}

	public static void goLeft(TreeNode root, double target, Stack<TreeNode> backUpRight) {
		while (root != null) {
			if (root.val == target) {
				backUpRight.push(root);
				break;
			} else if (root.val > target) {
				backUpRight.push(root);
				root = root.left;
			} else {
				root = root.right;
			}
		}
	}

	public static void goRight(TreeNode root, double target, Stack<TreeNode> backUpLeft) {
		while (root != null) {
			if (root.val == target) {
				backUpLeft.push(root);
				break;
			} else if (root.val < target) {
				backUpLeft.push(root);
				root = root.right;
			} else {
				root = root.left;
			}
		}
	}

	public static int getSuccessor(Stack<TreeNode> backUpRight) {
		TreeNode cur = backUpRight.pop();
		int ret = cur.val;
		cur = cur.right;
		while (cur != null) {
			backUpRight.push(cur);
			cur = cur.left;
		}
		return ret;
	}

	public static int getPredecessor(Stack<TreeNode> backUpLeft) {
		TreeNode cur = backUpLeft.pop();
		int ret = cur.val;
		cur = cur.left;
		while (cur != null) {
			backUpLeft.push(cur);
			cur = cur.right;
		}
		return ret;
	}

}
