package blimpl;

import blservice.StockBLService;
import enumeration.Stock_Attribute;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.DateCalculator;
import vo.OHLC_VO;
import vo.StockVO;
import vo.TimeSharingVO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Qiang
 * @date 4/15/16
 */
public class StockBLImplTest {
    private StockBLService bl;
    @Before
    public void setUp() throws Exception {
        bl = BusinessFactory.getStockBLService();
    }

    @Test
    public void getAllStocks() throws Exception {
        // test in other method
    }

    @Test
    public void getSortStocks() throws Exception {
        Iterator<StockVO> vos = bl.getSortStocks(true , Stock_Attribute.amplitude);

        double last = Double.MIN_VALUE;
        last = vos.next().amplitude;
        while(vos.hasNext()){
            double temp = vos.next().amplitude;
            System.out.println(temp);
            if(temp < last){
                System.out.print(temp + " " + last);
                fail("Can not pass the sort of amplitude");
            }else{
                last = temp;
            }
        }





    }

    /**
     * 本测试主要测试对600XXX的股票进行升序排序是否有问题
     * @throws Exception
     */
    @Test
    public void getSortStocksInScope() throws Exception {
        Iterator<StockVO> vos =  bl.getStocksByStockCode("600");
        List<String> vostr = new ArrayList<>();
        while(vos.hasNext()){
            vostr.add(vos.next().code);
        }


        vos = bl.getSortStocksInScope(false , Stock_Attribute.amplitude , vostr);


        double last = Double.MAX_VALUE;
        while(vos.hasNext()){
            double temp = vos.next().amplitude;
            System.out.println(temp);
            if(temp > last){
                fail("Can not pass the sort of high in given scope");
            }else{
                last = temp;
            }
        }




    }

    @Test
    public void getRecentStocks() throws Exception {
        // not need to test
    }

    @Test
    public void getTodayStockVO() throws Exception {
        // not need to test
    }

    @Test
    public void getStocksByTime() throws Exception {
        // not need to test
    }

    @Test
    public void getStocksByStockCode() throws Exception {
        // no need to test
    }

    @Test
    public void getDayOHLC_Data() throws Exception {
        Iterator<StockVO> vos = bl.getStocksByTime("sh600300", DateCalculator.getAnotherDay(-30), DateCalculator.getAnotherDay(0));
        List<Double> lows = new ArrayList<Double>();
        while(vos.hasNext()){
            lows.add(vos.next().low);
        }
        List<OHLC_VO> tmp = bl.getDayOHLC_Data("sh600300", DateCalculator.getAnotherDay(-30), DateCalculator.getAnotherDay(0));
        for (int i = 0; i < 15; i++) {
            if(!lows.get(i).equals(tmp.get(i).low)){
                fail("Fail to get OHLC_DATA correctly.");
            }
        }
    }

    @Test
    public void getWeekOHLC_Data() throws Exception {

    }





    @Test
    public void getMonthOHLC_Data() throws Exception {

    }

    @Test
    public void getSharingVOs() throws Exception {
        List<TimeSharingVO> vos = bl.getSharingVOs("sh600000");
        for (int i = 0; i < vos.size(); i++) {
            System.out.println(vos.get(i).nowTime.getHour() +":"+ vos.get(i).nowTime.getMin());

            System.out.println(vos.get(i).nowPrice);

        }



    }



    @Test
    public void updateAllStockMes() throws Exception {

    }

}