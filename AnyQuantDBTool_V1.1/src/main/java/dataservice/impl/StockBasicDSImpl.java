package dataservice.impl;

import dataservice.StockBasicService;
import module.po.StockBasicInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.enumeration.API_TYPE;
import util.helper.ConnectionHelper;
import util.helper.TransferHelper;

/**
 * Created by 67534 on 2016/5/18.
 */
public class StockBasicDSImpl implements StockBasicService{

    private static StockBasicDSImpl stockBasicDSImpl;

    public static StockBasicService getStockBasicDSImpl(){
        if(stockBasicDSImpl==null){
            return new StockBasicDSImpl();
        }else{
            return stockBasicDSImpl;
        }
    }

    @Override
    public StockBasicInfo getStockBasicInfo(String code) {

        String shortCode = code.substring(2);
        JSONObject jo = ConnectionHelper.
                requestAPI(API_TYPE.GET_StockBasicInfo,shortCode);

        JSONArray jArray = jo.getJSONArray("data");
        JSONObject stockbasicJsonObject = jArray.getJSONObject(0);

        StockBasicInfo po =TransferHelper.
                JSONObjectToStockBasicInfo(stockbasicJsonObject);
        return po;

    }
}
