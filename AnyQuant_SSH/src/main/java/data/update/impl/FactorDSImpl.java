package data.update.impl;



import data.update.factorDataService;
import entity.FactorEntity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.DateCalculator;
import util.MyDate;
import util.update.enumeration.API_TYPE;
import util.update.helper.ConnectionHelper;
import util.update.helper.TransferHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 67534 on 2016/5/20.
 */


public class FactorDSImpl implements factorDataService {


    private static factorDataService ds ;

    public static factorDataService getFactorDSImpl(){
          if(ds==null){
              return new FactorDSImpl();

          }else{
              return ds;
          }
    }
    @Override
    public List<FactorEntity> getFactorEntityBetweenDate(String code, MyDate start, MyDate end) {


        String shortCode = code.substring(2);
        String startSimple = start.DateToStringSimple();
        String endSimple = end.DateToStringSimple();
        JSONObject jo = ConnectionHelper.requestAPI
                (API_TYPE.GET_FACTOR_BetweenDate,shortCode,startSimple,endSimple);
        if(jo.getInt("retCode")==1){
            ArrayList<FactorEntity> result = new ArrayList<>();
            JSONArray ja = jo.getJSONArray("data");
            for(int i=0;i<ja.size();i++){
                JSONObject data = ja.getJSONObject(i);
                FactorEntity entity = TransferHelper.JSONObjectToFactorEntity(data);
                result.add(entity);
            }
            return result;

        }
        return null;
    }

    @Override
    public List<FactorEntity> getFactorEntityAllTheTime(String code) {
        MyDate start = MyDate.getDateFromString("2014-08-01");
        MyDate end = DateCalculator.getAnotherDay(-1);
        return getFactorEntityBetweenDate(code,start,end);
    }


}
