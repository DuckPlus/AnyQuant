package controller;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.StrategyService;
import util.Configure;
import util.MyDate;
import vo.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    private final StrategyService service;

    @Autowired
    public StrategyController(StrategyService service) {
        this.service = service;
    }


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
    public JSONObject getStocksFactorJudgment(String codes, String start, String end, String baseBench) {
        List<String> stockCodes = Arrays.asList(codes.split(Configure.STOCK_SPLITER));

        FactorJudgmentVO vo =  service.getStocksFactorJudgment(stockCodes, MyDate.getDateFromString(start), MyDate.getDateFromString(end), baseBench);
        return vo.getJSON();
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
     * investWeight - 仓位控制<br>
     * @return analysis report
     */
    @RequestMapping(value = "/analyseWithFactor", method = RequestMethod.POST)
    public ReportVO analyseWithFactor(String arguments) {
        JSONObject jsonObject = JSONObject.fromObject(arguments);
        System.out.println("arguments" +arguments);
//        System.out.println(jsonObject);
        List<String> stockCodes = Arrays.asList((jsonObject.getString("codes")).split(Configure.STOCK_SPLITER));
        MyDate start = MyDate.getDateFromString(jsonObject.getString("start"));
        MyDate end = MyDate.getDateFromString(jsonObject.getString("end"));
        Map<String, Double> factorWeight = jsonObject.getJSONObject("factorWeight");
        int capital = jsonObject.getInt("capital");
        double taxRate = jsonObject.getDouble("taxRate");
        String baseCode = jsonObject.getString("baseCode");
        int interval = jsonObject.getInt("interval");
        double[] investWeight = Arrays.stream(jsonObject.getString("investWeight").split(Configure.STOCK_SPLITER)).mapToDouble(Double::parseDouble).toArray();


//        for (int i = 0; i < stockCodes.size(); i++) {
//            System.out.println("done"+i);
//            System.out.println(stockCodes.get(i));
//        }
//        System.out.println(start.DateToString());
//
//        System.out.println(end.DateToString());
//        System.out.println(factorWeight);
//        for (Map.Entry<String  , Double> entry : factorWeight.entrySet()){
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }
//        System.out.println(capital);
//        System.out.println(taxRate);
//        System.out.println(baseCode);
//        System.out.println(Arrays.toString(investWeight));

        ReportVO vo  = service.analyseWithFactor(stockCodes, start, end, factorWeight, capital, taxRate, baseCode, interval , investWeight);

//        System.out.println("PK-------");
//        for(CumRtnVO temp : vo.cumRtnVOList){
//            System.out.println("date: "+temp.date.DateToString()+" test: "+temp.testValue+" base: "+temp.baseValue);
//        }
//
//        System.out.println("Detail-------");
//        for(TradeDataVO tradeDataVO : vo.tradeDataVOList){
//            System.out.print("date: "+tradeDataVO.tradeDate.DateToString());
//            System.out.print("  profit: "+tradeDataVO.profit);
//            System.out.println("  nowCaptial: "+tradeDataVO.nowCapital);
//            for(TradeDetailVO detailVO : tradeDataVO.tradeDetailVOs){
//                if(detailVO.buyOrSell){
//                    System.out.print("Buy ");
//                }else{
//                    System.out.print("Sell ");
//                }
//                System.out.print(100*detailVO.numofTrade+" lots ");
//                System.out.print(detailVO.code+"  "+detailVO.codeName+" at");
//                System.out.println("price: "+detailVO.tradePrice);
//
//            }
//        }

        return vo;
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
    public ReportVO analyseWithSpecificStrategy( String arguments) {
        System.out.println(arguments+"]]");
        JSONObject jsonObject = JSONObject.fromObject(arguments);
        String strategyName = jsonObject.getString("name");
        int capital = jsonObject.getInt("capital");
        double taxRate = jsonObject.getDouble("taxRate");
        String baseCode = jsonObject.getString("baseCode");
        int interval = jsonObject.getInt("interval");
        MyDate start = MyDate.getDateFromString(jsonObject.getString("start"));
        MyDate end = MyDate.getDateFromString(jsonObject.getString("end"));
        int vol = jsonObject.getInt("vol");
        switch (strategyName) {
            case "Strategy_VOL":
                return service.analyseWithStrategyVol(vol, interval, capital, taxRate, baseCode, start, end);
            case "Strategy_PE":
                return service.analyseWithStrategyPE(vol, interval, capital, taxRate, baseCode, start, end);
            default:
                throw new RuntimeException();

        }


    }


}
