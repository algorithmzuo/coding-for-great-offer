package class46;

public class Problem_0440_KthSmallestInLexicographicalOrder {

	public static int[] k0 = { 0, 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000 };

	public static int[] k1 = { 0, 1, 11, 111, 1111, 11111, 111111, 1111111, 11111111, 111111111, 1111111111 };

	public static int findKthNumber(int n, int k) {
		int len = len(n);
		int first = n / k0[len];
		int left = (first - 1) * k1[len];
		int prefix = 0;
		int pre = 0;
		if (k <= left) {
			prefix = ((k - 1) / k1[len]) + 1;
			pre = (prefix - 1) * k1[len];
			return kth(prefix * k0[len] - 1 + k0[len], len, k - pre);
		}
		int mid = k1[len - 1] + (n % k0[len]) + 1;
		if (k - left <= mid) {
			return kth(n, len, k - left);
		}
		k -= left + mid;
		len--;
		prefix = ((k - 1) / k1[len]) + first + 1;
		pre = (prefix - first - 1) * k1[len];
		return kth(prefix * k0[len] - 1 + k0[len], len, k - pre);
	}

	// 求一个正数有几个十进制位
	public static int len(int n) {
		int len = 0;
		while (n != 0) {
			n /= 10;
			len++;
		}
		return len;
	}

	public static int kth(int limit, int len, int k) {
		boolean touchLimit = true;
		int offset = k0[len];
		int prefix = limit / offset;
		int chooseNum = 0;
		int limitNum = 0;
		int left = 0;
		int mid = 0;
		for (len--, k--, limit %= offset, offset /= 10; k > 0; len--, k--, limit %= offset, offset /= 10) {
			if (!touchLimit) {
				chooseNum = (k - 1) / k1[len];
				prefix = prefix * 10 + chooseNum;
				k -= chooseNum * k1[len];
			} else {
				limitNum = offset > limit ? 0 : (limit / offset);
				left = limitNum * k1[len];
				if (k <= left) {
					touchLimit = false;
					chooseNum = (k - 1) / k1[len];
					prefix = prefix * 10 + chooseNum;
					k -= chooseNum * k1[len];
					continue;
				}
				k -= left;
				mid = k1[len - 1] + (limit % k0[len]) + 1;
				if (k <= mid) {
					prefix = prefix * 10 + limitNum;
					continue;
				}
				touchLimit = false;
				k -= mid;
				len--;
				chooseNum = (k - 1) / k1[len] + limitNum + 1;
				prefix = prefix * 10 + chooseNum;
				k -= (chooseNum - limitNum - 1) * k1[len];
			}
		}
		return prefix;
	}

}
