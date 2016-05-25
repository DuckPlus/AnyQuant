package DAO;

import entity.StockEntity;

import java.util.List;

/**
 * @author Qiang
 * @date 16/5/6
 */
public interface StockDAO {

    List<String> getAllStockCodes();

    List<StockEntity> getAllStocks();

    StockEntity getStockEntity(String code);


}
