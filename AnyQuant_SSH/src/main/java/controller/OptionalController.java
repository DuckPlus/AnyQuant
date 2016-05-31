package controller;

import entity.StockdataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.OptionalStockService;
import util.Configure;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Qiang
 * @date 16/5/10
 */
@Controller
@RequestMapping("/Optional")
public class OptionalController {
    @Autowired
    private OptionalStockService optionalService;

    @RequestMapping("/get")
    @ResponseBody
    public List<StockdataEntity> getOptionalStock(Model model , HttpServletRequest request){
        String id = (String) request.getSession().getAttribute(Configure.USERID_KEY);
        System.out.print(id);

        return checkIfLogin(model , id) ? optionalService.getOptionalStocksDatas(id) : null;

    }
    @RequestMapping("/check")
    @ResponseBody
    public boolean checkIfStockExist(Model model ,HttpServletRequest request , @RequestParam("code") String code){
        String id = (String) request.getSession().getAttribute(Configure.USERID_KEY);

        if(checkIfLogin(model , id)){
            return optionalService.ifStockExist(id , code);
        }else {
            return false;
        }
    }

    @RequestMapping("/add")
    @ResponseBody
    public boolean addOptionalStock(Model model ,HttpServletRequest request , @RequestParam("code") String code){
        String id = (String) request.getSession().getAttribute(Configure.USERID_KEY);

        if(checkIfLogin(model , id)){
            if(optionalService.ifStockExist(id , code)){
                model.addAttribute(Configure.ERROR_REASON , Configure.ERROR_HAD_EXISTS);
                return false;
            }else {
                return optionalService.addStockCode(id , code);
            }
        }else {
            return false;
        }
    }

    @RequestMapping("/del")
    @ResponseBody
    public boolean delOptionalStock(Model model ,HttpServletRequest request , @RequestParam("code") String code){
        String id = (String) request.getSession().getAttribute(Configure.USERID_KEY);
        if(checkIfLogin(model , id)){
            if(!optionalService.ifStockExist(id , code)){
                model.addAttribute(Configure.ERROR_REASON , Configure.ERROR_NOT_EXISTS);
                return false;
            }else {
                return optionalService.deleteStockCode(id , code);
            }
        }else {
            return false;
        }




    }

    @RequestMapping("/getBoardDistribution")
    @ResponseBody
    public Map<String , Integer> getBoardDistribution(Model model , HttpServletRequest request){
        String id = (String) request.getSession().getAttribute(Configure.USERID_KEY);
        if(checkIfLogin(model , id)){
            return optionalService.getBoardDistributionMap(id);
        }else {
            return null;
        }
    }


//    @RequestMapping("/getMockBoardDistribution")
//    @ResponseBody
//    public Map<String , Integer> getMockBoardDistribution(){
//
//        Map<String, Integer> regions = new HashMap<>(34);
//        regions.put("汽油",30);
//        regions.put("猪肉",24);
//        regions.put("房地产",2);
//        regions.put("YSL",12);
//
//        return regions;
//    }
    @RequestMapping("/getRegionDistribution")
    @ResponseBody
    public Map<String , Integer> getRegionDistribution(Model model , HttpServletRequest request){
        String id = (String) request.getSession().getAttribute(Configure.USERID_KEY);
        if(checkIfLogin(model , id)){
            return optionalService.getRegionDistributionMap(id);
        }else {
            return null;
        }
    }




    private boolean checkIfLogin(Model model ,String id ){

        if(id == null){
            model.addAttribute(Configure.ERROR_REASON , Configure.ERROR_NOT_LOGIN);
            return false;
        }else {
            return true;
        }
    }


}
