package service.impl;

import DAO.StockDAO;
import DAO.StockDataDAO;
import data.DataServiceFactory;
import data.StockDataService;
import entity.StockEntity;
import entity.StockdataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.StockService;
import service.helper.StableDataCacheHelper;
import util.DateCalculator;
import util.MyDate;
import vo.DealVO;
import vo.OHLC_VO;
import vo.TimeSharingVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 同时支持大盘
 * @author Qiang
 * @date 16/5/6
 */
@Service
@Transactional
public class StockServiceImpl implements StockService {
    @Autowired
    StockDAO stockDAO;
    @Autowired
    StockDataDAO stockDataDAO;
    @Autowired
    StableDataCacheHelper cacheHelper;
    private StockDataService stockDataService = DataServiceFactory.getStockDataService();

    @Override
    public List<OHLC_VO> getDayOHLC_Data(String stockCode, MyDate start, MyDate end) {
        List<StockdataEntity> entities = getStocksByTime(stockCode ,start ,end);
        List<OHLC_VO> results;
        OHLC_VO vo;
        if (entities != null) {
            results = new ArrayList<OHLC_VO>(entities.size());
            for (StockdataEntity entity : entities) {
                vo = new OHLC_VO(DateCalculator.SQLDateToMyDate(entity.getDate()), entity.getOpen(),
                        entity.getClose(), entity.getHigh(), entity.getLow());
                if (judgeData(vo)) { // if the stock didn't trade that day , desert it
                    results.add(vo);
                }

            }
            return results;
        } else {
            return null;
        }
    }


    @Override
    public List<OHLC_VO> getWeekOHLC_Data(String stockCode, MyDate start, MyDate end) {
        List<StockdataEntity> pos = getStocksByTime(stockCode ,start ,end);
        List<OHLC_VO> results;
        OHLC_VO vo;
        int DAY_OF_WEEK = 5;
        if (pos == null) {
            return null;
        } else {
            int len = pos.size();
            int weekNum = len / DAY_OF_WEEK + (len % DAY_OF_WEEK > 0 ? 1 : 0);
            int monday;
            int friday;
            results = new ArrayList<OHLC_VO>(weekNum);

            for (int i = 0; i < weekNum; i++) {
                monday = i * DAY_OF_WEEK;
                friday = i == weekNum - 1 ? (len % DAY_OF_WEEK - 1) : 4;
                vo = new OHLC_VO(DateCalculator.SQLDateToMyDate(pos.get(monday).getDate()), pos.get(monday).getOpen(),
                        pos.get(monday + friday).getClose(), getHighInScope(pos.subList(monday, monday + friday + 1)),
                        getLowInScope(pos.subList(monday, monday + friday + 1)));
                // Friday sometimes means the last trading day in this week
                if (judgeData(vo)) { // if the stock didn't trade that day , desert it
                    results.add(vo);
                }
            }
            return results;
        }
    }


    @Override
    public List<OHLC_VO> getMonthOHLC_Data(String stockCode, MyDate start, MyDate end) {
        List<OHLC_VO> vos;
        List<StockdataEntity> pos;

        int monthNum = 12 * (end.getYear() - start.getYear()) + end.getMonth() - start.getMonth() + 1;
        vos = new ArrayList<>(monthNum);
        MyDate thisMonth = start.clone();
        MyDate monthEnd = thisMonth.clone();

        thisMonth.setDay(1);
        monthEnd.setDay(DateCalculator.getMonthEndDay(monthEnd));
        OHLC_VO vo;
        for (int i = 0; i < monthNum; i++) {


            pos = getStocksByTime(stockCode, thisMonth, monthEnd);

            if (pos == null || pos.size() == 0) {

            } else {
                vo = new OHLC_VO(thisMonth, pos.get(0).getOpen(), pos.get(pos.size() - 1).getClose(),
                        getHighInScope(pos), getLowInScope(pos));
                if (judgeData(vo)) { // if the stock didn't trade that day , desert it
                    vos.add(vo);
                }

            }


            DateCalculator.getNextMonth(thisMonth);
            DateCalculator.getNextMonth(monthEnd);
        }
        return vos.isEmpty() ? null : vos;
    }

    @Override
    public List<TimeSharingVO> getSharingVOs(String stockCode) {
        return stockDataService.getTimeSharingVOs(stockCode);
    }


    @Override
    public List<DealVO> getDayDealVOs(String stockCode, MyDate start, MyDate end) {
        List<StockdataEntity> pos = getStocksByTime(stockCode ,start ,end);
        List<DealVO> results;
        if (pos == null) {
            return null;
        } else {
            results = new ArrayList<DealVO>(pos.size());
            for (StockdataEntity stockPO : pos) {
                results.add(new DealVO(stockPO.getTurnoverValue(), stockPO.getTurnoverVol(),
                        DateCalculator.SQLDateToMyDate(stockPO.getDate())));
            }
        }

        return results.isEmpty() ? null : results;
    }


    @Override
    public List<DealVO> getWeekDealVOs(String stockCode, MyDate start, MyDate end) {
        List<StockdataEntity> pos = getStocksByTime(stockCode, DateCalculator.getMondayofTheWeek(start),
                DateCalculator.getFridayofTheWeek(end));
        List<DealVO> results;
        int DAY_OF_WEEK = 5;
        if (pos == null) {
            return null;
        } else {
            int len = pos.size();
            int weekNum = len / DAY_OF_WEEK + (len % DAY_OF_WEEK > 0 ? 1 : 0);
            int monday;
            int friday;
            results = new ArrayList<DealVO>(weekNum);
            for (int i = 0; i < weekNum; i++) {
                monday = i * DAY_OF_WEEK;
                // last item is exclusive of the method "sublist"
                friday = (i == weekNum - 1) ? (len % DAY_OF_WEEK) : 5;
                // Friday sometimes means the last trading day in this week
                results.add(getSumDealVO(pos.subList(monday, monday + friday)));

            }
            return results.isEmpty() ? null : results;
        }
    }

    @Override
    public List<DealVO> getMonthDealVOs(String stockCode, MyDate start, MyDate end) {
        List<DealVO> vos;
        List<StockdataEntity> pos;

        int monthNum = 12 * (end.getYear() - start.getYear()) + end.getMonth() - start.getMonth() + 1;
        vos = new ArrayList<>(monthNum);
        MyDate thisMonth = start.clone();
        MyDate monthEnd = thisMonth.clone();
        thisMonth.setDay(1);
        monthEnd.setDay(DateCalculator.getMonthEndDay(monthEnd));
        for (int i = 0; i < monthNum; i++) {
            pos = getStocksByTime(stockCode, thisMonth, monthEnd);
            vos.add(getSumDealVO(pos));

            DateCalculator.getNextMonth(monthEnd);
            DateCalculator.getNextMonth(thisMonth);
        }

        return vos.isEmpty() ? null : vos;
    }


    @Override
    public List<StockdataEntity> getRecentStocks(String stockCode) {
        return (List<StockdataEntity>) stockDataDAO.getStockData(stockCode , DateCalculator.getAnotherDay(-30));
    }

    @Override
    public List<StockEntity> getAllStocks() {
        return cacheHelper.getAllStocks();
    }

    @Override
    public StockEntity getStockDescription(String code) {
        return cacheHelper.getStockDescription(code);
    }

    @Override
    public List<StockdataEntity> getTodayAllStockData() {
        return cacheHelper.getTodayAllStockData();
    }


    @Override
    public StockdataEntity getTodayStockVO(String stockCode) {
        return stockDataDAO.getStockData(stockCode);
    }



    @Override
    public List<StockdataEntity> getStocksByTime(String stockCode, MyDate start, MyDate end) {
        return stockDataDAO.getStockData(stockCode , start ,end);
    }

    @Override
    public StockEntity getStockEntity(String code) {
        return cacheHelper.getStockEntity(code);
    }

    /**
         * 计算给定范围内的成交量、成交额总量
         *
         */
    private static DealVO getSumDealVO(List<StockdataEntity> subList) {
        long volume = 0;
        double turnoverval = 0;
        for (StockdataEntity stockPO : subList) {
            volume += stockPO.getTurnoverVol();
            turnoverval += stockPO.getTurnoverValue();
        }
        return new DealVO(turnoverval, volume, DateCalculator.SQLDateToMyDate(subList.get(0).getDate()));
    }


    private static double getLowInScope(List<StockdataEntity> scope) {
        double result = Double.MAX_VALUE;
        for (StockdataEntity stockPO : scope) {
            if (stockPO.getLow() < result) {
                result = stockPO.getLow();
            }
        }
        return result;
    }

    private static double getHighInScope(List<StockdataEntity> scope) {
        double result = Double.MIN_VALUE;
        for (StockdataEntity stockPO : scope) {
            if (stockPO.getHigh() > result) {
                result = stockPO.getHigh();
            }
        }
        return result;
    }




    private static boolean judgeData(OHLC_VO vo) {
        return !(vo.low == vo.high);
    }
}
