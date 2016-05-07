package data.DBHelper;

import java.util.List;

import data.StockDSImpl;
import enumeration.MyDate;
import po.StockPO;
import util.DateCalculator;

/**
 *
 * @author ss
 * @date 2016年4月22日
 */
public class DBMain {

	public static void main(String args[]) {
		DataBaseHelper helper = new DataBaseHelper();

		// helper.initStocks();

		// helper.initBenches();
		// helper.insetStockData("000001", "2016-5-4", 0, 0, 0, 0, 0, 0, 0, 0,
		// 0, 0, 0, 00, 0, 0, 0);
		// helper.initStockData();
		//helper.initBenchData();
	//	 helper.insertBenchData("11110001", "2016-5-4", 100, 100, 100, 100, 0,
	//	 0, 0, 0, 0);
		
		
		 StockDSImpl stockDSImpl = (StockDSImpl) StockDSImpl.getStockDSImpl();
 	    MyDate  start = new MyDate(2006, 01, 01);
 	    MyDate  end  =  DateCalculator.getToDay();
 	   List<StockPO>  pos = stockDSImpl.getStockMes("sh600000", start, end);
 	   helper.insertStockDataList(pos);
		 
	}

}
