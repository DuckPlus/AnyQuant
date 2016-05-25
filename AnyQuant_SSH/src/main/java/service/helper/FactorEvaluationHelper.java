package service.helper;

import entity.FactorEntity;
import entity.StockdataEntity;
import org.jcp.xml.dsig.internal.MacOutputStream;
import util.enumration.AnalysisFactor;
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


        return new FactorEvaluationVO(analysis , mark , 2,  2);
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
        /**
         * the num of positive factor
         */
        int positiveFactor;
        /**
         * the sum of negative factor
         */
        int negativeFactor;

        FactorEvaluationVO(List<String> analysis, int mark , int positiveFactor , int negativeFactor) {
            this.analysis = analysis;
            this.mark = mark;
            this.positiveFactor = positiveFactor;
            this.negativeFactor = negativeFactor;
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





