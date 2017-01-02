package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @author Qiang
 * @since 02/01/2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
        (locations = {"classpath:/META-INF/applicationContext.xml","classpath:/META-INF/infrastructure.xml"})
public class UpdateBLServiceTest {

    @Autowired
    UpdateBLService blService;

    @Test
    public void update() throws Exception {
        blService.update();
    }

    @Test
    public void initFactorTable() throws Exception {

    }

    @Test
    public void updateStockBasicInfo() throws Exception {

    }

    @Test
    public void updateFactor() throws Exception {

    }

    @Test
    public void updateStockdataEntities() throws Exception {

    }

    @Test
    public void updatBenchdataEntities() throws Exception {

    }

}