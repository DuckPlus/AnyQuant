package DAO.impl;

import DAO.OptionalDAO;
import entity.StockEntity;
import entity.StockdataEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by 67534 on 2016/5/12.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
        (locations = {"classpath:/META-INF/applicationContext.xml","classpath:/META-INF/infrastructure.xml"})

public class OptionalDAOImplTest {

    @Autowired
    OptionalDAO dao;

    @Test
    public void getOptionalStocksDatas() throws Exception {
        int userID =1;
        ArrayList<StockdataEntity> result =
                (ArrayList<StockdataEntity>) dao.getOptionalStocksDatas(userID+"");
        for(StockdataEntity temp : result){
            System.out.println("code: "+temp.getCode());
            System.out.println("name: "+temp.getName());
            System.out.println("date: "+temp.getDate().toString());
        }
    }

    @Test
    public void getOptionalStocks() throws Exception {
        int userID = 2;
        ArrayList<StockEntity> result =
                (ArrayList<StockEntity>) dao.getOptionalStocks(userID+"");
        for(StockEntity temp : result){
            if(temp!=null){
                System.out.println("code: "+temp.getCode());
                System.out.println("name: "+temp.getName());
            }

        }

    }

    @Test
    public void deleteStockCode() throws Exception {
        int userID = 2;
        System.out.println("before delete:");
        ArrayList<StockEntity> result =
                (ArrayList<StockEntity>) dao.getOptionalStocks(userID+"");

        for(StockEntity temp : result){
            System.out.println("code: "+temp.getCode());
            System.out.println("name: "+temp.getName());
        }

        System.out.println("delete: "+"sh7000000");
//      dao.deleteStockCode(userID+"",result.get(0).getCode());
        dao.deleteStockCode(userID+"","sh7000000");

        System.out.println("after delete:");
        for(StockEntity temp : result){
            System.out.println("code: "+temp.getCode());
            System.out.println("name: "+temp.getName());
        }


    }

    @Test
    public void addStockCode() throws Exception {
        String userID ="1";
        String code = "sh600067";
        dao.addStockCode(userID,code);
        assertEquals(true, dao.ifStockExist(userID,code));
        dao.deleteStockCode(userID,code);
    }

    @Test
    public void clearOptionalStocks() throws Exception {
//        String userID="1";
//        dao.clearOptionalStocks(userID);
//        ArrayList<StockEntity> result =
//                (ArrayList<StockEntity>) dao.getOptionalStocks(userID);
//        assertEquals( 0 , result.size());
    }

    @Test
    public void ifStockExist() throws Exception {
        String userID ="1";
        String code = "sh600000";
        boolean bool=dao.ifStockExist(userID,code);
        assertEquals(false,bool);
    }

}