package controller;

import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.BoardAnalysisService;
import vo.BoardDistributionVO;
import vo.CompareBoardAndBenchVO;

import java.util.List;

/**
 * @author Qiang
 * @date 16/5/22
 */
@Controller
@RequestMapping("/Board")
@ResponseBody
public class BoardController {

    @Autowired
    private BoardAnalysisService service;

    /**
     * 获得所有板块的名称
     */
    @RequestMapping("/getAllBoards")
    public List<String> getAllBoardName(){
        return service.getAllBoradName();
    }

    @RequestMapping("/getAllBoardsAndStockData")
    public JSONArray getAllBoardAndStockData(){
        return service.getAllBoardAndStockData();
    }


    @RequestMapping("/checkBoard")
    public boolean checkIfBoardExist(String board){
        return service.checkIfBoardExist(board);
    }

    /**
     * 获得沪深300和板块的对比数据,默认返回半年的数据
     */
    @RequestMapping("/getCompareData")
    public List<CompareBoardAndBenchVO> getBoardAndBenchChartData(String boardName){
        return service.getBoardAndBenchChartData(boardName , 180);
    }
    /**
     * 获得沪深300和板块的对比数据
     * @param offset 偏移量,即回溯的数据时间,可为任意整数值,如30天表示一个月 正值!!
     */
    @RequestMapping("/getCompareDataWithOffset")
    public List<CompareBoardAndBenchVO> getBoardAndBenchChartData(String boardName , int offset){
        return service.getBoardAndBenchChartData(boardName , offset);
    }

    /**
     * Get the distribution of the board
     * 返回板块所属的股票的分布,暂时根据 《股票权重(即成交量)、ID、名称、涨跌幅》
     */
    @RequestMapping("/getBoardDistribution")
    List<BoardDistributionVO> getBoardDistributionChartData(String boardName){
        return service.getBoardDistributionChartData(boardName);
    }








}
