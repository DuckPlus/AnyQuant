package service.impl.strategy;

import vo.ReportVO;

/**
 * 因子选股策略
 * 根据因子评价策略选出较为有效的因子,之后根据这个因子以及用户给定的其他一些参数:调仓频率,交易费率等等,
 * 提供给用户回测的数据,包括各个因子的情况
 * @author Qiang
 * @date 6/5/16
 */
public class FactorStrategy extends MultiStockStrategy {







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
    protected void buyStocks() {

    }
}
