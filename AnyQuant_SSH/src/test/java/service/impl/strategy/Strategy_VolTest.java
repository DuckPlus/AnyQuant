package service.impl.strategy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.MyDate;
import vo.CumRtnVO;
import vo.ReportVO;

import static org.junit.Assert.fail;

/**
 * Created by 67534 on 2016/6/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
        (locations = {"classpath:/META-INF/applicationContext.xml","classpath:/META-INF/infrastructure.xml"})

public class Strategy_VolTest {
    @Autowired
    Strategy_Vol strategy_Vol;

    double capital=2000000;
    double taxRate=0.001;
    String baseCode="000300";
    MyDate start =MyDate.getDateFromString("2014-01-01");
    MyDate end=MyDate.getDateFromString("2016-01-01");
    int vol=5;
    int interval=60;



    @Before
    public void setUp() throws Exception {
        strategy_Vol.setPara_Vol
                (capital,taxRate,baseCode,start,end,vol,interval);

        if(strategy_Vol !=null){
            System.out.println("capital: "+ strategy_Vol.capital);
            System.out.println("taxRate: "+ strategy_Vol.taxRate);
            System.out.println("baseCode: "+ strategy_Vol.baseCode);
            System.out.println("interval: "+ strategy_Vol.interval);
            System.out.println("start: "+ strategy_Vol.start.DateToString());
            System.out.println("end: "+ strategy_Vol.end.DateToString());
        }else{
            fail("null");
        }
    }



    @Test
    public void analyse() throws Exception {
        ReportVO vo = strategy_Vol.analyse();
        if(vo==null){
            fail();
        }else{
            for(CumRtnVO temp : vo.cumRtnVOList){
                System.out.println("date: "+temp.date.DateToString()+" test: "+temp.testValue+" base: "+temp.baseValue);
            }
        }
    }

}