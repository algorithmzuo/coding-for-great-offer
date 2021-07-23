package class26;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class Code01_MinRange {

	// 本题为求最小包含区间
	// 测试链接 :
	// https://leetcode.com/problems/smallest-range-covering-elements-from-k-lists/
	public static class Node {
		public int val;
		public int arr;
		public int idx;

		public Node(int value, int arrIndex, int index) {
			val = value;
			arr = arrIndex;
			idx = index;
		}
	}

	public static class NodeComparator implements Comparator<Node> {

		@Override
		public int compare(Node a, Node b) {
			return a.val != b.val ? (a.val - b.val) : (a.arr - b.arr);
		}

	}

	public static int[] smallestRange(List<List<Integer>> nums) {
		int N = nums.size();
		TreeSet<Node> set = new TreeSet<>(new NodeComparator());
		for (int i = 0; i < N; i++) {
			set.add(new Node(nums.get(i).get(0), i, 0));
		}
		int r = Integer.MAX_VALUE;
		int a = 0;
		int b = 0;
		while (set.size() == N) {
			Node max = set.last();
			Node min = set.pollFirst();
			if (max.val - min.val < r) {
				r = max.val - min.val;
				a = min.val;
				b = max.val;
			}
			if (min.idx < nums.get(min.arr).size() - 1) {
				set.add(new Node(nums.get(min.arr).get(min.idx + 1), min.arr, min.idx + 1));
			}
		}
		return new int[] { a, b };
	}

	// 以下为课堂题目，在main函数里有对数器
	public static int minRange1(int[][] m) {
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < m[0].length; i++) {
			for (int j = 0; j < m[1].length; j++) {
				for (int k = 0; k < m[2].length; k++) {
					min = Math.min(min,
							Math.abs(m[0][i] - m[1][j]) + Math.abs(m[1][j] - m[2][k]) + Math.abs(m[2][k] - m[0][i]));
				}
			}
		}
		return min;
	}

	public static int minRange2(int[][] matrix) {
		int N = matrix.length;
		TreeSet<Node> set = new TreeSet<>(new NodeComparator());
		for (int i = 0; i < N; i++) {
			set.add(new Node(matrix[i][0], i, 0));
		}
		int min = Integer.MAX_VALUE;
		while (set.size() == N) {
			min = Math.min(min, set.last().val - set.first().val);
			Node cur = set.pollFirst();
			if (cur.idx < matrix[cur.arr].length - 1) {
				set.add(new Node(matrix[cur.arr][cur.idx + 1], cur.arr, cur.idx + 1));
			}
		}
		return min << 1;
	}

	public static int[][] generateRandomMatrix(int n, int v) {
		int[][] m = new int[3][];
		int s = 0;
		for (int i = 0; i < 3; i++) {
			s = (int) (Math.random() * n) + 1;
			m[i] = new int[s];
			for (int j = 0; j < s; j++) {
				m[i][j] = (int) (Math.random() * v);
			}
			Arrays.sort(m[i]);
		}
		return m;
	}

	public static void main(String[] args) {
		int n = 20;
		int v = 200;
		int t = 1000000;
		System.out.println("测试开始");
		for (int i = 0; i < t; i++) {
			int[][] m = generateRandomMatrix(n, v);
			int ans1 = minRange1(m);
			int ans2 = minRange2(m);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}
