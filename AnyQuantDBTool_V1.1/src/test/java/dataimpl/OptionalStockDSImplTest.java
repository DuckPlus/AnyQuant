package dataimpl;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import module.po.StockPO;
import dataservice.OptionalStockDataService;
import dataservice.impl.OptionalStockDSImpl;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.fail;

/**
 *
 * @author ss
 * @date 2016年4月15日
 */
public class OptionalStockDSImplTest {
	OptionalStockDataService dataservice;

	@Before
	public void setUp() throws Exception {
		dataservice = OptionalStockDSImpl.getOptionalStockDSImpl();
	}

	@After
	public void tearDown() throws Exception {
		dataservice = null;
	}

	@Test
	public void testGetSelectedStockCodes() {
		List<String> codes = dataservice.getSelectedStockCodes();
		if (codes == null) {
			fail(" testGetSelectedStockCodes()--null");
		}
	}

	@Test
	public void testGetOptionalStocks() {
		Iterator<StockPO> poIterator = dataservice.getOptionalStocks();
		if (poIterator == null) {
			fail("testGetOptionalStocks()--null");
		} else if (!poIterator.hasNext()) {
			fail("testGetOptionalStocks()--size");
		}
	}

	@Test
	public void testDeleteOptionalStock() {
		String codes[] = { "sh600023", "sh600021", "sh50012" };
		if (!dataservice.deleteOptionalStock(codes[0])) {
			fail("testDeleteOptionalStock()--can't delete code t" + "hat does exist!");
		} else {
			if (dataservice.deleteOptionalStock(codes[0])) {
				fail("testDeleteOptionalStock()--try to delete code that doesn't exist!");
			}
		}

		dataservice.addOptionalStock(codes[0]);

		if (dataservice.deleteOptionalStock(codes[2])) {
			fail("testDeleteOptionalStock()-- try to delete code that doesn't exist!");
		}
	}

	@Test
	public void testAddOptionalStock() {
		String code = "sz002592";
		dataservice.addOptionalStock(code);
		List<String> codes = dataservice.getSelectedStockCodes();
		if (!codes.contains(code)) {
			fail("testAddOptionalStock()--fail to add stock");
		} else {
			dataservice.deleteOptionalStock(code);
		}

	}

	@Test
	public void testClearOptionalStocks() {

		dataservice.clearOptionalStocks();
		List<String> codes = dataservice.getSelectedStockCodes();
		if (codes == null) {
			fail("testClearOptionalStocks()--null");
		} else if (codes.size() != 0) {
			fail("testClearOptionalStocks()--size");
		}

		String oldCodes[] = { "sh600012", "sh600019", "sh600028", "sh600048", "sh600007", "sh600018", "sh600026",
				"sh600016", "sh600021", "sh600023" };
		for(String code:oldCodes){
			dataservice.addOptionalStock(code);
		}
	}

}
