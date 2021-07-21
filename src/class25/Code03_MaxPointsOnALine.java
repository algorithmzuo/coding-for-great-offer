package class25;

import java.util.HashMap;
import java.util.Map;

// 本题测试链接: https://leetcode.com/problems/max-points-on-a-line/
public class Code03_MaxPointsOnALine {

	// [
	//    [1,3]
	//    [4,9]
	//    [5,7]
	//   ]
	
	public static int maxPoints(int[][] points) {
		if (points == null) {
			return 0;
		}
		if (points.length <= 2) {
			return points.length;
		}
		// key = 3
		// value = {7 , 10}  -> 斜率为3/7的点 有10个
		//         {5,  15}  -> 斜率为3/5的点 有15个
		Map<Integer, Map<Integer, Integer>> map = new HashMap<Integer, Map<Integer, Integer>>();
		int result = 0;
		for (int i = 0; i < points.length; i++) {
			map.clear();
			int samePosition = 1;
			int sameX = 0;
			int sameY = 0;
			int line = 0;
			for (int j = i + 1; j < points.length; j++) { // i号点，和j号点，的斜率关系
				int x = points[j][0] - points[i][0];
				int y = points[j][1] - points[i][1];
				if (x == 0 && y == 0) {
					samePosition++;
				} else if (x == 0) {
					sameX++;
				} else if (y == 0) {
					sameY++;
				} else { // 普通斜率
					int gcd = gcd(x, y);
					x /= gcd;
					y /= gcd;
					// x / y
					if (!map.containsKey(x)) {
						map.put(x, new HashMap<Integer, Integer>());
					}
					if (!map.get(x).containsKey(y)) {
						map.get(x).put(y, 0);
					}
					map.get(x).put(y, map.get(x).get(y) + 1);
					line = Math.max(line, map.get(x).get(y));
				}
			}
			result = Math.max(result, Math.max(Math.max(sameX, sameY), line) + samePosition);
		}
		return result;
	}

	// 保证初始调用的时候，a和b不等于0
	// O(1)
	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

}
