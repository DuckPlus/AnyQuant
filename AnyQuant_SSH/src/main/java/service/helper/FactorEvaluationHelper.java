package service.helper;

import entity.FactorEntity;
import entity.StockdataEntity;
import util.ReflectHelper;
import util.enumration.AnalysisFactor;
import util.enumration.Suggestion;
import vo.EvaluationVO;

import java.util.*;

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
        int len = Math.min(entities.size() , factors.size());
        Map<AnalysisFactor , double[]> factorArrays = getFactorArray(factors);
        System.out.println(entities.size() + " " + factors.size());
        for (Map.Entry<AnalysisFactor , double[]> entry : factorArrays.entrySet()){
            System.out.println(entry.getKey().name() + " " + Arrays.toString(entry.getValue()));
        }



        vos.add(analyseMA5(entities , factorArrays.get(AnalysisFactor.MA5)));

        vos.add(analyseMA10(entities , factorArrays.get(AnalysisFactor.MA10)));





        return makeEvaluation(vos);
    }

    private static EvaluationVO makeEvaluation(List<FactorEvaluationVO> vos) {

        List<String> analysis = new ArrayList<>();
        int sum = 0;
        for (FactorEvaluationVO vo : vos){
            analysis.addAll(vo.analysis);
            sum += vo.mark;
        }
        System.out.println(sum + " " + vos.size());
       int mark = (int) (sum/(double)vos.size());


        if(analysis.size() == 0){
            analysis.add("股票因子表现一般,建议观望");
        }

        return new EvaluationVO( analysis ,"" , mark, Suggestion.getSuggestion(mark));
    }



    /**
     * 分析五日均线走势,给出相应结论和分值
     * @param lastFiveDay
     * @param ma5
     * @return
     */
    private static FactorEvaluationVO analyseMA5(List<StockdataEntity> lastFiveDay , double[] ma5){
        int positiveMark = 0;
        int negativeMark = 0;
        int len = Math.min(ma5.length , lastFiveDay.size());
        List<String> analysis = new ArrayList<>();

        double[] closes = new double[len];
        for (int i = 0; i < len; i++) {
            closes[i] = lastFiveDay.get(i).getClose();
        }

        closes = MathHelper.getClipFromArray(closes , 5);
        ma5 = MathHelper.getClipFromArray(ma5 , 5);
        len = Math.min( 5, len);

        if(MathHelper.checkIfAllSmallOrLarge(closes , ma5 , false)){
            positiveMark = positiveMark + 2;
            analysis.add("连续"+ len + "日股价高于五日均线,股票表现强劲");
        } else if (MathHelper.checkIfAllSmallOrLarge(closes , ma5 , true)) {
            negativeMark = negativeMark + 2;
            analysis.add("连续"+ len + "日股价低于五日均线,股票表现疲软");
        } else if(MathHelper.passThrough(closes , ma5 , true)){
            positiveMark++;
            analysis.add("股价上穿五日均线,行情看好");
        }else if(MathHelper.passThrough(closes , ma5 , false)){
            negativeMark++;
            analysis.add("股价下穿五日均线,行情看空");
        }

        return new FactorEvaluationVO(analysis , computeMark(positiveMark , negativeMark , 3 , 3) );
    }

    private static FactorEvaluationVO analyseMA10(List<StockdataEntity> data, double[] ma10){

        int positiveMark = 0;
        int negativeMark = 0;
        int len = Math.min(ma10.length , data.size());
        List<String> analysis = new ArrayList<>();

        double[] closes = new double[len];
        for (int i = 0; i < len; i++) {
            closes[i] = data.get(i).getClose();
        }
        len = Math.min( 10, len);
        closes = MathHelper.getClipFromArray(closes , len);
        ma10 = MathHelper.getClipFromArray(ma10, len);


        if(MathHelper.checkIfAllSmallOrLarge(closes , ma10, false)){
            positiveMark = positiveMark + 2;
            analysis.add("连续"+ len + "日股价高于十日均线,股票表现强劲");
        } else if (MathHelper.checkIfAllSmallOrLarge(closes , ma10, true)) {
            negativeMark = negativeMark + 2;
            analysis.add("连续"+ len + "日股价低于十日均线,股票表现疲软");
        } else if(MathHelper.passThrough(closes , ma10, true)){
            positiveMark++;
            analysis.add("股价上穿十日均线,行情看好");
        }else if(MathHelper.passThrough(closes , ma10, false)){
            negativeMark++;
            analysis.add("股价下穿十日均线,行情看空");
        }

        return new FactorEvaluationVO(analysis , computeMark(positiveMark , negativeMark , 3 , 3) );
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


    private static Map<AnalysisFactor , double[]> getFactorArray(List<FactorEntity> entities){
        Map<AnalysisFactor , double[]> factorArrays = new HashMap<>(20);


        for (AnalysisFactor factor : AnalysisFactor.values()){
            factorArrays.put(factor , new double[entities.size()]);
        }

            for (int i = 0; i < entities.size(); i++) {
                FactorEntity entity = entities.get(i);
                for (AnalysisFactor factor : AnalysisFactor.values()){
                    factorArrays.get(factor)[i] = (double) ReflectHelper.getValueByAttrName(entity , factor.name().toLowerCase());
                }


            }

        return factorArrays;
    }

    private static int computeMark(int positiveMark, int negativeMark, int positiveNum, int negativeNum) {
        int base = 50;

        return (int) (base + positiveMark*(50.0/positiveNum) - negativeMark*(50.0/negativeNum));



    }
}





