package service.impl;

import DAO.OptionalDAO;
import entity.StockEntity;
import entity.StockdataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.OptionalStockService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Qiang
 * @date 16/5/10
 */
@Service
@Transactional
public class OptionalStockServiceImpl implements OptionalStockService {
    @Autowired
    OptionalDAO optionalDAO;
    /**
     * Get today's(or last trading day)data of the optional stockCodes
     *
     * @param userID
     * @return a Collection's Iterator on StockVOs
     */
    @Override
    public List<StockdataEntity> getOptionalStocksDatas(String userID) {
        return optionalDAO.getOptionalStocksDatas(userID);
    }

    /**
     * Get  the optional stocks
     *
     * @param userID
     * @return a Collection's Iterator on StockVOs
     */
    @Override
    public List<StockEntity> getOptionalStocks(String userID) {
        return optionalDAO.getOptionalStocks(userID);
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
        return optionalDAO.deleteStockCode(userID  , stockCode);
    }

    /**
     * delete optional stocks
     *
     * @param userID
     * @param stockCode the list of the stockCodes to delete
     * @return if not exist, return false , if success return true
     */
    @Override
    public boolean deleteStockCode(String userID, List<String> stockCode) {
        for ( String code : stockCode){
            if(!optionalDAO.deleteStockCode(userID , code)){
                return false;
            }
        }
        return true;
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
        return optionalDAO.addStockCode(userID , stockCode);
    }

    /**
     * add optional stocks
     *
     * @param userID
     * @param stockCodes the list of stock to add
     * @return if this stock has existed in the optional list or the stock not actually exists , return false
     */
    @Override
    public boolean addStockCode(String userID, List<String> stockCodes) {
        for ( String code : stockCodes){
            if(!optionalDAO.addStockCode(userID , code)){
                return false;
            }
        }
        return true;
    }

    /**
     * clear all the optional stocks
     *
     * @param userID
     * @return success or fail
     */
    @Override
    public boolean clearOptionalStocks(String userID) {
        return optionalDAO.clearOptionalStocks(userID);
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
        return optionalDAO.ifStockExist(userID , stockCode);
    }

    /**
     * Get the region's distribution of the optional stocks
     *
     * @param userID
     * @return map
     */
    @Override
    public Map<String, Integer> getRegionDistributionMap(String userID) {
        List<StockEntity> optionStocks = getOptionalStocks(userID);
        Map<String, Integer> regions = new HashMap<>(34);

        for (StockEntity temp : optionStocks) {
            if (regions.containsKey(temp.getRegion())) {
                regions.put(temp.getRegion(), regions.get(temp.getRegion()) + 1);
            } else {
                regions.put(temp.getRegion(), 1);
            }
        }

        return regions;
    }

    /**
     * Get the board's distribution of the optional stocks
     *
     * @param userID
     * @return map
     */
    @Override
    public Map<String, Integer> getBoardDistributionMap(String userID) {
        return null;
    }
}
