package class31;

import java.util.HashSet;

public class Problem_0202_HappyNumber {

    public static boolean isHappy1(int n) {
		HashSet<Integer> set = new HashSet<>();
		while (n != 1) {
			int sum = 0;
			while (n != 0) {
				int r = n % 10;
				sum += r * r;
				n /= 10;
			}
			n = sum;
			if (set.contains(n)) {
				break;
			}
			set.add(n);
		}
		return n == 1;
	}

	public static boolean isHappy2(int n) {
		while (n != 1 && n != 4) {
			int sum = 0;
			while (n != 0) {
				sum += (n % 10) * (n % 10);
				n /= 10;
			}
			n = sum;
		}
		return n == 1;
	}

	public static void main(String[] args) {
		for (int i = 1; i < 1000; i++) {
			System.out.println(i + " : " + isHappy1(i));
		}
	}

}
