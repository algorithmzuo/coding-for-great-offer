package class12;

// 测试链接 : https://leetcode.com/problems/regular-expression-matching/
public class Code04_RegularExpressionMatch {

	public static boolean isValid(char[] s, char[] e) {
		// s中不能有'.' or '*'
		for (int i = 0; i < s.length; i++) {
			if (s[i] == '*' || s[i] == '.') {
				return false;
			}
		}
		// 开头的e[0]不能是'*'，没有相邻的'*'
		for (int i = 0; i < e.length; i++) {
			if (e[i] == '*' && (i == 0 || e[i - 1] == '*')) {
				return false;
			}
		}
		return true;
	}

	// 初始尝试版本，不包含斜率优化
	public static boolean isMatch1(String str, String exp) {
		if (str == null || exp == null) {
			return false;
		}
		char[] s = str.toCharArray();
		char[] e = exp.toCharArray();
		return isValid(s, e) && process(s, e, 0, 0);
	}

	public static boolean process(char[] s, char[] e, int si, int ei) {
		if (ei == e.length) {
			return si == s.length;
		}
		if (ei + 1 == e.length || e[ei + 1] != '*') {
			return si != s.length && (e[ei] == s[si] || e[ei] == '.') && process(s, e, si + 1, ei + 1);
		}
		while (si != s.length && (e[ei] == s[si] || e[ei] == '.')) {
			if (process(s, e, si, ei + 2)) {
				return true;
			}
			si++;
		}
		return process(s, e, si, ei + 2);
	}

	// 动态规划版本 + 斜率优化
	public static boolean isMatch2(String str, String pattern) {
		if (str == null || pattern == null) {
			return false;
		}
		char[] s = str.toCharArray();
		char[] p = pattern.toCharArray();
		if (!isValid(s, p)) {
			return false;
		}
		int N = s.length;
		int M = p.length;
		boolean[][] dp = new boolean[N + 1][M + 1];
		dp[N][M] = true;
		for (int j = M - 1; j >= 0; j--) {
			dp[N][j] = (j + 1 < M && p[j + 1] == '*') && dp[N][j + 2];
		}
		// dp[0..N-2][M-1]都等于false，只有dp[N-1][M-1]需要讨论
		if (N > 0 && M > 0) {
			dp[N - 1][M - 1] = (s[N - 1] == p[M - 1] || p[M - 1] == '.');
		}
		for (int i = N - 1; i >= 0; i--) {
			for (int j = M - 2; j >= 0; j--) {
				if (p[j + 1] != '*') {
					dp[i][j] = ((s[i] == p[j]) || (p[j] == '.')) && dp[i + 1][j + 1];
				} else {
					if ((s[i] == p[j] || p[j] == '.') && dp[i + 1][j]) {
						dp[i][j] = true;
					} else {
						dp[i][j] = dp[i][j + 2];
					}
				}
			}
		}
		return dp[0][0];
	}

}
