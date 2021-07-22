package class29;

import java.util.HashSet;
import java.util.List;

public class Problem_0139_WordBreak {

	public static boolean wordBreak(String s, List<String> wordDict) {
		Node root = new Node();
		for (String str : wordDict) {
			char[] chs = str.toCharArray();
			Node node = root;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = chs[i] - 'a';
				if (node.nexts[index] == null) {
					node.nexts[index] = new Node();
				}
				node = node.nexts[index];
			}
			node.end = true;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		boolean[] dp = new boolean[N + 1];
		dp[N] = true;
		for (int i = N - 1; i >= 0; i--) {
			Node cur = root;
			for (int end = i; end < N; end++) {
				int path = str[end] - 'a';
				if (cur.nexts[path] == null) {
					break;
				}
				cur = cur.nexts[path];
				if (cur.end && dp[end + 1]) {
					dp[i] = true;
					break;
				}
			}
		}
		return dp[0];
	}

	public static boolean wordBreak2(String s, List<String> wordDict) {
		return process(s, 0, new HashSet<>(wordDict)) != 0;
	}

	// s[0......index-1]这一段，已经分解过了，不用在操心
	// s[index.....] 这一段字符串，能够被分解的方法数，返回
	public static int process(String s, int index, HashSet<String> wordDict) {
		if (index == s.length()) {
			return 1;
		}
		// index没到最后
		// index...index
		// index...index+1
		// index....index+2
		// index....end
		int ways = 0;
		for (int end = index; end < s.length(); end++) {
			// s[index....end]
			String pre = s.substring(index, end + 1);
			if (wordDict.contains(pre)) {
				ways += process(s, end + 1, wordDict);
			}
		}
		return ways;
	}

	public static boolean wordBreak3(String s, List<String> wordDict) {
		HashSet<String> set = new HashSet<>(wordDict);
		int N = s.length();
		int[] dp = new int[N + 1];
		// dp[i] = process(s, i, set)的返回值
		dp[N] = 1;
		for (int index = N - 1; index >= 0; index--) {
			int ways = 0;
			for (int end = index; end < s.length(); end++) {
				// s[index....end]
				String pre = s.substring(index, end + 1);
				if (set.contains(pre)) {
					ways += dp[end + 1];
				}
			}
			dp[index] = ways;
		}
		return dp[0] != 0;
	}

	public static class Node {
		public boolean end;
		public Node[] nexts;

		public Node() {
			end = false;
			nexts = new Node[26];
		}
	}

	public static boolean wordBreak4(String s, List<String> wordDict) {
		Node root = new Node();
		for (String str : wordDict) {
			char[] chs = str.toCharArray();
			Node node = root;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = chs[i] - 'a';
				if (node.nexts[index] == null) {
					node.nexts[index] = new Node();
				}
				node = node.nexts[index];
			}
			node.end = true;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		int[] dp = new int[N + 1];
		dp[N] = 1;
		for (int i = N - 1; i >= 0; i--) {
			Node cur = root;
			for (int end = i; end < N; end++) {
				cur = cur.nexts[str[end] - 'a'];
				if (cur == null) {
					break;
				}
				if (cur.end) {
					dp[i] += dp[end + 1];
				}
			}
		}
		return dp[0] != 0;
	}

}
