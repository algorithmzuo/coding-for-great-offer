package class30;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

// follow up : 如果要求返回整个路径怎么做？
public class Problem_0124_BinaryTreeMaximumPathSum {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		public TreeNode(int v) {
			val = v;
		}

	}

	public static int maxPathSum(TreeNode root) {
		if (root == null) {
			return 0;
		}
		return process(root).maxPathSum;
	}

	// 任何一棵树，必须汇报上来的信息
	public static class Info {
		public int maxPathSum;
		public int maxPathSumFromHead;

		public Info(int path, int head) {
			maxPathSum = path;
			maxPathSumFromHead = head;
		}
	}

	public static Info process(TreeNode x) {
		if (x == null) {
			return null;
		}
		Info leftInfo = process(x.left);
		Info rightInfo = process(x.right);
		// x 1)只有x 2）x往左扎 3）x往右扎
		int maxPathSumFromHead = x.val;
		if (leftInfo != null) {
			maxPathSumFromHead = Math.max(maxPathSumFromHead, x.val + leftInfo.maxPathSumFromHead);
		}
		if (rightInfo != null) {
			maxPathSumFromHead = Math.max(maxPathSumFromHead, x.val + rightInfo.maxPathSumFromHead);
		}
		// x整棵树最大路径和 1) 只有x 2)左树整体的最大路径和 3) 右树整体的最大路径和
		int maxPathSum = x.val;
		if (leftInfo != null) {
			maxPathSum = Math.max(maxPathSum, leftInfo.maxPathSum);
		}
		if (rightInfo != null) {
			maxPathSum = Math.max(maxPathSum, rightInfo.maxPathSum);
		}
		// 4) x只往左扎 5）x只往右扎
		maxPathSum = Math.max(maxPathSumFromHead, maxPathSum);
		// 6）一起扎
		if (leftInfo != null && rightInfo != null && leftInfo.maxPathSumFromHead > 0
				&& rightInfo.maxPathSumFromHead > 0) {
			maxPathSum = Math.max(maxPathSum, leftInfo.maxPathSumFromHead + rightInfo.maxPathSumFromHead + x.val);
		}
		return new Info(maxPathSum, maxPathSumFromHead);
	}

	// 如果要返回路径的做法
	public static List<TreeNode> getMaxSumPath(TreeNode head) {
		List<TreeNode> ans = new ArrayList<>();
		if (head != null) {
			Data data = f(head);
			HashMap<TreeNode, TreeNode> fmap = new HashMap<>();
			fmap.put(head, head);
			fatherMap(head, fmap);
			fillPath(fmap, data.from, data.to, ans);
		}
		return ans;
	}

	public static class Data {
		public int maxAllSum;
		public TreeNode from;
		public TreeNode to;
		public int maxHeadSum;
		public TreeNode end;

		public Data(int a, TreeNode b, TreeNode c, int d, TreeNode e) {
			maxAllSum = a;
			from = b;
			to = c;
			maxHeadSum = d;
			end = e;
		}
	}

	public static Data f(TreeNode x) {
		if (x == null) {
			return null;
		}
		Data l = f(x.left);
		Data r = f(x.right);
		int maxHeadSum = x.val;
		TreeNode end = x;
		if (l != null && l.maxHeadSum > 0 && (r == null || l.maxHeadSum > r.maxHeadSum)) {
			maxHeadSum += l.maxHeadSum;
			end = l.end;
		}
		if (r != null && r.maxHeadSum > 0 && (l == null || r.maxHeadSum > l.maxHeadSum)) {
			maxHeadSum += r.maxHeadSum;
			end = r.end;
		}
		int maxAllSum = Integer.MIN_VALUE;
		TreeNode from = null;
		TreeNode to = null;
		if (l != null) {
			maxAllSum = l.maxAllSum;
			from = l.from;
			to = l.to;
		}
		if (r != null && r.maxAllSum > maxAllSum) {
			maxAllSum = r.maxAllSum;
			from = r.from;
			to = r.to;
		}
		int p3 = x.val + (l != null && l.maxHeadSum > 0 ? l.maxHeadSum : 0)
				+ (r != null && r.maxHeadSum > 0 ? r.maxHeadSum : 0);
		if (p3 > maxAllSum) {
			maxAllSum = p3;
			from = (l != null && l.maxHeadSum > 0) ? l.end : x;
			to = (r != null && r.maxHeadSum > 0) ? r.end : x;
		}
		return new Data(maxAllSum, from, to, maxHeadSum, end);
	}

	public static void fatherMap(TreeNode h, HashMap<TreeNode, TreeNode> map) {
		if (h.left == null && h.right == null) {
			return;
		}
		if (h.left != null) {
			map.put(h.left, h);
			fatherMap(h.left, map);
		}
		if (h.right != null) {
			map.put(h.right, h);
			fatherMap(h.right, map);
		}
	}

	public static void fillPath(HashMap<TreeNode, TreeNode> fmap, TreeNode a, TreeNode b, List<TreeNode> ans) {
		if (a == b) {
			ans.add(a);
		} else {
			HashSet<TreeNode> ap = new HashSet<>();
			TreeNode cur = a;
			while (cur != fmap.get(cur)) {
				ap.add(cur);
				cur = fmap.get(cur);
			}
			ap.add(cur);
			cur = b;
			TreeNode lca = null;
			while (lca == null) {
				if (ap.contains(cur)) {
					lca = cur;
				} else {
					cur = fmap.get(cur);
				}
			}
			while (a != lca) {
				ans.add(a);
				a = fmap.get(a);
			}
			ans.add(lca);
			ArrayList<TreeNode> right = new ArrayList<>();
			while (b != lca) {
				right.add(b);
				b = fmap.get(b);
			}
			for (int i = right.size() - 1; i >= 0; i--) {
				ans.add(right.get(i));
			}
		}
	}

	public static void main(String[] args) {
		TreeNode head = new TreeNode(4);
		head.left = new TreeNode(-7);
		head.right = new TreeNode(-5);
		head.left.left = new TreeNode(9);
		head.left.right = new TreeNode(9);
		head.right.left = new TreeNode(4);
		head.right.right = new TreeNode(3);

		List<TreeNode> maxPath = getMaxSumPath(head);

		for (TreeNode n : maxPath) {
			System.out.println(n.val);
		}
	}

}