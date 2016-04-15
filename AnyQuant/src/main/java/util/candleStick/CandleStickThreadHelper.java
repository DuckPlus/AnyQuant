package util.candleStick;



import enumeration.Candle_Type;
import enumeration.MyDate;
import javafx.application.Platform;
import javafx.concurrent.Task;
import ui.controller.CandleStickController;
import ui.controller.StockDetailController;
import util.DealChart;
import util.candleStick.task.Day_KChart_Task;
import util.candleStick.task.DealChart_Task;
import util.candleStick.task.Month_KChart_Task;
import util.candleStick.task.TimeChart_Task;
import util.candleStick.task.Week_KChart_Task;
import vo.Stock;

/**
 *@status:code reviewed
 *@handler:dsn
 *@reviewed date:2016-4-14
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
			return new Day_KChart_Task(true,candleController,stockDetailController);
		case week:
			return new Week_KChart_Task(true,candleController, stockDetailController);
		case month:
			return new Month_KChart_Task(true,candleController, stockDetailController);
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
			return new Day_KChart_Task(false,candleController,stockDetailController);
		case week:
			return new Week_KChart_Task(false,candleController, stockDetailController);
		case month:
			return new Month_KChart_Task(false,candleController, stockDetailController);
		default:
			return null;
		}
	}
	public static Task createDealAmountInitWorker(Stock stock){
		DealChart barChart = new DealChart(stock);
		return new DealChart_Task(barChart, stockDetailController);

	}
	public static Task createUpdateDealAmountInitWorker(Stock stock,MyDate start,MyDate end){
		DealChart barChart = new DealChart(stock,start,end);
		return new DealChart_Task(barChart, stockDetailController);
	}

	public static Task createTimeSharingInitWorker(Stock stock){
		return new TimeChart_Task(stock, stockDetailController);
	}
}

