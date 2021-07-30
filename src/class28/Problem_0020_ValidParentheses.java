package class28;

public class Problem_0020_ValidParentheses {

	public static boolean isValid(String s) {
		if (s == null || s.length() == 0) {
			return true;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		char[] stack = new char[N];
		int size = 0;
		for (int i = 0; i < N; i++) {
			char cha = str[i];
			if (cha == '(' || cha == '[' || cha == '{') {
				stack[size++] = cha == '(' ? ')' : (cha == '[' ? ']' : '}');
			} else {
				if (size == 0) {
					return false;
				}
				char last = stack[--size];
				if (cha != last) {
					return false;
				}
			}
		}
		return size == 0;
	}

}
