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
import vo.CompareBoardAndBenchVO;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by dsn on 2016/5/22.
 */
@Controller
@RequestMapping("/Mock")
public class MockController {
    /**
     * 获得沪深300和板块的对比数据,默认返回半年的数据
     */
    @RequestMapping("/getCompareData")
    @ResponseBody
    public List<CompareBoardAndBenchVO> getBoardAndBenchChartData(String boardName){
       List<CompareBoardAndBenchVO> ans=new ArrayList<>(180);
        for(int i=0;i<30;i++){
            CompareBoardAndBenchVO vo= new CompareBoardAndBenchVO(new MyDate(2016,1,i),"钢铁",i*10,i*10+2);
            ans.add(vo);
        }
        return ans;
    }

}
