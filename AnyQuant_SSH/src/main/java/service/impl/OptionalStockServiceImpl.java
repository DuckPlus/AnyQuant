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

    @Override
    public List<StockdataEntity> getOptionalStocksDatas(String userID) {
        return optionalDAO.getOptionalStocksDatas(userID);
    }


    @Override
    public List<StockEntity> getOptionalStocks(String userID) {
        return optionalDAO.getOptionalStocks(userID);
    }


    @Override
    public boolean deleteStockCode(String userID, String stockCode) {
        return optionalDAO.deleteStockCode(userID  , stockCode);
    }


    @Override
    public boolean deleteStockCode(String userID, List<String> stockCode) {
        for ( String code : stockCode){
            if(!optionalDAO.deleteStockCode(userID , code)){
                return false;
            }
        }
        return true;
    }


    @Override
    public boolean addStockCode(String userID, String stockCode) {
        return optionalDAO.addStockCode(userID , stockCode);
    }


    @Override
    public boolean addStockCode(String userID, List<String> stockCodes) {
        for ( String code : stockCodes){
            if(!optionalDAO.addStockCode(userID , code)){
                return false;
            }
        }
        return true;
    }


    @Override
    public boolean clearOptionalStocks(String userID) {
        return optionalDAO.clearOptionalStocks(userID);
    }


    @Override
    public boolean ifStockExist(String userID, String stockCode) {
        return optionalDAO.ifStockExist(userID , stockCode);
    }


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


    @Override
    public Map<String, Integer> getBoardDistributionMap(String userID) {
        List<StockEntity> optionStocks = getOptionalStocks(userID);
        Map<String, Integer> regions = new HashMap<>(34);

        for (StockEntity temp : optionStocks) {
            if (regions.containsKey(temp.getBoard())) {
                regions.put(temp.getBoard(), regions.get(temp.getBoard()) + 1);
            } else {
                regions.put(temp.getBoard(), 1);
            }
        }

        return regions;
    }
}
