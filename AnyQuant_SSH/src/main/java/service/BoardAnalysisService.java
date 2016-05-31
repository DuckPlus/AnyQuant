package service;

import net.sf.json.JSONArray;
import vo.BoardDistributionVO;
import vo.CompareBoardAndBenchVO;

import java.util.List;

/**
 * 行业分析接口
 * @author Qiang
 * @date 16/5/19
 */
public interface BoardAnalysisService {
    /**
     * 获得沪深300和板块的对比数据
     * @param offset 偏移量,即回溯的数据时间,可为任意整数值,如30天表示一个月
     * @return
     */
    List<CompareBoardAndBenchVO> getBoardAndBenchChartData(String boardName , int offset);

    /**
     * 获得大盘和板块的对比数据
     * @param boardName
     * @param offset
     * @param bench 大盘代码
     * @return
     */
    List<CompareBoardAndBenchVO> getBoardAndBenchChartData(String boardName , int offset , String bench);

    /**
     * Get the distribution of the board
     * 返回板块所属的股票的分布,暂时根据 《股票权重、ID、名称、涨跌幅》
     * @param boardName
     * @return
     */
    List<BoardDistributionVO> getBoardDistributionChartData(String boardName );

    /**
     * 获得所有板块的名字
     * @return
     */
    List<String> getAllBoradName();

    JSONArray getAllBoardAndStockData();

    boolean checkIfBoardExist(String board);


}
