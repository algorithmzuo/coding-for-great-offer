package class34;

import java.util.ArrayList;
import java.util.TreeMap;

public class Problem_0673_NumberOfLongestIncreasingSubsequence {

	public static int findNumberOfLIS(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		ArrayList<TreeMap<Integer, Integer>> dp = new ArrayList<>();
		for (int num : nums) {
			int L = 0;
			int R = dp.size() - 1;
			int find = dp.size();
			while (L <= R) {
				int mid = (L + R) / 2;
				if (dp.get(mid).firstKey() >= num) {
					find = mid;
					R = mid - 1;
				} else {
					L = mid + 1;
				}
			}
			int count = 1;
			if (find > 0) {
				TreeMap<Integer, Integer> lastMap = dp.get(find - 1);
				count = lastMap.get(lastMap.firstKey());
				if (lastMap.ceilingKey(num) != null) {
					count -= lastMap.get(lastMap.ceilingKey(num));
				}
			}
			if (find == dp.size()) {
				TreeMap<Integer, Integer> newMap = new TreeMap<Integer, Integer>();
				newMap.put(num, count);
				dp.add(newMap);
			} else {
				TreeMap<Integer, Integer> curMap = dp.get(find);
				curMap.put(num, curMap.get(curMap.firstKey()) + count);
			}
		}
		return dp.get(dp.size() - 1).firstEntry().getValue();
	}

}
