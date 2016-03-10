package blimpl;

import java.util.Iterator;

import enumeration.Stock_Attribute;
import vo.StockVO;
import blservice.StockBLService;
import junit.framework.TestCase;

/**
 *
 * @author czq
 * @date 2016年3月4日
 */
public class APIBlImplTest extends TestCase {
	
	StockBLService api;
	
	protected void setUp() throws Exception {
		super.setUp();
		api = StockBLImpl.getAPIBLService();
	}

	public void testGetAPIBLService() {
		
	}

	public void testGetAllStocks() {
		Iterator<StockVO> stocks = api.getAllStocks();
		while(stocks.hasNext()){
			System.out.println(stocks.next().high);
		}
	}

	/**
	 * pass 部分
	 */
	public void testGetSortStocks() {
		System.out.println("---------------------接下来是代码排序----------------");
		Iterator<StockVO> stocksWithCode = api.getAllStocks();
		while(stocksWithCode.hasNext()){
			System.out.println(stocksWithCode.next().code);
		}
		System.out.println("---------------------接下来是涨跌幅排序----------------");
		StockVO vo;
		Iterator<StockVO> stocksWithZhangFU = api.getSortStocks(true, Stock_Attribute.changeRate);
		while(stocksWithZhangFU.hasNext()){
			vo = stocksWithZhangFU.next();
			System.out.println(vo);
			if(vo.changeRate > 0.1){
				System.out.println("该股票数据存在异常：涨跌幅大于10%");
				System.out.println("Code:" + vo.code + " 收盘：" + vo.close + " 昨收盘："+vo.preClose );
			}
		}
		
		System.out.println("---------------------接下来是振幅排序----------------");
		Iterator<StockVO> stocksWithzhenfu = api.getSortStocks(false, Stock_Attribute.amplitude);
		while(stocksWithzhenfu.hasNext()){
			System.out.println(stocksWithzhenfu.next().amplitude);
		}
		
		System.out.println("---------------------接下来是成交量排序----------------");
		Iterator<StockVO> stocksWithvolume = api.getSortStocks(false, Stock_Attribute.volume);
		while(stocksWithvolume.hasNext()){
			System.out.println(stocksWithvolume.next().volume);
		}
		
	}


	public void testGetSortStocksInScope() {
		
		//pass
		
	}

	public void testGetAllBenchMarks() {
		// TODO
		fail("Not yet implemented");
	}

	public void testGetRecentStocks() {
		Iterator<StockVO> vos = api.getRecentStocks("sh600791");
		while(vos.hasNext()){
			StockVO vo = vos.next();
			System.out.print(vo.date);
			System.out.print(vo.name);
			System.out.println();
		}
	}

//	public void testGetStocksByTime() {
//		fail("Not yet implemented");
//	}

	public void testGetStocksByStockCode() {
		System.out.println("测试通过股票代码获得相关股票");
		Iterator<StockVO> vos = api.getStocksByStockCode("600");
		while(vos.hasNext()){
			StockVO vo = vos.next();
			System.out.print(vo.code);
			System.out.print(vo.name);
			System.out.println();
		}
	}

}
