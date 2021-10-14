package class48;

import java.util.HashSet;

public class Problem_0489_RobotRoomCleaner {

	// 不要提交这个接口的内容
	interface Robot {
		public boolean move();

		public void turnLeft();

		public void turnRight();

		public void clean();
	}

	// 提交下面的内容
	public static void cleanRoom(Robot robot) {
		clean(robot, 0, 0, 0, new HashSet<>());
	}

	private static final int[][] ds = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };

	public static void clean(Robot robot, int x, int y, int d, HashSet<String> visited) {
		robot.clean();
		visited.add(x + "_" + y);
		for (int i = 0; i < 4; i++) {
			int nd = (i + d) % 4;
			int nx = ds[nd][0] + x;
			int ny = ds[nd][1] + y;
			if (!visited.contains(nx + "_" + ny) && robot.move()) {
				clean(robot, nx, ny, nd, visited);
			}
			robot.turnRight();
		}
		robot.turnRight();
		robot.turnRight();
		robot.move();
		robot.turnRight();
		robot.turnRight();
	}

}
