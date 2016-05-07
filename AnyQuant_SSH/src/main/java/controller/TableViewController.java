package controller;

import DAO.StockDAO;
import entity.StockEntity;
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
    StockService stockService;

    @RequestMapping("/getStockList")
    @ResponseBody
    public  List<StockEntity> getStockList(){
        List<StockEntity> stockEntities = stockService.getAllStocks();

       return stockEntities;
//

    }


}
