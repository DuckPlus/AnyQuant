package DAO.impl;

import entity.StockEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.fail;

/**
 * Created by 67534 on 2016/5/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
        (locations = {"classpath:/META-INF/applicationContext.xml","classpath:/META-INF/infrastructure.xml"})

public class StockDAOImplTest {
    @Autowired
    StockDAOImpl ds;
    @Test
    public void findAllStocks() throws Exception {

    }

    @Test
    public void getStockEntity() throws Exception {
          String code="sh603306";
          StockEntity temp=ds.getStockEntity(code);
          if(temp==null){
              fail("null");
          }else{
              System.out.println("code: "+temp.getCode());
          }
    }

}