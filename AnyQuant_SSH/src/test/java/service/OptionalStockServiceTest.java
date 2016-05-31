package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * @author Qiang
 * @date 16/5/23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
        (locations = {"classpath:/META-INF/applicationContext.xml","classpath:/META-INF/infrastructure.xml"})
public class OptionalStockServiceTest {
    @Autowired
    OptionalStockService service;

    @Test
    public void getOptionalStocksDatas() throws Exception {

    }

    @Test
    public void getOptionalStocks() throws Exception {

    }

    @Test
    public void deleteStockCode() throws Exception {

    }

    @Test
    public void deleteStockCode1() throws Exception {

    }

    @Test
    public void addStockCode() throws Exception {

    }

    @Test
    public void addStockCode1() throws Exception {

    }

    @Test
    public void clearOptionalStocks() throws Exception {

    }

    @Test
    public void ifStockExist() throws Exception {

    }

    @Test
    public void getRegionDistributionMap() throws Exception {
        Map<String , Integer> re = service.getRegionDistributionMap("1");
        for (Map.Entry<String , Integer> entry : re.entrySet()){

            System.out.println(entry.getKey() + " " + entry.getValue());

        }

    }

    @Test
    public void getBoardDistributionMap() throws Exception {
        Map<String , Integer> re = service.getBoardDistributionMap("1");
        for (Map.Entry<String , Integer> entry : re.entrySet()){

            System.out.println(entry.getKey() + " " + entry.getValue());

        }
    }

}