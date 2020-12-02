package com.runningcode.tcs;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSlider;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JF_Options extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txt_mapl;
	private JTextField txt_mapw;
	private JTextField txt_speed;
	private JSlider sl_mapl;
	private JSlider sl_mapw;
	private JSlider sl_speed;
	
	/**
	 * Create the frame.
	 */
	public JF_Options(Desktop mainJF) {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				mainJF.setOptions(null);;
			}
		});
		setTitle("\u8BBE\u7F6E");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txt_mapl = new JTextField();
		txt_mapl.setText(""+mainJF.getMaplength());
		txt_mapl.setEditable(false);
		txt_mapl.setBounds(308, 10, 66, 26);
		contentPane.add(txt_mapl);
		txt_mapl.setColumns(10);
		
		txt_mapw = new JTextField();
		txt_mapw.setEditable(false);
		txt_mapw.setBounds(308, 46, 66, 26);
		contentPane.add(txt_mapw);
		txt_mapw.setText(""+mainJF.getMapwidth());
		txt_mapw.setColumns(10);
		
		txt_speed = new JTextField();
		txt_speed.setEditable(false);
		txt_speed.setBounds(308, 81, 66, 26);
		contentPane.add(txt_speed);
		txt_speed.setText(""+SnakeAI.SPEED);
		txt_speed.setColumns(10);
		
		sl_mapl = new JSlider();
		sl_mapl.setBounds(98, 10, 200, 26);
		sl_mapl.setMinimum(10);
		sl_mapl.setMaximum(20);
		sl_mapl.setValue(mainJF.getMaplength());
		sl_mapl.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				txt_mapl.setText(""+sl_mapl.getValue());
				mainJF.setMaplength(sl_mapl.getValue());
			}
		});
		contentPane.add(sl_mapl);
		
		sl_mapw = new JSlider();
		sl_mapw.setBounds(98, 46, 200, 26);
		sl_mapw.setMinimum(10);
		sl_mapw.setMaximum(20);
		sl_mapw.setValue(mainJF.getMapwidth());
		sl_mapw.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				txt_mapw.setText(""+sl_mapw.getValue());
				mainJF.setMapwidth(sl_mapw.getValue());
			}
		});
		contentPane.add(sl_mapw);
		
		sl_speed = new JSlider();
		sl_speed.setBounds(98, 82, 200, 26);
		sl_speed.setMinimum(50);
		sl_speed.setMaximum(1000);
		sl_speed.setValue(SnakeAI.SPEED);
		sl_speed.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				txt_speed.setText(""+sl_speed.getValue());
//				mainJF.setSpeed(sl_speed.getValue());
			}
		});
		contentPane.add(sl_speed);
		
		JLabel lblNewLabel = new JLabel("\u5730\u56FE\u957F\u5EA6");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 10, 78, 26);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u5730\u56FE\u5BBD\u5EA6");
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(10, 46, 78, 26);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u86C7\u7684\u901F\u5EA6");
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(10, 82, 78, 26);
		contentPane.add(lblNewLabel_2);
		
		
	}
}
