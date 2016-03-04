package blimpl;

import java.util.Comparator;

import vo.StockVO;

/**
 *
 * @author czq
 * @date 2016年3月4日
 */
public class StockSortHelper {
	
	static sortByVolume sortByVolume(){
		return new sortByVolume();
	}
	
	/*
	 * 股票属性
	 * open: 开盘价  high: 最高价 low: 最低价 close: 收盘价 adj_price: 后复权价 volume: 成交量
	 * turnover: 换手率 pe: 市盈率 pb: 市净率
	 *
	 */
	public static class sortByVolume implements Comparator<StockVO>{
		
		
		@Override
		public int compare(StockVO o1, StockVO o2) {
			// TODO Auto-generated method stub
			return 0;
		}

		
	}
}
