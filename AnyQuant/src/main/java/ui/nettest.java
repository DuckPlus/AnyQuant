package ui;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.codec.EncoderException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public class nettest {
    //创建http client
    private static CloseableHttpClient httpClient = createHttpsClient();
    private static final String ACCESS_TOKEN = "44a70d35d80240eaa3d9a66b0b090de5bef4c96914f39c4faa225b4570ee301c";
    public static void main(String[] args) throws IOException, EncoderException {
        //根据api store页面上实际的api url来发送get请求，获取数据
        String url = "https://api.wmcloud.com:443/data/v1" 
        + "/api/master/getSecTypeRegion.json?field="; //用来获得地域
//        + "/api/master/getSecType.json?field=";
        
        HttpGet httpGet = new HttpGet(url);
        //在header里加入 Bearer {token}，添加认证的token，并执行get请求获取json数据
        httpGet.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);
        System.out.println(body);
        Map<String, List<String>> regions = new HashMap<>(50);
        Map<String, String> provinces = new HashMap<>(50);
        Map<String, String> cities = new HashMap<>(3000);
        JSONObject jsonObject = JSONObject.fromObject(body);
        
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        int len = jsonArray.size();
       
        JSONObject js ;
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("Regions")));
        for (int i = 0; i < len; i++) {
        	js = JSONObject.fromObject(jsonArray.get(i));
			if (  (js.getInt("typeLevel")) == 5) {
				regions.put((String) js.get("typeID"), new ArrayList<>());
				provinces.put((String)js.get("typeID"), (String) js.get("typeName"));
			}else if( js.getInt("typeLevel") == 6){
				regions.get(js.get("parentID")).add((String) js.get("typeID"));
				cities.put((String)js.get("typeID"), (String) js.get("typeName"));
			}
		}
        for(Map.Entry<String, List<String>> temp : regions.entrySet()){
        		System.out.print(provinces.get(temp.getKey()));
        		List<String> tm2 = temp.getValue();
        		for (int i = 0; i < temp.getValue().size(); i++) {
					System.out.print(cities.get(tm2.get(i)));
					
					writer.write(cities.get(tm2.get(i)) + " " + provinces.get(temp.getKey()));
					writer.newLine();
					
				}
        		System.out.println();
        }
        
        
      writer.close();
        
        
        
    }
    //创建http client
    public static CloseableHttpClient createHttpsClient() {
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
        //因为java客户端要进行安全证书的认证，这里我们设置ALLOW_ALL_HOSTNAME_VERIFIER来跳过认证，否则将报错
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{x509mgr}, null);
            sslsf = new SSLConnectionSocketFactory(
                    sslContext,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }
}