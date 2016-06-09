package service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.StrategyService;
import service.impl.analysis.FactorAnalyseHelper;
import service.impl.strategy.Strategy_PE;
import service.impl.strategy.Strategy_Vol;
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
    private Strategy_Vol strategy_vol;
    @Autowired
    private Strategy_PE strategy_pe;
    @Autowired
    private FactorAnalyseHelper factorAnalyseHelper;

//    @Override
//    public FactorJudgmentVO getStocksFactorJudgment(List<String> codes) {
//        return factorAnalyseHelper.report(codes);
//    }


    @Override
    public FactorJudgmentVO getStocksFactorJudgment(List<String> codes, MyDate start, MyDate end , String baseBench) {
        return factorAnalyseHelper.report(codes ,start , end , baseBench);
    }

    @Override
    public ReportVO analyseWithFactor(List<String> codes, MyDate start, MyDate end, Map<String, Double> factorWeight, int capital, double taxRate, String baseCode, int interval) {


        return null;
    }

    @Override
    public ReportVO analyseWithStrategyVol(int vol, int interval, double capital, double taxRate, String baseCode, MyDate start, MyDate end) {
        strategy_vol.setPara_Vol
                (capital,taxRate,baseCode,start,end,vol,interval);
        return strategy_vol.analyse();
    }

    @Override
    public ReportVO analyseWithStrategyPE(int vol, int interval, double capital, double taxRate, String baseCode, MyDate start, MyDate end) {
        strategy_pe.setPara_PE
                (capital,taxRate,baseCode,start,end,vol,interval);
        return strategy_pe.analyse();
    }


}
