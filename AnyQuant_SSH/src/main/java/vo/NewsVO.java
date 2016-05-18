package vo;

/**
 * Created by 67534 on 2016/5/18.
 */
public class NewsVO {

    public String newsID;
    public String publishDate;
    public String title;
    public String summary;
    public String author;
    public String source;

    public NewsVO(){
        this.author="Unknown";

    }

    public NewsVO(String id,String date,String title,
                  String summary,String author,String source){
        this.newsID=id;
        this.publishDate=date;
        this.title=title;
        this.summary=summary;
        this.author=author;
        this.source=source;
    }


}
