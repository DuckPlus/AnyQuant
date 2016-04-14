/**
 * @author:dsn
 * @date:2016年4月14日
 */
package util.candleStick.task;

import javafx.concurrent.Task;
import ui.controller.CandleStickController;
import ui.controller.StockDetailController;
import util.DealChart;

public class MyKTask extends Task{
	protected CandleStickController k_ctr;
	protected StockDetailController stock_ctr;
	protected Boolean ifDefault;
	public MyKTask(Boolean ifDefault,CandleStickController k_ctr,StockDetailController stock_ctr){
		this.ifDefault=ifDefault;
		this.k_ctr=k_ctr;
		this.stock_ctr=stock_ctr;
	}

	@Override
	protected Object call() throws Exception {
		return null;
	}

}
