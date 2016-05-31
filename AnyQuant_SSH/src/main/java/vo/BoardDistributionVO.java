package vo;

/**
 * 板块内部股票分布图
 * @author Qiang
 * @date 16/5/19
 */
public class BoardDistributionVO {
    /**
     * 板块名称
     */
    public String board;

    public String stockName;
    public String code;
    /**
     * 股票权重
     */
    public double weight;
    public double changeRate;
    public double high;
    public double low;
    public double open;
    public double close;
    public long turnoverVol;
    public double turnoverValue;
    public double turnoverRate;
}
