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
     * 只需要传入股票的代码例如"sh600126"，返回最新信息
     *
     * @param stockCode
     * @return
     */
    public List<StockdataEntity> getStockData(List<String> stockCode);


    /**
     *增加了时间限制，可以设置只查看某一天的数据
     * @param stockCode
     * @param date
     * @return
     */
    public StockdataEntity getStockData(String stockCode, MyDate date);

    /**
     * 同时为多只股票提供上面的请求服务
     * @param stockCodes
     * @param date
     * @return
     */
    public List<StockdataEntity> getStockData(List<String> stockCodes, MyDate date);

    /**
     * 增加了时间限制，可以查看某段时间内的数据
     *
     * @param stockCode
     * @param
     * @return
     */
    public List<StockdataEntity> getStockData(String stockCode, MyDate start, MyDate end);

    /**
     * 同时为多只股票提供上面的请求服务
     * @param stockCodes
     * @param start
     * @param end
     * @return
     */
    public List<StockdataEntity> getStockData(List<String> stockCodes, MyDate start, MyDate end);

    /**
     * 返回当天的全部股票具体信息
     * @return
     */
    public List<StockdataEntity> getAllStockData();


    /**
     * 返回某天pe>=?的股票列表
     * @param date
     * @return
     */
    public List<String> getStockCodeByPE(MyDate date,double low_PE , double high_PE);

    /**
     * 返回某天多只股票的平均价格
     * @param codes
     * @param date
     * @return
     */
    public double [] getAvgPriceByCodes(List<String> codes, MyDate date);


    /**
     * 返回某天某只股票的平均价格
     * @param code
     * @param date
     * @return
     */
    public double  getAvgPriceByCode(String code, MyDate date);


    /**
     * 获取市场上某日期段内成交量最小的vol只股票
     */
    public List<String>  getStockCodeByVolDec(MyDate start ,MyDate end, int vol);

    /**
     * 返回给定日期段内的有效交易日日期
     * @param start
     * @param end
     * @return
     */
    public List<MyDate> getTradeDates(MyDate start, MyDate end);



}
