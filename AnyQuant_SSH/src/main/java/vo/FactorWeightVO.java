package vo;

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
     * 用于评价因子有效性的因子(IC、IR、胜率)的值
     */
    public double judgeFactorValue;

    /**
     * 正相关 或  负相关
     */
    public boolean postive;

    public FactorWeightVO(){

    }

    public FactorWeightVO(double judgeFactorValue, String name, boolean postive) {
        this.judgeFactorValue = judgeFactorValue;
        this.name = name;
        this.postive = postive;
    }
}
