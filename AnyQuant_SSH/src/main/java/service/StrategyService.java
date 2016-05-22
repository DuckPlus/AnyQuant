package service;

import vo.StrategyArgumentVO;

import javax.security.auth.callback.Callback;

/**
 * 用户策略处理逻辑层接口
 * @author Qiang
 * @date 16/5/19
 */
public interface StrategyService {

    void doAnalysis(StrategyArgumentVO vo , Callback handler);


}
