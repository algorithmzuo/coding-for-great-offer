package class45;

public class Problem_0411_MinimumUniqueWordAbbreviation {

	public static int min = 0;

	public static int best = 0;

	public static String minAbbreviation(String target, String[] dictionary) {
		char[] t = target.toCharArray();
		int len = t.length;
		int siz = 0;
		for (String word : dictionary) {
			if (word.length() == len) {
				siz++;
			}
		}
		int[] words = new int[siz];
		int index = 0;
		int diff = 0;
		for (String word : dictionary) {
			if (word.length() == len) {
				char[] w = word.toCharArray();
				int status = 0;
				for (int j = 0; j < len; j++) {
					if (t[j] != w[j]) {
						status |= 1 << j;
					}
				}
				words[index++] = status;
				diff |= status;
			}
		}
		min = Integer.MAX_VALUE;
		best = 0;
		dfs(words, len, diff, 0, 0);
		StringBuilder builder = new StringBuilder();
		int count = 0;
		for (int i = 0; i < len; i++) {
			if ((best & (1 << i)) != 0) {
				if (count > 0) {
					builder.append(count);
				}
				builder.append(t[i]);
				count = 0;
			} else {
				count++;
			}
		}
		if (count > 0) {
			builder.append(count);
		}
		return builder.toString();
	}

	public static void dfs(int[] words, int len, int diff, int fix, int index) {
		if (!canFix(words, fix)) {
			if (index < len) {
				dfs(words, len, diff, fix, index + 1);
				if ((diff & (1 << index)) != 0) {
					dfs(words, len, diff, fix | (1 << index), index + 1);
				}
			}
		} else {
			int ans = abbrLen(fix, len);
			if (ans < min) {
				min = ans;
				best = fix;
			}
		}
	}

	public static boolean canFix(int[] words, int fix) {
		for (int word : words) {
			if ((fix & word) == 0) {
				return false;
			}
		}
		return true;
	}

	public static int abbrLen(int fix, int len) {
		int ans = 0;
		int cnt = 0;
		for (int i = 0; i < len; i++) {
			if ((fix & (1 << i)) != 0) {
				ans++;
				if (cnt != 0) {
					ans += (cnt > 9 ? 2 : 1) - cnt;
				}
				cnt = 0;
			} else {
				cnt++;
			}
		}
		if (cnt != 0) {
			ans += (cnt > 9 ? 2 : 1) - cnt;
		}
		return ans;
	}

}
