package DAO.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import entity.StockdataEntity;
import DAO.StockDataDAO;
import util.MyDate;


import static org.junit.Assert.*;

/**
 * Created by 67534 on 2016/5/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
        (locations = {"classpath:/META-INF/applicationContext.xml","classpath:/META-INF/infrastructure.xml"})


public class StockDataDAOImplTest {
    @Autowired
    private StockDataDAO dao;


    @Test
    public void getStockData() throws Exception {

    }

    @Test
    public void getStockData1() throws Exception {
        String code  = "sh600216";
        MyDate myDate = new MyDate (2016,3,3);
        StockdataEntity entity =dao.getStockData(code,myDate);
    //    System.out.println("code :"+entity.getCode());
    //    System.out.print("date :"+entity.getDate().toString());
        assertEquals("sh600216",entity.getCode());
        assertEquals("2016-03-03",entity.getDate().toString());
    }

    @Test
    public void getStockData2() throws Exception {

    }

    @Test
    public void getAllStockData() throws Exception {

    }

}