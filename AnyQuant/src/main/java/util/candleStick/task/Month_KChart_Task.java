/**
 * @author:dsn
 * @date:2016年4月14日
 */
package util.candleStick.task;

import javafx.application.Platform;
import ui.controller.CandleStickController;
import ui.controller.StockDetailController;
import util.candleStick.CandleStickChart;

public class Month_KChart_Task extends MyKTask{

	@Override
	protected Object call() throws Exception {
		// on the worker thread...
		if(ifDefault){
			k_ctr.getDefaultMonthData();
		}else k_ctr.getMonthDataByDate();
		Platform.runLater(() -> {
			// on the JavaFX Application Thread....
			k_ctr.monthChart= CandleStickChart.createChart(k_ctr.monthList);
            stock_ctr.monthSPane.setContent(k_ctr.monthChart);
			// removeProgressIndicator();

		});
		return true;
	}
	public Month_KChart_Task(Boolean ifDefault, CandleStickController k_ctr, StockDetailController stock_ctr) {
		super(ifDefault, k_ctr, stock_ctr);
	}


}
