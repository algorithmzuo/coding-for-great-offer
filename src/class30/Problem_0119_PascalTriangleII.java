package class30;

import java.util.ArrayList;
import java.util.List;

public class Problem_0119_PascalTriangleII {

	public List<Integer> getRow(int rowIndex) {
		List<Integer> ans = new ArrayList<>();
		int a = 1;
		int b = 0;
		for (int i = 0; i <= rowIndex; i++) {
			for (int j = 1; j < i; j++) {
				b = ans.get(j);
				ans.set(j, a + ans.get(j));
				a = b;
			}
			ans.add(1);
		}
		return ans;
	}

}
