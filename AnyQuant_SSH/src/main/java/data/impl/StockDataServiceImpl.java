package data.impl;

import data.StockDataService;
import data.helper.ConnectionHelper;
import data.helper.StockMesHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.DateCalculator;
import util.MyDate;
import util.StaticMessage;
import util.enumration.API_TYPE;
import vo.TimeSharingVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Qiang
 * @date 16/5/22
 */
public class StockDataServiceImpl implements StockDataService {
    @Override
    public List<TimeSharingVO> getTimeSharingVOs(String stockCode) {
        if (stockCode.startsWith("sh")) {
            stockCode = stockCode.substring(2) + StaticMessage.SH_EXCHANGE;
        } else {
            stockCode = stockCode.substring(2) + StaticMessage.SZ_EXCHANGE;
        }

        String endTime = StockMesHelper.isTradeDay() ? "" : StaticMessage.TRADE_OVER_TIME;


//		String url = "https://api.wmcloud.com:443/data/v1/api/market/getBarRTIntraDay.json?securityID=" + stockCode
//				+ "&startTime=&endTime="+ endTime +"&unit=1";
        JSONObject jsonObject = ConnectionHelper.requestAPI(API_TYPE.GET_TIMESAHRING	,stockCode , "" , endTime , "1");
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        jsonObject = jsonArray.getJSONObject(0);
        jsonArray = jsonObject.getJSONArray("barBodys");

        List<TimeSharingVO> pos = new ArrayList<>(jsonArray.size());
        for (int i = 0; i < jsonArray.size(); i++) {
            pos.add(makeTimeSharingVO(jsonArray.getJSONObject(i)));
        }
        return pos.isEmpty() ? null : pos;
    }


    private TimeSharingVO makeTimeSharingVO(JSONObject jsonObject) {
        MyDate date = DateCalculator.getToDay();

        String nowTime = jsonObject.getString("barTime");
        String[] split = nowTime.split(":");
        date.setHour(Integer.parseInt(split[0]));
        date.setMin(Integer.parseInt(split[1]));
        date.setSecond(0);

        return new TimeSharingVO(date, jsonObject.getDouble("closePrice"), jsonObject.getLong("totalVolume"),
                jsonObject.getDouble("totalValue"));
    }
}
