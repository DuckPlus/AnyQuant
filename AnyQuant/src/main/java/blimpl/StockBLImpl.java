package blimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import blHelper.StockSortHelper;
import blHelper.VOPOchange;
import blservice.StockBLService;
import dataservice.APIDataFactory;
import dataservice.StockDataService;
import enumeration.MyDate;
import enumeration.Stock_Attribute;
import po.StockPO;
import po.TimeSharingPO;
import util.DateCalculator;
import vo.DealVO;
import vo.OHLC_VO;
import vo.StockVO;
import vo.TimeSharingVO;

/**
 * Stocks Business logic implement
 *
 * @author Qiang
 * @date 2016年3月4日
 */
public class StockBLImpl implements StockBLService {
	/**
	 * 单例模式
	 */
	private static StockBLService APIBlservice;
	private StockDataService APIDataSer;
	/**
	 * 两份股票的数据，Collection版便于排序，Map版便于查找
	 */
	private List<StockVO> stocks;
	private Map<String, StockVO> stockMap;


	private StockBLImpl() {
		APIDataSer = APIDataFactory.getStockDataService();
		List<StockPO> stocksCode = APIDataSer.getAllStockMes();
		//use TreeMap to sort the stocks by stocks code automatically
		stockMap = new TreeMap<String, StockVO>();
		stocks = new ArrayList<StockVO>(stocksCode.size());
		for (StockPO po : stocksCode) {
			stockMap.put(po.getCode(), (StockVO) VOPOchange.POtoVO(po));
		}
		stocks = new ArrayList<StockVO>(stockMap.values());

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
	public Iterator<StockVO> getSortStocksInScope(boolean isUp, Stock_Attribute attr, List<String> stocksCode) {
		List<StockVO> stocks = new ArrayList<StockVO>(stocksCode.size());
		for (String string : stocksCode) {
			stocks.add(stockMap.get(string));
		}
		return StockSortHelper.sortStocks(stocks, attr, isUp);
	}

	@Override
	public Iterator<StockVO> getRecentStocks(String stockCode) {
		return getStocksByTime(stockCode, DateCalculator.getAnotherDay(-30), DateCalculator.getToDay());
	}

	@Override
	public StockVO getTodayStockVO(String stockCode) {
		return stockMap.get(stockCode).clone();

	}

	@Override
	public Iterator<StockVO> getStocksByTime(String stockCode, MyDate start, MyDate end) {
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
		System.out.println(code);
		List<StockVO> result = new ArrayList<StockVO>();
		for (String temp : stockMap.keySet()) {
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
	public List<OHLC_VO> getDayOHLC_Data(String stockCode, MyDate start, MyDate end) {
		List<StockPO> pos = APIDataSer.getStockMes(stockCode, start, end);
		List<OHLC_VO> results;
        OHLC_VO vo;
		if (pos != null) {
			results = new ArrayList<OHLC_VO>(pos.size());
			for (StockPO stockPO : pos) {
                vo = new OHLC_VO(MyDate.getDateFromString(stockPO.getDate()), stockPO.getOpen(),
                        stockPO.getClose(), stockPO.getHigh(), stockPO.getLow());
				if (judgeData(vo)) { // if the stock didn't trade that day , desert it
					results.add(vo);
				}

			}
			return results;
		} else {
			return null;
		}

	}

	@Override
	public List<OHLC_VO> getWeekOHLC_Data(String stockCode, MyDate start, MyDate end) {
		List<StockPO> pos = APIDataSer.getStockMes(stockCode, DateCalculator.getMondayofTheWeek(start),
				DateCalculator.getFridayofTheWeek(end));
		List<OHLC_VO> results;
        OHLC_VO vo;
		int DAY_OF_WEEK = 5;
		if (pos == null) {
			return null;
		} else {
			int len = pos.size();
			int weekNum = len / DAY_OF_WEEK + (len % DAY_OF_WEEK > 0 ? 1 : 0);
			int monday;
			int friday;
			results = new ArrayList<OHLC_VO>(weekNum);

            for (int i = 0; i < weekNum; i++) {
				monday = i * DAY_OF_WEEK;
				friday = i == weekNum - 1 ? (len % DAY_OF_WEEK - 1) : 4;
                vo = new OHLC_VO(MyDate.getDateFromString(pos.get(monday).getDate()), pos.get(monday).getOpen(),
                        pos.get(monday + friday).getClose(), getHighInScope(pos.subList(monday, monday + friday + 1)),
                        getLowInScope(pos.subList(monday, monday + friday + 1)));
				// Friday sometimes means the last trading day in this week
                if (judgeData(vo)) { // if the stock didn't trade that day , desert it
                    results.add(vo);
                }
			}
			return results;
		}
	}

	@Override
	public List<OHLC_VO> getMonthOHLC_Data(String stockCode, MyDate start, MyDate end) {

		List<OHLC_VO> vos;
		List<StockPO> pos;

		int monthNum = 12 * (end.getYear() - start.getYear()) + end.getMonth() - start.getMonth() + 1;
		vos = new ArrayList<>(monthNum);
		MyDate thisMonth = start.clone();
		MyDate monthEnd = thisMonth.clone();

		thisMonth.setDay(1);
		monthEnd.setDay(DateCalculator.getMonthEndDay(monthEnd));
        OHLC_VO vo;
		for (int i = 0; i < monthNum; i++) {


			pos = APIDataSer.getStockMes(stockCode, thisMonth, monthEnd);

			if( pos==null || pos.size()==0){

			}else{
                vo = new OHLC_VO(thisMonth, pos.get(0).getOpen(), pos.get(pos.size() - 1).getClose(),
                        getHighInScope(pos), getLowInScope(pos));
                if (judgeData(vo)) { // if the stock didn't trade that day , desert it
                    vos.add(vo);
                }

			}



			getNextMonth(thisMonth);
			getNextMonth(monthEnd);

		}
		return vos.isEmpty() ? null : vos;
	}



	@Override
	public List<TimeSharingVO> getSharingVOs(String stockCode) {

		List<TimeSharingPO> pos = APIDataSer.getTimeSharingPOs(stockCode);

		if(pos == null){
			return null;
		}

		List<TimeSharingVO> vos = new ArrayList<>();

		for (TimeSharingPO po : pos) {

			vos.add((TimeSharingVO) VOPOchange.POtoVO(po));

		}

		return vos.isEmpty() ? null : vos;
	}

	@Override
	public List<DealVO> getDayDealVOs(String stockCode, MyDate start, MyDate end) {
		List<StockPO> pos = APIDataSer.getStockMes(stockCode, start, end);
		List<DealVO> results;
//		System.out.println("StockBLImpl.getDayDealVO "+start.DateToString()+"--"+end.DateToString());
		if (pos == null) {
			return null;
		} else {
			results = new ArrayList<DealVO>(pos.size());
			for (StockPO stockPO : pos) {
				results.add(new DealVO(stockPO.getTurnoverValue(), stockPO.getTurnoverVol(),
						MyDate.getDateFromString(stockPO.getDate())));
			}
		}

		return results.isEmpty() ? null : results;
	}

	@Override
	public List<DealVO> getWeekDealVOs(String stockCode, MyDate start, MyDate end) {
		List<StockPO> pos = APIDataSer.getStockMes(stockCode, DateCalculator.getMondayofTheWeek(start),
				DateCalculator.getFridayofTheWeek(end));
		List<DealVO> results;
		int DAY_OF_WEEK = 5;
		if (pos == null) {
			return null;
		} else {
			int len = pos.size();
			int weekNum = len / DAY_OF_WEEK + (len % DAY_OF_WEEK > 0 ? 1 : 0);
			int monday;
			int friday;
//			System.out.println("pos:" + pos.size());
			results = new ArrayList<DealVO>(weekNum);
			for (int i = 0; i < weekNum; i++) {
				monday = i * DAY_OF_WEEK;
				// last item is exclusive of the method "sublist"
				friday = (i == weekNum - 1) ? (len % DAY_OF_WEEK) : 5;
				// Friday sometimes means the last trading day in this week
				results.add(getSumDealVO(pos.subList(monday, monday + friday)));

			}
			return results.isEmpty() ? null : results;
		}

	}

	@Override
	public List<DealVO> getMonthDealVOs(String stockCode, MyDate start, MyDate end) {

		List<DealVO> vos;
		List<StockPO> pos = new ArrayList<StockPO>(25);

		int monthNum = 12 * (end.getYear() - start.getYear()) + end.getMonth() - start.getMonth() + 1;
		vos = new ArrayList<>(monthNum);
		MyDate thisMonth = start.clone();
		MyDate monthEnd = thisMonth.clone();
		thisMonth.setDay(1);
		monthEnd.setDay(DateCalculator.getMonthEndDay(monthEnd));
		for (int i = 0; i < monthNum; i++) {
			pos = APIDataSer.getStockMes(stockCode, thisMonth, monthEnd);
			vos.add(getSumDealVO(pos));

			getNextMonth(monthEnd);
			getNextMonth(thisMonth);
		}

		return vos.isEmpty() ? null : vos;
	}

    @Override
    public void updateAllStockMes() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                if(APIDataSer.updateAllMes()){
                    //send success message


                }

            }
        }).start();

    }
	/**
	 * 计算给定范围内的成交量、成交额总量
	 *
	 */
	private static DealVO getSumDealVO(List<StockPO> subList) {
		long volume = 0;
		double turnoverval = 0;
		for (StockPO stockPO : subList) {
			volume += stockPO.getTurnoverVol();
			turnoverval += stockPO.getTurnoverValue();
		}

		return new DealVO(turnoverval, volume, MyDate.getDateFromString(subList.get(0).getDate()));
	}

	private static double getLowInScope(List<StockPO> scope) {
		double result = Double.MAX_VALUE;
		for (StockPO stockPO : scope) {
			if (stockPO.getLow() < result) {
				result = stockPO.getLow();
			}
		}
		return result;
	}

	private static double getHighInScope(List<StockPO> scope) {
		double result = Double.MIN_VALUE;
		for (StockPO stockPO : scope) {
			if (stockPO.getHigh() > result) {
				result = stockPO.getHigh();
			}
		}
		return result;
	}

	private static void getNextMonth(MyDate date) {
		if (date.getMonth() == 12) {
			date.setMonth(1);
			date.setYear(date.getYear() + 1);
		} else {
			date.setMonth(date.getMonth() + 1);
		}
	}

	private static boolean judgeData(OHLC_VO vo) {
		return !(vo.low == vo.high);
	}

//	@SuppressWarnings("unused")
//	private void printList(List<StockPO> subList) {
//		for (StockPO stockPO : subList) {
//			System.out.print(stockPO.getDate() + " ");
//			// System.out.println(stockPO.getVolume());
//		}
//
//	}



}
