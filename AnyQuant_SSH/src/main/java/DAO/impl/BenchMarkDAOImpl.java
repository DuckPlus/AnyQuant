package DAO.impl;

import DAO.BenchMarkDAO;
import entity.BenchmarkEntity;
import entity.BenchmarkdataEntity;
import org.springframework.stereotype.Repository;
import util.MyDate;

import java.util.List;

/**
 * @author ss
 * @date 16/5/11
 */
@Repository
public class BenchMarkDAOImpl implements BenchMarkDAO {
    @Override
    public List<BenchmarkEntity> getAllBenchMarksList() {
        return null;
    }

    @Override
    public List<BenchmarkdataEntity> getAllBenchMarksDataList() {
        return null;
    }

    @Override
    public List<BenchmarkdataEntity> getRecentBenchMarks(String BenchMarkCode) {
        return null;
    }

    @Override
    public List<BenchmarkdataEntity> getBenchMarkByTime(String BenchMarkCode, MyDate start, MyDate end) {
        return null;
    }
}
