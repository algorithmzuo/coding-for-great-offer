package class35;

// 来自小红书
// 一场电影开始和结束时间可以用一个小数组来表示["07:30","12:00"]
// 已知有2000场电影开始和结束都在同一天，这一天从00:00开始到23:59结束
// 一定要选3场完全不冲突的电影来观看，返回最大的观影时间
public class Code03_WatchMovieMaxTime {

	public static int maxEnjoy(int[][] movies, int index, int time, int rest) {
		if (index == movies.length) {
			return rest == 0 ? 0 : -1;
		}
		int p1 = maxEnjoy(movies, index + 1, time, rest);
		int next = movies[index][0] >= time && rest > 0 ? maxEnjoy(movies, index + 1, movies[index][1], rest - 1) : -1;
		int p2 = next != -1 ? (movies[index][1] - movies[index][0] + next) : -1;
		return Math.max(p1, p2);
	}

}
