package dataimpl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.StockDSImpl;
import dataservice.StockDataService;
import enumeration.Exchange;
import po.StockPO;

/**
 *
 * @author ss
 * @date 2016年4月15日
 */
public class StockDSImplTest {

	StockDataService stockDataService;

	@Before
	public void setUp() throws Exception {
		stockDataService = StockDSImpl.getStockDSImpl();
	}

	@After
	public void tearDown() throws Exception {
		stockDataService = null;
	}

	@Test
	public void testGetAllStocks() {
		List<String> codeStrings = stockDataService.getAllStocks();
		if (codeStrings == null) {
			fail("testGetAllStocks()--null");
		} else if (codeStrings.size() < 10) {
			fail("testGetAllStocks()--size");
		}
	}

	@Test
	public void testGetAllStocksInt() {
		for (int i = 2010; i < 2017; i++) {
			List<String> codeStrings = stockDataService.getAllStocks(i);
			if (codeStrings == null) {
				fail("testGetAllStocks()--null");
			} else if (codeStrings.size() < 10) {
				fail("testGetAllStocks()--size");
			}
		}
	}

	@Test
	public void testGetAllStocksExchange() {
		List<String> codes = stockDataService.getAllStocks(Exchange.sh);
		if (codes == null) {
			fail("testGetAllStocks()--null");
		} else if (codes.size() < 10) {
			fail("testGetAllStocks()--size");
		}

		List<String> codeStrings = stockDataService.getAllStocks(Exchange.sz);
		if (codeStrings == null) {
			fail("testGetAllStocks()--null");
		} else if (codeStrings.size() < 10) {
			fail("testGetAllStocks()--size");
		}

	}

	@Test
	public void testGetAllStocksIntExchange() {
		List<String> codes;

		for (int i = 2015; i < 2018; i++) {
			for (int j = 0; j < 2; j++) {

				if (j % 2 == 0) {
					codes = stockDataService.getAllStocks(Exchange.sh);
				} else {
					codes = stockDataService.getAllStocks(Exchange.sz);
				}

				if (codes == null) {
					fail("testGetAllStocks()--null");
				} else if (codes.size() < 10) {
					fail("testGetAllStocks()--size");
				}
			}
		}

	}

	@Test
	public void testGetStockMesString() {
		String [] codes = {"sh600000","sh600216","sz002588","sz002310","sh00012","sh5000001"};
		List<String> errorCode = new ArrayList<String>();
		List<StockPO>  stockPOs = new ArrayList<>();
		for(int i=0; i<codes.length;i++){
			StockPO stockpo =  stockDataService.getStockMes(codes[i]);
			if(stockpo==null){
				errorCode.add(codes[i]);
			}else{
				stockPOs.add(stockpo);
			}
		}

		if(!errorCode.get(0).equals(codes[4])||!errorCode.get(1).equals(codes[5])){
			fail("testGetStockMesString()---errorCodes can't be properly handled");
		}else if( !stockPOs.get(0).getCode().equals(codes[0])){
			fail("testGetStockMesString()---stockpo code changed");
		}



	}

	@Test
	public void testGetStockMesStringMyDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStockMesStringMyDateMyDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllStockMes() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTimeSharingPOs() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateAllMes() {
		fail("Not yet implemented");
	}

}
