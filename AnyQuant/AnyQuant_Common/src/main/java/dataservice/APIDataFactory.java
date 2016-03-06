package dataservice;
/**
 *
 * @author ss
 * @date 2016年3月6日
 */
public class APIDataFactory {
        public static APIInterface getAPIDataService(){
        	   APIInterface  api = new APIInterfaceImpl();
        	   APIInterface  dataService = new APIDataCache(api);
        	   return dataService;
        }
}
