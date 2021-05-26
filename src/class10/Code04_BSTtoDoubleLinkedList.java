package class10;

// 本题测试链接 : https://leetcode.com/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/
public class Code04_BSTtoDoubleLinkedList {

	// 提交时不要提交这个类
	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	// 提交下面的代码
	public static Node treeToDoublyList(Node head) {
		if (head == null) {
			return null;
		}
		Info allInfo = process(head);
		allInfo.end.right = allInfo.start;
		allInfo.start.left = allInfo.end;
		return allInfo.start;
	}

	public static class Info {
		public Node start;
		public Node end;

		public Info(Node start, Node end) {
			this.start = start;
			this.end = end;
		}
	}

	public static Info process(Node X) {
		if (X == null) {
			return new Info(null, null);
		}
		Info lInfo = process(X.left);
		Info rInfo = process(X.right);
		if (lInfo.end != null) {
			lInfo.end.right = X;
		}
		X.left = lInfo.end;
		X.right = rInfo.start;
		if (rInfo.start != null) {
			rInfo.start.left = X;
		}
		// 整体链表的头    lInfo.start != null ? lInfo.start : X
		// 整体链表的尾    rInfo.end != null ? rInfo.end : X
		return new Info(lInfo.start != null ? lInfo.start : X, rInfo.end != null ? rInfo.end : X);
	}

}