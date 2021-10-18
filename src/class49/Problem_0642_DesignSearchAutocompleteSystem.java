package class49;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class Problem_0642_DesignSearchAutocompleteSystem {

	class AutocompleteSystem {

		public class TrieNode {
			public TrieNode father;
			public String path;
			public TrieNode[] nexts;

			public TrieNode(TrieNode f, String p) {
				father = f;
				path = p;
				nexts = new TrieNode[27];
			}
		}

		public class WordCount implements Comparable<WordCount> {
			public String word;
			public int count;

			public WordCount(String w, int c) {
				word = w;
				count = c;
			}

			public int compareTo(WordCount o) {
				return count != o.count ? (o.count - count) : word.compareTo(o.word);
			}
		}

		public final int top = 3;
		public final TrieNode root = new TrieNode(null, "");
		public HashMap<TrieNode, TreeSet<WordCount>> nodeRankMap = new HashMap<>();
		public HashMap<String, WordCount> wordCountMap = new HashMap<>();
		public String path;
		public TrieNode cur;

		private int f(char c) {
			return c == ' ' ? 0 : (c - '`');
		}

		public AutocompleteSystem(String[] sentences, int[] times) {
			path = "";
			cur = root;
			for (int i = 0; i < sentences.length; i++) {
				String word = sentences[i];
				int count = times[i];
				WordCount wc = new WordCount(word, count - 1);
				wordCountMap.put(word, wc);
				for (char c : word.toCharArray()) {
					input(c);
				}
				input('#');
			}
		}

		public List<String> input(char c) {
			List<String> ans = new ArrayList<>();
			if (c != '#') {
				path += c;
				int i = f(c);
				if (cur.nexts[i] == null) {
					cur.nexts[i] = new TrieNode(cur, path);
				}
				cur = cur.nexts[i];
				if (!nodeRankMap.containsKey(cur)) {
					nodeRankMap.put(cur, new TreeSet<>());
				}
				int k = 0;
				for (WordCount wc : nodeRankMap.get(cur)) {
					if (k == top) {
						break;
					}
					ans.add(wc.word);
					k++;
				}
			}
			if (c == '#' && !path.equals("")) {
				if (!wordCountMap.containsKey(path)) {
					wordCountMap.put(path, new WordCount(path, 0));
				}
				WordCount oldOne = wordCountMap.get(path);
				WordCount newOne = new WordCount(path, oldOne.count + 1);
				while (cur != root) {
					nodeRankMap.get(cur).remove(oldOne);
					nodeRankMap.get(cur).add(newOne);
					cur = cur.father;
				}
				wordCountMap.put(path, newOne);
				path = "";
			}
			return ans;
		}

	}

}
