package service.impl.strategy;

import vo.ReportVO;

/**
 * Created by 67534 on 2016/6/5.
 */
public abstract class SingleStockStrategy extends BaseStrategy {



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

    @Override
    protected abstract boolean buyStocks();

    @Override
    protected abstract boolean sellStocks();
}
