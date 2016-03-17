package blimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import po.BenchMarkPO;
import util.MyTime;
import vo.BenchMarkVO;
import blservice.BenchMarkBLService;
import businessLogicHelper.VOPOchange;
import dataservice.APIDataFactory;
import dataservice.APIInterface;
import enumeration.MyDate;

/**
 *
 * @author czq
 * @date 2016年3月10日
 */
public class BenchMarkBLImpl implements BenchMarkBLService {
	/**
	 * 大盘数据
	 */
	private List<BenchMarkVO> benchMarkVOs;
	private Map<String, BenchMarkVO> benchMap;
	private APIInterface APIDataSer;
	private static BenchMarkBLService bl;

	private BenchMarkBLImpl() {
		APIDataSer = APIDataFactory.getAPIDataService();
		List<String> benchCodes = APIDataSer.getAllBenchMarks();
		benchMarkVOs = new ArrayList<>(benchCodes.size());
		benchMap = new TreeMap<String, BenchMarkVO>();
		List<BenchMarkPO> benchMarkPOs = APIDataSer.getAllBenchMes();
		for (BenchMarkPO po : benchMarkPOs) {

			benchMap.put(po.getCode(), (BenchMarkVO) VOPOchange.POtoVO(po));
		}
		benchMarkVOs = new ArrayList<BenchMarkVO>(benchMap.values());
	}

	public static BenchMarkBLService getBenchMarkBLService() {
		if (bl == null) {
			bl = new BenchMarkBLImpl();
		}
		return bl;
	}

	@Override
	public Iterator<BenchMarkVO> getAllBenchMarks() {

		return benchMarkVOs.iterator();
	}

	@Override
	public Iterator<BenchMarkVO> getRecentBenchMarks(String BenchMarkCode) {
		return getBenchMarkByTime(BenchMarkCode, MyTime.getAnotherDay(-30),
				MyTime.getToDay());
	}

	@Override
	public Iterator<BenchMarkVO> getBenchMarkByTime(String BenchMarkCode,
			MyDate start, MyDate end) {
		List<BenchMarkPO> pos = APIDataSer.getBenchMes(BenchMarkCode, start,
				end);
		if (pos != null) {
			List<BenchMarkVO> result = new ArrayList<BenchMarkVO>(pos.size());
			for (BenchMarkPO benchMarkPO : pos) {
				result.add((BenchMarkVO) VOPOchange.POtoVO(benchMarkPO));
			}
			return result.iterator();
		} else {
			return null;
		}
	}

}
