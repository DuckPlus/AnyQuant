package service.impl.strategy;

import service.StrategyBehavior;
import util.MyDate;
import vo.ReportVO;

/**
 * Created by 67534 on 2016/5/25.
 */
public  class BaseStrategy  {

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
     * 具体的策略算法
     */
    StrategyBehavior  strategy;

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

    public void setStrategy(StrategyBehavior strategy){
          this.strategy=strategy;
    }

    public ReportVO analyse(){
        return strategy.doAnalysis();
    }

}
