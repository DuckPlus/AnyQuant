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
    /**
     * get all of the BenchMarkEntities
     * @return
     */
    List<BenchmarkEntity> getAllBenchMarksList();

    /**
     * get the list of the latest BenchMarkdataEntities
     * @return
     */
    List<BenchmarkdataEntity> getAllBenchMarksDataList();

    List<BenchmarkdataEntity> getRecentBenchMarks(String BenchMarkCode);

    /**
     *
     * @param BenchMarkCode
     * @param start
     * @param end
     * @return
     */
    List<BenchmarkdataEntity> getBenchMarkByTime(String BenchMarkCode, MyDate start, MyDate end);


    String getBenchMarkCodeByName(String name);


    double getAvgPrice(String code , MyDate date);

    List<String> getAllBenchMarkCodes();

}
