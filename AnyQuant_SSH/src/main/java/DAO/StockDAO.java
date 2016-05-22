package DAO;

import entity.StockEntity;

import java.util.List;

/**
 * @author Qiang
 * @date 16/5/6
 */
public interface StockDAO {

    List<StockEntity> getAllStocks();
    List<String> getAllStockCodes();

    StockEntity getStockEntity(String code);

    /**
     * 获得某个板块下所有股票的代码
     * @param boardName 板块名称
     * @return 所有股票的代码
     */
    List<String> getBoardRealatedStockCodes(String boardName);
    List<StockEntity> getBoardRelatedStock(String boardName);
    List<StockEntity> getRegionRelatedStock(String regionName);

}
