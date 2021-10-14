package class48;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Problem_0527_WordAbbreviation {

	public static List<String> wordsAbbreviation(List<String> words) {
		int len = words.size();
		List<String> res = new ArrayList<>();
		int[] prefix = new int[len];
		HashMap<String, List<Integer>> map = new HashMap<>();
		for (int i = 0; i < len; i++) {
			prefix[i] = 0;
			res.add(makeAbbr(words.get(i), 1));
			List<Integer> list = map.getOrDefault(res.get(i), new ArrayList<>());
			list.add(i);
			map.put(res.get(i), list);
		}
		for (int i = 0; i < len; i++) {
			if (map.get(res.get(i)).size() > 1) {
				List<Integer> indexes = map.get(res.get(i));
				map.remove(res.get(i));
				for (int j : indexes) {
					prefix[j]++;
					res.set(j, makeAbbr(words.get(j), prefix[j]));
					List<Integer> list = map.getOrDefault(res.get(j), new ArrayList<>());
					list.add(j);
					map.put(res.get(j), list);
				}
				i--;
			}
		}
		return res;
	}

	public static String makeAbbr(String s, int k) {
		if (k >= s.length() - 2) {
			return s;
		}
		StringBuilder builder = new StringBuilder();
		builder.append(s.substring(0, k));
		builder.append(s.length() - 1 - k);
		builder.append(s.charAt(s.length() - 1));
		return builder.toString();
	}

}
