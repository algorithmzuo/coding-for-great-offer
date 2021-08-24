package class36;

public class Code03_SA {

	public static int sa(int n, String a) {
		if (n < 1 || a == null || a.length() < 1) {
			return 0;
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 1; i <= n; i++) {
			builder.append(String.valueOf(i));
		}
		char[] str1 = builder.toString().toCharArray();
		char[] str2 = a.toCharArray();
		return kmp(str1, str2);
	}

	public static int kmp(char[] str1, char[] str2) {
		int x = 0;
		int y = 0;
		int count = 0;
		int[] next = getNextArray(str2);
		while (x < str1.length) {
			if (str1[x] == str2[y]) {
				x++;
				y++;
				if (y == str2.length) {
					count++;
					y = next[y];
				}
			} else if (next[y] == -1) {
				x++;
			} else {
				y = next[y];
			}
		}
		return count;
	}

	// next数组多求一位
	// 比如：str2 = aaaa
	// 那么，next = -1,0,1,2,3
	// 最后一个3表示，终止位置之前的字符串最长前缀和最长后缀的匹配长度
	// 也就是next数组补一位
	public static int[] getNextArray(char[] str2) {
		if (str2.length == 1) {
			return new int[] { -1, 0 };
		}
		int[] next = new int[str2.length + 1];
		next[0] = -1;
		next[1] = 0;
		int i = 2;
		int cn = 0;
		while (i < next.length) {
			if (str2[i - 1] == str2[cn]) {
				next[i++] = ++cn;
			} else if (cn > 0) {
				cn = next[cn];
			} else {
				next[i++] = 0;
			}
		}
		return next;
	}

}
