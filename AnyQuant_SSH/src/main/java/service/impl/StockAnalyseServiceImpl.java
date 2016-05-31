package service.impl;

import DAO.FactorDAO;
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
import service.StockService;
import service.helper.FactorEvaluationHelper;
import service.helper.StockHelper;
import util.DateCalculator;
import util.MyDate;
import util.enumration.AnalysisFactor;
import util.enumration.FactorJudge;
import vo.EvaluationVO;
import vo.FactorWeightVO;
import vo.Factor_VO;
import vo.NewsVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 股票分析界面逻辑层
 * @author Qiang
 * @date 16/5/18
 */
@Service
public class StockAnalyseServiceImpl implements StockAnalyseService {


    private NewsDataService newsDS = DataServiceFactory.getNewsDataService();
    @Autowired
    private StockService stockService;
    @Autowired
    private StockDAO stockDAO;
    @Autowired
    private StockDataDAO stockDataDAO;
    @Autowired
    private FactorDAO factorDAO;
    @Override
    @Transactional
    public StockEntity getStockFullMessage(String stockCode) {
        return stockService.getStockEntity(stockCode);
    }

    @Override
    public List<NewsVO> getRelatedNewsVO(String stockCode) {
        return newsDS.getRelatedNewsVO(stockCode);
    }

    @Override
    public EvaluationVO getEvaluation(String stockCode) {

        return FactorEvaluationHelper.evaluateStockByFactor(stockService.getStocksByTime(stockCode , DateCalculator.getAnotherDay( - 50) , DateCalculator.getToDay()) , factorDAO.getFactorByDate(stockCode , DateCalculator.getAnotherDay( - 50) , DateCalculator.getToDay()), factorDAO.getListFactors(stockDAO.getBoardRealatedStockCodes(stockDAO.getStockEntity(stockCode).getBoard())));
    }

    @Override
    @Transactional
    public List<StockdataEntity> getBoardRelatedStockMessage(String stockCode) {
        String board = getStockFullMessage(stockCode).getBoard();
        List<String> relatedStocks = stockDAO.getBoardRealatedStockCodes(board);

        return stockDataDAO.getStockData(relatedStocks);


    }

    @Override
    @Transactional
    public List<StockdataEntity> getRegionRelatedStockMessage(String stockCode) {
        String region = getStockFullMessage(stockCode).getRegion();
        List<String> relatedStockCodes = stockDAO.getRegionRealatedStockCodes(region);

        return stockDataDAO.getStockData(relatedStockCodes);

    }

    @Override
    public List<Factor_VO> getFactorVO(String stockCode, AnalysisFactor factor , int offset) {
        return factorDAO.getFactors(stockCode , factor , DateCalculator.getAnotherDay(-offset) , DateCalculator.getToDay());
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
    public List<String> getAllFactorJudgeFactors() {
        List<String> result = new ArrayList<>();
        for (FactorJudge factor :  FactorJudge.values()){
            result.add(factor.chinese);
        }
        return result;
    }

    @Override
    public List<FactorWeightVO> getMostUsefulFactors(String code, int timeLen , FactorJudge factorJudge) {
        MyDate start = DateCalculator.getAnotherDay(-timeLen);
        MyDate end = DateCalculator.getToDay();

        List<StockdataEntity> entities = stockDataDAO.getStockData(code , start ,end);
        List<FactorWeightVO> factorWeightVOs = new ArrayList<>(AnalysisFactor.values().length);
        for (AnalysisFactor factor : AnalysisFactor.values()){
            List<Factor_VO> factor_vos = factorDAO.getFactors(code , factor , start , end);

            double factorJudgeVal = computeFactorJudgeValue(entities , factor_vos , factorJudge);


            factorWeightVOs.add(new FactorWeightVO(factorJudgeVal , factor.chinese , factorJudgeVal > 0 ));
        }
        //进行因子绝对值的简单排序
        factorWeightVOs.sort( (v1 , v2) ->  Math.abs(v1.judgeFactorValue) > Math.abs(v2.judgeFactorValue) ? 1 : -1  );

        return factorWeightVOs;
    }

    private static double computeFactorJudgeValue(List<StockdataEntity> entities, List<Factor_VO> factor_vos, FactorJudge factorJudge) {
        switch (factorJudge){
            case IC:
                 return StockHelper.computeIC(entities ,factor_vos);
            case IR:
                 return StockHelper.computeIR();
            case T_CHECK:
                 return StockHelper.computeWIN_RATE();

        }
        return 0;
    }


}
