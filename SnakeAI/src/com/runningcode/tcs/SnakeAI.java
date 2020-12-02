package com.runningcode.tcs;

import java.util.Arrays;

import com.runningcode.tcs.Game.Direction;

/**
 * 完成贪吃蛇的AI算法，让贪吃蛇能自行寻找食物
 * 此实现只是一个简单样例
 * 
 * map中，0代表空白，1代表边界，2代表食物，3代表贪吃蛇的头部，4代表贪吃蛇的身体
 * 
 * 返回Direction.left表示贪吃蛇向左走，Direction.right向右，Direction.up向上，Direction.down向下
 * 
 * @author obj
 * @email oubijie@139.com
 * @vserion 2019年7月24日
 *
 */
public class SnakeAI {
	//蛇行走的速度，这里是间隔的毫秒数，数字越小蛇走得越快
	public static final int SPEED = 500;

	public static Direction autoPlay(int[][] map) {
		
		return Direction.down;
	}
	
	

}
