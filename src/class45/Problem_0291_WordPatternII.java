package class45;

import java.util.HashSet;

public class Problem_0291_WordPatternII {

	public static boolean wordPatternMatch(String pattern, String str) {
		String[] map = new String[26];
		HashSet<String> set = new HashSet<>();
		return process(str, pattern, 0, 0, map, set);
	}

	public static boolean process(String s, String p, int si, int pi, String[] map, HashSet<String> set) {
		if (pi == p.length() && si == s.length()) {
			return true;
		}
		if (pi == p.length() || si == s.length()) {
			return false;
		}
		char ch = p.charAt(pi);
		String matched = map[ch - 'a'];
		if (matched != null) {
			int count = matched.length();
			return si + count <= s.length() && matched.equals(s.substring(si, si + count))
					&& process(s, p, si + matched.length(), pi + 1, map, set);
		} else {
			int endPoint = s.length() - 1;
			for (int i = p.length() - 1; i > pi; i--) {
				endPoint -= map[p.charAt(i) - 'a'] == null ? 1 : map[p.charAt(i) - 'a'].length();
			}
			for (int i = si; i <= endPoint; i++) {
				matched = s.substring(si, i + 1);
				if (!set.contains(matched)) {
					set.add(matched);
					map[ch - 'a'] = matched;
					if (process(s, p, i + 1, pi + 1, map, set)) {
						return true;
					} else {
						map[ch - 'a'] = null;
						set.remove(matched);
					}
				}
			}
		}
		return false;
	}

}
