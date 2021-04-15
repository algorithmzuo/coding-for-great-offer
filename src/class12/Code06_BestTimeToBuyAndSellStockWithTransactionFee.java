package class12;

//leetcode 714
public class Code06_BestTimeToBuyAndSellStockWithTransactionFee {

	public static int maxProfit(int[] arr, int fee) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int N = arr.length;
		int ans = 0;
		int buy = -arr[0] - fee;
		int sell = 0;
		for (int i = 1; i < N; i++) {
			int curbuy = sell - arr[i] - fee;
			int cursell = buy + arr[i];
			ans = Math.max(ans, cursell);
			buy = Math.max(buy, curbuy);
			sell = Math.max(sell, cursell);
		}
		return ans;
	}

}
