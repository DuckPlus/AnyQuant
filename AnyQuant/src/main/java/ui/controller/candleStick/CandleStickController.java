package ui.controller.candleStick;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.python.antlr.PythonParser.return_stmt_return;

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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.MyTime;
import vo.OHLC_VO;

/**
 *
 * @author ss
 * @date 2016年3月24日 =。=
 */
public class CandleStickController implements Initializable {

	@FXML
	private VBox vbox;
	@FXML
	private StackPane stackPane;
	@FXML
	private GridPane cachePane;

	private ProgressIndicator progressIndicator;
	@FXML
	private CandleStickChart dayChart, weekChart, monthChart;
	private ObservableList<OHLC_VO> dayList, weekList, monthList;
	private String stockCode;
	private MyDate startDate, endDate;


	private static StockBLService stockBl;
	private static CandleStickController instance;

	public CandleStickController() {
		if (instance == null) {
			stockCode = "sh600000";
			startDate = MyTime.getToDay();
			endDate = startDate;
			stockBl = StockBLImpl.getAPIBLService();
			dayList = FXCollections.observableArrayList();
			weekList = FXCollections.observableArrayList();
			monthList = FXCollections.observableArrayList();
			dayList.clear();
			weekList.clear();
			monthList.clear();
			instance = this;
		}
	}

	public static CandleStickController getCandleStickController() {
		if (instance != null) {
			return instance;
		} else {
			return new CandleStickController();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}


	public Node getInitialDayChart(String code) {
		this.stockCode = code;
		selectDay();
        dayChart = CandleStickChart.createChart(dayList);
		return dayChart;
	}

	public Node getInitialWeekChart(String code){
	    this.stockCode=code;
	    selectWeek();
	    weekChart= CandleStickChart.createChart(weekList);
	    return weekChart;
	}

	public Node getInitialMonthChart(String code){
       this.stockCode=code;
       selectMonth();
       monthChart=CandleStickChart.createChart(monthList);
       return monthChart;
	}

	// set date to update charts
	public Node getUpdatedDayChart(String code, MyDate start, MyDate end) {
		this.stockCode = code;
		this.startDate = start;
		this.endDate = end;
	//	Task updateTask = createUpdateDayChartWorker();
	//	 showProgressIndicator(
	//	 updateTask.progressProperty(),updateTask.runningProperty());
   //		new Thread(updateTask).start();
      updateDay();
      dayChart = CandleStickChart.createChart(dayList);

		return dayChart;

	}


	public Node getUpdatedWeekChart(String code, MyDate start, MyDate end) {
		this.stockCode = code;
		this.startDate = start;
		this.endDate = end;
	//	Task updateTask = createUpdateWeekChartWorker();
		// showProgressIndicator(
		// updateTask.progressProperty(),updateTask.runningProperty());
	//	new Thread(updateTask).start();
		updateWeek();
        weekChart=CandleStickChart.createChart(weekList);
		return weekChart;

	}


	public Node getUpdatedMonthChart(String code, MyDate start, MyDate end) {
		this.stockCode = code;
		this.startDate = start;
		this.endDate = end;
	//	Task updateTask = createUpdateMonthChartWorker();
		// showProgressIndicator(
		// updateTask.progressProperty(),updateTask.runningProperty());
	//	new Thread(updateTask).start();
		updateMonth();
		monthChart=CandleStickChart.createChart(monthList);
		return dayChart;

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

	// private Node createPane( Node chartNode, ScrollPane spane ){
	// //RowConstraints rc = new RowConstraints(500, 690, 690);
	// //ColumnConstraints cc = new ColumnConstraints(800,900,900);
	// HBox hBox = new HBox();
	// HBox.setHgrow(chartNode, Priority.ALWAYS);
	// hBox.setPrefSize(800, 600);
	// hBox.getChildren().add(chartNode);
	//
	//
	// spane.setContent(hBox);
	// spane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
	// spane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
	// return spane;
	// }

	private void getDayData() {
		// 默认显示近一个月的数据
		MyDate end = MyTime.getToDay();
		MyDate start = MyTime.getAnotherDay(-30);
		List<OHLC_VO> list = stockBl.getDayOHLC_Data(stockCode, start, end);
		dayList.clear();
		for (OHLC_VO temp : list) {
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

	private void getWeekDataByDate() {
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

	private void getMonthDataByDate() {
		if (startDate != null && endDate != null) {
			List<OHLC_VO> list = stockBl.getMonthOHLC_Data(stockCode, startDate, endDate);
			monthList.clear();
			for (OHLC_VO temp : list) {
				monthList.add(temp);
			}
		}
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
			ObservableValue<? extends Boolean> runningProperty) {
		progressIndicator.setVisible(true);
		progressIndicator.progressProperty().unbind();
		progressIndicator.progressProperty().bind(progressProperty);
		cachePane.visibleProperty().bind(runningProperty);

	}

	private void removeProgressIndicator() {
		progressIndicator.setVisible(false);
	}

	private Task createInitWorker() {
		return new Task() {
			@Override
			protected Object call() throws Exception {
				// on the worker thread...
				//getInitData();
				Platform.runLater(() -> {

					// on the JavaFX Application Thread....
					System.out.println("done init Charts");
					// removeProgressIndicator();

				});
				return true;
			}

			@Override
            protected void succeeded() {
                super.succeeded();

            }

		};
	}

	private Task createUpdateDayChartWorker() {
		return new Task() {
			@Override
			protected Object call() throws Exception {
				// on the worker thread...
				updateDay();
				Platform.runLater(() -> {

					// on the JavaFX Application Thread....
					System.out.println("done updateCharts");
					// removeProgressIndicator();
					dayChart = CandleStickChart.createChart(dayList);
				});
				return true;
			}
		};
	}

	private Task createUpdateWeekChartWorker() {
		return new Task() {
			@Override
			protected Object call() throws Exception {
				// on the worker thread...
				updateWeek();
				Platform.runLater(() -> {

					// on the JavaFX Application Thread....
					System.out.println("done updateCharts");
					// removeProgressIndicator();
					weekChart = CandleStickChart.createChart(weekList);
				});
				return true;
			}
		};
	}

	private Task createUpdateMonthChartWorker() {
		return new Task() {
			@Override
			protected Object call() throws Exception {
				// on the worker thread...
				updateMonth();
				Platform.runLater(() -> {

					// on the JavaFX Application Thread....
					System.out.println("done updateCharts");
					// removeProgressIndicator();
					monthChart = CandleStickChart.createChart(monthList);
				});
				return true;
			}
		};
	}

}
