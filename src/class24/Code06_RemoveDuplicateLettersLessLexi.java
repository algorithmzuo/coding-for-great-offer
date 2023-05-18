package class24;

// 本题重写了
// 不用看课上的解释了
// 这道题最优解是单调栈的解法
// 看注释能看懂
// 本题测试链接 : https://leetcode.com/problems/remove-duplicate-letters/
public class Code06_RemoveDuplicateLettersLessLexi {

	public static String removeDuplicateLetters(String s) {
		int[] cnts = new int[26];
		boolean[] enter = new boolean[26];
		for (int i = 0; i < s.length(); i++) {
			cnts[s.charAt(i) - 'a']++;
		}
		// 单调栈
		// 从左到右只保留依次变大的字符
		// 比如: a c e....
		char[] stack = new char[26];
		int size = 0;
		for (int i = 0; i < s.length(); i++) {
			// 假设当前字符是d
			char cur = s.charAt(i);
			// 如果d已经在单调栈里，不进入!
			// 因为单调栈里每种字符只保留一个
			if (!enter[cur - 'a']) {
				// 如果d不在单调栈里，进入!
				enter[cur - 'a'] = true;
				// 如果单调栈里已经有 :
				// a c e f
				// 当前字符是d
				// 那么f弹出、e弹出、然后d进入，
				// 单调栈变成 : a c d
				// 但是!
				// 如果后面还有f，f才能弹出
				// 如果后面还有e，e才能弹出
				// 如果一个字符要弹出，但是后面已经没有这种字符了，不能弹出!
				// 因为一旦弹出，再也没有机会收集到这种字符了！因为后面没有了
				// 这就是核心逻辑
				// 所以 : size > 0，表示单调栈里有字符
				// stack[size - 1] > cur，表示单调栈当前最右的字符，比当前字符大，那么它弹出
				// cnts[stack[size - 1] - 'a'] > 0，重要限制 : 后面还有单调栈当前最右的字符，才能弹出
				// 缺一不可
				while (size > 0 && stack[size - 1] > cur && cnts[stack[size - 1] - 'a'] > 0) {
					// 这种字符要弹出了，所以标记这种字符出去了
					enter[stack[size - 1] - 'a'] = false;
					// 单调栈大小缩减了，也就是弹出了
					size--;
				}
				// 当前字符进入了单调栈
				stack[size++] = cur;
			}
			// 当前字符的词频调整，调整后表示后面还有多少个当前字符
			// 注意词频表更新
			cnts[cur - 'a']--;
		}
		// 单调栈里的字符，拼字符串返回
		return String.valueOf(stack, 0, size);
	}

}
