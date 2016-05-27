package service.impl.strategy;

import org.springframework.stereotype.Service;
import util.MyDate;
import vo.CumRtnVO;
import vo.ReportVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 67534 on 2016/5/25.
 */
@Service
public class Strategy_PE extends BaseStrategy {

    /**
     * 调仓间隔
     */
    public int interval;

    /**
     * 股票池
     */
    public List<String> stocks;

    /**
     * 每次投资10只股票
     */
    private int vol;
    /**
     * 界定PE的合理界限（20,40）
     */
    private int low_PE ,high_PE;

    /**
     * 记录每只股票购买的手数
     */
    private int [] lots;
    /**
     *记录每只股票的买入价格
     */
    private double [] buy_Prices;
    /**
     *记录每只股票的卖出价格
     */
    private double [] sell_Prices;





    public Strategy_PE()
    {
        super();
    }

    public void initStrategy_PE(double capital, double taxRate, String baseCode ,
                       MyDate start , MyDate end , int interval ){
        super.initBaseStrategy(capital,taxRate,baseCode,start,end);
        this.interval=interval;
        this.stocks = new ArrayList<>();

        this.vol=10;
        this.low_PE=20;
        this.high_PE=40;
        this.lots=new int [vol];
        this.buy_Prices=new double [vol];
        this.sell_Prices=new double [vol];
    }


    /**
     * 初始任意买入10只PE大于20的股票
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
        init();
        for(int i=interval;i<this.validDates.length;i+=interval){
            curTradeDay=this.validDates[i];
            System.out.println("handle the "+i+"th day"+"date : "+curTradeDay.DateToString());
            this.handleData();
        }
        ReportVO reportVO = new ReportVO();
        reportVO.cumRtnVOList=this.cumRtnVOList;

        return reportVO;
    }



    private void buyStocks(){
        /**
         * 首先清空股票池
         */
        stocks.clear();
        /**
         * 获取符合PE区间的候选股票
         */
        List<String> codes = stockDataDAO.getStockCodeByPE(curTradeDay,low_PE,high_PE);

        /**
         * 随机挑选vol只股票加入股票池
         */
        int i=0;
        for(String code:codes){
            stocks.add(code);
            System.out.println(code);
            i++;
            if(i==vol){
                break;
            }
        }
        System.out.println("size: "+stocks.size());

        /**
         * 获取每只股票交易日当天的均价（总交易额/总交易量）
         */
        double [] temp=stockDataDAO.getAvgPriceByCodes(stocks,curTradeDay);
        buy_Prices= new double[vol];
        for( i=0;i<temp.length;i++){
            buy_Prices[i]=temp[i];
        }

        System.out.println("lots.lenth:"+lots.length);
        System.out.println("buy_prices.lenth:"+buy_Prices.length);
        System.out.println("sell_prices.lenth:"+sell_Prices.length);

        /**
         * 确定每只股票买入的手数
         * 并记录花费
         */
        for(i=0;i<stocks.size();i++){
            System.out.println("i: "+i);
            lots[i]= (int) (capital/vol/(buy_Prices[i]*stocksPerLot));
            System.out.println("buy "+stocks.get(i)+" "+lots[i]*stocksPerLot+" at price: "+buy_Prices[i]);
            expense+=lots[i]*stocksPerLot*buy_Prices[i];
        }

        /**
         * 记录当日的指数价格
         */
        base_BuyPrice=benchMarkDAO.getAvgPrice(this.baseCode,curTradeDay);
    }

    private void sellStocks(){
        /**
         * 获取当日的股票池的均价
         */
        double [] temp=stockDataDAO.getAvgPriceByCodes(stocks,curTradeDay);
        System.out.println("temp.size()"+temp.length);
        sell_Prices= new double[vol];
        for(int i=0;i<temp.length;i++){
            sell_Prices[i]=temp[i];
        }

        for(int i=0;i<stocks.size();i++){
            System.out.println("sell "+stocks.get(i)+" "+lots[i]*stocksPerLot+" at price: "+sell_Prices[i]);
            income+=sell_Prices[i]*lots[i]*stocksPerLot;
        }
        stocks.clear();

        /**
         * 计算测试股票的累计收益率
         */
        profit=income-expense;
        cumRtnRate=profit/expense;

        /**
         * 计算测试指数的累计收益率
         */
        base_SellPrice=benchMarkDAO.getAvgPrice(this.baseCode,curTradeDay);
        baseRtnRate+=(base_SellPrice-base_BuyPrice)/base_BuyPrice;

        /**
         * 向结果链表中添加一个元素
         */
        CumRtnVO vo = new CumRtnVO(baseRtnRate,cumRtnRate,curTradeDay);
        this.cumRtnVOList.add(vo);
    }


}
