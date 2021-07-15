package class24;

// 里程表不能含有4，给定一个数num，返回他是里程表里的第几个
public class Code03_NotContains4 {

	// num中一定没有4这个数字
	public static long notContains4Nums1(long num) {
		long count = 0;
		for (long i = 1; i <= num; i++) {
			if (isNot4(i)) {
				count++;
			}
		}
		return count;
	}

	public static boolean isNot4(long num) {
		while (num != 0) {
			if (num % 10 == 4) {
				return false;
			}
			num /= 10;
		}
		return true;
	}

	// arr[1] : 比1位数还少1位，有几个数(数字里可以包含0但是不可以包含4)？0个
	// arr[2] : 比2位数还少1位，有几个数(数字里可以包含0但是不可以包含4)？9个
	// 1 -> 0 1 2 3 - 5 6 7 8 9 = 9
	// arr[3] :比3位数还少1位，有几个数(数字里可以包含0但是不可以包含4)？81个
	// 1 : 0 (0 1 2 3 - 5 6 7 8 9) = 9
	// 2 :
	// 1 (0 1 2 3 - 5 6 7 8 9) = 9
	// 2 (0 1 2 3 - 5 6 7 8 9) = 9
	// 3 (0 1 2 3 - 5 6 7 8 9) = 9
	// 5 (0 1 2 3 - 5 6 7 8 9) = 9
	// ...
	// 9 (0 1 2 3 - 5 6 7 8 9) = 9
	public static long[] arr = { 0L, 1L, 9L, 81L, 729L, 6561L, 59049L, 531441L, 4782969L, 43046721L, 387420489L,
			3486784401L, 31381059609L, 282429536481L, 2541865828329L, 22876792454961L, 205891132094649L,
			1853020188851841L, 16677181699666569L, 150094635296999121L, 1350851717672992089L };

	// num中一定没有4这个数字
	public static long notContains4Nums2(long num) {
		if (num <= 0) {
			return 0;
		}
		int len = decimalLength(num);
		long offset = offset(len);
		long first = num / offset;
		return arr[len] - 1 + (first - (first < 4 ? 1 : 2)) * arr[len] + process(num % offset, offset / 10, len - 1);
	}

	public static long process(long num, long offset, int len) {
		if (len == 0) {
			return 1;
		}
		long first = num / offset;
		return (first < 4 ? first : (first - 1)) * arr[len] + process(num % offset, offset / 10, len - 1);
	}

	public static int decimalLength(long num) {
		int len = 0;
		while (num != 0) {
			len++;
			num /= 10;
		}
		return len;
	}

	public static long offset(int len) {
		long offset = 1;
		for (int i = 1; i < len; i++) {
			offset *= 10L;
		}
		return offset;
	}

	public static void main(String[] args) {
		System.out.println("long类型最大长度为 : " + decimalLength(Long.MAX_VALUE));
		System.out.print("pubic static long[] arr = { 0L, ");
		long value = 1L;
		for (int i = 0; i <= 19; i++) {
			System.out.print(value + "L, ");
			value *= 9L;
		}
		System.out.println("}");

		long max = 8888L;
		System.out.println("功能测试开始，验证 0 ~ " + max + " 以内所有的结果");
		for (long i = 0; i <= max; i++) {
			// 测试的时候，输入的数字i里不能含有4，这是题目的规定！
			if (isNot4(i) && notContains4Nums1(i) != notContains4Nums2(i)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("如果没有打印Oops说明验证通过");

		long num = 1192360215L;
		long start;
		long end;
		System.out.println("性能测试开始，计算 num = " + num + " 的答案");
		start = System.currentTimeMillis();
		long ans1 = notContains4Nums1(num);
		end = System.currentTimeMillis();
		System.out.println("方法一答案 : " + ans1 + ", 运行时间 : " + (end - start) + " ms");

		start = System.currentTimeMillis();
		long ans2 = notContains4Nums2(num);
		end = System.currentTimeMillis();
		System.out.println("方法二答案 : " + ans2 + ", 运行时间 : " + (end - start) + " ms");
	}

}
