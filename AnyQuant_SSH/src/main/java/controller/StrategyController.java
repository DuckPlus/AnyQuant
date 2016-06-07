package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.StrategyService;
import util.MyDate;
import vo.FactorJudgmentVO;
import vo.ReportVO;

import java.util.List;
import java.util.Map;

/**
 * 用户制定策略界面Controller
 * @author Qiang
 * @date 16/5/20
 */
@Controller
@RequestMapping("/Strategy")
@ResponseBody
public class StrategyController {

    @Autowired
    private StrategyService service;

    /**
     * Get the factorJudgement of the giving stocks
     * 获得股票池因子的分析情况
     * @param codes the stock universal
     * @return some of the most useful factors , which gives customer suggestions on strategy to analyse
     */
    public FactorJudgmentVO getStocksFactorJudgment(List<String> codes){
        return service.getStocksFactorJudgment(codes);
    }

    /**
     * FactorAnalysis
     * 因子分析
     * @param codes 股票池
     * @param start 起始日期
     * @param end   结束日期
     * @param factorWeight 因子及其对应的权重
     * @return analysis report
     */
    public ReportVO analyseWithFactor(List<String> codes, MyDate start , MyDate end , Map<String , Double> factorWeight){
        return service.analyseWithFactor(codes , start, end , factorWeight);
    }

}
