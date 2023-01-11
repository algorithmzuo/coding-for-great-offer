package class08;

import java.util.LinkedList;

// 本题测试链接 : https://leetcode.com/problems/basic-calculator-iii/
public class Code01_ExpressionCompute {

	public static int calculate(String str) {
		return f(str.toCharArray(), 0)[0];
	}

	// 请从str[i...]往下算，遇到字符串终止位置或者右括号，就停止
	// 返回两个值，长度为2的数组
	// 0) 负责的这一段的结果是多少
	// 1) 负责的这一段计算到了哪个位置
	public static int[] f(char[] str, int i) {
		LinkedList<String> queue = new LinkedList<String>();
		int cur = 0;
		int[] bra = null;
		// 从i出发，开始撸串
		while (i < str.length && str[i] != ')') {
			if (str[i] >= '0' && str[i] <= '9') {
				cur = cur * 10 + str[i++] - '0';
			} else if (str[i] != '(') { // 遇到的是运算符号
				addNum(queue, cur, str[i++]);
				cur = 0;
			} else { // 遇到左括号了
				bra = f(str, i + 1);
				cur = bra[0];
				i = bra[1] + 1;
			}
		}
		addNum(queue, cur, '+');
		return new int[] { getAns(queue), i };
	}

	public static void addNum(LinkedList<String> queue, int num, char op) {
		if (!queue.isEmpty() && (queue.peekLast().equals("*") || queue.peekLast().equals("/"))) {
			String top = queue.pollLast();
			int pre = Integer.valueOf(queue.pollLast());
			num = top.equals("*") ? (pre * num) : (pre / num);
		}
		queue.addLast(String.valueOf(num));
		queue.addLast(String.valueOf(op));
	}

	public static int getAns(LinkedList<String> queue) {
		int ans = Integer.valueOf(queue.pollFirst());
		while (queue.size() > 1) {
			String op = queue.pollFirst();
			int num = Integer.valueOf(queue.pollFirst());
			ans += op.equals("+") ? num : -num;
		}
		return ans;
	}

}
