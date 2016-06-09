package data.update.impl;



import data.update.OptionalStockDataService;
import data.update.StockDataService;
import util.update.helper.CacheHelper;
import util.update.helper.FileIOHelper;
import util.update.po.StockPO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author ss
 * @date 2016年4月7日
 */
public class OptionalStockDSImpl implements OptionalStockDataService {
	private static OptionalStockDSImpl optionalStockDSImpl;
	StockDataService stockDSImplDataService = StockDSImpl.getStockDSImpl() ;
	private static String optionalCodesFilePath = "data//OptionalStocks.txt";
	private static List<StockPO> pos;
	private OptionalStockDSImpl() {
		 initOptionalStockList();
	}

	public static OptionalStockDataService getOptionalStockDSImpl() {
		if (optionalStockDSImpl == null) {
			return new OptionalStockDSImpl();
		} else {
			return optionalStockDSImpl;
		}
	}



	private void  initOptionalStockList() {
		pos = new ArrayList<StockPO>();
		if (CacheHelper.needUpdate(true)) {
			List<String> codeStrings = getSelectedStockCodes();
			StockPO temp = null;
			for (String code : codeStrings) {
				if (code.length() > 2) {
					temp = stockDSImplDataService.getStockMes(code);
					pos.add(temp);
				}
			}
			FileIOHelper.writeOptionalStocks(pos);
		}else {
			pos = CacheHelper.getCacheStockPOs();
		}
	}



	@Override
	public Iterator<StockPO> getOptionalStocks() {
		return pos.iterator();
	}

	@Override
	public boolean deleteOptionalStock(String stockCode) {
		List<String> CodeStrings = getSelectedStockCodes();
		boolean result = CodeStrings.remove(stockCode);
		for (StockPO stockPO : pos) {
			if(stockPO.getCode().equals(stockCode)){
				pos.remove(stockPO);
				break;
			}
		}
		updateOptionalStocks(CodeStrings);

		return result;
	}

	@Override
	public boolean addOptionalStock(String stockCode) {
		List<String> CodeStrings = getSelectedStockCodes();
		if (!CodeStrings.contains(stockCode)) {
			CodeStrings.add(stockCode);
			pos.add(stockDSImplDataService.getStockMes(stockCode));
			updateOptionalStocks(CodeStrings);
			return true;
		}
		return false;
	}

	@Override
	public boolean clearOptionalStocks() {
		try {

			File file = new File(optionalCodesFilePath);
			file.delete();
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			FileWriter fileWritter = new FileWriter(file, false);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);

			bufferWritter.write("");

			bufferWritter.close();
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public List<String> getSelectedStockCodes() {
		return FileIOHelper.readFiles(optionalCodesFilePath);
	}


	private void updateOptionalStocks(List<String> CodeStrings){
		FileIOHelper.writeOptionalStocks(pos);
		FileIOHelper.writeDataToFile(CodeStrings, optionalCodesFilePath);
	}

}
