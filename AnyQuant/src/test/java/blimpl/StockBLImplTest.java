package blimpl;

import blservice.StockBLService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Qiang on 3/27/16.
 */
public class StockBLImplTest {
    StockBLService bl;
    @Before
    public void setUp() throws Exception {
        bl = BusinessFactory.getStockBLService();
    }

    @Test
    public void getAllStocks() throws Exception {
        // test in other method
    }

    @Test
    public void getSortStocks() throws Exception {
        getSortStocks();;
    }

    @Test
    public void getSortStocksInScope() throws Exception {

    }

    @Test
    public void getRecentStocks() throws Exception {

    }

    @Test
    public void getTodayStockVO() throws Exception {

    }

    @Test
    public void getStocksByTime() throws Exception {

    }

    @Test
    public void getStocksByStockCode() throws Exception {

    }

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
    public void getOptionalStocks() throws Exception {

    }

    @Test
    public void addStockCode() throws Exception {

    }

    @Test
    public void deleteStockCode() throws Exception {

    }

    @Test
    public void deleteStockCode1() throws Exception {

    }

    @Test
    public void addStockCode1() throws Exception {

    }
}