package service;

import entity.StockEntity;
import entity.StockdataEntity;

import java.util.List;
import java.util.Map;

/**
 * Business Logic Interface on Optional Stocks
 * @author Qiang
 * @date 3/29/16.
 */
public interface OptionalStockService {
    /**
     * Get today's(or last trading day)data of the optional stockCodes
     * @return a Collection's Iterator on StockVOs
     */
    List<StockdataEntity> getOptionalStocksDatas(String userID);
    /**
     * Get  the optional stocks
     * @return a Collection's Iterator on StockVOs
     */
    List<StockEntity> getOptionalStocks(String userID);
    /**
     * delete optional stocks
     * @param stockCode the stock to delete
     * @return if not exist, return false , if success return true
     */
    boolean deleteStockCode(String userID , String stockCode);

    /**
     * delete optional stocks
     * @param stockCode the list of the stockCodes to delete
     * @return if not exist, return false , if success return true
     */
    boolean deleteStockCode(String userID , List<String> stockCode);

    /**
     * add optional stocks
     * @param stockCode the stock to add
     * @return if this stock has existed in the optional list or the stock not actually exists , return false
     */
    boolean addStockCode(String userID , String stockCode);

    /**
     * add optional stocks
     * @param stockCodes the list of stock to add
     * @return if this stock has existed in the optional list or the stock not actually exists , return false
     */
    boolean addStockCode(String userID , List<String> stockCodes);


    /**
     * clear all the optional stocks
     * @return success or fail
     */
    boolean clearOptionalStocks(String userID);

    /**
     * if the stock exist return true
     * @param stockCode
     * @return
     */
    boolean ifStockExist(String userID , String stockCode);

    /**
     *  Get the region's distribution of the optional stocks
     * @return  map
     */
    Map<String, Integer> getRegionDistributionMap(String userID);

	   /**
     * Get the board's distribution of the optional stocks
     * @return map
     */
    Map<String, Integer> getBoardDistributionMap(String userID);




}
