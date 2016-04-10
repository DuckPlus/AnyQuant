package ui.controller;


import blimpl.OptionalStockBLImpl;
import blservice.OptionalStockBLService;
import enumeration.MyDate;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ui.controller.candleStick.TimeSharingChart;
import util.PanelType;
import vo.Stock;

public class StockDetailController {

	@FXML
	private Label nameLB, codeLB;
	@FXML
	private Label openLB, closeLB, lowLB , highLB;
	@FXML
	private Label turnoverRateLB, turnoverVolLB;
	@FXML
	private Label peLB,pbLB;
	@FXML
	private Button addBtn,backBtn;
	@FXML
	private Tab k_day, k_week, k_month;
	@FXML
	private Tab timeSharing;
	@FXML
	private DatePicker  dayStart,dayEnd,weekStart,weekEnd,monthStart,monthEnd;
	@FXML
	private Button dayBT,weekBT,monthBT;
	@FXML
	private BorderPane dayBorderPane,weekBorderPane,monthBorderPane;
	@FXML
	private VBox vbox;
	@FXML
	private StackPane stackPane;
	@FXML
	private GridPane cachePane;
	@FXML
	TabPane tabPane;
	private Boolean exist;
	private String stockCode;
	private PanelType parentPanelType;
	private Stock currentStock;
	private OptionalStockBLService optionBl = OptionalStockBLImpl.getOptionalBLService();
	private CandleStickController candleStickController = CandleStickController.getCandleStickController();
	private RightPaneController rightPaneController = RightPaneController.getRightPaneController();
	private static StockDetailController instance;

	public StockDetailController() {
		// System.err.println("init constructor======");
		if (instance == null) {
			// System.out.println("【null instance】");
			instance = this;
		}
	}

	public static StockDetailController getStockDetailController() {
		// System.err.println("get instance");
		if (instance == null) {
			// System.err.println("create");
			instance = new StockDetailController();
		}
		return instance;
	}

	@FXML
	private void initialize() {

	}


	public void setData(Stock stock,PanelType panelType) {
		parentPanelType=panelType;
		System.out.println("[get in and current stock :" + stock.code + "]");
		currentStock = stock;
		System.out.println("changed!!");
		stockCode = stock.code.get();
		nameLB.setText(stock.name.get());
		codeLB.setText(stockCode);
		openLB.setText(String.valueOf(stock.open.get()));
		closeLB.setText(String.valueOf(stock.close.get()));
		highLB.setText(String.valueOf(stock.high.get()));
		lowLB.setText(String.valueOf(stock.low.get()));
		turnoverRateLB.setText(String.valueOf(stock.turnoverRate.get()));
		peLB.setText(String.valueOf(stock.pe.get()));
		pbLB.setText(String.valueOf(stock.pb.get()));
		turnoverVolLB.setText(String.valueOf(stock.turnoverVol.get()));
		if (optionBl.ifStockExist(stockCode)) {// 存在于自选股
			addBtn.setText("删除该自选股");
			exist = true;
		} else {
			addBtn.setText("加入自选股");
			exist = false;
		}
		// add time sharing then
		initTimeSharing();
		// add k_line
		initKLine();
	}

	private void initTimeSharing() {
		TimeSharingChart timeChart = new TimeSharingChart(currentStock);
		timeSharing.setContent(timeChart.getTimeSharingChart());
	}

	private void initKLine() {
		System.out.println("initKLine()"+currentStock.code.get());
		Node dayChart=candleStickController.getInitialDayChart(currentStock.code.get());
		Node weekChart=candleStickController.getInitialWeekChart(currentStock.code.get());
		Node monthChart = candleStickController.getInitialMonthChart(currentStock.code.get());
		dayBorderPane.setCenter(dayChart);
		weekBorderPane.setCenter(weekChart);
		monthBorderPane.setCenter(monthChart);

	}
	@FXML
	private  void updateDayChart(){
		MyDate start = new MyDate(dayStart.getValue().getYear(),dayStart.getValue().getMonthValue(),dayStart.getValue().getDayOfMonth());
		MyDate end = new MyDate(dayEnd.getValue().getYear(),dayEnd.getValue().getMonthValue(),dayEnd.getValue().getDayOfMonth());
        Node dayChart = candleStickController.getUpdatedDayChart(currentStock.code.get(), start, end);
        dayBorderPane.setCenter(dayChart);
	}
	@FXML
	private  void updateWeekChart(){
		MyDate start = new MyDate(weekStart.getValue().getYear(),weekStart.getValue().getMonthValue(),weekStart.getValue().getDayOfMonth());
		MyDate end = new MyDate(weekEnd.getValue().getYear(),weekEnd.getValue().getMonthValue(),weekEnd.getValue().getDayOfMonth());
        Node weekChart = candleStickController.getUpdatedWeekChart(currentStock.code.get(), start, end);
        weekBorderPane.setCenter(weekChart);
	}
	@FXML
	private  void updateMonthChart(){
		MyDate start = new MyDate(monthStart.getValue().getYear(),monthStart.getValue().getMonthValue(),monthStart.getValue().getDayOfMonth());
		MyDate end = new MyDate(monthEnd.getValue().getYear(),monthEnd.getValue().getMonthValue(),monthEnd.getValue().getDayOfMonth());
        Node monthChart = candleStickController.getUpdatedMonthChart(currentStock.code.get(), start, end);
        monthBorderPane.setCenter(monthChart);
	}
	@FXML
	private void back(){
		if(parentPanelType==PanelType.OPTIONAL_STOCK){
			rightPaneController.showOptionalStockPane();
		}else if(parentPanelType==PanelType.STOCK_LIST){
			rightPaneController.showStockListPane();
		}
	}

	@FXML
 	private void addOptionalStock() {
		if (currentStock == null)
			System.err.println("current null");
		if (exist) {// 执行删除操作
			if (optionBl.deleteStockCode(stockCode)) {// 删除成功
				addBtn.setText("加入自选股");
				exist = false;
			} else {
				addBtn.setText("删除失败");// TODO 失败原因？。。
			}
		} else {// 执行增加操作
			System.out.println("add begin");
			if (optionBl.addStockCode(stockCode)) {// 添加成功
				addBtn.setText("删除该自选股");
				exist = true;
			} else {
				addBtn.setText("加入失败");
			}
		}

	}
}
