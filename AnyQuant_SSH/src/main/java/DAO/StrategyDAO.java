package DAO;

import vo.ReportVO;

import java.util.List;

/**
 *
 * @author Qiang
 * @date 6/10/16
 */
public interface StrategyDAO {

    /**
     * 保存策略报告
     */
    void saveReport(String userID , ReportVO vo);

    /**
     * 获得用户的所有策略
     * @param userID userID
     */
    List<ReportVO> getAllReports(String userID);
}
