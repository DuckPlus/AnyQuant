package ui.controller;

public class TimeShareThread extends Thread{
	StockDetailController stockDetailController;
	public TimeShareThread(StockDetailController sdc) {
		stockDetailController = sdc;
	}
	
	@Override
	public void run(){
		super.run();
		while(true){
//	    stockDetailController.upDateTimeSharing();
	    System.out.println("refresh time sharing");
	    	try {
				sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
