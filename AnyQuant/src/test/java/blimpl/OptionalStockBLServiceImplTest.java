package blimpl;

import blservice.OptionalStockBLService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import vo.StockVO;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * @author Qiang
 * @date 3/31/16.
 */
public class OptionalStockBLServiceImplTest {
    OptionalStockBLService bl;
    Iterator<StockVO> stocksForSave;

    @Before
    public void setUp() throws Exception {
        bl = BusinessFactory.getOptionalBLService();
        stocksForSave = bl.getOptionalStocks();
    }

    /**
     * not change the data after the test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        bl.clearOptionalStocks();
        while (stocksForSave.hasNext()){
            bl.addStockCode(stocksForSave.next().code);
        }
    }

    @Test
    public void getOptionalStocks() throws Exception {
        //No need to test
    }

    @Test
    public void addStockCode() throws Exception {
        bl.clearOptionalStocks();
        if(bl.addStockCode("sh600000") && !bl.addStockCode("sh600000") ){
            //pass
        }else {
            fail("Allow To add one stock twice or can not add a stock");
        }
    }

    @Test
    public void deleteStockCode() throws Exception {
        bl.clearOptionalStocks();
        bl.addStockCode("sh600000");
        if(bl.deleteStockCode("sh600000") && !bl.deleteStockCode("sh600001")){
            Iterator<StockVO> tmp = bl.getOptionalStocks();
            if(tmp.hasNext()){
                fail("can not del a stock");
            }
        }else{
            fail("Allow To del one stock twice or can not del a stock");
        }
    }

    @Test
    public void deleteStockCode1() throws Exception {

    }

    @Test
    public void addStockCode1() throws Exception {

    }

    @Test
    public void clearOptionalStocks() throws Exception {

    }
}