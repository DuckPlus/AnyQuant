package DAO;

import entity.StockdataEntity;
import util.MyDate;

import java.util.List;

/**
 * Created by 67534 on 2016/5/7.
 */
public interface StockDataDAO {
    /**
     * 只需要传入股票的代码例如"sh600126"，返回最新信息
     *
     * @param stockCode
     * @return
     */
    public StockdataEntity getStockData(String stockCode);

    /**
     *增加了时间限制，可以设置只查看某一天的数据
     * @param stockCode
     * @param date
     * @return
     */
    public StockdataEntity getStockData(String stockCode, MyDate date);
    public StockdataEntity getStockData(List<String> stockCodes, MyDate date);

    /**
     * 增加了时间限制，可以查看某段时间内的数据
     *
     * @param stockCode
     * @param
     * @return
     */
    public List<StockdataEntity> getStockData(String stockCode, MyDate start, MyDate end);
    public List<StockdataEntity> getStockData(List<String> stockCodes, MyDate start, MyDate end);

    /**
     * 返回当天的全部股票具体信息
     * @return
     */
    public List<StockdataEntity> getAllStockData();
}
