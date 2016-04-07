package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import dataservice.APIInterface;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import po.BenchMarkPO;
import po.StockPO;
import po.TimeSharingPO;
import util.MyTime;
import enumeration.Exchange;
import enumeration.MyDate;

/**
 * API接口的实现类
 * @author shishuo
 * 2016/03/02
 */
public class APIInterfaceImpl implements APIInterface{

	 private static  APIInterfaceImpl apiInterfaceImpl;
	 private static Map<String ,String[]> IndustryLocationMap;
	 private static String nameFilePath = "data//StockIndustries&Regions.txt";
	 private static String optionalCodesFilePath = "data//OptionalStocks.txt";
	 private APIInterfaceImpl(){
		  SetMapUp();
	 }

	 private void SetMapUp(){
		 IndustryLocationMap = new  HashMap<>();
		 try {
             String encoding="utf-8";
             File file=new File(nameFilePath);
             if(file.isFile() && file.exists()){ //判断文件是否存在

                      InputStreamReader read = new InputStreamReader(
                                                                     new FileInputStream(file),encoding);//考虑到编码格式
                      BufferedReader bufferedReader = new BufferedReader(read);
                      String temp="";
                      while( (temp=bufferedReader.readLine())!=null){
                    	    String [] codeAndMes = temp.split(",");
                    	    String[] industryAndLocationStrings =  new String [2] ;
                    	    industryAndLocationStrings[0]=codeAndMes[1];
                    	    industryAndLocationStrings[1]=codeAndMes[2];
                    	    IndustryLocationMap.put(codeAndMes[0], industryAndLocationStrings);
                      }
                      read.close();

             }else{
                       System.out.println("找不到"+nameFilePath);
                       file.createNewFile();
             }
       } catch (Exception e) {
              System.out.println("读取文件内容出错");
              e.printStackTrace();
       }

	 }

	 public static APIInterface getAPIInterfaceImpl(){
		 if(apiInterfaceImpl==null){
			 return new APIInterfaceImpl();
		 }else{
			 return apiInterfaceImpl;
		 }
	 }



	/**
	 *此方法用来建立url-connection并返回助教提供的API所提供的初始数据
	 */
	private String SendGET(String url, String param) {
		String result = "";// 访问返回结果
		BufferedReader read = null;// 读取访问结果
		int times=0;
		while (result.equals("")&&times<3) {

			try {
				// 创建url
				URL realurl = new URL(url);
				// 打开连接
				URLConnection connection = realurl.openConnection();
				// 设置通用的请求属性
				connection.setRequestProperty("accept", "*/*");
				connection.setRequestProperty("connection", "Keep-Alive");
				connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
				connection.setRequestProperty("X-Auth-Code", "0d4cb00bb416d44daea6fb74f5bfcfc2");
				// 建立连接
				connection.connect();
				// // 获取所有响应头字段
				// Map<String, List<String>> map = connection.getHeaderFields();
				// // 遍历所有的响应头字段，获取到cookies等
				// for (String key : map.keySet()) {
				// System.out.println(key + "--->" + map.get(key));
				// }
				// 定义 BufferedReader输入流来读取URL的响应
				read = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String line;// 循环读取
				while ((line = read.readLine()) != null) {
					result += line;
				}
			} catch (IOException e) {
				 e.printStackTrace();
			} finally {
				if (read != null&&(!result.equals(""))) {// 关闭流
					try {
						read.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
//					 System.out.println("request successfully");
				}else{
					 times++;
			         System.out.println("request failed , try again ,times : "+times);
				}
			}

		}

        if(result.equals("")){
        	System.out.println("request failed fainally");
        }
		return result;
	}
	/**
	 *此方法用来建立url-connection并返回通联API所提供的初始数据
	 */
    private static String request(String url) {
		final String ACCESS_TOKEN =
				"44a70d35d80240eaa3d9a66b0b090de5bef4c96914f39c4faa225b4570ee301c";
		CloseableHttpClient httpClient = createHttpsClient();
		HttpGet httpGet = new HttpGet(url);
		// 在header里加入 Bearer {token}，添加认证的token，并执行get请求获取json数据
		httpGet.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
		CloseableHttpResponse response;
		HttpEntity entity = null;
		String result = "";
		try {
			response = httpClient.execute(httpGet);
			entity = response.getEntity();
			result = EntityUtils.toString(entity);
	//		System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// 创建http client
	private static CloseableHttpClient createHttpsClient() {
		X509TrustManager x509mgr = new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] xcs, String string) {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] xcs, String string) {
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		// 因为java客户端要进行安全证书的认证，这里我们设置ALLOW_ALL_HOSTNAME_VERIFIER来跳过认证，否则将报错
		SSLConnectionSocketFactory sslsf = null;
		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, new TrustManager[] { x509mgr }, null);
			sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return HttpClients.custom().setSSLSocketFactory(sslsf).build();
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

		if(year<2007||year>2015){
			year =2015;
		}
		String url = "http://121.41.106.89:8010/api/stocks/?year="+year;
		//System.out.println(SendGET(url, ""));
		JSONObject jo = JSONObject.fromObject(SendGET(url, ""));
		JSONArray ja = jo.getJSONArray("data");
		int length = ja.size();
	    ArrayList<String> stockCode = new  ArrayList<>();
		for(int i=0;i<length;i++){
			JSONObject tempJo = ja.getJSONObject(i);


			if(tempJo.getString("name").equals("sh000300")){
				         continue;
			}
		    stockCode.add(tempJo.getString("name")) ;
		}
		return stockCode;
	}
	 /**
       * 默认返回2015年
       */
	public List<String> getAllStocks(Exchange exchange) {

		return  getAllStocks(2015, exchange);
	}

	public List<String> getAllStocks(int year, Exchange exchange) {

		String exchangeStr = "";
		if(exchange==Exchange.sh){
			exchangeStr="sh";
		}else {
			exchangeStr="sz";
		}
		String url = "http://121.41.106.89:8010/api/stocks/?year="+year+"&exchange="+exchangeStr ;
		//System.out.println(SendGET(url, ""));
		JSONObject jo = JSONObject.fromObject(SendGET(url, ""));
		JSONArray ja = jo.getJSONArray("data");
		int length = ja.size();
	    ArrayList<String> stockCode = new  ArrayList<>();
		for(int i=0;i<length;i++){
			JSONObject tempJo = ja.getJSONObject(i);

			if(tempJo.getString("name").equals("sh000300")){
				         continue;
			}
		    stockCode.add(tempJo.getString("name")) ;
		   // System.out.println(tempJo.getString("name"));
		}
		return stockCode;
	}

  /**
   * 获取指定代码的股票的最新数据
   *
   */
	public StockPO getStockMes(String stockCode) {
		  int offset=0;
		  MyDate date  = MyTime.getAnotherDay(offset);
		  while(getStockMesRequestResult(stockCode, date)!=1){
			  offset--;
			  date=MyTime.getAnotherDay(offset);

		  }
         return  getStockMes(stockCode, date);
	}

   /**
    * 获取指定代码的股票的在指定日期的数据
    */
	public StockPO getStockMes(String code, MyDate date) {

        String shortCode = code.substring(2);
		String tradeDateString=date.DateToStringSimple();
		String url = "https://api.wmcloud.com:443/data/v1"
				+ "/api/market/getMktEqud.json?field=&beginDate=&endDate=&secID=&ticker="+shortCode+"&tradeDate="+tradeDateString ;
	    String result = request(url);
	    JSONObject jo = JSONObject.fromObject(result);
	    if(jo.getInt("retCode")==1){
	       JSONArray jArray = jo.getJSONArray("data");
	       JSONObject  stockpoJsonObject = jArray.getJSONObject(0);
           StockPO po= JSONObjectToStockPO(stockpoJsonObject);
           return po;
	    }else{
           return new StockPO();
	    }

	}

	private StockPO JSONObjectToStockPO(JSONObject jo){
		StockPO po = new StockPO();
		po.setDate(jo.getString("tradeDate")); po.setName(jo.getString("secShortName"));

		String code=jo.getString("ticker");
		if(code.startsWith("6")){
			code="sh"+code;
		}else{
			code="sz"+code;
		}
		po.setCode(code);

		po.setHigh(jo.getDouble("highestPrice"));  	po.setLow(jo.getDouble("lowestPrice"));   po.setOpen(jo.getDouble("openPrice"));
		po.setClose(jo.getDouble("closePrice"));  po.setPreClose(jo.getDouble("preClosePrice"));  po.setTurnoverVol(jo.getLong("turnoverVol"));
		po.setTurnoverValue(jo.getDouble("turnoverValue"));  po.setTurnoverRate(jo.getDouble("turnoverRate"));   po.setPb(jo.getDouble("PB"));
		po.setPe(jo.getDouble("PE"));  po.setAccAdjFactor(jo.getDouble("accumAdjFactor"));  po.setCirMarketValue(jo.getDouble("negMarketValue"));
		po.setTotalMarketValue(jo.getDouble("marketValue"));
		po.computeAmplitude();  po.computeChangeRate();

		String [] industryAndLoc = IndustryLocationMap.get(code);
		po.setBoard(industryAndLoc[0]);   po.setRegion(industryAndLoc[1]);
		return po;
	}


	private BenchMarkPO JSONObjectToBenchMarkPO(JSONObject jo){
		BenchMarkPO po = new BenchMarkPO();
		po.setDate(jo.getString("tradeDate")); po.setName(jo.getString("secShortName"));
        po.setCode(jo.getString("ticker"));     po.setHigh(jo.getDouble("highestIndex"));  	po.setLow(jo.getDouble("lowestIndex"));   po.setOpen(jo.getDouble("openIndex"));
		po.setClose(jo.getDouble("closeIndex"));  po.setPreclose(jo.getDouble("preCloseIndex"));  po.setTurnoverVol(jo.getLong("turnoverVol"));
		po.setTurnoverValue(jo.getDouble("turnoverValue"));  po.setChange(jo.getDouble("CHG"));     po.setChangePct(jo.getDouble("CHGPct"));
		return po;
	}

	  /**
	   * 根据日期获取指定代码的股票的数据
	   *
	   */
	public List<StockPO> getStockMes(String code, MyDate start, MyDate end) {

		if( end.DateToString().equals( MyTime.getToDay().DateToString() )  ){
			List<StockPO> stocks = new ArrayList<>();
			stocks.add(getStockMes(code,start));
			return  stocks;
		}
		String shortCode = code.substring(2);
		List<StockPO> pos = new ArrayList<>();
		String url = "https://api.wmcloud.com:443/data/v1"
				+ "/api/market/getMktEqud.json?field=&beginDate="+start.DateToStringSimple()+"&endDate="+end.DateToStringSimple() +"&secID=&ticker="+shortCode+"&tradeDate=";
	    String result = request(url);
	    JSONObject jo = JSONObject.fromObject(result);
	    if(jo.getInt("retCode")==1){
	       JSONArray jArray = jo.getJSONArray("data");
	       for(int i=0;i<jArray.size();i++){
	    	   JSONObject  stockpoJsonObject = jArray.getJSONObject(0);
	           StockPO po= JSONObjectToStockPO(stockpoJsonObject);
	           pos.add(po);
	       }
           return pos;
	    }else{
           return new ArrayList<>();
	    }
	}

	//不确定请求的股票信息在某日是否有数据，返回请求结果1为有，-1为没有
	private  int    getStockMesRequestResult(String code, MyDate date){
		String shortCodeString = code.substring(2);
		String tradeDateString=date.DateToStringSimple();
		String url = "https://api.wmcloud.com:443/data/v1"
				+ "/api/market/getMktEqud.json?field=&beginDate=&endDate=&secID=&ticker="+shortCodeString+"&tradeDate="+tradeDateString ;
	    String result = request(url);
	    JSONObject jo = JSONObject.fromObject(result);
	    return  jo.getInt("retCode");
	}

	//不确定请求的大盘信息在某日是否有数据，返回请求结果1为有，-1为没有
		private  int    getBenchMesRequestResult(String benchCode, MyDate date){
			String tradeDateString=date.DateToStringSimple();
			String url = "https://api.wmcloud.com:443/data/v1"
					+ "/api/market/getMktIdxd.json?field=&beginDate=&endDate=&indexID=&ticker="+benchCode+"&tradeDate="+tradeDateString ;
		    String result = request(url);
		    JSONObject jo = JSONObject.fromObject(result);
		    return  jo.getInt("retCode");
		}

	/**
	 * 获取所有股票的最新信息列表
	 * 在APIDataCache中实现
	 */
	@Override
	public List<StockPO> getAllStockMes() {
		// TODO Auto-generated method stub
		return null;
	}






	//获得最新的大盘数据
	@Override
	public BenchMarkPO getBenchMes(String benchCode) {
		  int offset=0;
		  MyDate date  = MyTime.getAnotherDay(offset);
		  while(getBenchMesRequestResult(benchCode, date)!=1){
			  offset--;
			  date=MyTime.getAnotherDay(offset);
		  }
         return  getBenchMes(benchCode, date);
	}


	@Override
	public BenchMarkPO getBenchMes(String benchCode, MyDate date) {
		String tradeDateString=date.DateToStringSimple();
		String url = "https://api.wmcloud.com:443/data/v1"
				+ "/api/market/getMktIdxd.json?field=&beginDate=&endDate=&indexID=&ticker="+benchCode+"&tradeDate="+tradeDateString ;
	    String result = request(url);
	    JSONObject jo = JSONObject.fromObject(result);
	    if(jo.getInt("retCode")==1){
	       JSONArray jArray = jo.getJSONArray("data");
	       JSONObject  benJsonObject = jArray.getJSONObject(0);
	       BenchMarkPO po= JSONObjectToBenchMarkPO(benJsonObject);
           return po;
	    }else{
           return new BenchMarkPO();
	    }
	}


	@Override
	public List<BenchMarkPO> getBenchMes(String benchCode, MyDate start, MyDate end) {
		if(  start.DateToString().equals(end.DateToString()) ){
			List<BenchMarkPO> benchMarkPOs = new ArrayList<>();
			benchMarkPOs.add(getBenchMes(benchCode,start));
			return  benchMarkPOs;
		}

		List<BenchMarkPO> pos = new ArrayList<>();
		String url = "https://api.wmcloud.com:443/data/v1"
				+ "/api/market/getMktIdxd.json?field=&beginDate="+start.DateToStringSimple()+"&endDate="+end.DateToStringSimple()+"&indexID=&ticker="+benchCode+"&tradeDate=" ;
	    String result = request(url);
	    JSONObject jo = JSONObject.fromObject(result);
	    if(jo.getInt("retCode")==1){
	       JSONArray jArray = jo.getJSONArray("data");
	       for(int i=0;i<jArray.size();i++){
	    	   JSONObject  stockpoJsonObject = jArray.getJSONObject(0);
	           BenchMarkPO po= JSONObjectToBenchMarkPO(stockpoJsonObject);
	           pos.add(po);
	       }
           return pos;
	    }else{
           return new ArrayList<>();
	    }

	}





	/**
	 * 返回所有大盘代码,目前只有hs300
	 */
	@Override
	public List<String> getAllBenchMarks() {
				List <String> list =  new ArrayList<>();
				list.add("hs300");
				return list;
	}

	@Override
	public List<BenchMarkPO> getAllBenchMes() {
		return null;
	}

	@Override
	public Iterator<StockPO> getOptionalStocks() {
		// TODO Auto-generated method stub
		List<StockPO> pos = new ArrayList<StockPO>();
		List<String> codeStrings =getSelectedStockCodes() ;
		StockPO temp = null;
        for(String code: codeStrings){
        	   if(code.length()>2){
        		   System.out.println(code);
                    temp= getStockMes(code);
                    pos.add(temp);
        	   }
        }
		return  pos.iterator();
	}

	@Override
	public boolean deleteOptionalStock(String stockCode) {
		// TODO Auto-generated method stub
		List<String>  CodeStrings = getSelectedStockCodes();
		boolean result =CodeStrings.remove(stockCode);
		writeSelectedStockCodes(CodeStrings);
		return result;
	}

	@Override
	public boolean addOptionalStock(String stockCode) {
		// TODO Auto-generated method stub
		List<String>  CodeStrings = getSelectedStockCodes();
		if(!CodeStrings.contains(stockCode)){
		     CodeStrings.add(stockCode);
		     writeSelectedStockCodes(CodeStrings);
		     return true;
		}
		return false;
	}


	public List<String> getSelectedStockCodes(){
		 try {
             String encoding="utf-8";
             File file=new File(optionalCodesFilePath);

             if(file.isFile() && file.exists()){ //判断文件是否存在
                      InputStreamReader read = new InputStreamReader(
                                                                     new FileInputStream(file),encoding);//考虑到编码格式
                      BufferedReader bufferedReader = new BufferedReader(read);
                      String temp="";
                      List<String> codes = new ArrayList<>();
                      while( (temp=bufferedReader.readLine())!=null){
                    	   codes.add(temp);
                      }
                      read.close();
                      return codes;
             }else{
                       System.out.println("找不到"+optionalCodesFilePath);
                       file.createNewFile();
             }
       } catch (Exception e) {
              System.out.println("读取文件内容出错");
              e.printStackTrace();
       }
         return null;
	}

	@Override
	public boolean clearOptionalStocks() {
		try{

	         File file =new File(optionalCodesFilePath);
	         file.delete();
	         //if file doesnt exists, then create it
	         if(!file.exists()){
	                file.createNewFile();
	         }

	         //true = append file
	         FileWriter fileWritter = new FileWriter(file,false);
	         BufferedWriter bufferWritter = new BufferedWriter(fileWritter);

	         bufferWritter.write("");

	         bufferWritter.close();
	         System.out.println("Done");
	    }catch(IOException e){
	            e.printStackTrace();
	    }
         return true;
	}


	private void writeSelectedStockCodes(List<String> codes){
		try{

	         File file =new File(optionalCodesFilePath);
	         //if file doesnt exists, then create it
	         if(!file.exists()){
	                file.createNewFile();
	         }

	         //true = append file
	         FileWriter fileWritter = new FileWriter(file,false);
	         BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	         for(String code : codes){
	        	 bufferWritter.write(code+'\n');
	        //	 System.out.println("write: "+line);
	         }
	         bufferWritter.close();
	         System.out.println("Done");
	    }catch(IOException e){
	            e.printStackTrace();
	    }
     }





	@Override
	public List<TimeSharingPO> geTimeSharingPOs(String stockCode) {
		String SH_EXCHANGE = ".XSHG";
		String SZ_EXCHANGE = ".XSHE";

		if(stockCode.startsWith("sh")){
			stockCode = stockCode.substring(2) + SH_EXCHANGE;
		}else{
			stockCode = stockCode.substring(2) + SZ_EXCHANGE;
		}


		String url = "https://api.wmcloud.com:443/data/v1/api/market/getBarRTIntraDay.json?securityID="+stockCode +"&startTime=&endTime=&unit=1";
		 JSONObject jsonObject = JSONObject.fromObject(request(url));
		 JSONArray jsonArray = jsonObject.getJSONArray("data");
		 JSONObject jsonObject2 = jsonArray.getJSONObject(0);
		jsonArray = jsonObject2.getJSONArray("barBodys");

		List<TimeSharingPO> pos = new ArrayList<>(jsonArray.size());
		for (int i = 0; i < jsonArray.size(); i++) {
			pos.add(makeTimeSharingPO(jsonArray.getJSONObject(i)));
		}



		return pos.isEmpty()? null : pos;
	}


	private TimeSharingPO makeTimeSharingPO(JSONObject jsonObject){
		MyDate date = MyTime.getToDay();

		String nowTime = jsonObject.getString("barTime");
		String[] split = nowTime.split(":");
 		date.setHour(Integer.parseInt(split[0]));
		date.setMin(Integer.parseInt(split[1]));

		return new TimeSharingPO(date, jsonObject.getDouble("closePrice"), jsonObject.getLong("totalVolume") , jsonObject.getDouble("totalValue"));
	}


	public static void main(String[] args) {
		APIInterface apiInterface  = new APIInterfaceImpl();
		List<TimeSharingPO> po = apiInterface.geTimeSharingPOs("sh600000");

		for (int i = 0; i < po.size(); i++) {
			System.out.println(po.get(i).nowPrice);
		}
	}

}
