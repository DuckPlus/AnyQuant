package data.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vo.NewsVO;

import static org.junit.Assert.fail;

/**
 * Created by 67534 on 2016/5/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
        (locations = {"classpath:/META-INF/applicationContext.xml","classpath:/META-INF/infrastructure.xml"})

public class newsDSImplTest {
    @Autowired
    NewsDataServiceImpl ds;
    @Test
    public void getRelatedNews() throws Exception {
         String code = "sh600216";
         ds.getRelatedNewsID(code);
    }
    @Test
    public void getNewsVO() throws Exception {
        //21375210   21396338 21338303
        String newsID= "21375210";
        NewsVO vo = ds.getNewsVO(newsID);
        if(vo==null){
            fail("null-------");
        }else if(!vo.newsID.equals(newsID)) {
            fail("id error-------");
        }else{
            System.out.println("newsID: "+vo.newsID);
            System.out.println("date: "+vo.publishDate);
            System.out.println("title: "+vo.title);
            System.out.println("summary: "+vo.summary);
            System.out.println("source: "+vo.source);
        }


    }

    @Test
    public void getRelatedNewsVO() throws Exception {
        String code = "sh600216";
        ds.getRelatedNewsVO(code);
    }


}