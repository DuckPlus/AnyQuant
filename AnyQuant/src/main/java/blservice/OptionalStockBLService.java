package blservice;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import vo.StockVO;

/**
 * Business Logic Interface on Optional Stocks
 * @author Qiang
 * @date 3/29/16.
 */
public interface OptionalStockBLService {
    /**
     * Get today's(or last trading day)data of the optional stockCodes
     * @return a Collection's Iterator on StockVOs
     */
    public Iterator<StockVO> getOptionalStocks();
    /**
     * delete optional stocks
     * @param stockCode the stock to delete
     * @return if not exist, return false , if success return true
     */
    public boolean deleteStockCode(String stockCode);

    /**
     * delete optional stocks
     * @param stockCode the list of the stockCodes to delete
     * @return if not exist, return false , if success return true
     */
    public boolean deleteStockCode(List<String> stockCode);

    /**
     * add optional stocks
     * @param stockCode the stock to add
     * @return if this stock has existed in the optional list or the stock not actually exists , return false
     */
    public boolean addStockCode(String stockCode);

    /**
     * add optional stocks
     * @param stockCodes the list of stock to add
     * @return if this stock has existed in the optional list or the stock not actually exists , return false
     */
    public boolean addStockCode(List<String> stockCodes);


    /**
     * clear all the optional stocks
     * @return success or fail
     */
    public boolean clearOptionalStocks();


    /**
     * Get the region's distribution of the optional stocks
     * @return the iterator of a collection which contains the distribution
     */
    public Iterator<Map.Entry<String, Integer>> getRegionDistribution();
    /**
     * Get the border's distribution of the optional stocks
     * @return the iterator of a collection which contains the distribution
     */
    public Iterator<Map.Entry<String, Integer>> getBorderDistribution();
    /**
     * if the stock exist return true
     * @param stockCode
     * @return
     */
    public boolean ifStockExist(String stockCode);



}
