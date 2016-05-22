package DAO.impl;

import DAO.BaseDAO;
import DAO.BenchMarkDAO;
import entity.BenchmarkEntity;
import entity.BenchmarkdataEntity;
import entity.StockdataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import util.MyDate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ss
 * @date 16/5/11
 */
@Repository
public class BenchMarkDAOImpl implements BenchMarkDAO {
    @Autowired
    BaseDAO baseDAO;

    private static String tableName = BenchmarkEntity.class.getName();
    private static String dataTableName = BenchmarkdataEntity.class.getName();
    @Override
    public List<BenchmarkEntity> getAllBenchMarksList()
    {
        return (ArrayList<BenchmarkEntity>)
                baseDAO.getAllList(BenchmarkEntity.class);
    }

    @Override
    public List<BenchmarkdataEntity> getAllBenchMarksDataList()
    {
        String hql = "from "+dataTableName+" where  date = "+
                "(select max(date) from " +dataTableName+ ")" ;
        return (List<BenchmarkdataEntity>) baseDAO.getAllList(hql);
    }

    @Override
    public List<BenchmarkdataEntity> getRecentBenchMarks(String BenchMarkCode)
    {
        return null;
    }

    @Override
    public List<BenchmarkdataEntity> getBenchMarkByTime(String BenchMarkCode, MyDate start, MyDate end)
    {
        String hql = "from "+dataTableName+
                " where code = '"+BenchMarkCode+"' and date between '"+start.DateToString()+"' and '"+end.DateToString()+"'";

        return (List<BenchmarkdataEntity>) baseDAO.getAllList(hql);
    }

    @Override
    public String getBenchMarkCodeByName(String name) {
        return null;
    }
}
