package ui.controller;

import java.awt.event.MouseEvent;
import java.util.Iterator;

import blimpl.StockBLImpl;
import blservice.StockBLService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import vo.Stock;
import vo.StockVO;

/**
*
*@author:duanzhengmou
*@date:2016年3月22日
*/
public class StockListController{
	@FXML
	TableColumn<Stock, String>code = new TableColumn<Stock,String>();
	@FXML
	TableColumn<Stock, String>name = new TableColumn<Stock,String>();
	@FXML
	TableColumn<Stock, Double>open = new TableColumn<Stock, Double>();
	@FXML
	TableColumn<Stock, Double>high = new TableColumn<Stock, Double>();
	@FXML
	TableColumn<Stock, Double>low = new TableColumn<Stock, Double>();
	@FXML
	TableColumn<Stock, Double>close = new TableColumn<Stock, Double>();
	@FXML
	TableColumn<Stock, Double>turnover = new TableColumn<Stock, Double>();
	@FXML
	TableColumn<Stock, Long>volume = new TableColumn<Stock, Long>();
	@FXML
	TableColumn<Stock, Double>amplitude = new TableColumn<Stock, Double>();
	@FXML
	TableColumn<Stock, Double>changeRate = new TableColumn<Stock, Double>();
	@FXML
	TableView<Stock> tableview = new TableView<Stock>();

	//get the logic service
	StockBLService stockBl = StockBLImpl.getAPIBLService();
	//
	ObservableList<Stock> obsevableList ;
	public StockListController() {

		obsevableList = FXCollections.observableArrayList();
//		tableview.setOnMouseClicked(value);
	}
	
	/**
	 * 
	 */
	@FXML
	public void showTableData(){
		
		Iterator<StockVO>itr = stockBl.getAllStocks();
		while(itr.hasNext()){
			StockVO temp = itr.next();
			Stock dataProperty = new Stock(temp);
			obsevableList.add(dataProperty);
		}

		tableview.setItems(obsevableList);
		name.setCellValueFactory(cell -> cell.getValue().name);
		code.setCellValueFactory(cell -> cell.getValue().code);
		open.setCellValueFactory(cell ->cell.getValue().open.asObject());
		high.setCellValueFactory(cell ->cell.getValue().high.asObject());
		low.setCellValueFactory(cell ->cell.getValue().low.asObject());
		close.setCellValueFactory(cell ->cell.getValue().close.asObject());
		turnover.setCellValueFactory(cell ->cell.getValue().turnover.asObject());
		volume.setCellValueFactory(cell ->cell.getValue().volume.asObject());
		amplitude.setCellValueFactory(cell ->cell.getValue().amplitude.asObject());
		changeRate.setCellValueFactory(cell ->cell.getValue().changeRate.asObject());
		System.out.println("hello world");
		
	}
	
	@FXML
	private void delAllData(){
//		int sum = tableview.getItems().size();
		tableview.getItems().removeAll(obsevableList);
	}
	
	@FXML
	protected void initialize() {
				showTableData();
	}
}
