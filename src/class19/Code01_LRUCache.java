package class19;

import java.util.HashMap;

// 本题测试链接 : https://leetcode.com/problems/lru-cache/
// 提交时把类名和构造方法名改成 : LRUCache
public class Code01_LRUCache {

	public Code01_LRUCache(int capacity) {
		cache = new MyCache<>(capacity);
	}

	private MyCache<Integer, Integer> cache;

	public int get(int key) {
		Integer ans = cache.get(key);
		return ans == null ? -1 : ans;
	}

	public void put(int key, int value) {
		cache.set(key, value);
	}

	public static class Node<K, V> {
		public K key;
		public V value;
		public Node<K, V> last;
		public Node<K, V> next;

		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	public static class NodeDoubleLinkedList<K, V> {
		private Node<K, V> head;
		private Node<K, V> tail;

		public NodeDoubleLinkedList() {
			head = null;
			tail = null;
		}

		// 现在来了一个新的node，请挂到尾巴上去
		public void addNode(Node<K, V> newNode) {
			if (newNode == null) {
				return;
			}
			if (head == null) {
				head = newNode;
				tail = newNode;
			} else {
				tail.next = newNode;
				newNode.last = tail;
				tail = newNode;
			}
		}

		// node 入参，一定保证！node在双向链表里！
		// node原始的位置，左右重新连好，然后把node分离出来
		// 挂到整个链表的尾巴上
		public void moveNodeToTail(Node<K, V> node) {
			if (tail == node) {
				return;
			}
			if (head == node) {
				head = node.next;
				head.last = null;
			} else {
				node.last.next = node.next;
				node.next.last = node.last;
			}
			node.last = tail;
			node.next = null;
			tail.next = node;
			tail = node;
		}

		public Node<K, V> removeHead() {
			if (head == null) {
				return null;
			}
			Node<K, V> res = head;
			if (head == tail) {
				head = null;
				tail = null;
			} else {
				head = res.next;
				res.next = null;
				head.last = null;
			}
			return res;
		}

	}

	public static class MyCache<K, V> {
		private HashMap<K, Node<K, V>> keyNodeMap;
		private NodeDoubleLinkedList<K, V> nodeList;
		private final int capacity;

		public MyCache(int cap) {
			keyNodeMap = new HashMap<K, Node<K, V>>();
			nodeList = new NodeDoubleLinkedList<K, V>();
			capacity = cap;
		}

		public V get(K key) {
			if (keyNodeMap.containsKey(key)) {
				Node<K, V> res = keyNodeMap.get(key);
				nodeList.moveNodeToTail(res);
				return res.value;
			}
			return null;
		}

		// set(Key, Value)
		// 新增  更新value的操作
		public void set(K key, V value) {
			if (keyNodeMap.containsKey(key)) {
				Node<K, V> node = keyNodeMap.get(key);
				node.value = value;
				nodeList.moveNodeToTail(node);
			} else { // 新增！
				Node<K, V> newNode = new Node<K, V>(key, value);
				keyNodeMap.put(key, newNode);
				nodeList.addNode(newNode);
				if (keyNodeMap.size() == capacity + 1) {
					removeMostUnusedCache();
				}
			}
		}

		private void removeMostUnusedCache() {
			Node<K, V> removeNode = nodeList.removeHead();
			keyNodeMap.remove(removeNode.key);
		}

	}

}
