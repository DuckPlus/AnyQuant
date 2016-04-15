package blHelper;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import vo.StockVO;
import enumeration.Stock_Attribute;

/**
 * 股票排序Helper
 *
 * @author czq
 * @date 2016年3月4日
 */
public class StockSortHelper {
    /**
     * 确定升降序
     */
    private static boolean isUp = false;

    public static Iterator<StockVO> sortStocks(List<StockVO> stocks,
                                               Stock_Attribute attr, boolean isUp) {
        StockSortHelper.isUp = isUp;
        switch (attr) {
            case volume:
                stocks.sort(new sortByVolume());
                break;
            case amplitude:
                stocks.sort(new sortByAmplitude());
                break;
            case changeRate:
                stocks.sort(new sortByChangeRate());
                break;
            case low:
                stocks.sort(new sortByLow());
            case high:
                stocks.sort(new sortByHigh());
            default:
                break;
        }
        return stocks.iterator();

    }


    /*
     * 股票属性 open: 开盘价 high: 最高价 low: 最低价 close: 收盘价 adj_price: 后复权价 volume: 成交量
     * turnover: 换手率 pe: 市盈率 pb: 市净率
     */
    private static class sortByVolume implements Comparator<StockVO> {
        @Override
        public int compare(StockVO o1, StockVO o2) {
            return compareWith(o1.turnoverVol, o2.turnoverVol);
        }
    }

    private static class sortByAmplitude implements Comparator<StockVO> {
        @Override
        public int compare(StockVO o1, StockVO o2) {
            return compareWith(o1.amplitude, o2.amplitude);
        }
    }

    private static class sortByChangeRate implements Comparator<StockVO> {
        @Override
        public int compare(StockVO o1, StockVO o2) {
            return compareWith(o1.changeRate, o2.changeRate);
        }
    }

    private static class sortByLow implements Comparator<StockVO> {

        @Override
        public int compare(StockVO o1, StockVO o2) {
            return compareWith(o1.low, o2.low);
        }
    }

    private static class sortByHigh implements Comparator<StockVO> {

        @Override
        public int compare(StockVO o1, StockVO o2) {
            return compareWith(o1.high, o2.high);
        }
    }


    private final static int compareWith(double s1, double s2) {
        if (s1 == s2) {
            return 0;
        }
        if (isUp) {
            return s1 > s2 ? 1 : -1;
        } else {
            return s1 > s2 ? -1 : 1;
        }

    }

}
