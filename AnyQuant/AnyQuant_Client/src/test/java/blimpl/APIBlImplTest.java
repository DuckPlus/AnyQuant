package blimpl;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import vo.StockVO;
import blservice.APIBlservice;

/**
 *
 * @author czq
 * @date 2016年3月4日
 */
public class APIBlImplTest {
	APIBlservice api;
	Iterator<StockVO> stocks;
	@Before
	public void setUp() throws Exception {
		api = APIBlImpl.getAPIBLService();
	}

	@Test
	public void testGetAPIBLService() {
		//PASS
	}

	@Test
	public void testGetAllStocks() {
		stocks = api.getAllStocks();
		while(stocks.hasNext()){
			System.out.println(stocks.next().high);
		}
		
	}

	@Test
	public void testGetSortStocks() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSortStocksInScope() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllBenchMarks() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRecentStocks() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStocksByTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStocksByStockCode() {
		fail("Not yet implemented");
	}

}
