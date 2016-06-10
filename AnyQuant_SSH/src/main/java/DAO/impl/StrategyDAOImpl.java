package DAO.impl;

import DAO.StrategyDAO;
import org.springframework.stereotype.Repository;
import vo.ReportVO;

import java.util.List;

/**
 * @author Qiang
 * @date 6/10/16
 */
@Repository
public class StrategyDAOImpl implements StrategyDAO {
    @Override
    public void saveReport(String userID, ReportVO vo) {

    }

    @Override
    public List<ReportVO> getAllReports(String userID) {
        return null;
    }
}
