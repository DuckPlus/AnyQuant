package blimpl;

import blservice.OptionalStockBLService;
import blservice.StockBLService;
import businessLogicHelper.VOPOchange;
import dataservice.APIDataFactory;
import dataservice.APIInterface;
import po.StockPO;
import vo.StockVO;

import java.util.*;

/**
 * Created by Qiang on 3/29/16.
 */
public class OptionalStockBLServiceImpl implements OptionalStockBLService {

    /**
     * 单例模式
     */
    private static OptionalStockBLService bl;
    private APIInterface APIDataSer;

    private List<StockVO> optionStocks;
    private Map<String, StockVO> optionalStockMap;

    public static OptionalStockBLService getOptionalBLService(){
        if(bl == null){
            bl = new OptionalStockBLServiceImpl();
        }
        return  bl;
    }

    private OptionalStockBLServiceImpl(){
        APIDataSer = APIDataFactory.getAPIDataService();

        Iterator<StockPO> pos = APIDataSer.getOptionalStocks();
        optionalStockMap = new TreeMap<>();
        StockPO po;
        while (pos.hasNext()){
            po = pos.next();
            optionalStockMap.put(po.getCode() , (StockVO) VOPOchange.POtoVO(po));
        }
        optionStocks = new ArrayList<>(optionalStockMap.values());

    }

    @Override
    public Iterator<StockVO> getOptionalStocks() {


        return optionStocks.iterator();


//		return APIDataSer.getOptionalStocks(VOPOchange.POtoVO(o));
    }


    @Override
    public boolean addStockCode(String stockCode) {
        return APIDataSer.addOptionalStock(stockCode);
    }

    @Override
    public boolean deleteStockCode(String stockCode) {
        return APIDataSer.deleteOptionalStock(stockCode);
    }

    @Override
    public boolean deleteStockCode(List<String> stockCode) {
        boolean result = true;
        for (String stock : stockCode) {
            result = result && APIDataSer.deleteOptionalStock(stock);
        }
        return result;
    }

    @Override
    public boolean addStockCode(List<String> stockCodes) {
        boolean result = true;
        for (String stock : stockCodes) {
            result = result && APIDataSer.addOptionalStock(stock);
        }
        return result;
    }
}
