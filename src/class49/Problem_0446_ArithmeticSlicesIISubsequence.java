package class49;

import java.util.ArrayList;
import java.util.HashMap;

public class Problem_0446_ArithmeticSlicesIISubsequence {

	// 时间复杂度是O(N^2)，最优解的时间复杂度
	public static int numberOfArithmeticSlices(int[] arr) {
		int N = arr.length;
		int ans = 0;
		ArrayList<HashMap<Integer, Integer>> maps = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			maps.add(new HashMap<>());
			//  ....j...i（结尾）
			for (int j = i - 1; j >= 0; j--) {
				long diff = (long) arr[i] - (long) arr[j];
				if (diff <= Integer.MIN_VALUE || diff > Integer.MAX_VALUE) {
					continue;
				}
				int dif = (int) diff;
				int count = maps.get(j).getOrDefault(dif, 0);
				ans += count;
				maps.get(i).put(dif, maps.get(i).getOrDefault(dif, 0) + count + 1);
			}
		}
		return ans;
	}

}
