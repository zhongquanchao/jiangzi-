package com.runningcode.tcs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;

import javax.swing.JPanel;


/**
 * @author Nightcat
 *@自定义画板
 *继承自Jpanel,重写paint();
 */
public class GameJPanel extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;
	
	//这个类继承接口的线程
	private Thread t = null;
	
	@SuppressWarnings("unused")
	private boolean runisover = false;
	
	/**
	 * @双缓冲用的临时图像画板
	 */
	private Image image = null;
	
	/**
	 * @取得游戏信息对象
	 */
	private Game game = null;
	
	/**
	 * @param game 获取的游戏信息对象
	 */
	public GameJPanel(Game game){
		this.game = game;
	}
	
	/**
	 * @为画板设置一个新的Game对象
	 * @param game
	 */
	public void setGame(Game game) {
		this.game = game;
	}



	/**
	 * @双缓冲技术应用 绘制缓冲区
	 */
	private void drawBufferedImage() {
		// 创建缓冲区对象
	    image = createImage(this.getWidth(), this.getHeight());
	    // 获取图像上下文对象
	    Graphics g = image.getGraphics();
	    
	    int xp=1;
	    int yp=20;
	    
	    //绘制得分
	    g.drawString("当前得分："+game.getScore(), xp, yp-8);

	    //地图绘制部分
	    int[][] map = game.getMap();
	    int drawx=0;
	    int drawy=0;
	    for(int m=0;m<map.length;m++) {
	    	for(int n =0;n<map[0].length;n++) {
	    		drawx = m*(20+2)+xp;
	    		drawy = n*(20+2)+yp;
	    		
	    		if(map[m][n]==Game.Gridtype.wall.getIndex()) {
	    			g.setColor(Color.BLACK);
	    		}
	    		else {
	    			g.setColor(Color.GRAY);
	    		}
	    		g.fillRect(drawx, drawy, 20, 20);
	    	}
	    }
	    
	    //蛇绘制部分
	    LinkedList<Point> snake = game.getSnake();
	    for(int m=0;m<snake.size();m++) {
	    	//绘制首个结点
	    	if(m==0) {
	    		g.setColor(Color.BLUE);
	    	}
	    	//绘制末尾结点
	    	else if(m==snake.size()-1){
	    		switch(snake.get(m).getDrState()) {
	    		case 2:g.setColor(Color.YELLOW);break;
	    		case 8:g.setColor(Color.YELLOW);break;
	    		case 4:g.setColor(Color.YELLOW);break;
	    		case 6:g.setColor(Color.YELLOW);break;
	    		
	    		default:g.setColor(Color.BLACK);break;
	    		}
	    	}
	    	//绘制中间结点
	    	else {
	    		//绘制方向部分
	    		switch(snake.get(m).getDrState()) {
	    		case 2:g.setColor(Color.GREEN);break;
	    		case 8:g.setColor(Color.GREEN);break;
	    		case 4:g.setColor(Color.PINK);break;
	    		case 6:g.setColor(Color.PINK);break;
	    		
	    		//default:g.setColor(Color.BLACK);break;
	    		}
	    		//绘制转弯部分
	    		switch(snake.get(m).getTurnState()) {
	    		case 1:g.setColor(Color.orange);break;
	    		case 3:g.setColor(Color.orange);break;
	    		case 7:g.setColor(Color.orange);break;
	    		case 9:g.setColor(Color.orange);break;
	    		
	    		//default:g.setColor(Color.BLACK);break;
	    		}
	    	}
	    	g.fillRect(snake.get(m).getX()*(20+2)+xp, snake.get(m).getY()*(20+2)+yp, 20, 20);
	    }
	    
	    //食物绘制部分
	    g.setColor(Color.RED);
    	g.fillRect(game.getFood().getX()*(20+2)+xp,game.getFood().getY()*(20+2)+yp, 20, 20);
    	
    	//绘制结束画面
    	if(game.getGameState()==-1) {
    		int x = this.getWidth()/2-200/2;
    		int y = this.getHeight()/2-100/2;
    		g.setColor(Color.WHITE);
    		g.fillRect(x, y, 200, 100);
    		g.setFont(new Font("微软雅黑", Font.ITALIC, 30));
    		g.setColor(Color.PINK);
    		g.drawRect(x, y, 200, 100);
    		g.drawString("游戏结束", x+40, y+30);
    		g.drawString(game.getScore()+"分", x+40, y+80);
    	}
    	
    	//绘制暂停画面
    	if(game.getGameState()==1) {
    		int x = this.getWidth()/2-200/2;
    		int y = this.getHeight()/2-100/2;
    		g.setColor(Color.WHITE);
    		g.fillRect(x, y, 200, 100);
    		g.setFont(new Font("微软雅黑", Font.ITALIC, 30));
    		g.setColor(Color.PINK);
    		g.drawRect(x, y, 200, 100);
    		g.drawString("暂停", x+60, y+30);
    		g.drawString("按方向键开始", x+10, y+80);
    	}
	    
	    
	    
	}
	/**
	 * @双缓冲技术应用 重写paint函数
	 */
	public void paint(Graphics g){
		drawBufferedImage();
	    g.drawImage(image, 0, 0, this);
	}

		

	@Override
	//run里面包含需要多线程循环执行的代码
	public void run() {
		while(true) {
			//code
				//需要线程调用代码写在这里
			if(game!=null) {
				this.repaint();
			}
				//System.out.println("gp id run.");
			//每次执行后线程休眠时间
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void start () {
	      if (t == null) {
	         t = new Thread (this,"game");
	         t.start ();
	      }
	}
	
	@SuppressWarnings("deprecation")
	public void stop () {
		if (t == null) {
	         t.stop();
	      }
	}
	
	public void runover() {
		runisover = true;
	}

}
