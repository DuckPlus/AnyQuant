package service;

import org.junit.Before;
import org.junit.Test;
import service.impl.strategy.Strategy_PE;
import util.MyDate;

import static org.junit.Assert.fail;


/**
 * Created by 67534 on 2016/5/25.
 */

public class Strategy_PETest {
    Strategy_PE strategy_pe;

    @Before
    public void setUp() throws Exception {
        double capital=10000;
        double taxRate=0.001;
        String baseCode="000001";
        MyDate start =MyDate.getDateFromString("2015-01-01");
        MyDate end=MyDate.getDateFromString("2016-01-01");
        int interval=30;
        strategy_pe =
                new Strategy_PE(capital,taxRate,baseCode,start,end,interval);

    }

    @Test
    public void initTest() throws Exception {
        if(strategy_pe!=null){
            System.out.println("capital: "+strategy_pe.capital);
            System.out.println("taxRate: "+strategy_pe.taxRate);
            System.out.println("baseCode: "+strategy_pe.baseCode);
            System.out.println("interval: "+strategy_pe.interval);
        }else{
            fail("null");
        }

    }




}