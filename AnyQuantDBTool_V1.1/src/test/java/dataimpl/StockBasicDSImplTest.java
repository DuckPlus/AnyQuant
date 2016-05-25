package dataimpl;


import dataservice.StockBasicService;
import dataservice.impl.StockBasicDSImpl;
import module.po.StockBasicInfo;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Created by 67534 on 2016/5/18.
 */
public class StockBasicDSImplTest {
    StockBasicService ds = StockBasicDSImpl.getStockBasicDSImpl();

    @Test
    public void getStockBasicInfo() throws Exception {
     String code="sz002094";
        StockBasicInfo po = ds.getStockBasicInfo(code);
        if(po==null){
            fail("null----");
        }else{
            System.out.println("code: "+po.code);
            System.out.println("totalShares: "+po.totalShares);
            System.out.println("listCode: "+po.listDate);
            System.out.println("primeOperating: "+po.primeOperating);
            System.out.println("officeAddr: "+po.officeAddr);
        }

    }

}