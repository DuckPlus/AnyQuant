package ui.controller;


import java.util.Iterator;
import java.util.Map.Entry;

import blimpl.BenchMarkBLImpl;
import blimpl.OptionalStockBLImpl;
import blservice.BenchMarkBLService;
import blservice.OptionalStockBLService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import util.PanelType;
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
	TableColumn<Stock, String>amplitude;// = new TableColumn<Stock, Double>();
	@FXML
	TableColumn<Stock, String>changeRate;// = new TableColumn<Stock, Double>();
	@FXML
	TableView<Stock> tableview;// = new TableView<Stock>();
	@FXML
	TextField searchBar;
	@FXML
	AnchorPane bottomPane;
	@FXML
	Tab geographicalDis;
	@FXML
	Tab boardDis;
	private ObservableList<Stock> observableList;

	private OptionalStockBLService optionalBl = OptionalStockBLImpl.getOptionalBLService();

	private BenchMarkBLService benchMarkBl = BenchMarkBLImpl.getBenchMarkBLService();

	private StockDetailController stockDetailController;

	private RightPaneController rightPaneController;

	private BorderPane stockDetailPane;
	private AnchorPane chartPane;
	CandleStickController candleStickController;

	private static optionalStockController instance;
	InstanceController instanceController = InstanceController.getInstance();
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
		stockDetailPane = instanceController.getStockDetailPane();
		initPieChart();
		if(name!=null){
			System.out.println("not null col");
		getOptionalStock();
		}

	}
	private void initPieChart(){
		MyPieChart pc_board = new MyPieChart();
		Iterator<Entry<String,Integer>>itr = optionalBl.getBorderDistribution();
		while(itr.hasNext()){
			Entry<String,Integer> temp = itr.next();
			pc_board.addData(temp.getKey(), temp.getValue());
		}
//		optionalBl.getBorderDistribution();
		boardDis.setContent(pc_board.getPieChart());
		//
		MyPieChart pc_geog = new MyPieChart();
		Iterator<Entry<String,Integer>>itr2 = optionalBl.getRegionDistribution();
		while(itr2.hasNext()){
			Entry<String,Integer> temp = itr2.next();
			pc_geog.addData(temp.getKey(), temp.getValue());
		}
//		optionalBl.getBorderDistribution();
		geographicalDis.setContent(pc_geog.getPieChart());

	}
	@FXML
	public void getOptionalStock(){
		Iterator<StockVO>itr = optionalBl.getOptionalStocks();
		showTableData(itr);
		initPieChart();
	}
	private void showTableData(Iterator<StockVO>itr){
		tableview.getItems().removeAll(observableList);
		while(itr.hasNext()){
			StockVO temp = itr.next();
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
		amplitude.setCellValueFactory(cell ->cell.getValue().getStringAmplitude());
		changeRate.setCellValueFactory(cell ->cell.getValue().getStringChangeRate());
	}

	@FXML
	private void deleteOptionalStock(){
		if(tableview.getSelectionModel().getSelectedIndex()!=-1){
			String stockCode = tableview.getSelectionModel().getSelectedItem().code.get();
			boolean status = optionalBl.deleteStockCode(stockCode);
		}
	}
	@FXML
	private void handleMouseClick(MouseEvent e){
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

//			chartPane = (AnchorPane)GraphicsUtils.getParent("CandleStickPane");
//			stockDetailPane.setCenter(chartPane);

			stockDetailController.setData(selectedStock,PanelType.OPTIONAL_STOCK);
			rightPaneController.showDetailPane(stockDetailPane);

		}
	}

}
