package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.helper.ConnectionHelper;
import data.helper.FileIOHelper;
import data.helper.TransferHelper;
import data.helper.StockMesHelper;
import dataservice.StockDataService;
import enumeration.API_TYPE;
import enumeration.Exchange;
import enumeration.MyDate;
import enumeration.StaticMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import po.StockPO;
import po.TimeSharingPO;
import util.MyTime;

/**
 *
 * @author ss
 * @date 2016年4月7日
 */
public class StockDSImpl implements StockDataService {
	private static StockDSImpl stockDSImpl;
	private static Map<String, String[]> IndustryLocationMap;
	private static String nameFilePath = "data//StockIndustries&Regions.txt";
	
	
	
	
	private StockDSImpl() {
		SetMapUp();
	}

	private void SetMapUp() {
		IndustryLocationMap = new HashMap<>();
		try {
			String encoding = "utf-8";
			File file = new File(nameFilePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在

				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String temp = "";
				while ((temp = bufferedReader.readLine()) != null) {
					String[] codeAndMes = temp.split(",");
					String[] industryAndLocationStrings = new String[2];
					industryAndLocationStrings[0] = codeAndMes[1];
					industryAndLocationStrings[1] = codeAndMes[2];
					IndustryLocationMap.put(codeAndMes[0], industryAndLocationStrings);
				}
				read.close();

			} else {
				System.out.println("找不到" + nameFilePath);
				file.createNewFile();
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

	}

	public static StockDataService getStockDSImpl() {
		if (stockDSImpl == null) {
			return new StockDSImpl();
		} else {
			return stockDSImpl;
		}
	}

	/**
	 * 默认返回2015年全部股票
	 */
	public List<String> getAllStocks() {
		return getAllStocks(2015);
	}

	/**
	 * 返回指定年份的全部股票
	 */
	public List<String> getAllStocks(int year) {

		if (year < 2007 || year > 2015) {
			year = 2015;
		}
		JSONObject jo = ConnectionHelper.requestAPI(API_TYPE.GET_STOCKS_LIST, String.valueOf(year));
		JSONArray ja = jo.getJSONArray("data");
		int length = ja.size();
		ArrayList<String> stockCode = new ArrayList<>();
		for (int i = 0; i < length; i++) {
			JSONObject tempJo = ja.getJSONObject(i);

			if (tempJo.getString("name").equals("sh000300")) {
				continue;
			}
			stockCode.add(tempJo.getString("name"));
		}
		return stockCode;
	}

	/**
	 * 默认返回2015年
	 */
	public List<String> getAllStocks(Exchange exchange) {

		return getAllStocks(2015, exchange);
	}

	public List<String> getAllStocks(int year, Exchange exchange) {

		String exchangeStr = "";
		if (exchange == Exchange.sh) {
			exchangeStr = "sh";
		} else {
			exchangeStr = "sz";
		}
//		String url = "http://121.41.106.89:8010/api/stocks/?year=" + year + "&exchange=" + exchangeStr;
		// System.out.println(SendGET(url, ""));
		JSONObject jo = ConnectionHelper.requestAPI(API_TYPE.GET_STOCKS_LIST_WITH_EXCHANGE, String.valueOf(year) , exchangeStr);
		JSONArray ja = jo.getJSONArray("data");
		int length = ja.size();
		ArrayList<String> stockCode = new ArrayList<>();
		for (int i = 0; i < length; i++) {
			JSONObject tempJo = ja.getJSONObject(i);

			if (tempJo.getString("name").equals("sh000300")) {
				continue;
			}
			stockCode.add(tempJo.getString("name"));
			// System.out.println(tempJo.getString("name"));
		}
		return stockCode;
	}

	/**
	 * 获取指定代码的股票的最新数据
	 *
	 */
	public StockPO getStockMes(String stockCode) {
		int offset = 0;
		MyDate date = MyTime.getAnotherDay(offset);
		
		JSONObject jObject = null;
		while (  ((jObject = checkDataValid(stockCode, date)) ==null) &&(offset>-30)) {
			offset--;
			date = MyTime.getAnotherDay(offset);
		}

//		if(offset<=-30){
//			System.out.println(stockCode+"error-----------------------");
//			return null;
//		}
		
		if(jObject == null){
			System.err.println("Error! 股票信息读取错误");
			return null;
		}
		
		
		
		return TransferHelper.JSONObjectToStockPO(IndustryLocationMap, jObject);
	}

	/**
	 * 获取指定代码的股票的在指定日期的数据
	 */
	public StockPO getStockMes(String code, MyDate date) {
		String shortCode = code.substring(2);
		String tradeDateString = date.DateToStringSimple();
		JSONObject jo = ConnectionHelper.requestAPI(API_TYPE.GET_STOCKMES_AT_TIME, "" , "" , "" , shortCode , tradeDateString);
		if (jo.getInt("retCode") == 1) {
			JSONArray jArray = jo.getJSONArray("data");
			JSONObject stockpoJsonObject = jArray.getJSONObject(0);
			StockPO po = TransferHelper.JSONObjectToStockPO(IndustryLocationMap, stockpoJsonObject);
			return po;
		} else {
			return null;
		}

	}

	/**
	 * 根据日期获取指定代码的股票的数据
	 *
	 */
	public List<StockPO> getStockMes(String code, MyDate start, MyDate end) {

		if (end.DateToString().equals(start.DateToString())) {
			List<StockPO> stocks = new ArrayList<>();
			stocks.add(getStockMes(code, start));
			return stocks;
		}
		String shortCode = code.substring(2);
		List<StockPO> pos = new ArrayList<>();
		JSONObject jo = ConnectionHelper.requestAPI(API_TYPE.GET_STOCKMES_AT_TIME, start.DateToStringSimple() , end.DateToStringSimple() , "" , shortCode ,"");
		if (jo.getInt("retCode") == 1) {
			JSONArray jArray = jo.getJSONArray("data");
			for (int i = 0; i < jArray.size(); i++) {
				JSONObject stockpoJsonObject = jArray.getJSONObject(i);
				StockPO po = TransferHelper.JSONObjectToStockPO(IndustryLocationMap, stockpoJsonObject);
				pos.add(po);
			}
			return pos;
		} else {
			return null;
		}
	}

	 /**
	  * 判断股票在该日期是否有有效数据
	  * @param code
	  * @param date
	  * @return 若有的话，返回有效数据，若无效返回null
	  */
	private JSONObject checkDataValid(String code, MyDate date) {
				
		String shortCodeString = code.substring(2);
		String tradeDateString = date.DateToStringSimple();
	
		JSONObject jo = ConnectionHelper.requestAPI(API_TYPE.CHECK_IF_TRADING, shortCodeString , tradeDateString);
		//此时表示该天大盘未开盘
		if(jo.getInt("retCode") == -1){
			return null;
		}
		//表示该天该股票停牌
		jo =  jo.getJSONArray("data").getJSONObject(0);
		return jo.isNullObject() ? null : (jo.getLong("turnoverVol") != 0 ? jo : null);
		
	}

	/**
	 * 获取所有股票的最新信息列表 在APIDataCache中实现
	 */
	@Override
	public List<StockPO> getAllStockMes() {
		return FileIOHelper.readAllStocks();
	}

	@Override
	public List<TimeSharingPO> getTimeSharingPOs(String stockCode) {
		

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

		List<TimeSharingPO> pos = new ArrayList<>(jsonArray.size());
		for (int i = 0; i < jsonArray.size(); i++) {
			pos.add(makeTimeSharingPO(jsonArray.getJSONObject(i)));
		}
		return pos.isEmpty() ? null : pos;
	}

	private TimeSharingPO makeTimeSharingPO(JSONObject jsonObject) {
		MyDate date = MyTime.getToDay();

		String nowTime = jsonObject.getString("barTime");
		String[] split = nowTime.split(":");
		date.setHour(Integer.parseInt(split[0]));
		date.setMin(Integer.parseInt(split[1]));
		date.setSecond(0);

		return new TimeSharingPO(date, jsonObject.getDouble("closePrice"), jsonObject.getLong("totalVolume"),
				jsonObject.getDouble("totalValue"));
	}

	@Override
	public boolean updateAllMes() {
		FileIOHelper.updateLatestStockMes();
		return true;
	}

}
