package ui.controller;


import java.time.LocalDate;
import java.util.Iterator;
import java.util.Map.Entry;

import blimpl.BenchMarkBLImpl;
import blimpl.OptionalStockBLImpl;
import blimpl.StockBLImpl;
import blservice.BenchMarkBLService;
import blservice.OptionalStockBLService;
import blservice.StockBLService;
import enumeration.MyDate;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ChoiceBox;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import ui.controller.candleStick.MyLineChart;
import util.MyTime;
import ui.helper.ColorHelper;
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
	TableColumn<Stock, Double>turnoverRate;// = new TableColumn<Stock, Double>();
	@FXML
	TableColumn<Stock, Long>turnoverVol;// = new TableColumn<Stock, Long>();
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
	Tab geoDisTab;
	@FXML
	Tab boardDisTab;
	// compare below
	@FXML
	ComboBox<String>chartType;
	@FXML
	TableColumn<Stock, String>cmpName,cmpCode;
	@FXML
	TableView<Stock>cmpTableview;
	@FXML
	DatePicker cmpBgn,cmpEnd;
	@FXML
	AnchorPane cmpChartPane;

	MyLineChart cmpChart;

//	@FXML
//	AnchorPane rightP;

	private ObservableList<Stock> observableList;

	private ObservableList<Stock> cmpTableData;

	private OptionalStockBLService optionalBl = OptionalStockBLImpl.getOptionalBLService();

	private BenchMarkBLService benchMarkBl = BenchMarkBLImpl.getBenchMarkBLService();

	private StockBLService stockBl = StockBLImpl.getAPIBLService();

	private StockDetailController stockDetailController;

	private RightPaneController rightPaneController;

	private BorderPane stockDetailPane;
//	private AnchorPane chartPane;
	private optionalStockController currentController;
	CandleStickController candleStickController;

	private static optionalStockController instance;
	InstanceController instanceController = InstanceController.getInstance();
	public optionalStockController() {
		if(instance == null ){
			instance = this;
		}
		currentController = this;
		System.out.println(currentController.toString()+"current");
		System.out.println(this.toString()+"this");
	}
	public static optionalStockController getOptionalStockController() {
		if (instance == null) {
			System.err.println("get a new one");
			instance = new optionalStockController();
		}
		return instance;
	}


	@FXML
	private void initialize(){
		InstanceController insc = InstanceController.getInstance();
		insc.registOptionalStockController(this);
		observableList = FXCollections.observableArrayList();
		cmpTableData = FXCollections.observableArrayList();
		stockDetailPane = instanceController.getStockDetailPane();
//		initPieChart();
		getOptionalStock();
		initChoice();
		initCmpTable();
	}

	private void initChoice(){
		chartType.getItems().add("市盈率");
		chartType.getItems().add("市净率");
		chartType.getItems().add("成交量");
		chartType.getSelectionModel().select(0);
	}
	private void initCmpTable(){
		//init date picker
		MyDate monthAgo = MyTime.getAnotherDay(new MyDate(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()), -30);

		cmpBgn.setValue(LocalDate.of(monthAgo.getYear(), monthAgo.getMonth(), monthAgo.getDay()));
		cmpEnd.setValue(LocalDate.now());
		//init table
		cmpTableview.getItems().removeAll(cmpTableData);
		Iterator<StockVO>itr = optionalBl.getOptionalStocks();
		while(itr.hasNext()){
			StockVO temp = itr.next();
			Stock dataProperty = new Stock(temp);
			cmpTableData.add(dataProperty);
		}
		cmpTableview.setItems(cmpTableData);
		cmpName.setCellValueFactory(cell -> cell.getValue().name);
		cmpCode.setCellValueFactory(cell -> cell.getValue().code);

		//init chart
		cmpChart = new MyLineChart();
		cmpChart.setSize(650, 650);
		cmpChartPane.getChildren().add(cmpChart.getTimesharingChart());
	}
	@FXML
	private void addCmpStock(){
		if(cmpTableview.getSelectionModel().getSelectedIndex()!=-1){
		Stock selectedCmpStock = cmpTableview.getSelectionModel().getSelectedItem();
//		MyDate cmpB = new MyDate(cmpBgn.getValue().getYear(), cmpBgn.getValue().getMonthValue(), cmpBgn.getValue().getDayOfMonth());
//		MyDate cmpE = new MyDate(cmpEnd.getValue().getYear(), cmpBgn.getValue().getMonthValue(), cmpBgn.getValue().getDayOfMonth());

		switch(chartType.getSelectionModel().getSelectedItem()){
			case "市盈率":drawCmpPEChart(selectedCmpStock.code.get());break;
			case "市净率":drawCmpPBChart(selectedCmpStock.code.get());;break;
			case "成交量":System.out.println("cjl");break;
			default:System.out.println("default");break;
		}
		}
	}
	private void drawCmpPEChart(String code){

		MyDate cmpB = new MyDate(cmpBgn.getValue().getYear(), cmpBgn.getValue().getMonthValue(), cmpBgn.getValue().getDayOfMonth());
		MyDate cmpE = new MyDate(cmpEnd.getValue().getYear(), cmpEnd.getValue().getMonthValue(), cmpEnd.getValue().getDayOfMonth());

		if(!MyTime.ifEarlier(cmpB, cmpE)){
			return;
		}
		Iterator<StockVO>itr = stockBl.getStocksByTime(code, cmpB, cmpE);
		Series<String, Number> series = new Series<String,Number>();
		while(itr.hasNext()){
			StockVO temp = itr.next();
			String date = temp.date;
			series.getData().add(new XYChart.Data<String, Number>(date, temp.pe));
		}
		cmpChart.addSeries(series, code, CmpChartType.peChart);
	}
	private void drawCmpPBChart(String code){
		MyDate cmpB = new MyDate(cmpBgn.getValue().getYear(), cmpBgn.getValue().getMonthValue(), cmpBgn.getValue().getDayOfMonth());
		MyDate cmpE = new MyDate(cmpEnd.getValue().getYear(), cmpEnd.getValue().getMonthValue(), cmpEnd.getValue().getDayOfMonth());

		if(!MyTime.ifEarlier(cmpB, cmpE)){
			return;
		}
		Iterator<StockVO>itr = stockBl.getStocksByTime(code, cmpB, cmpE);
		Series<String, Number> series = new Series<String,Number>();
		while(itr.hasNext()){
			StockVO temp = itr.next();
			String date = temp.date;
			series.getData().add(new XYChart.Data<String, Number>(date, temp.pb));
		}
		cmpChart.addSeries(series,code,CmpChartType.pbChart);
	}

	@FXML
	private void clearCmpChart(){
		cmpChart.removeAllSeries();

	}
	@FXML
	private void initPieChart(){
		MyPieChart  myPieChart = new MyPieChart();

		ObservableList<Data> datas_board = FXCollections.observableArrayList();
		Iterator<Entry<String,Integer>>itr = optionalBl.getBorderDistribution();
		while(itr.hasNext()){
			Entry<String,Integer> temp = itr.next();
			datas_board.add(new PieChart.Data(temp.getKey(), temp.getValue()));
		}
        PieChart boardPieChart = myPieChart.createPieChart(datas_board);
        boardDisTab.setContent(boardPieChart);
        myPieChart.animate();


		ObservableList<Data> datas_geog = FXCollections.observableArrayList();
		Iterator<Entry<String,Integer>>itr2 = optionalBl.getRegionDistribution();
		while(itr2.hasNext()){
			Entry<String,Integer> temp = itr2.next();
			datas_geog.add(new PieChart.Data(temp.getKey(), temp.getValue()));
		}
		PieChart regionPieChart = myPieChart.createPieChart(datas_geog);
        geoDisTab.setContent(regionPieChart);
        myPieChart.animate();

	}



	@FXML
	public void getOptionalStock(){

		System.out.println("refresh "+currentController.toString());
		Iterator<StockVO>itr = optionalBl.getOptionalStocks();
		Iterator<StockVO>itrBack = optionalBl.getOptionalStocks();
		while(itrBack.hasNext()){
			StockVO temp = itrBack.next();
			System.out.println(temp.name);
		}
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
		turnoverRate.setCellValueFactory(cell ->cell.getValue().turnoverRate.asObject());
		turnoverVol.setCellValueFactory(cell ->cell.getValue().turnoverVol.asObject());
		amplitude.setCellValueFactory(cell ->cell.getValue().getStringAmplitude());
		changeRate.setCellValueFactory(cell ->cell.getValue().getStringChangeRate());

		ColorHelper.setColorForStock(observableList, tableview.getColumns());


	}

	@FXML
	private void deleteOptionalStock(){
		if(tableview.getSelectionModel().getSelectedIndex()!=-1){
			String stockCode = tableview.getSelectionModel().getSelectedItem().code.get();
			boolean status = optionalBl.deleteStockCode(stockCode);
			getOptionalStock();
//			System.out.println(currentController.toString()+"deleteCurrent");
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
//	public AnchorPane getRightBorderPane(){
//		return rightP;
//	}



}
