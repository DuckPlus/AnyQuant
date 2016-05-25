package service.helper;

import entity.StockdataEntity;
import vo.Factor_VO;

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

        for (int i = 0; i <  len; i++) {
            stocks[i] = stockdataEntities.get(i).getClose();
            factors[i] = factor_vos.get(i).value;
        }
        return computeIC(stocks , factors);
    }


    private static double computeIC(double[] stocks,  double[] factors){
        double[] profits = computeStockProfit(stocks);

        return AnalysisAlgorithm.computeRelated(factors , profits);
    }



    public static double computeIR(){

        return 0;
    }

    public static double computeWIN_RATE(){
        return 0;
    }




    public static double[] computeStockProfit(double[] datas){
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
