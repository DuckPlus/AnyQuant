package ui.controller;

import java.util.Iterator;

import blimpl.StockBLImpl;
import blservice.StockBLService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import ui.GraphicsUtils;
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
	@FXML 
	TextField searchBar;
	
	RightPaneController rightPaneController = RightPaneController.getRightPaneController();
	StockDetailController stockDetailController;// = StockDetailController.getStockDetailController();
	//get the logic service
	StockBLService stockBl = StockBLImpl.getAPIBLService();
	//
	ObservableList<Stock> obsevableList ;
	
	BorderPane stockDetailPane;
	
	public StockListController() {
		System.out.println("hello constractor");
		obsevableList = FXCollections.observableArrayList();
//		tableview.setOnMouseClicked(value);
	}
	@FXML
	private void initialize(){
		System.out.println("hello init");
		showStocklist();
		stockDetailPane = (BorderPane)GraphicsUtils.getParent("StockDetail");
	}
	
	/**
	 * 
	 */
	@FXML
	public void showStocklist(){
		
		Iterator<StockVO>itr = stockBl.getAllStocks();
		showTableData(itr);
		
	}
	
	private void showTableData(Iterator<StockVO>itr){
		tableview.getItems().removeAll(obsevableList);
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
	}
	@FXML
	private void delAllData(){
//		int sum = tableview.getItems().size();
		tableview.getItems().removeAll(obsevableList);
	}
	@FXML
	private void handleMouseClick(MouseEvent event){
		if(event.getClickCount()==2){
			if(tableview.getSelectionModel().getSelectedIndex()==-1){
				System.out.println("empty line ");
				return;
			}
			
			int row =tableview.getSelectionModel().getSelectedIndex();
			String code =tableview.getSelectionModel().getSelectedItem().code.get();
			Stock selectedStock = tableview.getSelectionModel().getSelectedItem();
			System.out.println(code);
//			if(stockDetailPane==null){
//				System.out.println("pane null");
//				stockDetailPane = (BorderPane)GraphicsUtils.getParent("StockDetail");
//			}
//			stockDetailController.setData(selectedStock);
//			StockDetailController sc = StockDetailController.getCurrent();
//			StockDetailController sc = StockDetailController.getStockDetailController();
			
			//The stockDetailController is null at first, and it must generated after the fxml has initialize
			//it, otherwise we will get a totally defferent object from the fxml's
			if(stockDetailController==null){
			stockDetailController = StockDetailController.getStockDetailController();
			}
//			if(sc.nameLabel==null){
//				System.out.println("controller null");
//			}else{
//				System.out.println("controller NOT null");
//			}
			stockDetailController.setData(selectedStock);
//			sc.setData(selectedStock);
			rightPaneController.showDetailPane(stockDetailPane);
		}
	}
	@FXML
	private void searchStocklist(){
		System.out.println("hello search in time:  ["+searchBar.getText()+"]end");
		String stockCode = searchBar.getText();
		Iterator<StockVO>itr =stockBl.getStocksByStockCode(stockCode);
		showTableData(itr);
	}
	


}
