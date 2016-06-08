package service;

import entity.StockEntity;
import entity.StockdataEntity;
import util.MyDate;
import vo.DealVO;
import vo.OHLC_VO;
import vo.TimeSharingVO;

import java.util.List;



/**
 * Businesslogic Interface of Stock
 * 
 * @author czq
 * @date 2016年3月4日
 */

public interface StockService {
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
	List<OHLC_VO> getDayOHLC_Data(String stockCode, MyDate start,
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
	List<OHLC_VO> getWeekOHLC_Data(String stockCode, MyDate start,
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
	List<OHLC_VO> getMonthOHLC_Data(String stockCode, MyDate start,
										   MyDate end);

	/**
	 * Get the timeSharing datas by the given stockCode
	 *
	 * @param stockCode
	 * @return the recent message from opening quotation(开盘) up to now if today
	 *        			is weekend or festival ,then if will return last time's data
	 *       	 result will be null if any exception happens
	 */
	List<TimeSharingVO> getSharingVOs(String stockCode);

	/**
	 * Get the day deal datas by the date and stockcode
	 *
	 * @param stockCode
	 * @param start
	 * @param end
	 * @return Stock deal datas between this time interval in a List ordered by
	 *         time Will be null if stcokCode or the date is invalid
	 */
	List<DealVO> getDayDealVOs(String stockCode, MyDate start, MyDate end);

	/**
	 * Get the we deal datas by the date and stockcode
	 *
	 * @param stockCode
	 * @param start
	 * @param end
	 * @return Stock deal datas between this time interval in a List ordered by
	 *         time Will be null if stcokCode or the date is invalid
	 */
	List<DealVO> getWeekDealVOs(String stockCode, MyDate start,
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
	List<DealVO> getMonthDealVOs(String stockCode, MyDate start,
										MyDate end);





	/**
	/**
	 * Get the recent datas(last month) of the given stockCode
	 *
	 * @param stockCode
	 * @return
	 */
	List<StockdataEntity> getRecentStocks(String stockCode);


    List<StockEntity> getAllStocks();

	StockEntity getStockDescription(String code);


	List<StockdataEntity> getTodayAllStockData();

	/**
	 * Get today's(or last trading day)data of the given stockCode
	 *
	 * @param stockCode
	 * @return
	 */
	StockdataEntity getTodayStockVO(String stockCode);
//
	/**
	 * 获得某只股票一段时间内的数据
	 *
	 * @param stockCode
	 * @param start start date
	 *
	 * @param end end date
	 * @return if the stockCode is invalid will return <b>NULL</b>
	 */
	List<StockdataEntity> getStocksByTime(String stockCode, MyDate start,
												 MyDate end);
//
//	/**
//	 * 获得包含该股票代码序列的股票的数据
//	 *
//	 * @param code
//	 * @return 注意：最多返回10只搜索到的股票
//	 */
//	public Iterator<StockVO> getStocksByStockCode(String code);
//
//	/**
//	 * Force to update all data of the stocks , will send back a message
//	 */
//	public void updateAllStockMes();
	StockEntity getStockEntity(String code);



	
}
