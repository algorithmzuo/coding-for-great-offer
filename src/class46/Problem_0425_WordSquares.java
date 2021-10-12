package class46;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Problem_0425_WordSquares {

	public static class Node {
		public List<String> words;
		public Node[] nexts;

		public Node() {
			words = new ArrayList<>();
			nexts = new Node[26];
		}

	}

	public static class Trie {

		public Node root;

		public Trie(String[] words) {
			root = new Node();
			Node cur = null;
			for (String word : words) {
				cur = root;
				cur.words.add(word);
				char[] str = word.toCharArray();
				for (int i = 0; i < str.length; i++) {
					int path = str[i] - 'a';
					if (cur.nexts[path] == null) {
						cur.nexts[path] = new Node();
					}
					cur = cur.nexts[path];
					cur.words.add(word);
				}
			}
		}

		public List<String> getCands(String prefix) {
			char[] str = prefix.toCharArray();
			Node cur = root;
			for (int i = 0; i < str.length; i++) {
				int path = str[i] - 'a';
				if (cur.nexts[path] == null) {
					return new ArrayList<>();
				}
				cur = cur.nexts[str[i] - 'a'];
			}
			return cur.words;
		}

	}

	public static List<List<String>> wordSquares(String[] words) {
		Trie trie = new Trie(words);
		LinkedList<String> path = new LinkedList<>();
		List<List<String>> ans = new ArrayList<>();
		int N = words[0].length();
		for (String word : words) {
			path.addLast(word);
			process(path, 1, trie, ans, N);
			path.pollLast();
		}
		return ans;
	}

	public static void process(LinkedList<String> path, int index, Trie trie, List<List<String>> ans, int len) {
		if (index == len) {
			ans.add(new ArrayList<>(path));
		} else {
			StringBuilder prefix = new StringBuilder();
			for (String pre : path) {
				prefix.append(pre.charAt(index));
			}
			List<String> nexts = trie.getCands(prefix.toString());
			for (String next : nexts) {
				path.addLast(next);
				process(path, index + 1, trie, ans, len);
				path.pollLast();
			}
		}
	}

}
