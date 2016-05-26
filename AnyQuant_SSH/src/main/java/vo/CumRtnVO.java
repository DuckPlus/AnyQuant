package vo;

import util.MyDate;

/**
 * Created by 67534 on 2016/5/25.
 * 累积收益率
 *
 */
public class CumRtnVO {
    /**
     * 基线值
     */
    double baseValue;
    /**
     * 回测值
     */
    double testValue;
    /**
     * 日期
     */
    MyDate date;

    public CumRtnVO(){}

    public CumRtnVO(double base,double test ,MyDate date){
        this.baseValue=base;
        this.testValue=test;
        this.date=date;
    }
}
