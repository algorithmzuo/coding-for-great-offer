package class49;

public class Problem_0440_KthSmallestInLexicographicalOrder {

	public static int[] offset = { 0, 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000 };

	public static int[] number = { 0, 1, 11, 111, 1111, 11111, 111111, 1111111, 11111111, 111111111, 1111111111 };

	public static int findKthNumber(int n, int k) {
		int len = len(n);
		int first = n / offset[len];
		int left = (first - 1) * number[len];
		int pick = 0;
		int already = 0;
		if (k <= left) {
			pick = (k + number[len] - 1) / number[len];
			already = (pick - 1) * number[len];
			return kth((pick + 1) * offset[len] - 1, len, k - already);
		}
		int mid = number[len - 1] + (n % offset[len]) + 1;
		if (k - left <= mid) {
			return kth(n, len, k - left);
		}
		k -= left + mid;
		len--;
		pick = (k + number[len] - 1) / number[len] + first;
		already = (pick - first - 1) * number[len];
		return kth((pick + 1) * offset[len] - 1, len, k - already);
	}

	public static int len(int n) {
		int len = 0;
		while (n != 0) {
			n /= 10;
			len++;
		}
		return len;
	}

	public static int kth(int max, int len, int kth) {
		boolean closeToMax = true;
		int ans = max / offset[len];
		while (--kth > 0) {
			max %= offset[len--];
			int pick = 0;
			if (!closeToMax) {
				pick = (kth - 1) / number[len];
				ans = ans * 10 + pick;
				kth -= pick * number[len];
			} else {
				int first = max / offset[len];
				int left = first * number[len];
				if (kth <= left) {
					closeToMax = false;
					pick = (kth - 1) / number[len];
					ans = ans * 10 + pick;
					kth -= pick * number[len];
					continue;
				}
				kth -= left;
				int mid = number[len - 1] + (max % offset[len]) + 1;
				if (kth <= mid) {
					ans = ans * 10 + first;
					continue;
				}
				closeToMax = false;
				kth -= mid;
				len--;
				pick = (kth + number[len] - 1) / number[len] + first;
				ans = ans * 10 + pick;
				kth -= (pick - first - 1) * number[len];
			}
		}
		return ans;
	}

}
