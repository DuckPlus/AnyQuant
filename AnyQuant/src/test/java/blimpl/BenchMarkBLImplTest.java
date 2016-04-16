package blimpl;

import blservice.BenchMarkBLService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Qiang
 * @date 4/15/16
 */
public class BenchMarkBLImplTest {
    private BenchMarkBLService bl;
    @Before
    public void setUp() throws Exception {
        bl = BusinessFactory.getBenchMarkBLService();
    }

    @Test
    public void getBenchMarkBLService() throws Exception {
        // no need to test
    }

    @Test
    public void getAllBenchMarks() throws Exception {
       // no need to test

    }

    @Test
    public void getRecentBenchMarks() throws Exception {
        // no need to test
    }

    @Test
    public void getBenchMarkByTime() throws Exception {
        //  no need to test
    }

}