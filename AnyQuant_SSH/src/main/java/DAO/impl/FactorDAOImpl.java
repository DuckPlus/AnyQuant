package DAO.impl;

import DAO.BaseDAO;
import DAO.FactorDAO;
import entity.FactorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import util.DateCalculator;
import util.MyDate;
import util.ReflectHelper;
import util.enumration.AnalysisFactor;
import vo.Factor_VO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ss
 * @date 16/5/20
 */
@Repository
public class FactorDAOImpl implements FactorDAO {

    @Autowired
    BaseDAO baseDAO;

    private static String tableName = FactorEntity.class.getName();



    @Override
    public List<Factor_VO> getFactors(String stockCode, AnalysisFactor factor, MyDate start, MyDate end) {
        List<FactorEntity> tempList = getFactorBetweenDate(stockCode,start,end);
        List<Factor_VO> result = new ArrayList<>();
        for(FactorEntity entity:tempList){
            Factor_VO vo = new Factor_VO();
            vo.name = factor.chinese;
            vo.value= (double)
                    ReflectHelper.getValueByAttrName(entity,factor.name());

            vo.date= DateCalculator.SQLDateToMyDate(entity.getDate());
            result.add(vo);
        }
        return result;
    }

    @Override
    public List<FactorEntity> getFactorBetweenDate(String stockCode, MyDate start, MyDate end)
    {

        String hql = "from "+tableName+
                " where code = '"+stockCode+"' and date between '"+start.DateToString()+"' and '"+end.DateToString()+"'";

        return (List<FactorEntity>) baseDAO.getAllList(hql);
    }

    @Override
    public List<FactorEntity> getFactorBetweenDate(List<String> stockCodes, MyDate start, MyDate end) {
        String hql = "from "+tableName+" where code = ? and date between '"+start.DateToString()+"' and '"+end.DateToString()+"'";
        List result  = baseDAO.batchSingleQuery(hql, stockCodes);
        return  result;
    }

    @Override
    public List<FactorEntity> getFactorAtDate(List<String> stockCodes, MyDate date) {
        String hql = "from "+tableName+" where code = ? and date = '"+date.DateToString() +"'";
        List result  = baseDAO.batchSingleQuery(hql, stockCodes);
        return  result;
    }

    @Override
    public List<FactorEntity> getListFactors(List<String> stockCodes) {
        String hql = "from "+tableName+" where code = ? and date = "+"(select max(date) from " +tableName+")";

        List result  = baseDAO.batchSingleQuery(hql, stockCodes);
        return  result;
    }




}
