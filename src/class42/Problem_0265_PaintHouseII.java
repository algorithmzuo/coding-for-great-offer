package class42;

public class Problem_0265_PaintHouseII {

	// costs[i][k] i号房子用k颜色刷的花费
	// 要让0...N-1的房子相邻不同色
	// 返回最小花费
	public static int minCostII(int[][] costs) {
		int N = costs.length;
		if (N == 0) {
			return 0;
		}
		int K = costs[0].length;
		// 之前取得的最小代价、取得最小代价时的颜色
		int preMin1 = 0;
		int preEnd1 = -1;
		// 之前取得的次小代价、取得次小代价时的颜色
		int preMin2 = 0;
		int preEnd2 = -1;
		for (int i = 0; i < N; i++) { // i房子
			int curMin1 = Integer.MAX_VALUE;
			int curEnd1 = -1;
			int curMin2 = Integer.MAX_VALUE;
			int curEnd2 = -1;
			for (int j = 0; j < K; j++) { // j颜色！
				if (j != preEnd1) {
					if (preMin1 + costs[i][j] < curMin1) {
						curMin2 = curMin1;
						curEnd2 = curEnd1;
						curMin1 = preMin1 + costs[i][j];
						curEnd1 = j;
					} else if (preMin1 + costs[i][j] < curMin2) {
						curMin2 = preMin1 + costs[i][j];
						curEnd2 = j;
					}
				} else if (j != preEnd2) {
					if (preMin2 + costs[i][j] < curMin1) {
						curMin2 = curMin1;
						curEnd2 = curEnd1;
						curMin1 = preMin2 + costs[i][j];
						curEnd1 = j;
					} else if (preMin2 + costs[i][j] < curMin2) {
						curMin2 = preMin2 + costs[i][j];
						curEnd2 = j;
					}
				}
			}
			preMin1 = curMin1;
			preEnd1 = curEnd1;
			preMin2 = curMin2;
			preEnd2 = curEnd2;
		}
		return preMin1;
	}

}
