package service.impl;

import entity.BenchmarkEntity;
import entity.BenchmarkdataEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BenchMarkService;
import util.MyDate;

import java.util.List;


/**
 * @author Qiang
 * @date 16/5/8
 */
@Service
@Transactional
public class BenchMarkServiceImpl implements BenchMarkService {
    /**
     * 获得可用的大盘的列表
     *
     * @return
     */
    @Override
    public List<BenchmarkEntity> getAllBenchMarksList() {
        return null;
    }

    /**
     * 获得所有大盘的所有数据
     */
    @Override
    public List<BenchmarkdataEntity> getAllBenchMarksDataList() {
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
