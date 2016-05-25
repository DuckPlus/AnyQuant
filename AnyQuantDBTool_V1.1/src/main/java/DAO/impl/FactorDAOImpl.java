package DAO.impl;

import DAO.BaseDAO;
import DAO.FactorDAO;
import entity.FactorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import util.DateCalculator;
import util.MyDate;

import java.sql.Date;

/**
 * Created by 67534 on 2016/5/22.
 */
@Repository
public class FactorDAOImpl implements FactorDAO{
    @Autowired
    BaseDAO baseDAO;

    private static String tableName = FactorEntity.class.getName();

    @Override
    public MyDate getMaxDate() {
        String hql = " select max (date) from "+tableName;
        Date sqlDate = (Date) baseDAO.load(hql);
        MyDate myDate = DateCalculator.SQLDateToMyDate(sqlDate);
        return myDate;
    }
}
