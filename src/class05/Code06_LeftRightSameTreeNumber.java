package class05;

import java.util.HashMap;

// 如果一个节点X，它左树结构和右树结构完全一样，那么我们说以X为头的子树是相等子树
// 给定一棵二叉树的头节点head，返回head整棵树上有多少棵相等子树
public class Code06_LeftRightSameTreeNumber {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int v) {
			value = v;
		}
	}

	// 时间复杂度O(N^2)
	public static int sameNumber1(Node head) {
		if (head == null) {
			return 0;
		}
		return sameNumber1(head.left) + sameNumber2(head.right) + (same(head.left, head.right) ? 1 : 0);
	}

	public static boolean same(Node h1, Node h2) {
		if (h1 == null ^ h2 == null) {
			return false;
		}
		if (h1 == null && h2 == null) {
			return true;
		}
		return h1.value == h2.value && same(h1.left, h2.left) && same(h1.right, h2.right);
	}

	// 时间复杂度O(N*logN)
	public static int sameNumber2(Node head) {
		if (head == null) {
			return 0;
		}
		HashMap<Node, Integer> numberMap = new HashMap<>();
		int N = createNumberMap(head, numberMap);
		String[] arr = new String[N];
		return process(head, 0, N - 1, arr, numberMap);
	}

	public static int createNumberMap(Node head, HashMap<Node, Integer> numberMap) {
		if (head == null) {
			return 1;
		}
		int size = createNumberMap(head.left, numberMap) + createNumberMap(head.right, numberMap) + 1;
		numberMap.put(head, size);
		return size;
	}

	public static int process(Node head, int L, int R, String[] arr, HashMap<Node, Integer> numberMap) {
		int ans = 0;
		if (head == null) {
			arr[L] = "#";
		} else {
			arr[L] = String.valueOf(head.value);
			int leftR = L + (head.left == null ? 1 : numberMap.get(head.left));
			int leftAns = process(head.left, L + 1, leftR, arr, numberMap);
			int rightAns = process(head.right, leftR + 1, R, arr, numberMap);
			ans = leftAns + rightAns + (equalTree(arr, L + 1, leftR, leftR + 1, R) ? 1 : 0);
		}
		return ans;
	}

	public static boolean equalTree(String[] arr, int leftL, int leftR, int rightL, int rightR) {
		if (leftR - leftL != rightR - rightL) {
			return false;
		}
		for (; leftL <= leftR; leftL++, rightL++) {
			if (!arr[leftL].equals(arr[rightL])) {
				return false;
			}
		}
		return true;
	}

	// 时间复杂度O(N)
	public static int sameNumber3(Node head) {
		String algorithm = "SHA-256";
		Hash hash = new Hash(algorithm);
		return process(head, hash).ans;
	}

	public static class Info {
		public int ans;
		public String str;

		public Info(int a, String s) {
			ans = a;
			str = s;
		}
	}

	public static Info process(Node head, Hash hash) {
		if (head == null) {
			return new Info(0, hash.hashCode("#,"));
		}
		Info l = process(head.left, hash);
		Info r = process(head.right, hash);
		int ans = (l.str.equals(r.str) ? 1 : 0) + l.ans + r.ans;
		String str = hash.hashCode(String.valueOf(head.value) + "," + l.str + r.str);
		return new Info(ans, str);
	}

	public static Node randomBinaryTree(int restLevel, int maxValue) {
		if (restLevel == 0) {
			return null;
		}
		Node head = Math.random() < 0.2 ? null : new Node((int) (Math.random() * maxValue));
		if (head != null) {
			head.left = randomBinaryTree(restLevel - 1, maxValue);
			head.right = randomBinaryTree(restLevel - 1, maxValue);
		}
		return head;
	}

	public static void main(String[] args) {
		int maxLevel = 8;
		int maxValue = 4;
		int testTime = 100000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			Node head = randomBinaryTree(maxLevel, maxValue);
			int ans1 = sameNumber1(head);
			int ans2 = sameNumber2(head);
			int ans3 = sameNumber3(head);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("出错了！");
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
			}
		}
		System.out.println("测试结束");

	}

}
