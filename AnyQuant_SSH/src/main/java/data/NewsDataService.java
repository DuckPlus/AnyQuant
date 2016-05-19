package data;

import vo.NewsVO;

import java.util.List;

/**
 * @author ss
 * @date 16/5/18
 */
public interface NewsDataService {
    /**
     * 根据股票获取最近的新闻，默认为最近Max_dateGap天的新闻，最多返回Max_newsCount条
     * @param stockCode
     * @return
     */
    List<String> getRelatedNewsID(String stockCode);


    /**
     * 根据新闻id获取新闻vo
     * @param newsID
     * @return
     */
    NewsVO getNewsVO(String newsID);

    /**
     * 获取某只股票最近相关的不多于Max_newsCount条新闻vo
     * @param stockCode
     * @return
     */
    List<NewsVO> getRelatedNewsVO(String stockCode);

}