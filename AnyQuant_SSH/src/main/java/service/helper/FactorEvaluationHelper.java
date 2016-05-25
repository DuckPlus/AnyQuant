package service.helper;

import entity.FactorEntity;
import entity.StockdataEntity;
import vo.EvaluationVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 分析股票因子
 * @author Qiang
 * @date 16/5/25
 */
public class FactorEvaluationHelper {

    /**
     * 分析股票因子,返回对其的评分
     * @param entities
     * @param factors
     * @return
     */
    public static EvaluationVO evaluateStockByFactor(List<StockdataEntity> entities , List<FactorEntity> factors){
        List<FactorEvaluationVO> vos = new ArrayList<>();
//        vos.add(analyseMA5())




        return null;
    }






    /**
     * 分析五日均线走势,给出相应结论和分值
     * @param lastFiveDay
     * @param ma5
     * @return
     */
    private static FactorEvaluationVO analyseMA5(List<StockdataEntity> lastFiveDay , double[] ma5){
        int mark = 0;
        int len = ma5.length;
        List<String> analysis = new ArrayList<>();

        double[] closes = new double[len];


        boolean allSmall = true;
        boolean allLarge = true;
        boolean smallToLarge = ma5[0] > closes[0] && ma5[len - 1 ] < closes[len-1];//从低于五日均线到高于
        boolean largeToSmall = ma5[0] < closes[0] && ma5[len - 1 ] > closes[len-1];
        for (int i = 0; i < len; i++) {

            if(ma5[i] > closes[i]){
                allSmall = false;
            }else {
                allLarge = false;
            }
        }
        if(allSmall){
            mark++;
            analysis.add("连续"+ len + "日股价高于五日均线,股票表现强劲");
        }
        if (allLarge) {
            mark--;
            analysis.add("连续"+ len + "日股价低于五日均线,股票表现疲软");
        }
        if(smallToLarge){
            mark++;
            analysis.add("股价上穿五日均线,行情看好");
        }
        if(largeToSmall){
            mark--;
            analysis.add("股价下穿五日均线,行情看空");
        }


        return new FactorEvaluationVO(analysis , mark);
    }

    /**
     *
     * @param lastTenDay
     * @param ma10
     * @return
     */
    private static FactorEvaluationVO analyseMA10(List<StockdataEntity> lastTenDay , double[] ma10){
        return null;//TODO
    }

    /**
     * 分析均线间穿来穿去的关系
     * @param factors
     * @return
     */
    private static FactorEvaluationVO analyseMAs(List<FactorEntity> factors){
        return null;//TODO
    }

    private static FactorEvaluationVO analyseVol5(List<StockdataEntity> lastFiveDay , double[] vol5){
        return null;//TODO
    }


    private static class FactorEvaluationVO {

        int mark;

        List<String> analysis;

        FactorEvaluationVO(List<String> analysis, int mark) {
            this.analysis = analysis;
            this.mark = mark;
        }
    }

}





