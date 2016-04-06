package ui.controller;

public class TimeShareThread extends Thread{
	String something = "nothing";
	public TimeShareThread(String s) {
		something = s;
	}
	
	@Override
	public void run(){
		System.out.println(something);
		super.run();
	}
}
