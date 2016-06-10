package controller.helper;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import service.CacheService;
import service.UpdateBLService;

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

        new Thread(() -> {
            System.out.println("preparing cache = = =");
            cacheService.prepareCache();
            System.out.println("Reading cache finish = = =");
        }).start();

//TODO 部署的时候会自动更新数据
//        System.out.println("Start schedule of update data");
//        Calendar cal = Calendar.getInstance();
//        //每天定点执行
//        cal.set(Calendar.HOUR_OF_DAY,3);
//        cal.set(Calendar.MINUTE,0);
//        cal.set(Calendar.SECOND,0);
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            public void run() {
//                System.out.println("Start updating data = = =");
//                updateBLService.update();
//                System.out.println("Finish updating data = = =");
//            }
//        },cal.getTime(), 24*60*60*1000);



    }
}
