package class32;

import java.util.Comparator;
import java.util.PriorityQueue;

// 提交时，把这个类名和构造方法名改成，MedianFinder
public class Problem_0295_FindMedianFromDataStream {

	public static class MaxHeapComparator implements Comparator<Integer> {

		@Override
		public int compare(Integer o1, Integer o2) {
			return o2 - o1;
		}

	}

	public static class MinHeapComparator implements Comparator<Integer> {

		@Override
		public int compare(Integer o1, Integer o2) {
			return o1 - o2;
		}

	}

	private PriorityQueue<Integer> maxh;
	private PriorityQueue<Integer> minh;

	public Problem_0295_FindMedianFromDataStream() {
		maxh = new PriorityQueue<>(new MaxHeapComparator());
		minh = new PriorityQueue<>(new MinHeapComparator());
	}

	public void addNum(int num) {
		if (maxh.isEmpty()) {
			maxh.add(num);
		} else {
			if (maxh.peek() >= num) {
				maxh.add(num);
			} else {
				minh.add(num);
			}
		}
		balance();
	}

	public double findMedian() {
		if (maxh.size() == minh.size()) {
			return (double) (maxh.peek() + minh.peek()) / 2;
		} else {
			return maxh.size() > minh.size() ? maxh.peek() : minh.peek();
		}
	}

	private void balance() {
		if (maxh.size() == minh.size() + 2) {
			minh.add(maxh.poll());
		}
		if (maxh.size() == minh.size() - 2) {
			maxh.add(minh.poll());
		}
	}

}