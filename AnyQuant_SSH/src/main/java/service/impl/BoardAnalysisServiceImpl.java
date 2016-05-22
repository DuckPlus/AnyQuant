package service.impl;

import DAO.BenchMarkDAO;
import DAO.BoardDAO;
import DAO.StockDataDAO;
import entity.BenchmarkdataEntity;
import entity.StockdataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import service.BoardAnalysisService;
import service.helper.StockHelper;
import util.Configure;
import util.DateCalculator;
import util.MyDate;
import vo.BoardDistributionVO;
import vo.CompareBoardAndBenchVO;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Qiang
 * @date 16/5/19
 */
@Service
public class BoardAnalysisServiceImpl implements BoardAnalysisService {
    @Autowired
    BenchMarkDAO benchMarkDAO;

    @Autowired
    BoardDAO boardDAO;
    @Autowired
    StockDataDAO stockDataDAO;

    @Override
    public List<CompareBoardAndBenchVO> getBoardAndBenchChartData(String boardName, int offset) {
        return getBoardAndBenchChartData(boardName , offset , Configure.HUSHEN300);
    }

    @Override
    public List<CompareBoardAndBenchVO> getBoardAndBenchChartData(String boardName, int offset, String bench) {
        List<BenchmarkdataEntity> benchDatas = benchMarkDAO.getBenchMarkByTime(benchMarkDAO.getBenchMarkCodeByName(bench) , DateCalculator.getAnotherDay(-offset) , DateCalculator.getToDay());
        try {
            if(benchDatas != null){
                double[] profits = new double[benchDatas.size()];
                for (int i = 0; i < benchDatas.size(); i++) {
                    profits[i] = benchDatas.get(i).getClose();
                }
                profits = StockHelper.computeStockProfit(profits);

                double[] boardProfit = computeBoardData(boardName , offset);

                int len = Math.min(profits.length , boardProfit.length);

                List<CompareBoardAndBenchVO> vos = new ArrayList<>(len);
                for (int i = 0; i < len; i++) {
                    vos.add(new CompareBoardAndBenchVO(DateCalculator.SQLDateToMyDate(benchDatas.get(i).getDate()) , boardName , profits[i] , boardProfit[i]));
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
        List<String> stocks = boardDAO.getAllStocks(boardName);

        List<BoardDistributionVO> vos = new ArrayList<>(stocks.size());
        double sum = 0;
        StockdataEntity entity;
        for (String stock :stocks){
            entity = stockDataDAO.getStockData(stock);
            sum += entity.getTurnoverValue();
            vos.add(makeDistributionVO(entity));
        }
        for (BoardDistributionVO vo : vos){
            vo.board = boardName;
            vo.weight = vo.turnoverValue/sum;
        }






        return vos;
    }

    private double[] computeBoardData(String boardName , int offset){


        return null;
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


        return vo;


    }
}
