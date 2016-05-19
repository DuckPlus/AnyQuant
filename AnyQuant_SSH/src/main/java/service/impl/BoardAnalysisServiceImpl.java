package service.impl;

import service.BoardAnalysisService;
import vo.BoardDistributionVO;
import vo.CompareBoardAndBenchVO;

import java.util.List;

/**
 * @author Qiang
 * @date 16/5/19
 */
public class BoardAnalysisServiceImpl implements BoardAnalysisService {
    @Override
    public List<CompareBoardAndBenchVO> getBoardAndBenchChartData(String boardName) {
        return null;
    }

    @Override
    public List<CompareBoardAndBenchVO> getBoardAndBenchChartData(String boardName, int offset) {
        return null;
    }

    @Override
    public List<BoardDistributionVO> getBoardDistributionChartData(String boardName) {
        return null;
    }

    @Override
    public List<BoardDistributionVO> getBoardDistributionChartData(String boardName, int offset) {
        return null;
    }
}
