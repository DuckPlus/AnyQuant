package util.update.helper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.EncoderException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpUtil {
	// 创建http client
	private static String fileName1 = "data//Industries.txt";
	private static String fileName2 = "cache//industry&loation.txt";
	private static String fileName3=  "cache//name.txt";
	private static String fileName4=  "data//Regions.txt";
	private static String fileName5=  "data//StockIndustries&Regions.txt";

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


	private static List<String> readAllCodes() {
		try {
			String encoding = "utf-8";
			String filePath = fileName1;
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = bufferedReader.readLine();
				List<String> result = Arrays.asList(lineTxt.split(","));
				read.close();
				return result;
			} else {
				System.out.println("找不到指定的文件,创建新文件");
				return null;
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return null;
	}

	private static void writeAllCodes(List<String> contents) {
		try {

			File file = new File(fileName2);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			FileWriter fileWritter = new FileWriter(file, true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			for (String line : contents) {
				bufferWritter.write(line + '\n');
				System.out.println("write: " + line);
			}
			bufferWritter.close();
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private static String getIndustryLocationByCode(String code) {
		String shortCode = code.substring(2);
		String url = "https://api.wmcloud.com:443/data/v1"
				+ "/api/master/getSecTypeRel.json?field=&typeID=&secID=&ticker=" + shortCode;
		String result = request(url);
		JSONObject jo = JSONObject.fromObject(result);
		if (jo.getInt("retCode") != -1) {
			JSONArray ja = jo.getJSONArray("data");

			String city = "", industry = "";
			for (int i = 0; i < ja.size(); i++) {

				JSONObject tempJo = ja.getJSONObject(i);

				if (tempJo.getString("typeID").length() == 18) {
					if (tempJo.getString("typeName").endsWith("市")) {
						city = tempJo.getString("typeName");
					} else {
						industry = tempJo.getString("typeName");
					}
				}
			}
			return code + "," + industry + "," + city;

		} else {
			return code;
		}
	}

	private static void createIndustryLocationFile() {
		List<String> codes = readAllCodes();
		List<String> contents = new ArrayList<String>();
		for (String code : codes) {
			String line = getIndustryLocationByCode(code);
			contents.add(line);
		}
		writeAllCodes(contents);
	}

	private static void updateIndustryLocation(){
		ArrayList<String>  lineStrings = new ArrayList<String>();
		ArrayList<String []>  region = new ArrayList<String []>();
		ArrayList<String []>  industry = new ArrayList<String []>();
		ArrayList<String> contentStrings = new ArrayList<String>();
		//建立lineString
		try {
			String encoding = "utf-8";
			String filePath = fileName2;
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String line;
			    while ( (line=bufferedReader.readLine())!=null) {
                      String [] temp = line.split(",");
                      if(temp.length==2){
                    	  line=line+"未知位置";
                      }
                      lineStrings.add(line);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件,创建新文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

//		for(String line:lineStrings){
//			System.out.println(line);
//		}

		//建立industry
		try {
			String encoding = "utf-8";
			String filePath = fileName1;
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String line;
			    while ( (line=bufferedReader.readLine())!=null) {
                      String [] temp = line.split(" ");   //temp[1]为父类
                  //    System.out.println(temp.length);
                      //System.out.println(temp[0]+"  "+temp[1]);
                      industry.add(temp);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件,创建新文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

		//建立region
				try {
					String encoding = "utf-8";
					String filePath = fileName4;
					File file = new File(filePath);
					if (file.isFile() && file.exists()) { // 判断文件是否存在
						InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
						BufferedReader bufferedReader = new BufferedReader(read);
						String line;
					    while ( (line=bufferedReader.readLine())!=null) {
		                      String [] temp = line.split(" ");   //temp[1]为父类
		                      region.add(temp);
						}
						read.close();
					} else {
						System.out.println("找不到指定的文件,创建新文件");
					}
				} catch (Exception e) {
					System.out.println("读取文件内容出错");
					e.printStackTrace();
				}

		for (String line : lineStrings) {
			String[] temp = line.split(",");
			if (temp.length == 3) {

				for (int i = 0; i < region.size(); i++) {
					String[] regionMap = region.get(i);
					if (regionMap[0].equals(temp[2])) {
						temp[2] = regionMap[1];
					}
				}
				for (int i = 0; i < industry.size(); i++) {
					String[] industryMap = industry.get(i);
					if (industryMap[0].equals(temp[1])) {
						temp[1] = industryMap[1];
					}
				}
				String resultString = temp[0] + "," + temp[1] + "," + temp[2];
				contentStrings.add(resultString);
			//	System.out.println(resultString);
			}
		}

		try {

			File file = new File(fileName5);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			FileWriter fileWritter = new FileWriter(file, true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			for (String line : contentStrings) {
				bufferWritter.write(line + '\n');
				System.out.println("write: " + line);
			}
			bufferWritter.close();
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	public static String request(String url) {
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
			System.out.println(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	//最新数据
	public static void getStockMes(String code){
		String shortCode = code.substring(2);
//		MyDate end = MyDate.getDateFromString("2016-03-28");
//		MyDate start = MyTime.getAnotherDay(end,-3);
//		String url = "https://api.wmcloud.com:443/data/v1"
//				+ "/api/market/getMktEqud.json?field=&beginDate=&endDate=&secID=&ticker="+shortCode+"&tradeDate="+end.DateToStringSimple() ;
//		String url = "https://api.wmcloud.com:443/data/v1"
//				+ "/api/market/getMktEqud.json?field=tradeDate&beginDate="+start.DateToStringSimple()+"&endDate="+end.DateToStringSimple() +"&secID=&ticker="+shortCode+"&tradeDate=";
		String url = "https://api.wmcloud.com:443/data/v1"
				+ "/api/market/getMktIdxd.json?field=&beginDate=&endDate=&indexID=&ticker="+"399000"+"&tradeDate="+20140406 ;
		String result = request(url);
	    JSONObject jo = JSONObject.fromObject(result);
		if (jo.getInt("retCode") != -1) {

		}

	}

	public static void getLocation(String code){

	    String shortCode = code.substring(2);
		String url = "https://api.wmcloud.com:443/data/v1"
				+ "/api/equity/getEqu.json?field=equType&listStatusCD=&secID=&ticker=&equTypeCD=A";
	    String result = request(url);
	    JSONObject jo = JSONObject.fromObject(result);
	}
	public static void main(String[] args) throws IOException, EncoderException {
		// 根据api store页面上实际的api url来发送get请求，获取数据
		getStockMes("sh600216");
	//	getLocation("sh600216");
	}

}
