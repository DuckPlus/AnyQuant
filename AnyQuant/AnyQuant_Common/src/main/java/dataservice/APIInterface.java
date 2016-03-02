package dataservice;

import java.util.ArrayList;
import java.util.List;

import po.StockPO;
import enumeration.Exchange;
import enumeration.MyDate;
import enumeration.Stock_Attribute;

public interface APIInterface {
	
public String welcomePage();
	
	public ArrayList<String> getAllStocks();
	
	public ArrayList<String> getAllStocks(int year);
	
	public ArrayList<String> getAllStocks(Exchange exchange);
	
	public ArrayList<String> getAllStocks(int year , Exchange exchange);
	
	
	public List<StockPO> getStockMes(String stockCode , Stock_Attribute... fields);
	public List<StockPO> getStockMes(String stockCode , MyDate start , MyDate end , Stock_Attribute... fields);
	
}
