package dataservice;
/**
 *
 * @author ss
 * @date 2016年3月6日
 */
public class APIDataFactory {
        public static APIInterface getAPIDataService(){
        	   APIInterface  api = APIInterfaceImpl.getAPIInterfaceImpl();
        	   APIInterface  dataService = new APIDataCache(api);
        	   return dataService;
        }
}
