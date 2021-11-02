package class51;

public class Problem_0875_KokoEatingBananas {

	public static int minEatingSpeed(int[] piles, int h) {
		int L = 1;
		int R = 0;
		for (int pile : piles) {
			R = Math.max(R, pile);
		}
		int ans = 0;
		int M = 0;
		while (L <= R) {
			M = L + ((R - L) >> 1);
			if (hours(piles, M) <= h) {
				ans = M;
				R = M - 1;
			} else {
				L = M + 1;
			}
		}
		return ans;
	}

	public static int hours(int[] piles, int speed) {
		int ans = 0;
		int offset = speed - 1;
		for (int pile : piles) {
			ans += (pile + offset) / speed;
		}
		return ans;
	}

}
