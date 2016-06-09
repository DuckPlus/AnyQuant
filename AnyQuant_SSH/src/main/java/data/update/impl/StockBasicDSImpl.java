package data.update.impl;


import data.update.StockBasicService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.update.enumeration.API_TYPE;
import util.update.helper.ConnectionHelper;
import util.update.helper.TransferHelper;
import util.update.po.StockBasicInfo;

/**
 * Created by 67534 on 2016/5/18.
 */
public class StockBasicDSImpl implements StockBasicService {

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

        StockBasicInfo po = TransferHelper.
                JSONObjectToStockBasicInfo(stockbasicJsonObject);
        return po;

    }
}
