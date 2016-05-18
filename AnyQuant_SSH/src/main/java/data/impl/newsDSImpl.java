package data.impl;

import data.helper.ConnectionHelper;
import data.helper.TransferHelper;
import data.service.newsDataService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Repository;
import util.DateCalculator;
import util.MyDate;
import util.enumration.API_TYPE;
import vo.NewsVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 67534 on 2016/5/18.
 */

@Repository

public class newsDSImpl implements newsDataService {

    private static final int Max_newsCount=10;
    /**
     * 根据股票获取最近的新闻，默认为最近5天的新闻，最多返回Max_newsCount条
     * @param stockCode
     * @return
     */
    @Override
    public List<String> getRelatedNewsID(String stockCode) {
        List<String> newsCodes = new ArrayList<>();

        String shortCode = stockCode.substring(2);
        MyDate end = DateCalculator.getToDay();
        MyDate start  = DateCalculator.getAnotherDay(-5);
        JSONObject jo = ConnectionHelper.requestAPI
                (API_TYPE.GET_RelatedNews,shortCode,
                        start.DateToStringSimple(),end.DateToStringSimple());
        if(jo.getInt("retCode")==0){
            return null;
        }

        JSONArray ja = jo.getJSONArray("data");
        for(int i=0;i<ja.size();i++){

            newsCodes.add(( (JSONObject)ja.get(i)).getString("newsID") );
            if(i>=Max_newsCount){
                break;
            }
         //   System.out.println(newsCodes.get(i));
        }
       // System.out.println("size: "+newsCodes.size()+"  -----------------");

        return  newsCodes;
    }

    @Override
    public NewsVO getNewsVO(String newsID) {
        NewsVO vo ;
        JSONObject jo = ConnectionHelper.requestAPI
                (API_TYPE.GET_NewsInfo,newsID);

        if(jo.getInt("retCode")==0){
            return null;
        }

        JSONArray ja = jo.getJSONArray("data");
        JSONObject vojo = ja.getJSONObject(0);
        vo=TransferHelper.JSONObjectToNewsVO(vojo);
        return vo;
    }

    @Override
    public List<NewsVO> getRelatedNewsVO(String stockCode) {
        return null;
    }
}
