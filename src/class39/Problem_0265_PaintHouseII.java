package class39;

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
		int allMin1 = 0;
		int allEnd1 = -1;
		int allMin2 = 0;
		int allEnd2 = -1;
		for (int i = 0; i < N; i++) {
			int curMin1 = Integer.MAX_VALUE;
			int curEnd1 = -1;
			int curMin2 = Integer.MAX_VALUE;
			int curEnd2 = -1;
			for (int j = 0; j < K; j++) {
				if (j != allEnd1) {
					if (allMin1 + costs[i][j] <= curMin1) {
						curMin2 = curMin1;
						curEnd2 = curEnd1;
						curMin1 = allMin1 + costs[i][j];
						curEnd1 = j;
					} else if (allMin1 + costs[i][j] < curMin2) {
						curMin2 = allMin1 + costs[i][j];
						curEnd2 = j;
					}
				} else if (j != allEnd2) {
					if (allMin2 + costs[i][j] <= curMin1) {
						curMin2 = curMin1;
						curEnd2 = curEnd1;
						curMin1 = allMin2 + costs[i][j];
						curEnd1 = j;
					} else if (allMin2 + costs[i][j] < curMin2) {
						curMin2 = allMin2 + costs[i][j];
						curEnd2 = j;
					}
				}
			}
			allMin1 = curMin1;
			allEnd1 = curEnd1;
			allMin2 = curMin2;
			allEnd2 = curEnd2;
		}
		return allMin1;
	}

}
