package dataimpl;


import dataservice.StockDataService;
import dataservice.impl.StockDSImpl;
import module.po.StockPO;
import module.po.TimeSharingPO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.DateCalculator;
import util.MyDate;
import util.enumeration.Exchange;
import util.helper.FileIOHelper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

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
		MyDate date = DateCalculator.getAnotherDay(-1);
		String code = "sh600216";
		String nameString = "浙江医药";
		String dateString = date.DateToString();

		StockPO po  =  stockDataService.getStockMes(code,date);

		if(po==null){
			fail("testGetStockMesStringMyDate() --NULL");
		}else if(!po.getCode().equals(code)){
			fail("testGetStockMesStringMyDate() --code");
		}else if(!po.getName().equals(nameString)){
			fail("testGetStockMesStringMyDate() --name");
		}else if(!po.getDate().equals(dateString)){
			fail("testGetStockMesStringMyDate() --date");
		}

	}

	@Test
	public void testGetStockMesStringMyDateMyDate() {
		MyDate endDate = DateCalculator.getToDay();
		MyDate startDate = DateCalculator.getAnotherDay(-30);
		String code = "sh600216";
		String nameString = "浙江医药";
		String dateString = startDate.DateToString();

		List<StockPO>  pos  =  stockDataService.getStockMes(code,startDate,endDate);

		if(pos==null){
			fail("testGetStockMesStringMyDateMyDate() --NULL");
		}else if(!pos.get(0).getCode().equals(code)){
			fail("testGetStockMesStringMyDateMyDate()--code");
		}else if(!pos.get(0).getName().equals(nameString)){
			fail("testGetStockMesStringMyDateMyDate() --name");
		}else if(!pos.get(0).getDate().equals(dateString)){
			fail("testGetStockMesStringMyDateMyDate() --date");
		}
	}

	@Test
	public void testGetAllStockMes() {
		List<StockPO>  pos  =  stockDataService.getAllStockMes();
		if(pos==null){
			fail("testGetAllStockMes() --NULL");
		}

	}

	@Test
	public void testGetTimeSharingPOs() {
		MyDate today = DateCalculator.getToDay();
		String code = "sh600216";
		String dateString = today.DateToString();
		List<TimeSharingPO>  pos  =  stockDataService.getTimeSharingPOs(code);
		if(pos==null){
			fail("testGetTimeSharingPOs() --NULL");
		}else if(!pos.get(0).nowTime.DateToString().equals(dateString)){
			fail("testGetTimeSharingPOs()  --date");
		}
	}

	@Test
	public void testUpdateAllMes() {
//		MyDate yestoday = DateCalculator.getAnotherDay(-1);
//		String dateString = yestoday.DateToString();
//		stockDataService.updateAllMes();
//
//		List<StockPO>  pos  =  stockDataService.getAllStockMes();
//
//		if(pos==null){
//			fail("testUpdateAllMes()--NULL");
//		}else if(!pos.get(0).getDate().equals(dateString)){
//			fail("testUpdateAllMes()--date");
//		}

	}
	@Test
	public void updateAllStockMesFromDate(){
		MyDate start = DateCalculator.getAnotherDay(-5);
		MyDate end = DateCalculator.getToDay();
		String [] codes = {"sh600216","sh600067"};
		ArrayList<StockPO> content = new ArrayList<>();
		for( String code : codes){
			List<StockPO> temp =  stockDataService.getStockMes(code,start,end);
			content.addAll(temp);
		}
		FileIOHelper.writeAllStockMes(content);

	}

}
