package service.impl.strategy;

import entity.FactorEntity;
import service.helper.MathHelper;
import util.MyDate;
import util.enumration.AnalysisFactor;
import vo.ReportVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 因子选股策略
 * 根据因子评价策略选出较为有效的因子,之后根据这个因子以及用户给定的其他一些参数:调仓频率,交易费率等等,
 * 提供给用户回测的数据,包括各个因子的情况
 * @author Qiang
 * @date 6/5/16
 */
public class FactorStrategy extends MultiStockStrategy {
    /**
     * 用户选择的因子和比重
     */
    Map  <AnalysisFactor,Double>  weightedFactors;

    /**
     * 每次买入前计算各个股票的综合因子
     */
    Map<String,Double> finalFactors;

    public void setPara_Factor(double capital, double taxRate,
                              String baseCode , MyDate start , MyDate end,
                               List<String> stocks,  Map  <AnalysisFactor,Double>  weightedFactors,
                               int interval){
        super.setPara(capital,taxRate,baseCode,start,end);

        this.stocks = stocks;
        this.weightedFactors=weightedFactors;
        this.interval=interval;

        this.vol=stocks.size();
        this.lots=new int [vol];
        this.buy_Prices=new double [vol];
        this.sell_Prices=new double [vol];
    }





    @Override
    public void init() {

    }

    @Override
    public void handleData() {

    }

    @Override
    public ReportVO analyse() {
        return null;
    }


    @Override
    protected void buyStocks() {

    }

    @Override
    protected void sellStocks() {

    }


    /**
     *
     * @param factorEntities  交易日当天的股票池中各个股票的因子数据
     * @return  各个股票及其综合因子
     */
    private Map<String,Double>  computeValidFactors(List<FactorEntity> factorEntities){

        double [] PEs = new double[0];   double avg_pe; double svar_pe;
        double [] PBs= new double[0];    double avg_pb; double svar_pb;
        double [] PSs= new double[0];    double avg_ps; double svar_ps;
        double [] PCFs= new double[0];   double avg_pcf;double svar_pcf;
        double [] VOL5s= new double[0];  double avg_vol5;double svar_vol5;
        double [] VOL10s= new double[0]; double avg_vol10;double svar_vol10;
        double [] VOL60s= new double[0]; double avg_vol60;double svar_vol60;
        double [] VOL120s= new double[0];double avg_vol120;double svar_vol120;
        /**
         * 遍历factorEntities对各个数组进行赋值
        */
        for(int i = 0; i< factorEntities.size(); i++){

            for(Map.Entry<AnalysisFactor,Double>
                    entry: weightedFactors.entrySet()){
                switch(entry.getKey()){
                    case PE:
                        if(i==0){
                            PEs = new double[factorEntities.size()];
                        }

                        PEs[i]= factorEntities.get(i).getPe();
                        break;

                    case PB:
                        if(i==0){
                            PBs = new double[factorEntities.size()];
                        }

                        PBs[i]= factorEntities.get(i).getPb();
                        break;

                    case PS:
                        if(i==0){
                            PSs = new double[factorEntities.size()];
                        }

                        PSs[i]= factorEntities.get(i).getPs();
                        break;

                    case PCF:
                        if(i==0){
                            PCFs = new double[factorEntities.size()];
                        }

                        PCFs[i]= factorEntities.get(i).getPcf();
                        break;

                    case VOL5:
                        if(i==0){
                            VOL5s = new double[factorEntities.size()];
                        }

                        VOL5s[i]= factorEntities.get(i).getVol5();

                        break;
                    case VOL10:
                        if(i==0){
                            VOL10s = new double[factorEntities.size()];
                        }

                        VOL10s[i]= factorEntities.get(i).getVol10();

                        break;
                    case VOL60:
                        if(i==0){
                            VOL60s = new double[factorEntities.size()];
                        }

                        VOL60s[i]= factorEntities.get(i).getVol60();

                        break;
                    case VOL120:
                        if(i==0){
                            VOL120s = new double[factorEntities.size()];
                        }

                        VOL120s[i]= factorEntities.get(i).getVol120();
                        break;

                    default:
                        break;
                }
            }

        }



        for(Map.Entry<AnalysisFactor,Double>
                entry: weightedFactors.entrySet()){

            switch(entry.getKey()){

                case PE:
                    avg_pe= MathHelper.computeAverage(PEs);
                    svar_pe=MathHelper.computeStandardVar(PEs);
                    break;
                case PB:
                    avg_pb= MathHelper.computeAverage(PBs);
                    svar_pb=MathHelper.computeStandardVar(PBs);
                    break;

                case PS:
                    avg_ps= MathHelper.computeAverage(PSs);
                    svar_ps=MathHelper.computeStandardVar(PSs);
                    break;
                case PCF:
                    avg_pcf= MathHelper.computeAverage(PCFs);
                    svar_pcf=MathHelper.computeStandardVar(PCFs);
                    break;
                case VOL5:
                    avg_vol5= MathHelper.computeAverage(VOL5s);
                    svar_vol5=MathHelper.computeStandardVar(VOL5s);
                    break;
                case VOL10:
                    avg_vol10= MathHelper.computeAverage(VOL10s);
                    svar_vol10=MathHelper.computeStandardVar(VOL10s);
                    break;
                case VOL60:
                    avg_vol60= MathHelper.computeAverage(VOL60s);
                    svar_vol60=MathHelper.computeStandardVar(VOL60s);
                    break;
                case VOL120:
                    avg_vol120= MathHelper.computeAverage(VOL120s);
                    svar_vol120=MathHelper.computeStandardVar(VOL120s);
                    break;
                default:
                    break;

            }

        }






        return new HashMap<>();
    }


    private double getStandardizedFactorValue(double target, double avg , double var){
        double result=0;
        /**
         * x'= (x-u)/v  标准化为正态分布
         */
        result = (target-avg)/var;
        return result;
    }
}
