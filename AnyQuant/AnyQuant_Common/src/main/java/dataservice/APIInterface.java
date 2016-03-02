package dataservice;

import java.util.ArrayList;
import java.util.List;

import po.StockPO;
import enumeration.Exchange;
import enumeration.MyDate;
import enumeration.Stock_Attribute;
/**
 * API接口定义
 * @author ss,czq
 *
 */
public interface APIInterface {
	
	/**
	 * 下列重载方法用于获取股票代码
	 * Exchange 为交易所枚举类型
	 * @return
	 */
	public ArrayList<String> getAllStocks();
	
	public ArrayList<String> getAllStocks(int year);
	
	public ArrayList<String> getAllStocks(Exchange exchange);
	
	public ArrayList<String> getAllStocks(int year , Exchange exchange);
	
	/**
	 * 下列方法用于获取某只股票的具体信息，参数的个数为任意多的Stock_Attribute枚举类型
	 * @param stockCode   股票代码
	 * @param fields   属性
	 * @return  
	 */
	public List<StockPO> getStockMes(String stockCode , Stock_Attribute... fields);
	public List<StockPO> getStockMes(String stockCode , MyDate start , MyDate end , Stock_Attribute... fields);
	
}
