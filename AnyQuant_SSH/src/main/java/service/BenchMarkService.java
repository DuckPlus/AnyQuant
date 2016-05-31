package service;

import entity.BenchmarkEntity;
import entity.BenchmarkdataEntity;
import util.MyDate;

import java.util.List;

/**
 * 大盘逻辑层接口
 * @author Qiang
 * @date 2016年3月10日
 */
public interface BenchMarkService {


    /**
	 * 获得可用的大盘的列表
	 * @return
     */
	List<BenchmarkEntity> getAllBenchMarksList();
	/**
	 * 获得所有大盘的所有数据
	 */
	List<BenchmarkdataEntity> getAllBenchMarksDataList();
	/**
	 * 获得某只大盘最近一个月的所有数据
	 * @param BenchMarkCode
	 * @return  最近一个月数据集合的迭代器,如果该大盘不存在，返回null
	 */
	List<BenchmarkdataEntity> getRecentBenchMarks(String BenchMarkCode);
	/**
	 * 获得某只大盘某段时间内的所有数据
	 * @param BenchMarkCode
	 * @param start
	 * @param end
	 * @return 如果该大盘不存在，返回null
	 */
	List<BenchmarkdataEntity> getBenchMarkByTime(String BenchMarkCode, MyDate start,
													MyDate end);
}
