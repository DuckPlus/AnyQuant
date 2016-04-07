package ui.controller;

import javafx.scene.layout.BorderPane;

public class InstanceController {
	private BorderPane stockDetailPane;
	
	private static InstanceController instance;
	private InstanceController() {
		
	}
	public static InstanceController getInstance(){
		if(instance==null){
			instance = new InstanceController();
		}
			return instance;
		
		
	}
	
	/**
	 * register
	 * @param stockDetailPane
	 */
	public void registStockDetailPane(BorderPane s){
		System.out.println("regist seccess");
		if(stockDetailPane==null){
		stockDetailPane = s;
		}else{
			System.err.println("=====  stockDetailPane EXIST already  !!!=====");
		}
	}
	/**
	 * getter
	 * @return stockDetailPane
	 */
	public BorderPane getStockDetailPane(){
		if(stockDetailPane==null)System.out.println("Attention that the stockdetailPane has null value");
		return stockDetailPane;
	}
}




