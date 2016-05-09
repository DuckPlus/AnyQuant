package service.impl;

import entity.StockdataEntity;
import service.OptionalStockService;

import java.util.List;
import java.util.Map;

/**
 * @author Qiang
 * @date 16/5/8
 */
public class OptionalStockServiceImpl implements OptionalStockService {
    /**
     * Get today's(or last trading day)data of the optional stockCodes
     *
     * @return a Collection's Iterator on StockVOs
     */
    @Override
    public List<StockdataEntity> getOptionalStocks() {
        return null;
    }

    /**
     * delete optional stocks
     *
     * @param stockCode the stock to delete
     * @return if not exist, return false , if success return true
     */
    @Override
    public boolean deleteStockCode(String stockCode) {
        return false;
    }

    /**
     * delete optional stocks
     *
     * @param stockCode the list of the stockCodes to delete
     * @return if not exist, return false , if success return true
     */
    @Override
    public boolean deleteStockCode(List<String> stockCode) {
        return false;
    }

    /**
     * add optional stocks
     *
     * @param stockCode the stock to add
     * @return if this stock has existed in the optional list or the stock not actually exists , return false
     */
    @Override
    public boolean addStockCode(String stockCode) {
        return false;
    }

    /**
     * add optional stocks
     *
     * @param stockCodes the list of stock to add
     * @return if this stock has existed in the optional list or the stock not actually exists , return false
     */
    @Override
    public boolean addStockCode(List<String> stockCodes) {
        return false;
    }

    /**
     * clear all the optional stocks
     *
     * @return success or fail
     */
    @Override
    public boolean clearOptionalStocks() {
        return false;
    }

    /**
     * if the stock exist return true
     *
     * @param stockCode
     * @return
     */
    @Override
    public boolean ifStockExist(String stockCode) {
        return false;
    }

    /**
     * Get the region's distribution of the optional stocks
     *
     * @return map
     */
    @Override
    public Map<String, Integer> getRegionDistributionMap() {
        return null;
    }

    /**
     * Get the board's distribution of the optional stocks
     *
     * @return map
     */
    @Override
    public Map<String, Integer> getBoardDistributionMap() {
        return null;
    }
}
