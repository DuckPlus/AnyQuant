package service.impl;

import data.DataServiceFactory;
import data.NewsDataService;
import entity.StockEntity;
import entity.StockdataEntity;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;
import org.springframework.stereotype.Service;
import service.StockAnalyseService;
import util.enumration.AnalysisFactor;
import vo.EvaluationVO;
import vo.Factor_VO;
import vo.NewsVO;

import java.util.List;

/**
 * 股票分析界面逻辑层
 * @author Qiang
 * @date 16/5/18
 */
@Service
public class StockAnalyseServiceImpl implements StockAnalyseService {


    private NewsDataService newsDS = DataServiceFactory.getNewsDataService();


    @Override
    public StockEntity getStockFullMessage(String stockCode) {
        return null;
    }

    @Override
    public List<NewsVO> getRelatedNewsVO(String stockCode) {
        return newsDS.getRelatedNewsVO(stockCode);
    }

    @Override
    public EvaluationVO getEvaluation(String stockCode) {
        return null;
    }

    @Override
    public List<StockdataEntity> getBoardRelatedStockMessage(String stockCode) {
        return null;
    }

    @Override
    public List<StockdataEntity> getRegionRelatedStockMessage(String stockCode) {
        return null;
    }

    @Override
    public List<Factor_VO> getFactorVO(String stockCode, AnalysisFactor factor) {
        return null;
    }


}
