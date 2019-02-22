package com.teris.self;

public class Initial {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Body().setVisible(true);            
	}

}
/*先构造Body，再setVisible。
 * Body为JFrame 子类，相当于一个不可见的面板容器，在Body类中填充属性。
 * 最后设置set Visible使整个窗口可见*/

/*遗留问题:
 * 1、程序如何获得键盘输入的按键
 * 2、各个组件（component）的设置方法
 */