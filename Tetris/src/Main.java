import java.awt.*;

import java.awt.event.*;

import javax.swing.*;

// 创建一个俄罗斯方块类

public class Main extends JFrame {

	public Main() {

		TetrisPanel a = new TetrisPanel();

		addKeyListener(a.listener); // 添加监听事件 父类中的方法 ？a中有监听器为何要再add
		// 只有在顶层容器中添加事件监听器才能起作用？？

		add(a); // ？能添加什么、如何添加？
		/*
		 * add()以JFrame为容器添加a的内容，a的类的父类为JPanel，相当于一个子容器 执行a中初始化等的画图操作，绘制方块，开始计时
		 */

		/*
		 * 设置窗口菜单 有以下对象【空菜单栏】【子菜单栏】-对应选项
		 */

		JMenu game = new JMenu("游戏"); // 设置菜单栏的一个选项为【游戏】
		game.add("退出"); // 添加一个子菜单项 game 【退出】

		JMenuBar menu = new JMenuBar(); // 添加一个【菜单栏】menu（空的）

		menu.add(game); // 将做好的【菜单选项】【添加】到空的【菜单栏】上

		this.setJMenuBar(menu); // 将做好的【菜单栏】【添加】到【窗口容器】中来

		setLocationRelativeTo(null); // 设置窗口初始位置
		/*
		 * public void setLocationRelativeTo(Component c) c = null placed in the
		 * [center] of the screen
		 */

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置默认窗口关闭方式

		setSize(220, 275); // 设置窗体大小

		setTitle("XBX 俄罗斯方块"); // 设置窗体标题

		setResizable(false); // 设置窗口大小是否可以让用户自己调整 re size able = resizable 重调大小

	}

	public static void main(String[] args) {

		new Main().setVisible(true); // 显示面板可见。

	}

}

class TetrisPanel extends JPanel {

	public TimerListener listener = new TimerListener();
	// 【自定义类】

	// blockType 代表方块类型

	// turnState代表方块状态

	private int blockType; // 数组中代表方块的样式

	private int score = 0; // 初始化分数

	private int turnState; // 方块旋转的状态

	private int x; // 画布横坐标

	private int y; // 画布纵坐标

	int flag = 0; // ?

	// 定义已经放下的方块x=0-11,y=0-21;

	int[][] map = new int[13][23]; // 创建一个画布 ?

	// 一个16X16三维数组，代表一个形状的四种状态。

	/*
	 * 方块的形状【 第一组】代表方块类型有S、Z、L、J、I、O、T 7种 
	 * 【 第二组】 代表旋转几次 
	 * 【第三、四组】为 方块矩阵
	 */
	private final int shapes[][][] = new int[][][] {

			// I 4x16

			{
			{ 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },

			{ 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 },

			{ 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },

			{ 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 } },

			// S

			{ { 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },

			{ 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },

			{ 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },

			{ 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } },

			// Z

			{ { 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },

			{ 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },

			{ 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },

			{ 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 } },

			// J

			{ { 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },

			{ 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },

			{ 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },

			{ 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },

			// O

			{ { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },

			{ 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },

			{ 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },

			{ 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },

			// L

			{ { 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },

			{ 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },

			{ 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },

			{ 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },

			// T

			{ { 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },

			{ 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },

			{ 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },

			{ 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 } } };

	// 初始化构造方法
	public TetrisPanel() {

		nextBlock(); // 开始制造一个新的方块。

		newGame(); // 开始一个新游戏 初始化分数地图

		new Timer(1000, listener).start(); // 【标准类】用定时器设置每次下落的时间，1000=1秒。
		// 与actionPerformed绑定，执行其下的代码

	}

	// 生成新方块的方法

	private void nextBlock() {

		blockType = (int) (Math.random() * 1000) % 7; // 生成一个0-6的随机数。7种

		turnState = (int) (Math.random() * 1000) % 4; // 生成一个0-3的随机数。4种

		x = 4;

		y = 0; // 设置方块出现的位置，每一个元素代表一个方格。4为中间。

		if (crash(x, y, blockType, turnState) == 0) {

			JOptionPane.showMessageDialog(null, "GAME OVER");

		}

		// 判断一开始下落就发生碰撞了，一碰撞就代表到顶了。游戏结束。

	}

	// 初始化地图

	private void newGame() {

		score = 0;

		for (int i = 0; i < 12; i++) {

			for (int j = 0; j < 22; j++) {

				map[i][j] = 0;   //i列 j行  【0不画】

				map[11][j] = map[0][j] = 3;  //边框的空方格 【3要画】

			}

			map[i][21] = 3;  //底部的空方格

		}

	}

	// 设置左边，右边，下边的值为3其他的为0。

	// 旋转的方法

	private void turn() {

		turnState = (crash(x, y, blockType, (turnState + 1) % 4) + turnState) % 4;

		repaint(); // 从新绘制画布。不然的话会产生拖影。

	}

	// 左移的方法

	private void left() {

		x -= crash(x - 1, y, blockType, turnState); // 将X坐标减少。X坐标为0-12

		repaint(); // 从新绘制画布。不然的话会产生拖影。

	}

	// 右移的方法

	private void right() { // 将X坐标增加。X坐标为0-12

		x += crash(x + 1, y, blockType, turnState);

		repaint(); // 从新绘制画布。不然的话会产生拖影。

	}

	// 下落的方法

	private void down() {

		y += crash(x, y + 1, blockType, turnState); // 将Y坐标增加。Y坐标为0-22

		if (crash(x, y + 1, blockType, turnState) == 0) { // 检测是否碰撞

			add(x, y, blockType, turnState); // 将方块添加为障碍物

			nextBlock(); // 生成新的方块。

		}

		repaint(); //重画/刷新 此元素ASAP. （自动找画画方法） 这样方块移动才能看见  即方块坐标刷新后需要重画

	}

	// 是否碰撞的方法

	private int crash(int x, int y, int blockType, int turnState) {

		for (int a = 0; a < 4; a++) {

			for (int b = 0; b < 4; b++) {

				if ((shapes[blockType][turnState][a * 4 + b] & map[x + b + 1][y

				+ a]) == 1) {  //&为交 1&1=1 1&2=0……  1|0=1……

					return 0; // 检测是否碰撞的方法。【碰撞就返回0】

				}

			}

		}

		return 1;

	}

	// 尝试消行的方法

	private void tryDelLine() { // 从上往下判断消除每一行，并且从上往下转移方块的坐标内容。

		for (int b = 0; b < 21; b++) {

			int c = 1;

			for (int a = 0; a < 12; a++) {

				c &= map[a][b]; // 判断是否满一行都是方块。

			}

			if (c == 1) {

				score += 10;

				for (int d = b; d > 0; d--) {

					for (int e = 0; e < 11; e++) {

						map[e][d] = map[e][d - 1]; // 上下置换

					}

				}

			}

		}

	}

	// 把当前添加map

	private void add(int x, int y, int blockType, int turnState) {

		for (int a = 0; a < 4; a++) {

			for (int b = 0; b < 4; b++) {

				map[x + b + 1][y + a] |= shapes[blockType][turnState][a * 4 + b];  //将形状填充到map上，再画出来

			} // 交集判断是否填充。

		}

		tryDelLine();// 判断是否可以消除一行。

	}

	// 【重写】画方块的的方法（玩家操控的方块） 【JComponent 类中有此方法】？是父类？

	public void paintComponent(Graphics g) { // g为Graphics类的对象 用于画图 参数列表 无需实例化，
		// 调用时需输入实例化的g

		super.paintComponent(g); // 用于消除运动方块之前的轨迹（消除残影）

		// 画当前【移动的方块】，功能不包含清除之前帧的方块

		for (int j = 0; j < 16; j++) {

			if (shapes[blockType][turnState][j] == 1) {

				g.fillRect((j % 4 + x + 1) * 10, (j / 4 + y) * 10, 10, 10);

			} // 填充方块 x y 为起始点，width height 为填充的长宽

		}

		// 画【边框】+【已经固定的方块】

		for (int j = 0; j < 22; j++) {

			for (int i = 0; i < 12; i++) {

				if (map[i][j] == 1) {

					g.fillRect(i * 10, j * 10, 10, 10);// 填充方块

				} else if (map[i][j] == 3) {

					g.drawRect(i * 10, j * 10, 10, 10); // 绘制方块

				}

			}

		}

		g.drawString("score=" + score, 125, 10); // 重绘分数
		/* （a,b,c）a内容 b横向坐标 c纵向坐标 */

	}

	// 定时器监听和键盘监听

	class TimerListener extends KeyAdapter implements ActionListener {

		/*
		 * 【抽象类】与【接口】都在 java.awt.event中 接口本质为只有抽象方法与属性的抽象类 (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */

		public void actionPerformed(ActionEvent e) { // 何时被调用的？按键时？
			// 参数列表：标准类 java.awt.event.ActionEvent
			// 实现了ActionListener接口中的actionPerformed 方法 按键时自动生成e并传到每个用到e的地方
			down();
		}

		public void keyPressed(KeyEvent ei) { // 判断按键，是按了哪个键。
												// 按键时自动生成e并传到每个用到e的地方
			/*
			 * 标准类java.awt.event.KeyEvent 类中包含了各种按键的属性值，用 【类名】. 访问
			 * 对象e可以接收键盘传过来的值，用getKeyCode()返回键值
			 */

			switch (ei.getKeyCode()) {

			case KeyEvent.VK_DOWN:

				down();

				break;

			case KeyEvent.VK_UP:

				turn();

				break;

			case KeyEvent.VK_RIGHT:

				right();

				break;

			case KeyEvent.VK_LEFT:

				left();

			}

		}

	}

}