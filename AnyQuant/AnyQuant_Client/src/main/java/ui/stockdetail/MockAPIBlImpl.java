/**
 *@author dsn
 *@version 2016年3月4日    下午5:41:16
 */

package ui.stockdetail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import dataservice.APIInterface;
import dataservice.APIInterfaceImpl;
import ui.config.TestData;
import vo.BenchMarkVO;
import vo.DealVO;
import vo.OHLC_VO;
import vo.StockVO;
import vo.TimeSharingVO;
import enumeration.MyDate;
import enumeration.Stock_Attribute;
import blimpl.APIBlImpl;
import blimpl.VOPOchange;
import blservice.StockBLService;

public class MockAPIBlImpl implements StockBLService{
	/**
	 * 单例模式
	 */
	private static StockBLService APIBlservice;
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
		if(APIBlservice == null){
			APIBlservice = new MockAPIBlImpl();
		}
		return APIBlservice;
	}
	
	private MockAPIBlImpl() {
		
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
	public Iterator<BenchMarkVO> getAllBenchMarks() {
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
	public Iterator<BenchMarkVO> getRecentBenchMarks(String BenchMarkCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override

	public Iterator<BenchMarkVO> getBenchMarkByTime(String BenchMarkCode,
			MyDate start, MyDate end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OHLC_VO> getDayOHLC_Data(String stockCode, MyDate start,
			MyDate end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OHLC_VO> getWeekOHLC_Data(String stockCode, MyDate start,
			int weekNum) {
		// TODO Auto-generated method stub
		return null;
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
	public List<DealVO> getDealVOs(String stockCode, MyDate start, MyDate end) {
		// TODO Auto-generated method stub
		return null;
	}

}

