package DAO.impl;

import DAO.BaseDAO;
import DAO.OptionalDAO;
import DAO.StockDAO;
import DAO.StockDataDAO;
import entity.OptionalstockEntity;
import entity.OptionalstockEntityPK;
import entity.StockEntity;
import entity.StockdataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
/**
 * @author ss
 * @date 16/5/11
 */
@Repository
public class OptionalDAOImpl implements OptionalDAO {

    @Autowired
    BaseDAO baseDAO;
    @Autowired
    StockDataDAO stockDataDAO;
    @Autowired
    StockDAO stockDAO;
    private static String tableName = OptionalstockEntity.class.getName();

    /**
     * Get today's(or last trading day)data of the optional stockCodes
     *
     * @param userID
     * @return a Collection's Iterator on StockVOs
     */
    @Override
    public List<StockdataEntity> getOptionalStocksDatas(String userID)
    {   ArrayList<StockdataEntity> result = new ArrayList<>();

        ArrayList<String> codes =
                (ArrayList<String>) getSelectedStockCodes(userID);
        if(codes == null){
            return null;
        }

        for(String code : codes){
            StockdataEntity temp =stockDataDAO.getStockData(code);
            result.add(temp);
        }
        return result;
    }

    /**
     * Get  the optional stocks
     *
     * @param userID
     * @return a Collection's Iterator on StockVOs
     */
    @Override
    public List<StockEntity> getOptionalStocks(String userID)
    {
        ArrayList<StockEntity> result = new ArrayList<>();

        ArrayList<String> codes =
                (ArrayList<String>) getSelectedStockCodes(userID);

        if(codes == null){
            return null;
        }


        for(String code : codes){
            StockEntity temp =stockDAO.getStockEntity(code);
            result.add(temp);
        }
        return result;
    }

    /**
     * delete optional stocks
     *
     * @param userID
     * @param stockCode the stock to delete
     * @return if not exist, return false , if success return true
     */
    @Override
    public boolean deleteStockCode(String userID, String stockCode)
    {
        if(ifStockExist(userID,stockCode)){
            OptionalstockEntity bean =
                    createOptionalStockEntity(userID,stockCode);
            baseDAO.delete(bean);
            return true;
        }

        return false;
    }

    /**
     * add optional stocks
     *
     * @param userID
     * @param stockCode the stock to add
     * @return if this stock has existed in the optional list or the stock not actually exists , return false
     */
    @Override
    public boolean addStockCode(String userID, String stockCode)
    {
        if(ifStockExist(userID,stockCode)){
            return true;
        }else{
            OptionalstockEntity bean =
                    createOptionalStockEntity(userID,stockCode);
            baseDAO.save(bean);
            return true;
        }

    }

    /**
     * clear all the optional stocks
     *
     * @param userID
     * @return success or fail
     */
    @Override
    public boolean clearOptionalStocks(String userID)
    {
        String hql = "delete   from "+tableName+" where userID = "+userID;
        int result = baseDAO.updateByHQL(hql);
        return result>0? true : false;
    }

    /**
     * if the stock exist return true
     *
     * @param userID
     * @param stockCode
     * @return
     */
    @Override
    public boolean ifStockExist(String userID, String stockCode)
    {
        OptionalstockEntityPK bean = new OptionalstockEntityPK();
        bean.setCode(stockCode);
        bean.setUserId(Integer.parseInt(userID));
        OptionalstockEntity temp = (OptionalstockEntity) baseDAO.load
                (OptionalstockEntity.class,bean);

        return temp==null? false: true;
    }


    private List<String> getSelectedStockCodes(String userID){
        String hql = "select code from "+tableName+" where userID = "+userID;
        ArrayList<String> codes = (ArrayList<String>) baseDAO.getAllList(hql);
        return codes;
    }

    private OptionalstockEntity createOptionalStockEntity(String userID ,String stockCode)
    {
        OptionalstockEntity bean = new OptionalstockEntity();
        bean.setCode(stockCode);
        bean.setUserId(Integer.parseInt(userID));
        return bean;
    }

}
