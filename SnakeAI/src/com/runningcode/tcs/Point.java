package com.runningcode.tcs;

/**
 * @author Nightcat
 *@基础坐标类
 *包含整形类型 x，y坐标
 */
public class Point {
	
	private int x;
	private int y;
	
	/**
	 * @表明当前节点的状态
	 * 借鉴数字小键盘
	 * 7 8 9 
	 * 4 5 6 
	 * 1 2 3 
	 * turnState 有效参数为 7,9,1,3分别表示蛇主体转弯时，那个折点的状态
	 * drState 有效参数为4,2,6,8分别表示蛇主体这个结点经历的方向
	 */
	private int turnState;
	private int drState;
	
	public Point(int x, int y,int turntate,int drState) {
		this.x = x;
		this.y = y;
		this.turnState = turntate;
		this.drState = drState;
	}
	
	public Point(int x, int y,int drState) {
		this.x = x;
		this.y = y;
		this.drState = drState;
	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point() {

	}
	
	public int getTurnState() {
		return turnState;
	}

	public void setTurnState(int turnState) {
		this.turnState = turnState;
	}

	public int getDrState() {
		return drState;
	}

	public void setDrState(int drState) {
		this.drState = drState;
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	
}
