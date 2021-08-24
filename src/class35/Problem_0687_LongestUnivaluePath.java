package class35;

public class Problem_0687_LongestUnivaluePath {

	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(int v) {
			val = v;
		}
	}

	public static int longestUnivaluePath(TreeNode root) {
		if (root == null) {
			return 0;
		}
		return process(root).max - 1;
	}

	// 建设以x节点为头的树，返回两个信息
	public static class Info {
		// 在一条路径上：要求每个节点通过且只通过一遍
		public int len; // 路径必须从x出发且只能往下走的情况下，路径的最大距离
		public int max; // 路径不要求必须从x出发的情况下，整棵树的合法路径最大距离

		public Info(int l, int m) {
			len = l;
			max = m;
		}
	}

	private static Info process(TreeNode x) {
		if (x == null) {
			return new Info(0, 0);
		}
		TreeNode l = x.left;
		TreeNode r = x.right;
		// 左树上，不要求从左孩子出发，最大路径
		// 左树上，必须从左孩子出发，往下的最大路径
		Info linfo = process(l);
		// 右树上，不要求从右孩子出发，最大路径
		// 右树上，必须从右孩子出发，往下的最大路径
		Info rinfo = process(r);
		// 必须从x出发的情况下，往下的最大路径
		int len = 1;
		if (l != null && l.val == x.val) {
			len = linfo.len + 1;
		}
		if (r != null && r.val == x.val) {
			len = Math.max(len, rinfo.len + 1);
		}
		// 不要求从x出发，最大路径
		int max = Math.max(Math.max(linfo.max, rinfo.max), len);
		if (l != null && r != null && l.val == x.val && r.val == x.val) {
			max = Math.max(max, linfo.len + rinfo.len + 1);
		}
		return new Info(len, max);
	}

}
