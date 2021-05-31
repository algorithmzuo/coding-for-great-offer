package class15;

//leetcode 714
public class Code06_BestTimeToBuyAndSellStockWithTransactionFee {

	public static int maxProfit(int[] arr, int fee) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int N = arr.length;
		int bestbuy = -arr[0] - fee;
		int bestsell = 0;
		for (int i = 1; i < N; i++) {
			int curbuy = bestsell - arr[i] - fee;
			int cursell = bestbuy + arr[i];
			bestbuy = Math.max(bestbuy, curbuy);
			bestsell = Math.max(bestsell, cursell);
		}
		return bestsell;
	}

}
