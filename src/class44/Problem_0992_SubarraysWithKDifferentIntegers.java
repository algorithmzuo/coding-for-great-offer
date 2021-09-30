package class44;

import java.util.HashMap;

public class Problem_0992_SubarraysWithKDifferentIntegers {

	// nums 数组，题目规定，nums中的数字，不会超过nums的长度
	// [ ]长度为5，0~5
	public static int subarraysWithKDistinct1(int[] nums, int k) {
		int n = nums.length;
		// k-1种数的窗口词频统计
		int[] lessCounts = new int[n + 1];
		// k种数的窗口词频统计
		int[] equalCounts = new int[n + 1];
		int lessLeft = 0;
		int equalLeft = 0;
		int lessKinds = 0;
		int equalKinds = 0;
		int ans = 0;
		for (int r = 0; r < n; r++) {
			// 当前刚来到r位置！
			if (lessCounts[nums[r]] == 0) {
				lessKinds++;
			}
			if (equalCounts[nums[r]] == 0) {
				equalKinds++;
			}
			lessCounts[nums[r]]++;
			equalCounts[nums[r]]++;
			while (lessKinds == k) {
				if (lessCounts[nums[lessLeft]] == 1) {
					lessKinds--;
				}
				lessCounts[nums[lessLeft++]]--;
			}
			while (equalKinds > k) {
				if (equalCounts[nums[equalLeft]] == 1) {
					equalKinds--;
				}
				equalCounts[nums[equalLeft++]]--;
			}
			ans += lessLeft - equalLeft;
		}
		return ans;
	}

	public static int subarraysWithKDistinct2(int[] arr, int k) {
		return numsMostK(arr, k) - numsMostK(arr, k - 1);
	}

	public static int numsMostK(int[] arr, int k) {
		int i = 0, res = 0;
		HashMap<Integer, Integer> count = new HashMap<>();
		for (int j = 0; j < arr.length; ++j) {
			if (count.getOrDefault(arr[j], 0) == 0) {
				k--;
			}
			count.put(arr[j], count.getOrDefault(arr[j], 0) + 1);
			while (k < 0) {
				count.put(arr[i], count.get(arr[i]) - 1);
				if (count.get(arr[i]) == 0) {
					k++;
				}
				i++;
			}
			res += j - i + 1;
		}
		return res;
	}

}
