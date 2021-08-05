package class30;

import java.util.ArrayList;
import java.util.List;

public class Problem_0119_PascalTriangleII {

	public List<Integer> getRow(int rowIndex) {
		List<Integer> ans = new ArrayList<>();
		for (int i = 0; i <= rowIndex; i++) {
			for (int j = i - 1; j > 0; j--) {
				ans.set(j, ans.get(j - 1) + ans.get(j));
			}
			ans.add(1);
		}
		return ans;
	}

}
