package data.helper;


import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import util.StaticMessage;
import util.enumration.API_TYPE;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
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

	/**
	 * 存储API序列
	 */
	private static final Map<API_TYPE, List<String>>  API_Saver = new HashMap<>(30);
	
	static{
		API_Saver.put(API_TYPE.GET_RelatedNews, Arrays.asList
                (API_PREFIX +"api/subject/getNewsByTickers.json?field=&secID=&exchangeCD=&secShortName=",
						"&ticker=","&beginDate=","&endDate="));

        API_Saver.put(API_TYPE.GET_NewsInfo,Arrays.asList
                (API_PREFIX +"/api/subject/getNewsInfo.json?field=&",
                        "newsID="));
		API_Saver.put(API_TYPE.GET_TIMESAHRING, Arrays.asList(API_PREFIX +"api/market/getBarRTIntraDay.json?" ,"securityID=" ,  "&startTime=" , "&endTime=" , "&unit="  ) );
		API_Saver.put(API_TYPE.IS_TRADING_DAY, Arrays.asList(API_PREFIX +"api/master/getTradeCal.json?field=" ,  "&exchangeCD="  , "&beginDate=" , "&endDate=" ) );
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
		System.err.println(buffer.toString());
		System.out.println(request(buffer.toString()));

		return JSONObject.fromObject(request(buffer.toString()));

		
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
