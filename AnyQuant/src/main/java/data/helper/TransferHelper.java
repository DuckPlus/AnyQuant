package data.helper;

import java.lang.reflect.Field;
import java.util.Map;

import net.sf.json.JSONObject;
import po.BenchMarkPO;
import po.StockPO;

/**
 *
 * @author ss
 * @date 2016年4月7日
 */
public class TransferHelper {
	public static StockPO JSONObjectToStockPO(Map<String ,String[]> IndustryLocationMap, JSONObject jo){
		StockPO po = new StockPO();
		po.setDate(jo.getString("tradeDate")); po.setName(jo.getString("secShortName"));

		String code=jo.getString("ticker");
		if(code.startsWith("6")){
			code="sh"+code;
		}else{
			code="sz"+code;
		}
		po.setCode(code);

		po.setHigh(jo.getDouble("highestPrice"));  	po.setLow(jo.getDouble("lowestPrice"));   po.setOpen(jo.getDouble("openPrice"));
		po.setClose(jo.getDouble("closePrice"));  po.setPreClose(jo.getDouble("preClosePrice"));  po.setTurnoverVol(jo.getLong("turnoverVol"));
		po.setTurnoverValue(jo.getDouble("turnoverValue"));  po.setTurnoverRate(jo.getDouble("turnoverRate"));   po.setPb(jo.getDouble("PB"));
		po.setPe(jo.getDouble("PE")); 
//		po.setAccAdjFactor(jo.getDouble("accumAdjFactor"));   TODO
		po.setCirMarketValue(jo.getDouble("negMarketValue"));
		po.setTotalMarketValue(jo.getDouble("marketValue"));
		po.computeAmplitude();  po.computeChangeRate();

		String [] industryAndLoc = IndustryLocationMap.get(code);
		po.setBoard(industryAndLoc[0]);   po.setRegion(industryAndLoc[1]);
		return po;
	}



	public static BenchMarkPO JSONObjectToBenchMarkPO(JSONObject jo){
		BenchMarkPO po = new BenchMarkPO();
		po.setDate(jo.getString("tradeDate")); po.setName(jo.getString("secShortName"));
        po.setCode(jo.getString("ticker"));     po.setHigh(jo.getDouble("highestIndex"));  	po.setLow(jo.getDouble("lowestIndex"));   po.setOpen(jo.getDouble("openIndex"));
		po.setClose(jo.getDouble("closeIndex"));  po.setPreclose(jo.getDouble("preCloseIndex"));  po.setTurnoverVol(jo.getLong("turnoverVol"));
		po.setTurnoverValue(jo.getDouble("turnoverValue"));  po.setChange(jo.getDouble("CHG"));     po.setChangePct(jo.getDouble("CHGPct"));
		return po;
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
