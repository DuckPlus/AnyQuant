package service.impl.strategy;

import vo.ReportVO;

/**
 * Created by 67534 on 2016/5/25.
 */
public class Strategy_PE extends BaseStrategy {
    /**
     * 调仓间隔
     */
    public int interval;

    public Strategy_PE(int interval){
        this.interval = interval;
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
