package com.teris.self;

import javax.swing.*; //javax.swing.JFrame

import java.awt.*;
import java.awt.event.*;

public class Body extends JFrame {

	public Body() { // �������ɲ�д����ֵ����

		// ���ô��ڱ��⼰��С
		// setVisible(true);
		setTitle("Tetris          2015  NaHo");
		setSize(300, 310);
		setLocationRelativeTo(null); // ���ô��ڳ�ʼλ��

		// �趨�˵�����ʽ
		JMenuBar bar = new JMenuBar();

		JMenu menu1 = new JMenu("�˵�"); // ������ö���
		JButton button1 = new JButton("�˳�");
		menu1.add(button1);
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mbutton1();
			}
		});

		JMenu menu2 = new JMenu("��Ϸ");
		JButton button2 = new JButton("˵��");
		menu2.add(button2);
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mbutton2();
			}
		});

		bar.add(menu1); // �����ڲ˵�����֮�����add ��˳��ִ��
		bar.add(menu2);
		this.setJMenuBar(bar);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ����Ĭ�ϴ��ڹرշ�ʽ
		setResizable(false); // ���ô��ڴ�С�Ƿ�������û��Լ����� re size able = resizable �ص���С

		TetrisPanel tetrispanel = new TetrisPanel();// ��ʱ����Ч

		addKeyListener(tetrispanel.timerlistener); // ʵ����JFrame��Body�����ڵİ���������ԭ����������tetrispanel��

		add(tetrispanel); // add �����terispanel��ͼ�εĲ���

	}

	public static void mbutton1() {
		System.exit(0);// ִ��A����
	}

	public void mbutton2() {
		JOptionPane.showMessageDialog(null,"������ת     ���������ƶ�  \n\n            �ո���ͣ");
	}
}
