package blimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.python.antlr.PythonParser.return_stmt_return;

import jnr.ffi.Struct.int16_t;
import po.StockPO;
import util.MyTime;
import vo.DealVO;
import vo.OHLC_VO;
import vo.StockVO;
import vo.TimeSharingVO;
import blservice.StockBLService;
import businessLogicHelper.StockSortHelper;
import dataservice.APIDataFactory;
import dataservice.APIInterface;
import enumeration.MyDate;
import enumeration.Stock_Attribute;

/**
 * API逻辑层实现
 * 
 * @author czq
 * @date 2016年3月4日
 */
public class StockBLImpl implements StockBLService {
	/**
	 * 单例模式
	 */
	private static StockBLService APIBlservice;
	private APIInterface APIDataSer;
	/**
	 * 两份股票的数据，Collection版便于排序，Map版便于查找
	 */
	private List<StockVO> stocks;
	private Map<String, StockVO> stockMap;

	/*
	 * 为了加快测试速度，在开发阶段只引入100只股票
	 */
	private StockBLImpl() {
		APIDataSer = APIDataFactory.getAPIDataService();
		List<StockPO> stocksCode = APIDataSer.getAllStockMes();
		stockMap = new TreeMap<String, StockVO>();

		System.out.println("Reading Data-----------");// TODO

		/*
		 * 使用TreeMap支持自动排序
		 */

		System.out.println("股票数量：" + stocksCode.size());
		stocks = new ArrayList<StockVO>(stocksCode.size());
		for (StockPO po : stocksCode) {
			stockMap.put(po.getCode(), (StockVO) VOPOchange.POtoVO(po));
		}
		stocks = new ArrayList<StockVO>(stockMap.values());

		// for (String string : benchCodes) {
		// benchMarkVOs.add( (BenchMarkVO)
		// VOPOchange.POtoVO(APIDataSer.getBenchMes(string)));
		// }

		System.out.println("Reading Finish-----------");

	}

	public static StockBLService getAPIBLService() {
		if (APIBlservice == null) {
			APIBlservice = new StockBLImpl();
		}
		return APIBlservice;
	}

	@Override
	public Iterator<StockVO> getAllStocks() {
		return stocks.iterator();
	}

	@Override
	public Iterator<StockVO> getSortStocks(boolean isUp, Stock_Attribute attr) {
		return StockSortHelper.sortStocks(stocks, attr, isUp);
	}

	@Override
	public Iterator<StockVO> getSortStocksInScope(boolean isUp,
			Stock_Attribute attr, List<String> stocksCode) {
		List<StockVO> stocks = new ArrayList<StockVO>(stocksCode.size());
		for (String string : stocksCode) {
			stocks.add(stockMap.get(string));
		}
		return StockSortHelper.sortStocks(stocks, attr, isUp);
	}

	@Override
	public Iterator<StockVO> getRecentStocks(String stockCode) {
		return getStocksByTime(stockCode, MyTime.getAnotherDay(-30),
				MyTime.getToDay());
	}

	@Override
	// TODO
	public Iterator<StockVO> getStocksByTime(String stockCode, MyDate start,
			MyDate end) {
		List<StockPO> pos = APIDataSer.getStockMes(stockCode, start, end);
		if (pos != null) {
			List<StockVO> result = new ArrayList<StockVO>(pos.size());
			for (StockPO stockPO : pos) {
				result.add((StockVO) VOPOchange.POtoVO(stockPO));
			}
			return result.iterator();
		} else {
			return null;
		}
	}

	@Override
	public Iterator<StockVO> getStocksByStockCode(String code) {
		List<StockVO> result = new ArrayList<StockVO>();

		for (Iterator<String> iterator = stockMap.keySet().iterator(); iterator
				.hasNext();) {
			String temp = iterator.next();
			if (temp.contains(code)) {
				result.add(stockMap.get(temp));
			}
			if (result.size() > 10) {
				break;
			}
		}

		return result.iterator();
	}

	@Override
	public List<OHLC_VO> getDayOHLC_Data(String stockCode, MyDate start,
			MyDate end) {
		List<StockPO> pos = APIDataSer.getStockMes(stockCode, start, end);
		List<OHLC_VO> results;
		if (pos != null) {
			results = new ArrayList<OHLC_VO>(pos.size());
			for (StockPO stockPO : pos) {
				results.add(new OHLC_VO(MyDate.getDateFromString(stockPO
						.getDate()), stockPO.getOpen(), stockPO.getClose(),
						stockPO.getHigh(), stockPO.getLow()));
			}
			return results.isEmpty() ? null : results;
		} else {
			return null;
		}

	}

	@Override
	public List<OHLC_VO> getWeekOHLC_Data(String stockCode, MyDate start,
			MyDate end) {
		List<StockPO> pos = APIDataSer.getStockMes(stockCode,
				MyTime.getMondayofTheWeek(start),
				MyTime.getFridayofTheWeek(end));
//		System.out.println(start.DateToString());
//		System.out.println(MyTime.getMondayofTheWeek(start).DateToString());
		List<OHLC_VO> results;
		int DAY_OF_WEEK = 5;
		if (pos == null) {
			return null;
		} else {
//			System.out.println(pos.size());
//			System.out.println(pos.get(0).getDate());
			int len = pos.size();
			int weekNum = len / 5 + (len % 5 > 0 ? 1 : 0);
			int monday;
			int friday;
//			System.out.println(weekNum);
			results = new ArrayList<OHLC_VO>(weekNum);
			for (int i = 0; i < weekNum; i++) {
				 monday = i * DAY_OF_WEEK;
				 friday = i==weekNum-1?(len%5):4;
				 //Friday sometimes means the last trading day in this week
				results.add(new OHLC_VO(MyDate.getDateFromString(pos
						.get(monday).getDate()), pos.get(monday).getOpen(), pos
						.get(monday + friday).getClose(), getHighInScope(pos
						.subList(monday, monday+  friday)), getLowInScope(pos
						.subList(monday, monday + friday))));
			}
			 return results;
		}
	}

	@Override
	public List<OHLC_VO> getMonthOHLC_Data(String stockCode, MyDate start,
			MyDate end) {
		start.setDay(1);
		end.setDay(28);
		List<OHLC_VO> vos ;
		List<StockPO> pos = APIDataSer.getStockMes(stockCode, start, end);
		if(pos == null){
			return null;
		}else{
			vos = new ArrayList<OHLC_VO>(pos.size());
			int len = vos.size();
			int monthNum = 12*(end.getYear()  - start.getYear()  - 1)+ end.getMonth() - start.getMonth();
			
		}
		return null;
	}

	@Override
	public List<TimeSharingVO> getSharingVOs(String stockCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DealVO> getDayDealVOs(String stockCode, MyDate start, MyDate end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DealVO> getWeekDealVOs(String stockCode, MyDate start,
			MyDate end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DealVO> getMonthDealVOs(String stockCode, MyDate start,
			MyDate end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StockVO getTodayStockVO(String stockCode) {
		// TODO Auto-generated method stub
		return null;
	}

	private static final double getLowInScope(List<StockPO> scope) {
		double result = Double.MAX_VALUE;
		for (StockPO stockPO : scope) {
			if (stockPO.getLow() < result) {
				result = stockPO.getLow();
			}
		}
		return result;
	}

	private static final double getHighInScope(List<StockPO> scope) {
		double result = Double.MIN_VALUE;
		for (StockPO stockPO : scope) {
			if (stockPO.getHigh() > result) {
				result = stockPO.getHigh();
			}
		}
		return result;
	}
}
