package data;
import java.util.Map;

import net.sf.json.JSONObject;
/**
 *
 * @author ss
 * @date 2016年3月4日
 */
public class MyJSONObject {
            
            public   static  <T> T   toBean(JSONObject jsonObject,Class<T> beanClass){
            	
            	  T bean = (T)JSONObject.toBean(jsonObject, beanClass);
            	  if(bean instanceof InitialBean){
            		  InitialBean initialBean = (InitialBean) bean;
            		  initialBean.initialize();
            	  }
				return  bean;
            }
            
}
