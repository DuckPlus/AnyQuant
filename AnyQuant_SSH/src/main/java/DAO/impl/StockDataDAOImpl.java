package DAO.impl;

import DAO.BaseDAO;
import DAO.StockDataDAO;
import entity.StockdataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import util.MyDate;

import java.util.List;

/**
 * Created by 67534 on 2016/5/7.
 */
public class StockDataDAOImpl implements StockDataDAO {
    @Autowired
    BaseDAO baseDAO;

    @Override
    public StockdataEntity getStockData(String stockCode) {
        return null;
    }

    @Override
    public StockdataEntity getStockData(String stockCode, MyDate date) {
        return null;
    }

    @Override
    public List<StockdataEntity> getStockData(String stockCode, MyDate start, MyDate end) {
        return null;
    }

    @Override
    public List<StockdataEntity> getAllStockData() {
        return null;
    }
}
