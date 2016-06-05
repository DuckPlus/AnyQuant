package service.impl.strategy;

import util.MyDate;
import vo.ReportVO;

import java.util.ArrayList;
import java.util.List;

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



}
