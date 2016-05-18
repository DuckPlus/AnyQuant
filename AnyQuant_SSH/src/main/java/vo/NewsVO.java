package vo;

/**
 * Created by 67534 on 2016/5/18.
 */
public class NewsVO {

    public String newsID;
    public String publishDate;
    public String title;
    public String summary;
    public String source;

    public NewsVO(){


    }

    public NewsVO(String id,String date,String title,
                  String summary,String source){
        this.newsID=id;
        this.publishDate=date;
        this.title=title;
        this.summary=summary;
        this.source=source;
    }


}
