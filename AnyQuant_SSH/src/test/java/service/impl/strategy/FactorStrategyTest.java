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
import vo.TradeDataVO;
import vo.TradeDetailVO;

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
        String [] codes = {
                "sh600004", "sh600010", "sh600099",  "sh600103", "sh600105",
                "sh600108", "sh600110", "sh600114", "sh600117", "sh600121",
                "sh600123", "sh600125", "sh600130", "sh600137","sh600160"};

        Map< AnalysisFactor,Double> weightedFactors;
        weightedFactors= new HashMap<AnalysisFactor,Double>();
        weightedFactors.put(AnalysisFactor.PE,0.5);
        weightedFactors.put(AnalysisFactor.VOL5,0.5);

        double [] investWeight= {0.7,0.1,0.1,0.05,0.05};

        int interval=60;

        strategy.setPara_Factor
                (capital,taxRate,baseCode,start,end,
                        new ArrayList<>(Arrays.asList(codes)),weightedFactors,
                        investWeight,interval);

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
            System.out.println("Detail-------");
            for(TradeDataVO tradeDataVO : vo.tradeDataVOList){
                System.out.print("date: "+tradeDataVO.tradeDate.DateToString());
                System.out.print("  profit: "+tradeDataVO.profit);
                System.out.println("  nowCaptial: "+tradeDataVO.nowCapital);
                for(TradeDetailVO detailVO : tradeDataVO.tradeDetailVOs){
                    if(detailVO.buyOrSell){
                        System.out.print("Buy ");
                    }else{
                        System.out.print("Sell ");
                    }
                    System.out.print(100*detailVO.numofTrade+" lots ");
                    System.out.print(detailVO.code+"  "+detailVO.codeName+" at");
                    System.out.println(" price: "+detailVO.tradePrice);

                }
            }


            System.out.println("PK-------");
            for(CumRtnVO temp : vo.cumRtnVOList){
                System.out.println("date: "+temp.date.DateToString()+" test: "+temp.testValue+" base: "+temp.baseValue);
            }
        }
    }

}