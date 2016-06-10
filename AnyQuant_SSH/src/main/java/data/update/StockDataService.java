package data.update;


import util.MyDate;
import util.update.enumeration.Exchange;
import util.update.po.StockPO;
import util.update.po.TimeSharingPO;

import java.util.List;

/**
 *
 * @author ss
 * @date 2016年4月7日
 */

public interface StockDataService {
	/**
	 * 下列重载方法用于获取股票代码 Exchange 为交易所枚举类型
	 *
	 * @return
	 */

	/**
	 * 返回2015年上海和深圳的全部股票代码
	 *
	 * @return
	 */
	public List<String> getAllStocks();

	public List<String> getAllStocks(int year);

	public List<String> getAllStocks(Exchange exchange);

	public List<String> getAllStocks(int year, Exchange exchange);

	/**
	 * 下列方法用于获取某只股票的具体信息，参数的个数为任意多的Stock_Attribute枚举类型
	 *
	 * @param stockCode
	 *            股票代码
	 * @param fields
	 *            属性
	 * @return
	 */

	/**
	 * 只需要传入股票的代码例如"sh600126"，返回最新信息
	 *
	 * @param stockCode
	 * @return
	 */
	public StockPO getStockMes(String stockCode);
	/**
	 *增加了时间限制，可以设置只查看某一天的数据
	 * @param stockCode
	 * @param date
	 * @return
	 */
	public StockPO getStockMes(String stockCode, MyDate date);


	/**
	 * 增加了时间限制，可以查看某段时间内的数据
	 *
	 * @param stockCode
	 * @param
	 * @return
	 */
	public List<StockPO> getStockMes(String stockCode, MyDate start, MyDate end);

	/**
	 * 返回当天的全部股票具体信息
	 * @return
	 */
	public List<StockPO> getAllStockMes();

	/**
	 * 获得分时图数据
	 * @param stockCode
	 * @return
	 */
	public List<TimeSharingPO> getTimeSharingPOs(String stockCode);
	
	/**
	 * 更新所有股票数据
	 */
	public boolean updateAllMes();
}
