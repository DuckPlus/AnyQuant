package blimpl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import util.MyTime;
import vo.OHLC_VO;
import vo.StockVO;
import blservice.StockBLService;

/**
 *
 * @author czq
 * @date 2016年3月13日
 */
public class StockBLImplTest {
	StockBLService bl ;
	@Before
	public void setUp() throws Exception {
		bl  = BusinessFactory.getStockBLService();
	}

	@Test
	public void testGetAPIBLService() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllStocks() {
		fail("Not yet implemented");
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

	@Test
	public void testGetDayOHLC_Data() {
		Iterator<StockVO> vos = bl.getStocksByTime("sh600300", MyTime.getAnotherDay(-30), MyTime.getAnotherDay(0));
		List<Double> lows = new ArrayList<Double>();
		while(vos.hasNext()){
			lows.add(vos.next().low);
		}
		System.out.println("--------------------------------------");
		List<OHLC_VO> tmp = bl.getDayOHLC_Data("sh600300", MyTime.getAnotherDay(-30), MyTime.getAnotherDay(0));
		for (int i = 0; i < 15; i++) {
			if(!lows.get(i).equals(tmp.get(i).low)){
				fail();
			}
		}				
	}

	@Test
	public void testGetWeekOHLC_Data() {
		List<OHLC_VO> tmp = bl.getWeekOHLC_Data("sh600300", MyTime.getAnotherDay(-30), MyTime.getAnotherDay(0));
		System.out.println(tmp.size());
		for (OHLC_VO ohlc_VO : tmp) {
			System.out.println("Day is " + ohlc_VO.date.getDay() + " low is " + ohlc_VO.low);
		}
	}

	@Test
	public void testGetMonthOHLC_Data() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSharingVOs() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDayDealVOs() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetWeekDealVOs() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMonthDealVOs() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTodayStockVO() {
		fail("Not yet implemented");
	}

}
