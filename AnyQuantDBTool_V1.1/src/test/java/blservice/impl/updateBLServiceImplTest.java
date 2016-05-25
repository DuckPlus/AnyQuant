package blservice.impl;

import blservice.updateBLService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by 67534 on 2016/5/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
        (locations = {"classpath:/META-INF/applicationContext.xml"})

public class updateBLServiceImplTest {
    @Autowired
    updateBLService service;

    @Test
    public void updateStockBasicInfo() throws Exception {
          //  service.updateStockBasicInfo();

    }



    @Test
    public void updateFactor() throws Exception {
        service.updateFactor();
    }


    @Test
    public void updateStockdataEntities() throws Exception {
           service.updateStockdataEntities();
    }

    @Test
    public void updatBenchdataEntities() throws Exception {
          //  service.updatBenchdataEntities();
    }

    @Test
    public void initFactorTable() throws Exception {
        //  service.initFactorTable();
    }

}