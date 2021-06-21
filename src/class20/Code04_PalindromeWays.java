package class20;

public class Code04_PalindromeWays {

	public static int way1(String str) {
		char[] s = str.toCharArray();
		int len = s.length;
		int[][] dp = new int[len + 1][len + 1];
		for (int i = 0; i <= len; i++) {
			dp[i][i] = 1;
		}
		// dp[i][j]，在空串不算回文串的情况下，求str[i..j]有多少不同的回文子序列
		// index -> dp
		// str[index-1]
		// dp 1 str 0
		// dp 2 str 1
		for (int subLen = 2; subLen <= len; subLen++) {
			for (int l = 1; l <= len - subLen + 1; l++) {
				int r = l + subLen - 1;
				dp[l][r] += dp[l + 1][r];
				dp[l][r] += dp[l][r - 1];
				if (s[l - 1] == s[r - 1])
					dp[l][r] += 1;
				else
					dp[l][r] -= dp[l + 1][r - 1];
			}
		}
		return dp[1][len];
	}

	public static int way2(String str) {
		char[] s = str.toCharArray();
		int n = s.length;
		int[][] dp = new int[n][n];
		for (int i = 0; i < n; i++) {
			dp[i][i] = 1;
		}
		for (int i = 0; i < n - 1; i++) {
			dp[i][i + 1] = s[i] == s[i + 1] ? 3 : 2;
		}
		for (int L = n - 3; L >= 0; L--) {
			for (int R = L + 2; R < n; R++) {
				dp[L][R] = dp[L + 1][R] + dp[L][R - 1] - dp[L + 1][R - 1];
				if (s[L] == s[R]) {
					dp[L][R] += dp[L + 1][R - 1] + 1;
				}
			}
		}
		return dp[0][n - 1];
	}

	public static void main(String[] args) {
		System.out.println(way1("ABA"));
		System.out.println(way1("XXY"));

		System.out.println(way2("ABA"));
		System.out.println(way2("XXY"));
	}

}
