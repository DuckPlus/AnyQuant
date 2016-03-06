package dataservice;

import java.util.List;

import enumeration.Exchange;
import enumeration.MyDate;
import po.BenchMarkPO;
import po.StockPO;

/**
 *这个类在逻辑层和数据层交互时可以起到缓冲作用
 *具体实现：
 *
 * @author ss
 * @date 2016年3月6日
 */
public class APIDataCache implements APIInterface{
	
    APIInterface   api = null;
    public  APIDataCache(APIInterface api) {
		  this.api = api;
	}
    
	@Override
	public List<String> getAllStocks() {
	    
		return null;
	}

	@Override
	public List<String> getAllStocks(int year) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllStocks(Exchange exchange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllStocks(int year, Exchange exchange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StockPO getStockMes(String stockCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StockPO> getStockMes(String stockCode, MyDate start, MyDate end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BenchMarkPO getBenchMes(String benchCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BenchMarkPO> getBenchMes(String benchCode, MyDate start, MyDate end) {
		// TODO Auto-generated method stub
		return null;
	}
	    
	    

}
