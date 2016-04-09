package ui.controller.candleStick;

import java.net.URL;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import blimpl.StockBLImpl;
import blservice.StockBLService;
import enumeration.MyDate;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.MyTime;
import vo.OHLC_VO;
import vo.TimeSharingVO;



/**
 *
 * @author ss
 * @date 2016年3月24日
 * =。=
 */
public class CandleStickController  implements Initializable {
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab dayTab;
    @FXML
    private Tab weekTab;
    @FXML
    private Tab monthTab;
    @FXML
    private Tab timeShareTab;
    @FXML
    private AnchorPane bottomPane;
    @FXML
    private VBox vbox;
    @FXML
    private StackPane stackPane;
    @FXML
    private GridPane cachePane;

	@FXML
	private DatePicker startDatePicker;
	@FXML
	private DatePicker endDatePicker;
	@FXML

	private ProgressIndicator progressIndicator;
	@FXML
	private CandleStickChart dayChart, weekChart, monthChart;
    private ObservableList<OHLC_VO> dayList,weekList,monthList;
	private static String stockCode;
	private static MyDate startDate, endDate;
	private static StockBLService stockBl;
  	private static CandleStickController instance;

	public CandleStickController() {
		if (instance == null) {
			System.err.println("instance null");
			stockCode = "sh600000";
			startDate = new MyDate(2016, 3, 1);
			endDate = new MyDate(2016, 4, 1);
			stockBl = StockBLImpl.getAPIBLService();
			dayList = FXCollections.observableArrayList();
			weekList = FXCollections.observableArrayList();
			monthList = FXCollections.observableArrayList();
			dayList.clear();  weekList.clear(); monthList.clear();
			instance = this;
		}
	}

    public static CandleStickController  getCandleStickController(){
             if(instance!=null){
            	 return instance;
             }else{
            	 return new CandleStickController();
             }
    }

    public void setStockCode(String  newCode){

          stockCode = newCode;

    }

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {

    	startDatePicker.setValue(LocalDate.now());
		endDatePicker.setValue(LocalDate.now());
		startDatePicker.setEditable(false);
		endDatePicker.setEditable(false);
		initCharts();
    }
    //double click to enter this scene
  public void initCharts(){
		Task initTask = createInitWorker();
		showProgressIndicator( initTask.progressProperty(),initTask.runningProperty());
		new Thread(initTask).start();
  }

  //set date  to update charts
  @FXML
public void updateCharts(){
       startDate = new MyDate
			   (startDatePicker.getValue().getYear(), startDatePicker.getValue().getMonthValue(), startDatePicker.getValue().getDayOfMonth());
		endDate  = new MyDate
				(endDatePicker.getValue().getYear(), endDatePicker.getValue().getMonthValue(), endDatePicker.getValue().getDayOfMonth());

		Task updateTask = createUpdateWorker();
		showProgressIndicator( updateTask.progressProperty(),updateTask.runningProperty());
		new Thread( updateTask).start();
}




	public void displayCharts() {
		dayChart = createChart(dayList);
		weekChart = createChart(weekList);
		monthChart = createChart(monthList);
		initPane(dayTab, dayChart, new ScrollPane());
		initPane(weekTab, weekChart, new ScrollPane());
		initPane(monthTab, monthChart, new ScrollPane());
	}

  public void  prepareInitCharts(){
	  selectDay();
	  selectWeek();
	  selectMonth();
  }


  public void  prepareUpdateCharts(){
	updateDay();
	updateWeek();
	updateMonth();
  }

	public void selectDay() {
		if (dayChart == null) {
			getDayData();
		}
	}

	public void selectWeek() {
		if (weekChart == null) {
			getWeekData();
		}
	}

	public void selectMonth() {
		if (monthChart == null) {
			getMonthData();
		}
	}


	public void updateDay() {

		getDayDataByDate();
	}





	public void updateWeek() {

		getWeekDataByDate();
	}

	public void updateMonth() {

		getMonthDataByDate();
	}

    private void initPane( Tab tab  , Node chartNode, ScrollPane spane ){
    	 //RowConstraints  rc = new RowConstraints(500, 690, 690);
    	//ColumnConstraints cc = new ColumnConstraints(800,900,900);
          HBox hBox = new HBox();
          HBox.setHgrow(chartNode, Priority.ALWAYS);
          hBox.setPrefSize(800, 600);
          hBox.getChildren().add(chartNode);


	      spane.setContent(hBox);
	      spane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
	      spane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
          tab.setContent(spane);
  }

    private  void  getDayData(){
           //默认显示近一个月的数据
    	   MyDate end = MyTime.getToDay();
    	   MyDate start = MyTime.getAnotherDay(-30);
    	   List<OHLC_VO>list =stockBl.getDayOHLC_Data(stockCode, start, end);
    	   if(dayList==null)System.err.println("null day list");
    	   dayList.clear();
    	   for(OHLC_VO temp : list){
    		     dayList.add(temp);
    	   }
    }
    private  void  getDayDataByDate(){
		if (startDate != null && endDate != null) {
			List<OHLC_VO> list = stockBl.getDayOHLC_Data(stockCode, startDate, endDate);
			dayList.clear();
			for (OHLC_VO temp : list) {
				dayList.add(temp);
			}
		}
 }

	private void getWeekData() {
		// 默认显示最近一年的数据
		MyDate end = MyTime.getToDay();
		MyDate start = MyTime.getAnotherDay(-180);
		List<OHLC_VO> list = stockBl.getWeekOHLC_Data(stockCode, start, end);
		weekList.clear();
		for (OHLC_VO temp : list) {
			weekList.add(temp);
		}
	}


    private  void  getWeekDataByDate(){
		if (startDate != null && endDate != null) {
			List<OHLC_VO> list = stockBl.getWeekOHLC_Data(stockCode, startDate, endDate);
			weekList.clear();
			for (OHLC_VO temp : list) {
				weekList.add(temp);
			}
		}
    }

	private void getMonthData() {
		// 默认显示最近三年的数据
		MyDate end = MyTime.getToDay();
		MyDate start = MyTime.getAnotherDay(-365 * 2);
		List<OHLC_VO> list = stockBl.getMonthOHLC_Data(stockCode, start, end);
		monthList.clear();
		for (OHLC_VO temp : list) {
			monthList.add(temp);
		}
	}

    private  void  getMonthDataByDate(){
		if (startDate != null && endDate != null) {
			List<OHLC_VO> list = stockBl.getMonthOHLC_Data(stockCode, startDate, endDate);
			monthList.clear();
			for (OHLC_VO temp : list) {
				monthList.add(temp);
			}
		}
    }

    private CandleStickChart createChart(ObservableList<OHLC_VO> obsevableList) {
    	//System.out.println(getMax(obsevableList)+"    "+getMin(obsevableList));
    	double gap=(getMax(obsevableList)-getMin(obsevableList))/10;
    	//X轴
        final CategoryAxis xAxis = new CategoryAxis ();
        //Y轴
        final NumberAxis yAxis = new NumberAxis(getMin(obsevableList)-gap,getMax(obsevableList)+gap*2,gap);
        final CandleStickChart candleStickChart = new CandleStickChart(xAxis,yAxis);
        // setup chart
       // candleStickChart.setTitle("Custom Candle Stick Chart");
        xAxis.setLabel("Day");
        yAxis.setLabel("Price");
        // add starting data
        XYChart.Series<String,Number> series = new XYChart.Series<String,Number>();
        for (int i=0; i< obsevableList.size(); i++) {
            OHLC_VO vo = obsevableList.get(i);
                //参数：日期、开盘价
             series.getData().add(   new XYChart.Data<String,Number>
                (vo.date.DateToString(),vo.open,new CandleStickExtraValues(vo.close,vo.high,vo.low,vo.close)));
        }
        // candleStickChart.getData()  return type:ObservableList<XYChart.Series<Number,Number>>
        ObservableList<XYChart.Series<String,Number>> data = candleStickChart.getData();
        if (data == null) {

            data = FXCollections.observableArrayList(series);
            candleStickChart.setData(data);
        } else {
            candleStickChart.getData().add(series);
        }
        return candleStickChart;
    }


	/*
	 * Create a progress indicator control to be centered.
	 *
	 * @param scene The primary application scene.
	 *
	 * @return ProgressIndicator a new progress indicator centered.
	 */
	private ProgressIndicator createProgressIndicator() {
		ProgressIndicator progress = new ProgressIndicator(0);
		progress.setVisible(false);
		return progress;
	}

	private void showProgressIndicator(ObservableValue<? extends Number> progressProperty,
			ObservableValue<? extends Boolean> runningProperty          ){
		progressIndicator.setVisible(true);
		progressIndicator.progressProperty().unbind();
		progressIndicator.progressProperty().bind( progressProperty);
		cachePane.visibleProperty().bind(runningProperty);

	}

	private void  removeProgressIndicator(){
		progressIndicator.setVisible(false);
	}

	private Task createInitWorker() {
		return new Task() {
			@Override
			protected Object call() throws Exception {
				// on the worker thread...
				prepareInitCharts();
				Platform.runLater(() -> {

					// on the JavaFX Application Thread....
					System.out.println("done init Charts");
					removeProgressIndicator();
					displayCharts();
				});
				return true;
			}
		};
	}


	private Task createUpdateWorker() {
		return new Task() {
			@Override
			protected Object call() throws Exception {
				// on the worker thread...
			    prepareUpdateCharts();
				Platform.runLater(() -> {

					// on the JavaFX Application Thread....
					System.out.println("done updateCharts");
					removeProgressIndicator();
					displayCharts();
				});
				return true;
			}
		};
	}


	private double getMin(ObservableList<OHLC_VO>  obsevableList) {
		double min = 100;
		for (OHLC_VO temp : obsevableList) {
			if (temp.low < min) {
				min = temp.low;
			}
		}
		return min;
	}


	private double getMax(ObservableList<OHLC_VO>  obsevableList) {
		double max = 0;
		for (OHLC_VO temp : obsevableList) {
			if (temp.high > max) {
				max = temp.high;
			}
		}
		return max;
	}


    /**
     * 以下是画分时图的部分
     * @author dzm
     * @date 2016-04-07
     */
    @FXML
    private void initTimeSharing(){
//    	TimeSharingChart timeSharingChart = new TimeSharingChart();
//    	initPane(timeShareTab, timeSharingChart.getTimesharingChart(), new ScrollPane());
//    	List<TimeSharingVO> timeSharingData = stockBl.getSharingVOs("sh600000");
//    	//get the upperBounds and lowerBounds
//    	Iterator<TimeSharingVO> itrc = timeSharingData.iterator();
//    	double upperBounds=0;
//    	double lowerBounds=10000;
//    	while(itrc.hasNext()){
//    		TimeSharingVO tempCmp = itrc.next();
//    		if(tempCmp.nowPrice>upperBounds)upperBounds = tempCmp.nowPrice;
//    		if(tempCmp.nowPrice<lowerBounds)lowerBounds = tempCmp.nowPrice;
//    	}
//    	//end
//    	timeSharingChart.setChartProperty("Stock xxx", upperBounds+0.02, lowerBounds-0.02);
//    	Iterator<TimeSharingVO>itr = timeSharingData.iterator();
//    	while(itr.hasNext()){
//    		TimeSharingVO temp = itr.next();
//    		timeSharingChart.addData(temp.nowTime.TimeToString(), temp.nowPrice);
//    	}
    }

}
