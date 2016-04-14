package data.helper;

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

import data.StockDSImpl;
import dataservice.StockDataService;
import enumeration.MyDate;
import enumeration.StaticMessage;
import jnr.ffi.Struct.int16_t;
import po.BenchMarkPO;
import po.StockPO;
import util.MyTime;

/**
 *
 * @author ss
 * @date 2016年4月7日
 */
public class FileIOHelper {

	public static final String stockCodeFileName = "data//stockCode.txt";
	public static final String benchCodeFileName = "data//benchCode.txt";
	public static final String STOCK_CACHE_FILE_NAME = "cache//stockMes.txt";
	public static final String SELECTED_FILE = "cache//selected.txt";
	public static final String BENCHMARK_CACHE_FILE = "cache//benchMarkCache.txt";
	public static final String SPLIT_CHAR = ",";
	private static final String ENCODING = "utf-8";
	private static final char LINE_WRAPPER = '\n';
	private static final String FILE_POSTFIX = ".txt";
	private static final String BENCHMARK_PO = StaticMessage.BENCHMARK_PO;
	private static final String STOCK_PO = StaticMessage.STOCK_PO;
	private static StockDataService stockDSImpl = StockDSImpl.getStockDSImpl();
	// private static OptionalStockDataService optionalStockDSImpl =
	// OptionalStockDSImpl.getOptionalStockDSImpl();

	public static List<BenchMarkPO> readAllBenches() {
		List<String> data = readFiles(BENCHMARK_CACHE_FILE);
		List<BenchMarkPO> pos = new ArrayList<>(data.size() - 1);
		String[] attrs;
		BenchMarkPO po;
		for (int i = 1; i < data.size(); i++) {
			attrs = data.get(i).split(SPLIT_CHAR);
			po = new BenchMarkPO();
			assignValue(po, attrs, BENCHMARK_PO);
			pos.add(po);
		}
		return pos;
	}

	public static List<StockPO> readAllSelectedStocks() {
		return readStocks(SELECTED_FILE);
	}

	public static List<StockPO> readAllStocks() {
		return readStocks(STOCK_CACHE_FILE_NAME);
	}

	private static List<StockPO> readStocks(String fileName) {
		List<String> data = readFiles(fileName);
		List<StockPO> pos = new ArrayList<>(data.size() - 1);
		String[] attrs;
		StockPO po;
		for (int i = 1; i < data.size(); i++) {
			attrs = data.get(i).split(SPLIT_CHAR);
			po = new StockPO();
			assignValue(po, attrs, STOCK_PO);
			pos.add(po);
		}
		return pos;
	}

	public static void writeAllStockMes(List<StockPO> stocks) {
		List<String> strings = new ArrayList<>();
		strings.add(MyTime.getToDay().DateToString());
		for (StockPO stockPO : stocks) {
			if (stockPO != null) {
				strings.add(stockPO.MyToString(SPLIT_CHAR));
			}
		}
		writeDataToFile(strings, STOCK_CACHE_FILE_NAME);

	}

	public static void writeAllBenMes(List<BenchMarkPO> benchMarkPOs) {
		List<String> strings = new ArrayList<>(benchMarkPOs.size() + 1);
		strings.add(MyTime.getToDay().DateToString());
		for (BenchMarkPO po : benchMarkPOs) {
			System.out.println(po.getChange());
			strings.add(po.MyToString(SPLIT_CHAR));
			System.out.println(po.MyToString(SPLIT_CHAR));
		}
		writeDataToFile(strings, BENCHMARK_CACHE_FILE);
	}

	public static void writeOptionalStocks(List<StockPO> stocks) {
		List<String> strings = new ArrayList<>(stocks.size() + 1);
		strings.add(MyTime.getToDay().DateToString());
		for (StockPO stockPO : stocks) {
			strings.add(stockPO.MyToString(SPLIT_CHAR));
		}
		writeDataToFile(strings, SELECTED_FILE);

	}

	// 调用此方法来更新股票列表的数据
	public static void updateLatestStockMes() {
		try {

			String encoding = ENCODING;
			String filePath = STOCK_CACHE_FILE_NAME;
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
					List<String> stockCodes = readCode();

					for (int i = 0; i < stockCodes.size(); i++) {

						result.add(stockDSImpl.getStockMes(stockCodes.get(i)));

						System.out.println("finish " + i + " of " + stockCodes.size());
						System.out.println("Used Time :" + (System.currentTimeMillis() - time));
					}
					// 覆盖写入
					writeAllStockMes(result);
				} else {
					System.out.println("不需要更新文件");
				}

			} else {

				System.out.println("找不到" + STOCK_CACHE_FILE_NAME + ",创建新文件");
				List<StockPO> result = new ArrayList<>();
				List<String> stockCodes = readCode();

				for (String code : stockCodes) {
						result.add(stockDSImpl.getStockMes(code));
				}
				// 覆盖写入
				writeAllStockMes(result);
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
					bufferWritter.write(stocks.get(stocks.size() - 1).getDate() + LINE_WRAPPER);
					int i = 0;
					for (StockPO stock : stocks) {
						i++;
						String temp = stock.MyToString(SPLIT_CHAR) + LINE_WRAPPER;
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
		List<String> codes = readFiles(stockCodeFileName);
		System.out.println("共有" + codes.size() + "只股票");
		for (int i = 0; i < codes.size(); i++) {
			updateStockInfo(codes.get(i));

			System.out.println("已完成" + i + "/" + codes.size());
		}
	}

	// public static void updateSelectedStockInfo() {
	// List<String> selectedStocks =
	// optionalStockDSImpl.getSelectedStockCodes();
	// for (String code : selectedStocks) {
	// if (code != null && !code.equals("")) {
	// updateStockInfo(code);
	// }
	// }
	// }

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
		List<String> codes = readFiles(stockCodeFileName);
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
				bufferWritter.write(stocks.get(stocks.size() - 1).getDate() + LINE_WRAPPER);
				int i = 0;
				for (StockPO stock : stocks) {
					i++;
					String temp = stock.MyToString(SPLIT_CHAR) + LINE_WRAPPER;
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

	private static void assignValue(Object stock, String[] attrs, String className) {
		try {
			Class<?> c = Class.forName(className);
			Field[] fields = c.getDeclaredFields();

			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				if (fields[i].getType().getName().equalsIgnoreCase("double")) {
					fields[i].set(stock, Double.parseDouble(attrs[i]));
				} else if (fields[i].getType().getName().equalsIgnoreCase("long")) {
					fields[i].set(stock, Long.parseLong(attrs[i]));
				} else {
					fields[i].set(stock, attrs[i]);
				}
			}

		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException e) {
			System.err.println("赋值给StockPO出现异常==============================");
			e.printStackTrace();
		}
	}

	private static List<String> readCode() {
		try {
			String encoding = ENCODING;
			File file = new File(stockCodeFileName);

			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String temp = bufferedReader.readLine();
				String[] tempCodes = temp.split(SPLIT_CHAR);
				List<String> codes = Arrays.asList(tempCodes);
				read.close();
				return codes;
			} else {
				System.out.println("找不到" + stockCodeFileName);
				file.createNewFile();
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> readFiles(String filePath) {
		try {
			String encoding = ENCODING;
			File file = new File(filePath);

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
				System.out.println("找不到" + filePath);
				file.createNewFile();
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return null;
	}

	public static void writeDataToFile(List<String> codes, String filePath) {
		try {

			File file = new File(filePath);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			FileWriter fileWritter = new FileWriter(file, false);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			for (String code : codes) {
				bufferWritter.write(code + LINE_WRAPPER);
				// System.out.println("write: "+line);
			}
			bufferWritter.close();
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeDataToErrorLog(String code) {
		try {

			File file = new File("cache//errorCode.txt");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			FileWriter fileWritter = new FileWriter(file, true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);

			bufferWritter.write(code + LINE_WRAPPER);
			// System.out.println("write: "+line);

			bufferWritter.close();
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
