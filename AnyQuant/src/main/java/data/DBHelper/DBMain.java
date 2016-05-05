package data.DBHelper;

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
		 helper.initStockData();
		//helper.initBenchData();
		// helper.insertBenchData("000001", "2016-5-4", 100, 100, 100, 100, 0,
		// 0, 0, 0, 0);
	}

}
