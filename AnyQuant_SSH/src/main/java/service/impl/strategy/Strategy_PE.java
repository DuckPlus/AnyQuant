package service.impl.strategy;

import util.MyDate;
import vo.ReportVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 67534 on 2016/5/25.
 */
public class Strategy_PE extends BaseStrategy {
    /**
     * 调仓间隔
     */
    public int interval;
    /**
     * 股票池
     */
    public List<String> stocks;

    public Strategy_PE(){
        super();
    }
    public Strategy_PE(double capital, double taxRate, String baseCode ,
                       MyDate start , MyDate end , int interval ){
        super(capital,taxRate,baseCode,start,end);
        this.interval=interval;
        this.stocks = new ArrayList<>();
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
}
