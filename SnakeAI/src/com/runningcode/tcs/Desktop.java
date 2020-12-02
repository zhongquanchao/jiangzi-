package com.runningcode.tcs;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * @author Nightcat
 *@窗口主类 负责承载整体
 */
public class Desktop extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * 当前窗口的对象
	 */
	private Desktop thisDesktop = null;
	/**
	 * 主容器
	 */
	private JPanel contentPane = null;
	
	//设置参数
	private int mapwidth = 20;
	private int maplength = 20;
	
	//当前窗口位置坐标
	private int nowJFx=0;
	private int nowJFy=0;
	
	//主实例对象
	private Game game = null;
	private GameJPanel gameJpanel = null;
	private GetKey getkey = null;
	
	/**
	 * @子窗口 @设置窗口
	 */
	private JF_Options options = null;
	/**
	 * @子窗口 @关于窗口
	 */
	private JF_About about = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Desktop frame = new Desktop();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * @初始化Game类
	 */
	public void initGame() {
		game = new Game(mapwidth, maplength, getkey , SnakeAI.SPEED);
		game.setGameState(1);
		game.start();
	}
	
	/**
	 * @初始化GetKey类
	 */
	public void initGetKey() {
		getkey = new GetKey();
		contentPane.add(getkey);
		getkey.start();
	}
	
	/**
	 * @初始化GameJPanel类
	 */
	public void initGameJPanel() {
		gameJpanel = new GameJPanel(game);
		gameJpanel.setBounds(0, 0, mapwidth*(20+2), maplength*(20+2)+30);
		thisDesktop.setBounds( nowJFx, nowJFy, mapwidth*(20+2)+6, maplength*(20+2)+73);
		contentPane.add(gameJpanel);
		gameJpanel.start();
	}
	
	/**
	 * @初始化所有对象
	 */
	public void initAll() {
		initGetKey();
		initGame();
		initGameJPanel();
	}
	
	/**
	 * @通过按钮新游戏创建新的Game
	 */
	public void nb_newGame() {
		if(game!=null) {
			game.runover();
		}
		initGame();
		gameJpanel.setGame(game);
		gameJpanel.setBounds(0, 0, mapwidth*(20+2), maplength*(20+2)+30);
		thisDesktop.setBounds( nowJFx, nowJFy, mapwidth*(20+2)+6, maplength*(20+2)+73);
	}
	
	
	/**
	 * @主构造函数
	 */
	public Desktop() {
		setTitle("\u8D2A\u5403\u86C7");
		//设置窗口移动监听事件用来记录窗口位置
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent arg0) {
				nowJFx = thisDesktop.getX();
				nowJFy = thisDesktop.getY();
			}
		});
		
		setResizable(false);
		thisDesktop = this;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 615, 485);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("\u6E38\u620F");
		menuBar.add(mnNewMenu);
		
		//新游戏按钮事件
		JMenuItem nb_newgame = new JMenuItem("\u65B0\u6E38\u620F");
		nb_newgame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nb_newGame();
			}
		});
		mnNewMenu.add(nb_newgame);
		
		JMenuItem nb_pause = new JMenuItem("\u6682\u505C");
		mnNewMenu.add(nb_pause);
		
		//打开设置窗口事件
		JMenuItem nb_options = new JMenuItem("\u8BBE\u7F6E");
		nb_options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(options!=null) return;
				options = new JF_Options(thisDesktop);
				options.setBounds(thisDesktop.getX(), thisDesktop.getY(), 398, 156);
				options.setVisible(true);
				
			}
		});
		mnNewMenu.add(nb_options);
		
		JMenuItem nb_exit = new JMenuItem("\u9000\u51FA");
		nb_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(nb_exit);
		
		JMenu mnNewMenu_1 = new JMenu("\u5173\u4E8E");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem nb_about = new JMenuItem("\u5173\u4E8E\u4F5C\u8005");
		nb_about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(about!=null) return;
				about = new JF_About(thisDesktop);
				about.setBounds(thisDesktop.getX(), thisDesktop.getY(), 450, 300);
				about.setVisible(true);
			}
		});
		mnNewMenu_1.add(nb_about);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//初始化游戏
		initAll();
		
		//初始屏幕位置处理
		Dimension   screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screensize.getWidth();
		int height = (int)screensize.getHeight();
		nowJFx = width/2-this.getWidth()/2;
		nowJFy = height/2-this.getHeight()/2;
		this.setLocation( nowJFx, nowJFy);
	}
	
	//基本get与set方法
		public JF_Options getOptions() {
			return options;
		}

		public void setOptions(JF_Options options) {
			this.options = options;
		}

		public JF_About getAbout() {
			return about;
		}

		public void setAbout(JF_About about) {
			this.about = about;
		}

		public int getMapwidth() {
			return mapwidth;
		}

		public void setMapwidth(int mapwidth) {
			this.mapwidth = mapwidth;
		}

		public int getMaplength() {
			return maplength;
		}

		public void setMaplength(int maplength) {
			this.maplength = maplength;
		}

}
