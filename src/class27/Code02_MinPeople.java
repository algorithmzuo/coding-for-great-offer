package class27;

import java.util.Arrays;

public class Code02_MinPeople {

//  三道题如下：
//	微信面试题
//	题目一：
//	股民小 A 有一天穿越回到了 n 天前，他能记住某只股票连续 n 天的价格；他手上有足够多的启动资金，他可以在这 n 天内多次交易，但是有个限制
//	如果已经购买了一个股票，在卖出它之前就不能再继续购买股票了。
//	到 n 天之后，小 A 不能再持有股票。
//	求问 这 n 天内，小 A 能够获得的利润的最大值
//	如果不需要手续费的话，求最大的利润
//	function(number) {
//	return number
//	}
//	输入: prices = [3, 1, 2, 8, 5, 9]
//	输出: 11
//
//	题目二：
//	企鹅厂每年都会发文化衫，文化衫有很多种，厂庆的时候，企鹅们都需要穿文化衫来拍照
//  一次采访中，记者随机遇到的企鹅，企鹅会告诉记者还有多少企鹅跟他穿一种文化衫
//  我们将这些回答放在 answers 数组里，返回鹅厂中企鹅的最少数量。
//	输入: answers = [1]    输出：2
//	输入: answers = [1, 1, 2]    输出：5
//  Leetcode题目：https://leetcode.com/problems/rabbits-in-forest/
//
//	题目三：
//	WXG 的秘书有一堆的文件袋，现在秘书需要把文件袋嵌套收纳起来。请你帮他计算下，最大嵌套数量。
//	给你一个二维整数数组 folders ，其中 folders[i] = [wi, hi] ，表示第 i 个文件袋的宽度和长度
//	当某一个文件袋的宽度和长度都比这个另外一个文件袋大的时候，前者就能把后者装起来，称之为一组文件袋。
//	请计算，最大的一组文件袋的数量是多少。
//	实例
//	输入：[[6,10],[11,14],[6,1],[16,14],[13,2]]
//	输出： 3

	// 题目一，股票系列专题，大厂刷题班15节
	// 题目三，最长递增子序列专题，大厂刷题班第9节
	// 我们来讲一下题目二
	public static int numRabbits(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		Arrays.sort(arr);
		int x = arr[0];
		int c = 1;
		int ans = 0;
		for (int i = 1; i < arr.length; i++) {
			if (x != arr[i]) {
				ans += ((c + x) / (x + 1)) * (x + 1);
				x = arr[i];
				c = 1;
			} else {
				c++;
			}
		}
		return ans + ((c + x) / (x + 1)) * (x + 1);
	}

}
