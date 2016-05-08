package DAO.impl;

import entity.StockdataEntity;
import DAO.StockDataDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MyDate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * Created by 67534 on 2016/5/8.
 */
public class StockDataDAOImplTest {

    private StockDataDAO dao;
    @Before
    public void setUp() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext
                ("META-INF/applicationContext.xml");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getStockData() throws Exception {

    }

    @Test
    public void getStockData1() throws Exception {
        String code  = "sh600126";
        MyDate myDate = new MyDate (2016,3,3);
        StockdataEntity entity =dao.getStockData(code,myDate);
        System.out.println("code :"+entity.getCode());
        System.out.print("date :"+entity.getDate().toString());
        assertEquals("sh600216",entity.getCode());
        assertEquals("2016-3-3",entity.getDate().toString());
    }

    @Test
    public void getStockData2() throws Exception {

    }

    @Test
    public void getAllStockData() throws Exception {

    }

}