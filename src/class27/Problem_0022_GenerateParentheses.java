package class27;

import java.util.ArrayList;
import java.util.List;

public class Problem_0022_GenerateParentheses {

	public static List<String> generateParenthesis(int n) {
		char[] path = new char[n << 1];
		List<String> ans = new ArrayList<>();
		process(path, 0, 0, n, ans);
		return ans;
	}

	// 依次在path上填写决定
	// ( ( ) ) ( )....
	// 0 1 2 3 4 5
	// path[0...index-1]决定已经做完了
	// index位置上，( )
	public static void process(char[] path, int index, int leftMinusRight, int leftRest, List<String> ans) {
		if (index == path.length) {
			ans.add(String.valueOf(path));
		} else {
			if (leftRest > 0) {
				path[index] = '(';
				process(path, index + 1, leftMinusRight + 1, leftRest - 1, ans);
			}
			if (leftMinusRight > 0) {
				path[index] = ')';
				process(path, index + 1, leftMinusRight - 1, leftRest, ans);
			}
		}
	}

	// 不剪枝的做法
	public static List<String> generateParenthesis2(int n) {
		char[] path = new char[n << 1];
		List<String> ans = new ArrayList<>();
		process2(path, 0, ans);
		return ans;
	}

	public static void process2(char[] path, int index, List<String> ans) {
		if (index == path.length) {
			if (isValid(path)) {
				ans.add(String.valueOf(path));
			}
		} else {
			path[index] = '(';
			process2(path, index + 1, ans);
			path[index] = ')';
			process2(path, index + 1, ans);
		}
	}

	public static boolean isValid(char[] path) {
		int count = 0;
		for (char cha : path) {
			if (cha == '(') {
				count++;
			} else {
				count--;
			}
			if (count < 0) {
				return false;
			}
		}
		return count == 0;
	}

}
