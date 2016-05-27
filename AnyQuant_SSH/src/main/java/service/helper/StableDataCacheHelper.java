package service.helper;

import DAO.BenchMarkDAO;
import DAO.StockDAO;
import DAO.StockDataDAO;
import entity.BenchmarkEntity;
import entity.StockEntity;
import entity.StockdataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.CacheService;

import java.util.*;

/**
 * @author Qiang
 * @date 16/5/26
 */
@Service
public class StableDataCacheHelper implements CacheService{
    @Autowired
    private BenchMarkDAO benchMarkDAO;
    @Autowired
    private StockDataDAO stockDataDAO;
    @Autowired
    private StockDAO stockDAO;


    private Map<String , StockEntity> stockEntities;
    private List<BenchmarkEntity> benchmarkEntityList;
    private Map<String , List<String>> boardDistribution;
    private Map<String , List<StockEntity>> boardDistributionEntity;
    private List<String> boards;
//    private List<StockdataEntity> todayAllStockData;
    private Map<String , StockdataEntity> todayAllStockData;

    private boolean hasPrepared = false;
    @Override
    public void prepareCache() {
        if(!hasPrepared){
            getTodayAllStockData();
            makeBoardDistribution();
            getAllStocks();
            getAllBenchMarksList();
            getAllBoradName();
            hasPrepared = true;
        }

    }

    public List<StockdataEntity> getTodayAllStockData() {
        return new ArrayList<>(getTodayAllStockMap().values());
    }

    public List<StockdataEntity> getTodaySomeStocks(List<String> codes){
        List<StockdataEntity> entities = new ArrayList<>(codes.size());
        Map<String , StockdataEntity> entitiesMap = getTodayAllStockMap();
        for (String code : codes){
            entities.add(entitiesMap.get(code));
        }
        return entities;

    }

    private Map<String , StockdataEntity> getTodayAllStockMap(){
        if (todayAllStockData == null || todayAllStockData.get("sh600004").getDate().before(new Date())) {
            List<StockdataEntity> entities = stockDataDAO.getAllStockData();
            todayAllStockData = new HashMap<>(entities.size()*2);
            for (StockdataEntity entity : entities){
                todayAllStockData.put(entity.getCode() , entity);
            }
        }
        return todayAllStockData;
    }


    public List<StockEntity> getAllStocks() {
        if(stockEntities == null){
            List<StockEntity> entities = stockDAO.getAllStocks();
            stockEntities = new HashMap<>(entities.size()*2);
            for (StockEntity entity : entities){
                stockEntities.put(entity.getCode() , entity);
            }
        }

        return new ArrayList<>(stockEntities.values());
    }

    public StockEntity getStockDescription(String code) {
        if(stockEntities == null){
            getAllStocks();
        }
        return stockEntities.get(code);
    }


    public List<BenchmarkEntity> getAllBenchMarksList() {
        if(benchmarkEntityList == null){
            benchmarkEntityList = benchMarkDAO.getAllBenchMarksList();
        }
        return benchmarkEntityList;
    }



    public List<String> getAllBoradName() {
        if(boards == null){
            boards = stockDAO.getAllBoardName();
        }

        return boards;
    }

    /**
     * 获得所有板块和他们对应的股票代号
     */
    public Map<String , List<String> > getBoardDistribution(){

        if(boardDistribution == null){
            makeBoardDistribution();
        }


        return boardDistribution;


    }
    public Map<String , List<StockEntity>> getBoardDistributionEntity(){
        if(boardDistributionEntity == null){
            makeBoardDistribution();
        }


        return boardDistributionEntity;
    }

    private void makeBoardDistribution(){
        boardDistribution = new HashMap<>(500);
        boardDistributionEntity = new HashMap<>(500);
        List<String> boards = stockDAO.getAllBoardName();
        List<StockEntity> stocks = stockDAO.getAllStocks();

        for (String board : boards){
            boardDistribution.put(board , new ArrayList<>(50));
            boardDistributionEntity.put(board , new ArrayList<>(50));
        }

        for (StockEntity entity : stocks){
            boardDistribution.get(entity.getBoard()).add(entity.getCode());
            boardDistributionEntity.get(entity.getBoard()).add(entity);
        }
    }



}
