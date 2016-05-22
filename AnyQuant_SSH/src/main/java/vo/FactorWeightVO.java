package vo;

import util.MyDate;

/**
 * 因子有效性VO
 * @author Qiang
 * @date 16/5/20
 */
public class FactorWeightVO {
    /**
     * 因子名称
     */
    public String name;
    /**
     * 日期
     */
    public MyDate date;
    /**
     * 因子值
     */
    public double value;
    /**
     * 用于评价因子有效性的因子(IC、IR、胜率)的值
     */
    public double judgeFactorValue;
}
