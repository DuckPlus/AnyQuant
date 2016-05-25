package Main;
import blservice.impl.updateBLServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 67534 on 2016/5/13.
 */
public class Main {


    public static void main(String[] args) throws Exception {
        System.out.println("Timer running-----------");
        ApplicationContext context =
                new ClassPathXmlApplicationContext
                        ("classpath:/META-INF/applicationContext.xml");
        updateBLServiceImpl  updateblService =
                (updateBLServiceImpl) context.getBean("updateBLServiceImpl");

        Calendar cal = Calendar.getInstance();
        //每天定点执行
        cal.set(Calendar.HOUR_OF_DAY,24);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                //执行的内�?
                System.out.println("Update benchdata table-----------");
                updateblService.updatBenchdataEntities();

                System.out.println("Update stockdata table-----------");
                updateblService.updateStockdataEntities();

                System.out.println("Update compeleted ---------------");
            }
        },cal.getTime(), 24*60*60*1000);



    }

}
