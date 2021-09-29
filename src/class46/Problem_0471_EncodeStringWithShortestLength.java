package class46;

import java.util.HashMap;

public class Problem_0471_EncodeStringWithShortestLength {

	public static HashMap<String, String> map = new HashMap<>();

	public static String encode(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		if (s.length() <= 4) {
			return s;
		}
		if (map.containsKey(s)) {
			return map.get(s);
		}
		String ret = s;
		for (int k = s.length() / 2; k < s.length(); k++) {
			String pattern = s.substring(k);
			int times = countRepeat(s, pattern);
			if (times * pattern.length() != s.length()) {
				continue;
			}
			String candidate = Integer.toString(times) + "[" + encode(pattern) + "]";
			if (candidate.length() < ret.length()) {
				ret = candidate;
			}
		}
		for (int i = 1; i < s.length(); i++) {
			String left = encode(s.substring(0, i));
			String right = encode(s.substring(i));
			String candidate = left + right;
			if (candidate.length() < ret.length()) {
				ret = candidate;
			}
		}
		map.put(s, ret);
		return ret;
	}

	public static int countRepeat(String s, String pattern) {
		int times = 0;
		while (s.length() >= pattern.length()) {
			String sub = s.substring(s.length() - pattern.length());
			if (sub.equals(pattern)) {
				times++;
				s = s.substring(0, s.length() - pattern.length());
			} else {
				return times;
			}
		}
		return times;
	}

}
