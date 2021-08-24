package class36;

// 来自哈喽单车
// 本题是leetcode原题 : https://leetcode.com/problems/stone-game-iv/
public class Code11_StoneGameIV {

	public static boolean winnerSquareGame1(int n) {
		if (n == 0) {
			return false;
		}
		for (int i = 1; i * i <= n; i++) {
			if (!winnerSquareGame1(n - i * i)) {
				return true;
			}
		}
		return false;
	}

	public static boolean winnerSquareGame2(int n) {
		int[] dp = new int[n + 1];
		dp[0] = -1;
		return process2(n, dp);
	}

	public static boolean process2(int n, int[] dp) {
		if (dp[n] != 0) {
			return dp[n] == 1 ? true : false;
		}
		boolean ans = false;
		for (int i = 1; i * i <= n; i++) {
			if (!process2(n - i * i, dp)) {
				ans = true;
				break;
			}
		}
		dp[n] = ans ? 1 : -1;
		return ans;
	}

	public static boolean winnerSquareGame3(int n) {
		boolean[] dp = new boolean[n + 1];
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j * j <= i; j++) {
				if (!dp[i - j * j]) {
					dp[i] = true;
					break;
				}
			}
		}
		return dp[n];
	}

}
