package ui.controller;

import blimpl.StockBLImpl;
import blimpl.OptionalStockBLImpl;
import blservice.OptionalStockBLService;
import blservice.StockBLService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import ui.controller.candleStick.TimeSharingChart;
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
	@FXML
	Tab k_day;
	@FXML
	Tab k_month;
	@FXML
	Tab k_week;
	@FXML
	Tab timeSharing;
	@FXML
	TabPane tabPane;
	private Boolean exist;
	private String stockCode;

	private Stock currentStock;
//<<<<<<< HEAD
//	OptionalStockBLService optionBl = OptionalStockBLServiceImpl.getOptionalBLService();
//	StockBLService stockBl = StockBLImpl.getAPIBLService();
//=======
	OptionalStockBLService optionBl = OptionalStockBLImpl.getOptionalBLService();

//>>>>>>> 7115410b39c4a27d876bae4a7300b23b99469b4b
	private static StockDetailController instance;

	public StockDetailController() {
//		System.err.println("init constructor======");
		if (instance == null) {
//			System.out.println("【null instance】");
			instance = this;
		}
	}

	public static StockDetailController getStockDetailController() {
//		System.err.println("get instance");
		if (instance == null) {
//			System.err.println("create");
			instance = new StockDetailController();
		}
		return instance;
	}
	@FXML
	private void initialize(){
		
		
		
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
		System.out.println("[get in and current stock :"+stock.code+"]");
		currentStock = stock;
		System.out.println("changed!!");
		stockCode=stock.code.get();
		nameLabel.setText(stock.name.get());
		codeLabel.setText(stockCode);
		open.setText(String.valueOf(stock.open.get()));
		close.setText(String.valueOf(stock.close.get()));
		high.setText(String.valueOf(stock.high.get()));
		low.setText(String.valueOf(stock.low.get()));
		turnover.setText(String.valueOf(stock.turnoverRate.get()));
		pe.setText(String.valueOf(stock.pe.get()));
		pb.setText(String.valueOf(stock.pb.get()));
		volume.setText(String.valueOf(stock.turnoverVol.get()));
		if(optionBl.ifStockExist(stockCode)){//存在于自选股
			addBtn.setText("删除该自选股");
			exist=true;
		}
		else{
			addBtn.setText("加入自选股");
			exist=false;
		}
		//add time sharing then
		initTimeSharing();
	}
	private void initTimeSharing(){
		TimeSharingChart timeChart = new TimeSharingChart(currentStock);
		timeSharing.setContent(timeChart.getTimeSharingChart());
	}
	@FXML
	private void addOptionalStock(){
		if(currentStock==null)System.err.println("current null");
		if(exist){//执行删除操作
			if(optionBl.deleteStockCode(stockCode)){//删除成功
				addBtn.setText("加入自选股");
				exist=false;
			}else{
				addBtn.setText("删除失败");//TODO   失败原因？。。
			}
		}else{//执行增加操作
			System.out.println("add begin");
			if(optionBl.addStockCode(stockCode)){//添加成功
				addBtn.setText("删除该自选股");
				exist=true;
			}else{
				addBtn.setText("加入失败");
			}
		}


	}
}
