package class33;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Problem_0269_AlienDictionary {

	public static String alienOrder(String[] words) {
		if (words == null || words.length == 0) {
			return "";
		}
		int N = words.length;
		HashMap<Character, Integer> indegree = new HashMap<>();
		for (int i = 0; i < N; i++) {
			for (char c : words[i].toCharArray()) {
				indegree.put(c, 0);
			}
		}
		HashMap<Character, HashSet<Character>> graph = new HashMap<>();
		for (int i = 0; i < N - 1; i++) {
			char[] cur = words[i].toCharArray();
			char[] nex = words[i + 1].toCharArray();
			int len = Math.min(cur.length, nex.length);
			int j = 0;
			for (; j < len; j++) {
				if (cur[j] != nex[j]) {
					if (!graph.containsKey(cur[j])) {
						graph.put(cur[j], new HashSet<>());
					}
					if (!graph.get(cur[j]).contains(nex[j])) {
						graph.get(cur[j]).add(nex[j]);
						indegree.put(nex[j], indegree.get(nex[j]) + 1);
					}
					break;
				}
			}
			if (j < cur.length && j == nex.length) {
				return "";
			}
		}
		StringBuilder ans = new StringBuilder();
		Queue<Character> q = new LinkedList<>();
		for (Character key : indegree.keySet()) {
			if (indegree.get(key) == 0) {
				q.offer(key);
			}
		}
		while (!q.isEmpty()) {
			char cur = q.poll();
			ans.append(cur);
			if (graph.containsKey(cur)) {
				for (char next : graph.get(cur)) {
					indegree.put(next, indegree.get(next) - 1);
					if (indegree.get(next) == 0) {
						q.offer(next);
					}
				}
			}
		}
		return ans.length() == indegree.size() ? ans.toString() : "";
	}

}
