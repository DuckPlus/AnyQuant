package DAO.impl;

import DAO.BaseDAO;
import DAO.StockDataDAO;
import entity.StockdataEntity;
import entity.StockdataEntityPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import util.MyDate;
import util.DateCalculator;

import java.sql.Date;
import java.util.List;

/**
 * Created by 67534 on 2016/5/7.
 */
@Repository
public class StockDataDAOImpl implements StockDataDAO {

    @Autowired
    BaseDAO baseDAO;

    @Override
    public StockdataEntity getStockData(String stockCode)
    {

        return null;
    }

    @Override
    public StockdataEntity getStockData(String stockCode, MyDate date)
    {

        StockdataEntityPK stockDataKey = getStockKey(stockCode,date);
        StockdataEntity entity = (StockdataEntity) baseDAO.load(StockdataEntity.class,stockDataKey);
        return entity;
    }

    @Override
    public List<StockdataEntity> getStockData(String stockCode, MyDate start, MyDate end) {
        return null;
    }

    @Override
    public List<StockdataEntity> getAllStockData() {
        return null;
    }



    private StockdataEntityPK getStockKey(String stockCode, MyDate date){
        Date sqlDate = DateCalculator.MyDateToSQLDate(date);
        StockdataEntityPK stockDataKey = new StockdataEntityPK();
        stockDataKey.setCode(stockCode);
        stockDataKey.setDate(sqlDate);
        return stockDataKey;
    }
}
