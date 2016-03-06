package org.AnyQuant_Common;



import java.util.ArrayList;
import java.util.List;

import dataservice.APIInterface;
import dataservice.APIInterfaceImpl;
import enumeration.Exchange;
import enumeration.MyDate;
import enumeration.Stock_Attribute;
import junit.framework.TestCase;
import po.StockPO;


public class APITest extends TestCase {
         public static void main(String a[]){
        	    getAllstocksMessage();
         }
         public  static void getAllstocksMessage(){
        	 APIInterface api = new APIInterfaceImpl();
        	 MyDate start = new MyDate(2015, 3, 2);
        	 MyDate end = new MyDate(2016, 3, 3);
//        	 List<String>  stockCodes = api.getAllStocks();
//        	  for(int i=0;i<stockCodes.size();i++){
//        		    System.out.println(stockCodes.get(i)+"------------------------------");
//        		    List<StockPO> stockMess = api.getStockMes(stockCodes.get(i), start, end);
//        		    for (StockPO temp : stockMess){
//           		            System.out.println
//           		               (temp.getDate()+"||"+temp.getOpen()+"|| "+temp.getClose()+" ||"+temp.getHigh());
//                    }
//             }
        	List<StockPO>  stocks =  api.getStockMes("sh600126",start,end);
        	//  api.getStockMes("sh600126");
        	for(StockPO stock : stocks){
     
        	System.out.println("date: "+stock.getDate());
  //      	System.out.println("name: "+stock.getName());
        	System.out.print("code: "+stock.getCode()+" ");
        	System.out.print("open: "+stock.getOpen()+" ");
          	System.out.print("close: "+stock.getClose()+" ");
          	System.out.print("preClose:"+stock.getPreClose()+" ");
          	System.out.print("high: "+stock.getHigh()+" ");
          	System.out.print("low: "+stock.getLow()+" ");
        	System.out.print("changeRate: "+stock.getChangeRate()+" ");
        	System.out.print("amp: "+stock.getAmplitude()+" ");	
        	}
         }
}

