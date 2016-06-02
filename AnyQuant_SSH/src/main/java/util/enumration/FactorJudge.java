package util.enumration;

/**
 * 因子评价指标
 * @author Qiang
 * @date 16/5/20
 */
public enum FactorJudge {
    /**
     * 因子 IC(信息系数):即每个时点因子在各个股票的暴露值与各股票下个期回报的 相关系数
     * 我们认为如果一个因子的 IC 值高于 2%(或低于-2%),则认为该因子在个股 alpha 收益上有较好的效果
     * IC 为正表示该因子与股票的未来收益有正相关关系, 应该超配因子暴露值高的股票
     * 反之若 IC 为负则超配因子暴露值低的股票。
     */
    IC("信息系数"),
    /**
     * 因子 IR(信息比):即因子在样本期间的平均年化收益与年化平均标准差的比值
     * IR 的绝对值越高,表面该因子在个股 alpha 收益上效果越好
     * 另外,经统计发现, IR 的绝对值高于 0.7 时,Alpha 因子的选股效果通常比较明显
     * 另外,若 IR 为正,则代 表应该超配因子暴露值高的股票,反之若 IR 为负,则应该超配因子暴露值低的股票。
     */
    IR("信息比"),
    /**
     * 用于衡量 Alpha 因子是否具有显著的因子回报,因子的 t 检验概率 越小
     * 说明该因子的选股效果越好,经统计发现,t检验的概率小于0.2时,
     * 相应的Alpha 因子具有较好的选股效果!
     *
     * t 检验组合超额收益率与沪深 300 收益率的显著程度
     */
    T_CHECK("T检验");
    public String chinese;

    FactorJudge(String chinese){
        this.chinese = chinese;
    }

    public static FactorJudge getFactorJudgeFactors(String chinese){

        for (FactorJudge factor : FactorJudge.values()){
            if(factor.chinese.equals(chinese)){
                return factor;
            }
        }
        return IC;

    }

}
