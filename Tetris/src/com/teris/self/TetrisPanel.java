package com.teris.self;

import javax.swing.*; //javax.swing.JPanel

import java.awt.*;
import java.awt.event.*;

public class TetrisPanel extends JPanel {

	public TimerListener timerlistener = new TimerListener(); // 监听器
	

	private int score = 0;
	private int level = 0;
	private int x = 0;
	private int y = 0;
	private int stopStart=0;
	private int[][] map = new int[14][10];  //空为0  左右壁为1 底边为2 固定方块为3
	
	Timer timer = new Timer(  1000*(int)Math.pow(0.8, (double)level-1), timerlistener);
	/*Timer是方法，可以直接new方法
	 * 绑定listen.actionPerformed方法 使之每隔1000ms就调用运行一次
	 * 工作原理猜想：每到一个时间就使用listener的ActionListener接口，接入实现的函数
	 * listener必须是一个有ActionListener接口的对象，并且如果实现了接口的方法，接口就有了效果
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
		repaint();  //重新执行绘图函数  paintComponent(Graphics g)  原理？
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
		if (crash(x, y+1, blockType, turnType) == 0) { // 检测是否碰撞

			setBlock(x, y, blockType, turnType); // 将方块添加为障碍物

			generateBlock(); // 生成新的方块。

		}
		repaint();
	}
	
	private void setBlock(int x, int y, int blockType, int turnType) {// 在地图上标注已经固定的方块
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
					//&为交 1&1=1 1&2=0……  1|0=1……

					return 0; // 检测是否碰撞的方法。【碰撞就返回0】

				}
				
				if ((block[blockType][turnType][a][b] ==1) && (map[y+ a][x +b]) >= 1) { 


					return 0; // 检测是否碰撞的方法。【碰撞就返回0】

				}

			}

		}
		
		return 1;//1-不会碰撞
	}
	
	private void newGame() {
		score = 0;
		level = 1;
		
		//画地图
		for (int r = 0; r < 14; r++) {
			for (int c = 0; c < 10; c++) {
				map[r][c] = 0;
				map[r][0] = map[r][9] = 1;
				map[12][c] = 1;
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) { // 本函数自动执行？
												// Body中add时调用-否，setVisible之后才画
		// JComponent.paintComponent

		super.paintComponent(g); // 刷新界面，消除残留图像

		Color color = new Color(255,0,251);//创建颜色对象
		g.setColor(color);
		
		g.drawString("【Level】", 220, 30);
		g.drawString("" + level, 245, 50);
		g.drawString("【Score】", 220, 80);
		g.drawString("" + score+"/"+level*30, 235, 100);

		g.drawString("【Next Block】", 210, 145);
		//画下一个方块
		for(int r=0;r<3;r++){
			for(int c = 0;c<3;c++){
				if(block[nextBlock][0][r][c] == 1){
					g.fillRect((c +11)*20, (r+8)*20, 20, 20);
				}
			}
		}
		
		// 画边界空方块
		for (int r = 0; r < 14; r++) {
			for (int c = 0; c < 10; c++) {
				if (map[r][c] == 1) {
					g.drawRect(c * 20, r * 20, 20, 20);
				}
			}
		}
		
		// 画固定的方块
		for (int r = 0; r < 14; r++) {
			for (int c = 0; c < 10; c++) {
				if (map[r][c] == 2) {
					g.drawRect(c * 20, r * 20, 20, 20);
					g.fillRect(c * 20, r * 20, 20, 20);
				}
			}
		}

		//画移动的方块
		for(int r=0;r<3;r++){
			for(int c = 0;c<3;c++){
				if(block[blockType][turnType][r][c] == 1){
					g.fillRect((c +x)*20, (r+y)*20, 20, 20);
				}
			}
		}
	}

	// 构造方法
	public TetrisPanel() {
		newGame();//画地图 初始化分数值
		
		JOptionPane.showMessageDialog(null,"↑：翻转     ←↓→：移动  \n\n            空格：暂停");
		
		nextBlock = (int)(Math.random()*100)%7;
		generateBlock();
//		Timer timer = new Timer(  1000*(int)Math.pow(0.8, (double)level-1), timerlistener);
		timer.start();
	}

	// 计时器关联的动作&按键监听器
	class TimerListener extends KeyAdapter implements ActionListener {

		public void actionPerformed(ActionEvent keyevent) {
//			 与构造方法中的计时器 绑定，每一定间隔就运行一次
//			int a=2&2;
//			JOptionPane.showMessageDialog(null, ""+a);
			down();
		}

		@Override
		// 按键时自动调用KeyAdapter的keyPressed,所以要重写为己用
		public void keyPressed(KeyEvent keyevent) {// 注意方法名第一个小写！！【函数名必须一样】
			// JOptionPane.showMessageDialog(null, "keyaction worked!");

			switch (keyevent.getKeyCode()) {
			case KeyEvent.VK_UP: // 常量在KeyEvent类中，而非对象keyevent中（在对象中为变量）
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
