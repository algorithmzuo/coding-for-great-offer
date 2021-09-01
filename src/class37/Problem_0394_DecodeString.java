package class37;

public class Problem_0394_DecodeString {

	public static String decodeString(String s) {
		char[] str = s.toCharArray();
		return process(str, 0).ans;
	}

	public static class Info {
		public String ans;
		public int stop;

		public Info(String a, int e) {
			ans = a;
			stop = e;
		}
	}

	// s[i....]  何时停？遇到   ']'  或者遇到 s的终止位置，停止
	// 返回Info
	// 0) 串
	// 1) 算到了哪
	public static Info process(char[] s, int i) {
		StringBuilder ans = new StringBuilder();
		int count = 0;
		while (i < s.length && s[i] != ']') {
			if ((s[i] >= 'a' && s[i] <= 'z') || (s[i] >= 'A' && s[i] <= 'Z')) {
				ans.append(s[i++]);
			} else if (s[i] >= '0' && s[i] <= '9') {
				count = count * 10 + s[i++] - '0';
			} else { // str[index] = '['
				Info next = process(s, i + 1);
				ans.append(timesString(count, next.ans));
				count = 0;
				i = next.stop + 1;
			}
		}
		return new Info(ans.toString(), i);
	}

	public static String timesString(int times, String str) {
		StringBuilder ans = new StringBuilder();
		for (int i = 0; i < times; i++) {
			ans.append(str);
		}
		return ans.toString();
	}

}
