package class38;

import java.util.ArrayList;
import java.util.List;

public class Problem_0448_FindAllNumbersDisappearedInAnArray {

	public static List<Integer> findDisappearedNumbers(int[] nums) {
		List<Integer> ans = new ArrayList<>();
		if (nums == null || nums.length == 0) {
			return ans;
		}
		int N = nums.length;
		for (int i = 0; i < N; i++) {
			// 从i位置出发，去玩下标循环怼
			walk(nums, i);
		}
		for (int i = 0; i < N; i++) {
			if (nums[i] != i + 1) {
				ans.add(i + 1);
			}
		}
		return ans;
	}

	public static void walk(int[] nums, int i) {
		while (nums[i] != i + 1) { // 不断从i发货
			int nexti = nums[i] - 1;
			if (nums[nexti] == nexti + 1) {
				break;
			}
			swap(nums, i, nexti);
		}
	}

	public static void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}

}
