package class22;

public class Code05_RemoveDuplicateLettersLessLexi {

	// 在str中，每种字符都要保留一个，让最后的结果，字典序最小 ，并返回
	public static String removeDuplicateLetters1(String str) {
		if (str == null || str.length() < 2) {
			return str;
		}
		int[] map = new int[256];
		for (int i = 0; i < str.length(); i++) {
			map[str.charAt(i)]++;
		}
		int minACSIndex = 0;
		for (int i = 0; i < str.length(); i++) {
			minACSIndex = str.charAt(minACSIndex) > str.charAt(i) ? i : minACSIndex;
			if (--map[str.charAt(i)] == 0) {
				break;
			}
		}
		return String.valueOf(str.charAt(minACSIndex)) + removeDuplicateLetters1(
				str.substring(minACSIndex + 1).replaceAll(String.valueOf(str.charAt(minACSIndex)), ""));
	}

	public static String removeDuplicateLetters2(String s) {
		char[] str = s.toCharArray();
		// 小写字母ascii码值范围[97~122]，所以用长度为26的数组做次数统计
		// 如果map[i] > -1，则代表ascii码值为i的字符的出现次数
		// 如果map[i] == -1，则代表ascii码值为i的字符不再考虑
		int[] map = new int[26];
		for (int i = 0; i < str.length; i++) {
			map[str[i] - 'a']++;
		}
		char[] res = new char[26];
		int index = 0;
		int L = 0;
		int R = 0;
		while (R != str.length) {
			// 如果当前字符是不再考虑的，直接跳过
			// 如果当前字符的出现次数减1之后，后面还能出现，直接跳过
			if (map[str[R] - 'a'] == -1 || --map[str[R] - 'a'] > 0) {
				R++;
			} else { // 当前字符需要考虑并且之后不会再出现了
				// 在str[L..R]上所有需要考虑的字符中，找到ascii码最小字符的位置
				int pick = -1;
				for (int i = L; i <= R; i++) {
					if (map[str[i] - 'a'] != -1 && (pick == -1 || str[i] < str[pick])) {
						pick = i;
					}
				}
				// 把ascii码最小的字符放到挑选结果中
				res[index++] = str[pick];
				// 在上一个的for循环中，str[L..R]范围上每种字符的出现次数都减少了
				// 需要把str[pick + 1..R]上每种字符的出现次数加回来
				for (int i = pick + 1; i <= R; i++) {
					if (map[str[i] - 'a'] != -1) { // 只增加以后需要考虑字符的次数
						map[str[i] - 'a']++;
					}
				}
				// 选出的ascii码最小的字符，以后不再考虑了
				map[str[pick] - 'a'] = -1;
				// 继续在str[pick + 1......]上重复这个过程
				L = pick + 1;
				R = L;
			}
		}
		return String.valueOf(res, 0, index);
	}

}
