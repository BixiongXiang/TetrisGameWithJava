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
	//System.out.println(me);  重写toString方法会改变输出
	
		//classparameter(me);
		
	new Me().speak();  //先执行Me的构造方法（调用父类构造器，再完成自己的构造方法），再调用speak方法
	
	int[][] map = new int[3][5];
	System.out.println(map);
//	以类变量为参数 则类不用实例化？
	
	}

}

class Me extends FunctionPractice {
	int age = 0;

	public Me(){
		speak();  //可以直接调用父类的方法
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