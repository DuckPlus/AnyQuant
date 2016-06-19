package service.impl.strategy;

import org.springframework.stereotype.Service;
import util.DateCalculator;
import util.MyDate;
import vo.ReportVO;

import java.util.List;

/**
 * Created by 67534 on 2016/5/27.
 */
@Service
public class Strategy_Vol extends MultiStockStrategy{


    public Strategy_Vol()
    {
        super();
    }


    public void setPara_Vol(double capital, double taxRate, String baseCode ,
                           MyDate start , MyDate end , int vol, int interval ){

        super.setPara_Mutil(capital,taxRate,baseCode,start,end,vol);
        this.interval=interval;
    }


    /**
     * 初始买入交易日当天交易量最小的vol只股票
     */
    @Override
    public void init() {

        System.out.println("Strategy_VOL init-------");
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

        this.sellStocks();

        this.buyStocks();
    }

    @Override
    public ReportVO analyse() {
       return simpleAnalyse();
    }


    /**
     * 买入交易日当天交易量最小的vol只股票
     */
    @Override
    protected boolean buyStocks() {
        return super.simpleBuyStocks();
    }


    /**
     * 简单平仓，并计算累计收益率
     */
    @Override
    protected boolean sellStocks()
    {
       return  super.simpleSellStocks();
    }

    @Override
    protected List<String> getSelectedStocks() {
        /**
         * 获取市场上最近interval天内成交量最小的vol只股票加入股票池
         */
        MyDate start= DateCalculator.getAnotherDay(curTradeDay,-interval);
        List<String> codes = stockDataDAO.getStockCodeByVolDec(start,curTradeDay,vol);

        return codes;
    }


}