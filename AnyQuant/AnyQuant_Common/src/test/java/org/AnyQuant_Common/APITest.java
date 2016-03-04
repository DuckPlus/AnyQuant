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

         public void getAllstocks(){
        	 APIInterface api = new APIInterfaceImpl();
        	 MyDate start = new MyDate(2015, 1, 1);
        	 MyDate end = new MyDate(2015, 1, 30);
        	 List<StockPO> stockCodes =  api.getStockMes("sh600000", start, end);
        	 for (StockPO temp : stockCodes){
        		 System.out.println(temp.getDate()+"||"+temp.getOpen()+"|| "+temp.getClose()+" ||"+temp.getHigh());
        	 }
         }

}
