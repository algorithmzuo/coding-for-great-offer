package class25;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Code02_ExpressionAddOperators {

	public static List<String> getAllResult(String num, int target) {
		List<String> ans = new ArrayList<>();
		char[] str = num.toCharArray();
		process(str, 0, target, "", ans);
		return ans;

	}

	public static void process(char[] str, int index, int target, String path, List<String> ans) {
		if (index == str.length) {
			char last = path.charAt(path.length() - 1);
			path = (last == '+' || last == '-' || last == '*') ? path.substring(0, path.length() - 1) : path;
			if (check(path, target)) {
				ans.add(path);
			}
			return;
		}
		String p0 = String.valueOf(str[index]);
		String p1 = p0 + "+";
		String p2 = p0 + "-";
		String p3 = p0 + "*";
		process(str, index + 1, target, path + p0, ans);
		process(str, index + 1, target, path + p1, ans);
		process(str, index + 1, target, path + p2, ans);
		process(str, index + 1, target, path + p3, ans);
	}

	// path是正常的公式字符串，检查是否计算的过程是否和target一样
	public static boolean check(String path, int target) {
		// 黑盒
		return true;
	}

	public static int ways(String num, int target) {
		char[] str = num.toCharArray();
		int first = str[0] - '0';
		return f(str, 1, 0, first, target);
	}

	public static int f(char[] str, int index, int left, int cur, int target) {
		if (index == str.length) {
			return (left + cur) == target ? 1 : 0;
		}
		int ways = 0;
		int num = str[index]-'0';
		// 第一个决定 +
		ways += f(str, index+1, left + cur, num, target);
		// 第二个决定-
		ways += f(str, index+1, left + cur, -num, target);
		// 第三个决定*
		ways += f(str, index+1, left, cur * num , target);
		// 第四个决定不加符号
		if(cur != 0) {
			if(cur > 0) {
				ways += f(str, index+1, left, cur * 10 + num , target);
			}else {
				ways += f(str, index+1, left, cur * 10 - num , target);
			}
		}
		return ways;
	}

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
	public static void dfs(List<String> res, char[] path, int len,

			long left, // 已经结算的部分

			long cur, // 待定的部分(上一块的值)

			char[] num, int index,

			int aim) {
		if (index == num.length) {
			if (left + cur == aim) {
				res.add(new String(path, 0, len));
			}
			return;
		}
		// num中的index还没结束
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
