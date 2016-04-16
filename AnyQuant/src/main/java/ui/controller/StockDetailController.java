package ui.controller;


import java.time.LocalDate;

import blimpl.OptionalStockBLImpl;
import blservice.OptionalStockBLService;
import enumeration.Candle_Type;
import enumeration.MyDate;
import enumeration.PanelType;
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
import util.DateCalculator;
import util.TipsDialog;
import util.candleStick.CandleStickThreadHelper;
import util.candleStick.ProgressIndicatorHelper;
import util.candleStick.TimeSharingChart;
import vo.Stock;
/**
 * @status:code reviewed
 * @handler:dsn
 * @reviewed date:2016-4-14
 */
public class StockDetailController {


/********************************
 * 三个k线图、成交量图的更新~~~，在用户选择日期时调用
 * ***********************
 */
	@FXML
	private  void updateDayChart(){
		MyDate start = new MyDate(dayStart.getValue().getYear(),dayStart.getValue().getMonthValue(),dayStart.getValue().getDayOfMonth());
		MyDate end = new MyDate(dayEnd.getValue().getYear(),dayEnd.getValue().getMonthValue(),dayEnd.getValue().getDayOfMonth());
		if(! (DateCalculator.ifEarlier(start, end) )){
			new TipsDialog("起始时间不能晚于或等于终止时间");
			return;
		}

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
		if(! (DateCalculator.ifEarlier(start, end) )){
			new TipsDialog("起始时间不能晚于或等于终止时间");
			return;
		}
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
		if(! (DateCalculator.ifEarlier(start, end) )){
			new TipsDialog("起始时间不能晚于或等于终止时间");
			return;
		}
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
		if(! (DateCalculator.ifEarlier(start, end) )){
			new TipsDialog("起始时间不能晚于或等于终止时间");
			return;
		}
		Task updateDealTask=CandleStickThreadHelper.
		          createUpdateDealAmountInitWorker(currentStock, start, end);
		ProgressIndicatorHelper.showProgressIndicator(updateDealTask.progressProperty(),
				updateDealTask.runningProperty(), dealAmountIndicator, dealCachePane);
		new Thread(updateDealTask).start();
	}
	/**
	 * 返回到上一级界面~
	 */
	@FXML
	private void back(){
		if(parentPanelType==PanelType.OPTIONAL_STOCK){
			rightPaneController.showOptionalStockPane();
		}else if(parentPanelType==PanelType.STOCK_LIST){
			rightPaneController.showStockListPane();
		}
	}
	/**
	 * 添加到自选股
	 */
	@FXML
 	private void addOptionalStock() {
		if (currentStock == null)
			return;
		if (exist) {// 执行删除操作
			if (optionBl.deleteStockCode(stockCode)) {// 删除成功
				addBtn.setText("加入自选股");
				exist = false;
			} else {
				addBtn.setText("删除失败");//
			}
		} else {// 执行增加操作
			if (optionBl.addStockCode(stockCode)) {// 添加成功
				addBtn.setText("删除该自选股");
				exist = true;
			} else {
				addBtn.setText("加入失败");
			}
		}

	}
/***************************************************************
 *
 * 下面都是初始化
 *
 * *************************************************************
 */
	/**
	 * 全局初始化
	 */
	@FXML
	private void initialize() {
		dayEnd.setValue(LocalDate.now());
		dayStart.setValue(LocalDate.now());
		weekStart.setValue(LocalDate.now());
		weekEnd.setValue(LocalDate.now());
		monthStart.setValue(LocalDate.now());
		monthEnd.setValue(LocalDate.now());
	}
	/**
	 * 由上一级界面调用  进一步初始化界面
	 * @param stock
	 * @param panelType
	 */
	public void setData(Stock stock,PanelType panelType) {
		parentPanelType=panelType;
		currentStock = stock;
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
		initDealAmount();
	}


	/**
	 *初始化成交量的图
	 */
	@FXML
	private void initDealAmount() {
		//datepicker初始化
		dealStart.setValue(LocalDate.now());
		dealEnd.setValue(LocalDate.now());

		Task initdealTask = CandleStickThreadHelper.createDealAmountInitWorker(currentStock);
		ProgressIndicatorHelper.showProgressIndicator(initdealTask.progressProperty(),
       		initdealTask.runningProperty(), dealAmountIndicator,dealCachePane);
		new Thread(initdealTask).start();
	}
	/**
	 * 初始化分时图
	 * 注掉的部分不要删啦     是为了节省访问API次数~
	 */
	private void initTimeSharing() {
		TimeSharingChart timeChart = new TimeSharingChart(currentStock);
		timeSharing.setContent(timeChart.getTimeSharingChart());
//		Task initTimeSharingTask = CandleStickThreadHelper.createTimeSharingInitWorker(currentStock);
//		ProgressIndicatorHelper.showProgressIndicator(initTimeSharingTask.progressProperty(),
//        		 initTimeSharingTask.runningProperty(), timeSharingIndicator, timeSharingCachePane);
//		new Thread(initTimeSharingTask).start();
	}
	/**
	 * 初始化3个k线图
	 */
	private void initKLine() {
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



	/**
	 * 别这么构造！除非你想搞个大新闻-o-
	 */
	public StockDetailController() {
		if (instance == null) {
			instance = this;
		}
	}
	/**
	 *单例模式[但由于构造器变为private的话fxml就找不到。。]
	 */
	public static StockDetailController getStockDetailController() {
		if (instance == null) {
			instance = new StockDetailController();
		}
		return instance;
	}

	/**
	 * 变量~
	 */
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
	private RightPaneController rightPaneController = RightPaneController.getRightPaneController();
	private static StockDetailController instance;
}
