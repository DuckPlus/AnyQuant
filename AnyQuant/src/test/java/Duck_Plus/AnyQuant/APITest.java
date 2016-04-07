package Duck_Plus.AnyQuant;

import java.util.List;

import data.APIInterfaceImpl;
import po.BenchMarkPO;
import po.StockPO;
import util.MyTime;
import dataservice.APIDataFactory;
import dataservice.APIInterface;
import enumeration.MyDate;


public class APITest  {
         public static void main(String a[]){
                     getStockMesByNewAPI();

         }
         public  static void getAllstocksMessageByAPI(){
        	 APIInterface api =  APIInterfaceImpl.getAPIInterfaceImpl();
//        	 MyDate start = new MyDate(2016, 2, 29);
//        	 MyDate end = new MyDate(2016, 3, 7);
        	  StockPO  stock =  api.getStockMes("sh600979");
        //	StockPO stock =   api.getStockMes("sh600126");

        //	for(StockPO stock : stocks){

        		System.out.print("name: "+stock.getName()+"  " );
             	System.out.print("code: "+stock.getCode()+"  ");
             	System.out.print("open: "+stock.getOpen()+"  ");
               	System.out.print("close: "+stock.getClose()+"  ");
               	System.out.print("preClose:"+stock.getPreClose()+"  ");
               	System.out.print("high: "+stock.getHigh()+"  ");
               	System.out.print("low: "+stock.getLow()+"  ");
               	System.out.print("pe: "+stock.getPe()+"  ");
                System.out.print("pb: "+stock.getPb()+"  ");
                System.out.print("adj_price: "+stock.getAccAdjFactor()+"  ");
               	System.out.print("changeRate: "+stock.getChangeRate()+"  ");
             	System.out.print("amp: "+stock.getAmplitude()+"  ");
             	System.out.print("turnover: "+stock.getTurnoverRate()+"  ");
             	System.out.print("date: "+stock.getDate()+'\n');
       }
       //  }

         public static void getBenchMes(){

        	 APIInterface api =  APIInterfaceImpl.getAPIInterfaceImpl();
        	// BenchMarkPO stock = api.getBenchMes("hs300");
        	 MyDate start = new MyDate(2016, 2, 1);
        	 MyDate end = new MyDate(2016, 3,11);
        	 List<BenchMarkPO> benchs = api.getBenchMes("hs300", start, end);
        	 for(BenchMarkPO  stock: benchs){
        		            System.out.print("date: "+stock.getDate()+" ");
        		        	System.out.print("code: "+stock.getCode()+" ");
        		        	System.out.print("open: "+stock.getOpen()+" ");
        		          	System.out.print("close: "+stock.getClose()+" ");
        		          	System.out.print("high: "+stock.getHigh()+" ");
        		          	System.out.print("low: "+stock.getLow()+" ");


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

         public  static void getLatestMes(){
        	 APIInterface api = APIInterfaceImpl.getAPIInterfaceImpl();
        	 StockPO stock = api.getStockMes("sh600243");
        	 System.out.println(stock.getPe());
        	 System.out.println(stock.getAccAdjFactor());
         }
         public static void getAllMesByCache(){
        	 APIInterface api = new APIDataFactory().getAPIDataService();
        	 MyDate start = new MyDate(2016,3,20);
        	 MyDate end = new MyDate(2016, 3, 25);
        	 List<StockPO > stocks = api.getStockMes("sh600216",start,end);

        	 for(StockPO stock : stocks){

           	    System.out.print("name: "+stock.getName()+"  " );
             	System.out.print("code: "+stock.getCode()+"  ");
             	System.out.print("open: "+stock.getOpen()+"  ");
               	System.out.print("close: "+stock.getClose()+"  ");
               	System.out.print("preClose:"+stock.getPreClose()+"  ");
               	System.out.print("high: "+stock.getHigh()+"  ");
               	System.out.print("low: "+stock.getLow()+"  ");
               	System.out.print("pe: "+stock.getPe()+"  ");
                System.out.print("pb: "+stock.getPb()+"  ");
                System.out.print("adj_price: "+stock.getAccAdjFactor()+"  ");
               	System.out.print("changeRate: "+stock.getChangeRate()+"  ");
             	System.out.print("amp: "+stock.getAmplitude()+"  ");
             	System.out.print("turnover: "+stock.getTurnoverRate()+"  ");
             	System.out.print("date: "+stock.getDate()+'\n');
        	 }
         }

          public static void getStockMesByNewAPI(){
        	  APIInterface api =  APIInterfaceImpl.getAPIInterfaceImpl();
//        		MyDate end = MyDate.getDateFromString("2016-03-28");
//        		MyDate start = MyTime.getAnotherDay(end,-3);
        	  StockPO stock =api.getStockMes("sh600216");
        	//  List<StockPO> stocks = api.getStockMes("600216",start,end);
        //	  for(StockPO stock:stocks){
        	    System.out.print("name: "+stock.getName()+"  " );
           	    System.out.print("code: "+stock.getCode()+"  ");
           	    System.out.print("board: "+stock.getBoard()+"  ");
           	    System.out.print("region: "+stock.getRegion()+"  ");

             	System.out.print("open: "+stock.getOpen()+"  ");
             	System.out.print("close: "+stock.getClose()+"  ");
             	System.out.print("preClose:"+stock.getPreClose()+"  ");
             	System.out.print("high: "+stock.getHigh()+"  ");
             	System.out.print("low: "+stock.getLow()+"  ");
             	System.out.print("pe: "+stock.getPe()+"  ");
              System.out.print("pb: "+stock.getPb()+"  ");
              System.out.print("accAdjFactor: "+stock.getAccAdjFactor()+"  ");
          	System.out.print("turnoverVol: "+stock.getTurnoverVol()+"  ");
           	System.out.print("turnoverRate: "+stock.getTurnoverRate()+"  ");
        	System.out.print("turnoverValue: "+stock.getTurnoverValue()+"  ");
         	System.out.print("changeRate: "+stock.getChangeRate()+"  ");
           	System.out.print("amp: "+stock.getAmplitude()+"  ");
           	System.out.print("date: "+stock.getDate()+'\n');
           	}


}
