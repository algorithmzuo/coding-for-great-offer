package class25;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 本题测试链接 : https://leetcode.com/problems/3sum/
public class Code02_3Sum {

	public static List<List<Integer>> threeSum(int[] nums) {
		Arrays.sort(nums);
		int N = nums.length;
		List<List<Integer>> ans = new ArrayList<>();
		for (int i = N - 1; i > 1; i--) { // 三元组最后一个数，是arr[i]   之前....二元组 + arr[i]
			if (i == N - 1 || nums[i] != nums[i + 1]) {
				List<List<Integer>> nexts = twoSum(nums, i - 1, -nums[i]);
				for (List<Integer> cur : nexts) {
					cur.add(nums[i]);
					ans.add(cur);
				}
			}
		}
		return ans;
	}

	// nums[0...end]这个范围上，有多少个不同二元组，相加==target，全返回
	// {-1,5}     K = 4
	// {1, 3}
	public static List<List<Integer>> twoSum(int[] nums, int end, int target) {
		int L = 0;
		int R = end;
		List<List<Integer>> ans = new ArrayList<>();
		while (L < R) {
			if (nums[L] + nums[R] > target) {
				R--;
			} else if (nums[L] + nums[R] < target) {
				L++;
			} else { // nums[L] + nums[R] == target
				if (L == 0 || nums[L - 1] != nums[L]) {
					List<Integer> cur = new ArrayList<>();
					cur.add(nums[L]);
					cur.add(nums[R]);
					ans.add(cur);
				}
				L++;
			}
		}
		return ans;
	}

	public static int findPairs(int[] nums, int k) {
		Arrays.sort(nums);
		int left = 0, right = 1;
		int result = 0;
		while (left < nums.length && right < nums.length) {
			if (left == right || nums[right] - nums[left] < k) {
				right++;
			} else if (nums[right] - nums[left] > k) {
				left++;
			} else {
				left++;
				result++;
				while (left < nums.length && nums[left] == nums[left - 1])
					left++;
			}
		}
		return result;
	}

}
