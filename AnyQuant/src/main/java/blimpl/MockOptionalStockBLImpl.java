/**
 * @author:dsn
 * @date:2016年4月9日
 */
package blimpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import blservice.OptionalStockBLService;
import dataservice.OptionalStockDataService;
import vo.StockVO;

public class MockOptionalStockBLImpl implements OptionalStockBLService {

	/**
	 * 单例模式
	 */
	private static OptionalStockBLService bl;
	private OptionalStockDataService APIDataSer;

	private List<StockVO> optionStocks;
	private Map<String, StockVO> optionalStockMap;
	private Map<String, Integer> regions;
	private Map<String, Integer> boards;

	public static OptionalStockBLService getOptionalBLService() {
		if (bl == null) {
			bl = new MockOptionalStockBLImpl();
		}
		return bl;
	}

	/* (non-Javadoc)
	 * @see blservice.OptionalStockBLService#getOptionalStocks()
	 */
	@Override
	public Iterator<StockVO> getOptionalStocks() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see blservice.OptionalStockBLService#deleteStockCode(java.lang.String)
	 */
	@Override
	public boolean deleteStockCode(String stockCode) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see blservice.OptionalStockBLService#deleteStockCode(java.util.List)
	 */
	@Override
	public boolean deleteStockCode(List<String> stockCode) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see blservice.OptionalStockBLService#addStockCode(java.lang.String)
	 */
	@Override
	public boolean addStockCode(String stockCode) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see blservice.OptionalStockBLService#addStockCode(java.util.List)
	 */
	@Override
	public boolean addStockCode(List<String> stockCodes) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see blservice.OptionalStockBLService#clearOptionalStocks()
	 */
	@Override
	public boolean clearOptionalStocks() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see blservice.OptionalStockBLService#getRegionDistribution()
	 */
	@Override
	public Iterator<Entry<String, Integer>> getRegionDistribution() {
		regions = new HashMap<>(34);
	    regions.put("上海", 1);
	    regions.put("无锡", 8);
	    regions.put("南京", 20);
	    regions.put("沈阳", 10);
	    regions.put("深圳", 3);
//		StockVO temp;
//			temp = optionStocks.get(i);
//			if (regions.containsKey(temp.region)) {
//				regions.put(temp.region, regions.get(temp.region).intValue() + 1);
//			} else {
//
//			}

		return regions.entrySet().iterator();
	}

	/* (non-Javadoc)
	 * @see blservice.OptionalStockBLService#getBorderDistribution()
	 */
	@Override
	public Iterator<Entry<String, Integer>> getBorderDistribution() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see blservice.OptionalStockBLService#ifStockExist(java.lang.String)
	 */
	@Override
	public boolean ifStockExist(String stockCode) {
		// TODO Auto-generated method stub
		return false;
	}

}
