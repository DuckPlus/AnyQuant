package service.impl.analysis;

import entity.FactorEntity;
import entity.StockdataEntity;
import service.helper.MathHelper;
import util.ReflectHelper;
import util.enumration.AnalysisFactor;
import util.enumration.Suggestion;
import vo.EvaluationVO;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * 分析股票因子
 *
 * @author Qiang
 * @date 16/5/25
 */
public class FactorEvaluationHelper {

    /**
     * 分析股票因子,返回对其的评分
     *
     * @param boardsFactor 同行业的其他股票最近一个交易日的factor
     */
    public static EvaluationVO evaluateStockByFactor(List<StockdataEntity> entities, List<FactorEntity> factors, List<FactorEntity> boardsFactor) {
        List<FactorEvaluationVO> vos = new ArrayList<>();
        Map<AnalysisFactor, double[]> factorArrays = getFactorArray(factors);

        System.out.println(entities.size() + " " + factors.size());
        for (Map.Entry<AnalysisFactor, double[]> entry : factorArrays.entrySet()) {
            System.out.println(entry.getKey().name() + " " + Arrays.toString(entry.getValue()));
        }


        vos.add(analyseMA5(entities, factorArrays.get(AnalysisFactor.MA5)));
        vos.add(analyseMA10(entities, factorArrays.get(AnalysisFactor.MA10)));
        vos.add(analyseVol5(entities, factorArrays.get(AnalysisFactor.VOL5)));
        vos.add(analysePS(factorArrays.get(AnalysisFactor.PS), boardsFactor));
        vos.add(analysePE(factorArrays.get(AnalysisFactor.PE)));
        vos.add(analysePCF(factorArrays.get(AnalysisFactor.PCF ), boardsFactor));
        return makeEvaluation(vos);
    }

    /**
     * 根据各个子因子的分析给出总的分析情况
     *
     * @param vos
     * @return
     */
    private static EvaluationVO makeEvaluation(List<FactorEvaluationVO> vos) {

        List<String> analysis = new ArrayList<>();
        int sum = 0;
        int useFulSize = vos.size();
        for (FactorEvaluationVO vo : vos) {
            analysis.addAll(vo.analysis);
            if (vo.mark == 50) {
                useFulSize--;
            } else {
                sum += vo.mark;
            }


        }
        int mark = (int) (sum / (double) useFulSize) + 5;


        if (analysis.size() == 0) {
            analysis.add("股票因子表现一般,建议观望");
        }

        return new EvaluationVO(analysis, "", mark, Suggestion.getSuggestion(mark));
    }


    /**
     * 分析五日均线走势,给出相应结论和分值
     */
    private static FactorEvaluationVO analyseMA5(List<StockdataEntity> lastFiveDay, double[] ma5) {
        int positiveMark = 0;
        int negativeMark = 0;
        int len = Math.min(ma5.length, lastFiveDay.size());
        List<String> analysis = new ArrayList<>();

        double[] closes = new double[len];
        for (int i = 0; i < len; i++) {
            closes[i] = lastFiveDay.get(i).getClose();
        }

        closes = MathHelper.getClipFromArray(closes, 5);
        ma5 = MathHelper.getClipFromArray(ma5, 5);
        len = Math.min(5, len);

        if (MathHelper.checkIfAllSmallOrLarge(closes, ma5, false)) {
            positiveMark = positiveMark + 2;
            analysis.add("连续" + len + "日股价高于五日均线,股票表现强劲");
        } else if (MathHelper.checkIfAllSmallOrLarge(closes, ma5, true)) {
            negativeMark = negativeMark + 2;
            analysis.add("连续" + len + "日股价低于五日均线,股票表现疲软");
        } else if (MathHelper.passThrough(closes, ma5, true)) {
            positiveMark++;
            analysis.add("股价上穿五日均线,行情看好");
        } else if (MathHelper.passThrough(closes, ma5, false)) {
            negativeMark++;
            analysis.add("股价下穿五日均线,行情看空");
        }

        return new FactorEvaluationVO(analysis, computeMark(positiveMark, negativeMark, 3, 3));
    }

    private static FactorEvaluationVO analysePE(double[] pe) {
        List<String> analysis = new ArrayList<>();
        int positiveMark = 0;
        int negativeMark = 0;
        double[] levels = {13, 18, 29, 40};
        double avgPE = MathHelper.computeAverage(pe);

        if (avgPE < levels[0]) {
            positiveMark += 2;
            analysis.add("该股票价值被低估,建议关注");
        } else if (avgPE < levels[1]) {
            positiveMark++;
            analysis.add("该股票价值被低估,建议关注");
        } else if (avgPE < levels[2]) {

        } else if (avgPE < levels[3]) {
            negativeMark++;
            analysis.add("该股票市盈率较高,谨慎投资");
        } else if (avgPE > levels[3]) {
            negativeMark += 2;
            analysis.add("该股票市盈率偏高,可能存在投机性泡沫");
        }


        return new FactorEvaluationVO(analysis, computeMark(positiveMark, negativeMark, 2, 2));
    }


    private static FactorEvaluationVO analyseMA10(List<StockdataEntity> data, double[] ma10) {

        int positiveMark = 0;
        int negativeMark = 0;
        int len = Math.min(ma10.length, data.size());
        List<String> analysis = new ArrayList<>();

        double[] closes = new double[len];
        for (int i = 0; i < len; i++) {
            closes[i] = data.get(i).getClose();
        }
        len = Math.min(10, len);
        closes = MathHelper.getClipFromArray(closes, len);
        ma10 = MathHelper.getClipFromArray(ma10, len);


        if (MathHelper.checkIfAllSmallOrLarge(closes, ma10, false)) {
            positiveMark = positiveMark + 2;
            analysis.add("连续" + len + "日股价高于十日均线,股票表现强劲");
        } else if (MathHelper.checkIfAllSmallOrLarge(closes, ma10, true)) {
            negativeMark = negativeMark + 2;
            analysis.add("连续" + len + "日股价低于十日均线,股票表现疲软");
        } else if (MathHelper.passThrough(closes, ma10, true)) {
            positiveMark++;
            analysis.add("股价上穿十日均线,行情看好");
        } else if (MathHelper.passThrough(closes, ma10, false)) {
            negativeMark++;
            analysis.add("股价下穿十日均线,行情看空");
        }

        return new FactorEvaluationVO(analysis, computeMark(positiveMark, negativeMark, 3, 3));
    }

    /**分析市销率*/
    private static FactorEvaluationVO analysePS(double[] ps, List<FactorEntity> boardsFactor) {
        List<String> analysis = new ArrayList<>();
        int positiveMark = 0;
        int negativeMark = 0;
//        System.out.println("size = =" +boardsFactor.size());
//        for (FactorEntity entity : boardsFactor){
//            System.out.println(entity.getPs());
//        }

        double avg = MathHelper.computeAverage(ps);

        double[] boardFactor = boardsFactor.stream().mapToDouble(FactorEntity::getPs).toArray();

        Point rank = MathHelper.getRank(ps[ps.length - 1], boardFactor, true);


//        if(avg < 0.85){
//            positiveMark+=2;
//            analysis.add("该股票市销率较低,投资价值越高");
//        } else if (avg > 1.75){
//            negativeMark+=2;
//            analysis.add("该股票市销率较高,投资价值较低");
//        }
//        System.out.print("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
//        System.out.println(rank.getX() + " " + rank.getY());
        double rankRate = rank.getX() / rank.getY();
        if (rankRate < 0.1) {
            positiveMark += 2;
            analysis.add("该股票市销率在同行业中居于极低的排名,投资价值较高");
        } else if (rankRate < 0.3) {
            positiveMark += 1;
            analysis.add("该股票市销率在同行业中居于比较低的排名,投资价值较高");
        } else if (rankRate < 0.7) {

        } else if (rankRate < 0.9) {
            negativeMark++;
            analysis.add("该股票市销率在同行业中居于较高的位置,投资价值较低");
        } else {
            negativeMark += 2;
            analysis.add("该股票市销率在同行业中居于较高的排名,投资价值较低,买入需谨慎");
        }


        return new FactorEvaluationVO(analysis, computeMark(positiveMark, negativeMark, 2, 2));
    }

    private static FactorEvaluationVO analysePCF(double[] pcf , List<FactorEntity> boardsFactor) {
        List<String> analysis = new ArrayList<>();
        int positiveMark = 0;
        int negativeMark = 0;
        double avg = MathHelper.computeAverage(pcf);

        double[] boardFactor = boardsFactor.stream().mapToDouble(FactorEntity::getPs).toArray();

        Point rank = MathHelper.getRank(pcf[pcf.length - 1], boardFactor, true);
        double rankRate = rank.getX() / rank.getY();
        if (rankRate < 0.1) {
            positiveMark += 2;
            analysis.add("该股票市现率较低,表明上市公司的每股现金增加额较多，经营压力较小,投资价值较高");
        } else if (rankRate < 0.3) {
            positiveMark += 1;
            analysis.add("该股票市现率较低,投资价值较高");
        } else if (rankRate < 0.7) {

        } else if (rankRate < 0.9) {
            negativeMark++;
            analysis.add("该股票市现率较高,投资价值较低");
        } else {
            negativeMark += 2;
            analysis.add("该股票市现率较高,投资价值较低");

        }
        return new FactorEvaluationVO(analysis, computeMark(positiveMark, negativeMark, 2, 2));
    }
    /**
     * 分析均线间穿来穿去的关系
     */
    private static FactorEvaluationVO analyseMAs(List<FactorEntity> factors) {


        return null;//TODO
    }

    private static FactorEvaluationVO analyseVol5(List<StockdataEntity> data, double[] vol5) {
        List<String> analysis = new ArrayList<>();
        int positiveMark = 0;
        int negativeMark = 0;

        double avg = MathHelper.computeAverage(vol5);
        if (avg < 0.005) {
            negativeMark += 2;
            analysis.add("近期换手率极低,股票交易不活跃");

        } else if (avg < 0.01) {
            negativeMark++;
            analysis.add("近期股票换手率较低,交易较为温和");
        } else if (avg < 0.02) {
            //Nothing
        } else if (avg < 0.025) {
            positiveMark++;
            analysis.add("该股票市场交易活跃");

        } else if (avg >= 0.035) {
            positiveMark += 2;
            analysis.add("该股票近期交易十分活跃,建议密切关注");
        }


        return new FactorEvaluationVO(analysis, computeMark(positiveMark, negativeMark, 3, 3));
    }


    private static class FactorEvaluationVO {

        int mark;

        List<String> analysis;


        FactorEvaluationVO(List<String> analysis, int mark) {
            this.analysis = analysis;
            this.mark = mark;
        }
    }


    private static Map<AnalysisFactor, double[]> getFactorArray(List<FactorEntity> entities) {
        Map<AnalysisFactor, double[]> factorArrays = new HashMap<>(20);


        for (AnalysisFactor factor : AnalysisFactor.values()) {
            factorArrays.put(factor, new double[entities.size()]);
        }

        for (int i = 0; i < entities.size(); i++) {
            FactorEntity entity = entities.get(i);
            for (AnalysisFactor factor : AnalysisFactor.values()) {
                factorArrays.get(factor)[i] = (double) ReflectHelper.getValueByAttrName(entity, factor.name().toLowerCase());
            }


        }

        return factorArrays;
    }

    private static int computeMark(int positiveMark, int negativeMark, int positiveNum, int negativeNum) {
        int base = 50;

        return (int) (base + positiveMark * (50.0 / positiveNum) - negativeMark * (50.0 / negativeNum));


    }
}





