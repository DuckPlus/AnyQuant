package controller;

import controller.helper.JSONHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.StrategyService;
import util.Configure;
import util.MyDate;
import vo.FactorJudgmentVO;
import vo.ReportVO;

import java.util.*;

/**
 * 用户制定策略界面Controller
 *
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
     *
     * @param codes the stock pool
     * @param start start date
     * @param end   end date
     * @return some of the most useful factors , which gives customer suggestions on strategy to analyse
     */
    @RequestMapping("/getStocksFactorJudgment")
    public FactorJudgmentVO getStocksFactorJudgment(String codes, String start, String end, String baseBench) {
        List<String> stockCodes = Arrays.asList(codes.split(Configure.STOCK_SPLITER));

        return service.getStocksFactorJudgment(stockCodes, MyDate.getDateFromString(start), MyDate.getDateFromString(end), baseBench);
    }

    /**
     * FactorAnalysis
     * 因子分析<br>
     * codes - 股票池,用逗号隔开的字符串<br>
     * start - 起始日期<br>
     * end - 结束日期<br>
     * factorWeight - 因子及其对应的权重<br>
     * capital - 起始资金<br>
     * taxRate - 交易费率<br>
     * baseCode - 基准大盘<br>
     * interval - 调仓间隔<br>
     *
     * @return analysis report
     */
    @RequestMapping(value = "/analyseWithFactor", method = RequestMethod.POST)
    public ReportVO analyseWithFactor(JSONObject argument) {

        List<String> stockCodes = Arrays.asList((argument.getString("codes")).split(Configure.STOCK_SPLITER));
        MyDate start = MyDate.getDateFromString(argument.getString("start"));
        MyDate end = MyDate.getDateFromString(argument.getString("end"));
        Map<String, Double> factorWeight = argument.getJSONObject("factorWeight");
        int capital = argument.getInt("capital");
        double taxRate = argument.getDouble("taxRate");
        String baseCode = argument.getString("baseCode");
        int interval = argument.getInt("interval");


        return service.analyseWithFactor(stockCodes, start, end, factorWeight, capital, taxRate, baseCode, interval);
    }

    /**
     * Specific strategy analysis
     * 特定策略分析<br>
     * <p>
     * Strategy1 Strategy_Vol<br>
     * vol - 每次买入的股票数<br>
     * interval - 调仓间隔<br>
     * capital - 初始资金<br>
     * taxRate - 交易费率<br>
     * baseCode - 基准大盘<br>
     * start - 起始日期<br>
     * end - 结束日期<br>
     * </p>
     * <p>
     * Strategy2  Strategy_PE<br>
     * vol - 每次买入的股票数<br>
     * interval - 调仓间隔<br>
     * capital - 初始资金<br>
     * taxRate - 交易费率<br>
     * baseCode - 基准大盘<br>
     * start - 起始日期<br>
     * end - 结束日期<br>
     * <p/>
     *
     * @return analysis report
     */
    @RequestMapping(value = "/analyseWithSpecificStrategy", method = RequestMethod.POST)
    public ReportVO analyseWithSpecificStrategy(JSONObject argument) {
        String strategyName = argument.getString("name");
        int capital = argument.getInt("capital");
        double taxRate = argument.getDouble("taxRate");
        String baseCode = argument.getString("baseCode");
        int interval = argument.getInt("interval");
        MyDate start = MyDate.getDateFromString(argument.getString("start"));
        MyDate end = MyDate.getDateFromString(argument.getString("end"));
        int vol = argument.getInt("vol");
        switch (strategyName) {
            case "Strategy_Vol":
                return service.analyseWithStrategyVol(vol, interval, capital, taxRate, baseCode, start, end);
            case "Strategy_PE":
                return service.analyseWithStrategyPE(vol, interval, capital, taxRate, baseCode, start, end);
            default:
                break;
        }


        return null;
    }


}
