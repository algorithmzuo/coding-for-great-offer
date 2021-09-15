package class39;

// 来自腾讯
// 给定一个只由0和1组成的字符串S，假设下标从1开始，规定i位置的字符价值V[i]计算方式如下 : 
// 1) i == 1时，V[i] = 1
// 2) i > 1时，如果S[i] != S[i-1]，V[i] = 1
// 3) i > 1时，如果S[i] == S[i-1]，V[i] = V[i-1] + 1
// 你可以随意删除S中的字符，返回整个S的最大价值
// 字符串长度<=5000
public class Code01_01AddValue {

	public static int max1(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		char[] str = s.toCharArray();
		int[] arr = new int[str.length];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = str[i] == '0' ? 0 : 1;
		}
		return process1(arr, 0, 0, 0);
	}

	// 递归含义 :
	// 目前在arr[index...]上做选择, str[index...]的左边，最近的数字是lastNum
	// 并且lastNum所带的价值，已经拉高到baseValue
	// 返回在str[index...]上做选择，最终获得的最大价值
	// index -> 0 ~ 4999
	// lastNum -> 0 or 1
	// baseValue -> 1 ~ 5000
	// 5000 * 2 * 5000 -> 5 * 10^7(过!)
	public static int process1(int[] arr, int index, int lastNum, int baseValue) {
		if (index == arr.length) {
			return 0;
		}
		int curValue = lastNum == arr[index] ? (baseValue + 1) : 1;
		// 当前index位置的字符保留
		int next1 = process1(arr, index + 1, arr[index], curValue);
		// 当前index位置的字符不保留
		int next2 = process1(arr, index + 1, lastNum, baseValue);
		return Math.max(curValue + next1, next2);
	}

	// 请看体系学习班，动态规划章节，把上面的递归改成动态规划！看完必会

}
