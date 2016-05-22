package vo;

import util.MyDate;
import util.enumration.StrategyAnalysisAlgorithm;

import java.util.List;
import java.util.Map;

/**
 * 策略参数
 * @author Qiang
 * @date 16/5/22
 */
public class StrategyArgumentVO {

    public MyDate start;
    public MyDate end;
    /**
     * 带权重的因子
     */
    public Map<String , Double> factorsWithWeight;
    /**
     * 调仓频率
     */
    public int changeRate;
    /**
     * 股票池
     */
    public List<String> stocks;
    /**
     * 交易费率
     */
    public double taxRate;
    /**
     * 交易算法
     */
    public StrategyAnalysisAlgorithm algorithm;
    /**
     * 基准大盘
     */
    public String baseBenchMark;

}
