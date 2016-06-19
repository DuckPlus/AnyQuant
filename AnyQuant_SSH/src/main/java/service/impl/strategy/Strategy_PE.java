package service.impl.strategy;

import org.springframework.stereotype.Service;
import util.MyDate;
import vo.ReportVO;

import java.util.ArrayList;
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
     * 买入PE值在合理区间内的vol只股票
     */
    @Override
    protected boolean buyStocks(){
        return super.simpleBuyStocks();
    }


    /**
     * 简单平仓，并计算累计收益率
     */
    @Override
    protected boolean sellStocks() {

       return super.simpleSellStocks();

    }

    @Override
    protected List<String> getSelectedStocks() {
        /**
         * 获取符合PE区间的候选股票
         */
        List<String> codes = stockDataDAO.getStockCodeByPE(curTradeDay,low_PE,high_PE);
        List<String> selectedCodes=new ArrayList<>();
        /**
         * 挑选vol只股票加入股票池
         */

        for(int i=0;i<vol;i++){
            selectedCodes.add(codes.get(i));
        }

        return selectedCodes;
    }


}
