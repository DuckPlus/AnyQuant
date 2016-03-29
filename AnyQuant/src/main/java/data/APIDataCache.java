package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.omg.CosNaming.NamingContextPackage.AlreadyBound;

import dataservice.APIInterface;
import enumeration.Exchange;
import enumeration.MyDate;
import jnr.ffi.Struct.int16_t;
import po.BenchMarkPO;
import po.StockPO;
import util.MyTime;

/**
 * 这个类在逻辑层和数据层交互时可以起到缓冲作用 具体实现：
 *
 * @author ss
 * @date 2016年3月6日
 */
public class APIDataCache implements APIInterface {

	private static String fileName1 = "cache//stockCode.txt";
	private static String fileName2 = "cache//stockMes.txt";
	APIInterface api = null;

	public APIDataCache(APIInterface api) {
		this.api = api;
		updateLatestStockMes();
		updateSelectedStockInfo();

	}

	@Override
	public List<String> getAllStocks() {
		return readAllCodes();
	}

	@Override
	public List<String> getAllStocks(int year) {
		// TODO Auto-generated method stub
		return api.getAllStocks(year);
	}

	@Override
	public List<String> getAllStocks(Exchange exchange) {
		// TODO Auto-generated method stub
		return api.getAllStocks(exchange);
	}

	@Override
	public List<String> getAllStocks(int year, Exchange exchange) {
		// TODO Auto-generated method stub
		return api.getAllStocks(year, exchange);
	}

	@Override
	public StockPO getStockMes(String stockCode) {
		// TODO Auto-generated method stub
		return api.getStockMes(stockCode);
	}

	@Override
	public List<StockPO> getStockMes(String stockCode, MyDate start, MyDate end) {
		// TODO Auto-generated method stub
		return api.getStockMes(stockCode, start, end);
	}

	@Override
	public List<StockPO> getAllStockMes() {
		// TODO Auto-generated method stub
		return readAllMes();
	}

	@Override
	public List<String> getAllBenchMarks() {
		return api.getAllBenchMarks();
	}

	@Override
	public BenchMarkPO getBenchMes(String benchCode) {
		// TODO Auto-generated method stub
		return api.getBenchMes(benchCode);
	}

	@Override
	public List<BenchMarkPO> getBenchMes(String benchCode, MyDate start, MyDate end) {
		// TODO Auto-generated method stub
		return api.getBenchMes(benchCode, start, end);
	}

	@Override
	public List<BenchMarkPO> getAllBenchMes() {
		// TODO Auto-generated method stub
		return api.getAllBenchMes();
	}

	private List<String> readAllCodes() {
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
				List<String> data = api.getAllStocks();
				writeAllCodes(data);
				return data;
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return null;
	}

	private void writeAllCodes(List<String> codes) {
		try {

			File file = new File(fileName1);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			FileWriter fileWritter = new FileWriter(file, true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			for (String line : codes) {
				bufferWritter.write(line + ',');
				// System.out.println("write: "+line);
			}
			bufferWritter.close();
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<StockPO> readAllMes() {
		try {
			String encoding = "utf-8";
			String filePath = fileName2;
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String temp = bufferedReader.readLine();
				String[] attrs = null;
				List<StockPO> result = new ArrayList<StockPO>();

				while ((temp = bufferedReader.readLine()) != null) {
					attrs = temp.split(",");
					StockPO stock = new StockPO(attrs[0], attrs[1], attrs[2], Double.parseDouble(attrs[3]),
							Double.parseDouble(attrs[4]), Double.parseDouble(attrs[5]), Double.parseDouble(attrs[6]),
							Double.parseDouble(attrs[7]), Double.parseDouble(attrs[8]), Long.parseLong(attrs[9]),
							Double.parseDouble(attrs[10]), Double.parseDouble(attrs[11]), Double.parseDouble(attrs[12]),
							Double.parseDouble(attrs[13]), Double.parseDouble(attrs[14]));
					result.add(stock);
				}
				read.close();
				return result;
			} else {
				System.out.println("找不到指定的文件,创建新文件");
				List<StockPO> result = new ArrayList<>();
				List<String> stockCodes = getAllStocks();

				for (String code : stockCodes) {

					result.add(api.getStockMes(code));

				}
				writeAllMes(result);
				return result;
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return null;
	}

	private void writeAllMes(List<StockPO> stocks) {
		try {

			File file = new File(fileName2);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			FileWriter fileWritter = new FileWriter(file, false);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			// 开头记录日期
			bufferWritter.write(stocks.get(0).getDate() + '\n');
			for (StockPO stock : stocks) {
				String temp = stock.MyToString(',') + '\n';
				// System.out.println("write: "+temp);
				bufferWritter.write(temp);
			}
			bufferWritter.close();
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 调用此方法来更新股票列表的数据
	public  void updateLatestStockMes() {
		try {

			String encoding = "utf-8";
			String filePath = fileName2;
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				// 先读取第一行
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String preDate = bufferedReader.readLine();
				read.close();
				// 判断是否需要更新
				if (needUpdate(preDate)) {
					System.out.println("更新stockMes.txt---------");
					List<StockPO> result = new ArrayList<>();
					List<String> stockCodes = getAllStocks();

					for (String code : stockCodes) {
						result.add(api.getStockMes(code));
					}
					// 覆盖写入
					writeAllMes(result);
				} else {
					System.out.println("不需要更新文件");
				}

			} else {

				System.out.println("找不到指定的文件,创建新文件");
				List<StockPO> result = new ArrayList<>();
				List<String> stockCodes = getAllStocks();

				for (String code : stockCodes) {
					result.add(api.getStockMes(code));
				}
				// 覆盖写入
				writeAllMes(result);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    //调用此方法来更新指定代码的股票信息
	public  void updateStockInfo(String code){
		try {

			String encoding = "utf-8";
			String filePath =  "cache//" + "stockInfo//" + code + ".txt";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				// 先读取第一行
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String preDate = bufferedReader.readLine();
				read.close();
				// 判断是否需要更新
				if (needUpdate(preDate)) {
                        System.out.println("更新 "+code+".txt");
						MyDate start = MyDate.getDateFromString("2006-01-01");
						MyDate end = MyTime.getToDay();
						ArrayList<StockPO> stocks = (ArrayList<StockPO>) api.getStockMes(code, start, end);
						// true = append file
						FileWriter fileWritter = new FileWriter(file, false);
						BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
						// 开头记录最后一个日期
						bufferWritter.write(stocks.get(stocks.size() - 1).getDate() + '\n');
						int i=0;
						for (StockPO stock : stocks) {
							i++;
							String temp = stock.MyToString(',') + '\n';
							// System.out.println("write: "+temp);
							bufferWritter.write(temp);
							System.out.println("create file No."+i);

						}
						bufferWritter.close();

				} else {
					System.out.println("不需要更新文件");
				}

			} else {

				System.out.println("找不到"+code+".txt"+",创建新文件");
                downloadStockInfo(code);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//调用此方法来更新全部代码的股票信息
	public  void updateAllStockInfo(){
             List<String> codes = api.getAllStocks();
             for(String  code:codes){
                     updateStockInfo(code);
             }
	}

	public void updateSelectedStockInfo(){
       List<String>  selectedStocks = api.getSelectedStockCodes();
       for(String code: selectedStocks){
    	   if(code!=null){
    	         updateStockInfo(code);
    	   }
       }
	}

 	private boolean needUpdate(String preDate) {
		// 如果昨天日期与上次纪录不符并且昨天不是周末
		MyDate yesterday = MyTime.getAnotherDay(-1);
		MyDate preMyDate = MyDate.getDateFromString(preDate);
		if (MyTime.ifEarlier(preMyDate, yesterday) && (!MyTime.isWeekend(yesterday))) {
			return false;
		}
		return false;
	}

	public void downloadAllStocks() {
		List<String> codes = api.getAllStocks();
		for (String code : codes) {
			downloadStockInfo(code);
		}
	}

	public void downloadStockInfo(String code) {
		String fileUrl = "cache//" + "stockInfo//" + code + ".txt";
		try {

			File file = new File(fileUrl);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
				System.out.println("create " + fileUrl);
				MyDate start = MyDate.getDateFromString("2006-01-01");
				MyDate end = MyTime.getToDay();
				ArrayList<StockPO> stocks = (ArrayList<StockPO>) api.getStockMes(code, start, end);
				// true = append file
				FileWriter fileWritter = new FileWriter(file, false);
				BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
				// 开头记录最后一个日期
				bufferWritter.write(stocks.get(stocks.size() - 1).getDate() + '\n');
				int i=0;
				for (StockPO stock : stocks) {
					i++;
					String temp = stock.MyToString(',') + '\n';
					// System.out.println("write: "+temp);
					bufferWritter.write(temp);
					System.out.println("create file No."+i);

				}
				bufferWritter.close();
			}else{
               System.out.println("file"+code+".txt allready exist");
			}

			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Iterator<StockPO> getOptionalStocks() {
		// TODO Auto-generated method stub
		return api.getOptionalStocks();
	}

	@Override
	public boolean deleteOptionalStock(String stockCode) {

		return  api.deleteOptionalStock(stockCode);
	}

	@Override
	public boolean addOptionalStock(String stockCode) {

		return api.addOptionalStock(stockCode);
	}

	@Override
	public List<String> getSelectedStockCodes() {

		return api.getSelectedStockCodes();
	}

	@Override
	public boolean clearOptionalStocks() {
		return false;
	}

}
