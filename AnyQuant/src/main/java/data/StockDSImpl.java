package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.python.antlr.PythonParser.return_stmt_return;

import dataservice.StockDataService;
import enumeration.Exchange;
import enumeration.MyDate;
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
		String url = "http://121.41.106.89:8010/api/stocks/?year=" + year;
		// System.out.println(SendGET(url, ""));
		JSONObject jo = JSONObject.fromObject(ConnectionHelper.SendGET(url, ""));
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
		String url = "http://121.41.106.89:8010/api/stocks/?year=" + year + "&exchange=" + exchangeStr;
		// System.out.println(SendGET(url, ""));
		JSONObject jo = JSONObject.fromObject(ConnectionHelper.SendGET(url, ""));
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
		while ((getStockMesRequestResult(stockCode, date) != 1)&&(offset>-10)) {
			offset--;
			date = MyTime.getAnotherDay(offset);
		}

		if(offset<=-10){
			System.out.println(stockCode+"error-----------------------");
			return null;
		}
		return getStockMes(stockCode, date);
	}

	/**
	 * 获取指定代码的股票的在指定日期的数据
	 */
	public StockPO getStockMes(String code, MyDate date) {

		String shortCode = code.substring(2);
		String tradeDateString = date.DateToStringSimple();
		String url = "https://api.wmcloud.com:443/data/v1"
				+ "/api/market/getMktEqud.json?field=&beginDate=&endDate=&secID=&ticker=" + shortCode + "&tradeDate="
				+ tradeDateString;
		String result = ConnectionHelper.request(url);
		JSONObject jo = JSONObject.fromObject(result);
		if (jo.getInt("retCode") == 1) {
			JSONArray jArray = jo.getJSONArray("data");
			JSONObject stockpoJsonObject = jArray.getJSONObject(0);
			StockPO po = JSONTransferHelper.JSONObjectToStockPO(IndustryLocationMap, stockpoJsonObject);
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
		String url = "https://api.wmcloud.com:443/data/v1" + "/api/market/getMktEqud.json?field=&beginDate="
				+ start.DateToStringSimple() + "&endDate=" + end.DateToStringSimple() + "&secID=&ticker=" + shortCode
				+ "&tradeDate=";
		String result = ConnectionHelper.request(url);
	//	System.out.println(result);
		JSONObject jo = JSONObject.fromObject(result);
		if (jo.getInt("retCode") == 1) {
			JSONArray jArray = jo.getJSONArray("data");
			for (int i = 0; i < jArray.size(); i++) {
				JSONObject stockpoJsonObject = jArray.getJSONObject(i);
				StockPO po = JSONTransferHelper.JSONObjectToStockPO(IndustryLocationMap, stockpoJsonObject);
				pos.add(po);
			}
			return pos;
		} else {
			return null;
		}
	}

	// 不确定请求的股票信息在某日是否有数据，返回请求结果1为有，-1为没有
	private int getStockMesRequestResult(String code, MyDate date) {
		String shortCodeString = code.substring(2);
		String tradeDateString = date.DateToStringSimple();
		String url = "https://api.wmcloud.com:443/data/v1"
				+ "/api/market/getMktEqud.json?field=&beginDate=&endDate=&secID=&ticker=" + shortCodeString
				+ "&tradeDate=" + tradeDateString;
		String result = ConnectionHelper.request(url);
		JSONObject jo = JSONObject.fromObject(result);
		return jo.getInt("retCode");
	}

	/**
	 * 获取所有股票的最新信息列表 在APIDataCache中实现
	 */
	@Override
	public List<StockPO> getAllStockMes() {
		return FileIOHelper.readAllMes();
	}

	@Override
	public List<TimeSharingPO> geTimeSharingPOs(String stockCode) {
		String SH_EXCHANGE = ".XSHG";
		String SZ_EXCHANGE = ".XSHE";

		if (stockCode.startsWith("sh")) {
			stockCode = stockCode.substring(2) + SH_EXCHANGE;
		} else {
			stockCode = stockCode.substring(2) + SZ_EXCHANGE;
		}

		String url = "https://api.wmcloud.com:443/data/v1/api/market/getBarRTIntraDay.json?securityID=" + stockCode
				+ "&startTime=&endTime=&unit=1";
		JSONObject jsonObject = JSONObject.fromObject(ConnectionHelper.request(url));
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

		return new TimeSharingPO(date, jsonObject.getDouble("closePrice"), jsonObject.getLong("totalVolume"),
				jsonObject.getDouble("totalValue"));
	}

}
