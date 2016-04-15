/**
 * @author:dsn
 * @date:2016年4月14日
 */
package util.candleStick.task;

import javafx.application.Platform;
import javafx.concurrent.Task;
import ui.controller.StockDetailController;
import util.candleStick.TimeSharingChart;
import vo.Stock;

public class TimeChart_Task extends Task{
	private Stock stock;
	private StockDetailController stock_ctr;
	public TimeChart_Task(Stock stock,StockDetailController stock_ctr) {
		this.stock=stock;
	}

	@Override
	protected Object call() throws Exception {
		// on the worker thread...
		TimeSharingChart timeChart = new TimeSharingChart(stock);
		Platform.runLater(() -> {
			// on the JavaFX Application Thread....
            stock_ctr.timeSharingSPane.setContent(timeChart.getTimeSharingChart());
		});
		return true;
	}
}
