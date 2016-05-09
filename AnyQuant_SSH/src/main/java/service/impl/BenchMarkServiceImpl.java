package service.impl;

import entity.BenchmarkdataEntity;
import service.BenchMarkService;
import util.MyDate;

import java.util.List;


/**
 * @author Qiang
 * @date 16/5/8
 */
public class BenchMarkServiceImpl implements BenchMarkService {
    /**
     * 获得所有大盘的所有数据
     */
    @Override
    public List<BenchmarkdataEntity> getAllBenchMarks() {
        return null;
    }

    /**
     * 获得某只大盘最近一个月的所有数据
     *
     * @param BenchMarkCode
     * @return 最近一个月数据集合的迭代器, 如果该大盘不存在，返回null
     */
    @Override
    public List<BenchmarkdataEntity> getRecentBenchMarks(String BenchMarkCode) {
        return null;
    }

    /**
     * 获得某只大盘某段时间内的所有数据
     *
     * @param BenchMarkCode
     * @param start
     * @param end
     * @return 如果该大盘不存在，返回null
     */
    @Override
    public List<BenchmarkdataEntity> getBenchMarkByTime(String BenchMarkCode, MyDate start, MyDate end) {
        return null;
    }
}
