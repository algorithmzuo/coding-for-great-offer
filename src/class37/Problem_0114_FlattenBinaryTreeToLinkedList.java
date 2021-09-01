package class37;

// 利用morris遍历
public class Problem_0114_FlattenBinaryTreeToLinkedList {

	// 这个类不用提交
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(int value) {
			val = value;
		}
	}

	// 普通解
	public static TreeNode convert(TreeNode head) {
		if (head == null) {
			return null;
		}
		return process(head).head;
	}

	public static class Info {
		public TreeNode head;
		public TreeNode tail;

		public Info(TreeNode h, TreeNode t) {
			head = h;
			tail = t;
		}
	}

	public static Info process(TreeNode x) {
		if (x == null) {
			return null;
		}
		Info leftInfo = process(x.left);
		Info rightInfo = process(x.right);
		// 2...4 5 6...13
		if (leftInfo != null) {
			leftInfo.tail.right = x;
			x.left = leftInfo.tail;
		}
		if (rightInfo != null) {
			x.right = rightInfo.head;
			rightInfo.head.left = x;
		}
		TreeNode head = leftInfo != null ? leftInfo.head : x;
		TreeNode tail = rightInfo != null ? rightInfo.tail : x;
		return new Info(head, tail);
	}

	public static void flatten(TreeNode root) {
		if (root == null) {
			return;
		}
		TreeNode pre = null;
		TreeNode cur = root;
		TreeNode mostRight = null;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				if (mostRight.right == null) {
					mostRight.right = cur;
					if (pre != null) {
						pre.left = cur;
					}
					pre = cur;
					cur = cur.left;
					continue;
				} else {
					mostRight.right = null;
				}
			} else {
				if (pre != null) {
					pre.left = cur;
				}
				pre = cur;
			}
			cur = cur.right;
		}
		cur = root;
		TreeNode next = null;
		while (cur != null) {
			next = cur.left;
			cur.left = null;
			cur.right = next;
			cur = next;
		}
	}

	public static void main(String[] args) {
		TreeNode head = new TreeNode(1);
		head.left = new TreeNode(2);
		head.left.left = new TreeNode(3);
		head.left.right = new TreeNode(4);
		head.right = new TreeNode(5);
		head.right.left = new TreeNode(6);
		head.right.right = new TreeNode(7);

		flatten(head);

		while (head != null) {
			System.out.println(head.val);
			System.out.println(head.left);
			head = head.right;
		}
	}

}
