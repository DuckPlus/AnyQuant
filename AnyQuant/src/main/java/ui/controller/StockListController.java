package ui.controller;

import java.util.Iterator;

import blimpl.StockBLImpl;
import blservice.StockBLService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import vo.Stock;
import vo.StockVO;

/**
*
*@author:duanzhengmou
*@date:2016年3月18日
*/
public class StockListController {
	@FXML
	private TableView<Stock> tableview;
	@FXML
	private TableColumn<Stock, String> sname;
	
	@FXML
	private TableColumn<Stock, String> scode;
	
	@FXML
	private TableColumn<Stock, Double> sopen;
	
	@FXML
	private TableColumn<Stock, Double> sclose;
	
	
	@FXML
	private TableColumn<Stock, Double> shigh;
	
	@FXML
	private TableColumn<Stock, Double> slow;
	
	@FXML
	private TableColumn<Stock, Long> svolume;
	public StockListController() {
		
		StockBLService stockbl = StockBLImpl.getAPIBLService();
		Iterator<StockVO> itr = stockbl.getAllStocks();
		ObservableList<Stock> ol = FXCollections.observableArrayList();
//		while(itr.hasNext()){
//			StockVO temp = itr.next();
//			ol.add(new Stock(temp));
//		}
		Stock s = new Stock(itr.next());
		System.out.println(s);
//		tableview.setItems(ol);
		System.out.println("done hello");
//		sname.setCellValueFactory(new PropertyValueFactory<Stock,String>("name"));
//		scode.setCellValueFactory(new PropertyValueFactory<Stock,String>("code"));
//		sopen.setCellValueFactory(new PropertyValueFactory<Stock,String>("open"));
//		sclose.setCellValueFactory(new PropertyValueFactory<Stock,String>("close"));
//		shigh.setCellValueFactory(new PropertyValueFactory<Stock,String>("high"));
//		slow.setCellValueFactory(new PropertyValueFactory<Stock,String>("low"));
//		svolume.setCellValueFactory(new PropertyValueFactory<Stock,String>("volume"));
	}
	
	@FXML
	private Button Btn;
	@FXML
	private Button DelBtn;
	
	@FXML
	private void showMsg(){
		StockBLService stockbl = StockBLImpl.getAPIBLService();
		Iterator<StockVO> itr = stockbl.getAllStocks();
		ObservableList<Stock> ol = FXCollections.observableArrayList();
		while(itr.hasNext()){
			StockVO temp = itr.next();
			ol.add(new Stock(temp));
		}
		if(tableview==null){System.out.println("null");return;}
		tableview.setItems(ol);
		
		System.out.println("done===");
//		sname.setCellValueFactory(new PropertyValueFactory<Stock,String>("name"));
//		scode.setCellValueFactory(new PropertyValueFactory<Stock,String>("code"));
//		sopen.setCellValueFactory(new PropertyValueFactory<Stock,String>("open"));
//		sclose.setCellValueFactory(new PropertyValueFactory<Stock,String>("close"));
//		shigh.setCellValueFactory(new PropertyValueFactory<Stock,String>("high"));
//		slow.setCellValueFactory(new PropertyValueFactory<Stock,String>("low"));
//		svolume.setCellValueFactory(new PropertyValueFactory<Stock,String>("volume"));
		sname.setCellValueFactory(cellData -> cellData.getValue().name);
		scode.setCellValueFactory(cellData -> cellData.getValue().code);
		sopen.setCellValueFactory(cellData -> cellData.getValue().open.asObject());
		sclose.setCellValueFactory(cellData -> cellData.getValue().close.asObject());
		shigh.setCellValueFactory(cellData -> cellData.getValue().high.asObject());
		slow.setCellValueFactory(cellData -> cellData.getValue().low.asObject());
		svolume.setCellValueFactory(cellData -> cellData.getValue().volume.asObject());
		
		System.out.println("message!");
	}
	@FXML
	private void DeleteData(){
		int index = tableview.getSelectionModel().getSelectedIndex();
//		System.out.println(index);
		while(tableview.getItems().size() != 0)
			tableview.getItems().remove(0);
	}
	@FXML
	private void handleMouseClicked(MouseEvent event) {
		if(event.getClickCount() < 2){
			return;
		}else{
			
			int index = tableview.getSelectionModel().getSelectedIndex();
			
			System.out.println("Double clicked! line is " + index);
			
			show_K_line();
			
			
		}
	}
	@FXML
	private void show_K_line(){
		
		
		
		
		
	}
	
}
