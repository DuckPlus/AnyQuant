/**
 * @author:dsn
 * @date:2016年4月14日
 */
package util.candleStick.task;

import javafx.application.Platform;
import ui.controller.CandleStickController;
import ui.controller.StockDetailController;
import util.candleStick.CandleStickChart;

public class Week_KChart_Task extends MyKTask{

	@Override
	protected Object call() throws Exception {
		// on the worker thread...
		if(ifDefault){
			k_ctr.getDefaultWeekData();
		}else k_ctr.getWeekDataByDate();
		Platform.runLater(() -> {
			// on the JavaFX Application Thread....
			k_ctr.weekChart= CandleStickChart.createChart(k_ctr.weekList);
            stock_ctr.weekSPane.setContent(k_ctr.weekChart);
			// removeProgressIndicator();
		});
		return true;
	}
	public Week_KChart_Task(Boolean ifDefault, CandleStickController k_ctr, StockDetailController stock_ctr) {
		super(ifDefault, k_ctr, stock_ctr);
	}
}
