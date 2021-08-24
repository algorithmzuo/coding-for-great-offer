package class36;

import java.util.Arrays;

// 来自腾讯
public class Code09_MinBoat {

	public static int minBoat(int[] arr, int limit) {
		Arrays.sort(arr);
		int odd = 0;
		int even = 0;
		for (int num : arr) {
			if ((num & 1) == 0) {
				even++;
			} else {
				odd++;
			}
		}
		int[] odds = new int[odd];
		int[] evens = new int[even];
		for (int i = arr.length - 1; i >= 0; i--) {
			if ((arr[i] & 1) == 0) {
				evens[--even] = arr[i];
			} else {
				odds[--odd] = arr[i];
			}
		}
		return min(odds, limit) + min(evens, limit);
	}

	public static int min(int[] arr, int limit) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		if (arr[N - 1] > limit) {
			return -1;
		}
		int lessR = -1;
		for (int i = N - 1; i >= 0; i--) {
			if (arr[i] <= (limit / 2)) {
				lessR = i;
				break;
			}
		}
		if (lessR == -1) {
			return N;
		}
		int L = lessR;
		int R = lessR + 1;
		int noUsed = 0;
		while (L >= 0) {
			int solved = 0;
			while (R < N && arr[L] + arr[R] <= limit) {
				R++;
				solved++;
			}
			if (solved == 0) {
				noUsed++;
				L--;
			} else {
				L = Math.max(-1, L - solved);
			}
		}
		int all = lessR + 1;
		int used = all - noUsed;
		int moreUnsolved = (N - all) - used;
		return used + ((noUsed + 1) >> 1) + moreUnsolved;
	}

}
