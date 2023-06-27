package class17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

// 课上讲了哈希表+manacher
// 有关manacher的解释，看这个帖子 : https://www.mashibing.com/question/detail/56727
// 从代码层次讲了一下，例子非常详细
// 测试链接 : https://leetcode.com/problems/palindrome-pairs/
public class Code03_PalindromePairs1 {

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
			res.add(Arrays.asList(rest, index));
			res.add(Arrays.asList(index, rest));
		}
		int[] rs = manacherss(word);
		int mid = rs.length >> 1;
		for (int i = 1; i < mid; i++) {
			if (i - rs[i] == -1) {
				rest = words.get(reverse.substring(0, mid - i));
				if (rest != null && rest != index) {
					res.add(Arrays.asList(rest, index));
				}
			}
		}
		for (int i = mid + 1; i < rs.length; i++) {
			if (i + rs[i] == rs.length) {
				rest = words.get(reverse.substring((mid << 1) - i));
				if (rest != null && rest != index) {
					res.add(Arrays.asList(index, rest));
				}
			}
		}
		return res;
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