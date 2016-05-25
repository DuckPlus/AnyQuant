package DAO;

import entity.StockEntity;
import entity.StockdataEntity;

import java.util.List;

/**
 * @author Qiang
 * @date 16/5/11
 */
public interface OptionalDAO {
    /**
     * Get today's(or last trading day)data of the optional stockCodes
     *
     * @param userID
     * @return a Collection's Iterator on StockVOs
     */
    List<StockdataEntity> getOptionalStocksDatas(String userID);

    /**
     * Get  the optional stocks
     *
     * @param userID
     * @return a Collection's Iterator on StockVOs
     */
    List<StockEntity> getOptionalStocks(String userID);

    /**
     * delete optional stocks
     *
     * @param userID
     * @param stockCode the stock to delete
     * @return if not exist, return false , if success return true
     */
    boolean deleteStockCode(String userID, String stockCode);

    /**
     * add optional stocks
     *
     * @param userID
     * @param stockCode the stock to add
     * @return if this stock has existed in the optional list or the stock not actually exists , return false
     */
    boolean addStockCode(String userID, String stockCode);


    /**
     * clear all the optional stocks
     *
     * @param userID
     * @return success or fail
     */

    boolean clearOptionalStocks(String userID);

    /**
     * if the stock exist return true
     *
     * @param userID
     * @param stockCode
     * @return
     */
    boolean ifStockExist(String userID, String stockCode);
}
