package service.helper;

import entity.StockdataEntity;

import java.util.List;

/**
 * @author Qiang
 * @date 16/5/19
 */
public class StockHelper {





    public static double computeIC(List<StockdataEntity> datas , double[] factors , int leap){
        int len = factors.length;

        double[] closes = new double[datas.size()];
        for (int i = 0; i < datas.size(); i++) {
            closes[i] = datas.get(i).getClose();
        }

        double[] profits = computeStockProfit(closes);

        return AnalysisAlgorithm.OLS(factors , profits);
    }



    public static double computeIR(){

        return 0;
    }

    public static double computeWIN_RATE(){
        return 0;
    }




    public static double[] computeStockProfit(double[] datas){
        if(datas.length < 2){
            return null;
        }
        double[] results = new double[datas.length - 1];
        for (int i = 0; i < results.length - 1; i++) {
            results[i] = computeStockProfit(datas[i] , datas[i+1]);
        }
        return results;
    }


    private static double computeStockProfit(double start ,  double end){
        return (end-start)/start;
    }
}
