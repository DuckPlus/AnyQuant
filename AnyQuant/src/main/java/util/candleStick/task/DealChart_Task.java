/**
 * @author:dsn
 * @date:2016年4月14日
 */
package util.candleStick.task;

import javafx.application.Platform;
import javafx.concurrent.Task;
import ui.controller.StockDetailController;
import util.DealChart;

public class DealChart_Task extends Task{
	private DealChart dealchart;
	private StockDetailController stock_ctr;
	public DealChart_Task(DealChart dealchart,StockDetailController stock_ctr){
		this.dealchart=dealchart;
		this.stock_ctr=stock_ctr;
	}
	@Override
	protected Object call() throws Exception {
		// on the worker thread...
		dealchart.getData();
		Platform.runLater(() -> {
			// on the JavaFX Application Thread...
			dealchart.initChart();
			stock_ctr.dealSPane.setContent(dealchart.getBarChart());
		});
		return true;
	}

}
