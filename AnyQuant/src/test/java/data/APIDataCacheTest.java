package data;

import org.junit.Before;
import org.junit.Test;

import blimpl.BusinessFactory;
import blimpl.StockBLImpl;
import dataservice.APIDataFactory;
import enumeration.MyDate;
import util.MyTime;
import vo.OHLC_VO;

import static org.junit.Assert.*;

import java.util.List;

/**
 * Created by Qiang on 3/27/16.
 */
public class APIDataCacheTest {


    @Before
    public void setUp() throws Exception {
    	APIDataCache apiDataCache = (APIDataCache) APIDataFactory.getAPIDataService();
    }

    @Test
    public void getAllStocks() throws Exception {

    }

    @Test
    public void getAllStocks1() throws Exception {

    }

    @Test
    public void getAllStocks2() throws Exception {

    }

    @Test
    public void getAllStocks3() throws Exception {

    }

    @Test
    public void getStockMes() throws Exception {

    }

    @Test
    public void getStockMes1() throws Exception {

    }

    @Test
    public void getAllStockMes() throws Exception {

    }

    @Test
    public void getAllBenchMarks() throws Exception {

    }

    @Test
    public void getBenchMes() throws Exception {

    }

    @Test
    public void getBenchMes1() throws Exception {

    }

    @Test
    public void getAllBenchMes() throws Exception {

    }

    @Test
    public void getOptionalStocks() throws Exception {

    }

    @Test
    public void deleteOptionalStock() throws Exception {

    }

    @Test
    public void addOptionalStock() throws Exception {

    }
}