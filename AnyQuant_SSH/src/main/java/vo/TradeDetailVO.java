package vo;

import util.MyDate;

/**
 * 记录某一条交易详情
 * @author Qiang
 * @date 6/6/16
 */
public class TradeDetailVO {
    public  MyDate tradeDate;
    public String code;
    public String codeName;

    public boolean buyOrSell;
    /**
     * 交易手数
     */
    public int numofTrade;
    /**
     * 交易价格
     */
    public double tradePrice;
}
