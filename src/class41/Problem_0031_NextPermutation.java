package class41;

public class Problem_0031_NextPermutation {

	public static void nextPermutation(int[] nums) {
		int N = nums.length;
		int firstLess = -1;
		for (int i = N - 2; i >= 0; i--) {
			if (nums[i] < nums[i + 1]) {
				firstLess = i;
				break;
			}
		}
		if (firstLess < 0) {
			reverse(nums, 0, N - 1);
		} else {
			int rightClosestMore = -1;
			// 找最靠右的、同时比nums[firstLess]大的数，位置在哪
			// 这里其实也可以用二分优化，但是这种优化无关紧要了
			for (int i = N - 1; i > firstLess; i--) {
				if (nums[i] > nums[firstLess]) {
					rightClosestMore = i;
					break;
				}
			}
			swap(nums, firstLess, rightClosestMore);
			reverse(nums, firstLess + 1, N - 1);
		}
	}

	public static void reverse(int[] nums, int L, int R) {
		while (L < R) {
			swap(nums, L++, R--);
		}
	}

	public static void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}

}
