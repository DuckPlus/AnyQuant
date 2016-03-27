package data;

import org.junit.Before;
import org.junit.Test;

import dataservice.APIDataFactory;
import enumeration.MyDate;

import static org.junit.Assert.*;

/**
 * Created by Qiang on 3/27/16.
 */
public class APIDataCacheTest {


	public static void main(String[] args) {
		 APIDataCache cache = (APIDataCache) APIDataFactory.getAPIDataService();
		// cache.downloadStockInfo("sh600216");
		 cache.downloadAllStocks();
	}

    @Before
    public void setUp() throws Exception {

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
    public void dealOptionalStock() throws Exception {

    }

    @Test
    public void addOptionalStock() throws Exception {

    }
}