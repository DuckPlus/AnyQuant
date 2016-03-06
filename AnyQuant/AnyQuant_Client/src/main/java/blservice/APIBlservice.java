package blservice;

import java.util.Iterator;
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
	 * @return 股票集合的迭代器
	 */
	public Iterator<StockVO> getAllStocks();
	
	/**
	 * 获得某只大盘最近一个月的所有数据
	 * @param BenchMarkCode
	 * @return  最近一个月数据集合的迭代器
	 */
	public Iterator<BenchMarkVO> getRecentBenchMarks(String BenchMarkCode);
	/**
	 * 获得某只大盘某段时间内的所有数据
	 * @param BenchMarkCode
	 * @param start
	 * @param end
	 * @return
	 */
	public Iterator<BenchMarkVO> getBenchMarkByTime(String BenchMarkCode, MyDate start,
			MyDate end);
	/**
	 * 
	 * @param isUp 是否为升序
	 * @param attr 排序标准：代码、涨跌幅、振幅、成交量
	 * @return
	 */
	public Iterator<StockVO> getSortStocks(boolean isUp, Stock_Attribute attr);

	/**
	 * 对于给定的排序范围 获得经过排序的股票 
	 * @param isUp 是否为升序
	 * @param attr 排序标准：代码、涨跌幅、振幅、成交量
	 * @param stocksCode 排序范围（股票列表）
	 * @return
	 */
	public Iterator<StockVO> getSortStocksInScope(boolean isUp,
			Stock_Attribute attr, List<String> stocksCode);

	/**
	 * 获得所有大盘的所有数据
	 */
	public Iterator<BenchMarkVO> getAllBenchMarks();

	/**
	 * 获得某只股票最近一个月的数据
	 * @param stockCode
	 * @return
	 */
	public Iterator<StockVO> getRecentStocks(String stockCode);

	/**
	 * 获得某只股票一段时间内的数据
	 * @param stockCode 股票代码
 	 * @param start 起始日期
	 * @param end 结束日期
	 * @return    若没有，返回<b>NULL</b>
	 */
	public Iterator<StockVO> getStocksByTime(String stockCode, MyDate start,
			MyDate end);

	
	/**
	 * 获得包含该股票代码序列的股票的数据
	 * @param code
	 * @return  注意：最多返回10只搜索到的股票
	 * TODO 后期将支持 中文搜索
	 */
	public Iterator<StockVO> getStocksByStockCode(String code);

}
