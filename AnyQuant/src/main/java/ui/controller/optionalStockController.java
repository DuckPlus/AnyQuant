/**
 * @status reviewed
 * @handler dzm
 */
package ui.controller;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Map;
import blimpl.BenchMarkBLImpl;
import blimpl.OptionalStockBLImpl;
import blimpl.StockBLImpl;
import blservice.BenchMarkBLService;
import blservice.OptionalStockBLService;
import blservice.StockBLService;
import enumeration.CmpChartType;
import enumeration.MyDate;
import enumeration.PanelType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import ui.helper.ColorHelper;
import util.MyBarChart;
import util.MyLineChart;
import util.MyPieChart;
import util.DateCalculator;
import util.StatisticUtil;
import vo.Stock;
import vo.StockVO;

public class optionalStockController {
	BarChart<String,Number> boardBarChart, regionBarChart;
	MyPieChart myPieChart_b,myPieChart_r;
	MyBarChart myBarChart_b, myBarChart_r;
	PieChart boardPieChart, regionPieChart;
	Map<String, Integer> boardMap, regionMap;
	@FXML
	TableColumn<Stock, String> code;
	@FXML
	TableColumn<Stock, String> name;
	@FXML
	TableColumn<Stock, Double> open;
	@FXML
	TableColumn<Stock, Double> high;
	@FXML
	TableColumn<Stock, Double> low;
	@FXML
	TableColumn<Stock, Double> close;
	@FXML
	TableColumn<Stock, Double> turnoverRate;
	@FXML
	TableColumn<Stock, Long> turnoverVol;
	@FXML
	TableColumn<Stock, String> amplitude;
	@FXML
	TableColumn<Stock, String> changeRate;
	@FXML
	TableView<Stock> tableview;
	@FXML
	TextField searchBar;
	@FXML
	AnchorPane bottomPane;
	@FXML
	Tab geoDisTab;
	@FXML
	Tab boardDisTab;
	@FXML
	HBox boardHBox, regionHBox;
	// compare below
	@FXML
	ComboBox<String> chartType;
	@FXML
	TableColumn<Stock, String> cmpName, cmpCode;
	@FXML
	TableView<Stock> cmpTableview;
	@FXML
	DatePicker cmpBgn, cmpEnd;
	@FXML
	AnchorPane cmpChartPane;
	MyLineChart cmpChart;

	private ObservableList<Stock> observableList = FXCollections.observableArrayList();
	private ObservableList<Stock> cmpTableData = FXCollections.observableArrayList();;
	private OptionalStockBLService optionalBl = OptionalStockBLImpl.getOptionalBLService();
	private StockBLService stockBl = StockBLImpl.getAPIBLService();
	private StockDetailController stockDetailController;
	private RightPaneController rightPaneController;
	private BorderPane stockDetailPane;
	private optionalStockController currentController;
	private static optionalStockController instance;
	InstanceController instanceController = InstanceController.getInstance();

	public optionalStockController() {
		if (instance == null) {
			instance = this;
		}
		currentController = this;
		System.err.println(currentController.toString() + "current");
		System.err.println(this.toString() + "this");
	}
	/**
	 * 全局初始化
//	 */
	@FXML
	private void initialize() {
		instanceController.registOptionalStockController(this);
		stockDetailPane = instanceController.getStockDetailPane();
		initPieAndBarChart();
		getOptionalStock();
		initChoice();
		initCmpModule();
	}

	public static optionalStockController getOptionalStockController() {
		if (instance == null) {
			instance = new optionalStockController();
		}
		return instance;
	}
	/**
	 * 选择tabpanel的地域分区按钮时的响应
	 */
	@FXML
	private void selectRegion() {
		initRegion();
		regionAnimate();
	}
	/**
	 * 选择tabpanel的板块分区按钮时的响应
	 */
	@FXML
	private void selectBoard() {
		initBoard();
		boardAnimate();
	}

	/**
	 * 仅初始化，还没加载数据
	 */
	private void initPieAndBarChart() {
		boardMap = optionalBl.getBoardDistributionMap();
		regionMap = optionalBl.getRegionDistributionMap();
	}

	/**
	 * 初始化的子步骤--初始化地域分区的两个图表
	 */
	private void initRegion() {
		regionHBox.getChildren().clear();
		myPieChart_r=new MyPieChart();
		regionPieChart = myPieChart_r.createPieChart(regionMap);
		myBarChart_r = new MyBarChart();
		regionBarChart = myBarChart_r.createBarChart(regionMap);
		regionHBox.getChildren().addAll(regionPieChart, regionBarChart);
	}

	/**
	 * 初始化的子步骤--初始化板块分区的两个图表
	 */
	private void initBoard() {
		boardHBox.getChildren().clear();
		myPieChart_b=new MyPieChart();
		boardPieChart = myPieChart_b.createPieChart(boardMap);
		myBarChart_b = new MyBarChart();
		boardBarChart = myBarChart_b.createBarChart(boardMap);
		boardHBox.getChildren().addAll(boardPieChart, boardBarChart);
	}

	/**
	 * 加载 板块分区的图表的数据
	 */
	private void boardAnimate() {
		myPieChart_b.animate();
		myBarChart_b.addData();
	}

	/**
	 * 加载 地域分区的图表的数据
	 */
	private void regionAnimate() {
		myPieChart_r.animate();
		myBarChart_r.addData();
	}
	/**
	 * 初始化自选股比较模块的类型选择器
	 */
	private void initChoice() {
		chartType.getItems().add("市盈率");
		chartType.getItems().add("市净率");
		chartType.getItems().add("成交量");
		chartType.getSelectionModel().select(0);
	}
	/**
	 * 初始化
	 */
	private void initCmpModule() {
		// init date picker
		MyDate monthAgo = DateCalculator.getAnotherDay(
				new MyDate(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()),
				-30);

		cmpBgn.setValue(LocalDate.of(monthAgo.getYear(), monthAgo.getMonth(), monthAgo.getDay()));
		cmpEnd.setValue(LocalDate.now());
		// init table
		cmpTableview.getItems().removeAll(cmpTableData);
		Iterator<StockVO> itr = optionalBl.getOptionalStocks();
		while (itr.hasNext()) {
			StockVO temp = itr.next();
			Stock dataProperty = new Stock(temp);
			cmpTableData.add(dataProperty);
		}
		cmpTableview.setItems(cmpTableData);
		cmpName.setCellValueFactory(cell -> cell.getValue().name);
		cmpCode.setCellValueFactory(cell -> cell.getValue().code);

		// init chart
		cmpChart = new MyLineChart();
		cmpChart.setSize(650, 650);
		cmpChart.getTimesharingChart().setCreateSymbols(true);
		cmpChart.getYAxis().setUpperBound(1);
		cmpChart.getYAxis().setLowerBound(0);
		cmpChart.getTimesharingChart().setTitle("Compare Module");
		cmpChartPane.getChildren().add(cmpChart.getTimesharingChart());
	}

	/**
	 * 在比较模块中点击加入比较后触发的一系列数据获取
	 * 和显示图线的方法
	 */
	@FXML
	private void addCmpStock(){
		if(cmpTableview.getSelectionModel().getSelectedIndex()!=-1){
		Stock selectedCmpStock = cmpTableview.getSelectionModel().getSelectedItem();
		switch(chartType.getSelectionModel().getSelectedItem()){
			case "市盈率":drawCmpPEChart(selectedCmpStock);break;
			case "市净率":drawCmpPBChart(selectedCmpStock);;break;
			case "成交量":drawCmpVolumeValueChart(selectedCmpStock);;break;
			default:System.out.println("default");break;
		}
		}
	}
	/**
	 * 画pe走势图的方法
	 * @param stock
	 */
	private void drawCmpPEChart(Stock stock){
		MyDate cmpB = new MyDate(cmpBgn.getValue().getYear(), cmpBgn.getValue().getMonthValue(),
				cmpBgn.getValue().getDayOfMonth());
		MyDate cmpE = new MyDate(cmpEnd.getValue().getYear(), cmpEnd.getValue().getMonthValue(),
				cmpEnd.getValue().getDayOfMonth());

		if (!DateCalculator.ifEarlier(cmpB, cmpE)) {
			return;
		}
		
		Iterator<StockVO>itr = stockBl.getStocksByTime(stock.code.get(), cmpB, cmpE);
		Iterator<StockVO>itrMax = stockBl.getStocksByTime(stock.code.get(), cmpB, cmpE);
		Iterator<StockVO>itrMin = stockBl.getStocksByTime(stock.code.get(), cmpB, cmpE);
		cmpChart.addSeries(StatisticUtil.dimensionLessPE(itr,itrMax,itrMin),stock,CmpChartType.peChart);
	}
	/**
	 * 画pb走势图的方法
	 * @param stock
	 */
	private void drawCmpPBChart(Stock stock){
		MyDate cmpB = new MyDate(cmpBgn.getValue().getYear(), cmpBgn.getValue().getMonthValue(), cmpBgn.getValue().getDayOfMonth());
		MyDate cmpE = new MyDate(cmpEnd.getValue().getYear(), cmpEnd.getValue().getMonthValue(), cmpEnd.getValue().getDayOfMonth());

		if (!DateCalculator.ifEarlier(cmpB, cmpE)) {
			return;
		}
		Iterator<StockVO>itr = stockBl.getStocksByTime(stock.code.get(), cmpB, cmpE);
		Iterator<StockVO>itrMax = stockBl.getStocksByTime(stock.code.get(), cmpB, cmpE);
		Iterator<StockVO>itrMin = stockBl.getStocksByTime(stock.code.get(), cmpB, cmpE);
		cmpChart.addSeries(StatisticUtil.dimensionLessPB(itr,itrMax,itrMin),stock,CmpChartType.pbChart);
	}
	/**
	 * 画成交额走势图的方法
	 * @param stock
	 */
	private void drawCmpVolumeValueChart(Stock stock){
		MyDate cmpB = new MyDate(cmpBgn.getValue().getYear(), cmpBgn.getValue().getMonthValue(), cmpBgn.getValue().getDayOfMonth());
		MyDate cmpE = new MyDate(cmpEnd.getValue().getYear(), cmpEnd.getValue().getMonthValue(), cmpEnd.getValue().getDayOfMonth());

		if(!DateCalculator.ifEarlier(cmpB, cmpE)){
			return;
		}
		Iterator<StockVO>itr = stockBl.getStocksByTime(stock.code.get(), cmpB, cmpE);
		Iterator<StockVO>itrMax = stockBl.getStocksByTime(stock.code.get(), cmpB, cmpE);
		Iterator<StockVO>itrMin = stockBl.getStocksByTime(stock.code.get(), cmpB, cmpE);
		cmpChart.addSeries(StatisticUtil.dimensionLessVolumeValue(itr,itrMax,itrMin),stock,CmpChartType.turnoverVolChart);
	}
	

	/**
	 * 清除比较图表上的所有图线
	 */
	@FXML
	private void clearCmpChart() {
		cmpChart.removeAllSeries();
	}
	/**
	 * 刷新自选股列表和图表
	 */
	@FXML
	private void refreshOptionalStockData() {
		getOptionalStock();
		initPieAndBarChart();
	}

	@FXML
	public void getOptionalStock() {
		Iterator<StockVO> itr = optionalBl.getOptionalStocks();
		showTableData(itr);
	}
	/**
	 * 读取迭代器的信息并显示到表格中
	 * @param itr
	 */
	
	private void showTableData(Iterator<StockVO> itr) {
		tableview.getItems().removeAll(observableList);
		while (itr.hasNext()) {
			StockVO temp = itr.next();
			Stock dataProperty = new Stock(temp);
			observableList.add(dataProperty);
		}
		tableview.setItems(observableList);
		name.setCellValueFactory(cell -> cell.getValue().name);
		code.setCellValueFactory(cell -> cell.getValue().code);
		open.setCellValueFactory(cell -> cell.getValue().open.asObject());
		high.setCellValueFactory(cell -> cell.getValue().high.asObject());
		low.setCellValueFactory(cell -> cell.getValue().low.asObject());
		close.setCellValueFactory(cell -> cell.getValue().close.asObject());
		turnoverRate.setCellValueFactory(cell -> cell.getValue().turnoverRate.asObject());
		turnoverVol.setCellValueFactory(cell -> cell.getValue().turnoverVol.asObject());
		amplitude.setCellValueFactory(cell -> cell.getValue().getStringAmplitude());
		changeRate.setCellValueFactory(cell -> cell.getValue().getStringChangeRate());

		ColorHelper.setColorForStock(observableList, tableview.getColumns());
	}

	/**
	 * 删除列表中选中的自选股
	 */
	@FXML
	private void deleteOptionalStock() {
		if (tableview.getSelectionModel().getSelectedIndex() != -1) {
			String stockCode = tableview.getSelectionModel().getSelectedItem().code.get();
			boolean status = optionalBl.deleteStockCode(stockCode);
			getOptionalStock();
		}
	}
	/**
	 * 处理双击选择一支股票后的股票细节显示，同StockList中的该方法
	 * @param e
	 */
	@FXML
	private void handleMouseClick(MouseEvent e) {
		if (e.getClickCount() == 2) {
			if (tableview.getSelectionModel().getSelectedIndex() == -1) {
				//do nothing if not a doubleClicked event or select nothing
				return;
			}

			Stock selectedStock = tableview.getSelectionModel().getSelectedItem();
			// The stockDetailController is null at first, and it must generated
			// after the fxml has initialize
			// it, otherwise we will get a totally defferent object from the
			// fxml's
			if (stockDetailController == null) {
				stockDetailController = StockDetailController.getStockDetailController();
			}
			if (rightPaneController == null) {
				rightPaneController = RightPaneController.getRightPaneController();
			}

			stockDetailController.setData(selectedStock, PanelType.OPTIONAL_STOCK);
			rightPaneController.showDetailPane(stockDetailPane);
		}
	}

}
