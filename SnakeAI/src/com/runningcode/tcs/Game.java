package com.runningcode.tcs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/**
 * @author 李栋
 * 
 * @游戏核心类 主要是游戏的核心算法
 */

public class Game implements Runnable {

	/**
	 * @蛇的主体链表
	 */
	private LinkedList<Point> snake = null;

	/**
	 * @游戏得分
	 */
	private int score = 0;

	/**
	 * @当前游戏状态 -1，0,1,2 = 输，正常，暂停，胜利
	 */
	private int gameState = 1;

	/**
	 * @判断是否需要结束Run函数
	 */
	private boolean runisover = false;

	/**
	 * @蛇的速度
	 */
	private int speed = 50;
	/**
	 * @当前按键
	 */
	private GetKey nowkey = null;

	/**
	 * @食物位置
	 */
	private Point food = null;

	/**
	 * @地图数组
	 */
	private int[][] map;

	/**
	 * @地图宽度
	 */
	private int mapwidth = 0;

	/**
	 * @地图长度
	 */
	private int maplength = 0;

	/**
	 * @方向枚举类型定义
	 */
	public enum Direction {
		up, down, left, right, stop
	};

	/**
	 * @表示当前运动方向
	 */
	private Direction nowDirection = Direction.stop;

	/**
	 * @author Nightcat
	 * @自定义枚举类Gridtype用来表示地图块的类型
	 */
	public static enum Gridtype {

		wall(1);

		private int index;

		/**
		 * @地图块类型的构造函数
		 * @param index
		 */
		private Gridtype(int index) {
			this.index = index;
		}

		/**
		 * @获取地图块index
		 * @return
		 */
		public int getIndex() {
			return index;
		}

	};

	// 这个类继承接口的线程
	private Thread t = null;

	/**
	 * @Game类的构造函数
	 * @param mapwidth
	 *            地图宽度
	 * @param maplength
	 *            地图长度
	 * @param nowkey
	 *            按键对象
	 */
	public Game(int mapwidth, int maplength, GetKey nowkey, int speed) {
		map = new int[mapwidth][maplength];
		this.nowkey = nowkey;
		this.speed = speed;
		this.mapwidth = mapwidth;
		this.maplength = maplength;
		initGame();
		// 播放开始的声音
		SoundPlay.playStar();

	}

	/**
	 * @蛇体移动函数
	 */
	public void move() {
		// 获取方向
		getnowDirection();
		// 创建新的蛇头结点
		boolean pdstop = addSnakePoint();
		//用来跳过暂停状态
		if(pdstop == false) {
			return;
		}
		// 每移动一次要判断是否吃到了食物
		if (eatFood()) {
			// 播放吃东西的声音
			SoundPlay.playEat();
			// 吃到了食物
			createFood();
		} else {
			// 没吃到删除蛇尾,并且处理第二为转弯的结点
			if (snake.size() > 2) {
				Point last = snake.get(snake.size() - 1);
				int lastN = last.getTurnState();
				if (lastN == 1 || lastN == 3 || lastN == 7 || lastN == 9) {
					last.setDrState(snake.get(snake.size() - 2).getDrState());
				}
			}
			snake.removeLast();
		}
		// 判断游戏是否结束
		isGameOver();
	}

	/**
	 * @添加节点判断函数
	 * @return
	 */
	private boolean addSnakePoint() {
		Point head = snake.getFirst();
		int x = head.getX();
		int y = head.getY();
		int dr = 0;
		switch (nowDirection) {
		case up:
			y--;
			dr = 8;
			break;
		case down:
			y++;
			dr = 2;
			break;
		case left:
			x--;
			dr = 4;
			break;
		case right:
			x++;
			dr = 6;
			break;
		case stop:
			gameState = 1;
			return false;
		default:
			break;
		}

		snake.addFirst(new Point( x, y, dr));

		if (snake.size() > 2) {
			pdTrunPoint();
		}
		
		return true;
	}

	/**
	 * @判断转弯结点函数
	 */
	private void pdTrunPoint() {
		Point before = snake.get(0);
		Point centre = snake.get(1);
		Point after = snake.get(2);
		if (centre.getX() + 1 == before.getX() && centre.getY() == before.getY() && centre.getX() == after.getX()
				&& centre.getY() + 1 == after.getY()
				|| centre.getX() == before.getX() && centre.getY() + 1 == before.getY()
						&& centre.getX() + 1 == after.getX() && centre.getY() == after.getY()) {
			centre.setTurnState(1);
			return;
		}
		if (centre.getX() == before.getX() && centre.getY() + 1 == before.getY() && centre.getX() - 1 == after.getX()
				&& centre.getY() == after.getY()
				|| centre.getX() - 1 == before.getX() && centre.getY() == before.getY() && centre.getX() == after.getX()
						&& centre.getY() + 1 == after.getY()) {
			centre.setTurnState(3);
			return;
		}
		if (centre.getX() - 1 == before.getX() && centre.getY() == before.getY() && centre.getX() == after.getX()
				&& centre.getY() - 1 == after.getY()
				|| centre.getX() == before.getX() && centre.getY() - 1 == before.getY()
						&& centre.getX() - 1 == after.getX() && centre.getY() == after.getY()) {
			centre.setTurnState(9);
			return;
		}
		if (centre.getX() == before.getX() && centre.getY() - 1 == before.getY() && centre.getX() + 1 == after.getX()
				&& centre.getY() == after.getY()
				|| centre.getX() + 1 == before.getX() && centre.getY() == before.getY() && centre.getX() == after.getX()
						&& centre.getY() - 1 == after.getY()) {
			centre.setTurnState(7);
			return;
		}
	}

	/**
	 * @从GetKey类获取当前按键函数
	 */
	public void getnowDirection() {
		refreshMap();
		printMap(map);
		
		int i_nowkey = nowkey.getKey();
		Direction tempd = Direction.stop;
		
		if(i_nowkey == 5){
			tempd = Direction.stop;
		}else{
			
			//采用自动模式
			tempd = SnakeAI.autoPlay(map);
			
		}
		
		// 对回头的情况进行过滤
		if (nowDirection == Direction.up && tempd == Direction.down)
			return;
		if (nowDirection == Direction.down && tempd == Direction.up)
			return;
		if (nowDirection == Direction.left && tempd == Direction.right)
			return;
		if (nowDirection == Direction.right && tempd == Direction.left)
			return;

		// 判断是否为转弯，然后播放声音
		if (nowDirection != tempd) {
			SoundPlay.playTurn();
		}

		// 正确操作则成功赋值
		nowDirection = tempd;
	}

	/**
	 * @随机生成食物函数
	 */
	public void createFood() {
		// 创建一个随机对象
		Random random = new Random();

		while (true) {
			int x = random.nextInt(mapwidth);
			int y = random.nextInt(maplength);
			if (map[x][y] != Gridtype.wall.getIndex()) {
				boolean isok = true;
				for (int k = 0; k < snake.size(); k++) {
					Point tempsnake = snake.get(k);
					if (tempsnake.getX() == x && tempsnake.getY() == y) {
						isok = false;
					}
				}
				if (isok == false) {
					continue;
				} else {
					food = new Point(x, y);
					return;
				}
			}
		}

	}

	/**
	 * @初始化蛇的位置
	 */
	public void initSnake() {
		int x = mapwidth / 2;
		int y = maplength / 2;
		snake = new LinkedList<Point>();
		snake.addFirst(new Point(x, y));
	}

	/**
	 * @初始化地图边界
	 */
	public void initBackground() {
		for (int rows = 0; rows < maplength; rows++) {
			for (int cols = 0; cols < mapwidth; cols++) {
				// 第一行跟最后一行，第一列跟最后一列
				if (rows == 0 || rows == (maplength - 1) || cols == 0 || cols == (mapwidth - 1)) {
					map[cols][rows] = Gridtype.wall.getIndex();
				}
			}
		}
	}

	/**
	 * @吃食物判断
	 * @return 成功返回true，失败返回false
	 */
	public boolean eatFood() {
		// 先获取原来的蛇头
		Point head = snake.getFirst();
		if (head.getX() == food.getX() && head.getY() == food.getY()) {
			score += 10;
			return true;
		}
		return false;

	}

	/**
	 * @判断游戏是否已经结束
	 */
	public void isGameOver() {
		// 撞墙死亡
		Point head = snake.getFirst();

		if (map[head.getX()][head.getY()] == Gridtype.wall.getIndex()) {
			gameState = -1;
		}
		// 咬到自己 蛇身
		if (snake.size() > 1) {
			for (int k = 1; k < snake.size(); k++) {
				Point tempsnake = snake.get(k);
				if (tempsnake.getX() == head.getX() && tempsnake.getY() == head.getY()) {
					gameState = -1;
				}
			}
		}

	}

	/**
	 * @初始化Game配置
	 */
	public void initGame() {
		initBackground();
		initSnake();
		createFood();
	}

	@Override
	// run里面包含需要多线程循环执行的代码
	public void run() {
		while (true) {
			if (runisover == true) {
				break;
			}
			move();
			try {
				// 如果已经判断结束，则结束run函数
				if (gameState == -1) {
					// 播放游戏失败音效
					SoundPlay.playGameOver();
					break;
				}
				// 如果暂停则等待其他按键按下，并且当前线程死循环
				while (gameState == 1) {
					if (nowkey.getKey() != 5) {
						gameState = 0;
						break;
					}
					Thread.sleep(speed);
					// System.out.println("puser");
				}
				// 每次执行后线程休眠时间
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//把食物、蛇头、蛇身体描绘到map上，2代表食物，3代表蛇头，4代表蛇的身体
	private void refreshMap(){
		for(int i=1; i<map.length-1;i++){
			for(int j=1; j<map.length-1;j++){
				map[i][j] = 0;
			}
		}
		map[food.getY()][food.getX()] = 2;
		map[snake.getFirst().getY()][snake.getFirst().getX()] = 3;
		for(int i=1; i<snake.size(); i++){
			map[snake.get(i).getY()][snake.get(i).getX()] = 4;
		}
	}
	
	private void printMap(int[][] map){
		System.out.println();
		for(int[] arr : map){
			System.out.println(Arrays.toString(arr));
		}
		System.out.println();
	}

	public void start() {
		if (t == null) {
			t = new Thread(this, "game");
			t.start();
		}
	}

	@SuppressWarnings("deprecation")
	public void stop() {
		if (t == null) {
			t.stop();
		}
	}

	public void runover() {
		runisover = true;
	}

	// 构造函数
	public LinkedList<Point> getSnake() {
		return snake;
	}

	public void setSnake(LinkedList<Point> snake) {
		this.snake = snake;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getGameState() {
		return gameState;
	}

	public void setGameState(int gameState) {
		this.gameState = gameState;
	}

	public GetKey getNowkey() {
		return nowkey;
	}

	public void setNowkey(GetKey nowkey) {
		this.nowkey = nowkey;
	}

	public Point getFood() {
		return food;
	}

	public void setFood(Point food) {
		this.food = food;
	}

	public int[][] getMap() {
		return map;
	}

	public void setMap(int[][] map) {
		this.map = map;
	}

	public Direction getNowDirection() {
		return nowDirection;
	}

	public void setNowDirection(Direction nowDirection) {
		this.nowDirection = nowDirection;
	}
}
