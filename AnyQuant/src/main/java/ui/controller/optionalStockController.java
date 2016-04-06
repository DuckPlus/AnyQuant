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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import ui.GraphicsUtils;
import ui.controller.candleStick.CandleStickController;
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
	private ObservableList<Stock> observableList;

	private OptionalStockBLService optinalBl = OptionalStockBLServiceImpl.getOptionalBLService();

	private StockDetailController stockDetailController;

	private RightPaneController rightPaneController;

	private BorderPane stockDetailPane;
	private AnchorPane chartPane;
	CandleStickController candleStickController;

	private static optionalStockController instance;
	public optionalStockController() {
		if(instance == null ){
			instance = this;
		}
	}
	public static optionalStockController getOptionalStockController() {
		if (instance == null) {
			instance = new optionalStockController();
		}
		return instance;
	}


	@FXML
	private void initialize(){
		observableList = FXCollections.observableArrayList();
		stockDetailPane = (BorderPane)GraphicsUtils.getParent("StockDetail");
		if(name!=null){
			System.out.println("not null col");
		getOptionalStock();
		}

	}
	@FXML
	public void getOptionalStock(){
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
		turnover.setCellValueFactory(cell ->cell.getValue().turnoverRate.asObject());
		volume.setCellValueFactory(cell ->cell.getValue().turnoverVol.asObject());
		amplitude.setCellValueFactory(cell ->cell.getValue().amplitude.asObject());
		changeRate.setCellValueFactory(cell ->cell.getValue().changeRate.asObject());
	}


	@FXML
	private void handleMouseClick(MouseEvent e){

//		if(e.getClickCount()==2){
//			if(tableview.getSelectionModel().getSelectedIndex()==-1){
//				System.out.println("empty line");
//				return ;
//			}
//			if(stockDetailController==null){
//				stockDetailController = StockDetailController.getStockDetailController();
//			}
//			if(rightPaneController==null){
//				rightPaneController = RightPaneController.getRightPaneController();
//			}
//
//			Stock selectedStock = tableview.getSelectionModel().getSelectedItem();
//			stockDetailController.setData(selectedStock);
//			rightPaneController.showDetailPane(Pane);
		if(e.getClickCount()==2){
			if(tableview.getSelectionModel().getSelectedIndex()==-1){
				System.out.println("empty line ");
				return;
			}

			Stock selectedStock = tableview.getSelectionModel().getSelectedItem();
			//The stockDetailController is null at first, and it must generated after the fxml has initialize
			//it, otherwise we will get a totally defferent object from the fxml's
			if(stockDetailController==null){
				System.err.println("============new controller============");
			      stockDetailController = StockDetailController.getStockDetailController();
			}
			if(rightPaneController==null){
				rightPaneController = RightPaneController.getRightPaneController();
			}

			chartPane = (AnchorPane)GraphicsUtils.getParent("CandleStickPane");
			stockDetailPane.setCenter(chartPane);

			if(candleStickController==null){
				candleStickController = CandleStickController.getCandleStickController();
			}
//			MyDate begin = new MyDate(2016, 03, 01);
//			MyDate end = new MyDate(2016, 03, 20);
//			candleStickController.setDate(begin, end);
			candleStickController.setStockCode(selectedStock.code.get());
			stockDetailController.setData(selectedStock);
			rightPaneController.showDetailPane(stockDetailPane);

		}
	}

}
