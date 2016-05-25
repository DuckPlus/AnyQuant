package service.impl.strategy;

import util.MyDate;
import vo.ReportVO;

/**
 * Created by 67534 on 2016/5/25.
 */
public abstract class BaseStrategy  {

    /**
     * 资金
     */
    public double capital;

    /**
     * 交易费率
     */
    public double taxRate;

    /**
     * 基准大盘代码
     */
    public String baseCode;

    /**
     * 回测周期开始日期
     */
    public MyDate start;

    /**
     * 回测周期结束日期
     */
    public MyDate end;

    public BaseStrategy(){}

    public BaseStrategy(double capital,double taxRate,String baseCode ,
     MyDate start , MyDate end){

        this.capital=capital;
        this.taxRate=taxRate;
        this.baseCode=baseCode;
        this.start=start;
        this.end=end;

    }

    /**
     * 初始化算法
     */
    public abstract  void init();

    /**
     * 如果是设定了调仓频率的策略，该方法即调仓时的动作
     * 如果是无需设定调仓频率的策略，该方法即算法主体
     */
    public abstract  void handleData();

    /**
     * 面向ui的接口
     * @return
     */
    public abstract ReportVO analyse();

}
