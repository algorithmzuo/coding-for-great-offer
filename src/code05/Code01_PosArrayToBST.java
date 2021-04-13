package code05;

import java.util.ArrayList;

public class Code01_PosArrayToBST {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int v) {
			value = v;
		}
	}

	public static Node posArrayToBST1(int[] posArr) {
		// 0~N-1  
		return process1(posArr, 0, posArr.length - 1);
	}

	// 目前，我们在使用posArr[L..R]这些数字，来建树
	// 建出的每一个节点都连好，然后把整棵树的头节点返回
	public static Node process1(int[] posArr, int L, int R) {
		if (L > R) {
			return null;
		}
		Node head = new Node(posArr[R]);
		if (L == R) {
			return head;
		}
		int M = L - 1;
		for (int i = L; i < R; i++) { // i -> L..R-1
			if (posArr[i] < posArr[R]) {
				M = i;
			}
		}
		head.left = process1(posArr, L, M);
		head.right = process1(posArr, M + 1, R - 1);
		return head;
	}
	
	
	public static Node process3(int[] posArr, int L, int R) {
		Node head = new Node(posArr[R]);
		if (L == R) {
			return head;
		}
		int M = -1;
		for (int i = L; i < R; i++) { // i -> L..R-1
			if (posArr[i] < posArr[R]) {
				M = i;
			}
		}
		// >
		if(M==-1) {
			head.right = process3(posArr, L, R - 1);
		}else if(M == R-1) {
			head.left = process3(posArr, L, R - 1);
		}else {
			head.left = process3(posArr, L, M);
			head.right = process3(posArr, M + 1, R - 1);
		}
		return head;
	}
	
	
	
	
	
	
	
	
	

	public static Node posArrayToBST2(int[] posArr) {
		return process2(posArr, 0, posArr.length - 1);
	}

	public static Node process2(int[] posArr, int L, int R) {
		if (L > R) {
			return null;
		}
		Node head = new Node(posArr[R]);
		if (L == R) {
			return head;
		}
		
		int M = L - 1;
		int left = L;
		int right = R - 1;
		while (left <= right) {
			int mid = left + ((right - left) >> 1);
			if (posArr[mid] < posArr[R]) {
				M = mid;
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		head.left = process2(posArr, L, M);
		head.right = process2(posArr, M + 1, R - 1);
		return head;
	}

	// for test
	public static int[] getBstPosArray(Node head) {
		ArrayList<Integer> posList = new ArrayList<>();
		pos(head, posList);
		int[] ans = new int[posList.size()];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = posList.get(i);
		}
		return ans;
	}

	// for test
	public static void pos(Node head, ArrayList<Integer> posList) {
		if (head != null) {
			pos(head.left, posList);
			pos(head.right, posList);
			posList.add(head.value);
		}
	}

	// for test [0~1000]
	// N
	public static Node generateRandomBST(int min, int max, int N) {
		if (min > max) {
			return null;
		}
		return createTree(min, max, 1, N);
	}

	// for test
	public static Node createTree(int min, int max, int level, int N) {
		if (min > max || level > N) {
			return null;
		}
		Node head = new Node(random(min, max));
		head.left = createTree(min, head.value - 1, level + 1, N);
		head.right = createTree(head.value + 1, max, level + 1, N);
		return head;
	}

	// for test
	public static int random(int min, int max) {
		return min + (int) (Math.random() * (max - min + 1));
	}

	// for test
	public static boolean isSameValueStructure(Node head1, Node head2) {
		if (head1 == null && head2 != null) {
			return false;
		}
		if (head1 != null && head2 == null) {
			return false;
		}
		if (head1 == null && head2 == null) {
			return true;
		}
		return head1.value == head2.value && isSameValueStructure(head1.left, head2.left)
				&& isSameValueStructure(head1.right, head2.right);
	}

	// for test
	public static void printTree(Node head) {
		System.out.println("Binary Tree:");
		printInOrder(head, 0, "H", 17);
		System.out.println();
	}

	// for test
	public static void printInOrder(Node head, int height, String to, int len) {
		if (head == null) {
			return;
		}
		printInOrder(head.right, height + 1, "v", len);
		String val = to + head.value + to;
		int lenM = val.length();
		int lenL = (len - lenM) / 2;
		int lenR = len - lenM - lenL;
		val = getSpace(lenL) + val + getSpace(lenR);
		System.out.println(getSpace(height * len) + val);
		printInOrder(head.left, height + 1, "^", len);
	}

	// for test
	public static String getSpace(int num) {
		String space = " ";
		StringBuffer buf = new StringBuffer("");
		for (int i = 0; i < num; i++) {
			buf.append(space);
		}
		return buf.toString();
	}

	// for test
	public static void main(String[] args) {
		int min = 0;
		int max = 12;
		int level = 4;
		Node head = generateRandomBST(min, max, level);
		int[] pos = getBstPosArray(head);
		printTree(head);
		printTree(posArrayToBST1(pos));
		printTree(posArrayToBST2(pos));
		System.out.println(isSameValueStructure(head, posArrayToBST1(pos)));
		System.out.println(isSameValueStructure(head, posArrayToBST2(pos)));

		int testTimes = 5000000;
		System.out.println("test begin, time time : " + testTimes);
		for (int i = 0; i < testTimes; i++) {
			head = generateRandomBST(min, max, level);
			pos = getBstPosArray(head);
			if (!isSameValueStructure(head, posArrayToBST1(pos)) || !isSameValueStructure(head, posArrayToBST2(pos))) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test finish");

	}

}
