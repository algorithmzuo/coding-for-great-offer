package class36;

import java.util.ArrayList;
import java.util.List;

public class Problem_0763_PartitionLabels {

	public static List<Integer> partitionLabels(String S) {
		char[] str = S.toCharArray();
		int[] far = new int[26];
		for (int i = 0; i < str.length; i++) {
			far[str[i] - 'a'] = i;
		}
		List<Integer> ans = new ArrayList<>();
		int left = 0;
		int right = far[str[0] - 'a'];
		for (int i = 1; i < str.length; i++) {
			if (i > right) {
				ans.add(right - left + 1);
				left = i;
			}
			right = Math.max(right, far[str[i] - 'a']);
		}
		ans.add(right - left + 1);
		return ans;
	}

}
