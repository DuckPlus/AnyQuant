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
     * 获得指定时间内factor的值
     * @param stockCode
     * @param factor
     * @param start
     * @param end
     * @return
     */
    List<Factor_VO> getFactorVO(String stockCode, AnalysisFactor factor , MyDate start , MyDate end);


    List<FactorEntity> getFactorByDate(String stockCode , MyDate start , MyDate end);



}
