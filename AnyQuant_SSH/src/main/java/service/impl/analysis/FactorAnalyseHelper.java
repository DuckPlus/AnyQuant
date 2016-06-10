package service.impl.analysis;

import DAO.BenchMarkDAO;
import DAO.FactorDAO;
import DAO.StockDataDAO;
import entity.BenchmarkdataEntity;
import entity.FactorEntity;
import entity.StockdataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.helper.AnalysisAlgorithm;
import service.helper.MathHelper;
import service.helper.StockHelper;
import util.DateCalculator;
import util.MyDate;
import util.ReflectHelper;
import vo.FactorJudgmentVO;

import java.util.*;

/**
 * 策略界面因子分析实现,主要过程为
 * //每个月初获得因子值
 * //根据因子值分为五档,超配高的、低配低的
 * //月底计算收益、超额收益
 * //12个月计算后获得 一组收益率、超额收益率、因子值(加权,根据比重)
 * //IC = 加权因子值  收益率  相关系数
 * //IR =  E(ic)/D^2(ic)
 * //win rate = num(超额收益率>0)/n
 * //T check : T 和 理论T 之间的差 概率值å
 * //排序 , 返回画表
 *
 * @author Qiang
 * @date 6/6/16
 */
@Service
public class FactorAnalyseHelper {

    private double[] distribution = {0.5, 0.3, 0.1, 0.07, 0.03};
    //    private int captial = 100000;



    private enum testFactor {PE(false), PB(false), PS(true), PCF(true) , VOL5(false) , VOL10(false);
        boolean upOrDown;
        testFactor(boolean upOrDown){
            this.upOrDown = upOrDown;
        }
    }

    @Autowired
    private FactorDAO factorDAO;
    @Autowired
    private StockDataDAO stockDataDAO;
    @Autowired
    private BenchMarkDAO benchMarkDAO;

    public FactorAnalyseHelper() {
        super();


    }


    public FactorJudgmentVO report(List<String> codes , MyDate startDate , MyDate endDate , String baseBench) {
        MyFactorJudgmentVO result = new MyFactorJudgmentVO();
        int month;
        if(startDate.getYear() == endDate.getYear()){
            month = endDate.getMonth() - startDate.getMonth();
        }else {
            month = 12 + endDate.getMonth() - startDate.getMonth();
        }


        //获得12个月的数据

        startDate.setDay(1);
        Map<String, List<FactorEntity>> factors = new HashMap<>(codes.size() * 2);
        Map<String, List<StockdataEntity>> dataEntities = new HashMap<>(codes.size() * 2);
        List<BenchmarkdataEntity> benchmarkdataEntities = benchMarkDAO.getBenchMarkByTime(baseBench, startDate, DateCalculator.getToDay());
        for (String code : codes) {
            List<FactorEntity> factorEntites = factorDAO.getFactorBetweenDate(code, startDate, DateCalculator.getToDay());
            if(factorEntites.isEmpty()){
//                codes.remove(code);
                continue;
            }
            factors.put(code, getUsefulData(factorEntites , month));
            dataEntities.put(code, stockDataDAO.getStockData(code, startDate, DateCalculator.getToDay()));
            System.out.println("*************get code " + code);
            System.out.println(factors.get(code).size() + " " + dataEntities.get(code).size());
            System.out.println(factors.get(code).get(0).getDate().toString() + " " + factors.get(code).get(factors.get(code).size() - 1).getDate().toString());
            System.out.println(dataEntities.get(code).get(0).getDate().toString() + " " + dataEntities.get(code).get(dataEntities.get(code).size() - 1).getDate().toString());

        }

        for (testFactor theTestFactor : testFactor.values()) {//遍历每一个要进行分析的因子
            MyDate tempStart = startDate.clone();
            double[] factorValue = new double[month];
            double[] profit = new double[month];
            double[] benchProfit = new double[month];
            double[] excessProfit = new double[month];
            for (int i = 0; i < month; i++) {
                CodeAndValue[][] codeDistribution = getDistributionByFactor(theTestFactor, factors, i);
                factorValue[i] = computeFactorWithPower(codeDistribution);
                profit[i] = getProfitAtMonthEnd(codeDistribution, dataEntities, tempStart);
                benchProfit[i] = computeBenchProfit(benchmarkdataEntities, tempStart);
                excessProfit[i] = profit[i] - benchProfit[i];



                DateCalculator.getNextMonth(tempStart);
            }

            System.out.println("*********************Test for factor " + theTestFactor.name() + "*************************");


            System.out.println("factorValue" +Arrays.toString(factorValue));
            System.out.println("profit" +Arrays.toString(profit));
            System.out.println("benchProfit" +Arrays.toString(benchProfit));
            System.out.println("excessProfit" +Arrays.toString(excessProfit));
            System.out.println();

            result.rankIC.put(theTestFactor.name(), AnalysisAlgorithm.computeRelated(factorValue, excessProfit));
            result.rankIR.put(theTestFactor.name(), MathHelper.computeAverage(profit) / Math.sqrt(MathHelper.computeVar(profit)));
            result.rankWinRate.put(theTestFactor.name(), MathHelper.getRank(0, excessProfit, false).getX() / month);
//            result.rankTCheck.put(theTestFactor.name() , )
            result.rankAvgProfit.put(theTestFactor.name() , MathHelper.computeAverage(excessProfit));
        }

        makeExplanation(result);
        return sortToUI(result);
    }

    private static FactorJudgmentVO sortToUI(MyFactorJudgmentVO result) {
        FactorJudgmentVO vo = new FactorJudgmentVO();
        vo.sortRankIC = new ArrayList<>(result.rankIC.entrySet());
        vo.sortRankWinRate = new ArrayList<>(result.rankWinRate.entrySet());
        vo.sortRankIR = new ArrayList<>(result.rankIR.entrySet());
        vo.sortAvgProfit = new ArrayList<>(result.rankAvgProfit.entrySet());
        vo.sort();
        vo.explanation = result.explanation;


        return vo;
    }

    /**
     * 根据因子排位情况做出简单的分析
     * @param result
     */
    private void makeExplanation(MyFactorJudgmentVO result) {





    }

//    private void initlizeResult(testFactor theTestFactor, FactorJudgmentVO result) {
//        result.rankIC.put(theTestFactor.name() )
//    }


    /**
     * 计算根据因子分布计算出来的股票的收益率
     *
     * @param codeDistribution
     * @param dataEntities
     * @param start
     * @return
     */
    private double getProfitAtMonthEnd(CodeAndValue[][] codeDistribution, Map<String, List<StockdataEntity>> dataEntities, MyDate start) {
        double profit = 0;
        for (int i = 0; i < codeDistribution.length; i++) {
            double power = distribution[i];
            double[] profits = new double[codeDistribution[i].length];
            for (int j = 0; j < codeDistribution[i].length; j++) {
                profits[j] = computeStockProfit(dataEntities.get(codeDistribution[i][j].code), start);
            }
            profit += power * MathHelper.computeAverage(profits);
        }
        return profit;

    }

    /**
     * 计算一只股票某个月的
     *
     * @param stockdataEntities
     * @param start
     * @return
     */
    private double computeStockProfit(List<StockdataEntity> stockdataEntities, MyDate start) {
        double startVal = 0;
        double endVal = 0;

        boolean hasFind = false;
        for (StockdataEntity entity : stockdataEntities) {
//            System.out.println(entity.getDate().getYear());
//            System.out.println("date is " + entity.getDate().getMonth() + " " + start.getMonth() + (entity.getDate().getMonth() == start.getMonth()));
            if (hasFind) {
                endVal = entity.getClose();
                if (entity.getDate().getMonth() != start.getMonth()) {
                    break;
                }
            } else {
                if (((entity.getDate().getMonth() + 1) == start.getMonth()) ) {
//                    System.out.println("has find and the date is " + entity.getDate().getMonth());
                    hasFind = true;
                    startVal = entity.getClose();
                }
            }


        }
        return StockHelper.computeStockProfit(startVal, endVal);
    }

    private double computeBenchProfit(List<BenchmarkdataEntity> benchmarkdataEntities, MyDate start) {
        double startVal = 0;
        double endVal = 0;

        boolean hasFind = false;
        for (BenchmarkdataEntity entity : benchmarkdataEntities) {
            if (hasFind) {
                endVal = entity.getClose();
                if (entity.getDate().getMonth() != start.getMonth()) {
                    break;
                }
            } else {
                if (entity.getDate().getMonth() == start.getMonth() ) {
                    hasFind = true;
                    startVal = entity.getClose();
                }
            }


        }
        return StockHelper.computeStockProfit(startVal, endVal);
    }

    /**
     * 根据分布
     * 获得加权的因子值
     *
     * @param codeDistribution
     * @return
     */
    private double computeFactorWithPower(CodeAndValue[][] codeDistribution) {
        double sum = 0;
        for (int i = 0; i < codeDistribution.length; i++) {
            double power = distribution[i];
//            System.out.println("now the sum is " + sum);
            sum += MathHelper.computeAverage(Arrays.stream(codeDistribution[i]).mapToDouble(code -> code.value).toArray()) * power;
        }
        return sum;
    }

    /**
     * 根据因子值分档(降序排列),返回每档的股票的代号
     *
     * @param theTestFactor 分档的股票
     * @param factors       股票因子值
     * @param month
     * @return
     */
    private CodeAndValue[][] getDistributionByFactor(testFactor theTestFactor, Map<String, List<FactorEntity>> factors, int month) {
        List<CodeAndValue> codes = new ArrayList<>(factors.keySet().size());

        for (String key : factors.keySet()) {
            codes.add(new CodeAndValue(key, (double) ReflectHelper.getValueByAttrName(factors.get(key).get(month), theTestFactor.name())));
        }

        if(theTestFactor.upOrDown){
            codes.sort( (c1 , c2) ->  Double.compare(c1.value , c2.value)   );
        }else {
            codes.sort((c1, c2) -> c1.value > c2.value ? -1 : 1);
        }


        int onePart = codes.size() / distribution.length;
        CodeAndValue[][] result = new CodeAndValue[distribution.length][];
        for (int i = 0; i < distribution.length; i++) {
            Object[] temp;
            if (i == distribution.length - 1) {
                temp = codes.subList(i * onePart, codes.size() - 1).toArray();
            } else {
                temp = codes.subList(i * onePart, i * onePart + onePart).toArray();
            }

            result[i] = Arrays.copyOf(temp, temp.length, CodeAndValue[].class);

        }
        return result;
    }


    private List<FactorEntity> getUsefulData(List<FactorEntity> factors, int months) {
//        System.out.println("size = ="+factors.get(0).getDate().toString() + "  " + factors.get(factors.size() - 1).getDate().toString());
        List<FactorEntity> result = new ArrayList<>(months);
        for (FactorEntity factor : factors) {
            if (result.size() > 0) {
                if (result.get(result.size() - 1).getDate().getMonth() != factor.getDate().getMonth()) {
                    result.add(factor);
                }
            } else {
                result.add(factor);
            }


        }
//        System.out.println("size"+result.size() + " month" + months);
//        for (int i = 0; i < result.size(); i++) {
//            System.out.println("size = ="+result.get(i).getDate().toString());
//        }
//        System.out.println("size"+result.size() + " month" + months);
        return result.subList(0, months);


    }

    private class CodeAndValue {
        String code;
        double value;

        CodeAndValue(String code, double value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return code + " " + value;
        }
    }


    private class MyFactorJudgmentVO {
        /**
         *  每个时点因子在各个股票的值与各股票下期回报的相关系数
         */
        Map<String, Double> rankIC;
        /**
         * 根据因子选择的投资组合在样本期间的平均年化收益不年化平均标准差的比值
         ，用于衡量该因子是否具有稳定的收益
         */
        Map<String, Double> rankIR;
        Map<String, Double> rankWinRate;
        /**
         * 根据因子选择的投资组合在样本期内的平均超额收益率Δr，用于衡量该因子是
         * 否具有可持续的收益
         */
        Map<String, Double> rankAvgProfit;
        List<String> explanation;

//    public FactorJudgmentVO(Map<String, Double> rankIC, Map<String, Double> rankIR, Map<String, Double> rankTCheck, Map<String, Double> rankWinRate) {
//        this.rankIC = rankIC;
//        this.rankIR = rankIR;
//        this.rankTCheck = rankTCheck;
//        this.rankWinRate = rankWinRate;
//    }

        MyFactorJudgmentVO() {
            rankIC = new HashMap<>();
            rankIR = new HashMap<>();
            rankWinRate = new HashMap<>();
            rankAvgProfit = new HashMap<>();
            explanation = new ArrayList<>();
        }



    }
}
