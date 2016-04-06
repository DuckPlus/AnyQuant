package ui.controller;

import blimpl.OptionalStockBLServiceImpl;
import blservice.OptionalStockBLService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import vo.Stock;

public class StockDetailController {

	@FXML
	Label nameLabel;
	@FXML
	Label codeLabel;
	@FXML
	Label open;
	@FXML
	Label close;
	@FXML
	Label high;
	@FXML
	Label low;
	@FXML
	Label turnover;
	@FXML
	Label volume;
	@FXML
	Label pe;
	@FXML
	Label pb;
	@FXML
	Button addBtn;
	private Stock currentStock;
	OptionalStockBLService optionBl = OptionalStockBLServiceImpl.getOptionalBLService();

	private static StockDetailController instance;

	public StockDetailController() {
		System.err.println("init constructor");
		if (instance == null) {
			instance = this;
		}
	}

	public static StockDetailController getStockDetailController() {
		System.err.println("get instance");
		if (instance == null) {
			System.err.println("create");
			instance = new StockDetailController();
		}
		return instance;
	}
	@FXML
	private void initialize(){
		//TODO
//		System.out.println("init complete");
//		if(nameLabel==null){
//			System.out.println("name null in init");
//			return;
//			}else{
//				System.out.println("not null in init");
//			}
//		nameLabel.setText("hello");

	}

	public static StockDetailController getCurrent(){
		return instance;
	}


	public void setData(Stock stock){
//		if(nameLabel==null){
//			System.out.println("name null in set method");
//			return;
//			}else{
//				System.out.println("not null in set method");
//				}
		currentStock = stock;
		System.out.println("changed!!");
//		System.out.println(stock.name.get());
		nameLabel.setText(stock.name.get());
		codeLabel.setText(stock.code.get());
		open.setText(String.valueOf(stock.open.get()));
		close.setText(String.valueOf(stock.close.get()));
		high.setText(String.valueOf(stock.high.get()));
		low.setText(String.valueOf(stock.low.get()));
		turnover.setText(String.valueOf(stock.turnoverRate.get()));
		pe.setText(String.valueOf(stock.pe.get()));
		pb.setText(String.valueOf(stock.pb.get()));
		volume.setText(String.valueOf(stock.turnoverVol.get()));
		addBtn.setText("加入自选股");
	}
	@FXML
	private void addOptionalStock(){
		boolean added=optionBl.addStockCode(currentStock.code.get());
		if(!added){
			addBtn.setText("已存在自选股");
		}else{
			addBtn.setText("加入成功");
		}
	}
}
