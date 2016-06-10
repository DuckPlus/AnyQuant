package service.impl;

import DAO.StrategyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.StrategyService;
import service.impl.analysis.FactorAnalyseHelper;
import service.impl.strategy.FactorStrategy;
import service.impl.strategy.Strategy_PE;
import service.impl.strategy.Strategy_Vol;
import util.MyDate;
import util.enumration.AnalysisFactor;
import vo.FactorJudgmentVO;
import vo.ReportVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private FactorStrategy factorStrategy;
    @Autowired
    private StrategyDAO strategyDAO;
//    @Override
//    public FactorJudgmentVO getStocksFactorJudgment(List<String> codes) {
//        return factorAnalyseHelper.report(codes);
//    }


    @Override
    public FactorJudgmentVO getStocksFactorJudgment(List<String> codes, MyDate start, MyDate end , String baseBench) {
        return factorAnalyseHelper.report(codes ,start , end , baseBench);
    }

    @Override
    public ReportVO analyseWithFactor(List<String> codes, MyDate start, MyDate end, Map<String, Double> factorWeight, int capital, double taxRate, String baseCode, int interval , double[] investWeight) {
//        double[] investWeight = {0.5 , 0.2 , 0.2 ,0.1};
        Map<AnalysisFactor , Double> factorDoubleMap = new HashMap<>();
        for (Map.Entry<String , Double> factorWeightEntry : factorWeight.entrySet()){
            factorDoubleMap.put(AnalysisFactor.valueOf(factorWeightEntry.getKey()) , factorWeightEntry.getValue());
        }


        System.out.println("base code: "+baseCode);
        factorStrategy.setPara_Factor(capital , taxRate , baseCode , start , end , codes , factorDoubleMap , investWeight , interval);
//        System.out.println("capital: "+factorStrategy.capital);
//        System.out.println("taxRate: "+strategy.taxRate);
//        System.out.println("baseCode: "+strategy.baseCode);
//        System.out.println("interval: "+strategy.interval);
//        System.out.println("start: "+strategy.start.DateToString());
//        System.out.println("end: "+strategy.end.DateToString());
//        System.out.println("size of stock pool:"+strategy.stocks.size());
//        for(Map.Entry<AnalysisFactor,Double> entry:weightedFactors.entrySet()){
//            System.out.println(entry.getKey()+"  "+entry.getValue());
//        }

//        System.out.println("investWright: ");
//        for(int i=0 ; i<investWeight.length;i++){
//            System.out.println(investWeight[i]+"  ");
//        }
//
//        System.out.println("numOfLevel: "+strategy.numOfLevel);
        return factorStrategy.analyse();
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

    @Override
    public void saveReport(String userID , ReportVO vo) {
        strategyDAO.saveReport(userID , vo);
    }

    @Override
    public List<ReportVO> getAllReports(String userID) {
        return strategyDAO.getAllReports(userID);
    }


}
