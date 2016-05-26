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
        System.out.println(factor_vos.get(0).name);
        System.out.println(stockdataEntities.size() + " " + factor_vos.size());
        for (int i = 0; i <  len; i++) {
            stocks[i] = stockdataEntities.get(i).getClose();
            factors[i] = factor_vos.get(i).value;
            System.out.print(stockdataEntities.get(i).getDate().toString());
            System.out.print(factor_vos.get(i).date.DateToString());
            System.out.println("stock " + i + " " + stocks[i] +  " " + factors[i]);
        }
        double resulr = computeIC(stocks , factors);
        System.out.println("IC is" + resulr);
        return resulr;
    }


    private static double computeIC(double[] stocks,  double[] factors){
        double[] profits = computeStockProfit(stocks);

        for (int i = 0; i < profits.length; i++) {
            System.out.println(profits[i]);
        }

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



    private static double computeStockProfit(double start ,  double end){
        if(start == 0 || end == 0){
            return 0.1; //TODO 由于数据不全,出现为0错误暂时使用0.1代替
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


        return avg;
    }
}
