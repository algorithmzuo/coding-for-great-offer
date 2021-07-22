package class27;

import java.util.HashMap;

public class Problem_0001_TwoSum {

	public static int[] twoSum(int[] nums, int target) {
		// key 某个之前的数   value 这个数出现的位置
		HashMap<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			if (map.containsKey(target - nums[i])) {
				return new int[] { map.get(target - nums[i]), i };
			}
			map.put(nums[i], i);
		}
		return new int[] { -1, -1 };
	}

}
