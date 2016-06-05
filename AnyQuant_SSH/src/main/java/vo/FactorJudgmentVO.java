package vo;

/**
 * @author Qiang
 * @date 6/5/16
 */
public class FactorJudgmentVO {
    /**
     * 因子名称
     */
    public String name;
    /**
     * IC值
     */
    public double valueIC;
    public double valueIR;
    public double valueT_CHECK;
    public double valueProfit;

    public FactorJudgmentVO(String name, double valueIC, double valueIR, double valueProfit, double valueT_CHECK) {
        this.name = name;
        this.valueIC = valueIC;
        this.valueIR = valueIR;
        this.valueProfit = valueProfit;
        this.valueT_CHECK = valueT_CHECK;
    }
}
