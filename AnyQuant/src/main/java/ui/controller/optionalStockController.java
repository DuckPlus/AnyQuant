package ui.controller;

import java.util.Iterator;

import blimpl.OptionalStockBLServiceImpl;
import blservice.OptionalStockBLService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import vo.Stock;
import vo.StockVO;

public class optionalStockController {
	
	@FXML
	TableColumn<Stock, String>code;// = new TableColumn<Stock,String>();
	@FXML
	TableColumn<Stock, String>name;// = new TableColumn<Stock,String>();
	@FXML
	TableColumn<Stock, Double>open;// = new TableColumn<Stock, Double>();
	@FXML
	TableColumn<Stock, Double>high;// = new TableColumn<Stock, Double>();
	@FXML
	TableColumn<Stock, Double>low;// = new TableColumn<Stock, Double>();
	@FXML
	TableColumn<Stock, Double>close;// = new TableColumn<Stock, Double>();
	@FXML
	TableColumn<Stock, Double>turnover;// = new TableColumn<Stock, Double>();
	@FXML
	TableColumn<Stock, Long>volume;// = new TableColumn<Stock, Long>();
	@FXML
	TableColumn<Stock, Double>amplitude;// = new TableColumn<Stock, Double>();
	@FXML
	TableColumn<Stock, Double>changeRate;// = new TableColumn<Stock, Double>();
	@FXML
	TableView<Stock> tableview;// = new TableView<Stock>();
	@FXML
	TextField searchBar;
	ObservableList<Stock> observableList;
	
	OptionalStockBLService optinalBl = OptionalStockBLServiceImpl.getOptionalBLService();
	
	public optionalStockController() {
	
	}
	
	
	@FXML
	private void initialize(){
		observableList = FXCollections.observableArrayList();
		if(name!=null){
			System.out.println("not null col");
		getOptionalStock();
		}
		
	}
	@FXML
	private void getOptionalStock(){
		Iterator<StockVO>itr = optinalBl.getOptionalStocks();
		showTableData(itr);
	}
	private void showTableData(Iterator<StockVO>itr){
		tableview.getItems().removeAll(observableList);
		while(itr.hasNext()){
			StockVO temp = itr.next();
			System.out.println(temp.name);
			Stock dataProperty = new Stock(temp);
			observableList.add(dataProperty);
		}
		tableview.setItems(observableList);
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
	}
	
}
