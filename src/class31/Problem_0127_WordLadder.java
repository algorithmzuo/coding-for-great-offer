package class31;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Problem_0127_WordLadder {

	// start，出发的单词
	// to, 目标单位
	// list, 列表
	// to 一定属于list
	// start未必
	// 返回变幻的最短路径长度
	public static int ladderLength1(String start, String to, List<String> list) {
		list.add(start);

		// key : 列表中的单词，每一个单词都会有记录！
		// value : key这个单词，有哪些邻居！
		HashMap<String, ArrayList<String>> nexts = getNexts(list);
		// abc  出发     abc  -> abc  0
		// 
		// bbc  1
		HashMap<String, Integer> distanceMap = new HashMap<>();
		distanceMap.put(start, 1);
		HashSet<String> set = new HashSet<>();
		set.add(start);
		Queue<String> queue = new LinkedList<>();
		queue.add(start);
		while (!queue.isEmpty()) {
			String cur = queue.poll();
			Integer distance = distanceMap.get(cur);
			for (String next : nexts.get(cur)) {
				if (next.equals(to)) {
					return distance + 1;
				}
				if (!set.contains(next)) {
					set.add(next);
					queue.add(next);
					distanceMap.put(next, distance + 1);
				}
			}

		}
		return 0;
	}

	public static HashMap<String, ArrayList<String>> getNexts(List<String> words) {
		HashSet<String> dict = new HashSet<>(words);
		HashMap<String, ArrayList<String>> nexts = new HashMap<>();
		for (int i = 0; i < words.size(); i++) {
			nexts.put(words.get(i), getNext(words.get(i), dict));
		}
		return nexts;
	}

	// 应该根据具体数据状况决定用什么来找邻居
	// 1)如果字符串长度比较短，字符串数量比较多，以下方法适合
	// 2)如果字符串长度比较长，字符串数量比较少，以下方法不适合
	public static ArrayList<String> getNext(String word, HashSet<String> dict) {
		ArrayList<String> res = new ArrayList<String>();
		char[] chs = word.toCharArray();
		for (int i = 0; i < chs.length; i++) {
			for (char cur = 'a'; cur <= 'z'; cur++) {
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

	public static int ladderLength2(String beginWord, String endWord, List<String> wordList) {
		HashSet<String> dict = new HashSet<>(wordList);
		if (!dict.contains(endWord)) {
			return 0;
		}
		HashSet<String> startSet = new HashSet<>();
		HashSet<String> endSet = new HashSet<>();
		HashSet<String> visit = new HashSet<>();
		startSet.add(beginWord);
		endSet.add(endWord);
		for (int len = 2; !startSet.isEmpty(); len++) {
			// startSet是较小的，endSet是较大的
			HashSet<String> nextSet = new HashSet<>();
			for (String w : startSet) {
				// w -> a(nextSet)
				// a b c
				// 0 
				//   1
				//     2
				for (int j = 0; j < w.length(); j++) {
					char[] ch = w.toCharArray();
					for (char c = 'a'; c <= 'z'; c++) {
						if (c != w.charAt(j)) {
							ch[j] = c;
							String next = String.valueOf(ch);
							if (endSet.contains(next)) {
								return len;
							}
							if (dict.contains(next) && !visit.contains(next)) {
								nextSet.add(next);
								visit.add(next);
							}
						}
					}
				}
			}
			// startSet(小) -> nextSet(某个大小)   和 endSet大小来比
			startSet = (nextSet.size() < endSet.size()) ? nextSet : endSet;
			endSet = (startSet == nextSet) ? endSet : nextSet;
		}
		return 0;
	}

}
