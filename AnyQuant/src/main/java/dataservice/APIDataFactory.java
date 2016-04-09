package dataservice;

import data.BenchMarkDSImpl;
import data.OptionalStockDSImpl;
import data.StockDSImpl;

/**
 *
 * @author ss
 * @date 2016年3月6日
 */
public class APIDataFactory {
//        public static APIInterface getAPIDataService(){
//        	   APIInterface  api = APIInterfaceImpl.getAPIInterfaceImpl();
//        	   APIInterface  dataService = new APIDataCache(api);
//        	   return dataService;
//        }

        public static StockDataService getStockDataService(){
     	   return  StockDSImpl.getStockDSImpl();
        }

        public static BenchMarkDataService getBenchMarkDataService(){
      	   return  BenchMarkDSImpl.getBenchMarkDSImpl();
         }

        public static OptionalStockDataService getOptionalStockDataService(){
      	   return  OptionalStockDSImpl.getOptionalStockDSImpl();
         }


}
