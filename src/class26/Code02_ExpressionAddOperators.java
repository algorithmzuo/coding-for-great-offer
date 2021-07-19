package class26;

import java.util.LinkedList;
import java.util.List;

// 本题测试链接 : https://leetcode.com/problems/expression-add-operators/
public class Code02_ExpressionAddOperators {

	public static List<String> addOperators(String num, int target) {
		List<String> ret = new LinkedList<>();
		if (num.length() == 0) {
			return ret;
		}
		// 沿途的数字拷贝和+ - * 的决定，放在path里
		char[] path = new char[num.length() * 2 - 1];
		// num -> char[]
		char[] digits = num.toCharArray();

		long n = 0;
		for (int i = 0; i < digits.length; i++) { // 尝试0~i前缀作为第一部分
			n = n * 10 + digits[i] - '0';
			path[i] = digits[i];
			dfs(ret, path, i + 1, 0, n, digits, i + 1, target); // 后续过程
			if (n == 0) {
				break;
			}
		}
		return ret;
	}

	// char[] digits 固定参数，字符类型数组，等同于num
	// int target 目标
	// char[] path 之前做的决定，已经从左往右依次填写的字符在其中，可能含有'0'~'9' 与 * - +
	// int len path[0..len-1]已经填写好，len是终止
	// int pos 字符类型数组num, 使用到了哪
	// left -> 前面固定的部分 cur -> 前一块
	// 默认 left + cur ...
	public static void dfs(List<String> res, char[] path, int len, long left, // 已经结算的部分
			long cur, // 待定的部分(上一块的值)
			char[] num, int index, int aim) {
		if (index == num.length) {
			if (left + cur == aim) {
				res.add(new String(path, 0, len));
			}
			return;
		}
		long n = 0;
		int j = len + 1;
		for (int i = index; i < num.length; i++) { // pos ~ i
			n = n * 10 + num[i] - '0';
			path[j++] = num[i];
			path[len] = '+';
			dfs(res, path, j, left + cur, n, num, i + 1, aim);
			path[len] = '-';
			dfs(res, path, j, left + cur, -n, num, i + 1, aim);
			path[len] = '*';
			dfs(res, path, j, left, cur * n, num, i + 1, aim);
			if (num[index] == '0') {
				break;
			}
		}
	}

}
