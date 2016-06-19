package service.impl.strategy;

import util.MyDate;
import vo.CumRtnVO;
import vo.ReportVO;
import vo.TradeDataVO;
import vo.TradeDetailVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 67534 on 2016/5/27.
 */
public abstract class MultiStockStrategy extends BaseStrategy {

    /**
     * 股票池
     */
    protected List<String> stocks;

    /**
     * 每次投资股票数
     */
    protected int vol;

    /**
     * 记录每只股票购买的手数
     */
    protected int [] lots;
    /**
     *记录每只股票的买入价格
     */
    protected double [] buy_Prices;
    /**
     *记录每只股票的卖出价格
     */
    protected double [] sell_Prices;

    /**
     * 用于存储股票代码和名称
     */
    protected Map<String,String> codeAndNames;


    public MultiStockStrategy()
    {
        super();
    }


    public void setPara_Mutil(double capital, double taxRate,
                              String baseCode , MyDate start , MyDate end, int vol){
        super.setPara(capital,taxRate,baseCode,start,end);

        this.stocks = new ArrayList<>();
        this.vol=vol;
        this.lots=new int [vol];
        this.buy_Prices=new double [vol];
        this.sell_Prices=new double [vol];

    }


    @Override
    public abstract void init();


    @Override
    public abstract void handleData();

    @Override
    public abstract ReportVO analyse();


    /**
     * 抽象的买入方法
     */
    protected abstract  boolean buyStocks();

    /**
     * 抽象的卖出方法
     */
    protected abstract  boolean sellStocks();


    protected  void fatchNames(){
        this.codeAndNames = new HashMap<>();
        List<String> names = this.stockDAO.getNames(stocks);
        for(int i=0;i<stocks.size();i++){
            codeAndNames.put(stocks.get(i),names.get(i));
        }
    }



    public boolean simpleBuyStocks(){
        if(curCapital<=0){
            return false;
        }

        TradeDataVO tradeDataVO = new TradeDataVO();
        tradeDataVO.tradeDate=curTradeDay;

        double tempExpense=0;
        /**
         * 首先替换股票池
         */
        stocks=getSelectedStocks();

        /**
         * 因为这个策略每次的股票池是动态生成的因此，股票名称也要动态获取
         */
        this.fatchNames();

        //System.out.println("size: "+stocks.size()+"---------------------");

        /**
         * 获取每只股票交易日当天的均价（总交易额/总交易量）
         * 因为可能会出现返回值不足vol个数据，因此先补充0，再赋值
         */
        double [] temp=stockDataDAO.getAvgPriceByCodes(stocks,curTradeDay);
        buy_Prices= new double[vol]; //这里讲买入价格全设为0
        int i=0;
        for( i=0;i<stocks.size();i++){
            buy_Prices[i]=temp[i];
        }


        double expensePerStock = curCapital/(double)stocks.size();
        /**
         * 注意stocks是被选中的vol只股票
         * 确定每只股票买入的手数
         * 并记录花费
         */
        for(i=0;i<stocks.size();i++){
            /**
             * 如果买入价格为0，就忽略该股票
             * 把买入手数设为0
             */
            if(buy_Prices[i]==0){
                lots[i]=0;
            }else{

                lots[i]= (int) (expensePerStock/(buy_Prices[i]*stocksPerLot));
                //System.out.println("buy "+stocks.get(i)+" "+lots[i]*stocksPerLot+" at price: "+buy_Prices[i]);

                expense+=lots[i]*stocksPerLot*buy_Prices[i];
                tempExpense+=lots[i]*stocksPerLot*buy_Prices[i];

                addNewTradeDetailVO(i,true,tradeDataVO);

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
     * 简单平仓，并计算累计收益率
     */
    public boolean simpleSellStocks(){
        if(stocks.size()==0){
            return false;
        }

        TradeDataVO tradeDataVO = new TradeDataVO();
        tradeDataVO.tradeDate=curTradeDay;
        double tempIncome=0;
        double tempTax=0;

        /**
         * 获取当日的股票池的均价
         */
        double [] temp=stockDataDAO.getAvgPriceByCodes(stocks,curTradeDay);
//        System.out.println("temp.size()"+temp.length);
//        System.out.println(" get sell_Prices"+sell_Prices);
          sell_Prices= new double[vol];
        for(int i=0;i<temp.length;i++){
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

                this.addNewTradeDetailVO(i,false,tradeDataVO);

                //System.out.println("sell "+stocks.get(i)+" "+lots[i]*stocksPerLot+" at price: "+sell_Prices[i]);

                income+=sell_Prices[i]*lots[i]*stocksPerLot;
                tempIncome+=sell_Prices[i]*lots[i]*stocksPerLot;
                tax+=sell_Prices[i]*lots[i]*stocksPerLot*taxRate;
                tempTax+=sell_Prices[i]*lots[i]*stocksPerLot*taxRate;

            }

        }

        stocks.clear();

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


    protected void addNewTradeDetailVO(int index, boolean buyOrSell, TradeDataVO tradeDataVO){

        TradeDetailVO detailVO = new TradeDetailVO();

        detailVO.code=stocks.get(index);
        detailVO.codeName=codeAndNames.get(stocks.get(index));
        detailVO.buyOrSell=buyOrSell;
        detailVO.numofTrade=lots[index];
        if(buyOrSell){
            detailVO.tradePrice=buy_Prices[index];
        }else{
            detailVO.tradePrice=sell_Prices[index];
        }


        tradeDataVO.tradeDetailVOs.add(detailVO);
    }


    protected abstract List<String> getSelectedStocks();
}
