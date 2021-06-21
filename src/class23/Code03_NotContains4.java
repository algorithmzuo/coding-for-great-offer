package class23;

// 里程表不能含有4，给定一个数max，返回他是里程表里的第几个
public class Code03_NotContains4 {

	public static long notContains4Nums1(long max) {
		long count = 0;
		for (long i = 1; i <= max; i++) {
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

	public static long[] arr1 = { 0L, 8L, 72L, 648L, 5832L, 52488L, 472392L, 4251528L, 38263752L, 344373768L,
			3099363912L, 27894275208L, 251048476872L, 2259436291848L, 20334926626632L, 183014339639688L,
			1647129056757192L, 14824161510814728L, 133417453597332552L, 1200757082375992968L };

	public static long[] arr2 = { 1L, 9L, 81L, 729L, 6561L, 59049L, 531441L, 4782969L, 43046721L, 387420489L,
			3486784401L, 31381059609L, 282429536481L, 2541865828329L, 22876792454961L, 205891132094649L,
			1853020188851841L, 16677181699666569L, 150094635296999121L, 1350851717672992089L };

	public static long notContains4Nums2(long max) {
		if (max <= 0) {
			return 0;
		}
		int bits = bits(max);
		long counts = 0;
		for (int i = 1; i < bits; i++) {
			counts += arr1[i];
		}
		long offset = offset(max);
		long first = max / offset;
		if (first == 4) {
			counts += (first - 1) * arr2[bits - 1];
		} else {
			counts += process(max % offset, offset / 10, bits - 1)
					+ (first < 4 ? (first - 1) : (first - 2)) * arr2[bits - 1];
		}
		return counts;
	}

	public static long process(long max, long offset, int bits) {
		if (bits == 0) {
			return 1;
		}
		long first = max / offset;
		long counts = 0;
		if (first == 4) {
			counts += first * arr2[bits - 1];
		} else {
			counts += process(max % offset, offset / 10, bits - 1) + (first < 4 ? first : (first - 1)) * arr2[bits - 1];
		}
		return counts;
	}

	public static int bits(long max) {
		int bits = 0;
		while (max != 0) {
			bits++;
			max /= 10;
		}
		return bits;
	}

	public static long offset(long max) {
		long offset = 1;
		while (max / offset > 9) {
			offset *= 10;
		}
		return offset;
	}

	public static void main(String[] args) {
		long max = 8888L;
		System.out.println("功能测试开始，验证 0 ~ " + max + " 以内所有的结果");
		for (long i = 0; i <= max; i++) {
			if (notContains4Nums1(i) != notContains4Nums2(i)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("如果没有打印Oops说明验证通过");

		max = 888888888L;
		long start;
		long end;
		System.out.println("性能测试开始，计算max = " + max + " 的答案");
		start = System.currentTimeMillis();
		long ans1 = notContains4Nums1(max);
		end = System.currentTimeMillis();
		System.out.println("方法一答案 : " + ans1 + ", 运行时间 : " + (end - start) + " ms");

		start = System.currentTimeMillis();
		long ans2 = notContains4Nums2(max);
		end = System.currentTimeMillis();
		System.out.println("方法二答案 : " + ans2 + ", 运行时间 : " + (end - start) + " ms");
	}

}
