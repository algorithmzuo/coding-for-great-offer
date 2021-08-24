package class36;

// 来自美团
public class Code04_ComputeExpressionValue {

	public static int sores(String s) {
		return compute(s.toCharArray(), 0)[0] - 1;
	}

	public static int[] compute(char[] s, int i) {
		if (s[i] == ')') {
			return new int[] { 2, i };
		}
		int ans = 1;
		while (i < s.length && s[i] != ')') {
			int[] info = compute(s, i + 1);
			ans *= info[0];
			i = info[1] + 1;
		}
		return new int[] { ans + 1, i };
	}

	public static void main(String[] args) {
		// (()()) -> 2 * 2 + 1 -> 5
		// (((()))) -> 5
		// ( (()) () ) -> (3 * 2) + 1 -> 7
		// 所以下面的结果应该是175
		String str = "(()())(((())))((())())";
		System.out.println(sores(str));
	}

}
