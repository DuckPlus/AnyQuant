package DAO.impl;

import DAO.BaseDAO;
import DAO.StrategyDAO;
import entity.ReportEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vo.ReportVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Qiang
 * @date 6/10/16
 */
@Repository
public class StrategyDAOImpl implements StrategyDAO {
    @Autowired
    BaseDAO baseDAO;

    private static String tableName = ReportEntity.class.getName();


    @Override
    public void saveReport(String userID, ReportVO vo) {
        byte [] bytes = baseDAO.ObjectToBytes(vo);
        ReportEntity entity = new ReportEntity();
        entity.setUserid(Integer.parseInt(userID));
        entity.setReport(bytes);
        baseDAO.saveObject(entity);
    }

    @Override
    public List<ReportVO> getAllReports(String userID)
    {
        String hql = "from "+tableName
                +" where userid = "+userID;
        List<ReportEntity> entities =
                (List<ReportEntity>) baseDAO.getAllList(hql);
        List<ReportVO> result = new ArrayList<>();
        for(ReportEntity entity:entities){
            result.add((ReportVO) baseDAO.BytesToObject(entity.getReport()));
        }
        return result;
    }
}
