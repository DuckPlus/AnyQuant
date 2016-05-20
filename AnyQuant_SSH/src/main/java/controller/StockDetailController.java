package controller;

import entity.StockEntity;
import org.hibernate.annotations.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.StockService;

/**
 * 股票详情界面Controller
 * @author Qiang
 * @date 16/5/18
 */
@Controller
@RequestMapping("/StockDetail")
public class StockDetailController {
    @Autowired
    StockService stockService;

    /**
     * Return all the detail message(non-real-time)
     * 返回单只股票详细信息(非实时数据)
     * @param code
     */
    @RequestMapping("/description")
    @ResponseBody
    public StockEntity getStockDescription(@RequestParam("code") String code){
        return stockService.getStockDescription(code);
    }





}
