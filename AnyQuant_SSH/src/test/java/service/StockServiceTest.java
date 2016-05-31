package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Qiang
 * @date 16/5/23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
        (locations = {"classpath:/META-INF/applicationContext.xml","classpath:/META-INF/infrastructure.xml"})
public class StockServiceTest {

    @Autowired
    StockService service;
    @Test
    public void getDayOHLC_Data() throws Exception {

    }

    @Test
    public void getWeekOHLC_Data() throws Exception {

    }

    @Test
    public void getMonthOHLC_Data() throws Exception {

    }

    @Test
    public void getSharingVOs() throws Exception {

    }

    @Test
    public void getDayDealVOs() throws Exception {

    }

    @Test
    public void getWeekDealVOs() throws Exception {

    }

    @Test
    public void getMonthDealVOs() throws Exception {

    }

    @Test
    public void getRecentStocks() throws Exception {

    }

    @Test
    public void getAllStocks() throws Exception {

    }

    @Test
    public void getStockDescription() throws Exception {

    }

    @Test
    public void getTodayAllStockData() throws Exception {

    }

    @Test
    public void getTodayStockVO() throws Exception {

    }

    @Test
    public void getStocksByTime() throws Exception {

    }

}