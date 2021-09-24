package class42;

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
		// >=8，最近的节点，而且需要快速找后继的这么一种结构
		Stack<TreeNode> moreTops = new Stack<>();
		// <=8，最近的节点，而且需要快速找前驱的这么一种结构
		Stack<TreeNode> lessTops = new Stack<>();
		getMoreTops(root, target, moreTops);
		getLessTops(root, target, lessTops);
		if (!moreTops.isEmpty() && !lessTops.isEmpty() && moreTops.peek().val == lessTops.peek().val) {
			getPredecessor(lessTops);
		}
		while (k-- > 0) {
			if (moreTops.isEmpty()) {
				ret.add(getPredecessor(lessTops));
			} else if (lessTops.isEmpty()) {
				ret.add(getSuccessor(moreTops));
			} else {
				double diffs = Math.abs((double) moreTops.peek().val - target);
				double diffp = Math.abs((double) lessTops.peek().val - target);
				if (diffs < diffp) {
					ret.add(getSuccessor(moreTops));
				} else {
					ret.add(getPredecessor(lessTops));
				}
			}
		}
		return ret;
	}

	// 在root为头的树上
	// 找到>=target，且最接近target的节点
	// 并且找的过程中，只要某个节点x往左走了，就把x放入moreTops里
	public static void getMoreTops(TreeNode root, double target, Stack<TreeNode> moreTops) {
		while (root != null) {
			if (root.val == target) {
				moreTops.push(root);
				break;
			} else if (root.val > target) {
				moreTops.push(root);
				root = root.left;
			} else {
				root = root.right;
			}
		}
	}

	// 在root为头的树上
	// 找到<=target，且最接近target的节点
	// 并且找的过程中，只要某个节点x往右走了，就把x放入lessTops里
	public static void getLessTops(TreeNode root, double target, Stack<TreeNode> lessTops) {
		while (root != null) {
			if (root.val == target) {
				lessTops.push(root);
				break;
			} else if (root.val < target) {
				lessTops.push(root);
				root = root.right;
			} else {
				root = root.left;
			}
		}
	}

	// 返回moreTops的头部的值
	// 并且调整moreTops : 为了以后能很快的找到返回节点的后继节点
	public static int getSuccessor(Stack<TreeNode> moreTops) {
		TreeNode cur = moreTops.pop();
		int ret = cur.val;
		cur = cur.right;
		while (cur != null) {
			moreTops.push(cur);
			cur = cur.left;
		}
		return ret;
	}

	// 返回lessTops的头部的值
	// 并且调整lessTops : 为了以后能很快的找到返回节点的前驱节点
	public static int getPredecessor(Stack<TreeNode> lessTops) {
		TreeNode cur = lessTops.pop();
		int ret = cur.val;
		cur = cur.left;
		while (cur != null) {
			lessTops.push(cur);
			cur = cur.right;
		}
		return ret;
	}

}
