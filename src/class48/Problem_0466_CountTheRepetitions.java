package class48;

public class Problem_0466_CountTheRepetitions {

	public static int getMaxRepetitions(String str1, int n1, String str2, int n2) {
		int len1 = str1.length();
		int len2 = str2.length();
		if (len1 == 0 || len2 == 0 || len1 * n1 < len2 * n2) {
			return 0;
		}
		char[] s1 = str1.toCharArray();
		char[] s2 = str2.toCharArray();
		int[][] map = new int[len2][2];
		for (int i = 0; i < len2; i++) {
			map[i][0] = -1;
			map[i][1] = -1;
		}
		int ans = 0;
		for (int i1 = 0, i2 = 0; i1 / len1 < n1; i1++) {
			if (i1 % len1 == len1 - 1) {
				if (map[i2 % len2][0] != -1) {
					int cycleLen = i1 - map[i2 % len2][0];
					int cycNum2 = (i2 - map[i2 % len2][1]) / len2;
					int cycNum1 = cycleLen / len1;
					int cycle = (n1 - 1 - i1 / len1) / cycNum1;
					ans += cycle * cycNum2;
					i1 += cycle * cycleLen;
				} else {
					map[i2 % len2][0] = i1;
					map[i2 % len2][1] = i2;
				}
			}
			if (s1[i1 % len1] == s2[i2 % len2]) {
				if (i2 % len2 == len2 - 1) {
					ans++;
				}
				i2++;
			}
		}
		return ans / n2;
	}

}
