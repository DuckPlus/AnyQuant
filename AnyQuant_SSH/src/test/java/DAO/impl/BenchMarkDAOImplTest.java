package DAO.impl;

import DAO.BenchMarkDAO;
import entity.BenchmarkEntity;
import entity.BenchmarkdataEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.MyDate;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by 67534 on 2016/5/12.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
        (locations = {"classpath:/META-INF/applicationContext.xml","classpath:/META-INF/infrastructure.xml"})

public class BenchMarkDAOImplTest {
    @Autowired
    BenchMarkDAO dao;

    @Test
    public void getAllBenchMarksList() throws Exception {
        ArrayList<BenchmarkEntity> list=
                ( ArrayList<BenchmarkEntity>)dao.getAllBenchMarksList();
        System.out.println("size :"+list.size());
        for(int i=0;i<10;i++){
            System.out.println("code: "+list.get(i).getCode()
                    +" name: "+list.get(i).getName());
        }
        assertEquals("000001",list.get(0).getCode());
    }

    @Test
    public void getAllBenchMarksDataList() throws Exception {
        ArrayList<BenchmarkdataEntity> list=
                ( ArrayList<BenchmarkdataEntity>)dao.getAllBenchMarksDataList();
        System.out.println("size :"+list.size());
        for(int i=0;i<10;i++){
            System.out.println("code: "+list.get(i).getCode()
                    +" date: "+list.get(i).getDate().toString());
        }
    }

    @Test
    public void getRecentBenchMarks() throws Exception {

    }

    @Test
    public void getBenchMarkByTime() throws Exception {
        String code ="000001";
        MyDate start = new MyDate (2016,3,1);
        MyDate end = new MyDate (2016,3,20);
        ArrayList<BenchmarkdataEntity> list =
                ( ArrayList<BenchmarkdataEntity>)dao.getBenchMarkByTime(code,start,end);
        System.out.println("size :"+list.size());
        for(BenchmarkdataEntity temp : list){
            System.out.print("code :"+temp.getCode()+"  ");
            System.out.println("date :"+temp.getDate().toString());
        }

        assertEquals(code,list.get(0).getCode());

    }

    @Test
    public void getBenchMarkCodeByName() throws Exception {
        String name = "上证综指";
        String code = "000001";
        assertEquals(code,dao.getBenchMarkCodeByName(name));

    }

    @Test
    public void getAvgPrice() throws Exception {
        String  code = "000001";
        MyDate date = MyDate.getDateFromString("2006-01-04");
        double  price = dao.getAvgPrice(code,date);
        if(price==0){
            fail();
        }else{
            System.out.println(code+" price: "+price);
        }

    }



}