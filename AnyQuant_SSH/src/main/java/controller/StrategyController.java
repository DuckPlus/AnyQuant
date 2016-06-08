package controller;

import controller.helper.JSONHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.StrategyService;
import util.Configure;
import util.MyDate;
import vo.FactorJudgmentVO;
import vo.ReportVO;

import java.util.*;

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
     * @param codes the stock pool
     * @param start start date
     * @param end end date
     * @return some of the most useful factors , which gives customer suggestions on strategy to analyse
     */
    @RequestMapping("/getStocksFactorJudgment")
    public FactorJudgmentVO getStocksFactorJudgment(String codes , String  start , String end, String baseBench){
        List<String> stockCodes = Arrays.asList(codes.split(Configure.STOCK_SPLITER));

        return service.getStocksFactorJudgment(stockCodes, MyDate.getDateFromString(start) , MyDate.getDateFromString(end), baseBench);
    }

    /**
     * FactorAnalysis
     * 因子分析
     * @param codes the stock pool 股票池
     * @param start 起始日期
     * @param end   结束日期
     * @param factorWeight 因子及其对应的权重
     * @return analysis report
     */
    @RequestMapping("/analyseWithFactor")
    public ReportVO analyseWithFactor(String codes, String start , String end , JSONObject factorWeight){
        List<String> stockCodes = Arrays.asList(codes.split(Configure.STOCK_SPLITER));

        return service.analyseWithFactor(stockCodes , MyDate.getDateFromString(start) , MyDate.getDateFromString(end), (Map<String, Double>) JSONHelper.convertToMap(factorWeight));
    }

}
