package class33;

public class Problem_0242_ValidAnagram {

	public static boolean isAnagram(String s, String t) {
		if (s.length() != t.length()) {
			return false;
		}
		char[] str1 = s.toCharArray();
		char[] str2 = t.toCharArray();
		int[] count = new int[256];
		for (char cha : str1) {
			count[cha]++;
		}
		for (char cha : str2) {
			if (--count[cha] < 0) {
				return false;
			}
		}
		return true;
	}

}
