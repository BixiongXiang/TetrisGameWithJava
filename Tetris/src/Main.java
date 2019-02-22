import java.awt.*;

import java.awt.event.*;

import javax.swing.*;

// ����һ������˹������

public class Main extends JFrame {

	public Main() {

		TetrisPanel a = new TetrisPanel();

		addKeyListener(a.listener); // ��Ӽ����¼� �����еķ��� ��a���м�����Ϊ��Ҫ��add
		// ֻ���ڶ�������������¼����������������ã���

		add(a); // �������ʲô�������ӣ�
		/*
		 * add()��JFrameΪ�������a�����ݣ�a����ĸ���ΪJPanel���൱��һ�������� ִ��a�г�ʼ���ȵĻ�ͼ���������Ʒ��飬��ʼ��ʱ
		 */

		/*
		 * ���ô��ڲ˵� �����¶��󡾿ղ˵��������Ӳ˵�����-��Ӧѡ��
		 */

		JMenu game = new JMenu("��Ϸ"); // ���ò˵�����һ��ѡ��Ϊ����Ϸ��
		game.add("�˳�"); // ���һ���Ӳ˵��� game ���˳���

		JMenuBar menu = new JMenuBar(); // ���һ�����˵�����menu���յģ�

		menu.add(game); // �����õġ��˵�ѡ�����ӡ����յġ��˵�������

		this.setJMenuBar(menu); // �����õġ��˵���������ӡ�������������������

		setLocationRelativeTo(null); // ���ô��ڳ�ʼλ��
		/*
		 * public void setLocationRelativeTo(Component c) c = null placed in the
		 * [center] of the screen
		 */

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ����Ĭ�ϴ��ڹرշ�ʽ

		setSize(220, 275); // ���ô����С

		setTitle("XBX ����˹����"); // ���ô������

		setResizable(false); // ���ô��ڴ�С�Ƿ�������û��Լ����� re size able = resizable �ص���С

	}

	public static void main(String[] args) {

		new Main().setVisible(true); // ��ʾ���ɼ���

	}

}

class TetrisPanel extends JPanel {

	public TimerListener listener = new TimerListener();
	// ���Զ����ࡿ

	// blockType ����������

	// turnState������״̬

	private int blockType; // �����д��������ʽ

	private int score = 0; // ��ʼ������

	private int turnState; // ������ת��״̬

	private int x; // ����������

	private int y; // ����������

	int flag = 0; // ?

	// �����Ѿ����µķ���x=0-11,y=0-21;

	int[][] map = new int[13][23]; // ����һ������ ?

	// һ��16X16��ά���飬����һ����״������״̬��

	/*
	 * �������״�� ��һ�顿������������S��Z��L��J��I��O��T 7�� 
	 * �� �ڶ��顿 ������ת���� 
	 * �����������顿Ϊ �������
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

	// ��ʼ�����췽��
	public TetrisPanel() {

		nextBlock(); // ��ʼ����һ���µķ��顣

		newGame(); // ��ʼһ������Ϸ ��ʼ��������ͼ

		new Timer(1000, listener).start(); // ����׼�ࡿ�ö�ʱ������ÿ�������ʱ�䣬1000=1�롣
		// ��actionPerformed�󶨣�ִ�����µĴ���

	}

	// �����·���ķ���

	private void nextBlock() {

		blockType = (int) (Math.random() * 1000) % 7; // ����һ��0-6���������7��

		turnState = (int) (Math.random() * 1000) % 4; // ����һ��0-3���������4��

		x = 4;

		y = 0; // ���÷�����ֵ�λ�ã�ÿһ��Ԫ�ش���һ������4Ϊ�м䡣

		if (crash(x, y, blockType, turnState) == 0) {

			JOptionPane.showMessageDialog(null, "GAME OVER");

		}

		// �ж�һ��ʼ����ͷ�����ײ�ˣ�һ��ײ�ʹ������ˡ���Ϸ������

	}

	// ��ʼ����ͼ

	private void newGame() {

		score = 0;

		for (int i = 0; i < 12; i++) {

			for (int j = 0; j < 22; j++) {

				map[i][j] = 0;   //i�� j��  ��0������

				map[11][j] = map[0][j] = 3;  //�߿�Ŀշ��� ��3Ҫ����

			}

			map[i][21] = 3;  //�ײ��Ŀշ���

		}

	}

	// ������ߣ��ұߣ��±ߵ�ֵΪ3������Ϊ0��

	// ��ת�ķ���

	private void turn() {

		turnState = (crash(x, y, blockType, (turnState + 1) % 4) + turnState) % 4;

		repaint(); // ���»��ƻ�������Ȼ�Ļ��������Ӱ��

	}

	// ���Ƶķ���

	private void left() {

		x -= crash(x - 1, y, blockType, turnState); // ��X������١�X����Ϊ0-12

		repaint(); // ���»��ƻ�������Ȼ�Ļ��������Ӱ��

	}

	// ���Ƶķ���

	private void right() { // ��X�������ӡ�X����Ϊ0-12

		x += crash(x + 1, y, blockType, turnState);

		repaint(); // ���»��ƻ�������Ȼ�Ļ��������Ӱ��

	}

	// ����ķ���

	private void down() {

		y += crash(x, y + 1, blockType, turnState); // ��Y�������ӡ�Y����Ϊ0-22

		if (crash(x, y + 1, blockType, turnState) == 0) { // ����Ƿ���ײ

			add(x, y, blockType, turnState); // ���������Ϊ�ϰ���

			nextBlock(); // �����µķ��顣

		}

		repaint(); //�ػ�/ˢ�� ��Ԫ��ASAP. ���Զ��һ��������� ���������ƶ����ܿ���  ����������ˢ�º���Ҫ�ػ�

	}

	// �Ƿ���ײ�ķ���

	private int crash(int x, int y, int blockType, int turnState) {

		for (int a = 0; a < 4; a++) {

			for (int b = 0; b < 4; b++) {

				if ((shapes[blockType][turnState][a * 4 + b] & map[x + b + 1][y

				+ a]) == 1) {  //&Ϊ�� 1&1=1 1&2=0����  1|0=1����

					return 0; // ����Ƿ���ײ�ķ���������ײ�ͷ���0��

				}

			}

		}

		return 1;

	}

	// �������еķ���

	private void tryDelLine() { // ���������ж�����ÿһ�У����Ҵ�������ת�Ʒ�����������ݡ�

		for (int b = 0; b < 21; b++) {

			int c = 1;

			for (int a = 0; a < 12; a++) {

				c &= map[a][b]; // �ж��Ƿ���һ�ж��Ƿ��顣

			}

			if (c == 1) {

				score += 10;

				for (int d = b; d > 0; d--) {

					for (int e = 0; e < 11; e++) {

						map[e][d] = map[e][d - 1]; // �����û�

					}

				}

			}

		}

	}

	// �ѵ�ǰ���map

	private void add(int x, int y, int blockType, int turnState) {

		for (int a = 0; a < 4; a++) {

			for (int b = 0; b < 4; b++) {

				map[x + b + 1][y + a] |= shapes[blockType][turnState][a * 4 + b];  //����״��䵽map�ϣ��ٻ�����

			} // �����ж��Ƿ���䡣

		}

		tryDelLine();// �ж��Ƿ��������һ�С�

	}

	// ����д��������ĵķ�������Ҳٿصķ��飩 ��JComponent �����д˷��������Ǹ��ࣿ

	public void paintComponent(Graphics g) { // gΪGraphics��Ķ��� ���ڻ�ͼ �����б� ����ʵ������
		// ����ʱ������ʵ������g

		super.paintComponent(g); // ���������˶�����֮ǰ�Ĺ켣��������Ӱ��

		// ����ǰ���ƶ��ķ��顿�����ܲ��������֮ǰ֡�ķ���

		for (int j = 0; j < 16; j++) {

			if (shapes[blockType][turnState][j] == 1) {

				g.fillRect((j % 4 + x + 1) * 10, (j / 4 + y) * 10, 10, 10);

			} // ��䷽�� x y Ϊ��ʼ�㣬width height Ϊ���ĳ���

		}

		// �����߿�+���Ѿ��̶��ķ��顿

		for (int j = 0; j < 22; j++) {

			for (int i = 0; i < 12; i++) {

				if (map[i][j] == 1) {

					g.fillRect(i * 10, j * 10, 10, 10);// ��䷽��

				} else if (map[i][j] == 3) {

					g.drawRect(i * 10, j * 10, 10, 10); // ���Ʒ���

				}

			}

		}

		g.drawString("score=" + score, 125, 10); // �ػ����
		/* ��a,b,c��a���� b�������� c�������� */

	}

	// ��ʱ�������ͼ��̼���

	class TimerListener extends KeyAdapter implements ActionListener {

		/*
		 * �������ࡿ�롾�ӿڡ����� java.awt.event�� �ӿڱ���Ϊֻ�г��󷽷������Եĳ����� (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */

		public void actionPerformed(ActionEvent e) { // ��ʱ�����õģ�����ʱ��
			// �����б���׼�� java.awt.event.ActionEvent
			// ʵ����ActionListener�ӿ��е�actionPerformed ���� ����ʱ�Զ�����e������ÿ���õ�e�ĵط�
			down();
		}

		public void keyPressed(KeyEvent ei) { // �жϰ������ǰ����ĸ�����
												// ����ʱ�Զ�����e������ÿ���õ�e�ĵط�
			/*
			 * ��׼��java.awt.event.KeyEvent ���а����˸��ְ���������ֵ���� ��������. ����
			 * ����e���Խ��ռ��̴�������ֵ����getKeyCode()���ؼ�ֵ
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