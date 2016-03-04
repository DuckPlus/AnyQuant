package dataservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Time;
import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import enumeration.Exchange;
import enumeration.MyDate;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import po.StockCollectionPO;
import po.StockPO;
import util.MyTime;

/**
 * API接口的实现类
 * @author shishuo
 * 2016/03/02
 */
public class APIInterfaceImpl implements APIInterface{
    
	
	/**
	 *此方法用来建立url-connection并返回API所提供的全部初始数据 
	 */
	private  String SendGET(String url,String param){
		   String result="";//访问返回结果
		   BufferedReader read=null;//读取访问结果
		    
		   try {
		    //创建url
		    URL realurl=new URL(url);
		    //打开连接
		    URLConnection connection=realurl.openConnection();
		      //设置通用的请求属性
		             connection.setRequestProperty("accept", "*/*");
		             connection.setRequestProperty("connection", "Keep-Alive");
		             connection.setRequestProperty("user-agent",
		                     "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		             connection.setRequestProperty("X-Auth-Code", "0d4cb00bb416d44daea6fb74f5bfcfc2");
		             //建立连接
		             connection.connect();
//		          // 获取所有响应头字段
//		             Map<String, List<String>> map = connection.getHeaderFields();
//		             // 遍历所有的响应头字段，获取到cookies等
//		             for (String key : map.keySet()) {
//		                 System.out.println(key + "--->" + map.get(key));
//		             }
		             // 定义 BufferedReader输入流来读取URL的响应
		             read = new BufferedReader(new InputStreamReader(
		                     connection.getInputStream(),"UTF-8"));
		             String line;//循环读取
		             while ((line = read.readLine()) != null) {
		                 result += line;
		             }
		   } catch (IOException e) {
		            // e.printStackTrace();
		             return "";
			         
		   }finally{
		       if(read!=null){//关闭流
		             try {
		                  read.close();
		             } catch (IOException e) {
		                  e.printStackTrace();
		             }
		       }
		   }
		     
		   return result; 
		 }
    /**
     * 默认返回2015年，上海交易所
     */
	public List<String> getAllStocks() {
		// TODO Auto-generated method stub
		List<String > sh = getAllStocks(2015,Exchange.sh);
		List<String > sz = getAllStocks(2015,Exchange.sz);
		sh.addAll(sz);
		return sh;
	}
	  /**
       * 默认返回上海交易所
       */
	public List<String> getAllStocks(int year) {
		// TODO Auto-generated method stub
		if(year<2007||year>2015){
			year =2015;
		}
		List<String > sh = getAllStocks(year,Exchange.sh);
		List<String > sz = getAllStocks(year,Exchange.sz);
		sh.addAll(sz);
		return sh;
	}
	 /**
       * 默认返回2015年
       */
	public List<String> getAllStocks(Exchange exchange) {
		// TODO Auto-generated method stub
		return  getAllStocks(2015, exchange);
	}

	public List<String> getAllStocks(int year, Exchange exchange) {
		// TODO Auto-generated method stub
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
		    stockCode.add(tempJo.getString("name")) ;
		  // System.out.println(tempJo.getString("name"));
		}
		return stockCode;
	}


	public List<StockPO> getStockMes(String stockCode) {
		// TODO Auto-generated method stub
		return getStockMes(stockCode, MyTime.getToDay(),MyTime.getToDay());
	}
	

	public List<StockPO> getStockMes(String stockCode, MyDate start, MyDate end) {
		// TODO Auto-generated method stub
		String labels = "open+close+high+low+volume+turnover+pb";
		String startTime = start.DateToString();
		String endTime = end.DateToString();
		String url = "http://121.41.106.89:8010/api/stock/"+stockCode+"/?start="+startTime +"&end="+endTime+"&fields="+labels ;
		//System.out.println(SendGET(url, ""));
		String result="";
		JSONObject jo = JSONObject.fromObject(SendGET(url, ""));
		JSONObject data = jo.getJSONObject("data");
		
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("trading_info", StockPO.class);
		StockCollectionPO   stockCollection  =  (StockCollectionPO)  
				JSONObject.toBean(data,StockCollectionPO.class , classMap);
	    return stockCollection.getTrading_info();
		
	}


}
