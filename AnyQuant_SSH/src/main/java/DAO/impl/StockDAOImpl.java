package DAO.impl;

import DAO.BaseDAO;
import DAO.StockDAO;
import entity.StockEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Qiang
 * @date 16/5/6
 */
@Repository
public class StockDAOImpl implements StockDAO {

    @Autowired
    BaseDAO baseDAO;
    private static String tableName = StockEntity.class.getName();


    @Override
    public List<StockEntity> getAllStocks()
    {
        return (List<StockEntity>) baseDAO.getAllList(StockEntity.class);
    }

    @Override
    public List<String> getAllStockCodes()
    {
        String hql = "select code from "+ tableName;
        return (List<String>) baseDAO.getAllList(hql);
    }

    @Override
    public StockEntity getStockEntity(String code)
    {
        return (StockEntity) baseDAO.load(StockEntity.class,code);
    }

    @Override
    public List<StockEntity> getBoardRelatedStock(String boardName)
    {

        String hql = "from "+tableName+" where board = '"+boardName+"'" ;

        return (List<StockEntity>) baseDAO.getAllList(hql);
    }

    @Override
    public List<StockEntity> getRegionRelatedStock(String regionName)

    {
        String hql = "from "+tableName+" where region = '"+regionName+"'" ;

        return (List<StockEntity>) baseDAO.getAllList(hql);
    }
}
