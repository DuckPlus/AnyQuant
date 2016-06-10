package service.helper;

import entity.BenchmarkdataEntity;
import entity.StockdataEntity;
import vo.Factor_VO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;

/**
 * @author Qiang
 * @date 16/5/19
 */
public class StockHelper {


    public static double computeIC(List<StockdataEntity> stockdataEntities , List<Factor_VO> factor_vos){
        int len = Math.min(stockdataEntities.size() , factor_vos.size());

        double[] stocks = new double[len];
        double[] factors = new  double[len];
//        System.out.println(factor_vos.get(0).name);
//        System.out.println(stockdataEntities.size() + " " + factor_vos.size());
        for (int i = 0; i <  len; i++) {
            stocks[i] = stockdataEntities.get(i).getClose();
            factors[i] = factor_vos.get(i).value;
//            System.out.print(stockdataEntities.get(i).getDate().toString());
//            System.out.print(factor_vos.get(i).date.DateToString());
//            System.out.println("stock " + i + " " + stocks[i] +  " " + factors[i]);
        }
        return computeIC(stocks , factors);
    }


    private static double computeIC(double[] stocks,  double[] factors){
        double[] profits = computeStockProfit(stocks);
        return AnalysisAlgorithm.computeRelated(factors , profits);
    }



    public static double computeIR(List<StockdataEntity> entities, List<Factor_VO> factor_vos){
        int len = Math.min(entities.size() , factor_vos.size());

        int divide = 6;
        List<List<StockdataEntity>> divideEntites = new ArrayList<>();
        List<List<Factor_VO>> divideVos = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            divideEntites.add(new ArrayList<>());
            divideVos.add(new ArrayList<>());
        }

        for (int i = 0; i < len; i++) {
            System.out.println(i + " " + len + " " + i%divide);
            divideEntites.get(i%divide).add(entities.get(i));
            divideVos.get(i%divide).add(factor_vos.get(i));
        }
//        for (int i = 0; i < len; i++) {
//            System.out.println(divideEntites.get(i).size());
//            System.out.println(divideVos.get(i).size());
//        }
//        System.out.println("***************************");
        double[] ICs = new double[divide];
        for (int i = 0; i < divide; i++) {
            ICs[i] = computeIC(divideEntites.get(i) , divideVos.get(i));
        }
        double mother;
        if( (mother = Math.sqrt(MathHelper.computeVar(ICs))) == 0){
            return 0;
        }else {
            return MathHelper.computeAverage(ICs)/mother;
        }


    }

    public static double computeTCheck(List<StockdataEntity> entities, List<Factor_VO> factor_vos, List<BenchmarkdataEntity> hushen300){
        int len = Math.min(entities.size() , Math.min(factor_vos.size() , hushen300.size()));

        double[] hushen300Profits = new double[len];
        double[] entitesProfit = new double[len];
        for (int i = 0; i < len; i++) {
            hushen300Profits[i] = hushen300.get(i).getClose();
            entitesProfit[i] = entities.get(i).getClose();
        }
        hushen300Profits = StockHelper.computeAccumulativeProfit(hushen300Profits);
        entitesProfit = StockHelper.computeAccumulativeProfit(entitesProfit);



        return 0;
    }




    private static double[] computeStockProfit(double[] datas){
        double[] results = new double[datas.length - 1];
        for (int i = 0; i < datas.length - 1; i++) {
            results[i] = computeStockProfit(datas[i] , datas[i+1]);
//            System.out.println("aaa"+datas[i] + datas[i + 1]);
        }
        return results;
    }


    public static double[] computeAccumulativeProfit(double[] datas){
        double[] results = new double[datas.length - 1];
        for (int i = 0; i < datas.length - 1; i++) {

            results[i] = computeStockProfit(datas[i] , datas[i+1]) + (i > 0 ? results[i - 1] : 0) ;
//            System.out.println("aaa"+datas[i] + datas[i + 1]);
        }
        return results;
    }



    public static double computeStockProfit(double start ,  double end){
        if(start == 0 || end == 0){
            return 0.01; //TODO 由于数据不全,出现为0错误暂时使用0.1代替
        }

        return (end-start)/start;
    }


    public static double computeAvgWithPower(double[] value , double[] power ) {
        if(value.length != power .length){
            return 0;
        }

        double avg = 0;
        for (int i = 0; i < value.length; i++) {
            avg+= value[i]*power[i];
        }
//        if(Double.isNaN(avg)){
//            System.out.println(Arrays.toString(value));
//            System.out.println(Arrays.toString(power));
//        }
//        System.out.println(avg);
        return avg;
    }
}
