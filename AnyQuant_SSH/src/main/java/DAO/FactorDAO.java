package DAO;

import entity.FactorEntity;
import util.MyDate;
import util.enumration.AnalysisFactor;
import vo.Factor_VO;

import java.util.List;

/**
 * 处理股票因子数据库事务
 * @author Qiang
 * @date 16/5/20
 */
public interface FactorDAO {
    /**
     * 获得指定时间内指定factor的值
     * @param stockCode
     * @param factor
     * @param start
     * @param end
     * @return
     */
    List<Factor_VO> getFactors(String stockCode, AnalysisFactor factor , MyDate start , MyDate end);

    /**
     * 获得指定时间内所有factor的值
     * @param stockCode
     * @param start
     * @param end
     * @return
     */
    List<FactorEntity> getFactorBetweenDate(String stockCode , MyDate start , MyDate end);

    List<FactorEntity> getFactorBetweenDate(List<String> stockCodes , MyDate start , MyDate end);

    /**
     *
     * @param stockCodes
     * @param date
     * @return
     */
    List<FactorEntity> getFactorAtDate(List<String> stockCodes , MyDate date);



    /**
     * 获得一批股票最近一个交易日的因子值
     * @param stockCode
     * @return
     */
    List<FactorEntity> getListFactors(List<String> stockCode);




}
