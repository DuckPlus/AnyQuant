package service;

import net.sf.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vo.BoardDistributionVO;
import vo.CompareBoardAndBenchVO;

import java.util.List;

/**
 * @author Qiang
 * @date 16/5/22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
        (locations = {"classpath:/META-INF/applicationContext.xml","classpath:/META-INF/infrastructure.xml"})
public class BoardAnalysisServiceTest {
    @Test
    public void setUp1() throws Exception {

    }

    @Test
    public void getAllBoardName1() throws Exception {

    }

    @Test
    public void getBoardAndBenchChartData2() throws Exception {
        JSONArray array = service.getAllBoardAndStockData();
        for (int i = 0; i < array.size(); i++) {
            System.out.println(array.get(i));
        }
    }

    @Test
    public void getBoardAndBenchChartData11() throws Exception {

    }

    @Test
    public void getBoardDistributionChartData1() throws Exception {

    }

    @Autowired
    BoardAnalysisService service;
    @Before
    public void setUp() throws Exception {

    }
    @Test
    public void getAllBoardName() throws  Exception{
        List<String> names = service.getAllBoradName();
        for (String name : names){
            System.out.println(name);
        }
    }

    @Test
    public void getBoardAndBenchChartData() throws Exception {
        List<CompareBoardAndBenchVO> compareBoardAndBenchVOs = service.getBoardAndBenchChartData("软饮料" , 30);
        for (CompareBoardAndBenchVO compareBoardAndBenchVO : compareBoardAndBenchVOs) {
            System.out.println(compareBoardAndBenchVO.boardName + compareBoardAndBenchVO.date.DateToString() + compareBoardAndBenchVO.boardData +"  " +  compareBoardAndBenchVO.huShen300Data);

        }
    }

    @Test
    public void getBoardAndBenchChartData1() throws Exception {
        //no need to test
    }

    @Test
    public void getBoardDistributionChartData() throws Exception {
        List<BoardDistributionVO> vos = service.getBoardDistributionChartData("汽车零部件");
        for (BoardDistributionVO vo : vos){
            System.out.println(vo.stockName + " " + vo.weight + " " + vo.turnoverVol);
        }
    }

}