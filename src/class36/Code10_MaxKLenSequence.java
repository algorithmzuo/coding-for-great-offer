package class36;

import java.util.TreeSet;

// 来自腾讯
public class Code10_MaxKLenSequence {

	public static String maxString(String s, int k) {
		if (k <= 0 || s.length() < k) {
			return "";
		}
		char[] str = s.toCharArray();
		int n = str.length;
		char[] stack = new char[n];
		int size = 0;
		for (int i = 0; i < n; i++) {
			while (size > 0 && stack[size - 1] < str[i] && size + n - i > k) {
				size--;
			}
			if (size + n - i == k) {
				return String.valueOf(stack, 0, size) + s.substring(i);
			}
			stack[size++] = str[i];
		}
		return String.valueOf(stack, 0, k);
	}

	// 为了测试
	public static String test(String str, int k) {
		if (k <= 0 || str.length() < k) {
			return "";
		}
		TreeSet<String> ans = new TreeSet<>();
		process(0, 0, str.toCharArray(), new char[k], ans);
		return ans.last();
	}

	// 为了测试
	public static void process(int si, int pi, char[] str, char[] path, TreeSet<String> ans) {
		if (si == str.length) {
			if (pi == path.length) {
				ans.add(String.valueOf(path));
			}
		} else {
			process(si + 1, pi, str, path, ans);
			if (pi < path.length) {
				path[pi] = str[si];
				process(si + 1, pi + 1, str, path, ans);
			}
		}
	}

	// 为了测试
	public static String randomString(int len, int range) {
		char[] str = new char[len];
		for (int i = 0; i < len; i++) {
			str[i] = (char) ((int) (Math.random() * range) + 'a');
		}
		return String.valueOf(str);
	}

	public static void main(String[] args) {
		int n = 12;
		int r = 5;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * (n + 1));
			String str = randomString(len, r);
			int k = (int) (Math.random() * (str.length() + 1));
			String ans1 = maxString(str, k);
			String ans2 = test(str, k);
			if (!ans1.equals(ans2)) {
				System.out.println("出错了！");
				System.out.println(str);
				System.out.println(k);
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("测试结束");
	}

}
