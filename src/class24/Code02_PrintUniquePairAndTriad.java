package class24;

public class Code02_PrintUniquePairAndTriad {

	public static void printUniquePair(int[] arr, int k) {
		if (arr == null || arr.length < 2) {
			return;
		}
		int left = 0;
		int right = arr.length - 1;
		while (left < right) {
			if (arr[left] + arr[right] < k) {
				left++;
			} else if (arr[left] + arr[right] > k) {
				right--;
			} else { // L   +   R   =   aim
				if (left == 0 || arr[left - 1] != arr[left]) {
					System.out.println(arr[left] + "," + arr[right]);
				}
				left++;
				right--;
			}
		}
	}

	public static void printUniqueTriad(int[] arr, int k) {
		if (arr == null || arr.length < 3) {
			return;
		}
		for (int i = 0; i < arr.length - 2; i++) {
			if (i == 0 || arr[i] != arr[i - 1]) {
				printRest(arr, i, i + 1, arr.length - 1, k - arr[i]);
			}
		}
	}

	public static void printRest(int[] arr, int f, int l, int r, int k) {
		while (l < r) {
			if (arr[l] + arr[r] < k) {
				l++;
			} else if (arr[l] + arr[r] > k) {
				r--;
			} else {
				if (l == f + 1 || arr[l - 1] != arr[l]) {
					System.out.println(arr[f] + "," + arr[l] + "," + arr[r]);
				}
				l++;
				r--;
			}
		}
	}

	public static void printArray(int[] arr) {
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int sum = 10;
		int[] arr1 = { -8, -4, -3, 0, 1, 2, 4, 5, 8, 9 };
		printArray(arr1);
		System.out.println("====");
		printUniquePair(arr1, sum);
		System.out.println("====");
		printUniqueTriad(arr1, sum);

	}

}
