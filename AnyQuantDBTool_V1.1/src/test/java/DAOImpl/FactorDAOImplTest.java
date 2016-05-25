package DAOImpl;

import DAO.FactorDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.MyDate;

/**
 * Created by 67534 on 2016/5/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
        (locations = {"classpath:/META-INF/applicationContext.xml"})

public class FactorDAOImplTest {
    @Autowired
    FactorDAO dao;
    @Test
    public void getMaxDate() throws Exception {
        MyDate date =dao.getMaxDate();
        if(date==null){
            System.out.println("getMaxDate() ---null---");
        }else{
            System.out.println("max date:"+date.DateToString());
        }
    }

}