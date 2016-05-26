package service.impl.strategy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.MyDate;

import static org.junit.Assert.fail;

/**
 * Created by 67534 on 2016/5/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
        (locations = {"classpath:/META-INF/applicationContext.xml","classpath:/META-INF/infrastructure.xml"})

public class Strategy_PETest {
    @Autowired
    Strategy_PE strategy_pe ;

    double capital=10000;
    double taxRate=0.001;
    String baseCode="000001";
    MyDate start =MyDate.getDateFromString("2015-01-01");
    MyDate end=MyDate.getDateFromString("2016-01-01");
    int interval=30;



    @Before
    public void setUp() throws Exception {
        strategy_pe.initStrategy_PE
                (capital,taxRate,baseCode,start,end,interval);

        if(strategy_pe!=null){
            System.out.println("capital: "+strategy_pe.capital);
            System.out.println("taxRate: "+strategy_pe.taxRate);
            System.out.println("baseCode: "+strategy_pe.baseCode);
            System.out.println("interval: "+strategy_pe.interval);
            System.out.println("start: "+strategy_pe.start.DateToString());
            System.out.println("end: "+strategy_pe.end.DateToString());
        }else{
            fail("null");
        }
    }

    @Test
    public void init() throws Exception {

        strategy_pe.init();
    }

    @Test
    public void handleData() throws Exception {

    }

    @Test
    public void analyse() throws Exception {

    }







}