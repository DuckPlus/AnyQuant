package org.AnyQuant_Common;



import java.util.ArrayList;
import java.util.List;

import dataservice.APIDataFactory;
import dataservice.APIInterface;
import dataservice.APIInterfaceImpl;
import enumeration.Exchange;
import enumeration.MyDate;
import enumeration.Stock_Attribute;
import junit.framework.TestCase;
import po.BenchMarkPO;
import po.StockPO;


public class APITest extends TestCase {
         public static void main(String a[]){
        	  //  getAllstocksMessage();
        	 //getBenchMes();
        	   // getStockCode();
        	 getAllMesByCache();
        	 
         }
         public  static void getAllstocksMessageByAPI(){
        	 APIInterface api = new APIInterfaceImpl();
        	 MyDate start = new MyDate(2016, 3, 3);
        	 MyDate end = new MyDate(2016, 3, 6);
        	List<StockPO>  stocks =  api.getStockMes("sh600126",start,end);
        	//  api.getStockMes("sh600126");
        	for(StockPO stock : stocks){
               System.out.println(stock.toString());
        	
  //      	System.out.println("name: "+stock.getName());
//        	System.out.print("code: "+stock.getCode()+" ");
//        	System.out.print("open: "+stock.getOpen()+" ");
//          	System.out.print("close: "+stock.getClose()+" ");
//          	System.out.print("preClose:"+stock.getPreClose()+" ");
//          	System.out.print("high: "+stock.getHigh()+" ");
//          	System.out.print("low: "+stock.getLow()+" ");
//        	System.out.print("changeRate: "+stock.getChangeRate()+" ");
//        	System.out.print("amp: "+stock.getAmplitude()+" ");	
//        	System.out.println("turnover: "+stock.getTurnover()+" ");
//        	System.out.print("date: "+stock.getDate()+'\n');
        	}
         }

         public static void getBenchMes(){
        	 APIInterface api = new APIInterfaceImpl();
        	 MyDate start = new MyDate(2015, 3, 2);
        	 MyDate end = new MyDate(2016, 3, 3);
        	 List<BenchMarkPO> benchs = api.getBenchMes("hs300", start, end);
        	 for(BenchMarkPO stock : benchs){
        		            System.out.print("date: "+stock.getDate()+" ");
        		        	System.out.print("code: "+stock.getCode()+" ");
        		        	System.out.print("open: "+stock.getOpen()+" ");
        		          	System.out.print("close: "+stock.getClose()+" ");
        		          	System.out.print("high: "+stock.getHigh()+" ");
        		          	System.out.print("low: "+stock.getLow()+" ");
        		          	System.out.print("adj_price: "+stock.getAdj_price()+'\n');
        		        	
        		 }
        		        
         }
         
         public static void getStockCode(){
        	 APIInterface api = new APIDataFactory().getAPIDataService();
        	 List<String > codes = api.getAllStocks();
        	 if(codes!=null){
        	   for(String temp : codes ){
        		   System.out.println(temp);
        	   }
        	 }else{
        		 System.out.println("null");
        	 }
         }
         
         
         public static void getAllMesByCache(){
        	 APIInterface api = new APIDataFactory().getAPIDataService();
        	 List<StockPO > stocks = api.getAllStockMes();
        	 for(StockPO stock : stocks){
           	    System.out.print("name: "+stock.getName()+"  " );
             	System.out.print("code: "+stock.getCode()+"  ");
             	System.out.print("open: "+stock.getOpen()+"  ");
               	System.out.print("close: "+stock.getClose()+"  ");
               	System.out.print("preClose:"+stock.getPreClose()+"  ");
               	System.out.print("high: "+stock.getHigh()+"  ");
               	System.out.print("low: "+stock.getLow()+"  ");
             	System.out.print("changeRate: "+stock.getChangeRate()+"  ");
             	System.out.print("amp: "+stock.getAmplitude()+"  ");	
             	System.out.print("turnover: "+stock.getTurnover()+"  ");
             	System.out.print("date: "+stock.getDate()+'\n');
        	 }
         }
}
