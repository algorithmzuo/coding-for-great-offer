package class06;

// 测试链接 : https://leetcode.com/problems/maximum-xor-with-an-element-from-array/
public class Code03_MaximumXorWithAnElementFromArray {

	public static class Node {
		public int min;
		public Node[] nexts;

		public Node() {
			min = Integer.MAX_VALUE;
			nexts = new Node[2];
		}
	}

	public static class NumTrie {
		public Node head = new Node();

		public void add(int num) {
			Node cur = head;
			head.min = Math.min(head.min, num);
			for (int move = 30; move >= 0; move--) {
				int path = ((num >> move) & 1);
				cur.nexts[path] = cur.nexts[path] == null ? new Node() : cur.nexts[path];
				cur = cur.nexts[path];
				cur.min = Math.min(cur.min, num);
			}
		}

		public int maxXorWithXiBehindMi(int xi, int mi) {
			Node cur = head;
			int ans = 0;
			for (int move = 30; move >= 0; move--) {
				int path = (xi >> move) & 1;
				int best = (path ^ 1);
				if (cur.nexts[best] != null && cur.nexts[best].min <= mi) {
					ans |= 1 << move;
					cur = cur.nexts[best];
				} else if (cur.nexts[path] != null && cur.nexts[path].min <= mi) {
					cur = cur.nexts[path];
				} else {
					return -1;
				}
			}
			return ans;
		}
	}

	public static int[] maximizeXor(int[] nums, int[][] queries) {
		int N = nums.length;
		NumTrie trie = new NumTrie();
		for (int i = 0; i < N; i++) {
			trie.add(nums[i]);
		}
		int M = queries.length;
		int[] ans = new int[M];
		for (int i = 0; i < M; i++) {
			ans[i] = trie.maxXorWithXiBehindMi(queries[i][0], queries[i][1]);
		}
		return ans;
	}

}
