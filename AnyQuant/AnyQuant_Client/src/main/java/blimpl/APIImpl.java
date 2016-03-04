package blimpl;

import java.util.List;

import vo.BenchMarkVO;
import vo.StockVO;
import enumeration.MyDate;
import enumeration.Stock_Attribute;
import blservice.APIBlservice;

/**
 *
 * @author czq
 * @date 2016年3月4日
 */
public class APIImpl implements APIBlservice {

	@Override
	public List<StockVO> getAllStocks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StockVO> getSortStocks(boolean isUp, Stock_Attribute attr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StockVO> getSortStocksInScope(boolean isUp,
			Stock_Attribute attr, List<String> stocksCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BenchMarkVO> getAllBenchMarks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StockVO> getRecentStocks(String stockCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StockVO> getStocksByTime(MyDate start, MyDate end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StockVO> getStocksByStockCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

}
