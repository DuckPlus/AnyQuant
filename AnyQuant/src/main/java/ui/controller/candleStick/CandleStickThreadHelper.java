package ui.controller.candleStick;



import javax.sound.midi.ControllerEventListener;


import org.python.modules.itertools.combinations;

import enumeration.MyDate;
import enumeration.Worker_Type;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import jnr.ffi.Struct.int16_t;
import ui.controller.CandleStickController;
import ui.controller.StockDetailController;

/**
 *
 * @author ss
 * @date 2016年4月10日
 */
public class CandleStickThreadHelper {

    private static CandleStickController  candleController = CandleStickController.getCandleStickController();
    private static StockDetailController stockDetailController = StockDetailController.getStockDetailController();

	public static Task createInitWorker(Worker_Type type , String code) {
       candleController.SetStockCode(code);

		switch (type) {

		case initDayChart:
			return new Task() {
				@Override
				protected Object call() throws Exception {
					// on the worker thread...
					 candleController.getDayData();
					Platform.runLater(() -> {
						// on the JavaFX Application Thread....
                        candleController.dayChart= CandleStickChart.createChart(candleController.dayList);
                    	if(stockDetailController.dayHBox.getChildren().size()!=0){
							stockDetailController.dayHBox.getChildren().clear();
						}
                    	HBox.setHgrow(candleController.dayChart,Priority.ALWAYS);
                        stockDetailController.dayHBox.getChildren().add(candleController.dayChart);
						System.out.println("done init Charts");
					});
					return true;
				}
			};

		case initWeekChart:
			return new Task() {
				@Override
				protected Object call() throws Exception {
					// on the worker thread...
					candleController.getWeekData();
					Platform.runLater(() -> {
						// on the JavaFX Application Thread....
						candleController.weekChart= CandleStickChart.createChart(candleController.weekList);
						if(stockDetailController.weekHBox.getChildren().size()!=0){
							stockDetailController.weekHBox.getChildren().clear();
						}
						HBox.setHgrow(candleController.weekChart,Priority.ALWAYS);
                        stockDetailController.weekHBox.getChildren().add(candleController.weekChart);
						System.out.println("done init Charts");
						// removeProgressIndicator();

					});
					return true;
				}
			};
		case initMonthChart:
			return new Task() {
				@Override
				protected Object call() throws Exception {
					// on the worker thread...
					candleController.getMonthData();
					Platform.runLater(() -> {
						// on the JavaFX Application Thread....
						candleController.monthChart= CandleStickChart.createChart(candleController.monthList);
						if(stockDetailController.monthHBox.getChildren().size()!=0){
							stockDetailController.monthHBox.getChildren().clear();
						}
						HBox.setHgrow(candleController.monthChart,Priority.ALWAYS);
                        stockDetailController.monthHBox.getChildren().add(candleController.monthChart);
						System.out.println("done init Charts");
						// removeProgressIndicator();

					});
					return true;
				}
			};
		default:
			return null;

		}
	}

	public static Task createUpdateChartWorker(Worker_Type type, String code, MyDate start, MyDate end) {
		candleController.SetStockCode(code);
		candleController.SetStartDate(start);
		candleController.SetEndDate(end);

		switch (type) {
		case updateDayChart:
			return new Task() {
				@Override
				protected Object call() throws Exception {
					// on the worker thread...
					 candleController.getDayDataByDate();
					Platform.runLater(() -> {

						// on the JavaFX Application Thread....
						candleController.dayChart= CandleStickChart.createChart(candleController.dayList);
						if(stockDetailController.dayHBox.getChildren().size()!=0){
							stockDetailController.dayHBox.getChildren().clear();
						}
						HBox.setHgrow(candleController.dayChart,Priority.ALWAYS);
                        stockDetailController.dayHBox.getChildren().add(candleController.dayChart);
						System.out.println("done updateCharts");

					});
					return true;
				}
			};

		case updateWeekChart:
			return new Task() {
				@Override
				protected Object call() throws Exception {
					// on the worker thread...
					candleController.getWeekDataByDate();
					Platform.runLater(() -> {

						// on the JavaFX Application Thread....
						candleController.weekChart= CandleStickChart.createChart(candleController.weekList);
						if(stockDetailController.weekHBox.getChildren().size()!=0){
							stockDetailController.weekHBox.getChildren().clear();
						}
						HBox.setHgrow(candleController.weekChart,Priority.ALWAYS);
                        stockDetailController.weekHBox.getChildren().add(candleController.weekChart);
						System.out.println("done updateCharts");

					});
					return true;
				}
			};

		case updateMonthChart:
			return new Task() {
				@Override
				protected Object call() throws Exception {
					// on the worker thread...
					candleController.getMonthDataByDate();
					Platform.runLater(() -> {

						// on the JavaFX Application Thread....
						candleController.monthChart= CandleStickChart.createChart(candleController.monthList);
						if(stockDetailController.monthHBox.getChildren().size()!=0){
							stockDetailController.monthHBox.getChildren().clear();
						}
						HBox.setHgrow(candleController.monthChart,Priority.ALWAYS);
                        stockDetailController.monthHBox.getChildren().add(candleController.monthChart);
						System.out.println("done updateCharts");

					});
					return true;
				}
			};
		default:
			return null;
		}

	}

}
