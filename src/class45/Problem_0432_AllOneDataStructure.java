package class45;

import java.util.HashMap;
import java.util.HashSet;

// 提交时修改类名、构造方法名
// 从Problem_0432_AllOneDataStructure改名为AllOne
public class Problem_0432_AllOneDataStructure {

	public static class Bucket {
		public int label;
		public HashSet<String> keys;
		public Bucket last;
		public Bucket next;

		public Bucket(int l) {
			label = l;
			keys = new HashSet<>();
			last = null;
			next = null;
		}
	}

	private Bucket head;
	private Bucket tail;
	private HashMap<String, Bucket> bucketMap;

	public Problem_0432_AllOneDataStructure() {
		head = null;
		tail = null;
		bucketMap = new HashMap<>();
	}

	// 如果这个bucket已经没有字符串了，请在bucket双向链表中将其删掉
	private void deleteBucket(Bucket bucket) {
		if (bucket.keys.size() == 0) {
			if (head == tail) {
				head = null;
				tail = null;
			} else {
				if (head == bucket) {
					head = bucket.next;
					head.last = null;
					bucket.next = null;
				} else if (tail == bucket) {
					tail = bucket.last;
					tail.next = null;
					bucket.last = null;
				} else {
					bucket.last.next = bucket.next;
					bucket.next.last = bucket.last;
					bucket.next = null;
					bucket.last = null;
				}
			}
		}
	}

	// 前一个环境pre，要插入的是mid，后一个环境是pos，怎么插入mid？
	private void insertBucket(Bucket pre, Bucket mid, Bucket pos) {
		if (pre == null && pos == null) {
			head = mid;
			tail = mid;
		} else {
			if (pre == null) {
				mid.next = pos;
				pos.last = mid;
				head = mid;
			} else if (pos == null) {
				pre.next = mid;
				mid.last = pre;
				tail = mid;
			} else {
				pre.next = mid;
				pos.last = mid;
				mid.last = pre;
				mid.next = pos;
			}
		}
	}

	public void inc(String key) {
		if (bucketMap.containsKey(key)) {
			Bucket cur = bucketMap.get(key);
			int label = cur.label;
			if (cur.next != null && cur.next.label == label + 1) {
				cur.keys.remove(key);
				cur.next.keys.add(key);
				bucketMap.put(key, cur.next);
			} else {
				Bucket next = new Bucket(cur.label + 1);
				cur.keys.remove(key);
				next.keys.add(key);
				bucketMap.put(key, next);
				insertBucket(cur, next, cur.next);
			}
			deleteBucket(cur);
		} else {
			if (head != null && head.label == 1) {
				head.keys.add(key);
				bucketMap.put(key, head);
			} else {
				Bucket cur = new Bucket(1);
				cur.keys.add(key);
				bucketMap.put(key, cur);
				if (head == null) {
					head = cur;
					tail = cur;
				} else {
					insertBucket(null, cur, head);
				}
			}
		}
	}

	public void dec(String key) {
		if (bucketMap.containsKey(key)) {
			Bucket cur = bucketMap.get(key);
			int label = cur.label;
			if (label == 1) {
				cur.keys.remove(key);
				bucketMap.remove(key);
			} else if (cur.last != null && cur.last.label == label - 1) {
				cur.keys.remove(key);
				cur.last.keys.add(key);
				bucketMap.put(key, cur.last);
			} else {
				Bucket last = new Bucket(cur.label - 1);
				cur.keys.remove(key);
				last.keys.add(key);
				bucketMap.put(key, last);
				insertBucket(cur.last, last, cur);
			}
			deleteBucket(cur);
		}
	}

	public String getMaxKey() {
		if (tail == null) {
			return "";
		} else {
			String ans = null;
			for (String key : tail.keys) {
				ans = key;
				break;
			}
			return ans;
		}
	}

	public String getMinKey() {
		if (head == null) {
			return "";
		} else {
			String ans = null;
			for (String key : head.keys) {
				ans = key;
				break;
			}
			return ans;
		}
	}

}
