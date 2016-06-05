package service;

import entity.StockEntity;
import entity.StockdataEntity;
import util.enumration.AnalysisFactor;
import util.enumration.FactorJudge;
import vo.EvaluationVO;
import vo.FactorWeightVO;
import vo.Factor_VO;
import vo.NewsVO;

import java.util.List;

/**
 *
 * @author Qiang
 * @date 16/5/18
 */
public interface StockAnalyseService {
    /**
     * 获得某只股票的所有非实时数据信息
     * @param stockCode
     * @return
     */
    StockEntity getStockFullMessage(String stockCode);
    /**
     * 获取某只股票最近相关的不多于Max_newsCount条新闻vo
     * @param stockCode
     * @return
     */
    List<NewsVO> getRelatedNewsVO(String stockCode);

    /**
     * Get the evaluation of the stock, including mark,suggestion and analysis
     * 获得对某只股票的评价,包括评分、建议、分析等等
     * @param stockCode
     * @return
     */
    EvaluationVO getEvaluation(String stockCode);

    /**
     * Get the board-related stocks and their last trading days' data
     * 获得某只股票同板块的其他股票最近一个交易日的情况
     * @return
     */
    List<StockdataEntity> getBoardRelatedStockMessage(String stockCode);

    /**
     * Get the region-related stocks and their last trading days' data
     * 获得某只股票同地域的其他股票最近一个交易日的情况
     * @param stockCode
     * @return
     */
    List<StockdataEntity> getRegionRelatedStockMessage(String stockCode);

    /**
     * Get the factor data in a certain time<br>This method simply return the recent data change
     * 返回某个股票某个因子在最近一段时间内的变化
     * @param stockCode
     * @param factor
     * @param offset a positive num represents the offset from now
     * @return
     */
    List<Factor_VO> getFactorVO(String stockCode , AnalysisFactor factor , int offset);

    /**
     * Get all the usable factors
     * 返回所有可用的评判因子
     * @return
     */
    List<String> getAllFactors();
    /**
     * Get all the usable factors that use to judge factors
     * 返回所有可用的评判因子的因子
     * @return
     */
    List<String> getAllFactorJudgeFactors();

    /**
     * Get the most useful factors(may be )
     * @param code
     * @param timeLen
     * @param factorJudge
     * @return
     */
    List<FactorWeightVO> getMostUsefulFactors(String code , int timeLen, FactorJudge factorJudge);



}
