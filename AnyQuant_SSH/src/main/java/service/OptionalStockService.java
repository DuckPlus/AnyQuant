package service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import entity.StockEntity;
import entity.StockdataEntity;

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
    List<StockdataEntity> getOptionalStocks();
    /**
     * delete optional stocks
     * @param stockCode the stock to delete
     * @return if not exist, return false , if success return true
     */
    boolean deleteStockCode(String stockCode);

    /**
     * delete optional stocks
     * @param stockCode the list of the stockCodes to delete
     * @return if not exist, return false , if success return true
     */
    boolean deleteStockCode(List<String> stockCode);

    /**
     * add optional stocks
     * @param stockCode the stock to add
     * @return if this stock has existed in the optional list or the stock not actually exists , return false
     */
    boolean addStockCode(String stockCode);

    /**
     * add optional stocks
     * @param stockCodes the list of stock to add
     * @return if this stock has existed in the optional list or the stock not actually exists , return false
     */
    boolean addStockCode(List<String> stockCodes);


    /**
     * clear all the optional stocks
     * @return success or fail
     */
    boolean clearOptionalStocks();

    /**
     * if the stock exist return true
     * @param stockCode
     * @return
     */
    boolean ifStockExist(String stockCode);

    /**
     *  Get the region's distribution of the optional stocks
     * @return  map
     */
    Map<String, Integer> getRegionDistributionMap();

	   /**
     * Get the board's distribution of the optional stocks
     * @return map
     */
    Map<String, Integer> getBoardDistributionMap();




}
