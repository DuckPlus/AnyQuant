package data;

import data.impl.NewsDataServiceImpl;
import data.impl.StockDataServiceImpl;

/**
 * 数据层(与外部API交互部分)工厂类
 * @author Qiang
 * @date 16/5/18
 */
public class DataServiceFactory {
    public static NewsDataService getNewsDataService(){
        return new NewsDataServiceImpl();
    }

    public static StockDataService getStockDataService(){ return  new StockDataServiceImpl();}


}
