package class46;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Problem_0425_WordSquares {

	public static List<List<String>> wordSquares(String[] words) {
		int n = words[0].length();
		// 所有单词，所有前缀字符串，都会成为key！
		HashMap<String, List<String>> map = new HashMap<>();
		for (String word : words) {
			for (int end = 0; end <= n; end++) {
				String prefix = word.substring(0, end);
				if (!map.containsKey(prefix)) {
					map.put(prefix, new ArrayList<>());
				}
				map.get(prefix).add(word);
			}
		}
		List<List<String>> ans = new ArrayList<>();
		process(0, n, map, new LinkedList<>(), ans);
		return ans;
	}

	// i, 当前填到第i号单词，从0开始，填到n-1
	// map, 前缀所拥有的单词
	// path, 之前填过的单词, 0...i-1填过的
	// ans, 收集答案
	public static void process(int i, int n, HashMap<String, List<String>> map, 
			LinkedList<String> path,
			List<List<String>> ans) {
		if (i == n) {
			ans.add(new ArrayList<>(path));
		} else {
			// 把限制求出来，前缀的限制！
			StringBuilder builder = new StringBuilder();
			for (String pre : path) {
				builder.append(pre.charAt(i));
			}
			String prefix = builder.toString();
			if (map.containsKey(prefix)) {
				for (String next : map.get(prefix)) {
					path.addLast(next);
					process(i + 1, n, map, path, ans);
					path.pollLast();
				}
			}
		}
	}

}
