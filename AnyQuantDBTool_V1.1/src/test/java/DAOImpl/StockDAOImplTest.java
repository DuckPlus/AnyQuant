package DAOImpl;

import DAO.StockDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 67534 on 2016/5/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
        (locations = {"classpath:/META-INF/applicationContext.xml"})
public class StockDAOImplTest {
    @Autowired
    StockDAO dao;
    @Test
    public void getAllStockCodes() throws Exception {
         List<String> codes = dao.getAllStockCodes();
        if(codes==null){
            fail("getAllStockCodes() ---null");
        }else{
            System.out.println("size : "+codes.size()+"-----------");
            for(String code : codes){
                 System.out.println(code);
            }
        }
    }

    @Test
    public void getAllStocks() throws Exception {

    }

    @Test
    public void getStockEntity() throws Exception {

    }

}