package service;

import vo.ReportVO;
import vo.StrategyArgumentVO;

/**
 * 用户策略处理逻辑层接口
 * @author Qiang
 * @date 16/5/19
 */
public interface StrategyService {

    ReportVO doAnalysis(StrategyArgumentVO vo);


}
