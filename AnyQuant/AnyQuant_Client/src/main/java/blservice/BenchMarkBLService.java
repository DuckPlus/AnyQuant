package blservice;

import java.util.Iterator;

import vo.BenchMarkVO;
import enumeration.MyDate;

/**
 *
 * @author czq
 * @date 2016年3月10日
 */
public interface BenchMarkBLService {
	/**
	 * 获得某只大盘最近一个月的所有数据
	 * @param BenchMarkCode
	 * @return  最近一个月数据集合的迭代器,如果该大盘不存在，返回null
	 */
	public Iterator<BenchMarkVO> getRecentBenchMarks(String BenchMarkCode);
	/**
	 * 获得某只大盘某段时间内的所有数据
	 * @param BenchMarkCode
	 * @param start
	 * @param end
	 * @return 如果该大盘不存在，返回null
	 */
	public Iterator<BenchMarkVO> getBenchMarkByTime(String BenchMarkCode, MyDate start,
			MyDate end);
}
