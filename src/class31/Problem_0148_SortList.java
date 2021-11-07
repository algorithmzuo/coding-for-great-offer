package class31;

public class Problem_0148_SortList {

	public static class ListNode {
		int val;
		ListNode next;

		public ListNode(int v) {
			val = v;
		}
	}

	// 链表的归并排序
	// 时间复杂度O(N*logN), 因为是链表所以空间复杂度O(1)
	public static ListNode sortList1(ListNode head) {
		int N = 0;
		ListNode cur = head;
		while (cur != null) {
			N++;
			cur = cur.next;
		}
		ListNode h = head;
		ListNode teamFirst = head;
		ListNode pre = null;
		for (int len = 1; len < N; len <<= 1) {
			while (teamFirst != null) {
				// 左组从哪到哪 ls le
				// 右组从哪到哪 rs re
				// 左 右 next
				ListNode[] hthtn = hthtn(teamFirst, len);
				// ls...le rs...re -> merge去
				// 整体的头、整体的尾
				ListNode[] mhmt = merge(hthtn[0], hthtn[1], hthtn[2], hthtn[3]);
				if (h == teamFirst) {
					h = mhmt[0];
					pre = mhmt[1];
				} else {
					pre.next = mhmt[0];
					pre = mhmt[1];
				}
				teamFirst = hthtn[4];
			}
			teamFirst = h;
			pre = null;
		}
		return h;
	}

	public static ListNode[] hthtn(ListNode teamFirst, int len) {
		ListNode ls = teamFirst;
		ListNode le = teamFirst;
		ListNode rs = null;
		ListNode re = null;
		ListNode next = null;
		int pass = 0;
		while (teamFirst != null) {
			pass++;
			if (pass <= len) {
				le = teamFirst;
			}
			if (pass == len + 1) {
				rs = teamFirst;
			}
			if (pass > len) {
				re = teamFirst;
			}
			if (pass == (len << 1)) {
				break;
			}
			teamFirst = teamFirst.next;
		}
		le.next = null;
		if (re != null) {
			next = re.next;
			re.next = null;
		}
		return new ListNode[] { ls, le, rs, re, next };
	}

	public static ListNode[] merge(ListNode ls, ListNode le, ListNode rs, ListNode re) {
		if (rs == null) {
			return new ListNode[] { ls, le };
		}
		ListNode head = null;
		ListNode pre = null;
		ListNode cur = null;
		ListNode tail = null;
		while (ls != le.next && rs != re.next) {
			if (ls.val <= rs.val) {
				cur = ls;
				ls = ls.next;
			} else {
				cur = rs;
				rs = rs.next;
			}
			if (pre == null) {
				head = cur;
				pre = cur;
			} else {
				pre.next = cur;
				pre = cur;
			}
		}
		if (ls != le.next) {
			while (ls != le.next) {
				pre.next = ls;
				pre = ls;
				tail = ls;
				ls = ls.next;
			}
		} else {
			while (rs != re.next) {
				pre.next = rs;
				pre = rs;
				tail = rs;
				rs = rs.next;
			}
		}
		return new ListNode[] { head, tail };
	}

	// 链表的快速排序
	// 时间复杂度O(N*logN), 空间复杂度O(logN)
	public static ListNode sortList2(ListNode head) {
		int n = 0;
		ListNode cur = head;
		while (cur != null) {
			cur = cur.next;
			n++;
		}
		return process(head, n).head;
	}

	public static class HeadAndTail {
		public ListNode head;
		public ListNode tail;

		public HeadAndTail(ListNode h, ListNode t) {
			head = h;
			tail = t;
		}
	}

	public static HeadAndTail process(ListNode head, int n) {
		if (n == 0) {
			return new HeadAndTail(head, head);
		}
		int index = (int) (Math.random() * n);
		ListNode cur = head;
		while (index-- != 0) {
			cur = cur.next;
		}
		Record r = partition(head, cur);
		HeadAndTail lht = process(r.lhead, r.lsize);
		HeadAndTail rht = process(r.rhead, r.rsize);
		if (lht.tail != null) {
			lht.tail.next = r.mhead;
		}
		r.mtail.next = rht.head;
		return new HeadAndTail(lht.head != null ? lht.head : r.mhead, rht.tail != null ? rht.tail : r.mtail);
	}

	public static class Record {
		public ListNode lhead;
		public int lsize;
		public ListNode rhead;
		public int rsize;
		public ListNode mhead;
		public ListNode mtail;

		public Record(ListNode lh, int ls, ListNode rh, int rs, ListNode mh, ListNode mt) {
			lhead = lh;
			lsize = ls;
			rhead = rh;
			rsize = rs;
			mhead = mh;
			mtail = mt;
		}
	}

	public static Record partition(ListNode head, ListNode mid) {
		ListNode lh = null;
		ListNode lt = null;
		int ls = 0;
		ListNode mh = null;
		ListNode mt = null;
		ListNode rh = null;
		ListNode rt = null;
		int rs = 0;
		ListNode tmp = null;
		while (head != null) {
			tmp = head.next;
			head.next = null;
			if (head.val < mid.val) {
				if (lh == null) {
					lh = head;
					lt = head;
				} else {
					lt.next = head;
					lt = head;
				}
				ls++;
			} else if (head.val > mid.val) {
				if (rh == null) {
					rh = head;
					rt = head;
				} else {
					rt.next = head;
					rt = head;
				}
				rs++;
			} else {
				if (mh == null) {
					mh = head;
					mt = head;
				} else {
					mt.next = head;
					mt = head;
				}
			}
			head = tmp;
		}
		return new Record(lh, ls, rh, rs, mh, mt);
	}

}
