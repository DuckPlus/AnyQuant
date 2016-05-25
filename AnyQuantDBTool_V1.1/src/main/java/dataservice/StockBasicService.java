package dataservice;


import module.po.StockBasicInfo;

/**
 * Created by 67534 on 2016/5/18.
 */
public interface StockBasicService {


    StockBasicInfo getStockBasicInfo(String code);
}
