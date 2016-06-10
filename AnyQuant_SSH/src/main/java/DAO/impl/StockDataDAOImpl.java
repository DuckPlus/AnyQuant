package DAO.impl;

import DAO.BaseDAO;
import DAO.StockDataDAO;
import entity.StockdataEntity;
import entity.StockdataEntityPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import util.DateCalculator;
import util.MyDate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


/**
 * @author ss
 * @date 16/5/7
 */
@Repository
public class StockDataDAOImpl implements StockDataDAO {

    @Autowired
    BaseDAO baseDAO;
    private static String tableName = StockdataEntity.class.getName();

    @Override
    public StockdataEntity getStockData(String stockCode)
    {
        String hql = "from "+tableName+" where  date = "+
               " (select max(date) from " +tableName+ ") and code = '"+stockCode+"'";

        return (StockdataEntity) baseDAO.load(hql);

    }

    @Override
    public List<StockdataEntity> getStockData(List<String> stockCodes) {
        String hql = "from "+tableName+" where code = ? and date = "+"(select max(date) from " +tableName+")";
        List result  = baseDAO.batchSingleQuery(hql, stockCodes);
        return  result;
    }

    @Override
    public StockdataEntity getStockData(String stockCode, MyDate date)
    {

        StockdataEntityPK stockDataKey = getStockKey(stockCode,date);
        StockdataEntity entity = (StockdataEntity) baseDAO.load(StockdataEntity.class,stockDataKey);
        return entity;
    }

    @Override
    public List<StockdataEntity> getStockData(List<String> stockCodes, MyDate date)

    {
        String hql = "from "+tableName+" where code = ? and date = '"+date.DateToString()+"'";
        List result  = baseDAO.batchSingleQuery(hql,stockCodes);
        return result;
    }

    @Override
    public List<StockdataEntity> getStockData(String stockCode, MyDate start, MyDate end)
    {
        String hql = "from "+tableName+
                " where code = '"+stockCode+"' and date between '"+start.DateToString()+"' and '"+end.DateToString()+"'";

        return (List<StockdataEntity>) baseDAO.getAllList(hql);
    }

    @Override
    public List<StockdataEntity> getStockData(List<String> stockCodes, MyDate start, MyDate end)
    {
        String hql = "from "+tableName+" where code = ? and date between '"+start.DateToString()+"' and '"+end.DateToString()+"'";
        List result  = baseDAO.batchListQuery(hql,stockCodes);
        return result;

    }

    @Override
    public List<StockdataEntity> getAllStockData()
    {
        String hql = "from "+tableName+" where  date = "+
                "(select max(date) from " +tableName+ ")" ;
        return (List<StockdataEntity>) baseDAO.getAllList(hql);
    }


    private StockdataEntityPK getStockKey(String stockCode, MyDate date){
        Date sqlDate = DateCalculator.MyDateToSQLDate(date);
        StockdataEntityPK stockDataKey = new StockdataEntityPK();
        stockDataKey.setCode(stockCode);
        stockDataKey.setDate(sqlDate);
        return stockDataKey;
    }




    @Override
    public List<String> getStockCodeByPE(MyDate date,double low_PE , double high_PE) {
        String hql = "select code from "+tableName+
                " where pe>= "+low_PE+" and pe<= "+high_PE+
                " and date = '"+date.DateToString()+"'"
                +"and turnoverValue >100000";
        return (List<String>) baseDAO.getAllList(hql);
    }

    @Override
    public double [] getAvgPriceByCodes(List<String> codes, MyDate date) {

        String hql = "select turnoverValue / turnoverVol from "+tableName+
                " where code = ? and date = '"+date.DateToString()+"'";
        List result  = baseDAO.batchSingleQuery_Exact(hql,codes);
        double [] prices = new double [codes.size()];
        for(int i=0;i<codes.size();i++){
            if(result.get(i)!=null){
                prices[i]= (double) result.get(i);
            }
        }
        return prices;

    }

    @Override
    public double getAvgPriceByCode(String code, MyDate date) {
        String hql = "select turnoverValue / turnoverVol from "
                +tableName+" where code = '"+code+"' and date = '"+date.DateToString()+"'"
                + " and turnoverVol > 0";
        Object temp = baseDAO.load(hql);
        return  (temp==null? 0: (double)temp);
    }

    @Override
    public List<String> getStockCodeByVolDec( MyDate start, MyDate end, int vol) {

        String hql = "select distinct code from "
                +tableName+
                " where  date between '"+start.DateToString()+"' and '"+end.DateToString()+"'"
                +" and turnoverVol > 0 and turnoverValue > 100000 "
                +" order by turnoverVol asc";
        return (List<String>) baseDAO.getAllList(hql,vol);

    }


    @Override
    public List<MyDate> getTradeDates(MyDate start, MyDate end) {
        String hql = "select distinct date from "+tableName+
                " where date between '"+start.DateToString()+"' and '"+end.DateToString()+"'"+
                " order by date asc";
        List<Date> dates = (List<Date>) baseDAO.getAllList(hql);
        List<MyDate> myDates = new ArrayList<>();
        for(Date date:dates){
            if(date!=null){
                MyDate myDate = DateCalculator.SQLDateToMyDate(date);
                myDates.add(myDate);
            }
        }
        return myDates;
    }


}
