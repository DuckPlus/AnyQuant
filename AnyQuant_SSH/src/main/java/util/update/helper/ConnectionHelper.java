package util.update.helper;


import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import util.update.enumeration.API_TYPE;
import util.update.enumeration.StaticMessage;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author ss,Qiang
 * @date 2016年4月7日
 */
public class ConnectionHelper {
	
	private static final String API_PREFIX = "https://api.wmcloud.com:443/data/v1/";
	private static final String ZHUJIAO_API_PREFIX = "http://121.41.106.89:8010/api/";
	/**
	 * 存储API序列
	 */
	private static final Map<API_TYPE, List<String>>  API_Saver = new HashMap<>(30);
	
	static{
		API_Saver.put(API_TYPE.GET_BENCHMARK_MES, Arrays.asList(API_PREFIX +"api/market/getMktIdxd.json?field=" , "&beginDate=" , "&endDate=" , "&indexID=" , "&ticker=" , "&tradeDate="));
		API_Saver.put(API_TYPE.IS_TRADING_DAY, Arrays.asList(API_PREFIX +"api/master/getTradeCal.json?field=" ,  "&exchangeCD="  , "&beginDate=" , "&endDate=" ) );
		API_Saver.put(API_TYPE.GET_STOCKMES_AT_TIME, Arrays.asList(API_PREFIX +"api/market/getMktEqud.json?field=" ,"&beginDate=","&endDate=","&secID=","&ticker=" ,"&tradeDate=") );
//		API_Saver.put(API_TYPE.GET_STOCKMES_BETWEEN_TIME, Arrays.asList( "api/master/getTradeCal.json?field=" ,  "&exchangeCD="  , "&beginDate=" , "&endDate=" ) );
		API_Saver.put(API_TYPE.GET_TIMESAHRING, Arrays.asList(API_PREFIX +"api/market/getBarRTIntraDay.json?" ,"securityID=" ,  "&startTime=" , "&endTime=" , "&unit="  ) );
		API_Saver.put(API_TYPE.CHECK_IF_TRADING, Arrays.asList(API_PREFIX +"api/market/getMktEqud.json?field=&beginDate=&endDate=&secID=" , "&ticker=" , "&tradeDate=" ));

		API_Saver.put(API_TYPE.GET_StockBasicInfo, Arrays.asList(API_PREFIX +"api/equity/getEqu.json?field=&listStatusCD=&secID=&equTypeCD=A","&ticker=" ));
		API_Saver.put(API_TYPE.GET_FACTOR_BetweenDate, Arrays.asList(API_PREFIX +"api/market/getStockFactorsDateRange.json?field=&secID=&tradeDate=" ,"&ticker=","&beginDate=","&endDate="));


				/*================以下为助教API=======================================*/
		API_Saver.put(API_TYPE.GET_STOCKS_LIST, Arrays.asList(ZHUJIAO_API_PREFIX + "stocks/?" , "year"));
		API_Saver.put(API_TYPE.GET_STOCKS_LIST_WITH_EXCHANGE , Arrays.asList(ZHUJIAO_API_PREFIX + "stocks/?" , "year" ,"&exchange="));
		
	}
	
	/**
	 * 
	 * @param type
	 * @param param
	 * @return
	 */
	public static JSONObject requestAPI(API_TYPE type , String... param ){
		List<String> urls = API_Saver.get(type);
		StringBuffer buffer = new StringBuffer(urls.get(0));
		for (int i = 0; i < urls.size() - 1; i++) {
			buffer.append(urls.get(i+1) );
			buffer.append(param[i]);
		}
//		System.err.println(buffer.toString());
//		if(API_TYPE.GET_TIMESAHRING == type)
//		System.out.println(request(buffer.toString()));
		
		if(urls.get(0).startsWith(API_PREFIX)){
			return JSONObject.fromObject(request(buffer.toString()));
		}else{
			return JSONObject.fromObject(SendGET(buffer.toString(), ""));
		}
		
	}
	
	
	
	/**
	 *此方法用来建立url-connection并返回助教提供的API所提供的初始数据
	 */
	private static String SendGET(String url, String param) {
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
				StaticMessage.ACCESS_TOKEN;
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
			e.printStackTrace();
		}
		return result;
	}

	// 创建http client
	@SuppressWarnings("deprecation")
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
}
