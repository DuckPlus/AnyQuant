package controller.helper;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import service.CacheService;
import service.UpdateBLService;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Qiang
 * @date 6/9/16
 */
@Controller
public class Scheduler implements InitializingBean{
    @Autowired
    CacheService cacheService;
    @Autowired
    UpdateBLService updateBLService;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("preparing cache");
        new Thread(() -> {
            cacheService.prepareCache();
            System.out.println("Reading cache finish = = =");
        }).start();


        Calendar cal = Calendar.getInstance();
        //每天定点执行
        cal.set(Calendar.HOUR_OF_DAY,24);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("Start updating data = = =");
                updateBLService.update();
                System.out.println("Finish updating data = = =");
            }
        },cal.getTime(), 24*60*60*1000);



    }
}
