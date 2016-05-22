package data.helper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.DateCalculator;
import util.enumration.API_TYPE;

/**
 * @author Qiang
 * @date 16/5/22
 */
public class StockMesHelper {
    public static boolean isTradeDay() {
        String date = DateCalculator.getToDay().DateToStringSimple();
//			String url = "https://api.wmcloud.com:443/data/v1/api/master/getTradeCal.json?field=&exchangeCD=XSHG,XSHE&beginDate=" +date+"&endDate=" + date;
        JSONArray array = (ConnectionHelper.requestAPI(API_TYPE.IS_TRADING_DAY, "XSHG,XSHE", date, date)).getJSONArray("data");
        JSONObject object = array.getJSONObject(0);

        return object.getInt("isOpen") == 1;


    }
}
