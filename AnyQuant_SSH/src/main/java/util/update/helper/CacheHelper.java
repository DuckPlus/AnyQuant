package util.update.helper;



import util.DateCalculator;
import util.update.po.BenchMarkPO;
import util.update.po.StockPO;

import java.util.List;

/**
 * 缓存机制实现
 * 
 * @author Qiang
 * @date 2016年4月10日
 */
public class CacheHelper {

	public static boolean needUpdate(boolean selectedOrBench) {
		List<String> tmp;
		if (selectedOrBench) {
			tmp = FileIOHelper.readFiles(FileIOHelper.SELECTED_FILE);
		} else {
			tmp = FileIOHelper.readFiles(FileIOHelper.BENCHMARK_CACHE_FILE);
		}

		if (tmp == null || tmp.size() == 0 || !tmp.get(0).equals(DateCalculator.getToDay().DateToString())) {
			
			System.out.println("今天第一次读取，请耐心等待");
			
			return true;
		} else {
//			System.out.println("可直接使用缓存文件");
			return false;
		}

	}

	public static List<StockPO> getCacheStockPOs() {

		return FileIOHelper.readAllSelectedStocks();

	}

	public static List<BenchMarkPO> getCacheBenchPOs()
	{
		return FileIOHelper.readAllBenches();
	}

}
