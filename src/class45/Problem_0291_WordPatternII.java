package class45;

import java.util.HashSet;

public class Problem_0291_WordPatternII {

	public static boolean wordPatternMatch(String pattern, String str) {
		String[] map = new String[26];
		HashSet<String> set = new HashSet<>();
		return process(str, pattern, 0, str.length() - 1, 0, pattern.length() - 1, map, set);
	}

	public static boolean process(String s, String p, int si, int se, int pi, int pe, String[] map,
			HashSet<String> set) {
		if (pi == pe + 1 && si == se + 1) {
			return true;
		}
		if ((pi > pe && si <= se) || (pi < pe && si > se)) {
			return false;
		}
		char ch = p.charAt(pi);
		String matched = map[ch - 'a'];
		if (matched != null) {
			int count = matched.length();
			return si + count <= se + 1 && matched.equals(s.substring(si, si + count))
					&& process(s, p, si + matched.length(), se, pi + 1, pe, map, set);
		} else {
			int endPoint = se;
			for (int i = pe; i > pi; i--) {
				endPoint -= map[p.charAt(i) - 'a'] == null ? 1 : map[p.charAt(i) - 'a'].length();
			}
			for (int i = si; i <= endPoint; i++) {
				matched = s.substring(si, i + 1);
				if (!set.contains(matched)) {
					set.add(matched);
					map[ch - 'a'] = matched;
					if (process(s, p, i + 1, se, pi + 1, pe, map, set)) {
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
