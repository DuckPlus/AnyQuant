package DAO;

import entity.BenchmarkEntity;
import entity.BenchmarkdataEntity;
import util.MyDate;

import java.util.List;

/**
 * @author Qiang
 * @date 16/5/11
 */
public interface BenchMarkDAO {

    List<BenchmarkEntity> getAllBenchMarksList();

    List<BenchmarkdataEntity> getAllBenchMarksDataList();

    List<BenchmarkdataEntity> getRecentBenchMarks(String BenchMarkCode);

    List<BenchmarkdataEntity> getBenchMarkByTime(String BenchMarkCode, MyDate start, MyDate end);

}
