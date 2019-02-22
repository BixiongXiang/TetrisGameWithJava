package com.teris.self;

import javax.swing.*; //javax.swing.JPanel

import java.awt.*;
import java.awt.event.*;

public class TetrisPanel extends JPanel {

	public TimerListener timerlistener = new TimerListener(); // ������
	

	private int score = 0;
	private int level = 0;
	private int x = 0;
	private int y = 0;
	private int stopStart=0;
	private int[][] map = new int[14][10];  //��Ϊ0  ���ұ�Ϊ1 �ױ�Ϊ2 �̶�����Ϊ3
	
	Timer timer = new Timer(  1000*(int)Math.pow(0.8, (double)level-1), timerlistener);
	/*Timer�Ƿ���������ֱ��new����
	 * ��listen.actionPerformed���� ʹ֮ÿ��1000ms�͵�������һ��
	 * ����ԭ����룺ÿ��һ��ʱ���ʹ��listener��ActionListener�ӿڣ�����ʵ�ֵĺ���
	 * listener������һ����ActionListener�ӿڵĶ��󣬲������ʵ���˽ӿڵķ������ӿھ�����Ч��
	 */


	private int nextBlock = 0;
	private int blockType = 0; //0-6
	private int turnType = 0; //0-3
	// [blockType][turnType][blockY][blockX] 7x4x3x3
	private int[][][][] block = {
				{//L
					{//L-1
						{0,0,0},
						{1,1,1},
						{0,0,0}
					} ,{//L-2
						{0,1,0},
						{0,1,0},
						{0,1,0}
						},{//L-3
							{0,0,0},
							{1,1,1},
							{0,0,0}
						},{//L-4
							{0,1,0},
							{0,1,0},
							{0,1,0}
							}
				},{//S
					{//-1
						{0,1,1},
						{1,1,0},
						{0,0,0}
					},
					{//-2
						{0,1,0},
						{0,1,1},
						{0,0,1}
					},
					{//-3
						{0,1,1},
						{1,1,0},
						{0,0,0}
					},{//-4
						{0,1,0},
						{0,1,1},
						{0,0,1}
					}
					},{//Z
						{//-1
							{1,1,0},
							{0,1,1},
							{0,0,0}
						},
						{//-2
							{0,0,1},
							{0,1,1},
							{0,1,0}
						},
						{//-3
							{1,1,0},
							{0,1,1},
							{0,0,0}
						},{//-4
							{0,0,1},
							{0,1,1},
							{0,1,0}
						}
					},{//J
						{//-1
							{0,1,0},
							{0,1,0},
							{1,1,0}
						},
						{//-2
							{1,0,0},
							{1,1,1},
							{0,0,0}
						},
						{//-3
							{1,1,0},
							{1,0,0},
							{1,0,0}
						},{//-4
							{1,1,1},
							{0,0,1},
							{0,0,0}
						}
					},{//O
						{//-1
							{1,1,0},
							{1,1,0},
							{0,0,0}
						},
						{//-2
							{1,1,0},
							{1,1,0},
							{0,0,0}
						},
						{//-3
							{1,1,0},
							{1,1,0},
							{0,0,0}
						},{//-4
							{1,1,0},
							{1,1,0},
							{0,0,0}
						}
					},{//L
						{//-1
							{1,0,0},
							{1,0,0},
							{1,1,0}
						},
						{//-2
							{1,1,1},
							{1,0,0},
							{0,0,0}
						},
						{//-3
							{1,1,0},
							{0,1,0},
							{0,1,0}
						},{//-4
							{0,0,1},
							{1,1,1},
							{0,0,0}
						}
					},{//T
						{//-1
							{0,1,0},
							{1,1,1},
							{0,0,0}
						},
						{//-2
							{1,0,0},
							{1,1,0},
							{1,0,0}
						},
						{//-3
							{1,1,1},
							{0,1,0},
							{0,0,0}
						},{//-4
							{0,1,0},
							{1,1,0},
							{0,1,0}
						}
					}
			};

	private void left(){
//		JOptionPane.showMessageDialog(null, "left!");
		x -=  crash(x-1,y,blockType,turnType);
		repaint();  //����ִ�л�ͼ����  paintComponent(Graphics g)  ԭ��
	}
	private void right(){
		x +=crash(x+1,y,blockType,turnType);
		repaint();
	}
	
	private void turn(){
		turnType = (crash(x,y+1,blockType,(turnType)%4) +turnType)%4;
		repaint();
	}
	
	private void down(){
		y += crash(x, y+1, blockType,turnType); 
		if (crash(x, y+1, blockType, turnType) == 0) { // ����Ƿ���ײ

			setBlock(x, y, blockType, turnType); // ���������Ϊ�ϰ���

			generateBlock(); // �����µķ��顣

		}
		repaint();
	}
	
	private void setBlock(int x, int y, int blockType, int turnType) {// �ڵ�ͼ�ϱ�ע�Ѿ��̶��ķ���
	// JOptionPane.showMessageDialog(null, "setBlock");
		for (int a = 0; a < 3; a++) {

			for (int b = 0; b < 3; b++) {
					if(block[blockType][turnType][a][b]==1){
						map[y + a][x + b] = 2 * block[blockType][turnType][a][b];
					}
				}
		}
		deleteLine();
		
		repaint();
	}

	private void deleteLine() {
		for (int r = 0; r < 13; r++) {

			if ((map[r][1] + map[r][2] + map[r][3] + map[r][4] + map[r][5]
					+ map[r][6] + map[r][7] + map[r][8]) >= 16) {
				for (int i = r; i > 0; i--) {
					for (int j = 1; j < 9; j++) {
						map[i][j] = map[i - 1][j];
					}
				}
				score += 10;
				if(score%30 == 0){level++;}
			}
		}
//		switch(score){
//		case 50:level++;break;
//		}
		repaint();
	}

	private void generateBlock(){
		x=4;
		y=0;
		blockType =   nextBlock;
		nextBlock = (int)(Math.random()*100)%7;  //0-6
		turnType =0;
		if(crash(x, y+1, blockType, turnType)==0){
			JOptionPane.showMessageDialog(null, "Game Over!");
			System.exit(0);
			}
	}
	
	private int crash(int x,int y,int blockType,int turnType){
				
		for (int a = 0; a < 3; a++) {

			for (int b = 0; b < 3; b++) {

				if ((block[blockType][turnType][a][b] ==1) && (map[y+ a][x + b]) >= 1) { 
					//&Ϊ�� 1&1=1 1&2=0����  1|0=1����

					return 0; // ����Ƿ���ײ�ķ���������ײ�ͷ���0��

				}
				
				if ((block[blockType][turnType][a][b] ==1) && (map[y+ a][x +b]) >= 1) { 


					return 0; // ����Ƿ���ײ�ķ���������ײ�ͷ���0��

				}

			}

		}
		
		return 1;//1-������ײ
	}
	
	private void newGame() {
		score = 0;
		level = 1;
		
		//����ͼ
		for (int r = 0; r < 14; r++) {
			for (int c = 0; c < 10; c++) {
				map[r][c] = 0;
				map[r][0] = map[r][9] = 1;
				map[12][c] = 1;
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) { // �������Զ�ִ�У�
												// Body��addʱ����-��setVisible֮��Ż�
		// JComponent.paintComponent

		super.paintComponent(g); // ˢ�½��棬��������ͼ��

		Color color = new Color(255,0,251);//������ɫ����
		g.setColor(color);
		
		g.drawString("��Level��", 220, 30);
		g.drawString("" + level, 245, 50);
		g.drawString("��Score��", 220, 80);
		g.drawString("" + score+"/"+level*30, 235, 100);

		g.drawString("��Next Block��", 210, 145);
		//����һ������
		for(int r=0;r<3;r++){
			for(int c = 0;c<3;c++){
				if(block[nextBlock][0][r][c] == 1){
					g.fillRect((c +11)*20, (r+8)*20, 20, 20);
				}
			}
		}
		
		// ���߽�շ���
		for (int r = 0; r < 14; r++) {
			for (int c = 0; c < 10; c++) {
				if (map[r][c] == 1) {
					g.drawRect(c * 20, r * 20, 20, 20);
				}
			}
		}
		
		// ���̶��ķ���
		for (int r = 0; r < 14; r++) {
			for (int c = 0; c < 10; c++) {
				if (map[r][c] == 2) {
					g.drawRect(c * 20, r * 20, 20, 20);
					g.fillRect(c * 20, r * 20, 20, 20);
				}
			}
		}

		//���ƶ��ķ���
		for(int r=0;r<3;r++){
			for(int c = 0;c<3;c++){
				if(block[blockType][turnType][r][c] == 1){
					g.fillRect((c +x)*20, (r+y)*20, 20, 20);
				}
			}
		}
	}

	// ���췽��
	public TetrisPanel() {
		newGame();//����ͼ ��ʼ������ֵ
		
		JOptionPane.showMessageDialog(null,"������ת     ���������ƶ�  \n\n            �ո���ͣ");
		
		nextBlock = (int)(Math.random()*100)%7;
		generateBlock();
//		Timer timer = new Timer(  1000*(int)Math.pow(0.8, (double)level-1), timerlistener);
		timer.start();
	}

	// ��ʱ�������Ķ���&����������
	class TimerListener extends KeyAdapter implements ActionListener {

		public void actionPerformed(ActionEvent keyevent) {
//			 �빹�췽���еļ�ʱ�� �󶨣�ÿһ�����������һ��
//			int a=2&2;
//			JOptionPane.showMessageDialog(null, ""+a);
			down();
		}

		@Override
		// ����ʱ�Զ�����KeyAdapter��keyPressed,����Ҫ��дΪ����
		public void keyPressed(KeyEvent keyevent) {// ע�ⷽ������һ��Сд����������������һ����
			// JOptionPane.showMessageDialog(null, "keyaction worked!");

			switch (keyevent.getKeyCode()) {
			case KeyEvent.VK_UP: // ������KeyEvent���У����Ƕ���keyevent�У��ڶ�����Ϊ������
//				JOptionPane.showMessageDialog(null, "up");
				turn();
				break;
			case KeyEvent.VK_DOWN:
//				JOptionPane.showMessageDialog(null, "down");
				down();
				break;
			case KeyEvent.VK_LEFT:
				//JOptionPane.showMessageDialog(null, "left");
				left();
				break;
			case KeyEvent.VK_RIGHT:
//				JOptionPane.showMessageDialog(null, "right");
				right();
				 break;
			case KeyEvent.VK_SPACE:
				if(stopStart==0){
				timer.stop();
				stopStart =1;
				}else{timer.start();stopStart =0;}
				break;
			}
		}
	}
}
