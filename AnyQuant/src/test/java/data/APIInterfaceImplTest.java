package data;

import org.junit.Before;
import org.junit.Test;

import dataservice.BenchMarkDataService;
import dataservice.OptionalStockDataService;
import dataservice.StockDataService;
import enumeration.Exchange;
import enumeration.MyDate;
import po.BenchMarkPO;
import po.StockPO;
import util.MyTime;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Qiang on 3/27/16.
 */
public class APIInterfaceImplTest {
	StockDataService stockImpl ;
	BenchMarkDataService benchImpl ;
	OptionalStockDataService optionImpl ;
    @Before
    public void setUp() throws Exception {
    	 stockImpl =  StockDSImpl.getStockDSImpl();
    	 benchImpl = BenchMarkDSImpl.getBenchMarkDSImpl();
    	 optionImpl = OptionalStockDSImpl.getOptionalStockDSImpl();
    }

    @Test
    public void getAPIInterfaceImpl() throws Exception {

    }

    @Test
    public void getAllStocks() throws Exception {
          List<String> codes = stockImpl.getAllStocks();
          if(codes==null){
        	  fail("fail to pass getAllStocks()--NULL");
          }
          if(!codes.get(0).equals("sz002644")||!codes.get(codes.size()-1).equals("sh600571")){
        	  fail("fail to pass getAllStocks()");
          }
    }

    @Test
    public void getAllStocks1() throws Exception {
    	 List<String> codes =stockImpl.getAllStocks(Exchange.sh);
    	 if(codes==null){
       	  fail("fail to pass getAllStocks1()--NULL");
         }
    	 if(!codes.get(0).equals("sh600216")){
       	  fail("fail to pass getAllStocks1()");
         }

    }

    @Test
    public void getAllStocks2() throws Exception {
    	 List<String> codes = stockImpl.getAllStocks(2015);
    	 if(codes==null){
       	  fail("fail to pass getAllStocks2()--NULL");
         }
    	 if(!codes.get(0).equals("sz002644")){
       	  fail("fail to pass getAllStocks2()");
         }
    }

    @Test
    public void getAllStocks3() throws Exception {
    	List<String> codes = stockImpl.getAllStocks(2015,Exchange.sz);
    	 if(codes==null){
       	    fail("fail to pass getAllStocks3()--NULL");
         }
   	    if(!codes.get(0).equals("sz002644")){
      	  fail("fail to pass getAllStocks3()");
        }
    }

    @Test
    public void getStockMes() throws Exception {
        StockPO stockPO = stockImpl.getStockMes("sh600216");
        if(stockPO==null){
     	   fail("fail to pass getStockMes()--NULL");
        }else if(!stockPO.getCode().equals("sh600216")){
     	   fail("fail to pass getStockMes()");
        }else if(!stockPO.getName().equals("浙江医药")){
     	   fail("fail to pass getStockMes()");
        }
    }

    @Test
    public void getStockMes1() throws Exception {
    	MyDate end = MyTime.getToDay();
    	MyDate start = MyTime.getAnotherDay(-10);
        List<StockPO>  stocks = stockImpl.getStockMes("sh600216",start,end);
      //  System.out.println(stocks.get(0).getCode()+"   "+stocks.get(0).getName());
        if(stocks==null){
     	   fail("fail to pass getStockMes1()--NULL");
        }else if(!stocks.get(0).getCode().equals("sh600216")){
     	   fail("fail to pass getStockMes1()");
        }else if(!stocks.get(0).getName().equals("浙江医药")){
     	   fail("fail to pass getStockMes1()");
        }
    }

    @Test
    public void getAllStockMes() throws Exception {
    	List<StockPO> pos = stockImpl.getAllStockMes();
    	for(StockPO po : pos){
    		System.out.println(po.MyToString(','));
    	}
    	if(pos==null){
    		fail("fail to pass getAllBenchMes()");
    	}
    }

    @Test
    public void getBenchMes() throws Exception {
    	BenchMarkPO  benchMark =  benchImpl.getBenchMes("000001");
    	if(benchMark==null){
    		fail("fail to pass getBenMes()--NULL");
    	}
    	if(!benchMark.getCode().equals("000001")){
    	    fail("fail to pass getBenchMes()");
    	}

    }

    @Test
    public void getBenchMes1() throws Exception {
    	MyDate endDate = MyTime.getToDay();
    	MyDate startDate = MyTime.getAnotherDay(endDate,-10);
    	List<BenchMarkPO>  benchMarks =  benchImpl.getBenchMes("000001",startDate,endDate);
    	if(benchMarks==null){
    		fail("fail to pass getBenMes1()--NULL");
    	}else if(!benchMarks.get(0).getCode().equals("000001")){
    		fail("fail to pass getBenMes1()");
    	}
    }

    @Test
    public void getAllBenchMarks() throws Exception {
       List<String> markCodes = benchImpl.getAllBenchMarks();
       if(!markCodes.get(0).equals("000001")){
    	   fail("fail to pass getAllBenchMarks()");
       }
    }

    @Test
    public void getAllBenchMes() throws Exception {
    	List<BenchMarkPO> pos = benchImpl.getAllBenchMes();
    	if(pos==null){
    		fail("fail to pass getAllBenchMes()");
    	}
    }

    @Test
    public void getOptionalStocks() throws Exception {
    	Iterator<StockPO> it = optionImpl.getOptionalStocks();
    	if(it==null){
    		fail("fail to pass getOptionalStocks()");
    	}
    }

    @Test
    public void addOptionalStock() throws Exception {
    	optionImpl.addOptionalStock("sh600000");
        List<String> codes  = optionImpl.getSelectedStockCodes();
       for(String code:codes){
    	   if(code.equals("sh600000")){
    		    return;
    	   }
       }
       fail("fail to pass addOptionalStock()");
    }

    @Test
    public void deleteOptionalStock() throws Exception {
    	optionImpl.addOptionalStock("sh600001");
    	 List<String> codes  = optionImpl.getSelectedStockCodes();
    	 if(codes.size()>=1){
    		 String toDeleteString = codes.get(codes.size()-1);
    		 optionImpl.deleteOptionalStock(toDeleteString);
            System.out.println("delete "+toDeleteString);
            List<String> newcodes  = optionImpl.getSelectedStockCodes();
            if(newcodes.size()==codes.size()){
               fail("fail to pass deleteOptionalStock()");
            }

            for(String newcode:newcodes){
            	if(newcode.equals(toDeleteString)){
            		fail("fail to pass deleteOptionalStock()");
            	}
            }
  	     }
      }

    @Test
    public void clearOptionalStock() throws Exception {

    	optionImpl.clearOptionalStocks();
       List<String> newcodes  = optionImpl.getSelectedStockCodes();
     //  System.out.println(newcodes.get(0));
       if(newcodes.size()>=1){
           fail("fail to pass clearOptionalStock()");
       }
    }


}