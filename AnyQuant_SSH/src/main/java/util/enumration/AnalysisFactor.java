package util.enumration;

/**
 * 评估因子
 * @author Qiang
 * @date 16/5/19
 */
public enum AnalysisFactor {
    /**
     *
     */
    PE("市盈率"),
    /**
     *
     */
    PB("市净率"),
    /**
     *
     */
    VOL5("五日换手率"),
    VOL10("十日换手率"),
    VOL60("六十日换手率"),
    VOL120("120日换手率"),
    /**
     *
     */
    MA5("5日均线"),
    MA10("10日均线"),
    MA60("60日均线"),
    MA120("120日均线"),
    /**
     * 市销率（Price-to-Sales Ratio，P/S或PSR），也译作股价营收比、市值营收比，是股票的一个估值指标。
     * 市销率是以公司市值除以上一财年（或季度）的营利收入，或等价地，以公司股价除以每股营利收入。
     * 也是投资银行业务中一个重要的参照数据。
     * 费希尔认为超级强势股的市销率可能在0.75 以下，建议避免买入市销率大于1.5的股票。
     * 只能用于同行业对比，不同行业的市销率对比没有意义；
     */
    PS("市销率"),
    PSY("心理线指标"),
    /**
     *市现率是股票价格与每股现金流量的比率。
     * 市现率可用于评价股票的价格水平和风险水平。
     * 市现率越小，表明上市公司的每股现金增加额越多，经营压力越小。
     */
    PCF("市现率"),
    /**
     *
     */
    REC("分析师推荐评级"),
    /**
     *
     */
    DAREC("分析师推荐评级变化");

    public String chinese;

    AnalysisFactor(String chinese){
        this.chinese = chinese;
    }



    public static AnalysisFactor getAnalysisFactor(String chinese){

        for (AnalysisFactor factor : AnalysisFactor.values()){
            if(factor.chinese.equals(chinese)){
                return factor;
            }
        }
        return PE;

    }
}
