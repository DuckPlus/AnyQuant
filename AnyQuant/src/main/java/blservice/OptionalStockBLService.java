package blservice;

import vo.StockVO;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Qiang on 3/29/16.
 */
public interface OptionalStockBLService {
    /**
     * Get today's(or last trading day)data of the optional(自选的) stockCodes
     * @return
     */
    public Iterator<StockVO> getOptionalStocks();
    /**
     * delete optional stocks
     * @param stockCode
     * @return if not exist, return false , if success return true
     */
    public boolean deleteStockCode(String stockCode);

    /**
     * delete optional stocks
     * @param stockCode the list of the stockCodes
     * @return if not exist, return false , if success return true
     */
    public boolean deleteStockCode(List<String> stockCode);

    /**
     * add optional stocks
     * @param stockCode
     * @return if this stock has existed in the optional list or the stock not actually exists , return false
     */
    public boolean addStockCode(String stockCode);

    /**
     * add optional stocks
     * @param stockCodes
     * @return if this stock has existed in the optional list or the stock not actually exists , return false
     */
    public boolean addStockCode(List<String> stockCodes);
}
