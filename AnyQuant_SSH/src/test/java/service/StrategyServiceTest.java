package service;

import DAO.StockDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.MyDate;
import vo.FactorJudgmentVO;

import java.util.List;

/**
 * @author Qiang
 * @date 6/6/16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
        (locations = {"classpath:/META-INF/applicationContext.xml","classpath:/META-INF/infrastructure.xml"})
public class StrategyServiceTest {
    @Autowired
    StrategyService service;
    @Autowired
    StockDAO stockDAO;
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void getStocksFactorJudgment() throws Exception {
        List<String> codes = stockDAO.getAllStockCodes();
        FactorJudgmentVO vo;
        for (int i = 0; i < 5; i++) {
            vo = service.getStocksFactorJudgment(codes.subList(i*20 , i*20 + 19) , MyDate.getDateFromString("2014-09-24") , MyDate.getDateFromString("2015-8-3") , "000001");
            System.out.println("***** Now is test for stocks from " + i*20 + " to " + (i*20 + 19)  +" *****");
            System.out.println("avg profit "+vo.sortAvgProfit);
            System.out.println("sortRankIC "+vo.sortRankIC);
            System.out.println("RankIR "+vo.sortRankIR);
            System.out.println("RankWinRate "+vo.sortRankWinRate);
            System.out.println("explanation "+vo.explanation);
            System.out.println();
        }



    }

    @Test
    public void analyseWithFactor() throws Exception {

    }

}