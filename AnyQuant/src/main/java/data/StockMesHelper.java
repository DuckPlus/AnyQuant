package data;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.MyTime;

/**
*
* @author Qiang
* @date 2016年4月9日
*/
public class StockMesHelper {
		public static boolean isTradeDay(){
			String date = MyTime.getToDay().DateToStringSimple();
			String url = "https://api.wmcloud.com:443/data/v1/api/master/getTradeCal.json?field=&exchangeCD=XSHG,XSHE&beginDate=" +date+"&endDate=" + date;
			JSONArray array = JSONObject.fromObject(ConnectionHelper.request(url)).getJSONArray("data");
			JSONObject object = array.getJSONObject(0);
			return object.getInt("isOpen") == 1;
			
			
		}
		public static void main(String[] args) {
			isTradeDay();
		}
}
