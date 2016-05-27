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
public class Strategy_PE extends BaseStrategy {

    /**
     * 调仓间隔
     */
    public int interval;
    /**
     * 股票池
     */
    public List<String> stocks;

    public Strategy_PE()
    {
        super();
    }

    public void initStrategy_PE(double capital, double taxRate, String baseCode ,
                       MyDate start , MyDate end , int interval ){
        super.initBaseStrategy(capital,taxRate,baseCode,start,end);
        this.interval=interval;
        this.stocks = new ArrayList<>();
    }


    /**
     * 初始任意买入10只PE大于20的股票
     */
    @Override
    public void init()
    {


        List<String> codes = stockDataDAO.getStockCodeByPE(start,20);

        int i=0;
        for(String code:codes){
            stocks.add(code);
            i++;
            if(i==10){
                break;
            }
        }




    }

    @Override
    public void handleData() {



    }

    @Override
    public ReportVO analyse() {
        init();

        return null;
    }
}
