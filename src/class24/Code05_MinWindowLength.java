package class24;

public class Code05_MinWindowLength {

	public static int minLength(String s1, String s2) {
		if (s1 == null || s2 == null || s1.length() < s2.length()) {
			return Integer.MAX_VALUE;
		}
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();
		int[] map = new int[256]; // map[37] = 4  37  4次
		for (int i = 0; i != str2.length; i++) {
			map[str2[i]]++;
		}
		int all = str2.length;
		
		// [L,R-1]  R
		// [L,R)  -> [0,0)
		int L = 0;
		int R = 0;
		int minLen = Integer.MAX_VALUE;
		while (R != str1.length) {
			map[str1[R]]--;
			if (map[str1[R]] >= 0) {
				all--;
			}
			if (all == 0) { // 还完了
				while (map[str1[L]] < 0) {
					map[str1[L++]]++;
				}
				// [L..R]
				minLen = Math.min(minLen, R - L + 1);
				all++;
				map[str1[L++]]++;
			}
			R++;
		}
		return minLen == Integer.MAX_VALUE ? 0 : minLen;
	}

	public static void main(String[] args) {
		String str1 = "adabbca";
		String str2 = "acb";
		System.out.println(minLength(str1, str2));

	}

}
