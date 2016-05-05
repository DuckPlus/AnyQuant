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
	public static StockPO JSONObjectToStockPO(Map<String, String[]> IndustryLocationMap, JSONObject jo) {
		StockPO po = new StockPO();
		po.setDate(jo.getString("tradeDate"));
		po.setName(jo.getString("secShortName"));
		String code = jo.getString("ticker");
		if (code.startsWith("6")) {
			code = "sh" + code;
		} else {
			code = "sz" + code;
		}
		po.setCode(code);

		po.setHigh(jo.getDouble("highestPrice"));
		po.setLow(jo.getDouble("lowestPrice"));
		po.setOpen(jo.getDouble("openPrice"));
		po.setClose(jo.getDouble("closePrice"));

		if (jo.containsKey("preClosePrice")) {
			po.setPreClose(jo.getDouble("preClosePrice"));
		} else {
			po.setPreClose(0);
		}

		if (jo.containsKey("turnoverVol")) {
			po.setTurnoverVol(jo.getLong("turnoverVol"));
		} else {
			po.setTurnoverVol(-100);
		}

		if (jo.containsKey("turnoverValue")) {
			po.setTurnoverValue(jo.getDouble("turnoverValue"));
		} else {
			po.setTurnoverValue(-100);
		}

		if (jo.containsKey("turnoverRate")) {
			po.setTurnoverRate(jo.getDouble("turnoverRate"));
		} else {
			po.setTurnoverRate(-100);
		}

		if (jo.containsKey("PB")) {
			po.setPb(jo.getDouble("PB"));
		} else {
			po.setPb(-100);
		}

		if (jo.containsKey("PE")) {
			po.setPe(jo.getDouble("PE"));
		} else {
			po.setPe(-100);
		}

		if (jo.containsKey("accumAdjFactor")) {
			po.setAccAdjFactor(jo.getDouble("accumAdjFactor"));
		} else {
			po.setAccAdjFactor(-100);
		}

		if (jo.containsKey("negMarketValue")) {
			po.setCirMarketValue(jo.getDouble("negMarketValue"));
		} else {
			po.setCirMarketValue(-100);
		}

		if (jo.containsKey("marketValue")) {
			po.setTotalMarketValue(jo.getDouble("marketValue"));
		} else {
			po.setTotalMarketValue(-100);
		}

		po.computeAmplitude();
		po.computeChangeRate();
		po.computeTurnOverRate();

		String[] industryAndLoc = IndustryLocationMap.get(code);
		po.setBoard(industryAndLoc[0]);
		po.setRegion(industryAndLoc[1]);
		return po;
	}

	public static BenchMarkPO JSONObjectToBenchMarkPO(JSONObject jo) {
		BenchMarkPO po = new BenchMarkPO();
		po.setDate(jo.getString("tradeDate"));
		po.setName(jo.getString("secShortName"));
		po.setCode(jo.getString("ticker"));

		if (jo.containsKey("highestIndex")) {
			po.setHigh(jo.getDouble("highestIndex"));
		} else {
			po.setHigh(0);
		}

		if (jo.containsKey("lowestIndex")) {
			po.setLow(jo.getDouble("lowestIndex"));
		} else {
			po.setLow(0);
		}

		if (jo.containsKey("openIndex")) {
			po.setOpen(jo.getDouble("openIndex"));
		} else {
			po.setOpen(0);
		}

		if (jo.containsKey("closeIndex")) {
			po.setClose(jo.getDouble("closeIndex"));
		} else {
			po.setClose(0);
		}

		if (jo.containsKey("preCloseIndex")) {
			po.setPreclose(jo.getDouble("preCloseIndex"));
		} else {
			po.setPreclose(0);
		}
		if (jo.containsKey("turnoverVol")) {
			po.setTurnoverVol(jo.getLong("turnoverVol"));
		} else {
			po.setTurnoverVol(-100);
		}

		if (jo.containsKey("turnoverValue")) {
			po.setTurnoverValue(jo.getDouble("turnoverValue"));
		} else {
			po.setTurnoverValue(-100);
		}

		if (jo.containsKey("CHG")) {
			po.setChange(jo.getDouble("CHG"));
		} else {
			po.setChange(po.getClose() - po.getOpen());
		}

		if (jo.containsKey("CHGPct")) {
			po.setChangePct(jo.getDouble("CHGPct"));
		} else {
			if(po.getOpen()!=0){
				po.setChangePct((po.getClose() - po.getOpen()) / po.getOpen());
			}else{
				po.setChangePct(0);
			}
			
		}
		return po;
	}

	public static String ObjectToString(String name, Object object, String splitChar) {
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
