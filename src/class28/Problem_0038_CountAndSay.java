package class28;

public class Problem_0038_CountAndSay {

	public static String countAndSay(int n) {
		if (n < 1) {
			return "";
		}
		if (n == 1) {
			return "1";
		}
		char[] last = countAndSay(n - 1).toCharArray();
		StringBuilder ans = new StringBuilder();
		int times = 1;
		for (int i = 1; i < last.length; i++) {
			if (last[i - 1] == last[i]) {
				times++;
			} else {
				ans.append(String.valueOf(times));
				ans.append(String.valueOf(last[i - 1]));
				times = 1;
			}
		}
		ans.append(String.valueOf(times));
		ans.append(String.valueOf(last[last.length - 1]));
		return ans.toString();
	}

	public static void main(String[] args) {
		System.out.println(countAndSay(20));
	}

}
