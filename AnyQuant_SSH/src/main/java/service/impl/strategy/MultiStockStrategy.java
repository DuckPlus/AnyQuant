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
    protected abstract  void buyStocks();

    /**
     * 抽象的卖出方法
     */
    protected abstract  void sellStocks();


    protected  void fatchNames(){
        this.codeAndNames = new HashMap<>();
        List<String> names = this.stockDAO.getNames(stocks);
        for(int i=0;i<stocks.size();i++){
            codeAndNames.put(stocks.get(i),names.get(i));
        }
    }

    /**
     * 简单平仓，并计算累计收益率
     */
    public void simpleSellStocks(){

        TradeDataVO tradeDataVO = new TradeDataVO();
        tradeDataVO.tradeDate=curTradeDay;

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
                tax+=sell_Prices[i]*lots[i]*stocksPerLot*taxRate;

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
        this.curCapital=this.curCapital-this.tax+this.income;
        System.out.println("curCapital: "+this.curCapital);

        /**
         * 向结果链表中添加一个元素
         */
        CumRtnVO vo = new CumRtnVO(baseRtnRate,cumRtnRate,curTradeDay);
        this.reportVO.cumRtnVOList.add(vo);

        tradeDataVO.nowCapital=curCapital;
        tradeDataVO.profit=this.profit;
        this.reportVO.tradeDataVOList.add(tradeDataVO);
    }



    protected void addNewTradeDetailVO(int index, boolean buyOrSell, TradeDataVO tradeDataVO){

        TradeDetailVO detailVO = new TradeDetailVO();

        detailVO.code=stocks.get(index);
        detailVO.codeName=codeAndNames.get(stocks.get(index));
        detailVO.buyOrSell=buyOrSell;
        detailVO.numofTrade=lots[index];
        detailVO.tradePrice=buy_Prices[index];

        tradeDataVO.tradeDetailVOs.add(detailVO);
    }

}
