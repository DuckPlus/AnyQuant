package DAOImpl;


import DAO.StockDataDAO;
import entity.StockdataEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.MyDate;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

/**
 * Created by 67534 on 2016/5/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
        (locations = {"classpath:/META-INF/applicationContext.xml"})
public class StockDataDAOImplTest {
    @Autowired
    private StockDataDAO dao;

    @Test
    public void getMaxDate() throws Exception {
        MyDate myDate = dao.getMaxDate();
        System.out.print("stockdata maxDate :"+myDate.DateToString());
    }

    @Test
    public void getStockData() throws Exception {

        String code  = "sh600005";
        String date = "2016-05-03";
        StockdataEntity entity =dao.getStockData(code);
        System.out.println("code :"+entity.getCode());
        System.out.print("date :"+entity.getDate().toString());
        assertEquals(code,entity.getCode());
    //    assertEquals(date,entity.getDate().toString());

    }

    @Test
    public void getStockData1() throws Exception {
        String code  = "sh600216";
        MyDate myDate = new MyDate(2016,3,3);
        StockdataEntity entity =dao.getStockData(code,myDate);
        System.out.println("code :"+entity.getCode());
        System.out.print("date :"+entity.getDate().toString());
        assertEquals("sh600216",entity.getCode());
        assertEquals("2016-03-03",entity.getDate().toString());
    }

    @Test
    public void getStockData2() throws Exception {
        String code  = "sh600216";
        MyDate start = new MyDate(2016,3,1);
        MyDate end = new MyDate(2016,3,20);
        ArrayList<StockdataEntity> list = ( ArrayList<StockdataEntity>)dao.getStockData(code,start,end);
        System.out.println("size :"+list.size());
        System.out.println("code :"+list.get(0).getCode());
        System.out.print("date :"+list.get(0).getDate().toString());
        assertEquals("sh600216",list.get(0).getCode());
     //   assertEquals(start.DateToString(),list.get(0).getDate().toString());

    }

    @Test
    public void getAllStockData() throws Exception {
        ArrayList<StockdataEntity> list=
        ( ArrayList<StockdataEntity>)dao.getAllStockData();
        System.out.println("size :"+list.size());
        for(int i=0;i<10;i++){
            System.out.println("code: "+list.get(i).getCode()
            +" date: "+list.get(i).getDate().toString());
        }
    }

}