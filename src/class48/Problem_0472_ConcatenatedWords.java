package class48;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Problem_0472_ConcatenatedWords {

	public static List<String> findAllConcatenatedWordsInADict1(String[] words) {
		List<String> ans = new ArrayList<>();
		if (words == null || words.length < 3) {
			return ans;
		}
		Arrays.sort(words, (str1, str2) -> str1.length() - str2.length());
		TrieNode root = new TrieNode();
		int[] dp = new int[1000];
		for (String str : words) {
			char[] s = str.toCharArray();
			Arrays.fill(dp, 0, s.length + 1, 0);
			if (s.length > 0 && split1(s, root, 0, dp)) {
				ans.add(str);
			} else {
				insert(root, s);
			}
		}
		return ans;
	}

	public static List<String> findAllConcatenatedWordsInADict2(String[] words) {
		List<String> ans = new ArrayList<>();
		if (words == null || words.length < 3) {
			return ans;
		}
		Arrays.sort(words, (str1, str2) -> str1.length() - str2.length());
		TrieNode root = new TrieNode();
		for (String str : words) {
			char[] s = str.toCharArray();
			if (s.length > 0 && split2(s, root, 0)) {
				ans.add(str);
			} else {
				insert(root, s);
			}
		}
		return ans;
	}

	public static class TrieNode {
		public boolean end;
		public TrieNode[] nexts;

		public TrieNode() {
			end = false;
			nexts = new TrieNode[26];
		}
	}

	public static boolean split1(char[] s, TrieNode r, int i, int[] dp) {
		if (dp[i] != 0) {
			return dp[i] == 1;
		}
		boolean ans = false;
		if (i == s.length) {
			ans = true;
		} else {
			TrieNode c = r;
			for (int end = i; end < s.length; end++) {
				int path = s[end] - 'a';
				if (c.nexts[path] == null) {
					break;
				}
				c = c.nexts[path];
				if (c.end && split1(s, r, end + 1, dp)) {
					ans = true;
					break;
				}
			}
		}
		dp[i] = ans ? 1 : -1;
		return ans;
	}

	public static boolean split2(char[] s, TrieNode r, int i) {
		boolean ans = false;
		if (i == s.length) {
			ans = true;
		} else {
			TrieNode c = r;
			for (int end = i; end < s.length; end++) {
				int path = s[end] - 'a';
				if (c.nexts[path] == null) {
					break;
				}
				c = c.nexts[path];
				if (c.end && split2(s, r, end + 1)) {
					ans = true;
					break;
				}
			}
		}
		return ans;
	}

	public static void insert(TrieNode root, char[] s) {
		int path = 0;
		for (char c : s) {
			path = c - 'a';
			if (root.nexts[path] == null) {
				root.nexts[path] = new TrieNode();
			}
			root = root.nexts[path];
		}
		root.end = true;
	}

}
