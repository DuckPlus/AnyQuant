package ui.controller.candleStick;



import enumeration.Candle_Type;
import enumeration.MyDate;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;
import ui.controller.CandleStickController;
import ui.controller.DealChart;
import ui.controller.StockDetailController;
import util.MyBarChart;
import vo.Stock;

/**
 *
 * @author ss
 * @date 2016年4月10日
 */
public class CandleStickThreadHelper {

    private static CandleStickController  candleController = CandleStickController.getCandleStickController();
    private static StockDetailController stockDetailController = StockDetailController.getStockDetailController();


	public static Task createKChartInitWorker(Candle_Type type , String code) {
       candleController.SetStockCode(code);

		switch (type) {

		case day:
			return new Task() {
				@Override
				protected Object call() throws Exception {
					// on the worker thread...
					 candleController.getDayData();
					Platform.runLater(() -> {
						// on the JavaFX Application Thread....
                        candleController.dayChart= CandleStickChart.createChart(candleController.dayList);
                        stockDetailController.daySPane.setContent(candleController.dayChart);

						System.out.println("done init Charts");
					});
					return true;
				}
			};

		case week:
			return new Task() {
				@Override
				protected Object call() throws Exception {
					// on the worker thread...
					candleController.getWeekData();
					Platform.runLater(() -> {
						// on the JavaFX Application Thread....
						candleController.weekChart= CandleStickChart.createChart(candleController.weekList);
                        stockDetailController.weekSPane.setContent(candleController.weekChart);
						System.out.println("done init Charts");
						// removeProgressIndicator();

					});
					return true;
				}
			};
		case month:
			return new Task() {
				@Override
				protected Object call() throws Exception {
					// on the worker thread...
					candleController.getMonthData();
					Platform.runLater(() -> {
						// on the JavaFX Application Thread....
						candleController.monthChart= CandleStickChart.createChart(candleController.monthList);
                        stockDetailController.monthSPane.setContent(candleController.monthChart);
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

	public static Task createUpdateKChartWorker(Candle_Type type, String code, MyDate start, MyDate end) {
		candleController.SetStockCode(code);
		candleController.SetStartDate(start);
		candleController.SetEndDate(end);

		switch (type) {
		case day:
			return new Task() {
				@Override
				protected Object call() throws Exception {
					// on the worker thread...
					 candleController.getDayDataByDate();
					Platform.runLater(() -> {

						// on the JavaFX Application Thread....
						candleController.dayChart= CandleStickChart.createChart(candleController.dayList);
                        stockDetailController.daySPane.setContent(candleController.dayChart);
						System.out.println("done updateCharts");

					});
					return true;
				}
			};

		case week:
			return new Task() {
				@Override
				protected Object call() throws Exception {
					// on the worker thread...
					candleController.getWeekDataByDate();
					Platform.runLater(() -> {

						// on the JavaFX Application Thread....
						candleController.weekChart= CandleStickChart.createChart(candleController.weekList);
                        stockDetailController.weekSPane.setContent(candleController.weekChart);
						System.out.println("done updateCharts");

					});
					return true;
				}
			};

		case month:
			return new Task() {
				@Override
				protected Object call() throws Exception {
					// on the worker thread...
					candleController.getMonthDataByDate();
					Platform.runLater(() -> {

						// on the JavaFX Application Thread....
						candleController.monthChart= CandleStickChart.createChart(candleController.monthList);
                        stockDetailController.monthSPane.setContent(candleController.monthChart);
						System.out.println("done updateCharts");
					});
					return true;
				}
			};
		default:
			return null;
		}
	}
	public static Task createDealAmountInitWorker(Stock stock){
		return new Task() {
			@Override
			protected Object call() throws Exception {
				// on the worker thread...
				DealChart barChart = new DealChart(stock);
				Platform.runLater(() -> {
					// on the JavaFX Application Thread....
					stockDetailController.dealSPane.setContent(barChart.getBarChart());
					barChart.addData();
				});
				return true;
			}
		};
	}
	public static Task createUpdateDealAmountInitWorker(Stock stock,MyDate start,MyDate end){
		return new Task() {
			@Override
			protected Object call() throws Exception {
				// on the worker thread...
				DealChart barChart = new DealChart(stock,start,end);
				Platform.runLater(() -> {
					// on the JavaFX Application Thread....
					stockDetailController.dealSPane.setContent(barChart.getBarChart());
					barChart.addData();
				});
				System.out.println("刷新成交量的图~");
				System.out.println(start.DateToString()+"--"+end.DateToString());
				return true;
			}
		};
	}

	public static Task createTimeSharingInitWorker(Stock stock){
		return new Task() {
			@Override
			protected Object call() throws Exception {
				// on the worker thread...
				TimeSharingChart timeChart = new TimeSharingChart(stock);

				Platform.runLater(() -> {
					// on the JavaFX Application Thread....
                    stockDetailController.timeSharingSPane.setContent(timeChart.getTimeSharingChart());
					System.out.println("done init Charts");
				});
				return true;
			}
		};
	}
}

