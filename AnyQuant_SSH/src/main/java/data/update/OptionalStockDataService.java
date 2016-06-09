package data.update;




import util.update.po.StockPO;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author ss
 * @date 2016年4月7日
 */
public interface OptionalStockDataService {
	/**
	 * Get the customize stocks' last transaction data
	 * @return the iteration of the collection that contains the optional stocks with the latest data
	 */
	public Iterator<StockPO> getOptionalStocks() ;

	/**
	 * delete stockCode of the customize stocks
	 * @return whether the operation is successfully done<br>
	 * 		            when the stockCode doesn't exist,return false
	 */
	public boolean deleteOptionalStock(String stockCode);
	/**
	 * add stockCode of the customize stocks
	 * @param stockCode
	 * @return whether the operation is successfully done<br>
	 * 		            when the stockCode doesn't exist,or it has exist in the collection ,return false
	 */
	public boolean addOptionalStock(String stockCode);

	public List<String> getSelectedStockCodes();
	/**
	 * clear all the optional stocks
	 * @return success or not
	 */
	public boolean clearOptionalStocks();
}
