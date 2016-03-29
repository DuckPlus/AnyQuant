package blimpl;

import blservice.OptionalStockBLService;
import org.junit.Before;
import org.junit.Test;
import vo.StockVO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 *
 * @author Qiang
 * @date 3/29/16.
 */
public class OptionalStockBLServiceImplTest {
    OptionalStockBLService bl;
    List<StockVO> vos = new ArrayList<>();
    boolean result;
    @Before
    public void setUp() throws Exception {
        bl = BusinessFactory.getOptionalBLService();
        getOptionalStocks();
    }

    @Test
    public void getOptionalStocks() throws Exception {
        Iterator<StockVO> temp = bl.getOptionalStocks();
        while (temp.hasNext()){
            vos.add(temp.next());
        }

    }

    @Test
    public void addStockCode() throws Exception {
        result = bl.addStockCode("600000");

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