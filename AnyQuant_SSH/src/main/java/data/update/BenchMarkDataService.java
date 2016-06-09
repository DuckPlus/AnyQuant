package data.update;



import util.MyDate;
import util.update.po.BenchMarkPO;

import java.util.List;

/**
 *
 * @author ss
 * @date 2016年4月7日
 */
public interface BenchMarkDataService {
	/**
	 * 返回所有可用的大盘的代号
	 * @return
	 */
	public List<String> getAllBenchMarks();

	/**
	 * 只需要传入大盘的代码例如"hs300"，返回当天的全部信息
	 *
	 * @param benchCode
	 * @return
	 */
	public BenchMarkPO getBenchMes(String benchCode);
	/**
	 * 增加了时间限制,可以查看某一天的大盘的数据
	 * @param benchCode
	 * @param date
	 * @return
	 */
	public BenchMarkPO getBenchMes(String benchCode, MyDate date);

	/**
	 * 增加了时间限制，可以查看某段时间内的大盘数据
	 *
	 * @param benchCode
	 * @param start
	 * @param end
	 * @return
	 */
	public List<BenchMarkPO> getBenchMes(String benchCode, MyDate start,
										 MyDate end);

    /**
     * 返回全部大盘的当日数据
     * @return
     */
	public List<BenchMarkPO>  getAllBenchMes();

}
