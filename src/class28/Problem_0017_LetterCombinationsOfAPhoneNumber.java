package class28;

import java.util.ArrayList;
import java.util.List;

public class Problem_0017_LetterCombinationsOfAPhoneNumber {

	public static char[][] phone = { 
			{ 'a', 'b', 'c' }, // 2    0
			{ 'd', 'e', 'f' }, // 3    1
			{ 'g', 'h', 'i' }, // 4    2
			{ 'j', 'k', 'l' }, // 5    3
			{ 'm', 'n', 'o' }, // 6    
			{ 'p', 'q', 'r', 's' }, // 7 
			{ 't', 'u', 'v' },   // 8
			{ 'w', 'x', 'y', 'z' }, // 9
	};

	// "23"
	public static List<String> letterCombinations(String digits) {
		List<String> ans = new ArrayList<>();
		if (digits == null || digits.length() == 0) {
			return ans;
		}
		char[] str = digits.toCharArray();
		char[] path = new char[str.length];
		process(str, 0, path, ans);
		return ans;
	}

	// str = ['2','3']  3   3
	// str[....index-1]，按出的结果是什么都在path里
	// str[index...]  按完之后，有哪些组合，放入到ans里
	public static void process(char[] str, int index, char[] path, List<String> ans) {
		if (index == str.length) {
			ans.add(String.valueOf(path));
		} else {
			char[] cands = phone[str[index] - '2'];
			for (char cur : cands) {
				path[index] = cur;
				process(str, index + 1, path, ans);
			}
		}
	}

}
