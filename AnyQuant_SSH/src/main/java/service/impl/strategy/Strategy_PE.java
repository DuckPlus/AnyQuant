package service.impl.strategy;

import org.springframework.stereotype.Service;
import util.MyDate;
import vo.ReportVO;
import vo.TradeDataVO;

import java.util.List;

/**
 * Created by 67534 on 2016/5/25.
 */
@Service
public class Strategy_PE extends MultiStockStrategy {

    /**
     * 界定PE的合理界限（20,40）
     */
    private int low_PE ,high_PE;

    public Strategy_PE()
    {
        super();
    }

    public void setPara_PE(double capital, double taxRate, String baseCode ,
                           MyDate start , MyDate end , int vol, int interval ){

        super.setPara_Mutil(capital,taxRate,baseCode,start,end,vol);

        this.interval=interval;
        this.low_PE=20;
        this.high_PE=40;
    }


    /**
     * 初始任意买入vol只PE大于20的股票
     */
    @Override
    public void init()
    {
        System.out.println("Strategy_PE init-------");
        this.curTradeDay=start;
        this.buyStocks();
    }

    /**
     * 调仓日首先平仓，再购买
     */
    @Override
    public void handleData() {


        this.sellStocks();

        this.buyStocks();

    }

    @Override
    public ReportVO analyse() {
        return simpleAnalyse();
    }

    /**
     * 买入PE值在合理区间内的vol只股票
     */
    @Override
    protected boolean buyStocks(){
        if(curCapital<=0){
            return false;
        }

        TradeDataVO tradeDataVO = new TradeDataVO();
        tradeDataVO.tradeDate=curTradeDay;

        double tempExpense=0;
        /**
         * 首先清空股票池
         */
        stocks.clear();
        /**
         * 获取符合PE区间的候选股票
         */
        List<String> codes = stockDataDAO.getStockCodeByPE(curTradeDay,low_PE,high_PE);

        /**
         * 挑选vol只股票加入股票池
         */
        int gap=codes.size()/vol;
        int i;
        for( i=0;i<codes.size();i+=gap){
            stocks.add(codes.get(i));
            i++;
            if(i==vol){
                break;
            }
        }


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
                super.addNewTradeDetailVO(i,true,tradeDataVO);

            }


        }

        /**
         * 记录当日的指数价格
         */
        base_BuyPrice=benchMarkDAO.getAvgPrice(this.baseCode,curTradeDay);

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
    @Override
    protected boolean sellStocks() {

       return super.simpleSellStocks();
    }


}
