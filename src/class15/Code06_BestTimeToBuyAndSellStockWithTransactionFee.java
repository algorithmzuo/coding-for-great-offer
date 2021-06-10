package class15;

//leetcode 714
public class Code06_BestTimeToBuyAndSellStockWithTransactionFee {

	public static int maxProfit(int[] arr, int fee) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int N = arr.length;
		// 0..0   0 -[0] - fee
		int bestbuy = -arr[0] - fee;
		// 0..0  卖  0
		int bestsell = 0;
		for (int i = 1; i < N; i++) {
			// 来到i位置了！
			// 如果在i必须买  收入 - 批发价 - fee
			int curbuy = bestsell - arr[i] - fee;
			// 如果在i必须卖  整体最优（收入 - 良好批发价 - fee）
			int cursell = bestbuy + arr[i];
			bestbuy = Math.max(bestbuy, curbuy);
			bestsell = Math.max(bestsell, cursell);
		}
		return bestsell;
	}

}
