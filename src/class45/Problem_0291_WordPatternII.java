package class45;

import java.util.HashSet;

public class Problem_0291_WordPatternII {

	public static boolean wordPatternMatch(String pattern, String str) {
		return match(str, pattern, 0, 0, new String[26], new HashSet<>());
	}

	public static boolean match(String s, String p, int si, int pi, String[] map, HashSet<String> set) {
		if (pi == p.length() && si == s.length()) {
			return true;
		}
		if (pi == p.length() || si == s.length()) {
			return false;
		}
		char ch = p.charAt(pi);
		String cur = map[ch - 'a'];
		if (cur != null) {
			return si + cur.length() <= s.length() && cur.equals(s.substring(si, si + cur.length()))
					&& match(s, p, si + cur.length(), pi + 1, map, set);
		}
		int end = s.length();
		for (int i = p.length() - 1; i > pi; i--) {
			end -= map[p.charAt(i) - 'a'] == null ? 1 : map[p.charAt(i) - 'a'].length();
		}
		for (int i = si; i < end; i++) {
			cur = s.substring(si, i + 1);
			if (!set.contains(cur)) {
				set.add(cur);
				map[ch - 'a'] = cur;
				if (match(s, p, i + 1, pi + 1, map, set)) {
					return true;
				}
				set.remove(cur);
			}
		}
		map[ch - 'a'] = null;
		return false;
	}

}
