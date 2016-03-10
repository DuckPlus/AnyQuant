package blimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import po.BenchMarkPO;
import po.StockPO;
import util.MyTime;
import vo.BenchMarkVO;
import vo.StockVO;
import enumeration.MyDate;
import blservice.BenchMarkBLService;
import dataservice.APIDataFactory;
import dataservice.APIInterface;

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
	private Map<String , BenchMarkVO> benchMap;
	private APIInterface APIDataSer; 
	
	public BenchMarkBLImpl() {
		// TODO Auto-generated constructor stub
		APIDataSer = APIDataFactory.getAPIDataService();
		List<String> benchCodes = APIDataSer.getAllBenchMarks();
		benchMarkVOs = new ArrayList<>(benchCodes.size());
		List<BenchMarkPO>  benchMarkPOs = APIDataSer.getAllBenchMes();
		for(BenchMarkPO po : benchMarkPOs){
			benchMap.put(po.getCode(), (BenchMarkVO) VOPOchange.POtoVO(po));
		}
		benchMarkVOs = new ArrayList<BenchMarkVO>(benchMap.values());
	}
	
	
	@Override
	public Iterator<BenchMarkVO> getAllBenchMarks() {
		
		return benchMarkVOs.iterator();
	}
	
	
	@Override
	public Iterator<BenchMarkVO> getRecentBenchMarks(String BenchMarkCode) {
		return getBenchMarkByTime(BenchMarkCode, MyTime.getAnotherDay(-30), MyTime.getToDay());
	}

	@Override
	public Iterator<BenchMarkVO> getBenchMarkByTime(String BenchMarkCode,
			MyDate start, MyDate end) {
		List<BenchMarkPO> pos = APIDataSer.getBenchMes(BenchMarkCode, start, end);
		if(pos != null){
			List<BenchMarkVO> result = new ArrayList<BenchMarkVO>(pos.size());
			for (BenchMarkVO benchMarkVO : result) {
				result.add((BenchMarkVO) VOPOchange.POtoVO(benchMarkVO));
			}
			return result.iterator();
		}else{
			return null;
		}
	}

}
