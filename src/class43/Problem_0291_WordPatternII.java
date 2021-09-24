package class43;

import java.util.HashMap;

public class Problem_0291_WordPatternII {

	public static boolean wordPatternMatch(String pattern, String str) {
		if (pattern == null || str == null) {
			return pattern == str;
		}
		if (pattern.length() == 0 || str.length() == 0) {
			return pattern.length() == str.length();
		}
		HashMap<String, Character> map1 = new HashMap<>();
		HashMap<Character, String> map2 = new HashMap<>();
		return process(str.toCharArray(), pattern.toCharArray(), 0, 0, map1, map2);
	}

	public static boolean process(char[] s, char[] p, int si, int pi, HashMap<String, Character> map1,
			HashMap<Character, String> map2) {
		if (si == s.length) {
			return pi == 0;
		}
		String pre = "";
		for (int i = si; i < s.length; i++) {
			pre += s[i];
			if (map1.containsKey(pre) && p[pi] != map1.get(pre)) {
				continue;
			}
			if (map2.containsKey(p[pi]) && !pre.equals(map2.get(p[pi]))) {
				continue;
			}
			boolean add1 = false;
			if (!map1.containsKey(pre)) {
				map1.put(pre, p[pi]);
				add1 = true;
			}
			boolean add2 = false;
			if (!map2.containsKey(p[pi])) {
				map2.put(p[pi], pre);
				add2 = true;
			}
			if (process(s, p, i + 1, nextpi(p.length, pi), map1, map2)) {
				return true;
			}
			if (add1) {
				map1.remove(pre);
			}
			if (add2) {
				map2.remove(p[pi]);
			}
		}
		return false;
	}

	public static int nextpi(int M, int i) {
		return i == M - 1 ? 0 : i + 1;
	}

}
