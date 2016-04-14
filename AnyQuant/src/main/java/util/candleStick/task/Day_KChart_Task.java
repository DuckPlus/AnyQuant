/**
 * @author:dsn
 * @date:2016年4月14日
 */
package util.candleStick.task;

import javafx.application.Platform;
import ui.controller.CandleStickController;
import ui.controller.StockDetailController;
import util.candleStick.CandleStickChart;

public class Day_KChart_Task extends MyKTask{
	@Override
	protected Object call() throws Exception {
		// on the worker thread...
		if(ifDefault){
			k_ctr.getDefaultDayData();
		}else k_ctr.getDayDataByDate();
		Platform.runLater(() -> {
			// on the JavaFX Application Thread....
            k_ctr.dayChart= CandleStickChart.createChart(k_ctr.dayList);
            stock_ctr.daySPane.setContent(k_ctr.dayChart);
		});
		return true;
	}
	public Day_KChart_Task(Boolean ifDefault, CandleStickController k_ctr, StockDetailController stock_ctr) {
		super(ifDefault, k_ctr, stock_ctr);
	}

}
