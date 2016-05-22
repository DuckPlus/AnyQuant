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
 * Created by dsn on 2016/5/22.
 */
@Controller
@RequestMapping("/Mock")
public class MockController {

    @RequestMapping("/getMockStockList")
    @ResponseBody
    public void getMockBoardDistribution(){

    }
}
