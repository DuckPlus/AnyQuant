package service.impl.strategy;

import org.springframework.stereotype.Service;
import util.MyDate;
import vo.ReportVO;

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
        this.low_PE=25;
        this.high_PE=30;
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
    protected void buyStocks(){
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
         * 其中股票价格处于合理区间内
         */
        int i=0;
        for(String code:codes){
            double price =stockDataDAO.getAvgPriceByCode(code,curTradeDay);

           // if(  low_Price<price & price<high_Price ){
                stocks.add(code);
                //System.out.println(code);
                i++;
          //  }

            if(i==vol){
                break;
            }
        }
        System.out.println("size: "+stocks.size()+"---------------------");

        /**
         * 获取每只股票交易日当天的均价（总交易额/总交易量）
         * 因为可能会出现返回值不足vol个数据，因此先补充0，再赋值
         */
        double [] temp=stockDataDAO.getAvgPriceByCodes(stocks,curTradeDay);
        buy_Prices= new double[vol]; //这里讲买入价格全设为0
        for( i=0;i<temp.length;i++){
            buy_Prices[i]=temp[i];
        }



        /**
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
                lots[i]= (int) (capital/vol/(buy_Prices[i]*stocksPerLot));
            }

            System.out.println("buy "+stocks.get(i)+" "+lots[i]*stocksPerLot+" at price: "+buy_Prices[i]);
            expense+=lots[i]*stocksPerLot*buy_Prices[i];
        }

        /**
         * 记录当日的指数价格
         */
        base_BuyPrice=benchMarkDAO.getAvgPrice(this.baseCode,curTradeDay);
    }


    /**
     * 简单平仓，并计算累计收益率
     */
    @Override
    protected void sellStocks() {
        this.simpleSellStocks();
    }


}
