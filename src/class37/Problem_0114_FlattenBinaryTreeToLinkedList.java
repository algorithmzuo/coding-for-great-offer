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
