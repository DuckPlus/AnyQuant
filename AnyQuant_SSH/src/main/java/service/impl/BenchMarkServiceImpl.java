package service.impl;

import DAO.BenchMarkDAO;
import entity.BenchmarkEntity;
import entity.BenchmarkdataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BenchMarkService;
import service.helper.StableDataCacheHelper;
import util.DateCalculator;
import util.MyDate;

import java.util.List;


/**
 * @author Qiang
 * @date 16/5/8
 */
@Service
@Transactional
public class BenchMarkServiceImpl implements BenchMarkService {

    @Autowired
    BenchMarkDAO benchMarkDAO;

    @Autowired
    StableDataCacheHelper cacheHelper;

    private List<BenchmarkEntity> benchmarkEntityList;
    /**
     * 获得可用的大盘的列表
     *
     * @return
     */
    @Override
    public List<BenchmarkEntity> getAllBenchMarksList() {
        return cacheHelper.getAllBenchMarksList();
    }

    /**
     * 获得所有大盘的所有数据
     */
    @Override
    public List<BenchmarkdataEntity> getAllBenchMarksDataList() {

        return benchMarkDAO.getAllBenchMarksDataList();
    }

    /**
     * 获得某只大盘最近一个月的所有数据
     *
     * @param benchMarkCode
     * @return 最近一个月数据集合的迭代器, 如果该大盘不存在，返回null
     */
    @Override
    public List<BenchmarkdataEntity> getRecentBenchMarks(String benchMarkCode) {
        return benchMarkDAO.getBenchMarkByTime(benchMarkCode , DateCalculator.getAnotherDay(-30) , DateCalculator.getToDay());
    }

    /**
     * 获得某只大盘某段时间内的所有数据
     *
     * @param benchMarkCode
     * @param start
     * @param end
     * @return 如果该大盘不存在，返回null
     */
    @Override
    public List<BenchmarkdataEntity> getBenchMarkByTime(String benchMarkCode, MyDate start, MyDate end) {

        return benchMarkDAO.getBenchMarkByTime(benchMarkCode, start  ,end);
    }
}
