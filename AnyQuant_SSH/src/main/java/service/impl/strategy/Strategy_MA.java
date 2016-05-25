package service.impl.strategy;

import service.StrategyBehavior;
import vo.ReportVO;

/**
 * Created by 67534 on 2016/5/25.
 */
public class Strategy_MA implements StrategyBehavior{

    public String stockCode;

    public Strategy_MA(String stockCode){
        this.stockCode=stockCode;
    }

    @Override
    public ReportVO doAnalysis() {
        return null;
    }
}
