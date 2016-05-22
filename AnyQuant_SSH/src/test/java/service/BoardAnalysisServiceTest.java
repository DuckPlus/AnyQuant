package service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vo.CompareBoardAndBenchVO;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Qiang
 * @date 16/5/22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
        (locations = {"classpath:/META-INF/applicationContext.xml","classpath:/META-INF/infrastructure.xml"})
public class BoardAnalysisServiceTest {
    @Autowired
    BoardAnalysisService service;
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void getBoardAndBenchChartData() throws Exception {
        List<CompareBoardAndBenchVO> compareBoardAndBenchVOs = service.getBoardAndBenchChartData("汽车零部件" , 30);
        for (int i = 0; i < compareBoardAndBenchVOs.size(); i++) {
            System.out.println(compareBoardAndBenchVOs.get(i).boardName + compareBoardAndBenchVOs.get(i).date.DateToString() + compareBoardAndBenchVOs.get(i).boardData + compareBoardAndBenchVOs.get(i).huShen300Data);

        }
    }

    @Test
    public void getBoardAndBenchChartData1() throws Exception {

    }

    @Test
    public void getBoardDistributionChartData() throws Exception {

    }

}