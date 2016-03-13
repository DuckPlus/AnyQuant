package blservice;

import java.util.Iterator;
import java.util.List;

import vo.DealVO;
import vo.OHLC_VO;
import vo.StockVO;
import vo.TimeSharingVO;
import enumeration.MyDate;
import enumeration.Stock_Attribute;

/**
 * Businesslogic Interface of Stock
 * 
 * @author czq
 * @date 2016年3月4日
 */

public interface StockBLService {
	/**
	 * Get the stock's OHLC data by day in the given time
	 * 
	 * @param stockCode
	 * @param start
	 *            startTime
	 * @param end
	 *            endTime
	 * @return will be null if the stockCode is invalid
	 */
	public List<OHLC_VO> getDayOHLC_Data(String stockCode, MyDate start,
			MyDate end);

	/**
	 * Get the stock's OHLC data by week in the given time
	 * 
	 * @param stockCode
	 * @param start
	 *            startTime
	 * @param end
	 *            endTime
	 * @return will be null if the stockCode is invalid
	 */
	public List<OHLC_VO> getWeekOHLC_Data(String stockCode, MyDate start,
			MyDate end);

	/**
	 * Get the stock's OHLC data by month in the given time
	 * 
	 * @param stockCode
	 * @param start
	 *            startTime
	 * @param end
	 *            endTime
	 * @return will be null if the stockCode is invalid
	 */
	public List<OHLC_VO> getMonthOHLC_Data(String stockCode, MyDate start,
			MyDate end);

	/**
	 * Get the timeSharing datas by the given stockCode
	 * 
	 * @param stockCode
	 * @return the recent message from opening quotation(开盘) up to now if today
	 *         is weekend or festival ,then if will return last time's datas
	 */
	public List<TimeSharingVO> getSharingVOs(String stockCode);

	/**
	 * Get the day deal datas by the date and stockcode
	 * 
	 * @param stockCode
	 * @param start
	 * @param end
	 * @return Stock deal datas between this time interval in a List ordered by
	 *         time Will be null if stcokCode or the date is invalid
	 */
	public List<DealVO> getDayDealVOs(String stockCode, MyDate start, MyDate end);

	/**
	 * Get the we deal datas by the date and stockcode
	 * 
	 * @param stockCode
	 * @param start
	 * @param end
	 * @return Stock deal datas between this time interval in a List ordered by
	 *         time Will be null if stcokCode or the date is invalid
	 */
	public List<DealVO> getWeekDealVOs(String stockCode, MyDate start,
			MyDate end);

	/**
	 * Get the month deal datas（成交量&成交额） by the date and stockcode
	 * 
	 * @param stockCode
	 * @param start
	 * @param end
	 * @return Stock deal datas between this time interval(时间区间) in a List
	 *         ordered by time Will be null if stcokCode or the date is invalid
	 */
	public List<DealVO> getMonthDealVOs(String stockCode, MyDate start,
			MyDate end);

	/**
	 * Get the last trading day's（交易日） datas of all the stocks It's in the order
	 * of stockCode
	 * 
	 * @return A Iterator of all the stocks' datas
	 */
	public Iterator<StockVO> getAllStocks();

	/**
	 * Get the last trading day's（交易日） datas of all the stocks <b>in a expected
	 * order</b>
	 * 
	 * @param isUp
	 *            whether is ordered in ascending order
	 * @param attr
	 *            the attribute to order the stock available attribute is
	 *            stockCode(default) , changeRate , amplitude(振幅) , volume
	 * @return a Iterator of the ordered stocks
	 */
	public Iterator<StockVO> getSortStocks(boolean isUp, Stock_Attribute attr);

	/**
	 * Get the last trading day's（交易日） datas of the given stocks <b>in a
	 * expected order</b>
	 * 
	 * @param isUp
	 *            whether is ordered in ascending order
	 * @param attr
	 *            the attribute to order the stock available attribute is
	 *            stockCode(default) , changeRate , amplitude(振幅) , volume
	 * @param stocksCodes
	 * @return a Iterator of the ordered stocks
	 */
	public Iterator<StockVO> getSortStocksInScope(boolean isUp,
			Stock_Attribute attr, List<String> stocksCodes);

	/**
	 * 获得某只股票最近一个月的数据
	 * 
	 * @param stockCode
	 * @return
	 */
	public Iterator<StockVO> getRecentStocks(String stockCode);

	/**
	 * 获得某只股票一段时间内的数据
	 * 
	 * @param stockCode
	 * @return
	 */
	public StockVO getTodayStockVO(String stockCode);

	/**
	 * 获得某只股票一段时间内的数据
	 * 
	 * @param stockCode
	 *            股票代码
	 * @param start
	 *            起始日期
	 * @param end
	 *            结束日期
	 * @return 若没有，返回<b>NULL</b>
	 */
	public Iterator<StockVO> getStocksByTime(String stockCode, MyDate start,
			MyDate end);

	/**
	 * 获得包含该股票代码序列的股票的数据
	 * 
	 * @param code
	 * @return 注意：最多返回10只搜索到的股票 TODO 后期将支持 中文搜索
	 */
	public Iterator<StockVO> getStocksByStockCode(String code);

}
