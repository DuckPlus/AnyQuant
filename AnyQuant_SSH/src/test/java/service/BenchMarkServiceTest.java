package service;

import entity.BenchmarkEntity;
import entity.BenchmarkdataEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.DateCalculator;

import java.util.List;

/**
 * @author Qiang
 * @date 16/5/23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
        (locations = {"classpath:/META-INF/applicationContext.xml","classpath:/META-INF/infrastructure.xml"})
public class BenchMarkServiceTest {

    @Autowired
    BenchMarkService service;


    @Before
    public void setUp() throws Exception {

    }



    @Test
    public void getAllBenchMarksList() throws Exception {
       List<BenchmarkEntity> entities =  service.getAllBenchMarksList();
        for (BenchmarkEntity entity : entities){
            System.out.println(entity.getCode() + " " + entity.getName());
        }
    }

    @Test
    public void getAllBenchMarksDataList() throws Exception {
        List<BenchmarkdataEntity> entities = service.getAllBenchMarksDataList();
        for (BenchmarkdataEntity entity : entities){
            System.out.println(entity.getName() +  " " + entity.getClose());
        }
    }

    @Test
    public void getRecentBenchMarks() throws Exception {
        List<BenchmarkEntity> entities =  service.getAllBenchMarksList();

        for (BenchmarkEntity entity : entities){
            List<BenchmarkdataEntity> dataentities = service.getRecentBenchMarks(entity.getCode());
            for (BenchmarkdataEntity dataentitiy : dataentities){
                System.out.println(dataentitiy.getName() +  " " + dataentitiy.getClose());
            }
        }

    }

    @Test
    public void getBenchMarkByTime() throws Exception {
        List<BenchmarkEntity> entities =  service.getAllBenchMarksList();

        for (BenchmarkEntity entity : entities){
            List<BenchmarkdataEntity> dataentities = service.getBenchMarkByTime(entity.getCode() , DateCalculator.getAnotherDay(-30) , DateCalculator.getToDay());
            for (BenchmarkdataEntity dataentitiy : dataentities){
                System.out.println(dataentitiy.getDate().toString() + " " + dataentitiy.getName() +  " " + dataentitiy.getClose());
            }
        }
    }

}