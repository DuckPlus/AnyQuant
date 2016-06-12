package controller.helper;

import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.Map;

/**
 * @author Qiang
 * @date 6/8/16
 */
public class JSONHelperTest {
    @Test
    public void convertToMap() throws Exception {
        JSONObject object = new JSONObject();
        object.put("1" , 0.1);
        object.put("2" , 0.2);
        object.put("3" , 0.3);

        Map<String , Double> result = JSONHelper.convertToMap(object);
        System.out.println(object);
        System.out.println(result);

    }

}