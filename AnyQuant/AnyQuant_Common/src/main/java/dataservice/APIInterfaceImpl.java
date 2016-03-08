package dataservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Templates;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import po.BenchMarkPO;
import po.StockPO;
import util.MyTime;
import enumeration.Exchange;
import enumeration.MyDate;

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
		
		List<String > sh = getAllStocks(2015,Exchange.sh);
		List<String > sz = getAllStocks(2015,Exchange.sz);
		sh.addAll(sz);
		return sh;
	}
	  /**
       * 默认返回上海交易所
       */
	public List<String> getAllStocks(int year) {
	
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
		
		 //当天可能没有数据
		 MyDate end = MyTime.getToDay();
		 MyDate  temp = MyTime.getFirstPreWookDay(end);
		 MyDate    start =   MyTime.getFirstPreWookDay(temp);
		 List<StockPO>  stocks = getManyMesFunc(stockCode, start, end);
        return  stocks.get(stocks.size()-1);
  
	}
	
	  /**
	   * 根据日期获取指定代码的股票的数据
	   * 
	   */
	public List<StockPO> getStockMes(String stockCode, MyDate start, MyDate end) {
		if( (end.DateToString().equals(MyTime.getToDay().DateToString()) )  && ( start.DateToString().equals(end.DateToString())||
				 MyTime.getAnotherDay(start,1 ).DateToString().equals(end.DateToString())  ) ){
			List<StockPO> stocks = new ArrayList<>();
			stocks.add(getStockMes(stockCode));
			return  stocks;
		}
		
		//拿到第一天的前一天有效数据
		start = MyTime.getFirstPreWookDay(start);
		
		if(getMesCount(stockCode, start, end)>=2){
			return  getManyMesFunc(stockCode, start, end);
		}else{
			start = MyTime.getFirstPreWookDay(start);
			return  getManyMesFunc(stockCode, start, end);
		}
		
	}
	
	
	//确保至少有两个数据再调用该方法，返回的数据不包含第一个（没有preclose）
	private List<StockPO>   getManyMesFunc(String stockCode, MyDate start , MyDate end){
		String labels = "open+close+high+low+volume+turnover+pb";
		String startTime = start.DateToString();
		String endTime = end.DateToString();
		String url = "http://121.41.106.89:8010/api/stock/"+stockCode+"/?start="+startTime +"&end="+endTime+"&fields="+labels ;
	    //System.out.println(SendGET(url, ""));
		JSONObject jo = JSONObject.fromObject(SendGET(url, ""));
		JSONObject data = jo.getJSONObject("data");
		JSONArray trading_info = data.getJSONArray("trading_info");
		//System.out.println("size: "+trading_info.size());
		List<StockPO> stocks =  new  ArrayList<>();
			for(int i=0;i<trading_info.size();i++){
				StockPO stock  = MyJSONObject.toBean(trading_info.getJSONObject(i), StockPO.class);
				stock.setCode(stockCode);
				stocks.add(stock);
			}
			
			for(int i=1;i<stocks.size();i++){
				stocks.get(i).setPreClose( stocks.get(i-1).getClose() );
				stocks.get(i).computeAmplitude();
			}
			stocks.remove(0);
		return stocks;
	}
	
	//不确定会有几个数据调用该方法，返回数据个数
	private  int    getMesCount(String stockCode, MyDate start , MyDate end){
		String labels = "open+close+high+low+volume+turnover+pb";
		String startTime = start.DateToString();
		String endTime = end.DateToString();
		String url = "http://121.41.106.89:8010/api/stock/"+stockCode+"/?start="+startTime +"&end="+endTime+"&fields="+labels ;
	    //System.out.println(SendGET(url, ""));
		JSONObject jo = JSONObject.fromObject(SendGET(url, ""));
		JSONObject data = jo.getJSONObject("data");
		JSONArray trading_info = data.getJSONArray("trading_info");
		//System.out.println("size: "+trading_info.size());
		return trading_info.size();
	}
	
	
	@Override
	public List<StockPO> getAllStockMes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public BenchMarkPO getBenchMes(String benchCode) {
		
		return null;
	}
	
	@Override
	public List<BenchMarkPO> getBenchMes(String benchCode, MyDate start, MyDate end) {
		String labels = "open+close+high+low+volume+adj_price";
		String startTime = start.DateToString();
		String endTime = end.DateToString();
		String url = "http://121.41.106.89:8010/api/benchmark/"+benchCode+"/?start="+startTime +"&end="+endTime+"&fields="+labels ;
	  //  System.out.println(SendGET(url, ""));
		JSONObject jo = JSONObject.fromObject(SendGET(url, ""));
		JSONObject data = jo.getJSONObject("data");
		JSONArray trading_info = data.getJSONArray("trading_info");
		List<BenchMarkPO> benchs =  new  ArrayList<>();
		for(int i=0;i<trading_info.size();i++){
			BenchMarkPO  bench  = MyJSONObject.toBean(trading_info.getJSONObject(i), BenchMarkPO.class);
			bench.setCode(benchCode);
			benchs.add(bench);
		}
		
		return benchs;
	}
	
	
	@Override
	public List<String> getAllBenchMarks() {
		
				List <String> list =  new ArrayList<>();
				list.add("hs300");
				return list;
	}

   

}
