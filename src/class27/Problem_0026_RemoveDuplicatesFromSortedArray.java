package class27;

public class Problem_0026_RemoveDuplicatesFromSortedArray {

	public static int removeDuplicates(int[] nums) {
		if (nums == null) {
			return 0;
		}
		if (nums.length < 2) {
			return nums.length;
		}
		int done = 0;
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] != nums[done]) {
				nums[++done] = nums[i];
			}
		}
		return done + 1;
	}

}
