package util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by 67534 on 2016/5/26.
 */
public class SpringUtil {

    public static final
            String applicationContextLocation="/META-INF/applicationContext.xml";
    private static ApplicationContext context ;


    public static Object getBean(String serviceName)
    {
        if(context==null){
            context=  new ClassPathXmlApplicationContext(applicationContextLocation);
        }

        return context.getBean(serviceName);
    }
}
