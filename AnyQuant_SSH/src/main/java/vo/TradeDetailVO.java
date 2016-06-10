package vo;

import java.io.Serializable;

/**
 * 记录某一条交易详情
 * @author Qiang
 * @date 6/6/16
 */
public class TradeDetailVO implements Serializable {

    public String code;
    public String codeName;

    /**
     * 买为真，卖为假
     */
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
