package ui.controller;


import java.time.LocalDate;

import blimpl.OptionalStockBLImpl;
import blservice.OptionalStockBLService;
import enumeration.Candle_Type;
import enumeration.MyDate;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import ui.controller.candleStick.CandleStickThreadHelper;
import ui.controller.candleStick.ProgressIndicatorHelper;
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
	private Tab deal;
	@FXML
	private DatePicker  dayStart,dayEnd,weekStart,weekEnd,
	                    monthStart,monthEnd,dealStart,dealEnd;
	@FXML
	private Button dayBT,weekBT,monthBT,dealBT;
	@FXML
	public ScrollPane  daySPane,weekSPane,monthSPane,timeSharingSPane,dealSPane;
	@FXML
	private GridPane dayCachePane,weekCachePane,monthCachePane
	                ,timeSharingCachePane,dealCachePane;
	@FXML
	private ProgressIndicator dayIndicator,weekIndicator,monthIndicator,
	 		timeSharingIndicator,dealAmountIndicator;
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
		dayEnd.setValue(LocalDate.now());
		dayStart.setValue(LocalDate.now());
		weekStart.setValue(LocalDate.now());
		weekEnd.setValue(LocalDate.now());
		monthStart.setValue(LocalDate.now());
		monthEnd.setValue(LocalDate.now());
	}
	@FXML
	public void selectDeal(){
		initDealAmount();
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
		// add k_line
		initKLine();
		// add time sharing then
		initTimeSharing();
		//add dealAmount

	}

	/**
	 *
	 */
	private void initDealAmount() {
		Task initdealTask = CandleStickThreadHelper.createDealAmountInitWorker(currentStock);
		ProgressIndicatorHelper.showProgressIndicator(initdealTask.progressProperty(),
       		initdealTask.runningProperty(), dealAmountIndicator,dealCachePane);

		new Thread(initdealTask).start();
	}

	private void initTimeSharing() {
//		TimeSharingChart timeChart = new TimeSharingChart(currentStock);
//		timeSharing.setContent(timeChart.getTimeSharingChart());
		Task initTimeSharingTask = CandleStickThreadHelper.createTimeSharingInitWorker(currentStock);
		ProgressIndicatorHelper.showProgressIndicator(initTimeSharingTask.progressProperty(),
        		 initTimeSharingTask.runningProperty(), timeSharingIndicator, timeSharingCachePane);
		new Thread(initTimeSharingTask).start();
	}
	private void initKLine() {
		System.out.println("initKLine()"+currentStock.code.get());


         Task initDayTask=CandleStickThreadHelper.
        		          createKChartInitWorker(Candle_Type.day,currentStock.code.get());

         Task initWeekTask= CandleStickThreadHelper.
        		          createKChartInitWorker(Candle_Type.week, currentStock.code.get());

         Task initMonthTask= CandleStickThreadHelper.
		          createKChartInitWorker(Candle_Type.month, currentStock.code.get());

         ProgressIndicatorHelper.showProgressIndicator(initDayTask.progressProperty(),
        		 initDayTask.runningProperty(), dayIndicator, dayCachePane);
         ProgressIndicatorHelper.showProgressIndicator(initWeekTask.progressProperty(),
        		 initWeekTask.runningProperty(), weekIndicator, weekCachePane);
         ProgressIndicatorHelper.showProgressIndicator(initMonthTask.progressProperty(),
        		 initMonthTask.runningProperty(), monthIndicator, monthCachePane);

 		 new Thread(initDayTask).start();
		 new Thread(initWeekTask).start();
		 new Thread(initMonthTask).start();
	}
	@FXML
	private  void updateDayChart(){
		MyDate start = new MyDate(dayStart.getValue().getYear(),dayStart.getValue().getMonthValue(),dayStart.getValue().getDayOfMonth());
		MyDate end = new MyDate(dayEnd.getValue().getYear(),dayEnd.getValue().getMonthValue(),dayEnd.getValue().getDayOfMonth());
		Task updateDayTask=CandleStickThreadHelper.
		          createUpdateKChartWorker(Candle_Type.day,currentStock.code.get(),start,end);
		ProgressIndicatorHelper.showProgressIndicator(updateDayTask.progressProperty(),
				updateDayTask.runningProperty(), dayIndicator, dayCachePane);
		new Thread(updateDayTask).start();
	}
	@FXML
	private  void updateWeekChart(){
		MyDate start = new MyDate(weekStart.getValue().getYear(),weekStart.getValue().getMonthValue(),weekStart.getValue().getDayOfMonth());
		MyDate end = new MyDate(weekEnd.getValue().getYear(),weekEnd.getValue().getMonthValue(),weekEnd.getValue().getDayOfMonth());
		Task updateWeekTask=CandleStickThreadHelper.
		          createUpdateKChartWorker(Candle_Type.week,currentStock.code.get(),start,end);
		ProgressIndicatorHelper.showProgressIndicator(updateWeekTask.progressProperty(),
				updateWeekTask.runningProperty(), weekIndicator, weekCachePane);
		new Thread(updateWeekTask).start();
	}
	@FXML
	private  void updateMonthChart(){
		MyDate start = new MyDate(monthStart.getValue().getYear(),monthStart.getValue().getMonthValue(),monthStart.getValue().getDayOfMonth());
		MyDate end = new MyDate(monthEnd.getValue().getYear(),monthEnd.getValue().getMonthValue(),monthEnd.getValue().getDayOfMonth());
		Task updateMonthTask=CandleStickThreadHelper.
		          createUpdateKChartWorker(Candle_Type.month,currentStock.code.get(),start,end);
		ProgressIndicatorHelper.showProgressIndicator(updateMonthTask.progressProperty(),
				updateMonthTask.runningProperty(), monthIndicator, monthCachePane);
		new Thread(updateMonthTask).start();
	}
	@FXML
	private void updateDealChart(){
		MyDate start = new MyDate(dealStart.getValue().getYear(),dealStart.getValue().getMonthValue(),dealStart.getValue().getDayOfMonth());
		MyDate end = new MyDate(dealEnd.getValue().getYear(),dealEnd.getValue().getMonthValue(),dealEnd.getValue().getDayOfMonth());
		Task updateDealTask=CandleStickThreadHelper.
		          createUpdateDealAmountInitWorker(currentStock, start, end);
		ProgressIndicatorHelper.showProgressIndicator(updateDealTask.progressProperty(),
				updateDealTask.runningProperty(), dealAmountIndicator, dealCachePane);
		new Thread(updateDealTask).start();
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
//				optionalStockController op = InstanceController.getInstance().getOptionalStockController();
//				op.getOptionalStock();
//				System.out.println("delete "+op.toString());
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
