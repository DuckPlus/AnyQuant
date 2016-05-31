package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.StockService;
import util.MyDate;
import vo.DealVO;
import vo.OHLC_VO;
import vo.TimeSharingVO;

import java.util.List;

/**
 * 股票图标类数据Controller
 * @author Qiang
 * @date 16/5/8
 */
@Controller
@RequestMapping("/chartData")
public class StockChartDataViewController {
    @Autowired
    private StockService stockService;

    /**
     * 获取股票某段时间内的成交量
     * @param type month\week\day
     * @param stockCode 代码
     * @param start 起始日期
     * @param end   结束如期
     * @return
     */
    @RequestMapping("/deal")
    @ResponseBody
    public List<DealVO> getDealVO(@RequestParam("type") String type , @RequestParam("code") String stockCode , @RequestParam("start") String start, @RequestParam("end") String end){

        if(type.equals("month")){
            return  stockService.getMonthDealVOs(stockCode , MyDate.getDateFromString(start) , MyDate.getDateFromString(end));
        }else if(type.equals("week")){
            return  stockService.getWeekDealVOs(stockCode , MyDate.getDateFromString(start) , MyDate.getDateFromString(end));
        }else if(type.equals("day")){
            return  stockService.getDayDealVOs(stockCode , MyDate.getDateFromString(start) , MyDate.getDateFromString(end));
        }else {
            return null;
        }



    }

    @RequestMapping("/TimeSharingVO")
    @ResponseBody
    public List<TimeSharingVO> getTimeSharingVO(String code){
        return stockService.getSharingVOs(code);
    }

    @RequestMapping("/OHLC")
    @ResponseBody
    public List<OHLC_VO> getOHLC_VO(@RequestParam("type") String type , @RequestParam("code") String stockCode , @RequestParam("start") String start, @RequestParam("end") String end){
        if(type.equals("month")){
            return  stockService.getMonthOHLC_Data(stockCode , MyDate.getDateFromString(start) , MyDate.getDateFromString(end));
        }else if(type.equals("week")){
            return  stockService.getWeekOHLC_Data(stockCode , MyDate.getDateFromString(start) , MyDate.getDateFromString(end));
        }else if(type.equals("day")){
            return  stockService.getDayOHLC_Data(stockCode , MyDate.getDateFromString(start) , MyDate.getDateFromString(end));
        }else {
            return null;
        }

    }


}
