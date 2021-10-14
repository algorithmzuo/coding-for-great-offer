package class48;

public class Problem_0548_SplitArrayEithEqualSum {

	public static boolean splitArray(int[] nums) {
		if (nums.length < 7) {
			return false;
		}
		int[] sumLeftToRight = new int[nums.length];
		int[] sumRightToLeft = new int[nums.length];
		int s = 0;
		for (int i = 0; i < nums.length; i++) {
			sumLeftToRight[i] = s;
			s += nums[i];
		}
		s = 0;
		for (int i = nums.length - 1; i >= 0; i--) {
			sumRightToLeft[i] = s;
			s += nums[i];
		}
		for (int i = 1; i < nums.length - 5; i++) {
			for (int j = nums.length - 2; j > i + 3; j--) {
				if (sumLeftToRight[i] == sumRightToLeft[j] && find(sumLeftToRight, sumRightToLeft, i, j)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean find(int[] sumLeftToRight, int[] sumRightToLeft, int l, int r) {
		int s = sumLeftToRight[l];
		int prefix = sumLeftToRight[l + 1];
		int suffix = sumRightToLeft[r - 1];
		for (int i = l + 2; i < r - 1; i++) {
			int s1 = sumLeftToRight[i] - prefix;
			int s2 = sumRightToLeft[i] - suffix;
			if (s1 == s2 && s1 == s) {
				return true;
			}
		}
		return false;
	}

}
