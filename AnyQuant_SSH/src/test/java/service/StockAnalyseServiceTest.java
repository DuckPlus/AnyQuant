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
import util.enumration.AnalysisFactor;
import util.enumration.FactorJudge;
import vo.EvaluationVO;
import vo.FactorWeightVO;
import vo.Factor_VO;
import vo.NewsVO;

import java.util.Arrays;
import java.util.List;

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
        List<NewsVO> vos = stockAnalyseService.getRelatedNewsVO("sh600004");
        for (NewsVO vo : vos) {
            System.out.println(vo.newsID + vo.summary);
        }
    }

    @Test
    public void getEvaluation() throws Exception {


        for (int i = 10; i < 90; i++) {
            try {
                EvaluationVO vo = stockAnalyseService.getEvaluation("sh6000" + i);
                System.out.println(Arrays.toString(vo.analysis.toArray()));
//                System.out.println(vo.code);
                System.out.println(vo.mark + " "  +vo.suggestion);
            }catch (Exception e){
            }


        }



//        EvaluationVO vo = stockAnalyseService.getEvaluation("sh600004");
//        System.out.println(Arrays.toString(vo.analysis.toArray()));
//        System.out.println(vo.code);
//        System.out.println(vo.mark + " "  +vo.suggestion);
    }

    @Test
    public void getBoardRelatedStockMessage() throws Exception {
        List<StockdataEntity> entities = stockAnalyseService.getBoardRelatedStockMessage("sh600004");
        System.out.println(entities.size());
        for (StockdataEntity entity : entities){
            System.out.println(entity.getName());
        }
    }

    @Test
    public void getRegionRelatedStockMessage() throws Exception {
        List<StockdataEntity> entities = stockAnalyseService.getRegionRelatedStockMessage("sh600004");
        System.out.println(entities.size());
        for (StockdataEntity entity : entities){
            System.out.println(entity.getName());
        }
    }

    @Test
    public void getFactorVO() throws Exception {
        List<Factor_VO> factor_vos = stockAnalyseService.getFactorVO("sh600004" , AnalysisFactor.MA5 , 10);
        for(Factor_VO vo : factor_vos){
            System.out.println(vo.name + vo.date.DateToString() + vo.value);
        }
    }

    @Test
    public void getMostUsefulFactors() throws Exception {
        List<FactorWeightVO> factorWeightVOs = stockAnalyseService.getMostUsefulFactors("sh600380" ,70 , FactorJudge.IC );
        for (FactorWeightVO vo : factorWeightVOs){
            System.out.println(vo.judgeFactorValue + " " + vo.name);
        }
        factorWeightVOs = stockAnalyseService.getMostUsefulFactors("sh600380" , 70 , FactorJudge.IR );
        for (FactorWeightVO vo : factorWeightVOs){
            System.out.println(vo.judgeFactorValue + " " + vo.name);
        }
    }

}