package class48;

import java.util.ArrayList;
import java.util.List;

public class Problem_0472_ConcatenatedWords {

	public static List<String> findAllConcatenatedWordsInADict(String[] words) {
		TrieNode head = new TrieNode();
		for (String s : words) {
			insert(s.toCharArray(), head);
		}
		List<String> ans = new ArrayList<>();
		for (String s : words) {
			if (split(s.toCharArray(), head, 0, 0, head) > 1) {
				ans.add(s);
			}
		}
		return ans;
	}

	public static class TrieNode {
		public boolean end;
		public TrieNode[] next;

		public TrieNode() {
			end = false;
			next = new TrieNode[26];
		}
	}

	public static void insert(char[] s, TrieNode h) {
		int i = 0;
		for (char c : s) {
			i = c - 'a';
			if (h.next[i] == null) {
				h.next[i] = new TrieNode();
			}
			h = h.next[i];
		}
		h.end = true;
	}

	public static int split(char[] s, TrieNode h, int f, int i, TrieNode c) {
		if (i == s.length) {
			return c.end ? (f == i - 1 ? 0 : 1) : -1;
		}
		if (c.next[s[i] - 'a'] == null) {
			return -1;
		}
		c = c.next[s[i] - 'a'];
		if (c.end) {
			int next = split(s, h, i, i + 1, h);
			if (next > 0) {
				return next + 1;
			}
		}
		return split(s, h, f, i + 1, c);
	}

}
