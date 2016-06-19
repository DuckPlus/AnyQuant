package service.impl.strategy;

import DAO.FactorDAO;
import entity.FactorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.helper.MathHelper;
import util.MyDate;
import util.enumration.AnalysisFactor;
import vo.CumRtnVO;
import vo.ReportVO;
import vo.TradeDataVO;

import java.util.ArrayList;
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
@Service
public class FactorStrategy extends MultiStockStrategy {
    @Autowired
    FactorDAO factorDAO;

    /**
     * 用户选择的因子和比重
     */
    Map  <AnalysisFactor,Double>  weightedFactors;

    /**
     * 每次买入前计算各个股票的综合因子
     */
    Map<String,Double> finalFactors;


    /**
     * 投资比重
     */
    double [] investWeight;


    int numOfLevel;
    /**
     * 每层的股票数
     */
    int gap;

    /**
     * 交易当天的股票因子数据
     */
    List<FactorEntity> curFactorEntities;

    /**
     *
     * @param capital
     * @param taxRate
     * @param baseCode
     * @param start
     * @param end
     * @param stocks            股票池
     * @param weightedFactors  因子和权重
     * @param investWeight     投资权重,由高到低，一档是最看好的，五档是不看好的
     *                         e.g{0.4,0.2,0.2,0.1,0.1}
     * @param interval         交易间隔
     */
    public void setPara_Factor(double capital, double taxRate,
                              String baseCode , MyDate start , MyDate end,
                               List<String> stocks,  Map  <AnalysisFactor,Double>  weightedFactors,
                               double [] investWeight,int interval){
        super.setPara_Mutil(capital,taxRate,baseCode,start,end,stocks.size());

        this.stocks = stocks;
        this.weightedFactors=weightedFactors;
        this.investWeight=investWeight;
        this.interval=interval;
        this.numOfLevel=investWeight.length;
        this.gap=stocks.size()/numOfLevel; //每一层的股票数量

        /**
         * 因为这个策略每次的股票池是固定的，股票名称只要获取一次即可
         */
        this.fatchNames();

    }




    /**
     *
     */
    @Override
    public void init() {

        System.out.println("Strategy_Factor init-------");
        this.curTradeDay=start;
        this.buyStocks();
        /**
         * 记录最初的指数价格
         */
        base_BuyPrice=benchMarkDAO.getAvgPrice(this.baseCode,start);
    }

    /**
     * 调仓日首先平仓，再购买
     */
    @Override
    public void handleData() {
        sellStocks();
        buyStocks();
    }

    @Override
    public ReportVO analyse() {
        return this.simpleAnalyse();
    }


    /**
     * 先按照因子比重计算股票池中的各个股票的finalFactor
     * 将股票池按照 finalFactor 排序，分层
     * 每层根据投资比重的进行买入，每只股票分配的钱相同
     */
    @Override
    protected boolean buyStocks() {
        if(curCapital<=0){
            return false;
        }

        TradeDataVO tradeDataVO = new TradeDataVO();
        tradeDataVO.tradeDate=curTradeDay;
        double tempExpense=0;

        this.curFactorEntities=this.factorDAO.getFactorAtDate(stocks,curTradeDay);
        List<Map.Entry<String,Double>>  tempMap = getSortedFinal_Factors(curFactorEntities);


        /**
         * 将股票池换成按照综合因子排序后的列表
         */
        this.stocks = new ArrayList<>();
        for(int i=0;i<tempMap.size();i++){
            stocks.add(tempMap.get(i).getKey());
            System.out.println(tempMap.get(i).getKey()+"  "+tempMap.get(i).getValue());
        }


        /**
         * 获取每只股票交易日当天的均价（总交易额/总交易量）
         * 因为可能会出现返回值不足vol个数据，因此先补充0，再赋值
         */
        double [] temp=stockDataDAO.getAvgPriceByCodes(stocks,curTradeDay);
        buy_Prices = new double[vol]; //这里讲买入价格全设为0
        for(int i=0;i<temp.length;i++){
            buy_Prices[i]=temp[i];
        }



        System.out.println(this.curCapital+" can spend");
        /**
         * 遍历各个层
         */
        for(int j=0;j<numOfLevel;j++){
            double expensePerStock = curCapital*investWeight[j]/(double)gap;

           // System.out.println("该层分配："+curCapital*investWeight[i]);
           // System.out.println("该层个数："+gap);
           // System.out.println("每股分配："+expensePerStock);

            /**
             * 对于每一层：
             * 确定每只股票买入的手数
             * 并记录花费
             */
            for(int i=j*gap;i<(j+1)*gap;i++){
                    /**
                     * 如果买入价格为0，就忽略该股票
                     * 把买入手数设为0
                     */
                    if(buy_Prices[i]==0){
                        lots[i]=0;
                    }else{
                        lots[i]= (int) (expensePerStock/(buy_Prices[i]*stocksPerLot));
                        expense+=lots[i]*stocksPerLot*buy_Prices[i];
                        tempExpense+=lots[i]*stocksPerLot*buy_Prices[i];
                    //    System.out.println("buy "+stocks.get(j)+" "+lots[j]*stocksPerLot+" at price: "+buy_Prices[j]);

                        super.addNewTradeDetailVO(i,true,tradeDataVO);

                    }

            }

        }

        /**
         * 更新当前资本
         */
        this.curCapital=this.curCapital-tempExpense;

        tradeDataVO.nowCapital=curCapital;
        tradeDataVO.profit=this.profit;
        this.reportVO.tradeDataVOList.add(tradeDataVO);

        return true;
    }


    /**
     * 简单平仓
     */
    @Override
    protected boolean sellStocks() {
        if(stocks.size()==0){
            return false;
        }
        TradeDataVO tradeDataVO = new TradeDataVO();
        tradeDataVO.tradeDate=curTradeDay;
        double tempIncome = 0;
        double tempTax=0;

        /**
         * 获取当日的股票池的均价
         */
        double [] temp=stockDataDAO.getAvgPriceByCodes(stocks,curTradeDay);
//        System.out.println("temp.size()"+temp.length);
//        System.out.println(" get sell_Prices"+sell_Prices);
        sell_Prices= new double[vol];
        for(int j=0;j<this.numOfLevel;j++){

            for(int i=j*gap;i<(j+1)*gap;i++){
                /**
                 * 如果买入价格是0，说明数据出错，
                 * 将卖出价格也设为0，从而忽略这只股票
                 */
                if(buy_Prices[i]!=0){

                    /**
                     * 如果卖出价格为0而买入不为0,说明数据出错，
                     * 把卖出价格设为买入价，从而忽略这只股票
                     */
                    if(temp[i]==0){
                        sell_Prices[i]=buy_Prices[i];
                    }else{
                        sell_Prices[i]=temp[i];
                    }

                    // System.out.println("sell "+stocks.get(i)+" "+lots[i]*stocksPerLot+" at price: "+sell_Prices[i]);

                    super.addNewTradeDetailVO(i,false,tradeDataVO);

                    income+=sell_Prices[i]*lots[i]*stocksPerLot;
                    tempIncome+=sell_Prices[i]*lots[i]*stocksPerLot;
                    tax+=sell_Prices[i]*lots[i]*stocksPerLot*taxRate;
                    tempTax+=sell_Prices[i]*lots[i]*stocksPerLot*taxRate;
                }

            }

        }

        //stocks.clear();

        /**
         * 计算测试股票的累计收益率
         */
//        profit=income-expense-tax;
//        cumRtnRate=profit/expense;
        computeCumRtnRate();
        /**
         * 计算测试指数的累计收益率
         */
//        base_SellPrice=benchMarkDAO.getAvgPrice(this.baseCode,curTradeDay);
//        baseRtnRate+=(base_SellPrice-base_BuyPrice-base_SellPrice*taxRate)/base_BuyPrice;
        computeBaseRtnRate();

        /**
         * 更新当前资本
         */
        this.curCapital=this.curCapital-tempTax+tempIncome;
        System.out.println("curCapital: "+this.curCapital);
        /**
         * 向结果链表中添加一个元素
         */
        CumRtnVO vo = new CumRtnVO(baseRtnRate,cumRtnRate,curTradeDay);
        this.reportVO.cumRtnVOList.add(vo);

        tradeDataVO.nowCapital=curCapital;
        tradeDataVO.profit=this.profit;
        this.reportVO.tradeDataVOList.add(tradeDataVO);

        return true;
    }


    @Override
    protected List<String> getSelectedStocks() {
        return null;
    }


    /**
     *
     * @param factorEntities  交易日当天的股票池中各个股票的因子数据
     * @return  各个股票及其综合因子
     */
    public  List<Map.Entry<String,Double>>  getSortedFinal_Factors(List<FactorEntity> factorEntities){
        Map<String,Double>  resultMap = new HashMap<String,Double>();
        double [] PEs = new double[0];   double avg_pe=0; double svar_pe=0;
        double [] PBs= new double[0];    double avg_pb=0; double svar_pb=0;
        double [] PSs= new double[0];    double avg_ps=0; double svar_ps=0;
        double [] PCFs= new double[0];   double avg_pcf=0;double svar_pcf=0;
        double [] VOL5s= new double[0];  double avg_vol5=0;double svar_vol5=0;
        double [] VOL10s= new double[0]; double avg_vol10=0;double svar_vol10=0;
        double [] VOL60s= new double[0]; double avg_vol60=0;double svar_vol60=0;
        double [] VOL120s= new double[0];double avg_vol120=0;double svar_vol120=0;
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


        /**
        *计算各类因子的平均值和标准差
        */
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

        double  st_pe=0,st_pb=0,st_ps=0,st_pcf=0;
        double st_vol5=0,st_vol10=0,st_vol60=0,st_vol120=0;
        double final_Factor=0;

        /**
         * 遍历每只股票信息，遍历所有因子，计算无量纲化的因子值，累加成finalFactor并添加到map中
         */
        for(int i=0; i<factorEntities.size();i++){
            final_Factor=0;
            for(Map.Entry<AnalysisFactor,Double>
                    entry: weightedFactors.entrySet()){

                switch(entry.getKey()){

                    case PE:
                        st_pe=getStandardizedFactorValue(factorEntities.get(i).getPe(),
                               avg_pe,svar_pe);

                        st_pe=st_pe*entry.getValue();
                      //  System.out.println("st_pe:"+st_pe);

                        final_Factor+=st_pe;

                        break;
                    case PB:
                        st_pb=getStandardizedFactorValue(factorEntities.get(i).getPb(),
                                avg_pb,svar_pb);

                        st_pb=st_pb*entry.getValue();
                     //   System.out.println("st_pb:"+st_pb);
                        final_Factor+=st_pb;
                        break;

                    case PS:
                        st_ps=getStandardizedFactorValue(factorEntities.get(i).getPs(),
                                avg_ps,svar_ps);

                        st_ps=-1*st_ps*entry.getValue();
                     //   System.out.println("st_ps:"+st_ps);
                        final_Factor+=st_ps;
                        break;
                    case PCF:
                        st_pcf=getStandardizedFactorValue(factorEntities.get(i).getPcf(),
                                avg_pcf,svar_pcf);

                        st_pcf=-1*st_pcf*entry.getValue();
                     //   System.out.println("st_pcf:"+st_pcf);
                        final_Factor+=st_pcf;
                        break;
                    case VOL5:
                        st_vol5=getStandardizedFactorValue(factorEntities.get(i).getVol5(),
                                avg_vol5,svar_vol5);

                        st_vol5=st_vol5*entry.getValue();
                    //    System.out.println("st_vol5:"+st_vol5);

                        final_Factor+=st_vol5;
                        break;
                    case VOL10:
                        st_vol10=getStandardizedFactorValue(factorEntities.get(i).getVol10(),
                                avg_vol10,svar_vol10);

                        st_vol10=st_vol10*entry.getValue();
                        final_Factor+=st_vol10;
                        break;
                    case VOL60:
                        st_vol60=getStandardizedFactorValue(factorEntities.get(i).getVol60(),
                                avg_vol60,svar_vol60);

                        st_vol60=st_vol60*entry.getValue();
                        final_Factor+=st_vol60;
                        break;
                    case VOL120:
                        st_vol120=getStandardizedFactorValue(factorEntities.get(i).getVol120(),
                                avg_vol120,svar_vol120);

                        st_vol120=st_vol120*entry.getValue();
                        final_Factor+=st_vol120;
                        break;
                    default:
                        break;

                }
            }
          //  System.out.println(factorEntities.get(i).getCode()+"  "+final_Factor);
            resultMap.put(factorEntities.get(i).getCode(),final_Factor);
        }

        return sortMap(resultMap);
    }


    private List<Map.Entry<String,Double>> sortMap(Map<String,Double> map){
        List<Map.Entry<String,Double>> result = new ArrayList<>(map.entrySet());
        result.sort( (c1 ,c2) -> Double.compare(c2.getValue() , c1.getValue()));
        return  result;
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
