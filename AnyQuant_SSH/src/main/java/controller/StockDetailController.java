package controller;

import data.StockDataService;
import entity.StockEntity;
import entity.StockdataEntity;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.StockAnalyseService;
import service.StockService;
import util.DateCalculator;
import util.MyDate;
import util.ReflectHelper;
import util.enumration.AnalysisFactor;
import util.enumration.FactorJudge;
import vo.EvaluationVO;
import vo.FactorWeightVO;
import vo.Factor_VO;
import vo.NewsVO;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.List;

/**
 * 股票详情界面Controller
 * @author Qiang
 * @date 16/5/18
 */
@Controller
@RequestMapping("/StockDetail")
@ResponseBody
public class StockDetailController {
    @Autowired
    private StockService stockService;
    @Autowired
    private StockAnalyseService analyseService;
    /**
     * Return all the detail message(non-real-time)
     * 返回单只股票详细信息(非实时数据)
     */
//    @RequestMapping("/description")
//    public StockEntity getStockDescription(String code){
//        return stockService.getStockDescription(code);
//    }

    @RequestMapping("/description")
    public JSONObject getStockDescriptionWithData(String code) throws ClassNotFoundException {
        StockEntity entity = stockService.getStockDescription(code);
        JSONObject object = new JSONObject();

        Class<?> stockEntityClass = Class.forName("entity.StockEntity");

        Field[] fields = stockEntityClass.getDeclaredFields();
        for (Field field : fields){
//            System.out.println(field.getName());
            if(field.getName().equalsIgnoreCase("listDate")){
                object.put("listDate" , DateCalculator.SQLDateToMyDate(entity.getListDate()));
            }else {
                object.put(field.getName() , ReflectHelper.getValueWithoutLower(entity , field.getName()));
            }

        }
        StockdataEntity stockdataEntity = stockService.getTodayStockVO(code);
        object.put("close" , stockdataEntity.getClose());
        object.put("changeRate" , stockdataEntity.getChangeRate());
        return  object;


    }

    /**
     * Return the recent newsVO of the giving stock
     */
    @RequestMapping("/news")
    public List<NewsVO> getRelatedNewsVO(String code){
        return analyseService.getRelatedNewsVO(code);
    }

    /**
     * Get the evaluation of the stock, including mark,suggestion and analysis
     * 获得对某只股票的评价,包括评分、建议、分析等等
     */
    @RequestMapping("/evaluation")
    public EvaluationVO getEvaluation(String code){
        return analyseService.getEvaluation(code);
    }

    /**
     * Get the board-related stocks and their last trading days' data
     * 获得某只股票同板块的其他股票最近一个交易日的情况
     */
    @RequestMapping("/boardRelatedStock")
    public List<StockdataEntity> getBoardRelatedStockMessage(String code){
        return analyseService.getBoardRelatedStockMessage(code);
    }

    /**
     * Get the region-related stocks and their last trading days' data
     * 获得某只股票同地域的其他股票最近一个交易日的情况
     */
    @RequestMapping("/regionRelatedStock")
    public List<StockdataEntity> getRegionRelatedStockMessage(String code){
        return analyseService.getRegionRelatedStockMessage(code);
    }

    /**
     * Get the factor data in a certain time<br>This method simply return the recent data change
     * 返回某个股票某个因子在最近一段时间内的变化
     * @param code   股票代码
     * @param factor 因子中文名称,可通过getAllFactors获得所有可用的因子中文名称
     * @param offset 偏移值,即从什么时候开始回溯
     * @return       该因子在给定时间内的值变化趋势
     */
    @RequestMapping("/getFactorChange")
    public List<Factor_VO> getFactorChange(String code , String factor , int offset){
        return analyseService.getFactorVO(code,AnalysisFactor.getAnalysisFactor(factor) ,offset);
    }

    /**
     * Get all the usable factors
     * 返回所有可用的评判因子
     */
    @RequestMapping("/getAllFactors")
    public List<String> getAllFactors(){
        return analyseService.getAllFactors();
    }
    /**
     * Get all the usable factors that use to judge factors
     * 返回所有可用的评判因子的因子
     */
    @RequestMapping("/getAllJudgeFactors")
    public List<String> getAllFactorJudgeFactors(){
        return analyseService.getAllFactorJudgeFactors();
    }

    /**
     * Get the most useful factors with
     * @param code 股票编码
     * @param timeLen 时间跨度,即因子有效性计算所基于的时间段的数据(距今天的时间),如一个月,则用过去一个月的数据计算
     * @param factorJudge 评价因子,即如何评价是否有效,评价因子可通过getAllFactorJudgeFactors获得
     *                    若为空,使用默认值IC(信息系数)
     * @return 较为有效的因子(目前返回6个),以及他们的有效程度、因子值
     */
    @RequestMapping("/getMostUsefulFactors")
    public List<FactorWeightVO> getMostUsefulFactors(String code , int timeLen, String factorJudge){
        return analyseService.getMostUsefulFactors(code, timeLen , FactorJudge.getFactorJudgeFactors(factorJudge) );
    }

}
