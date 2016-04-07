package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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

	private static String stockCodeFileName = "data//stockCode.txt";
	private static String benchCodeFileName = "data//benchCode.txt";
	private static String optionalCodesFilePath = "data//OptionalStocks.txt";
	private static String stockMesFileName = "cache//stockMes.txt";
	private static StockDataService stockDSImpl = StockDSImpl.getStockDSImpl();
	private static OptionalStockDataService optionalStockDSImpl = OptionalStockDSImpl.getOptionalStockDSImpl();

	public static List<String> readAllCodes() {
		try {
			String encoding = "utf-8";
			String filePath = stockCodeFileName;
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
			String encoding = "utf-8";
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
				bufferWritter.write(line + ',');
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
			String encoding = "utf-8";
			String filePath = stockMesFileName;
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String temp = bufferedReader.readLine();
				String[] attrs = null;
				List<StockPO> result = new ArrayList<StockPO>();

				while ((temp = bufferedReader.readLine()) != null) {
					attrs = temp.split(",");
					StockPO stock = new StockPO(attrs[0], attrs[1], attrs[2], attrs[3], attrs[4],
							Double.parseDouble(attrs[5]), Double.parseDouble(attrs[6]), Double.parseDouble(attrs[7]),
							Double.parseDouble(attrs[8]), Double.parseDouble(attrs[9]), Double.parseDouble(attrs[10]),
							Double.parseDouble(attrs[11]), Double.parseDouble(attrs[12]), Double.parseDouble(attrs[13]),
							Double.parseDouble(attrs[14]), Double.parseDouble(attrs[15]), Double.parseDouble(attrs[16]),
							Double.parseDouble(attrs[17]), Double.parseDouble(attrs[18]), Long.parseLong(attrs[19]));
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

	public  static List<String> readSelectedStockCodes() {
		try {
			String encoding = "utf-8";
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
				bufferWritter.write(code + '\n');
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

			String encoding = "utf-8";
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
					List<StockPO> result = new ArrayList<>();
					List<String> stockCodes = readAllCodes();

					for (String code : stockCodes) {
						result.add(stockDSImpl.getStockMes(code));
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

			String encoding = "utf-8";
			String filePath = "cache//" + "stockInfo//" + code + ".txt";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				// 先读取第一行
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String preDate = bufferedReader.readLine();
				read.close();
				// 判断是否需要更新
				if (needUpdate(preDate)) {
					System.out.println("更新 " + code + ".txt");
					MyDate start = MyDate.getDateFromString("2006-01-01");
					MyDate end = MyTime.getToDay();
					ArrayList<StockPO> stocks = (ArrayList<StockPO>) stockDSImpl.getStockMes(code, start, end);
					// true = append file
					FileWriter fileWritter = new FileWriter(file, false);
					BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
					// 开头记录最后一个日期
					bufferWritter.write(stocks.get(stocks.size() - 1).getDate() + '\n');
					int i = 0;
					for (StockPO stock : stocks) {
						i++;
						String temp = stock.MyToString(',') + '\n';
						// System.out.println("write: "+temp);
						bufferWritter.write(temp);
						System.out.println("create file No." + i);

					}
					bufferWritter.close();

				} else {
					System.out.println("不需要更新文件");
				}

			} else {

				System.out.println("找不到" + code + ".txt" + ",创建新文件");
				downloadStockInfo(code);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 调用此方法来更新全部代码的股票信息
	public static void updateAllStockInfo() {
		List<String> codes = readAllCodes();
		for (String code : codes) {
			updateStockInfo(code);
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
		String fileUrl = "cache//" + "stockInfo//" + code + ".txt";
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
				bufferWritter.write(stocks.get(stocks.size() - 1).getDate() + '\n');
				int i = 0;
				for (StockPO stock : stocks) {
					i++;
					String temp = stock.MyToString(',') + '\n';
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
}
