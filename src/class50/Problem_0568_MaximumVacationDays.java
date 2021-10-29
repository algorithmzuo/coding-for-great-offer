package class50;

public class Problem_0568_MaximumVacationDays {

	public static int maxVacationDays(int[][] fly, int[][] day) {
		int n = fly.length;
		int k = day[0].length;
		// pas[i] = {a, b, c}
		// 从a、b、c能飞到i
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
		// dp[i][j] -> 第i周必须在j这座城，0~i-1周（随意），最大休假天数
		int[][] dp = new int[k][n];
		// 飞的时机，是周一早上飞，认为对时间没有影响，直接到某个城，然后过一周
		dp[0][0] = day[0][0];
		for (int j = 1; j < n; j++) {
			dp[0][j] = fly[0][j] != 0 ? day[j][0] : -1;
		}
		for (int i = 1; i < k; i++) { // 第i周
			for (int j = 0; j < n; j++) { // 在j号城过！
				// 第i周，要怎么到j号城
				// 下面max的初始值，我第i-1周，就在j号城，选择不动地方，进入第i周
				int max = dp[i - 1][j];
				for (int p : pass[j]) { // 枚举什么？能到j号城的城市p
					max = Math.max(max, dp[i - 1][p]);
				}
				dp[i][j] = max != -1 ? max + day[j][i] : -1;
			}
		}
		int ans = 0;
		for (int i = 0; i < n; i++) {
			ans = Math.max(ans, dp[k - 1][i]);
		}
		return ans;
	}

}
