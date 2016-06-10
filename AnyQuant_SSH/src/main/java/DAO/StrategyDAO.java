package DAO;

import vo.ReportVO;

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
}
