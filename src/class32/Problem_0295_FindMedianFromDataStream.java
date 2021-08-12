package class32;

import java.util.PriorityQueue;

public class Problem_0295_FindMedianFromDataStream {

	class MedianFinder {

		private PriorityQueue<Integer> maxh;
		private PriorityQueue<Integer> minh;

		public MedianFinder() {
			maxh = new PriorityQueue<>((a, b) -> b - a);
			minh = new PriorityQueue<>((a, b) -> a - b);
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

}