package class52;

public class Problem_0683_KEmptySlots {

	public static int kEmptySlots(int[] bulbs, int k) {
		int n = bulbs.length;
		int[] days = new int[n];
		for (int i = 0; i < n; i++) {
			days[bulbs[i] - 1] = i + 1;
		}
		int left = 0, right = k + 1, res = Integer.MAX_VALUE;
		for (int mid = 1; right < n; mid++) {
			if (days[mid] <= Math.max(days[left], days[right])) {
				if (mid == right) {
					res = Math.min(res, Math.max(days[left], days[right]));
				}
				left = mid;
				right = mid + k + 1;
			}
		}
		return (res == Integer.MAX_VALUE) ? -1 : res;
	}

}
