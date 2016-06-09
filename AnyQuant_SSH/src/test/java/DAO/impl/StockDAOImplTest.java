package DAO.impl;

import DAO.StockDAO;
import entity.StockEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * Created by 67534 on 2016/5/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
        (locations = {"classpath:/META-INF/applicationContext.xml","classpath:/META-INF/infrastructure.xml"})

public class StockDAOImplTest {
    @Autowired
    StockDAO dao;
    @Test
    public void findAllStocks() throws Exception {
        List<String> codes = dao.getAllStockCodes();
        if(codes==null){
            fail("null");
        }else{
            System.out.println(codes.size()+"codes in total");
        }
    }

    @Test
    public void getStockEntity() throws Exception {
        String code="sh603306";
        StockEntity temp=dao.getStockEntity(code);
        if(temp==null){
            fail("null");
        }else{
            System.out.println("code: "+temp.getCode());
        }
    }
    @Test
    public void getBoardRelatedStockCodes() throws Exception
    {
        String board = "汽车零部件";
        List<String>  codes = dao.getBoardRealatedStockCodes(board);
        if(codes==null){
            fail("null");
        }else{
            System.out.println(codes.size()+"codes in board "+board);
            System.out.println(codes);
        }
    }
    @Test
    public void getBoardRelatedStock() throws Exception
    {
        String board = "汽车零部件";
        List<StockEntity>  entities = dao.getBoardRelatedStock(board);
        if(entities==null){
            fail("null");
        }else{
            System.out.println(entities.size()+"codes in board "+board);
            System.out.println("code: "+entities.get(0).getCode());
            System.out.println("name: "+entities.get(0).getName());
            System.out.println("board: "+entities.get(0).getBoard());
        }
    }

    @Test
    public void getRegionRelatedStock() throws Exception {
        String region = "江苏省";
        List<StockEntity>  entities = dao.getRegionRelatedStock(region);
        if(entities==null){
            fail("null");
        }else{
            System.out.println(entities.size()+"codes in region "+region);
            System.out.println("code: "+entities.get(0).getCode());
            System.out.println("name: "+entities.get(0).getName());
            System.out.println("board: "+entities.get(0).getRegion());
        }
    }

    @Test
    public void getAllBoard() throws Exception {
        ArrayList<String> boards =
                (ArrayList<String>) dao.getAllBoardName();
        if(boards!=null){
            System.out.println("size: "+boards.size());
            for(String temp : boards){
                System.out.println(temp);
            }
        }

    }



    @Test
    public void getNames() throws  Exception{
        String [] codes = {"sh603306","sh600137","sh600858","sh600083"};
        List<String> result = dao.getNames(Arrays.asList(codes));
        if (result == null) {
           fail("null-------");
        }else{
            for(int i=0;i<codes.length;i++ ){
                System.out.println(codes[i]+"  "+result.get(i));
            }
        }
    }



}