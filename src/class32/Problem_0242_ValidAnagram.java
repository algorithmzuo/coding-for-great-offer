package class32;

public class Problem_0242_ValidAnagram {

	public static boolean isAnagram(String s, String t) {
		if (s.length() != t.length()) {
			return false;
		}
		char[] str = s.toCharArray();
		char[] tar = t.toCharArray();
		int[] count = new int[256];
		for (char cha : str) {
			count[cha]++;
		}
		for (char cha : tar) {
			if (--count[cha] < 0) {
				return false;
			}
		}
		return true;
	}

}
