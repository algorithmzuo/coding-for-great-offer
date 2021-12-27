package class37;

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
		public int no;
		public int yes;

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
		int no = Math.max(leftInfo.no, leftInfo.yes) + Math.max(rightInfo.no, rightInfo.yes);
		int yes = x.val + leftInfo.no + rightInfo.no;
		return new Info(no, yes);
	}

}
