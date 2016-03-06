package dataservice;

import java.util.List;

import po.StockPO;
import enumeration.Exchange;
import enumeration.MyDate;

/**
 *
 * @author czq
 * @date 2016年3月6日
 */
public class APIDataCache implements APIInterface{

	@Override
	public List<String> getAllStocks() {
		// TODO Auto-generated method stub
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
	
}
