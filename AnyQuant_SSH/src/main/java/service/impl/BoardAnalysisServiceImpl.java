package service.impl;

import DAO.BenchMarkDAO;

import DAO.StockDAO;
import DAO.StockDataDAO;
import entity.BenchmarkdataEntity;

import entity.StockdataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BoardAnalysisService;
import service.helper.StockHelper;
import util.Configure;
import util.DateCalculator;
import vo.BoardDistributionVO;
import vo.CompareBoardAndBenchVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Qiang
 * @date 16/5/19
 */
@Service
public class BoardAnalysisServiceImpl implements BoardAnalysisService {
    @Autowired
    private BenchMarkDAO benchMarkDAO;
    @Autowired
    private StockDataDAO stockDataDAO;
    @Autowired
    private StockDAO stockDAO;
    @Override
    public List<CompareBoardAndBenchVO> getBoardAndBenchChartData(String boardName, int offset) {
        return getBoardAndBenchChartData(boardName , offset , Configure.HUSHEN300);
    }

    @Override
    public List<CompareBoardAndBenchVO> getBoardAndBenchChartData(String boardName, int offset, String bench) {
        List<BenchmarkdataEntity> benchData = benchMarkDAO.getBenchMarkByTime(benchMarkDAO.getBenchMarkCodeByName(bench) , DateCalculator.getAnotherDay(-offset) , DateCalculator.getToDay());



        try {
            if(benchData != null && benchData.size() != 0){
                double[] profits = new double[benchData.size()];
                for (int i = 0; i < benchData.size(); i++) {
                    profits[i] = benchData.get(i).getClose();
                }
//                System.out.println("***************");
//                System.out.println("大盘未计算数据" + Arrays.toString(profits)+ "len" + profits.length);
                profits = StockHelper.computeAccumulativeProfit(profits);
//                System.out.println("大盘数据" + Arrays.toString(profits) + "len"+ profits.length);
                double[] boardProfit = computeBoardData(boardName , offset);
//                System.out.println("--------------");
//                System.out.println("板块未计算前数据" +Arrays.toString(boardProfit));
                boardProfit = StockHelper.computeAccumulativeProfit(boardProfit);

//                System.out.println("板块计算后数据" + Arrays.toString(boardProfit));
                int len = Math.min(profits.length , boardProfit.length);

                List<CompareBoardAndBenchVO> vos = new ArrayList<>(len);
                for (int i = 0; i < len; i++) {
                    vos.add(new CompareBoardAndBenchVO(DateCalculator.SQLDateToMyDate(benchData.get(i).getDate()) , boardName , boardProfit[i], profits[i] ));
                }
                return vos;

            }
        }catch (ArrayIndexOutOfBoundsException ex){
            ex.printStackTrace();
            return null;
        }



        return null;
    }


    @Override
    public List<BoardDistributionVO> getBoardDistributionChartData(String boardName) {
        List<String> stocks = stockDAO.getBoardRealatedStockCodes(boardName);

        List<BoardDistributionVO> vos = new ArrayList<>(stocks.size());
        double sum = 0;
        List<StockdataEntity> entities = stockDataDAO.getStockData(stocks);
        for (StockdataEntity entity : entities){
            sum += entity.getTurnoverValue();
            vos.add(makeDistributionVO(entity));
        }
//        for (String stock :stocks){
//            entity = stockDataDAO.getStockData(stock);
//            sum += entity.getTurnoverValue();
//            vos.add(makeDistributionVO(entity));
//        }
        for (BoardDistributionVO vo : vos){
            vo.board = boardName;
            vo.weight = vo.turnoverValue/sum;
        }
        return vos;
    }

    @Override
    public List<String> getAllBoradName() {
        return stockDAO.getAllBoardName();
    }

    private double[] computeBoardData(String boardName , int offset){
//        System.out.println(boardName);
        List<String> stocks = stockDAO.getBoardRealatedStockCodes(boardName);
        if(stocks.size() == 0){
            return null;
        }
        List<StockdataEntity> dayData = new ArrayList<>(stocks.size());
        double[] result = new double[offset];
        List<StockdataEntity> allData = stockDataDAO.getStockData(stocks , DateCalculator.getAnotherDay(-offset) , DateCalculator.getToDay());
//        for (StockdataEntity entity : allData){
//            System.out.println(entity.getDate().toString() + entity.getName()  + entity.getClose());
//        }

        int days = allData.size()/stocks.size();

        for (int i = 0; i < days; i++) {
            for (int j = 0 ; j < stocks.size() ; j++){
                dayData.add(allData.get(days*j + i));
//                System.out.println("aaa"+allData.get(days*j + i).getDate() + allData.get(days*j + i).getName());
            }
            result[i] = computeBoardDate(dayData);

            dayData.clear();
        }
        return result;
    }



    //目前按照总市值占板块总的总市值的比值作为板块股票权重
    private static double computeBoardDate(List<StockdataEntity> entities){

        double sum = 0;
        for (StockdataEntity entity : entities){
            sum += entity.getTotalMarketValue();
        }
        double value = 0;
        for (StockdataEntity entity : entities){
            value += entity.getClose()*(entity.getTotalMarketValue()/sum);
        }

        return value;
    }

    private static BoardDistributionVO makeDistributionVO(StockdataEntity entity){
        BoardDistributionVO vo = new BoardDistributionVO();
        vo.code = entity.getCode();
        vo.stockName = entity.getName();
        vo.open = entity.getOpen();
        vo.close = entity.getClose();
        vo.high = entity.getHigh();
        vo.low = entity.getLow();
        vo.turnoverRate = entity.getTurnoverRate();
        vo.turnoverValue = entity.getTurnoverValue();
        vo.turnoverVol = entity.getTurnoverVol();
        vo.changeRate = entity.getChangeRate();

        return vo;


    }
}
