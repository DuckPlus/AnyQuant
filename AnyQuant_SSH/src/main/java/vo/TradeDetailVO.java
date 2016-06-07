package vo;

import util.MyDate;

/**
 * 记录某一条交易详情
 * @author Qiang
 * @date 6/6/16
 */
public class TradeDetailVO {
    private MyDate tradeDate;
    private String code;
    private String codeName;
    private boolean buyOrSell;
    /**
     * 交易手数
     */
    private int numofTrade;
    /**
     * 交易价格
     */
    private double tradePrice;
}
