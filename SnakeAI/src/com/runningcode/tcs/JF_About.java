package com.runningcode.tcs;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JF_About extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public JF_About(Desktop mainJF) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				mainJF.setAbout(null);
			}
		});
		setTitle("\u5173\u4E8E");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNightcat = new JLabel("\u4F5C\u8005\uFF1A Nightcat\uFF0C\u6768\u8D85\u8D85\uFF0C\u674E\u680B");
		lblNightcat.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		lblNightcat.setBounds(56, 201, 344, 46);
		contentPane.add(lblNightcat);
		
		JLabel label = new JLabel("");
		label.setBounds(5, 5, 434, 261);
		label.setIcon(new ImageIcon(JF_About.class.getResource("/imgs/bg.png")));
		contentPane.add(label);
	}

}
