package class03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

// lintcode
public class Code01_BuildingOutline {

	// 描述高度变化的对象
	public static class Op {
		public int x; // x轴上的值
		public boolean isAdd;// true为加入，false为删除
		public int h; // 高度

		public Op(int x, boolean isAdd, int h) {
			this.x = x;
			this.isAdd = isAdd;
			this.h = h;
		}
	}

	// 排序的比较策略
	// 1，第一个维度的x值从小到大。
	// 2，如果第一个维度的值相等，看第二个维度的值，“加入”排在前，“删除”排在后
	// 3，如果两个对象第一维度和第二个维度的值都相等，则认为两个对象相等，谁在前都行。
	public static class OpComparator implements Comparator<Op> {
		@Override
		public int compare(Op o1, Op o2) {
			if (o1.x != o2.x) {
				return o1.x - o2.x;
			}
			if (o1.isAdd != o2.isAdd) {
				return o1.isAdd ? -1 : 1;
			}
			return 0;
		}
	}

	// 全部流程的主方法
	// [s,e,h]
	// [s,e,h]
	// { {1,5,3} , {6,8,4}  .. ...  ...    }
	public static List<List<Integer>> buildingOutline(int[][] matrix) {
		int N = matrix.length;
		Op[] ops = new Op[N << 1];
		for (int i = 0; i < matrix.length; i++) {
			ops[i * 2] = new Op(matrix[i][0], true, matrix[i][2]);
			ops[i * 2 + 1] = new Op(matrix[i][1], false, matrix[i][2]);
		}
		// 把描述高度变化的对象数组，按照规定的排序策略排序
		Arrays.sort(ops, new OpComparator());
		
		
		// TreeMap就是java中的红黑树结构，直接当作有序表来使用
		// key  某个高度  value  次数
		TreeMap<Integer, Integer> mapHeightTimes = new TreeMap<>();
		// key   x点，   value 最大高度
		TreeMap<Integer, Integer> mapXHeight = new TreeMap<>();
		

		for (int i = 0; i < ops.length; i++) {
			// ops[i]
			if (ops[i].isAdd) { // 如果当前是加入操作
				if (!mapHeightTimes.containsKey(ops[i].h)) { // 没有出现的高度直接新加记录
					mapHeightTimes.put(ops[i].h, 1);
				} else { // 之前出现的高度，次数加1即可
					mapHeightTimes.put(ops[i].h, mapHeightTimes.get(ops[i].h) + 1);
				}
			} else { // 如果当前是删除操作
				if (mapHeightTimes.get(ops[i].h) == 1) { // 如果当前的高度出现次数为1，直接删除记录
					mapHeightTimes.remove(ops[i].h);
				} else { // 如果当前的高度出现次数大于1，次数减1即可
					mapHeightTimes.put(ops[i].h, mapHeightTimes.get(ops[i].h) - 1);
				}
			}
			// 根据mapHeightTimes中的最大高度，设置mapXvalueHeight表
			if (mapHeightTimes.isEmpty()) { // 如果mapHeightTimes为空，说明最大高度为0
				mapXHeight.put(ops[i].x, 0);
			} else { // 如果mapHeightTimes不为空，通过mapHeightTimes.lastKey()取得最大高度
				mapXHeight.put(ops[i].x, mapHeightTimes.lastKey());
			}
		}
		// res为结果数组，每一个List<Integer>代表一个轮廓线，有开始位置，结束位置，高度，一共三个信息
		List<List<Integer>> res = new ArrayList<>();
		// 一个新轮廓线的开始位置
		int start = 0;
		// 之前的最大高度
		int preHeight = 0;
		// 根据mapXvalueHeight生成res数组
		for (Entry<Integer, Integer> entry : mapXHeight.entrySet()) {
			// 当前位置
			int curX = entry.getKey();
			// 当前最大高度
			int curMaxHeight = entry.getValue();
			if (preHeight != curMaxHeight) { // 之前最大高度和当前最大高度不一样时
				if (preHeight != 0) {
					res.add(new ArrayList<>(Arrays.asList(start, curX, preHeight)));
				}
				start = curX;
				preHeight = curMaxHeight;
			}
		}
		return res;
	}

	public static void main(String[] args) {
		// java有序表 -> 红黑树
		// 有序表 ： 跳表、AVL树、SB树...
		// 只是底层细节不一样，但是对外提供的接口，和每个接口的性能都一样
		// O(logN)
		// 哈希表：O(1)
		TreeMap<Integer, String> map = new TreeMap<>();
		map.put(6, "我是6");
		map.put(3, "我是3");
		map.put(9, "我是9");
		map.put(1, "我是1");
		map.put(2, "我是2");
		map.put(5, "我是5");
		map.put(1, "我还是1");

		System.out.println(map.containsKey(5));
		//map.remove(5);
		System.out.println(map.containsKey(5));
		System.out.println(map.get(9));

		System.out.println(map.firstKey());
		System.out.println(map.lastKey());
		// 查询 <= num，离这个num最近的key是谁
		System.out.println(map.floorKey(5));
		// 查询 >= num，离这个num最近的key是谁
		System.out.println(map.ceilingKey(5));

		// 所有的方法，性能O(logN)

	}

}
