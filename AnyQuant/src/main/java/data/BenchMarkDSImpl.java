package data;

import java.util.ArrayList;
import java.util.List;

import data.helper.CacheHelper;
import data.helper.ConnectionHelper;
import data.helper.FileIOHelper;
import data.helper.TransferHelper;
import data.helper.StockMesHelper;
import dataservice.BenchMarkDataService;
import enumeration.API_TYPE;
import enumeration.MyDate;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import po.BenchMarkPO;
import util.MyTime;

/**
 *
 * @author ss
 * @date 2016年4月7日
 */
public class BenchMarkDSImpl implements BenchMarkDataService {
	private static BenchMarkDSImpl benchMarkDSImpl;

	private BenchMarkDSImpl() {

	}

	public static BenchMarkDataService getBenchMarkDSImpl() {
		if (benchMarkDSImpl == null) {
			return new BenchMarkDSImpl();
		} else {
			return benchMarkDSImpl;
		}
	}

	@Override
	public List<String> getAllBenchMarks() {
		return FileIOHelper.readFiles(FileIOHelper.benchCodeFileName);
	}

	// 获得最新的大盘数据
	@Override
	public BenchMarkPO getBenchMes(String benchCode) {
		int offset = 0;
		MyDate date = MyTime.getAnotherDay(offset);
		while ((!checkIfHavaData(benchCode, date)) && offset > -10) {
			offset--;
			date = MyTime.getAnotherDay(offset);
		}

		if (offset < -10) {
			System.out.println(benchCode + "error-----------");
			return null;
		}
		return getBenchMes(benchCode, date);
	}

	@Override
	public BenchMarkPO getBenchMes(String benchCode, MyDate date) {
		String tradeDateString = date.DateToStringSimple();
		JSONObject jo = ConnectionHelper.requestAPI(API_TYPE.GET_BENCHMARK_MES, "", "", "", benchCode, tradeDateString);
		if (jo.getInt("retCode") == 1) {
			JSONArray jArray = jo.getJSONArray("data");
			JSONObject benJsonObject = jArray.getJSONObject(0);
			BenchMarkPO po = TransferHelper.JSONObjectToBenchMarkPO(benJsonObject);
			return po;
		} else {
			return new BenchMarkPO();
		}
	}

	@Override
	public List<BenchMarkPO> getBenchMes(String benchCode, MyDate start, MyDate end) {
		if (start.DateToString().equals(end.DateToString())) {
			List<BenchMarkPO> benchMarkPOs = new ArrayList<>();
			benchMarkPOs.add(getBenchMes(benchCode, start));
			return benchMarkPOs;
		}

		List<BenchMarkPO> pos = new ArrayList<>();
		JSONObject jo = ConnectionHelper.requestAPI(API_TYPE.GET_BENCHMARK_MES, start.DateToStringSimple(),
				end.DateToStringSimple(), "", benchCode, "");
		if (jo.getInt("retCode") == 1) {
			JSONArray jArray = jo.getJSONArray("data");
			for (int i = 0; i < jArray.size(); i++) {
				JSONObject stockpoJsonObject = jArray.getJSONObject(i);
				BenchMarkPO po = TransferHelper.JSONObjectToBenchMarkPO(stockpoJsonObject);
				pos.add(po);
			}
			return pos;
		} else {
			return new ArrayList<>();
		}

	}

	@Override
	public List<BenchMarkPO> getAllBenchMes() {
		
		
		if(CacheHelper.needUpdate(false)){
			List<BenchMarkPO> pos = new ArrayList<>();
			List<String> benchs = FileIOHelper.readFiles(FileIOHelper.benchCodeFileName);
			for (String benchCode : benchs) {
				BenchMarkPO po = this.getBenchMes(benchCode);
				pos.add(po);
			}
			FileIOHelper.writeAllBenMes(pos);
		}
		
		return CacheHelper.getCacheBenchPOs();
	}

	// 不确定请求的大盘信息在某日是否有数据，返回请求结果1为有，-1为没有
	private boolean checkIfHavaData(String benchCode, MyDate date) {
		return StockMesHelper.isTradeDay();
	}
}
