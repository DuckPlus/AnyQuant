package DAO.impl;


import DAO.StockDataDAO;
import entity.StockdataEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.DateCalculator;
import util.MyDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.fail;


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
        MyDate myDate = new MyDate (2016,3,3);
        StockdataEntity entity =dao.getStockData(code,myDate);
        System.out.println("code :"+entity.getCode());
        System.out.print("date :"+entity.getDate().toString());
        assertEquals("sh600216",entity.getCode());
        assertEquals("2016-03-03",entity.getDate().toString());
    }

    @Test
    public void getStockData2() throws Exception {
        String code  = "sh600216";
        MyDate start = new MyDate (2016,3,1);
        MyDate end = new MyDate (2016,3,20);
        ArrayList<StockdataEntity> list = ( ArrayList<StockdataEntity>)dao.getStockData(code,start,end);
        System.out.println("size :"+list.size());
        System.out.println("code :"+list.get(0).getCode());
        System.out.print("date :"+list.get(0).getDate().toString());
        assertEquals("sh600216",list.get(0).getCode());
     //   assertEquals(start.DateToString(),list.get(0).getDate().toString());

    }


    @Test
    public void getStockData3() throws Exception {
        String [] codes  = {"sh603306","sh600137","sh600858"};
        MyDate date = new MyDate (2016,3,1);

        ArrayList<StockdataEntity> list =
                ( ArrayList<StockdataEntity>)
                        dao.getStockData(Arrays.asList(codes),date);

        assertEquals("sh603306",list.get(0).getCode());
        assertEquals(date.DateToString(),list.get(0).getDate().toString());

        System.out.println("size :"+list.size());
        for(int i=0;i<list.size();i++){
            System.out.print("code :"+list.get(i).getCode()+" ");
            System.out.println("date :"+list.get(i).getDate().toString());
        }



    }

    @Test
    public void getStockData4() throws Exception {
        String [] codes  = {"sh603306","sh600137","sh600858"};
        MyDate start = new MyDate (2015,3,15);
        MyDate end = new MyDate (2016,3,20);

        ArrayList<StockdataEntity> list =
                ( ArrayList<StockdataEntity>)
                        dao.getStockData(Arrays.asList(codes),start,end);

        assertEquals("sh603306",list.get(0).getCode());

        System.out.println("size :"+list.size());
        for(int i=0;i<list.size();i++){
            System.out.print("code :"+list.get(i).getCode()+" ");
            System.out.println("date :"+list.get(i).getDate().toString());
        }
    }


    @Test
    public void getStockDataByList()throws Exception{
        String [] codes  = {"sh603306","sh600137","sh600858"};
        ArrayList<StockdataEntity> entities =
                (ArrayList<StockdataEntity>) dao.getStockData(Arrays.asList(codes));
        if(entities!=null){
            System.out.println("size: "+entities.size());
            for(int i=0;i<entities.size();i++){
                System.out.println("code: "+entities.get(i).getCode());
                System.out.println("date: "+entities.get(i).getDate().toString());
            }
        }else{
            System.out.println("null");
        }
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



    @Test
    public void getStockByPE() throws Exception{
        MyDate date = MyDate.getDateFromString("2015-04-03");
        ArrayList<String> list= (ArrayList<String>) dao.getStockCodeByPE(date,20,40);
        if(list==null){
            fail("null");
        }else{
            System.out.println(list);
        }
    }

    @Test
    public void getTradeDates() throws Exception{
        MyDate start = MyDate.getDateFromString("2015-01-01");
        MyDate end = MyDate.getDateFromString("2016-01-01");
        List<MyDate> dates = dao.getTradeDates(start,end);
        if(dates!=null){
            System.out.println("size: "+dates.size());
            for(MyDate date:dates){
                System.out.println(date.DateToString());
            }

        }else{
            fail("null");
        }
    }

    @Test
    public void getAvgPriceByCodes() throws Exception{
        String[] codes = {"sh600004","sh600137","sh600858"};
        MyDate date = MyDate.getDateFromString("2006-01-04");
        double[]  prices = dao.getAvgPriceByCodes(Arrays.asList(codes),date);
        if(prices==null){
            fail("null");
        }else{
            for(int i=0;i<prices.length;i++)
            System.out.println(codes[i]+" price: "+prices[i]);
        }
    }


    @Test
    public void getStockCodeByVolDec() throws Exception{
        MyDate date = MyDate.getDateFromString("2006-01-04");
        int vol = 20;
        MyDate start = DateCalculator.getAnotherDay(date,-vol);
        List<String>  codes = dao.getStockCodeByVolDec(start,date,vol);
        if(codes==null){
            fail("null");
        }else{
            for(int i=0;i<codes.size();i++)
                System.out.println(codes.get(i));
        }
    }

}