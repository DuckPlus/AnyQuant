package DAO.impl;

import DAO.FactorDAO;
import entity.FactorEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.MyDate;
import util.enumration.AnalysisFactor;
import vo.Factor_VO;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.fail;

/**
 * Created by 67534 on 2016/5/22.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
        (locations = {"classpath:/META-INF/applicationContext.xml","classpath:/META-INF/infrastructure.xml"})

public class FactorDAOImplTest {
    @Autowired
    FactorDAO dao;

    @Test
    public void getFactorVO() throws Exception {
        String code  = "sh600004";
        MyDate start = new MyDate (2016,5,1);
        MyDate end = new MyDate (2016,5,7);
        AnalysisFactor factor = AnalysisFactor.MA5;
        ArrayList<Factor_VO> list = ( ArrayList<Factor_VO>)dao.getFactors(code,factor,start,end);
        if(list==null){
            fail("getFactorByDate() --null------");
        }else{

            System.out.println("size :"+list.size());
            for(Factor_VO vo : list){
                System.out.println("date :"+vo.date.DateToString());
                System.out.println("name :"+vo.name);
                System.out.println("value :"+vo.value);
            }
        }
    }

    @Test
    public void getFactorEntityByDate() throws Exception {

        String code  = "sh600004";
        MyDate start = new MyDate (2016,5,1);
        MyDate end = new MyDate (2016,5,7);
        ArrayList<FactorEntity> list = ( ArrayList<FactorEntity>)dao.getFactorBetweenDate(code,start,end);
        if(list==null){
            fail("getFactorByDate() --null------");
        }else if(  !code.equals(list.get(0).getCode()) ){
            fail("getFactorByDate() --code------");
        }else{
            System.out.println("size :"+list.size());
            System.out.println("code :"+list.get(0).getCode());
            System.out.print("date :"+list.get(0).getDate().toString());
        }
    }


    @Test
    public void getFactorAtDate()throws Exception {

        String [] codes  = {"sh600004","sh603306","sh600137"};
        MyDate date = new MyDate (2016,5,4);
        ArrayList<FactorEntity> list = ( ArrayList<FactorEntity>)
                dao.getFactorAtDate(Arrays.asList(codes),date);

        if(list==null){
            fail("getFactorByDate() --null------");
        }else if(  !codes[0].equals(list.get(0).getCode()) ){
            fail("getFactorByDate() --code------");
        }else{
            System.out.println("size :"+list.size());
            for(int i=0;i<codes.length;i++){
                System.out.println(list.get(i).getCode()+"  "+list.get(i).getDate().toString());
            }

        }
    }



}