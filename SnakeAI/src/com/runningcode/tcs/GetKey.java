package com.runningcode.tcs;

import java.awt.TextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Nightcat
 *@获取按键的处理类
 */
public class GetKey extends TextField implements Runnable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 处理后的按键值
	 * 1,2,3,4,5 = 上，下，左，右,暂停
	 */
	private int key=5;
	
	@SuppressWarnings("unused")
	private boolean runisover = false;
	
	
	//默认的get，set方法
	public int getKey() {
		return key;
	}
	
	public void setKey(int key) {
		this.key = key;
	}

	//这个类继承接口的线程
	private Thread t = null;

	public GetKey() {
		this.addKeyListener(new KeyEvents());
	}

	@Override
	//run里面包含需要多线程循环执行的代码
	public void run() {
		while(true) {
			//code
				//需要线程调用代码写在这里
			//每次执行后线程休眠时间
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void start () {
	      if (t == null) {
	         t = new Thread (this,"getkey");
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
	
	/**
	 * @param keycode
	 * @处理键值码
	 * @return 将keycode处理后的key
	 */
	private int keyCodeToKey(int keycode) {
		/*
        	keycode键盘 按键 - 键码 对应表 - Yiven - 博客园
			https://www.cnblogs.com/yiven/p/7118056.html
		 */
		switch(keycode) {
		case 37:return 3;//左
		case 38:return 1;//上
		case 39:return 4;//右
		case 40:return 2;//下
		case 80:return 5;//P
		}
		return -1;
	}
	
	/**
	 * @author Nightcat
	 *@按键适配器 @内部类
	 *用来包装按键事件
	 */
	private class KeyEvents extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
        	int keycode = e.getKeyCode();
            //System.out.println("keycode:"+keycode);  
            int tempkey = keyCodeToKey(keycode);

            if(tempkey!=-1) {
            	key = tempkey;
            	//System.out.println("key:"+key);  
            }
        }
    }

}
