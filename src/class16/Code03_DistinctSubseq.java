package class16;

public class Code03_DistinctSubseq {

	public static int numDistinct1(String S, String T) {
		char[] s = S.toCharArray();
		char[] t = T.toCharArray();
		return process(s, t, s.length, t.length);
	}

	public static int process(char[] s, char[] t, int i, int j) {
		if (j == 0) {
			return 1;
		}
		if (i == 0) {
			return 0;
		}
		int res = process(s, t, i - 1, j);
		if (s[i - 1] == t[j - 1]) {
			res += process(s, t, i - 1, j - 1);
		}
		return res;
	}

	// S[...i]的所有子序列中，包含多少个字面值等于T[...j]这个字符串的子序列
	// 记为dp[i][j]
	// 可能性1）S[...i]的所有子序列中，都不以s[i]结尾，则dp[i][j]肯定包含dp[i-1][j]
	// 可能性2）S[...i]的所有子序列中，都必须以s[i]结尾，
	// 这要求S[i] == T[j]，则dp[i][j]包含dp[i-1][j-1]
	public static int numDistinct2(String S, String T) {
		char[] s = S.toCharArray();
		char[] t = T.toCharArray();
		int[][] dp = new int[s.length + 1][t.length + 1];
		for (int j = 0; j <= t.length; j++) {
			dp[0][j] = 0;
		}
		for (int i = 0; i <= s.length; i++) {
			dp[i][0] = 1;
		}
		for (int i = 1; i <= s.length; i++) {
			for (int j = 1; j <= t.length; j++) {
				dp[i][j] = dp[i - 1][j] + (s[i - 1] == t[j - 1] ? dp[i - 1][j - 1] : 0);
			}
		}
		return dp[s.length][t.length];
	}

	public static int numDistinct3(String S, String T) {
		char[] s = S.toCharArray();
		char[] t = T.toCharArray();
		int[] dp = new int[t.length + 1];
		dp[0] = 1;
		for (int j = 1; j <= t.length; j++) {
			dp[j] = 0;
		}
		for (int i = 1; i <= s.length; i++) {
			for (int j = t.length; j >= 1; j--) {
				dp[j] += s[i - 1] == t[j - 1] ? dp[j - 1] : 0;
			}
		}
		return dp[t.length];
	}

}
