package service.impl.strategy;

import vo.ReportVO;

/**
 * Created by 67534 on 2016/5/25.
 */
public class Strategy_MA extends BaseStrategy{

    public String stockCode;

    public Strategy_MA(String stockCode){
        this.stockCode=stockCode;
    }


    @Override
    public void init() {

    }

    @Override
    public void handleData() {

    }

    @Override
    public ReportVO analyse() {
        return null;
    }
}
