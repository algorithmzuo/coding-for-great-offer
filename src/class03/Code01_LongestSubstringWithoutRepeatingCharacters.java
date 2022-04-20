package class03;

// 本题测试链接 : https://leetcode.com/problems/longest-substring-without-repeating-characters/
public class Code01_LongestSubstringWithoutRepeatingCharacters {

	public static int lengthOfLongestSubstring(String s) {
		if (s == null || s.equals("")) {
			return 0;
		}
		char[] str = s.toCharArray();
		int[] map = new int[256];
		for (int i = 0; i < 256; i++) {
			map[i] = -1;
		}
		map[str[0]] = 0;
		int N = str.length;
		int ans = 1;
		int pre = 1;
		for (int i = 1; i < N; i++) {
			// 假设后面的字符都不重复，最大值为pre+N-i，若还小于以求的最大值，则后面的位置不需要计算
            if (pre + N - i < longestLength) {
                break;
            }
			pre = Math.min(i - map[str[i]], pre + 1);
			ans = Math.max(ans, pre);
			map[str[i]] = i;
		}
		return ans;
	}

}
