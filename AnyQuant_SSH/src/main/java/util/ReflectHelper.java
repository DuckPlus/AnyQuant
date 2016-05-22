package util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by 67534 on 2016/5/22.
 */
public class ReflectHelper {


    public static Object getValueByAttrName(Object ob , String attrName) {

        Field field = null;
        try {
            field = ob.getClass().getDeclaredField(attrName.toLowerCase());
            return getValueByProperty(ob, field.getName());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;

    }

    private static Object getValueByProperty(Object p, String propertyName)
            throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        // get property by the argument propertyName.
        PropertyDescriptor pd = new PropertyDescriptor(propertyName, p.getClass());
        Method method = pd.getReadMethod();
        Object attr = method.invoke(p);
        return attr;
    }


}
