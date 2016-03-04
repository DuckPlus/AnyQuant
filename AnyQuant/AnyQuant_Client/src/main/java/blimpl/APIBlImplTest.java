package blimpl;

import java.util.Iterator;

import enumeration.Stock_Attribute;
import vo.StockVO;
import blservice.APIBlservice;
import junit.framework.TestCase;

/**
 *
 * @author czq
 * @date 2016年3月4日
 */
public class APIBlImplTest extends TestCase {
	
	APIBlservice api;
	
	protected void setUp() throws Exception {
		super.setUp();
		api = APIBlImpl.getAPIBLService();
	}

	public void testGetAPIBLService() {
		
	}

	public void testGetAllStocks() {
		Iterator<StockVO> stocks = api.getAllStocks();
		while(stocks.hasNext()){
			System.out.println(stocks.next().high);
		}
	}

	public void testGetSortStocks() {
		double rate = -100;
		Iterator<StockVO> stocksWithZhangFU = api.getSortStocks(true, Stock_Attribute.changeRate);
		while(stocksWithZhangFU.hasNext()){
			System.out.println(stocksWithZhangFU.next().changeRate);
		}
		
		
		
		
	}

//	public void testGetSortStocksInScope() {
//		fail("Not yet implemented");
//	}
//
//	public void testGetAllBenchMarks() {
//		fail("Not yet implemented");
//	}
//
//	public void testGetRecentStocks() {
//		fail("Not yet implemented");
//	}
//
//	public void testGetStocksByTime() {
//		fail("Not yet implemented");
//	}
//
//	public void testGetStocksByStockCode() {
//		fail("Not yet implemented");
//	}

}
