package class20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Code01_PreAndInArrayToPosArray {

	public static int[] zuo(int[] pre, int[] in) {
		if (pre == null || in == null || pre.length != in.length) {
			return null;
		}
		int N = pre.length;
		HashMap<Integer, Integer> inMap = new HashMap<>();
		for (int i = 0; i < N; i++) {
			inMap.put(in[i], i);
		}
		int[] pos = new int[N];
		func(pre, 0, N - 1, in, 0, N - 1, pos, 0, N - 1, inMap);
		return pos;
	}

	public static void func(int[] pre, int L1, int R1, int[] in, int L2, int R2, int[] pos, int L3, int R3,
			HashMap<Integer, Integer> inMap) {
		if (L1 > R1) {
			return;
		}
		if (L1 == R1) {
			pos[L3] = pre[L1];
		} else {
			pos[R3] = pre[L1];
			int index = inMap.get(pre[L1]);
			func(pre, L1 + 1, L1 + index - L2, in, L2, index - 1, pos, L3, L3 + index - L2 - 1, inMap);
			func(pre, L1 + index - L2 + 1, R1, in, index + 1, R2, pos, L3 + index - L2, R3 - 1, inMap);
		}
	}

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int v) {
			value = v;
		}
	}

	public static int[] preInToPos1(int[] pre, int[] in) {
		if (pre == null || in == null || pre.length != in.length) {
			return null;
		}
		int N = pre.length;
		int[] pos = new int[N];
		process1(pre, 0, N - 1, in, 0, N - 1, pos, 0, N - 1);
		return pos;
	}

	// L1...R1 L2...R2 L3...R3
	public static void process1(int[] pre, int L1, int R1, int[] in, int L2, int R2, int[] pos, int L3, int R3) {
		if (L1 > R1) {
			return;
		}
		if (L1 == R1) {
			pos[L3] = pre[L1];
			return;
		}
		pos[R3] = pre[L1];
		int mid = L2;
		for (; mid <= R2; mid++) {
			if (in[mid] == pre[L1]) {
				break;
			}
		}
		int leftSize = mid - L2;
		process1(pre, L1 + 1, L1 + leftSize, in, L2, mid - 1, pos, L3, L3 + leftSize - 1);
		process1(pre, L1 + leftSize + 1, R1, in, mid + 1, R2, pos, L3 + leftSize, R3 - 1);
	}

	public static int[] preInToPos2(int[] pre, int[] in) {
		if (pre == null || in == null || pre.length != in.length) {
			return null;
		}
		int N = pre.length;
		HashMap<Integer, Integer> inMap = new HashMap<>();
		for (int i = 0; i < N; i++) {
			inMap.put(in[i], i);
		}
		int[] pos = new int[N];
		process2(pre, 0, N - 1, in, 0, N - 1, pos, 0, N - 1, inMap);
		return pos;
	}

	public static void process2(int[] pre, int L1, int R1, int[] in, int L2, int R2, int[] pos, int L3, int R3,
			HashMap<Integer, Integer> inMap) {
		if (L1 > R1) {
			return;
		}
		if (L1 == R1) {
			pos[L3] = pre[L1];
			return;
		}
		pos[R3] = pre[L1];
		int mid = inMap.get(pre[L1]);
		int leftSize = mid - L2;
		process2(pre, L1 + 1, L1 + leftSize, in, L2, mid - 1, pos, L3, L3 + leftSize - 1, inMap);
		process2(pre, L1 + leftSize + 1, R1, in, mid + 1, R2, pos, L3 + leftSize, R3 - 1, inMap);
	}

	// for test
	public static int[] getPreArray(Node head) {
		ArrayList<Integer> arr = new ArrayList<>();
		fillPreArray(head, arr);
		int[] ans = new int[arr.size()];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = arr.get(i);
		}
		return ans;
	}

	// for test
	public static void fillPreArray(Node head, ArrayList<Integer> arr) {
		if (head == null) {
			return;
		}
		arr.add(head.value);
		fillPreArray(head.left, arr);
		fillPreArray(head.right, arr);
	}

	// for test
	public static int[] getInArray(Node head) {
		ArrayList<Integer> arr = new ArrayList<>();
		fillInArray(head, arr);
		int[] ans = new int[arr.size()];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = arr.get(i);
		}
		return ans;
	}

	// for test
	public static void fillInArray(Node head, ArrayList<Integer> arr) {
		if (head == null) {
			return;
		}
		fillInArray(head.left, arr);
		arr.add(head.value);
		fillInArray(head.right, arr);
	}

	// for test
	public static int[] getPosArray(Node head) {
		ArrayList<Integer> arr = new ArrayList<>();
		fillPostArray(head, arr);
		int[] ans = new int[arr.size()];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = arr.get(i);
		}
		return ans;
	}

	// for test
	public static void fillPostArray(Node head, ArrayList<Integer> arr) {
		if (head == null) {
			return;
		}
		fillPostArray(head.left, arr);
		fillPostArray(head.right, arr);
		arr.add(head.value);
	}

	// for test
	public static Node generateRandomTree(int value, int maxLevel) {
		HashSet<Integer> hasValue = new HashSet<Integer>();
		return createTree(value, 1, maxLevel, hasValue);
	}

	// for test
	public static Node createTree(int value, int level, int maxLevel, HashSet<Integer> hasValue) {
		if (level > maxLevel) {
			return null;
		}
		int cur = 0;
		do {
			cur = (int) (Math.random() * value) + 1;
		} while (hasValue.contains(cur));
		hasValue.add(cur);
		Node head = new Node(cur);
		head.left = createTree(value, level + 1, maxLevel, hasValue);
		head.right = createTree(value, level + 1, maxLevel, hasValue);
		return head;
	}

	// for test
	public static boolean isEqual(int[] arr1, int[] arr2) {
		if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
			return false;
		}
		if (arr1 == null && arr2 == null) {
			return true;
		}
		if (arr1.length != arr2.length) {
			return false;
		}
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		System.out.println("test begin");
		int maxLevel = 5;
		int value = 1000;
		int testTime = 100000;
		for (int i = 0; i < testTime; i++) {
			Node head = generateRandomTree(value, maxLevel);
			int[] pre = getPreArray(head);
			int[] in = getInArray(head);
			int[] pos = getPosArray(head);
			int[] ans1 = preInToPos1(pre, in);
			int[] ans2 = preInToPos2(pre, in);
			int[] classAns = zuo(pre, in);
			if (!isEqual(pos, ans1) || !isEqual(ans1, ans2) || !isEqual(pos, classAns)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test end");

	}
}
