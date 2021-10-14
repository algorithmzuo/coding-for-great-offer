package class48;

import java.math.BigInteger;

public class Problem_0483_SmallestGoodBase {

	public static String smallestGoodBase(String str) {
		long n = Long.valueOf(str);
		BigInteger N = BigInteger.valueOf(n);
		for (int s = 62; s > 2; s--) {
			long l = 2;
			long r = n - 1;
			while (l <= r) {
				long m = l + ((r - l) >> 1);
				BigInteger M = BigInteger.valueOf(m);
				BigInteger ans = BigInteger.valueOf(1);
				BigInteger add = BigInteger.valueOf(m);
				for (int i = 1; i < s && ans.compareTo(N) < 0; i++) {
					ans = ans.add(add);
					add = add.multiply(M);
				}
				int comp = ans.compareTo(N);
				if (comp == 0) {
					return String.valueOf(m);
				} else if (comp < 0) {
					l = m + 1;
				} else {
					r = m - 1;
				}
			}
		}
		return String.valueOf(n - 1);
	}

}
