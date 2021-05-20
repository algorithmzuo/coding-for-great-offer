package class09;

import java.util.ArrayList;
import java.util.List;

// 测试链接 : https://leetcode.com/problems/remove-invalid-parentheses/
public class Code02_RemoveInvalidParentheses {

	// 来自leetcode投票第一的答案，实现非常好，我们来赏析一下
	public static List<String> removeInvalidParentheses(String s) {
		List<String> ans = new ArrayList<>();
		remove(s, ans, 0, 0, new char[] { '(', ')' });
		return ans;
	}

	// modifyIndex <= checkIndex
	// 只查s[checkIndex....]的部分，因为之前的一定已经调整对了
	// 但是之前的部分是怎么调整对的，调整到了哪？就是modifyIndex
	// 比如：
	// ( ( ) ( ) ) ) ...
	// 0 1 2 3 4 5 6
	// 一开始当然checkIndex = 0，modifyIndex = 0
	// 当查到6的时候，发现不对了，
	// 然后可以去掉2位置、4位置的 )，都可以
	// 如果去掉2位置的 ), 那么下一步就是
	// ( ( ( ) ) ) ...
	// 0 1 2 3 4 5 6
	// checkIndex = 6 ，modifyIndex = 2
	// 如果去掉4位置的 ), 那么下一步就是
	// ( ( ) ( ) ) ...
	// 0 1 2 3 4 5 6
	// checkIndex = 6 ，modifyIndex = 4
	// 也就是说，
	// checkIndex和modifyIndex，分别表示查的开始 和 调的开始，之前的都不用管了
	public static void remove(String s, List<String> ans, int checkIndex, int modifyIndex, char[] par) {
		for (int stack = 0, i = checkIndex; i < s.length(); ++i) {
			if (s.charAt(i) == par[0]) {
				stack++;
			}
			if (s.charAt(i) == par[1]) {
				stack--;
			}
			// 只要有违规就把前缀调整对，而且没有其他分支
			if (stack < 0) {
				for (int j = modifyIndex; j <= i; ++j) {
					// 比如
					if (s.charAt(j) == par[1] && (j == modifyIndex || s.charAt(j - 1) != par[1])) {
						remove(s.substring(0, j) + s.substring(j + 1, s.length()), ans, i, j, par);
					}
				}
				return;
			}
		}
		String reversed = new StringBuilder(s).reverse().toString();
		if (par[0] == '(') {
			remove(reversed, ans, 0, 0, new char[] { ')', '(' });
		} else {
			ans.add(reversed);
		}
	}

}
