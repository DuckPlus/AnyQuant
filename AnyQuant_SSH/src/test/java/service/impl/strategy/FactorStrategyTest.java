package service.impl.strategy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.MyDate;
import util.enumration.AnalysisFactor;
import vo.CumRtnVO;
import vo.ReportVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;

/**
 * Created by 67534 on 2016/6/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
        (locations = {"classpath:/META-INF/applicationContext.xml","classpath:/META-INF/infrastructure.xml"})

public class FactorStrategyTest {
    @Autowired
    FactorStrategy strategy;




    @Before
    public void setUp() throws Exception {
        double capital=1000000;
        double taxRate=0.001;
        String baseCode="000300";
        MyDate start =MyDate.getDateFromString("2015-01-01");
        MyDate end=MyDate.getDateFromString("2016-01-01");
        String [] codes = {"sh603306","sh600137","sh600858","sh600083","sh600121",
                "sh600750", "sh600978", "sh600775", "sh600338",
                "sh601567", "sh600682", "sh600499", "sh601579", "sh600712"};

        Map< AnalysisFactor,Double> weightedFactors;
        weightedFactors= new HashMap<AnalysisFactor,Double>();
        weightedFactors.put(AnalysisFactor.PE,0.25);
        weightedFactors.put(AnalysisFactor.PS,0.25);
        weightedFactors.put(AnalysisFactor.PCF,0.25);
        weightedFactors.put(AnalysisFactor.VOL5,0.25);

        double [] investWeight= {0.4,0.2,0.2,0.1,0.1};

        int interval=30;

        strategy.setPara_Factor
                (capital,taxRate,baseCode,start,end,
                        new ArrayList<>(Arrays.asList(codes)),weightedFactors,investWeight,interval);

        if(strategy!=null){
            System.out.println("capital: "+strategy.capital);
            System.out.println("taxRate: "+strategy.taxRate);
            System.out.println("baseCode: "+strategy.baseCode);
            System.out.println("interval: "+strategy.interval);
            System.out.println("start: "+strategy.start.DateToString());
            System.out.println("end: "+strategy.end.DateToString());
            System.out.println("size of stock pool:"+strategy.stocks.size());
            for(Map.Entry<AnalysisFactor,Double> entry:weightedFactors.entrySet()){
                System.out.println(entry.getKey()+"  "+entry.getValue());
            }

            System.out.println("investWright: ");
            for(int i=0 ; i<investWeight.length;i++){
                System.out.println(investWeight[i]+"  ");
            }

            System.out.println("numOfLevel: "+strategy.numOfLevel);

        }else{
            fail("null");
        }
    }

    @Test
    public void analyse() throws Exception {
        ReportVO vo = strategy.analyse();
        if(vo==null){
            fail();
        }else{
            for(CumRtnVO temp : vo.cumRtnVOList){
                System.out.println("date: "+temp.date.DateToString()+" test: "+temp.testValue+" base: "+temp.baseValue);
            }
        }
    }

}