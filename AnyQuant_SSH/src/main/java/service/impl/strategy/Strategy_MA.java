package service.impl.strategy;

import util.MyDate;
import vo.ReportVO;

/**
 * Created by 67534 on 2016/5/25.
 */
public class Strategy_MA extends BaseStrategy{

    public String stockCode;

    public Strategy_MA(){
        super();
    }

    public Strategy_MA(double capital, double taxRate, String baseCode ,
                       MyDate start , MyDate end , String stockCode){
        super.setPara(capital,taxRate,baseCode,start,end);
        this.stockCode=stockCode;
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
