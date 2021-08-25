package class36;

// 来自美团
public class Code04_ComputeExpressionValue {

	public static int sores(String s) {
		return compute(s.toCharArray(), 0)[0];
	}

	public static int[] compute(char[] s, int i) {
		if (s[i] == ')') {
			return new int[] { 1, i };
		}
		int ans = 1;
		while (i < s.length && s[i] != ')') {
			int[] info = compute(s, i + 1);
			ans *= info[0] + 1;
			i = info[1] + 1;
		}
		return new int[] { ans, i };
	}

	public static void main(String[] args) {
		// (()()) + (((()))) + ((())())
		// (()()) -> 2 * 2 + 1 -> 5
		// (((()))) -> 5
		// ((())()) -> ((2 + 1) * 2) + 1 -> 7
		// 所以下面的结果应该是175
		String str1 = "(()())(((())))((())())";
		System.out.println(sores(str1));

		// (()()()) + (()(()))
		// (()()()) -> 2 * 2 * 2 + 1 -> 9
		// (()(())) -> 2 * 3 + 1 -> 7
		// 所以下面的结果应该是63
		String str2 = "(()()())(()(()))";
		System.out.println(sores(str2));
	}

}
