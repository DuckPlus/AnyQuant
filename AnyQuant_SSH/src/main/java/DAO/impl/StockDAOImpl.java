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
    @Override
    public List<StockEntity> findAllStocks() {
        return (List<StockEntity>) baseDAO.getAllList(StockEntity.class);
    }

    @Override
    public StockEntity getStockEntity(String code)
    {
        return (StockEntity) baseDAO.load(StockEntity.class,code);
    }
}
