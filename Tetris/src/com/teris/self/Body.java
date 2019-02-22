package com.teris.self;

import javax.swing.*; //javax.swing.JFrame

import java.awt.*;
import java.awt.event.*;

public class Body extends JFrame {

	public Body() { // 构造器可不写返回值类型

		// 设置窗口标题及大小
		// setVisible(true);
		setTitle("Tetris          2015  NaHo");
		setSize(300, 310);
		setLocationRelativeTo(null); // 设置窗口初始位置

		// 设定菜单栏样式
		JMenuBar bar = new JMenuBar();

		JMenu menu1 = new JMenu("菜单"); // 如何设置动作
		JButton button1 = new JButton("退出");
		menu1.add(button1);
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mbutton1();
			}
		});

		JMenu menu2 = new JMenu("游戏");
		JButton button2 = new JButton("说明");
		menu2.add(button2);
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mbutton2();
			}
		});

		bar.add(menu1); // 必须在菜单对象之后才能add 按顺序执行
		bar.add(menu2);
		this.setJMenuBar(bar);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置默认窗口关闭方式
		setResizable(false); // 设置窗口大小是否可以让用户自己调整 re size able = resizable 重调大小

		TetrisPanel tetrispanel = new TetrisPanel();// 计时器生效

		addKeyListener(tetrispanel.timerlistener); // 实现了JFrame（Body）窗口的按键监听，原本监听器在tetrispanel里

		add(tetrispanel); // add 添加了terispanel中图形的部分

	}

	public static void mbutton1() {
		System.exit(0);// 执行A方法
	}

	public void mbutton2() {
		JOptionPane.showMessageDialog(null,"↑：翻转     ←↓→：移动  \n\n            空格：暂停");
	}
}
