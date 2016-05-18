package data.helper;

import net.sf.json.JSONObject;
import vo.NewsVO;

import java.lang.reflect.Field;

/**
 *
 * @author ss
 * @date 2016年4月7日
 */
public class TransferHelper {


	public static NewsVO JSONObjectToNewsVO(JSONObject jo){
		NewsVO vo = new NewsVO();
		vo.newsID=jo.getString("newsID");

		if(jo.containsKey("newsPublishTime")){
			vo.publishDate=jo.getString("newsPublishTime");
		}
		if(jo.containsKey("newsTitle")){
			vo.title=jo.getString("newsTitle");
		}

		if(jo.containsKey("newsSummary")){
			vo.summary=jo.getString("newsSummary");
		}

		if(jo.containsKey("newsPublishSite")){
			vo.source=jo.getString("newsPublishSite");
		}

		return  vo;
	}


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
