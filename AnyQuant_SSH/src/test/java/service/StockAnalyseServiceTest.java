package service;

import entity.StockEntity;
import entity.StockdataEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.DateCalculator;
import util.enumration.AnalysisFactor;
import util.enumration.FactorJudge;
import vo.FactorWeightVO;
import vo.Factor_VO;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Qiang
 * @date 16/5/22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
        (locations = {"classpath:/META-INF/applicationContext.xml","classpath:/META-INF/infrastructure.xml"})
public class StockAnalyseServiceTest {
    @Autowired
    StockAnalyseService stockAnalyseService;


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getStockFullMessage() throws Exception {
       StockEntity entity =  stockAnalyseService.getStockFullMessage("sh600004");
        System.out.println(entity.getName());
    }

    @Test
    public void getRelatedNewsVO() throws Exception {
        //no need to test
    }

    @Test
    public void getEvaluation() throws Exception {
        //TODO
    }

    @Test
    public void getBoardRelatedStockMessage() throws Exception {
        List<StockdataEntity> entities = stockAnalyseService.getBoardRelatedStockMessage("sh600004");
        for (StockdataEntity entity : entities){
            System.out.println(entity.getName());
        }
    }

    @Test
    public void getRegionRelatedStockMessage() throws Exception {
        List<StockdataEntity> entities = stockAnalyseService.getRegionRelatedStockMessage("sh600004");
        for (StockdataEntity entity : entities){
            System.out.println(entity.getName());
        }
    }

    @Test
    public void getFactorVO() throws Exception {
        List<Factor_VO> factor_vos = stockAnalyseService.getFactorVO("sh600004" , AnalysisFactor.MA5 , 30);
        for(Factor_VO vo : factor_vos){
            System.out.println(vo.name + vo.date.DateToString() + vo.value);
        }
    }

    @Test
    public void getMostUsefulFactors() throws Exception {
        List<FactorWeightVO> factorWeightVOs = stockAnalyseService.getMostUsefulFactors("sh600004" , 30 , FactorJudge.IC );
        for (FactorWeightVO vo : factorWeightVOs){
            System.out.println(vo.judgeFactorValue + " " + vo.name);
        }
    }

}