package class51;

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

		// 题目的要求，只输出排名前3的列表
		public final int top = 3;
		public final TrieNode root = new TrieNode(null, "");
		// 某个前缀树节点，上面的有序表，不在这个节点内部
		// 外挂
		public HashMap<TrieNode, TreeSet<WordCount>> nodeRankMap = new HashMap<>();
		
		// 字符串 "abc"  7次   ->  ("abc", 7)
		public HashMap<String, WordCount> wordCountMap = new HashMap<>();
		
		
		public String path;
		public TrieNode cur;

		// ' ' -> 0
		// 'a' -> 1
		// 'b' -> 2
		// ...
		// 'z' -> 26 
		//  '`'  a b  .. z
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

		// 之前已经有一些历史了！
		// 当前键入 c 字符
		// 请顺着之前的历史，根据c字符是什么，继续
		// path : 之前键入的字符串整体
		// cur : 当前滑到了前缀树的哪个节点

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
				// for循环本身就是根据排序后的顺序来遍历！
				for (WordCount wc : nodeRankMap.get(cur)) {
					if (k == top) {
						break;
					}
					ans.add(wc.word);
					k++;
				}
			}
			// c = #   path = "abcde" 
			// #
			// #
			// #
			// a b .. #
			if (c == '#' && !path.equals("")) {
				// 真的有一个，有效字符串需要加入！path
				if (!wordCountMap.containsKey(path)) {
					wordCountMap.put(path, new WordCount(path, 0));
				}
				// 有序表内部的小对象，该小对象参与排序的指标数据改变
				// 但是有序表并不会自动刷新
				// 所以，删掉老的，加新的！
				WordCount oldOne = wordCountMap.get(path);
				WordCount newOne = new WordCount(path, oldOne.count + 1);
				while (cur != root) {
					nodeRankMap.get(cur).remove(oldOne);
					nodeRankMap.get(cur).add(newOne);
					cur = cur.father;
				}
				wordCountMap.put(path, newOne);
				path = "";
				// cur 回到头了
			}
			return ans;
		}

	}

}
