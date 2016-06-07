package service.impl;

import DAO.FactorDAO;
import DAO.StockDataDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.StrategyService;
import service.helper.FactorAnalyseHelper;
import util.MyDate;
import vo.FactorJudgmentVO;
import vo.ReportVO;

import java.util.*;

/**
 * @author Qiang
 * @date 6/5/16
 */
@Service
public class StrategyServiceImpl implements StrategyService {
    @Autowired
    private FactorDAO factorDAO;
    @Autowired
    private StockDataDAO stockDataDAO;
    @Autowired
    private FactorAnalyseHelper factorAnalyseHelper;

    @Override
    public FactorJudgmentVO getStocksFactorJudgment(List<String> codes) {
        return factorAnalyseHelper.report(codes);
    }





    @Override
    public ReportVO analyseWithFactor(List<String> codes, MyDate start, MyDate end, Map<String, Double> factorWeight) {
        return null;
    }
}
