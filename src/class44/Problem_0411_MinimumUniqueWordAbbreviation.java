package class44;

public class Problem_0411_MinimumUniqueWordAbbreviation {

	public static int min = 0;

	public static int best = 0;

	public static String minAbbreviation(String target, String[] dictionary) {
		char[] t = target.toCharArray();
		int N = t.length;
		int M = 0;
		for (String word : dictionary) {
			if (word.length() == N) {
				M++;
			}
		}
		if (M == 0) {
			return String.valueOf(N);
		}
		int[] words = new int[M];
		int index = 0;
		int cands = 0;
		for (String word : dictionary) {
			if (word.length() == N) {
				char[] w = word.toCharArray();
				int status = 0;
				for (int j = 0; j < N; j++) {
					if (t[j] != w[j]) {
						status |= 1 << j;
					}
				}
				words[index++] = status;
				cands |= status;
			}
		}
		if (cands == 0) {
			return target;
		}
		min = Integer.MAX_VALUE;
		best = 0;
		dfs(words, N, cands, 0, 0);
		StringBuilder builder = new StringBuilder();
		int count = 0;
		for (int i = 0; i < N; i++) {
			if ((best & (1 << i)) == 0) {
				count++;
			} else {
				if (count > 0) {
					builder.append(count);
				}
				builder.append(t[i]);
				count = 0;
			}
		}
		if (count > 0) {
			builder.append(count);
		}
		return builder.toString();
	}

	public static void dfs(int[] words, int N, int cands, int fix, int index) {
		if (!fix(words, fix)) {
			for (int i = index; i < N; i++) {
				if ((cands & (1 << i)) != 0) {
					dfs(words, N, cands, fix | (1 << i), i + 1);
				}
			}
		} else {
			int ans = parts(fix, N);
			if (ans < min) {
				min = ans;
				best = fix;
			}
		}
	}

	public static int parts(int fix, int N) {
		int count = N;
		int limit = 1 << N;
		for (int check = 3; check < limit; check <<= 1)
			if ((fix & check) == 0) {
				count--;
			}
		return count;
	}

	public static boolean fix(int[] words, int fix) {
		for (int word : words) {
			if ((fix & word) == 0) {
				return false;
			}
		}
		return true;
	}

}
