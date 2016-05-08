package controller;

import DAO.StockDAO;
import entity.StockEntity;
import entity.StockdataEntity;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.StockService;

import java.io.IOException;
import java.util.List;

/**
 * @author Qiang
 * @date 16/5/6
 */
@Controller
@RequestMapping("/TableView")
public class TableViewController {

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












}
