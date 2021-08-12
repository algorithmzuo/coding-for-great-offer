package class35;

public class Problem_0337_HouseRobberIII {

	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	public static int rob(TreeNode root) {
		Info info = process(root);
		return Math.max(info.no, info.yes);
	}

	public static class Info {
		public int no;  // 整个子树，在不抢头节点的情况下，获得的最好收益
		public int yes; // 整个子树，在抢头节点的情况下，获得的最好收益

		public Info(int n, int y) {
			no = n;
			yes = y;
		}
	}

	public static Info process(TreeNode x) {
		if (x == null) {
			return new Info(0, 0);
		}
		Info leftInfo = process(x.left);
		Info rightInfo = process(x.right);
		// 不抢x的情况下，最好收
		int no = Math.max(leftInfo.no, leftInfo.yes) + Math.max(rightInfo.no, rightInfo.yes);
		// 抢x的情况下，最好收益
		int yes = x.val + leftInfo.no + rightInfo.no;
		return new Info(no, yes);
	}

}
