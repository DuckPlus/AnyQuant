package vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Qiang
 * @date 6/5/16
 */
public class FactorJudgmentVO {

    public Map<String ,  Double> rankIC;
    public Map<String , Double> rankIR;
    public Map<String , Double> rankWinRate;
    public Map<String , Double> rankTCheck;
    public List<String> explaination;

    public FactorJudgmentVO(Map<String, Double> rankIC, Map<String, Double> rankIR, Map<String, Double> rankTCheck, Map<String, Double> rankWinRate) {
        this.rankIC = rankIC;
        this.rankIR = rankIR;
        this.rankTCheck = rankTCheck;
        this.rankWinRate = rankWinRate;
    }

    public FactorJudgmentVO() {
        rankIC = new HashMap<>();
        rankIR = new HashMap<>();
        rankTCheck = new HashMap<>();
        rankWinRate = new HashMap<>();
    }
}
