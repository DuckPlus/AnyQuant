package controller;

import entity.StockEntity;
import entity.StockdataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import service.StockService;
import util.DateCalculator;
import util.MyDate;

import java.util.List;

/**
 * 股票主Controller
 * @author Qiang
 * @date 16/5/6
 */
@Controller
@RequestMapping("/Stock")
public class StockDataController {

    @Autowired
    private StockService stockService;

    /**
     * 返回所有股票的信息
     * @return
     */
    @RequestMapping("/getStockList")
    @ResponseBody
    public  List<StockEntity> getStockList(){
        return stockService.getAllStocks();
    }

    /**
     * 返回最近一个交易日所有股票的交易数据
     * @return
     */
    @RequestMapping("/getStockDataList")
    @ResponseBody
    public  List<StockdataEntity> getStockDataList(){
        return stockService.getTodayAllStockData();
    }

    /**
     * 返回某只股票某段时间的数据
     * @return
     */
    @RequestMapping("/getStockDataListByTime")
    @ResponseBody
    public  List<StockdataEntity> getStockListByTime(@RequestParam("code") String stockCode , @RequestParam("start") String start, @RequestParam("end") String end){

        return stockService.getStocksByTime(stockCode , MyDate.getDateFromString(start) ,MyDate.getDateFromString(end) );
    }
    /**
     * 返回某只股票以某段时间开始向前偏移一段时间的数据
     * @param stockCode
     * @param end 结束日期,若无,表示今天
     * @param offset 偏移量
     * @return
     */
    @RequestMapping("/getStockDataListByOffset")
    @ResponseBody
    public List<StockdataEntity> getStockDataByOffset(@RequestParam("code") String stockCode , @RequestParam("end") String end , @RequestParam("offset") String offset){
        MyDate endDate;
        if(end.equals("")){
            endDate = DateCalculator.getToDay();
        }else {
            endDate = MyDate.getDateFromString(end);
        }


        return stockService.getStocksByTime(stockCode ,DateCalculator.getAnotherDay(endDate ,Integer.parseInt(offset)) , endDate );
    }








}
