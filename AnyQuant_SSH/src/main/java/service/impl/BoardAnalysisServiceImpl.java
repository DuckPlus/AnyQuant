package service.impl;

import DAO.BenchMarkDAO;
import entity.BenchmarkdataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import service.BoardAnalysisService;
import service.helper.StockHelper;
import util.Configure;
import util.DateCalculator;
import vo.BoardDistributionVO;
import vo.CompareBoardAndBenchVO;

import java.util.List;

/**
 * @author Qiang
 * @date 16/5/19
 */
@Service
public class BoardAnalysisServiceImpl implements BoardAnalysisService {
    @Autowired
    BenchMarkDAO benchMarkDAO;



    @Override
    public List<CompareBoardAndBenchVO> getBoardAndBenchChartData(String boardName, int offset) {
        return getBoardAndBenchChartData(boardName , offset , Configure.HUSHEN300);
    }

    @Override
    public List<CompareBoardAndBenchVO> getBoardAndBenchChartData(String boardName, int offset, String bench) {
        List<BenchmarkdataEntity> benchDatas = benchMarkDAO.getBenchMarkByTime(benchMarkDAO.getBenchMarkCodeByName(bench) , DateCalculator.getAnotherDay(-offset) , DateCalculator.getToDay());

        if(benchDatas != null){
             double[] profits = new double[benchDatas.size()];
            for (int i = 0; i < benchDatas.size(); i++) {
                profits[i] = benchDatas.get(i).getClose();
            }
            profits = StockHelper.computeStockProfit(profits);

        }


        return null;
    }


    @Override
    public List<BoardDistributionVO> getBoardDistributionChartData(String boardName, int offset) {
        return null;
    }
}
