package class26;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 本题测试链接 : https://leetcode.com/problems/word-ladder-ii/
public class Code03_WordLadderII {

	public static List<List<String>> findLadders(String start, String end, List<String> list) {
		list.add(start);
		HashMap<String, List<String>> nexts = getNexts(list);
		HashMap<String, Integer> distances = getDistances(start, nexts);
		LinkedList<String> pathList = new LinkedList<>();
		List<List<String>> res = new ArrayList<>();
		getShortestPaths(start, end, nexts, distances, pathList, res);
		return res;
	}

	public static HashMap<String, List<String>> getNexts(List<String> words) {
		HashSet<String> dict = new HashSet<>(words);
		HashMap<String, List<String>> nexts = new HashMap<>();
		for (int i = 0; i < words.size(); i++) {
			nexts.put(words.get(i), getNext(words.get(i), dict));
		}
		return nexts;
	}

	public static List<String> getNext(String word, HashSet<String> dict) {
		ArrayList<String> res = new ArrayList<String>();
		char[] chs = word.toCharArray();
		for (char cur = 'a'; cur <= 'z'; cur++) {
			for (int i = 0; i < chs.length; i++) {
				if (chs[i] != cur) {
					char tmp = chs[i];
					chs[i] = cur;
					if (dict.contains(String.valueOf(chs))) {
						res.add(String.valueOf(chs));
					}
					chs[i] = tmp;
				}
			}
		}
		return res;
	}

	public static HashMap<String, Integer> getDistances(String start, HashMap<String, List<String>> nexts) {
		HashMap<String, Integer> distances = new HashMap<>();
		distances.put(start, 0);
		Queue<String> queue = new LinkedList<>();
		queue.add(start);
		HashSet<String> set = new HashSet<>();
		set.add(start);
		while (!queue.isEmpty()) {
			String cur = queue.poll();
			for (String next : nexts.get(cur)) {
				if (!set.contains(next)) {
					distances.put(next, distances.get(cur) + 1);
					queue.add(next);
					set.add(next);
				}
			}
		}
		return distances;
	}

	public static void getShortestPaths(String cur, String to, HashMap<String, List<String>> nexts,
			HashMap<String, Integer> distances, LinkedList<String> path, List<List<String>> res) {
		path.add(cur);
		if (to.equals(cur)) {
			res.add(new LinkedList<String>(path));
		} else {
			for (String next : nexts.get(cur)) {
				if (distances.get(next) == distances.get(cur) + 1) {
					getShortestPaths(next, to, nexts, distances, path, res);
				}
			}
		}
		path.pollLast();
	}

}
