package controller;

import entity.BenchmarkEntity;
import entity.BenchmarkdataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.BenchMarkService;
import util.MyDate;

import java.util.List;

/**
 * 大盘控制器
 * @author Qiang
 * @date 16/5/10
 */
@Controller
@RequestMapping("/BenchMark")
public class BenchDataController {

    @Autowired
    private BenchMarkService benchMarkService;

    /**
     * 返回大盘列表的信息
     * @return
     */
    @RequestMapping("/getBenchList")
    @ResponseBody
    public List<BenchmarkEntity> getBenchMarkList(){
        return benchMarkService.getAllBenchMarksList();
    }

    /**
     * 返回最近一个交易日所有大盘的交易数据
     * @return
     */
    @RequestMapping("/getBenchDataList")
    @ResponseBody
    public  List<BenchmarkdataEntity> getBenchDataList(){
        return benchMarkService.getAllBenchMarksDataList();
    }

    /**
     * 返回某只股票某段时间的数据
     * @return
     */
    @RequestMapping("/getBenchDataListByTime")
    @ResponseBody
    public  List<BenchmarkdataEntity> getStockListByTime(@RequestParam("code") String stockCode , @RequestParam("start") String start, @RequestParam("end") String end){

        return benchMarkService.getBenchMarkByTime(stockCode , MyDate.getDateFromString(start) ,MyDate.getDateFromString(end) );
    }



}
