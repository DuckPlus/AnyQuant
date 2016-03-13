/**
 *@author dsn
 *@version 2016年3月4日    下午5:41:16
 */

package ui.stockdetail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jfree.data.time.Day;

import ui.config.TestData;
import vo.BenchMarkVO;
import vo.DealVO;
import vo.OHLC_VO;
import vo.StockVO;
import vo.TimeSharingVO;
import blservice.StockBLService;
import dataservice.APIInterface;
import enumeration.MyDate;
import enumeration.Stock_Attribute;

public class MockStockBLImpl implements StockBLService{
	/**
	 * 单例模式
	 */
	private static StockBLService stockBlservice;
	private APIInterface APIDataSer; 
	/**
	 * 两份股票的数据，Collection版便于排序，Map版便于查找
	 */
	private List<StockVO> stocks;
	private Map<String , StockVO> stockMap;
	/**
	 * 大盘数据
	 */
	private List<BenchMarkVO> benchMarkVOs;
	
	public static StockBLService getAPIBLService(){
		if(stockBlservice == null){
			stockBlservice = new MockStockBLImpl();
		}
		return stockBlservice;
	}
	
	private MockStockBLImpl() {
		
	}
	@Override
	public Iterator<StockVO> getAllStocks() {
		List<StockVO> vos=new ArrayList<StockVO>();
		vos=TestData.getOne_Stock_VOs(7);
		return vos.iterator();
	}

	@Override
	public Iterator<StockVO> getSortStocks(boolean isUp, Stock_Attribute attr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<StockVO> getSortStocksInScope(boolean isUp,
			Stock_Attribute attr, List<String> stocksCode) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Iterator<StockVO> getRecentStocks(String stockCode) {
		List<StockVO> vos=new ArrayList<StockVO>();

		vos=TestData.getOne_Stock_VOs(1000);
		return vos.iterator();
	}

	@Override
	public Iterator<StockVO> getStocksByTime(String stockCode, MyDate start,
			MyDate end) {
		List<StockVO> vos=new ArrayList<StockVO>();
		vos=TestData.getOne_Stock_VOs(7);
		return vos.iterator();
	}

	@Override
	public Iterator<StockVO> getStocksByStockCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<OHLC_VO> getDayOHLC_Data(String stockCode, MyDate start,
			MyDate end) {
		List<OHLC_VO> vos = new ArrayList<OHLC_VO>();
		vos.add(new OHLC_VO(new MyDate(2013, 8, 20), 7.02, 7.14, 7.19, 6.94));
		vos.add(new OHLC_VO(new MyDate(2013, 8, 21), 7.10, 7.07, 7.15, 7.02));
		vos.add(new OHLC_VO(new MyDate(2013, 8, 22), 6.96, 7.11, 7.15, 6.93));
		vos.add(new OHLC_VO(new MyDate(2013, 8, 23), 7.12, 7.03, 7.16, 7.00));
		vos.add(new OHLC_VO(new MyDate(2013, 8, 24), 7.05, 6.99, 7.09, 6.90));
		
		vos.add(new OHLC_VO(new MyDate(2013, 8, 27), 7.05, 7.41, 7.46, 7.02));
		vos.add(new OHLC_VO(new MyDate(2013, 8, 28), 7.31, 7.56, 7.70, 7.15));
		vos.add(new OHLC_VO(new MyDate(2013, 8, 29), 7.42, 7.33, 7.66, 7.22));
		vos.add(new OHLC_VO(new MyDate(2013, 8, 30), 7.42, 7.40, 7.56, 7.31));
		vos.add(new OHLC_VO(new MyDate(2013, 8, 31), 7.4, 7.43, 7.6, 7.28));
		vos.add(new OHLC_VO(new MyDate(2013, 9, 3), 7.5, 7.69, 7.8, 7.48));
		vos.add(new OHLC_VO(new MyDate(2013, 9, 4), 7.7, 8.27, 8.46, 7.67));
		vos.add(new OHLC_VO(new MyDate(2013, 9, 5), 8.2, 8.36, 8.74, 8.17));
		vos.add(new OHLC_VO(new MyDate(2013, 9, 6), 8.4, 8.88, 9.08, 8.33));
		vos.add(new OHLC_VO(new MyDate(2013, 9, 7), 8.89, 8.73, 9.04, 8.70));
		
		vos.add(new OHLC_VO(new MyDate(2013, 9, 10), 8.6, 8.95, 9.03, 8.40));
		vos.add(new OHLC_VO(new MyDate(2013, 9, 11), 9.0, 8.24, 9.0, 8.1));
		vos.add(new OHLC_VO(new MyDate(2013, 9, 12), 8.2, 8.13, 8.4, 7.81));
		vos.add(new OHLC_VO(new MyDate(2013, 9, 13), 8.13, 8.42, 8.46, 7.97));
		vos.add(new OHLC_VO(new MyDate(2013, 9, 14), 8.44, 8.33, 8.45, 8.13));
		vos.add(new OHLC_VO(new MyDate(2013, 9, 17), 8.26, 8.89, 8.98, 8.15));
		vos.add(new OHLC_VO(new MyDate(2013, 9, 18), 8.88, 8.80, 9.17, 8.69));
		vos.add(new OHLC_VO(new MyDate(2013, 9, 19), 8.80, 8.66, 8.94, 8.50));
		
		vos.add(new OHLC_VO(new MyDate(2013, 9, 20), 8.68, 8.69, 8.95, 8.50));
		vos.add(new OHLC_VO(new MyDate(2013, 9, 21), 8.68, 9.00, 9.05, 8.40));
		vos.add(new OHLC_VO(new MyDate(2013, 9, 24), 9.05, 9.25, 9.50, 8.91));
		vos.add(new OHLC_VO(new MyDate(2013, 9, 25), 9.25, 9.00, 9.33, 8.88));
		vos.add(new OHLC_VO(new MyDate(2013, 9, 26), 9.0, 9.04, 9.1, 8.82));
		vos.add(new OHLC_VO(new MyDate(2013, 9, 27), 8.9, 8.96, 9.06, 8.83));
		vos.add(new OHLC_VO(new MyDate(2013,9,28), 9.2, 9.34, 9.58, 9.16)); 
       
		return vos;
	}



	@Override
	public List<OHLC_VO> getMonthOHLC_Data(String stockCode, MyDate start,
			MyDate end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TimeSharingVO> getSharingVOs(String stockCode) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<OHLC_VO> getWeekOHLC_Data(String stockCode, MyDate start, MyDate end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DealVO> getDayDealVOs(String stockCode, MyDate start, MyDate end) {
		List<DealVO> vos=new ArrayList<DealVO>();
		
		vos.add(new DealVO(200287500 / 100, 10, new MyDate(2013,8,20)));
		vos.add(new DealVO(215693200 / 100, 10, new MyDate(2013,8,21)));
		vos.add(new DealVO(232570200 / 100, 10, new MyDate(2013,8,22)));
		vos.add(new DealVO(191760600 / 100, 10, new MyDate(2013,8,23)));
		vos.add(new DealVO(297679500 / 100, 10, new MyDate(2013,8,24)));
		vos.add(new DealVO(475797500 / 100, 10, new MyDate(2013,8,27)));
		vos.add(new DealVO(394975400 / 100, 10, new MyDate(2013,8,28)));
		vos.add(new DealVO(247341700 / 100, 10, new MyDate(2013,8,29)));
		vos.add(new DealVO(160048200 / 100, 10, new MyDate(2013,8,30)));
		vos.add(new DealVO(225339300 / 100, 10, new MyDate(2013,8,31)));
		vos.add(new DealVO(349647800 / 100, 10, new MyDate(2013,9,3)));
		vos.add(new DealVO(671942600 / 100, 10, new MyDate(2013,9,4)));
		vos.add(new DealVO(442421200 / 100, 10, new MyDate(2013,9,5)));
		vos.add(new DealVO(451357300 / 100, 10, new MyDate(2013,9,6)));
		vos.add(new DealVO(351575300 / 100, 10, new MyDate(2013,9,7)));
		vos.add(new DealVO(289063600 / 100, 10, new MyDate(2013,9,10)));
		vos.add(new DealVO(289287300 / 100, 10, new MyDate(2013,9,11)));
		vos.add(new DealVO(274795400 / 100, 10, new MyDate(2013,9,12)));
		vos.add(new DealVO(221260400 / 100, 10, new MyDate(2013,9,13)));
		vos.add(new DealVO(141652900 / 100, 10, new MyDate(2013,9,14)));
		vos.add(new DealVO(312799600 / 100, 10, new MyDate(2013,9,17)));
		vos.add(new DealVO(200661600 / 100, 10, new MyDate(2013,9,18)));
		vos.add(new DealVO(154622600 / 100, 10, new MyDate(2013,9,19)));
		vos.add(new DealVO(173912600 / 100, 10, new MyDate(2013,9,20)));
		vos.add(new DealVO(361042300 / 100, 10, new MyDate(2013,9,21)));
		vos.add(new DealVO(269978500 / 100, 10, new MyDate(2013,9,24)));
		vos.add(new DealVO(178492400 / 100, 10, new MyDate(2013,9,25)));
		vos.add(new DealVO(109719000 / 100, 10, new MyDate(2013,9,26)));
		vos.add(new DealVO(119701900 / 100, 10, new MyDate(2013,9,27)));
		vos.add(new DealVO(260659400 / 100, 10, new MyDate(2013,9,28)));

		return vos;

	}

	@Override
	public List<DealVO> getWeekDealVOs(String stockCode, MyDate start, MyDate end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DealVO> getMonthDealVOs(String stockCode, MyDate start, MyDate end) {
		// TODO Auto-generated method stub
		return null;
	}

}

