package class33;

public class Problem_0238_ProductOfArrayExceptSelf {

	public int[] productExceptSelf(int[] nums) {
		int n = nums.length;
		int[] ans = new int[n];
		ans[0] = nums[0];
		for (int i = 1; i < n; i++) {
			ans[i] = ans[i - 1] * nums[i];
		}
		int right = 1;
		for (int i = n - 1; i > 0; i--) {
			ans[i] = ans[i - 1] * right;
			right *= nums[i];
		}
		ans[0] = right;
		return ans;
	}

	// 扩展 : 如果仅仅是不能用除号，把结果直接填在nums里呢？
	// 解法：数一共几个0；每一个位得到结果就是，a / b，位运算替代 /，之前的课讲过（新手班）

}
