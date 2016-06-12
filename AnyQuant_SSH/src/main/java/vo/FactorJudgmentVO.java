package vo;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author Qiang
 * @date 6/5/16
 */
public class FactorJudgmentVO {
    /**
     *  每个时点因子在各个股票的值与各股票下期回报的相关系数
     */
    public List<Map.Entry<String, Double>> sortRankIC;
    /**
     * 根据因子选择的投资组合在样本期间的平均年化收益不年化平均标准差的比值
     * ，用于衡量该因子是否具有稳定的收益
     */
    public List<Map.Entry<String, Double>> sortRankIR;
    public List<Map.Entry<String, Double>> sortRankWinRate;
    /**
     * 根据因子选择的投资组合在样本期内的平均超额收益率Δr，用于衡量该因子是
     * 否具有可持续的收益
     */
    public List<Map.Entry<String, Double>> sortAvgProfit;

    public List<String> explanation;

//    public FactorJudgmentVO(Map<String, Double> rankIC, Map<String, Double> rankIR, Map<String, Double> rankTCheck, Map<String, Double> rankWinRate) {
//        this.rankIC = rankIC;
//        this.rankIR = rankIR;
//        this.rankTCheck = rankTCheck;
//        this.rankWinRate = rankWinRate;
//    }

    public FactorJudgmentVO() {

    }

    public void sort(){
        sortRankIC.sort( (c1 , c2) -> Double.compare(Math.abs(c1.getValue()) , Math.abs(c2.getValue()))  );
        sortRankIR.sort( (c1 , c2) -> Double.compare(Math.abs(c1.getValue()) , Math.abs(c2.getValue()))  );
        sortRankWinRate.sort( (c1 , c2) -> Double.compare(c1.getValue() , c2.getValue())  );
        sortAvgProfit.sort( (c1 , c2) -> Double.compare(c1.getValue() , c2.getValue())  );
    }


    public JSONObject getJSON(){
        JSONObject object = new JSONObject();
        object.put("sortRankIC" , new JSONObject());
        object.put("sortRankIR" , new JSONObject());
        object.put("sortRankWinRate" , new JSONObject());
        object.put("sortAvgProfit" , new JSONObject());
        object.put("explanation" , explanation);
        for (int i = 0; i < sortRankIC.size(); i++) {
            ((JSONObject)object.get("sortRankIC")).put(sortRankIC.get(i).getKey(), sortRankIC.get(i).getValue());
            ((JSONObject)object.get("sortRankIR")).put(sortRankIR.get(i).getKey(), sortRankIR.get(i).getValue());
            ((JSONObject)object.get("sortRankWinRate")).put(sortRankWinRate.get(i).getKey(), sortRankWinRate.get(i).getValue());
            ((JSONObject)object.get("sortAvgProfit")).put(sortAvgProfit.get(i).getKey(), sortAvgProfit.get(i).getValue());
        }
        return object;
    }







}
