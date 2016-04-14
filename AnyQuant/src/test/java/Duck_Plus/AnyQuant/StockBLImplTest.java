package Duck_Plus.AnyQuant;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import enumeration.Stock_Attribute;
import util.DateCalculator;
import vo.DealVO;
import vo.OHLC_VO;
import vo.StockVO;
import blimpl.BusinessFactory;
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
	}

	@Test
	public void testGetAllStocks() {
	}

	@Test
	public void testGetSortStocks() {
		System.out.println("---------------------接下来是代码排序----------------");
//		Iterator<StockVO> stocksWithCode = bl.getAllStocks();
//		while(stocksWithCode.hasNext()){
//			System.out.println(stocksWithCode.next().code);
//		}
//		System.out.println("---------------------接下来是涨跌幅排序----------------");
//		StockVO vo;
//		Iterator<StockVO> stocksWithZhangFU = bl.getSortStocks(true, Stock_Attribute.changeRate);
//		while(stocksWithZhangFU.hasNext()){
//			vo = stocksWithZhangFU.next();
//			System.out.println(vo.changeRate );
//			if(vo.changeRate > 0.1){
//				System.out.println("该股票数据存在异常：涨跌幅大于10%");
//				System.out.println("Code:" + vo.code + " 收盘：" + vo.close + " 昨收盘："+vo.preClose );
//			}
//		}

		System.out.println("---------------------接下来是振幅排序----------------");
		Iterator<StockVO> stocksWithzhenfu = bl.getSortStocks(false, Stock_Attribute.amplitude);
		while(stocksWithzhenfu.hasNext()){
			System.out.println(stocksWithzhenfu.next().amplitude);
		}

		System.out.println("---------------------接下来是成交量排序----------------");
		Iterator<StockVO> stocksWithvolume = bl.getSortStocks(false, Stock_Attribute.volume);
		while(stocksWithvolume.hasNext()){
			System.out.println(stocksWithvolume.next().turnoverVol);
		}
	}

	@Test
	public void testGetSortStocksInScope() {
//		fail("Not yet implemented");
		//pass
	}

	@Test
	public void testGetRecentStocks() {
		System.out.println("---------------------接下来是测试获得一个股票最近数据----------------");

		Iterator<StockVO> vos = bl.getRecentStocks("sh600791");
		while(vos.hasNext()){
			StockVO vo = vos.next();
			System.out.print(vo.date);
			System.out.print(vo.name);
			System.out.println();
		}	}

	@Test
	public void testGetStocksByTime() {
//		fail("Not yet implemented");
	}

	@Test
	public void testGetStocksByStockCode() {
		System.out.println("--------------------测试通过股票代码获得相关股票------------------");
		Iterator<StockVO> vos = bl.getStocksByStockCode("600");
		while(vos.hasNext()){
			StockVO vo = vos.next();
			System.out.print(vo.code);
			System.out.print(vo.name);
			System.out.println();
		}
	}

	@Test
	public void testGetDayOHLC_Data() {
		System.out.println("-------------------下面进入日K线测试------------------------");
		Iterator<StockVO> vos = bl.getStocksByTime("sh600300", DateCalculator.getAnotherDay(-30), DateCalculator.getAnotherDay(0));
		List<Double> lows = new ArrayList<Double>();
		while(vos.hasNext()){
			lows.add(vos.next().low);
		}
		System.out.println("-************************-");
		List<OHLC_VO> tmp = bl.getDayOHLC_Data("sh600300", DateCalculator.getAnotherDay(-30), DateCalculator.getAnotherDay(0));
		for (int i = 0; i < 15; i++) {
			if(!lows.get(i).equals(tmp.get(i).low)){
				fail();
			}
		}
	}

	@Test
	public void testGetWeekOHLC_Data() {
		System.out.println("-------------------下面进入周K线测试------------------------");
		List<OHLC_VO> tmp = bl.getWeekOHLC_Data("sh600300", DateCalculator.getAnotherDay(-30), DateCalculator.getAnotherDay(0));
		System.out.println(tmp.size());
		for (OHLC_VO ohlc_VO : tmp) {
			System.out.println("Day is " + ohlc_VO.date.getDay() + " low is " + ohlc_VO.low);
		}
	}

	@Test
	public void testGetMonthOHLC_Data() {
		System.out.println("-------------------下面进入月K线测试------------------------");
		List<OHLC_VO> tmp = bl.getMonthOHLC_Data("sh600300", DateCalculator.getAnotherDay(-100), DateCalculator.getAnotherDay(0));
		System.out.println("长度为 + " + tmp.size());
		for (OHLC_VO ohlc_VO : tmp) {
			System.out.println("Year and Month is  " + ohlc_VO.date.getYear() + " " + ohlc_VO.date.getMonth() + " low is " + ohlc_VO.low);
		}
	}

	@Test
	public void testGetSharingVOs() {
//		fail("Not yet implemented");
		//TODO
	}

	@Test
	public void testGetDayDealVOs() {
		System.out.println("-------------------下面进入日成交量测试------------------------");
		List<DealVO> dealVOs = bl.getDayDealVOs("sh600300", DateCalculator.getAnotherDay(-100), DateCalculator.getAnotherDay(0));
		for (DealVO dealVO : dealVOs) {
			System.out.println(dealVO.date.DateToString() + " " + dealVO.dealAmount+ " " + dealVO.volume);
		}

	}

	@Test
	public void testGetWeekDealVOs() {
		System.out.println("-------------------下面进入周成交量测试------------------------");
		List<DealVO> dealVOs = bl.getWeekDealVOs("sh600300", DateCalculator.getAnotherDay(-100), DateCalculator.getAnotherDay(0));
		for (DealVO dealVO : dealVOs) {
			System.out.println(dealVO.date.DateToString() + " " + dealVO.dealAmount+ " " + dealVO.volume);
		}

	}

	@Test
	public void testGetMonthDealVOs() {
		System.out.println("-------------------下面进入月成交量测试------------------------");
		List<DealVO> dealVOs = bl.getMonthDealVOs("sh600300", DateCalculator.getAnotherDay(-100), DateCalculator.getAnotherDay(0));
		for (DealVO dealVO : dealVOs) {
			System.out.println(dealVO.date.DateToString() + " " + dealVO.dealAmount+ " " + dealVO.volume);
		}
	}

	@Test
	public void testGetTodayStockVO() {

	}

}
