package blservice;

import vo.StockVO;

import java.security.KeyStore.Entry;
import java.util.AbstractMap.SimpleEntry;
import java.util.Iterator;
import java.util.List;

/**
 * Business Logic Interface on Optional Stocks
 * @author Qiang
 * @date 3/29/16.
 */
public interface OptionalStockBLService {
    /**
     * Get today's(or last trading day)data of the optional(自选的) stockCodes
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
    public Iterator<SimpleEntry<String , Integer>> getRegionDistribution();
    /**
     * Get the border's distribution of the optional stocks 
     * @return the iterator of a collection which contains the distribution
     */
    public Iterator<SimpleEntry<String , Integer>> getBorderDistribution();
    
    
    
}
