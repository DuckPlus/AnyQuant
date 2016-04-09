package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dataservice.OptionalStockDataService;
import dataservice.StockDataService;
import enumeration.MyDate;
import po.StockPO;
import util.MyTime;

/**
 *
 * @author ss
 * @date 2016年4月7日
 */
public class FileIOHelper {

	private static final String stockCodeFileName = "data//stockCode.txt";
	private static final String benchCodeFileName = "data//benchCode.txt";
	private static final String optionalCodesFilePath = "data//OptionalStocks.txt";
	private static final String stockMesFileName = "cache//stockMes.txt";
	private static final String SPLIT_CHAR = ",";
	private static final String ENCODING = "utf-8";
	private static final char  LINE_WRAPPER = '\n';
	private static final String FILE_POSTFIX = ".txt";
	private static StockDataService stockDSImpl = StockDSImpl.getStockDSImpl();
	private static OptionalStockDataService optionalStockDSImpl = OptionalStockDSImpl.getOptionalStockDSImpl();

	public static List<String> readAllCodes() {
		try {
			String encoding = ENCODING;
			String filePath = stockCodeFileName;
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = bufferedReader.readLine();
				List<String> result = Arrays.asList(lineTxt.split(SPLIT_CHAR));
				read.close();
				return result;
			} else {
				System.out.println("找不到指定的文件,创建新文件");
				List<String> data = stockDSImpl.getAllStocks();
				writeAllCodes(data);
				return data;
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> readAllBenches() {
		try {
			String encoding = ENCODING;
			String filePath = benchCodeFileName;
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				List<String> benchCodes = new ArrayList<>();
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					benchCodes.add(line);
				}
				read.close();
				return benchCodes;
			} else {
				System.out.println("找不到" + benchCodeFileName);
				return null;
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return null;
	}

	public static void writeAllCodes(List<String> codes) {
		try {

			File file = new File(stockCodeFileName);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			FileWriter fileWritter = new FileWriter(file, true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			for (String line : codes) {
				bufferWritter.write(line + SPLIT_CHAR);
				// System.out.println("write: "+line);
			}
			bufferWritter.close();
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<StockPO> readAllMes() {
		try {
			String encoding = ENCODING;
			String filePath = stockMesFileName;
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String temp = bufferedReader.readLine();
				String[] attrs = null;
				List<StockPO> result = new ArrayList<StockPO>();

				while ((temp = bufferedReader.readLine()) != null) {
					attrs = temp.split(SPLIT_CHAR);
					StockPO stock = new StockPO();
					//反射赋值
					assignValue(stock , attrs);
					
					result.add(stock);
				}
				read.close();
				return result;
			} else {
				System.out.println("找不到指定的文件,创建新文件");
				List<StockPO> result = new ArrayList<>();
				List<String> stockCodes = readAllCodes();

				for (String code : stockCodes) {

					result.add(stockDSImpl.getStockMes(code));

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

	

	public static void writeAllMes(List<StockPO> stocks) {
		try {

			File file = new File(stockMesFileName);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			FileWriter fileWritter = new FileWriter(file, false);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			// 开头记录日期
			bufferWritter.write(stocks.get(0).getDate() +LINE_WRAPPER);
			for (StockPO stock : stocks) {
				String temp = stock.MyToString(SPLIT_CHAR) +LINE_WRAPPER;
				// System.out.println("write: "+temp);
				bufferWritter.write(temp);
			}
			bufferWritter.close();
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public  static List<String> readSelectedStockCodes() {
		try {
			String encoding = ENCODING;
			File file = new File(optionalCodesFilePath);

			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String temp = "";
				List<String> codes = new ArrayList<>();
				while ((temp = bufferedReader.readLine()) != null) {
					codes.add(temp);
				}
				read.close();
				return codes;
			} else {
				System.out.println("找不到" + optionalCodesFilePath);
				file.createNewFile();
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return null;
	}

	public static void writeSelectedStockCodes(List<String> codes) {
		try {

			File file = new File(optionalCodesFilePath);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			FileWriter fileWritter = new FileWriter(file, false);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			for (String code : codes) {
				bufferWritter.write(code +LINE_WRAPPER);
				// System.out.println("write: "+line);
			}
			bufferWritter.close();
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 调用此方法来更新股票列表的数据
	public static void updateLatestStockMes() {
		try {

			String encoding = ENCODING;
			String filePath = stockMesFileName;
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
					long time = System.currentTimeMillis();
					
					
					List<StockPO> result = new ArrayList<>();
					List<String> stockCodes = readAllCodes();
					
					for (int i = 0 ; i< stockCodes.size() ; i++) {
						
						result.add(stockDSImpl.getStockMes(stockCodes.get(i)));
						
						System.out.println("finish " + i + " of "  + stockCodes.size());
						System.out.println("Used Time :" +   (System.currentTimeMillis() - time) );
					}
					// 覆盖写入
					writeAllMes(result);
				} else {
					System.out.println("不需要更新文件");
				}

			} else {

				System.out.println("找不到" + stockMesFileName + ",创建新文件");
				List<StockPO> result = new ArrayList<>();
				List<String> stockCodes = readAllCodes();

				for (String code : stockCodes) {
					result.add(stockDSImpl.getStockMes(code));
				}
				// 覆盖写入
				writeAllMes(result);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 调用此方法来更新指定代码的股票信息
	public static void updateStockInfo(String code) {
		try {

			String encoding = ENCODING;
			String filePath = "cache//" + "stockInfo//" + code + FILE_POSTFIX;
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				// 先读取第一行
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String preDate = bufferedReader.readLine();
				read.close();
				// 判断是否需要更新
				if (needUpdate(preDate)) {
					System.out.println("更新 " + code + FILE_POSTFIX);
					MyDate start = MyDate.getDateFromString("2006-01-01");
					MyDate end = MyTime.getToDay();
					ArrayList<StockPO> stocks = (ArrayList<StockPO>) stockDSImpl.getStockMes(code, start, end);
					// true = append file
					FileWriter fileWritter = new FileWriter(file, false);
					BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
					// 开头记录最后一个日期
					bufferWritter.write(stocks.get(stocks.size() - 1).getDate() +LINE_WRAPPER);
					int i = 0;
					for (StockPO stock : stocks) {
						i++;
						String temp = stock.MyToString(SPLIT_CHAR) +LINE_WRAPPER;
						// System.out.println("write: "+temp);
						bufferWritter.write(temp);
						System.out.println("create file No." + i);

					}
					bufferWritter.close();

				} else {
					System.out.println("不需要更新文件");
				}

			} else {

				System.out.println("找不到" + code + FILE_POSTFIX + ",创建新文件");
				downloadStockInfo(code);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 调用此方法来更新全部代码的股票信息
	public static void updateAllStockInfo() {
		List<String> codes = readAllCodes();
		System.out.println("共有"  +  codes.size() + "只股票");
		for (int i = 0 ; i < codes.size() ; i ++) {
			updateStockInfo(codes.get(i));
			
			System.out.println("已完成" +i +  "/" + codes.size());
		}
	}

	public  static void updateSelectedStockInfo() {
		List<String> selectedStocks = optionalStockDSImpl.getSelectedStockCodes();
		for (String code : selectedStocks) {
			if (code != null && !code.equals("")) {
				updateStockInfo(code);
			}
		}
	}

	public static boolean needUpdate(String preDate) {
		// 如果昨天日期与上次纪录不符并且昨天不是周末
		MyDate yesterday = MyTime.getAnotherDay(-1);
		MyDate preMyDate = MyDate.getDateFromString(preDate);
		if (MyTime.ifEarlier(preMyDate, yesterday) && (!MyTime.isWeekend(yesterday))) {
			return true;
		}
		return false;
	}

	public static void downloadAllStocks() {
		List<String> codes = readAllCodes();
		for (String code : codes) {
			downloadStockInfo(code);
		}
	}

	public static void downloadStockInfo(String code) {
		String fileUrl = "cache//" + "stockInfo//" + code + FILE_POSTFIX;
		try {

			File file = new File(fileUrl);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
				System.out.println("create " + fileUrl);
				MyDate start = MyDate.getDateFromString("2006-01-01");
				MyDate end = MyTime.getToDay();
				ArrayList<StockPO> stocks = (ArrayList<StockPO>) stockDSImpl.getStockMes(code, start, end);
				// true = append file
				FileWriter fileWritter = new FileWriter(file, false);
				BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
				// 开头记录最后一个日期
				bufferWritter.write(stocks.get(stocks.size() - 1).getDate() +LINE_WRAPPER);
				int i = 0;
				for (StockPO stock : stocks) {
					i++;
					String temp = stock.MyToString(SPLIT_CHAR) +LINE_WRAPPER;
					// System.out.println("write: "+temp);
					bufferWritter.write(temp);
					System.out.println("create file No." + i);

				}
				bufferWritter.close();
			} else {
				System.out.println("file" + code + ".txt allready exist");
			}

			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		updateLatestStockMes();
	}
	
	
	private static void assignValue(StockPO stock, String[] attrs) {
		
		
		
		try {
			Class<?> c = Class.forName("po.StockPO");
			Field[] fields = c.getDeclaredFields();
			
			for (int i = 0 ; i < fields.length ; i++) {
				fields[i].setAccessible(true);
				if(fields[i].getType().getName().equalsIgnoreCase("double")){
						fields[i].set(stock, Double.parseDouble(attrs[i]));
				}else if(fields[i].getType().getName().equalsIgnoreCase("long")){
						fields[i].set(stock, Long.parseLong(attrs[i]));
				}else{
					fields[i].set(stock, attrs[i]);
				}
				
				
			}
			
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException e) {
			System.err.println("赋值给StockPO出现异常==============================");
			e.printStackTrace();
		}
		
		
	
}
}
