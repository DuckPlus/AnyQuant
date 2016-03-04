package org.AnyQuant_Common;



import java.util.List;

import dataservice.APIInterface;
import dataservice.APIInterfaceImpl;
import enumeration.Exchange;
import enumeration.MyDate;
import enumeration.Stock_Attribute;
import junit.framework.TestCase;
import po.StockPO;


public class APITest extends TestCase {

         public void getAllstocksMessage(){
        	 APIInterface api = new APIInterfaceImpl();
        	 MyDate start = new MyDate(2016, 3, 2);
        	 MyDate end = new MyDate(2016, 3, 3);
        	 List<String>  stockCodes = api.getAllStocks();
        	  for(int i=0;i<stockCodes.size();i++){
        		    System.out.println(stockCodes.get(i)+"------------------------------");
        		    List<StockPO> stockMess = api.getStockMes(stockCodes.get(i), start, end);
        		    for (StockPO temp : stockMess){
           		            System.out.println
           		               (temp.getDate()+"||"+temp.getOpen()+"|| "+temp.getClose()+" ||"+temp.getHigh());
                    }
             }
         }
}

