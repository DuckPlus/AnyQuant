package ui.controller.candleStick;

import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 *
 * @author ss
 * @date 2016年4月10日
 */
public class CandleStickThreadHelper {
	public static Task createInitWorker() {
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

	public static Task createUpdateDayChartWorker() {
		return new Task() {
			@Override
			protected Object call() throws Exception {
				// on the worker thread...
			//	updateDay();
				Platform.runLater(() -> {

					// on the JavaFX Application Thread....
					System.out.println("done updateCharts");
					// removeProgressIndicator();
				//	dayChart = CandleStickChart.createChart(dayList);
				});
				return true;
			}
		};
	}

	public static Task createUpdateWeekChartWorker() {
		return new Task() {
			@Override
			protected Object call() throws Exception {
				// on the worker thread...
				//updateWeek();
				Platform.runLater(() -> {

					// on the JavaFX Application Thread....
					System.out.println("done updateCharts");
					// removeProgressIndicator();
				//	weekChart = CandleStickChart.createChart(weekList);
				});
				return true;
			}
		};
	}

	public static Task createUpdateMonthChartWorker() {
		return new Task() {
			@Override
			protected Object call() throws Exception {
				// on the worker thread...
				//updateMonth();
				Platform.runLater(() -> {

					// on the JavaFX Application Thread....
					System.out.println("done updateCharts");
					// removeProgressIndicator();
					//monthChart = CandleStickChart.createChart(monthList);
				});
				return true;
			}
		};
	}
}
