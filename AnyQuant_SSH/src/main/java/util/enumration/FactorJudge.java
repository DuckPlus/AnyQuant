package util.enumration;

/**
 * 因子评价指标
 * @author Qiang
 * @date 16/5/20
 */
public enum FactorJudge {

    IC("信息系数"),IR("信息比"),WIN_RATE("胜率");
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
