package DAO.impl;

import DAO.BaseDAO;
import DAO.UpdateDAO;
import entity.BenchmarkdataEntity;
import entity.FactorEntity;
import entity.StockdataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import util.DateCalculator;
import util.MyDate;

import java.sql.Date;

/**
 * @author Qiang
 * @date 6/9/16
 */
@Repository
public class UpdateDAOImpl implements UpdateDAO {

    @Autowired
    BaseDAO baseDAO;

    private static String factorTableName = FactorEntity.class.getName();
    private static String stockDataTableName = StockdataEntity.class.getName();
    private static String benchDataTableName = BenchmarkdataEntity.class.getName();

    private MyDate getMaxDate(String tableName) {
        String hql = " select max (date) from "+tableName;
        Date sqlDate = (Date) baseDAO.load(hql);
        MyDate myDate = DateCalculator.SQLDateToMyDate(sqlDate);
        return myDate;
    }

    @Override
    public MyDate getFactorMaxDate() {
        return getMaxDate(factorTableName);
    }

    @Override
    public MyDate getStockDataMaxDate() {
        return getMaxDate(stockDataTableName);
    }

    @Override
    public MyDate getBenchDataMaxDate() {
        return getMaxDate(benchDataTableName);
    }




}
