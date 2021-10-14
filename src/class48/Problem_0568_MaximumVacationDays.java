package class48;

public class Problem_0568_MaximumVacationDays {

	public static int maxVacationDays(int[][] fly, int[][] day) {
		int n = fly.length;
		int k = day[0].length;
		int[][] pass = new int[n][];
		for (int i = 0; i < n; i++) {
			int s = 0;
			for (int j = 0; j < n; j++) {
				if (fly[j][i] != 0) {
					s++;
				}
			}
			pass[i] = new int[s];
			for (int j = n - 1; j >= 0; j--) {
				if (fly[j][i] != 0) {
					pass[i][--s] = j;
				}
			}
		}
		int[][] dp = new int[k][n];
		dp[0][0] = day[0][0];
		for (int j = 1; j < n; j++) {
			dp[0][j] = fly[0][j] != 0 ? day[j][0] : -1;
		}
		for (int j = 1; j < k; j++) {
			for (int i = 0; i < n; i++) {
				int max = dp[j - 1][i];
				for (int p : pass[i]) {
					max = Math.max(max, dp[j - 1][p]);
				}
				dp[j][i] = max != -1 ? max + day[i][j] : -1;
			}
		}
		int ans = 0;
		for (int i = 0; i < n; i++) {
			ans = Math.max(ans, dp[k - 1][i]);
		}
		return ans;
	}

}
