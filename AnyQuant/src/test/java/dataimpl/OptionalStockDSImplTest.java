package dataimpl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.python.antlr.op.Add;

import data.OptionalStockDSImpl;
import dataservice.OptionalStockDataService;

/**
 *
 * @author ss
 * @date 2016年4月15日
 */
public class OptionalStockDSImplTest {
    OptionalStockDataService dataservice ;
	@Before
	public void setUp() throws Exception {
		dataservice = OptionalStockDSImpl.getOptionalStockDSImpl();
	}

	@After
	public void tearDown() throws Exception {
		dataservice=null;
	}

	@Test
	public void testDeleteOptionalStock() {
		String codes [] = {"sh600023","sh600021", "sh50012"};
		if(!dataservice.deleteOptionalStock(codes[0])){
			 fail("testDeleteOptionalStock()--can't delete code that does exist!");
		}else{
			if(dataservice.deleteOptionalStock(codes[0])){
				 fail("testDeleteOptionalStock()--try to delete code that doesn't exist!");
			}
		}

		dataservice.addOptionalStock(codes[0]);

		if(dataservice.deleteOptionalStock(codes[2])){
			 fail("testDeleteOptionalStock()-- try to delete code that doesn't exist!");
		}
	}

	@Test
	public void testAddOptionalStock() {
		fail("Not yet implemented");
	}

	@Test
	public void testClearOptionalStocks() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSelectedStockCodes() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOptionalStocks() {
		fail("Not yet implemented");
	}

}
