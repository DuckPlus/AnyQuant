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
import vo.BenchMarkVO;
import vo.StockVO;
import enumeration.MyDate;
import enumeration.Stock_Attribute;
import blimpl.APIBlImpl;
import blimpl.VOPOchange;
import blservice.APIBlservice;

public class MockAPIBlImpl implements APIBlservice{
	/**
	 * 单例模式
	 */
	private static APIBlservice APIBlservice;
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
	
	public static APIBlservice getAPIBLService(){
		if(APIBlservice == null){
			APIBlservice = new MockAPIBlImpl();
		}
		return APIBlservice;
	}
	
	private MockAPIBlImpl() {
		
	}
	@Override
	public Iterator<StockVO> getAllStocks() {
		// TODO Auto-generated method stub
		return null;
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
		StockVO vo;
		for(int i=0;i<29;i++){
			vo=new StockVO("2015-1-"+(i+1), "石化油服", 50, 10.00, 23.12, 24.15, 2, 25330, 0.015, 10, 10, 0.04, 0.03);
			vos.add(vo);
		}
		vo=new StockVO("2015-1-"+(30), "石化油服", 26, 18.00, 25.89, 22.95, 3, 283, 0.015, 10, 10, 0.54, -0.18);
		vos.add(vo);
		return vos.iterator();
	}

	@Override
	public Iterator<StockVO> getStocksByTime(String stockCode, MyDate start,
			MyDate end) {
		List<StockVO> vos=new ArrayList<StockVO>();
		StockVO vo;
		for(int i=0;i<5;i++){
			vo=new StockVO("2015-1-"+(2*i+1), "石化油服", 50, 10.00, 23.12, 24.15, 2, 25330, 0.015, 10, 10, 0.04, 0.03);
			vos.add(vo);
		}
		return vos.iterator();
	}

	@Override
	public Iterator<StockVO> getStocksByStockCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

}

