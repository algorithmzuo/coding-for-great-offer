package class50;

public class Problem_0600_NonnegativeIntegersWithoutConsecutiveOnes {

	public static int findIntegers(int n) {
		int i = 31;
		for (; i >= 0; i--) {
			if ((n & (1 << i)) != 0) {
				break;
			}
		}
		int[][][] dp = new int[2][2][i + 1];
		return f(0, 0, i, n, dp);
	}

	public static int f(int p, int o, int i, int c, int[][][] dp) {
		if (i == -1) {
			return 1;
		}
		if (dp[p][o][i] != 0) {
			return dp[p][o][i];
		}
		int ans = 0;
		if (p == 1) {
			ans = f(0, Math.max(o, (c & (1 << i)) != 0 ? 1 : 0), i - 1, c, dp);
		} else {
			if ((c & (1 << i)) == 0 && o == 0) {
				ans = f(0, o, i - 1, c, dp);
			} else {
				ans = f(1, o, i - 1, c, dp) + f(0, Math.max(o, (c & (1 << i)) != 0 ? 1 : 0), i - 1, c, dp);
			}
		}
		dp[p][o][i] = ans;
		return ans;
	}

}
