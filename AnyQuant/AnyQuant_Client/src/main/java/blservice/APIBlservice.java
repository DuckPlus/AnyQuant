package blservice;

import java.util.List;

import vo.BenchMarkVO;
import vo.StockVO;
import enumeration.MyDate;
import enumeration.Stock_Attribute;

/**
 *
 * @author czq
 * @date 2016年3月4日
 */

public interface APIBlservice {
	 /**
	  * 获得所有股票的所有数据
	  */
	 public List<StockVO> getAllStocks();
	/**
	 * 按照 代码、成交量、振幅、涨跌幅进行排序
	 */
	public List<StockVO> getSortStocks(boolean isUp , Stock_Attribute attr);
	/**
	 * 按照 代码、成交量、振幅、涨跌幅进行排序 在给定的股票代码中进行排序
	 * @param isUp  true表示升序
	 */
	public List<StockVO> getSortStocksInScope(boolean isUp , Stock_Attribute attr , List<String> stocksCode);
	
	
	
	/**
	 * 获得所有大盘的所有数据
	 */
	public List<BenchMarkVO> getAllBenchMarks ();
	/**
	 * 获得某只股票最近一个月的数据
	 */
	public List<StockVO> getRecentStocks(String stockCode);
	/**
	 * 获得某只股票某段时间的数据
	 */
	public List<StockVO> getStocksByTime(MyDate start , MyDate end);
	/**
	 * 获得含该代码序列的股票的代号、名字
	 */
	public List<StockVO> getStocksByStockCode(String code);
	/**
	 * 
	 */

}

