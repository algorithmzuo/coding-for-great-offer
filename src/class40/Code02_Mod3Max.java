package class40;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

// 来自去哪儿网
// 给定一个arr，里面的数字都是0~9
// 你可以随意使用arr中的数字，哪怕打乱顺序也行
// 请拼出一个能被3整除的，最大的数字，用str形式返回
public class Code02_Mod3Max {

	public static String max1(int[] arr) {
		Arrays.sort(arr);
		for (int l = 0, r = arr.length - 1; l < r; l++, r--) {
			int tmp = arr[l];
			arr[l] = arr[r];
			arr[r] = tmp;
		}
		StringBuilder builder = new StringBuilder();
		TreeSet<String> set = new TreeSet<>((a, b) -> Integer.valueOf(b).compareTo(Integer.valueOf(a)));
		process1(arr, 0, builder, set);
		return set.isEmpty() ? "" : set.first();
	}

	public static void process1(int[] arr, int index, StringBuilder builder, TreeSet<String> set) {
		if (index == arr.length) {
			if (builder.length() != 0 && Integer.valueOf(builder.toString()) % 3 == 0) {
				set.add(builder.toString());
			}
		} else {
			process1(arr, index + 1, builder, set);
			builder.append(arr[index]);
			process1(arr, index + 1, builder, set);
			builder.deleteCharAt(builder.length() - 1);
		}
	}

	public static String max2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return "";
		}
		Arrays.sort(arr);
		for (int l = 0, r = arr.length - 1; l < r; l++, r--) {
			int tmp = arr[l];
			arr[l] = arr[r];
			arr[r] = tmp;
		}
		if (arr[0] == 0) {
			return "0";
		}
		String ans = process2(arr, 0, 0);
		String res = ans.replaceAll("^(0+)", "");
		if (!res.equals("")) {
			return res;
		}
		return ans.equals("") ? ans : "0";
	}

	// arr中的数字一定是0~9
	// arr是经过排序的，并且是从大到小排序，比如[9,8,7,7,7,3,1]等
	// 这个递归函数的含义 :
	// 在arr[index...一直到最后]上做选择，arr[0...index-1]就当不存在
	// 每个位置的字符可以要、也可以不要，但是！选出来的数字拼完之后的结果，在%3之后，余数一定要是mod！
	// 返回在上面设定的情况下，最大的数是多少？
	// 如果存在这样的数，返回字符串的形式
	// 如果不存在这样的数，返回特殊字符串，比如"$"，代表不可能
	// 这个递归函数可以很轻易的改出动态规划
	public static String process2(int[] arr, int index, int mod) {
		if (index == arr.length) {
			return mod == 0 ? "" : "$";
		}
		String p1 = "$";
		int nextMod = nextMod(mod, arr[index] % 3);
		String next = process2(arr, index + 1, nextMod);
		if (!next.equals("$")) {
			p1 = String.valueOf(arr[index]) + next;
		}
		String p2 = process2(arr, index + 1, mod);
		if (p1.equals("$") && p2.equals("$")) {
			return "$";
		}
		if (!p1.equals("$") && !p2.equals("$")) {
			return smaller(p1, p2) ? p2 : p1;
		}
		return p1.equals("$") ? p2 : p1;
	}

	public static int nextMod(int require, int current) {
		if (require == 0) {
			if (current == 0) {
				return 0;
			} else if (current == 1) {
				return 2;
			} else {
				return 1;
			}
		} else if (require == 1) {
			if (current == 0) {
				return 1;
			} else if (current == 1) {
				return 0;
			} else {
				return 2;
			}
		} else { // require == 2
			if (current == 0) {
				return 2;
			} else if (current == 1) {
				return 1;
			} else {
				return 0;
			}
		}
	}

	public static boolean smaller(String p1, String p2) {
		if (p1.length() != p2.length()) {
			return p1.length() < p2.length();
		}
		return p1.compareTo(p2) < 0;
	}

	// 贪心的思路解法 :
	// 先得到数组的累加和，记为sum
	// 1) 如果sum%3==0，说明所有数从大到小拼起来就可以了
	// 2) 如果sum%3==1，说明多了一个余数1，
	// 只需要删掉一个最小的数(该数是%3==1的数);
	// 如果没有，只需要删掉两个最小的数(这两个数都是%3==2的数);
	// 3) 如果sum%3==2，说明多了一个余数2，
	// 只需要删掉一个最小的数(该数是%3==2的数);
	// 如果没有，只需要删掉两个最小的数(这两个数都是%3==1的数);
	// 如果上面都做不到，说明拼不成
	public static String max3(int[] A) {
		if (A == null || A.length == 0) {
			return "";
		}
		int mod = 0;
		ArrayList<Integer> arr = new ArrayList<>();
		for (int num : A) {
			arr.add(num);
			mod += num;
			mod %= 3;
		}
		if ((mod == 1 || mod == 2) && !remove(arr, mod, 3 - mod)) {
			return "";
		}
		if (arr.isEmpty()) {
			return "";
		}
		arr.sort((a, b) -> b - a);
		if (arr.get(0) == 0) {
			return "0";
		}
		StringBuilder builder = new StringBuilder();
		for (int num : arr) {
			builder.append(num);
		}
		return builder.toString();
	}

	// 在arr中，要么删掉最小的一个、且%3之后余数是first的数
	// 如果做不到，删掉最小的两个、且%3之后余数是second的数
	// 如果能做到返回true，不能做到返回false
	public static boolean remove(ArrayList<Integer> arr, int first, int second) {
		if (arr.size() == 0) {
			return false;
		}
		arr.sort((a, b) -> compare(a, b, first, second));
		int size = arr.size();
		if (arr.get(size - 1) % 3 == first) {
			arr.remove(size - 1);
			return true;
		} else if (size > 1 && arr.get(size - 1) % 3 == second && arr.get(size - 2) % 3 == second) {
			arr.remove(size - 1);
			arr.remove(size - 2);
			return true;
		} else {
			return false;
		}
	}

	// a和b比较:
	// 如果余数一样，谁大谁放前面
	// 如果余数不一样，余数是0的放最前面、余数是s的放中间、余数是f的放最后
	public static int compare(int a, int b, int f, int s) {
		int ma = a % 3;
		int mb = b % 3;
		if (ma == mb) {
			return b - a;
		} else {
			if (ma == 0 || mb == 0) {
				return ma == 0 ? -1 : 1;
			} else {
				return ma == s ? -1 : 1;
			}
		}
	}

	// 为了测试
	public static int[] randomArray(int len) {
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * 10);
		}
		return arr;
	}

	// 为了测试
	public static int[] copyArray(int[] arr) {
		int[] ans = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			ans[i] = arr[i];
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 10;
		int testTimes = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int len = (int) (Math.random() * N);
			int[] arr1 = randomArray(len);
			int[] arr2 = copyArray(arr1);
			int[] arr3 = copyArray(arr1);
			String ans1 = max1(arr1);
			String ans2 = max2(arr2);
			String ans3 = max3(arr3);
			if (!ans1.equals(ans2) || !ans1.equals(ans3)) {
				System.out.println("出错了！");
				for (int num : arr3) {
					System.out.print(num + " ");
				}
				System.out.println();
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
				break;
			}
		}
		System.out.println("测试结束");

	}

}
