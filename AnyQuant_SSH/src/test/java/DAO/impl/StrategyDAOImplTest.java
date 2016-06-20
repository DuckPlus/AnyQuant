package DAO.impl;

import DAO.StrategyDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vo.ReportVO;
import vo.TradeDataVO;

import java.util.List;

import static junit.framework.TestCase.fail;

/**
 * Created by 67534 on 2016/6/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
        (locations = {"classpath:/META-INF/applicationContext.xml","classpath:/META-INF/infrastructure.xml"})

public class StrategyDAOImplTest {
    @Autowired
    StrategyDAO strategyDAO;

    @Test
    public void saveReport() throws Exception {
        for(int j=0;j<5;j++){
            ReportVO reportVO = new ReportVO();
            int size =3;
            String userID="1";
            for(int i=0;i<size;i++){
                TradeDataVO tradeDataVO =new TradeDataVO();
                tradeDataVO.profit=i*1000;
                tradeDataVO.nowCapital=i*2000;
                reportVO.tradeDataVOList.add(tradeDataVO);
            }
            strategyDAO.saveReport(userID,reportVO);
        }

    }

//    @Test
//    public void getAllReports() throws Exception {
//        String userID="1";
//        List<ReportVO> voList = strategyDAO.getAllReports(userID);
//        if(voList==null){
//            fail("null");
//        }else{
//            System.out.println("size: "+voList.size());
//            int i=0;
//            for(ReportVO reportVO : voList ){
//                System.out.println("the "+i+" th reportvo");
//                i++;
//                for(TradeDataVO tradeDataVO:reportVO.tradeDataVOList){
//                    System.out.println("profit: "+tradeDataVO.profit);
//                    System.out.println("curCapital: "+tradeDataVO.nowCapital);
//                }
//            }
//        }
//    }

}