package util.update.helper;

import entity.BenchmarkdataEntity;
import entity.FactorEntity;
import entity.StockdataEntity;
import net.sf.json.JSONObject;
import util.update.po.BenchMarkPO;
import util.update.po.StockBasicInfo;
import util.update.po.StockPO;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.Map;

/**
 *
 * @author ss
 * @date 2016年4月7日
 */
public class TransferHelper {

	public static FactorEntity JSONObjectToFactorEntity(JSONObject jo){
		FactorEntity entity = new FactorEntity();

		String code=jo.getString("ticker");
		if(code.startsWith("6")){
			code="sh"+code;
		}else{
			code="sz"+code;
		}
		 entity.setCode(code);

		Date sqlDate  = Date.valueOf(jo.getString("tradeDate"));
		entity.setDate(sqlDate);

		if(jo.containsKey("VOL5")){
			entity.setVol5(jo.getDouble("VOL5"));
		}else{
			entity.setVol5(0);
		}
		if(jo.containsKey("VOL10")){
			entity.setVol10(jo.getDouble("VOL10"));
		}else{
			entity.setVol10(0);
		}
		if(jo.containsKey("VOL60")){
			entity.setVol60(jo.getDouble("VOL60"));
		}else{
			entity.setVol60(0);
		}
		if(jo.containsKey("VOL120")){
			entity.setVol120(jo.getDouble("VOL120"));
		}else{
			entity.setVol120(0);
		}



		if(jo.containsKey("MA5")){
			entity.setMa5(jo.getDouble("MA5"));
		}else{
			entity.setMa5(0);
		}
		if(jo.containsKey("MA10")){
			entity.setMa10(jo.getDouble("MA10"));
		}else{
			entity.setMa10(0);
		}
		if(jo.containsKey("MA60")){
			entity.setMa60(jo.getDouble("MA60"));
		}else{
			entity.setMa60(0);
		}
		if(jo.containsKey("MA120")){
			entity.setMa120(jo.getDouble("MA120"));
		}else{
			entity.setMa120(0);
		}


		if(jo.containsKey("PE")){
			entity.setPe(jo.getDouble("PE"));
		}else{
			entity.setPe(0);
		}

		if(jo.containsKey("PB")){
			entity.setPb(jo.getDouble("PB"));
		}else{
			entity.setPb(0);
		}

		if(jo.containsKey("PCF")){
			entity.setPcf(jo.getDouble("PCF"));
		}else{
			entity.setPcf(0);
		}

		if(jo.containsKey("PS")){
			entity.setPs(jo.getDouble("PS"));
		}else{
			entity.setPs(0);
		}

		if(jo.containsKey("PSY")){
			entity.setPsy(jo.getDouble("PSY"));
		}else{
			entity.setPsy(0);
		}

		if(jo.containsKey("REC")){
			entity.setRec(jo.getDouble("REC"));
		}else{
			entity.setRec(0);
		}

		if(jo.containsKey("DAREC")){
			entity.setDarec(jo.getDouble("DAREC"));
		}else{
			entity.setDarec(0);
		}














		return entity;

	}

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
		po.setTurnoverValue(jo.getDouble("turnoverValue"));
		po.setTurnoverRate(jo.getDouble("turnoverRate"));
		po.setPb(jo.getDouble("PB"));
		po.setPe(jo.getDouble("PE"));
		po.setAccAdjFactor(jo.getDouble("accumAdjFactor"));
		po.setCirMarketValue(jo.getDouble("negMarketValue"));
		po.setTotalMarketValue(jo.getDouble("marketValue"));
		po.computeAmplitude();  po.computeChangeRate();
		po.computeTurnOverRate();

		String [] industryAndLoc = IndustryLocationMap.get(code);
		po.setBoard(industryAndLoc[0]);   po.setRegion(industryAndLoc[1]);
		return po;
	}

    public static StockBasicInfo JSONObjectToStockBasicInfo(JSONObject jo){
		StockBasicInfo po = new StockBasicInfo();
		String code=jo.getString("ticker");
		if(code.startsWith("6")){
			po.code="sh"+code;
		}else{
			po.code="sz"+code;
		}
		po.totalShares=jo.getDouble("totalShares");
		po.listDate=jo.getString("listDate");
		po.primeOperating=jo.getString("primeOperating");
		po.officeAddr=jo.getString("officeAddr");
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
	public static void assignValue(Object stock, String[] attrs, String className) {
		try {
			Class<?> c = Class.forName(className);
			Field[] fields = c.getDeclaredFields();

			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				if (fields[i].getType().getName().equalsIgnoreCase("double")) {
					fields[i].set(stock, Double.parseDouble(attrs[i]));
				} else if (fields[i].getType().getName().equalsIgnoreCase("long")) {
					fields[i].set(stock, Long.parseLong(attrs[i]));
				} else {
					fields[i].set(stock, attrs[i]);
				}
			}

		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException e) {
			System.err.println("赋值给"+className+"出现异常==============================");
			e.printStackTrace();
		}
	}

    public static StockdataEntity StockPOToStockDataEntity(StockPO po){
        StockdataEntity entity = new StockdataEntity();
        entity.setCode(po.getCode());  entity.setDate(Date.valueOf(po.getDate()));
        entity.setName(po.getName()); entity.setHigh(po.getHigh()); entity.setLow(po.getLow());
        entity.setOpen(po.getOpen()); entity.setClose(po.getClose());  entity.setPreClose(po.getPreClose());
        entity.setTurnoverVol(po.getTurnoverVol());  entity.setTurnoverValue(po.getTurnoverValue());  entity.setTurnoverRate(po.getTurnoverRate());
        entity.setPe(po.getPe()); entity.setPb(po.getPb()); entity.setCirMarketValue(po.getCirMarketValue());
        entity.setTotalMarketValue(po.getTotalMarketValue());
        entity.setAccAdjFactor(po.getAccAdjFactor()); entity.setAmplitude(po.getAmplitude());
        entity.setChangeRate(po.getChangeRate());

        return entity;

    }


    public static BenchmarkdataEntity BenchPOToBenchDataEntity(BenchMarkPO po){
        BenchmarkdataEntity entity = new BenchmarkdataEntity();
        entity.setCode(po.getCode());  entity.setDate(Date.valueOf(po.getDate()));
        entity.setName(po.getName()); entity.setHigh(po.getHigh()); entity.setLow(po.getLow());
        entity.setOpen(po.getOpen()); entity.setClose(po.getClose());  entity.setPreClose(po.getPreclose());
        entity.setTurnoverValue(po.getTurnoverValue()); entity.setTurnoverVol(po.getTurnoverVol());
        entity.setChangeValue(po.getChange());  entity.setChangePct(po.getChangePct());
        return entity;

    }

}
