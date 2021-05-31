package class13;

import java.util.Stack;

// 本题测试链接 : https://leetcode.com/problems/recover-binary-search-tree/
public class Code02_RecoverBinarySearchTree {

	// 不要提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(int v) {
			val = v;
		}
	}

	// 如果能过leetcode，只需要提交这个方法即可
	// 但其实recoverTree2才是正路，只不过leetcode没有那么考
	public static void recoverTree(TreeNode root) {
		TreeNode[] errors = twoErrors(root);
		if (errors[0] != null && errors[1] != null) {
			int tmp = errors[0].val;
			errors[0].val = errors[1].val;
			errors[1].val = tmp;
		}
	}

	public static TreeNode[] twoErrors(TreeNode head) {
		TreeNode[] ans = new TreeNode[2];
		if (head == null) {
			return ans;
		}
		TreeNode cur = head;
		TreeNode mostRight = null;
		TreeNode pre = null;
		TreeNode e1 = null;
		TreeNode e2 = null;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				if (mostRight.right == null) {
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else {
					mostRight.right = null;
				}
			}
			if (pre != null && pre.val >= cur.val) {
				e1 = e1 == null ? pre : e1;
				e2 = cur;
			}
			pre = cur;
			cur = cur.right;
		}
		ans[0] = e1;
		ans[1] = e2;
		return ans;
	}

	// 以下的方法，提交leetcode是通过不了的，但那是因为leetcode的验证方式有问题
	// 但其实！以下的方法，才是正路！在结构上彻底交换两个节点，而不是值交换
	public static TreeNode recoverTree2(TreeNode head) {
		TreeNode[] errs = getTwoErrNodes(head);
		TreeNode[] parents = getTwoErrParents(head, errs[0], errs[1]);
		TreeNode e1 = errs[0];
		TreeNode e1P = parents[0];
		TreeNode e1L = e1.left;
		TreeNode e1R = e1.right;
		TreeNode e2 = errs[1];
		TreeNode e2P = parents[1];
		TreeNode e2L = e2.left;
		TreeNode e2R = e2.right;
		if (e1 == head) {
			if (e1 == e2P) {
				e1.left = e2L;
				e1.right = e2R;
				e2.right = e1;
				e2.left = e1L;
			} else if (e2P.left == e2) {
				e2P.left = e1;
				e2.left = e1L;
				e2.right = e1R;
				e1.left = e2L;
				e1.right = e2R;
			} else {
				e2P.right = e1;
				e2.left = e1L;
				e2.right = e1R;
				e1.left = e2L;
				e1.right = e2R;
			}
			head = e2;
		} else if (e2 == head) {
			if (e2 == e1P) {
				e2.left = e1L;
				e2.right = e1R;
				e1.left = e2;
				e1.right = e2R;
			} else if (e1P.left == e1) {
				e1P.left = e2;
				e1.left = e2L;
				e1.right = e2R;
				e2.left = e1L;
				e2.right = e1R;
			} else {
				e1P.right = e2;
				e1.left = e2L;
				e1.right = e2R;
				e2.left = e1L;
				e2.right = e1R;
			}
			head = e1;
		} else {
			if (e1 == e2P) {
				if (e1P.left == e1) {
					e1P.left = e2;
					e1.left = e2L;
					e1.right = e2R;
					e2.left = e1L;
					e2.right = e1;
				} else {
					e1P.right = e2;
					e1.left = e2L;
					e1.right = e2R;
					e2.left = e1L;
					e2.right = e1;
				}
			} else if (e2 == e1P) {
				if (e2P.left == e2) {
					e2P.left = e1;
					e2.left = e1L;
					e2.right = e1R;
					e1.left = e2;
					e1.right = e2R;
				} else {
					e2P.right = e1;
					e2.left = e1L;
					e2.right = e1R;
					e1.left = e2;
					e1.right = e2R;
				}
			} else {
				if (e1P.left == e1) {
					if (e2P.left == e2) {
						e1.left = e2L;
						e1.right = e2R;
						e2.left = e1L;
						e2.right = e1R;
						e1P.left = e2;
						e2P.left = e1;
					} else {
						e1.left = e2L;
						e1.right = e2R;
						e2.left = e1L;
						e2.right = e1R;
						e1P.left = e2;
						e2P.right = e1;
					}
				} else {
					if (e2P.left == e2) {
						e1.left = e2L;
						e1.right = e2R;
						e2.left = e1L;
						e2.right = e1R;
						e1P.right = e2;
						e2P.left = e1;
					} else {
						e1.left = e2L;
						e1.right = e2R;
						e2.left = e1L;
						e2.right = e1R;
						e1P.right = e2;
						e2P.right = e1;
					}
				}
			}
		}
		return head;
	}

	public static TreeNode[] getTwoErrNodes(TreeNode head) {
		TreeNode[] errs = new TreeNode[2];
		if (head == null) {
			return errs;
		}
		Stack<TreeNode> stack = new Stack<TreeNode>();
		TreeNode pre = null;
		while (!stack.isEmpty() || head != null) {
			if (head != null) {
				stack.push(head);
				head = head.left;
			} else {
				head = stack.pop();
				if (pre != null && pre.val > head.val) {
					errs[0] = errs[0] == null ? pre : errs[0];
					errs[1] = head;
				}
				pre = head;
				head = head.right;
			}
		}
		return errs;
	}

	public static TreeNode[] getTwoErrParents(TreeNode head, TreeNode e1, TreeNode e2) {
		TreeNode[] parents = new TreeNode[2];
		if (head == null) {
			return parents;
		}
		Stack<TreeNode> stack = new Stack<TreeNode>();
		while (!stack.isEmpty() || head != null) {
			if (head != null) {
				stack.push(head);
				head = head.left;
			} else {
				head = stack.pop();
				if (head.left == e1 || head.right == e1) {
					parents[0] = head;
				}
				if (head.left == e2 || head.right == e2) {
					parents[1] = head;
				}
				head = head.right;
			}
		}
		return parents;
	}

	// for test -- print tree
	public static void printTree(TreeNode head) {
		System.out.println("Binary Tree:");
		printInOrder(head, 0, "H", 17);
		System.out.println();
	}

	public static void printInOrder(TreeNode head, int height, String to, int len) {
		if (head == null) {
			return;
		}
		printInOrder(head.right, height + 1, "v", len);
		String val = to + head.val + to;
		int lenM = val.length();
		int lenL = (len - lenM) / 2;
		int lenR = len - lenM - lenL;
		val = getSpace(lenL) + val + getSpace(lenR);
		System.out.println(getSpace(height * len) + val);
		printInOrder(head.left, height + 1, "^", len);
	}

	public static String getSpace(int num) {
		String space = " ";
		StringBuffer buf = new StringBuffer("");
		for (int i = 0; i < num; i++) {
			buf.append(space);
		}
		return buf.toString();
	}

	// 为了测试
	public static boolean isBST(TreeNode head) {
		if (head == null) {
			return false;
		}
		Stack<TreeNode> stack = new Stack<TreeNode>();
		TreeNode pre = null;
		while (!stack.isEmpty() || head != null) {
			if (head != null) {
				stack.push(head);
				head = head.left;
			} else {
				head = stack.pop();
				if (pre != null && pre.val > head.val) {
					return false;
				}
				pre = head;
				head = head.right;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		TreeNode head = new TreeNode(5);
		head.left = new TreeNode(3);
		head.right = new TreeNode(7);
		head.left.left = new TreeNode(2);
		head.left.right = new TreeNode(4);
		head.right.left = new TreeNode(6);
		head.right.right = new TreeNode(8);
		head.left.left.left = new TreeNode(1);
		printTree(head);
		System.out.println(isBST(head));

		System.out.println("situation 1");
		TreeNode head1 = new TreeNode(7);
		head1.left = new TreeNode(3);
		head1.right = new TreeNode(5);
		head1.left.left = new TreeNode(2);
		head1.left.right = new TreeNode(4);
		head1.right.left = new TreeNode(6);
		head1.right.right = new TreeNode(8);
		head1.left.left.left = new TreeNode(1);
		printTree(head1);
		System.out.println(isBST(head1));
		TreeNode res1 = recoverTree2(head1);
		printTree(res1);
		System.out.println(isBST(res1));

		System.out.println("situation 2");
		TreeNode head2 = new TreeNode(6);
		head2.left = new TreeNode(3);
		head2.right = new TreeNode(7);
		head2.left.left = new TreeNode(2);
		head2.left.right = new TreeNode(4);
		head2.right.left = new TreeNode(5);
		head2.right.right = new TreeNode(8);
		head2.left.left.left = new TreeNode(1);
		printTree(head2);
		System.out.println(isBST(head2));
		TreeNode res2 = recoverTree2(head2);
		printTree(res2);
		System.out.println(isBST(res2));

		System.out.println("situation 3");
		TreeNode head3 = new TreeNode(8);
		head3.left = new TreeNode(3);
		head3.right = new TreeNode(7);
		head3.left.left = new TreeNode(2);
		head3.left.right = new TreeNode(4);
		head3.right.left = new TreeNode(6);
		head3.right.right = new TreeNode(5);
		head3.left.left.left = new TreeNode(1);
		printTree(head3);
		System.out.println(isBST(head3));
		TreeNode res3 = recoverTree2(head3);
		printTree(res3);
		System.out.println(isBST(res3));

		System.out.println("situation 4");
		TreeNode head4 = new TreeNode(3);
		head4.left = new TreeNode(5);
		head4.right = new TreeNode(7);
		head4.left.left = new TreeNode(2);
		head4.left.right = new TreeNode(4);
		head4.right.left = new TreeNode(6);
		head4.right.right = new TreeNode(8);
		head4.left.left.left = new TreeNode(1);
		printTree(head4);
		System.out.println(isBST(head4));
		TreeNode res4 = recoverTree2(head4);
		printTree(res4);
		System.out.println(isBST(res4));

		System.out.println("situation 5");
		TreeNode head5 = new TreeNode(2);
		head5.left = new TreeNode(3);
		head5.right = new TreeNode(7);
		head5.left.left = new TreeNode(5);
		head5.left.right = new TreeNode(4);
		head5.right.left = new TreeNode(6);
		head5.right.right = new TreeNode(8);
		head5.left.left.left = new TreeNode(1);
		printTree(head5);
		System.out.println(isBST(head5));
		TreeNode res5 = recoverTree2(head5);
		printTree(res5);
		System.out.println(isBST(res5));

		System.out.println("situation 6");
		TreeNode head6 = new TreeNode(4);
		head6.left = new TreeNode(3);
		head6.right = new TreeNode(7);
		head6.left.left = new TreeNode(2);
		head6.left.right = new TreeNode(5);
		head6.right.left = new TreeNode(6);
		head6.right.right = new TreeNode(8);
		head6.left.left.left = new TreeNode(1);
		printTree(head6);
		System.out.println(isBST(head6));
		TreeNode res6 = recoverTree2(head6);
		printTree(res6);
		System.out.println(isBST(res6));

		System.out.println("situation 7");
		TreeNode head7 = new TreeNode(5);
		head7.left = new TreeNode(4);
		head7.right = new TreeNode(7);
		head7.left.left = new TreeNode(2);
		head7.left.right = new TreeNode(3);
		head7.right.left = new TreeNode(6);
		head7.right.right = new TreeNode(8);
		head7.left.left.left = new TreeNode(1);
		printTree(head7);
		System.out.println(isBST(head7));
		TreeNode res7 = recoverTree2(head7);
		printTree(res7);
		System.out.println(isBST(res7));

		System.out.println("situation 8");
		TreeNode head8 = new TreeNode(5);
		head8.left = new TreeNode(3);
		head8.right = new TreeNode(8);
		head8.left.left = new TreeNode(2);
		head8.left.right = new TreeNode(4);
		head8.right.left = new TreeNode(6);
		head8.right.right = new TreeNode(7);
		head8.left.left.left = new TreeNode(1);
		printTree(head8);
		System.out.println(isBST(head8));
		TreeNode res8 = recoverTree2(head8);
		printTree(res8);
		System.out.println(isBST(res8));

		System.out.println("situation 9");
		TreeNode head9 = new TreeNode(5);
		head9.left = new TreeNode(2);
		head9.right = new TreeNode(7);
		head9.left.left = new TreeNode(3);
		head9.left.right = new TreeNode(4);
		head9.right.left = new TreeNode(6);
		head9.right.right = new TreeNode(8);
		head9.left.left.left = new TreeNode(1);
		printTree(head9);
		System.out.println(isBST(head9));
		TreeNode res9 = recoverTree2(head9);
		printTree(res9);
		System.out.println(isBST(res9));

		System.out.println("situation 10");
		TreeNode head10 = new TreeNode(5);
		head10.left = new TreeNode(3);
		head10.right = new TreeNode(6);
		head10.left.left = new TreeNode(2);
		head10.left.right = new TreeNode(4);
		head10.right.left = new TreeNode(7);
		head10.right.right = new TreeNode(8);
		head10.left.left.left = new TreeNode(1);
		printTree(head10);
		System.out.println(isBST(head10));
		TreeNode res10 = recoverTree2(head10);
		printTree(res10);
		System.out.println(isBST(res10));

		System.out.println("situation 11");
		TreeNode head11 = new TreeNode(5);
		head11.left = new TreeNode(3);
		head11.right = new TreeNode(7);
		head11.left.left = new TreeNode(6);
		head11.left.right = new TreeNode(4);
		head11.right.left = new TreeNode(2);
		head11.right.right = new TreeNode(8);
		head11.left.left.left = new TreeNode(1);
		printTree(head11);
		System.out.println(isBST(head11));
		TreeNode res11 = recoverTree2(head11);
		printTree(res11);
		System.out.println(isBST(res11));

		System.out.println("situation 12");
		TreeNode head12 = new TreeNode(5);
		head12.left = new TreeNode(3);
		head12.right = new TreeNode(7);
		head12.left.left = new TreeNode(8);
		head12.left.right = new TreeNode(4);
		head12.right.left = new TreeNode(6);
		head12.right.right = new TreeNode(2);
		head12.left.left.left = new TreeNode(1);
		printTree(head12);
		System.out.println(isBST(head12));
		TreeNode res12 = recoverTree2(head12);
		printTree(res12);
		System.out.println(isBST(res12));

		System.out.println("situation 13");
		TreeNode head13 = new TreeNode(5);
		head13.left = new TreeNode(3);
		head13.right = new TreeNode(7);
		head13.left.left = new TreeNode(2);
		head13.left.right = new TreeNode(6);
		head13.right.left = new TreeNode(4);
		head13.right.right = new TreeNode(8);
		head13.left.left.left = new TreeNode(1);
		printTree(head13);
		System.out.println(isBST(head13));
		TreeNode res13 = recoverTree2(head13);
		printTree(res13);
		System.out.println(isBST(res13));

		System.out.println("situation 14");
		TreeNode head14 = new TreeNode(5);
		head14.left = new TreeNode(3);
		head14.right = new TreeNode(7);
		head14.left.left = new TreeNode(2);
		head14.left.right = new TreeNode(8);
		head14.right.left = new TreeNode(6);
		head14.right.right = new TreeNode(4);
		head14.left.left.left = new TreeNode(1);
		printTree(head14);
		System.out.println(isBST(head14));
		TreeNode res14 = recoverTree2(head14);
		printTree(res14);
		System.out.println(isBST(res14));
	}

}
