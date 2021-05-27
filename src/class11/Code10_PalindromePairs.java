package class11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// 测试链接 : https://leetcode.com/problems/palindrome-pairs/
public class Code10_PalindromePairs {

	public static List<List<Integer>> palindromePairs(String[] words) {
		HashMap<String, Integer> wordset = new HashMap<>();
		for (int i = 0; i < words.length; i++) {
			wordset.put(words[i], i);
		}
		List<List<Integer>> res = new ArrayList<>();
		for (int i = 0; i < words.length; i++) {
			res.addAll(findAll(words[i], i, wordset));
		}
		return res;
	}

	public static List<List<Integer>> findAll(String word, int index, HashMap<String, Integer> words) {
		List<List<Integer>> res = new ArrayList<>();
		String reverse = reverse(word);
		Integer rest = words.get("");
		if (rest != null && rest != index && word.equals(reverse)) {
			addRecord(res, rest, index);
			addRecord(res, index, rest);
		}
		int[] rs = manacherss(word);
		int mid = rs.length >> 1;
		for (int i = 1; i < mid; i++) {
			if (i - rs[i] == -1) {
				rest = words.get(reverse.substring(0, mid - i));
				if (rest != null && rest != index) {
					addRecord(res, rest, index);
				}
			}
		}
		for (int i = mid + 1; i < rs.length; i++) {
			if (i + rs[i] == rs.length) {
				rest = words.get(reverse.substring((mid << 1) - i));
				if (rest != null && rest != index) {
					addRecord(res, index, rest);
				}
			}
		}
		return res;
	}

	public static void addRecord(List<List<Integer>> res, int left, int right) {
		List<Integer> newr = new ArrayList<>();
		newr.add(left);
		newr.add(right);
		res.add(newr);
	}

	public static int[] manacherss(String word) {
		char[] mchs = manachercs(word);
		int[] rs = new int[mchs.length];
		int center = -1;
		int pr = -1;
		for (int i = 0; i != mchs.length; i++) {
			rs[i] = pr > i ? Math.min(rs[(center << 1) - i], pr - i) : 1;
			while (i + rs[i] < mchs.length && i - rs[i] > -1) {
				if (mchs[i + rs[i]] != mchs[i - rs[i]]) {
					break;
				}
				rs[i]++;
			}
			if (i + rs[i] > pr) {
				pr = i + rs[i];
				center = i;
			}
		}
		return rs;
	}

	public static char[] manachercs(String word) {
		char[] chs = word.toCharArray();
		char[] mchs = new char[chs.length * 2 + 1];
		int index = 0;
		for (int i = 0; i != mchs.length; i++) {
			mchs[i] = (i & 1) == 0 ? '#' : chs[index++];
		}
		return mchs;
	}

	public static String reverse(String str) {
		char[] chs = str.toCharArray();
		int l = 0;
		int r = chs.length - 1;
		while (l < r) {
			char tmp = chs[l];
			chs[l++] = chs[r];
			chs[r--] = tmp;
		}
		return String.valueOf(chs);
	}

}