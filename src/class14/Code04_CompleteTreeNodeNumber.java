package class14;

public class Code04_CompleteTreeNodeNumber {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	// 请保证head为头的树，是完全二叉树
	public static int nodeNum(Node head) {
		if (head == null) {
			return 0;
		}
		return bs(head, 1, mostLeftLevel(head, 1));
	}

	// 当前来到node节点，node节点在level层，总层数是h
	// 返回node为头的子树(必是完全二叉树)，有多少个节点
	public static int bs(Node node, int Level, int h) {
		if (Level == h) {
			return 1;
		}
		if (mostLeftLevel(node.right, Level + 1) == h) {
			return (1 << (h - Level)) + bs(node.right, Level + 1, h);
		} else {
			return (1 << (h - Level - 1)) + bs(node.left, Level + 1, h);
		}
	}

	// 如果node在第level层，
	// 求以node为头的子树，最大深度是多少
	// node为头的子树，一定是完全二叉树
	public static int mostLeftLevel(Node node, int level) {
		while (node != null) {
			level++;
			node = node.left;
		}
		return level - 1;
	}

	public static void main(String[] args) {
		Node head = new Node(1);
		head.left = new Node(2);
		head.right = new Node(3);
		head.left.left = new Node(4);
		head.left.right = new Node(5);
		head.right.left = new Node(6);
		System.out.println(nodeNum(head));

	}

}
