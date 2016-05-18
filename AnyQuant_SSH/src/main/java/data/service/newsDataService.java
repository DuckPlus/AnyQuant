package data.service;

import vo.NewsVO;

import java.util.List;

/**
 * Created by 67534 on 2016/5/18.
 */
public interface newsDataService {

    List<String> getRelatedNewsID(String stockCode);

    NewsVO getNewsVO(String newsID);

    List<NewsVO> getRelatedNewsVO(String stockCode);

}
