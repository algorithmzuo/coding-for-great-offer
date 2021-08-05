package class31;

public class Problem_0162_FindPeakElement {

	public static int findPeakElement(int[] nums) {
		int N = nums.length;
		if (N < 2) {
			return 0;
		}
		if (nums[0] > nums[1]) {
			return 0;
		}
		if (nums[N - 1] > nums[N - 2]) {
			return N - 1;
		}
		int L = 1;
		int R = N - 2;
		int M = 0;
		while (L < R) {
			M = (L + R) / 2;
			if (nums[M - 1] < nums[M] && nums[M] > nums[M + 1]) {
				return M;
			} else if (nums[M - 1] > nums[M]) {
				R = M - 1;
			} else {
				L = M + 1;
			}
		}
		return L;
	}

}
