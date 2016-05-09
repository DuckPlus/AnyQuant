package controller;

import DAO.StockDAO;
import entity.StockEntity;
import entity.StockdataEntity;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.StockService;
import util.MyDate;

import java.io.IOException;
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










}
