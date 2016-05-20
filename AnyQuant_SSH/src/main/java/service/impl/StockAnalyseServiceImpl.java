package service.impl;

import DAO.StockDAO;
import DAO.StockDataDAO;
import data.DataServiceFactory;
import data.NewsDataService;
import entity.StockEntity;
import entity.StockdataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.StockAnalyseService;
import util.enumration.AnalysisFactor;
import util.enumration.FactorJudge;
import vo.EvaluationVO;
import vo.FactorWeightVO;
import vo.Factor_VO;
import vo.NewsVO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 股票分析界面逻辑层
 * @author Qiang
 * @date 16/5/18
 */
@Service
public class StockAnalyseServiceImpl implements StockAnalyseService {


    private NewsDataService newsDS = DataServiceFactory.getNewsDataService();
    @Autowired
    private StockDAO stockDAO;
    @Autowired
    private StockDataDAO stockDataDAO;

    @Override
    @Transactional
    public StockEntity getStockFullMessage(String stockCode) {
        return stockDAO.getStockEntity(stockCode);
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
    @Transactional
    public List<StockdataEntity> getBoardRelatedStockMessage(String stockCode) {
        String board = getStockFullMessage(stockCode).getBoard();
        List<StockEntity> relatedStocks = stockDAO.getBoardRelatedStock(board);
        List<StockdataEntity> results = new ArrayList<>(relatedStocks.size());
        results.addAll(relatedStocks.stream().map(entity -> stockDataDAO.getStockData(entity.getCode())).collect(Collectors.toList()));
        return results;
    }

    @Override
    @Transactional
    public List<StockdataEntity> getRegionRelatedStockMessage(String stockCode) {
        String region = getStockFullMessage(stockCode).getRegion();
        List<StockEntity> relatedStocks = stockDAO.getRegionRelatedStock(region);
        List<StockdataEntity> results = new ArrayList<>(relatedStocks.size());

        results.addAll(relatedStocks.stream().map(stockEntity -> stockDataDAO.getStockData(stockEntity.getCode())).collect(Collectors.toList()));
        return results;
    }

    @Override
    public List<Factor_VO> getFactorVO(String stockCode, AnalysisFactor factor , int offset) {



        return null;
    }

    @Override
    public List<String> getAllFactors() {
        List<String> result = new ArrayList<>();
        for (AnalysisFactor factor :  AnalysisFactor.values()){
            result.add(factor.chinese);
        }
        return result;
    }

    @Override
    public List<FactorWeightVO> getMostUsefulFactors(String code, int timeLen , FactorJudge factorJudge) {
        return null;
    }


}
