package com.teris.self;

public class FunctionPractice{

	public FunctionPractice(){
		System.out.println("father constructor!");
	}
	
	public void speak(){
		System.out.println("father!");
	}
	
	void classparameter(Me me){
		me.say();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Me me = new Me(); 
	//System.out.println(me);  ��дtoString������ı����
	
		//classparameter(me);
		
	new Me().speak();  //��ִ��Me�Ĺ��췽�������ø��๹������������Լ��Ĺ��췽�������ٵ���speak����
	
	int[][] map = new int[3][5];
	System.out.println(map);
//	�������Ϊ���� ���಻��ʵ������
	
	}

}

class Me extends FunctionPractice {
	int age = 0;

	public Me(){
		speak();  //����ֱ�ӵ��ø���ķ���
		System.out.println("My constructor!");
	}
	
	void say(){
		System.out.println("say");
	}
	
	@Override
	public String toString() {
		return "Me [age=" + age + "]";
	}

	
}