package service;

import util.MyDate;
import vo.FactorJudgmentVO;
import vo.ReportVO;

import java.util.List;
import java.util.Map;

/**
 * 策略界面Service
 *
 * @author Qiang
 * @date 6/5/16
 */
public interface StrategyService {

    /**
     * Get the factorJudgement of the giving stocks
     *
     * @param codes the stock universal
     * @return some of the most useful factors , which gives customer suggestions on strategy to analyse
     */
    FactorJudgmentVO getStocksFactorJudgment(List<String> codes, MyDate start, MyDate end, String baseBench);

    /**
     * FactorStrategy
     * 根据给定的因子权重进行回测
     *
     * @param codes        股票池
     * @param start        起始日期
     * @param end          结束日期
     * @param factorWeight 因子及其对应的权重
     * @param capital      起始资金
     * @param taxRate      交易费率
     * @param baseCode     基准大盘
     * @param interval     调仓间隔
     * @param investWeight 仓位控制
     * @return analysis report
     */
    ReportVO analyseWithFactor(List<String> codes, MyDate start, MyDate end, Map<String, Double> factorWeight, int capital, double taxRate, String baseCode, int interval, double[] investWeight);

    /**
     * Strategy VOL
     * 成交额（成交量）小，表明这个股票无人关注，所以它的价格也是比较低的，买入后，放上一段时间，大概率上涨。
     * 这个策略的买入逻辑是： 1、买入市场上前20个交易日成交额最小的20支股票。 2、每个月满仓轮换。
     * 2011~2016年间取得了161%的正收益。 十年取得50回报。
     *
     * @param vol      每次买入的股票数
     * @param interval 调仓间隔
     * @param capital  初始资金
     * @param taxRate  交易费率
     * @param baseCode 基准大盘
     * @param start    起始日期
     * @param end      结束日期
     * @return analysis report
     */
    ReportVO analyseWithStrategyVol(int vol, int interval, double capital, double taxRate, String baseCode, MyDate start, MyDate end);

    /**
     * 成交额（成交量）小，表明这个股票无人关注，所以它的价格也是比较低的，买入后，放上一段时间，大概率上涨。
     * 这个策略的买入逻辑是： 1、买入市场上前20个交易日成交额最小的20支股票。 2、每个月满仓轮换。
     * 2011~2016年间取得了161%的正收益。 十年取得50回报。
     *
     * @param vol      每次买入的股票数
     * @param interval 调仓间隔
     * @param capital  初始资金
     * @param taxRate  交易费率
     * @param baseCode 基准大盘
     * @param start    起始日期
     * @param end      结束日期
     * @return analysis report
     */
    ReportVO analyseWithStrategyPE(int vol, int interval, double capital, double taxRate, String baseCode, MyDate start, MyDate end);

    /**
     * 保存策略报告
     */
    void saveReport(String userID  ,ReportVO vo);

    /**
     * 获得用户所有策略报告
     */
    List<ReportVO> getAllReports(String userID);
}
