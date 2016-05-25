package service.impl.strategy;

import service.StrategyBehavior;
import vo.ReportVO;

/**
 * Created by 67534 on 2016/5/25.
 */
public class Strategy_PE implements StrategyBehavior {
    /**
     * 调仓间隔
     */
    public int interval;

    public Strategy_PE(int interval){
        this.interval = interval;
    }


    @Override
    public ReportVO doAnalysis() {
        return null;
    }
}
