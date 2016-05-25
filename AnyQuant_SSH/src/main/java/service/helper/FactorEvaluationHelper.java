package service.helper;

import entity.FactorEntity;
import entity.StockdataEntity;
import util.enumration.AnalysisFactor;
import util.enumration.Suggestion;
import vo.EvaluationVO;

import java.lang.reflect.InvocationTargetException;
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
            positiveMark = positiveMark + 2;
            analysis.add("连续"+ len + "日股价高于五日均线,股票表现强劲");
        }
        if (allLarge) {
            negativeMark = negativeMark + 2;
            analysis.add("连续"+ len + "日股价低于五日均线,股票表现疲软");
        }
        if(smallToLarge){
            positiveMark++;
            analysis.add("股价上穿五日均线,行情看好");
        }
        if(largeToSmall){
            negativeMark++;
            analysis.add("股价下穿五日均线,行情看空");
        }






        return new FactorEvaluationVO(analysis , computeMark(positiveMark , negativeMark , 3 , 3) );
    }

    private static int computeMark(int positiveMark, int negativeMark, int positiveNum, int negativeNum) {
        int base = 50;

        return (int) (base + positiveMark*(50.0/positiveNum) - negativeMark*(50.0/negativeNum));



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


    private static Map<AnalysisFactor , double[]> getFactorArray(List<FactorEntity> entities){
        Map<AnalysisFactor , double[]> factorArrays = new HashMap<>(20);




        for (AnalysisFactor factor : AnalysisFactor.values()){
            factorArrays.put(factor , new double[entities.size()]);
        }
        try {
            Class<?> factorClass =  Class.forName("entity.FactorEntity");

            for (int i = 0; i < entities.size(); i++) {
                FactorEntity entity = entities.get(i);
                for (AnalysisFactor factor : AnalysisFactor.values()){
                    factorArrays.get(factor)[i] = (double) factorClass.getDeclaredMethod("get" + factor.name().substring(0 ,1) + factor.name().substring(1).toLowerCase()).invoke(entity);
                }


            }

        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }


        return factorArrays;
    }


}





