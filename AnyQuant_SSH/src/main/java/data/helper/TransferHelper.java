package data.helper;

import java.lang.reflect.Field;

/**
 *
 * @author ss
 * @date 2016年4月7日
 */
public class TransferHelper {
	

	public static String ObjectToString(String name , Object object , String splitChar) {
		try {
			Class<?> class1 = Class.forName(name);
			Field[] fields = class1.getDeclaredFields();

			for (Field field : fields) {
				field.setAccessible(true);
			}
			StringBuffer buffer = new StringBuffer();

			for (Field field : fields) {
				buffer.append(field.get(object));
				buffer.append(splitChar);
			}
			// desert the last char
			return buffer.substring(0, buffer.length() - 1);

		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
