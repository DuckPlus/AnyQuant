package data.impl;

import data.NewsDataService;
import data.helper.ConnectionHelper;
import data.helper.TransferHelper;
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
 *
 */
@Repository
public class NewsDataServiceImpl implements NewsDataService {

    private static final int Max_newsCount=10;
    private static final int Max_dateGap=5;
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
        MyDate start  = DateCalculator.getAnotherDay(-Max_dateGap);
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
        System.out.println("newsID: "+vo.newsID);
        System.out.println("date: "+vo.publishDate);
        System.out.println("title: "+vo.title);
        System.out.println("summary: "+vo.summary);
        System.out.println("source: "+vo.source);
        return vo;
    }

    @Override
    public List<NewsVO> getRelatedNewsVO(String stockCode) {
        ArrayList<NewsVO> result = new ArrayList<>();
        List<String> newsIDs = getRelatedNewsID(stockCode);
        for(String id:newsIDs){
            NewsVO vo = getNewsVO(id);
            if(id!=null){
                result.add(vo);
            }
        }
        return result.size()==0 ? null:result;
    }
}
