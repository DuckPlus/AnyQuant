package DAO.impl;

import DAO.OptionalDAO;
import entity.StockEntity;
import entity.StockdataEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ss
 * @date 16/5/11
 */
@Repository
public class OptionalDAOImpl implements OptionalDAO {
    /**
     * Get today's(or last trading day)data of the optional stockCodes
     *
     * @param userID
     * @return a Collection's Iterator on StockVOs
     */
    @Override
    public List<StockdataEntity> getOptionalStocksDatas(String userID) {
        return null;
    }

    /**
     * Get  the optional stocks
     *
     * @param userID
     * @return a Collection's Iterator on StockVOs
     */
    @Override
    public List<StockEntity> getOptionalStocks(String userID) {
        return null;
    }

    /**
     * delete optional stocks
     *
     * @param userID
     * @param stockCode the stock to delete
     * @return if not exist, return false , if success return true
     */
    @Override
    public boolean deleteStockCode(String userID, String stockCode) {
        return false;
    }

    /**
     * add optional stocks
     *
     * @param userID
     * @param stockCode the stock to add
     * @return if this stock has existed in the optional list or the stock not actually exists , return false
     */
    @Override
    public boolean addStockCode(String userID, String stockCode) {
        return false;
    }

    /**
     * clear all the optional stocks
     *
     * @param userID
     * @return success or fail
     */
    @Override
    public boolean clearOptionalStocks(String userID) {
        return false;
    }

    /**
     * if the stock exist return true
     *
     * @param userID
     * @param stockCode
     * @return
     */
    @Override
    public boolean ifStockExist(String userID, String stockCode) {
        return false;
    }
}
