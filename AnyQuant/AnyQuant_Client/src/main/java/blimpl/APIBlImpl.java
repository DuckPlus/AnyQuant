package blimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dataservice.APIInterface;
import dataservice.APIInterfaceImpl;
import vo.BenchMarkVO;
import vo.StockVO;
import enumeration.MyDate;
import enumeration.Stock_Attribute;
import blservice.APIBlservice;

/**
 * API逻辑层实现
 * @author czq
 * @date 2016年3月4日
 */
public class APIBlImpl implements APIBlservice {
	
	private static APIBlservice APIBlservice;
	
	private APIBlImpl() {
	}
	
	public static APIBlservice getAPIBLService(){
		if(APIBlservice == null){
			APIBlservice = new APIBlImpl();
		}
		return APIBlservice;
	}
	
	APIInterface APIDataSer = new APIInterfaceImpl();
	private List<StockVO> stocks;
	@Override
	public Iterator<StockVO> getAllStocks() {
		List<String> stocksCode = APIDataSer.getAllStocks();
		stocks = new ArrayList<StockVO>(stocksCode.size());
		for (String string : stocksCode) {
			stocks.add((StockVO) VOPOchange.POtoVO(APIDataSer.getStockMes(string)));
		}
		return stocks.iterator();
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<StockVO> getStocksByTime(MyDate start, MyDate end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<StockVO> getStocksByStockCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}


}
