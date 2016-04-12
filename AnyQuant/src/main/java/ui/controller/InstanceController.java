package ui.controller;

import javafx.scene.layout.BorderPane;

public class InstanceController {
	private BorderPane stockDetailPane;
	private optionalStockController optionalStockController;
	
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
	
	/**
	 * register
	 * @param optionalStockController
	 */
	public void registOptionalStockController(optionalStockController opc){
		if(optionalStockController==null){
			optionalStockController = opc;
			System.out.println("[regist optionalStockController] AS "+optionalStockController.toString());
		}else{
			System.err.println("=====  optionalStockController EXIST already  !!!=====");
		}
	}
	
	public optionalStockController getOptionalStockController(){
		if(optionalStockController==null)System.out.println("Attention that the optionalStockController has NULL value");
		return optionalStockController;
	}
}




