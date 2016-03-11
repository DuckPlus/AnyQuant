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
 * 股票相关逻辑层接口
 * @author czq
 * @date 2016年3月4日
 */

public interface StockBLService {
	/**
	 * 
	 * @param stockCode
	 * @param start
	 * @param end
	 * @return
	 */
	public List<OHLC_VO> getDayOHLC_Data(String stockCode, MyDate start,
			MyDate end );
	/**
	 * 
	 * @param stockCode
	 * @param start
	 * @param weekNum
	 * @return
	 */
	public List<OHLC_VO> getWeekOHLC_Data(String stockCode, MyDate start,
			MyDate end);
	/**
	 * 
	 * @param stockCode
	 * @param start
	 * @param end
	 * @return
	 */
	public List<OHLC_VO> getMonthOHLC_Data(String stockCode, MyDate start,
			MyDate end );
	/**
	 * 
	 * @param stockCode
	 * @return
	 */
	public List<TimeSharingVO> getSharingVOs(String stockCode);
	/**
	 * 
	 * @param stockCode
	 * @param start
	 * @param end
	 * @return
	 */
	public List<DealVO> getDayDealVOs(String stockCode, MyDate start,
			MyDate end);
	
	public List<DealVO> getWeekDealVOs(String stockCode, MyDate start,
			MyDate end);
	
	public List<DealVO> getMonthDealVOs(String stockCode, MyDate start,
			MyDate end);
	
	
	
	
	/**
	 * 获得所有股票的所有数据
	 * @return 股票集合的迭代器
	 */
	public Iterator<StockVO> getAllStocks();
	
	
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
