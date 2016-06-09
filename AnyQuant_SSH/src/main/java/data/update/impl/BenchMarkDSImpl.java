package data.update.impl;


import data.update.BenchMarkDataService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.DateCalculator;
import util.MyDate;
import util.update.enumeration.API_TYPE;
import util.update.helper.*;
import util.update.po.BenchMarkPO;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ss
 * @date 2016年4月7日
 */
public class BenchMarkDSImpl implements BenchMarkDataService {
    private static BenchMarkDSImpl benchMarkDSImpl;

    private BenchMarkDSImpl() {

    }

    public static BenchMarkDataService getBenchMarkDSImpl() {
        if (benchMarkDSImpl == null) {
            return new BenchMarkDSImpl();
        } else {
            return benchMarkDSImpl;
        }
    }

    @Override
    public List<String> getAllBenchMarks() {
        return FileIOHelper.readFiles(FileIOHelper.benchCodeFileName);
    }

    // 获得最新的大盘数据
    @Override
    public BenchMarkPO getBenchMes(String benchCode) {
        int offset = 0;
        MyDate date = StockMesHelper.getPrevTradeDay();
        System.out.print("preTradeDay:"+date.DateToString());

        return getBenchMes(benchCode, date);
    }

    @Override
    public BenchMarkPO getBenchMes(String benchCode, MyDate date) {
        String tradeDateString = date.DateToStringSimple();
        JSONObject jo = ConnectionHelper.requestAPI(API_TYPE.GET_BENCHMARK_MES, "", "", "", benchCode, tradeDateString);
        if (jo.getInt("retCode") == 1) {
            JSONArray jArray = jo.getJSONArray("data");
            JSONObject benJsonObject = jArray.getJSONObject(0);
            BenchMarkPO po = TransferHelper.JSONObjectToBenchMarkPO(benJsonObject);
            return po;
        } else {
            return null;
        }
    }

    @Override
    public List<BenchMarkPO> getBenchMes(String benchCode, MyDate start, MyDate end) {
        if (start.DateToString().equals(end.DateToString())) {
            List<BenchMarkPO> benchMarkPOs = new ArrayList<>();
            benchMarkPOs.add(getBenchMes(benchCode, start));
            return benchMarkPOs;
        }

        List<BenchMarkPO> pos = new ArrayList<>();
        JSONObject jo = ConnectionHelper.requestAPI(API_TYPE.GET_BENCHMARK_MES, start.DateToStringSimple(),
                end.DateToStringSimple(), "", benchCode, "");
        if (jo.getInt("retCode") == 1) {
            JSONArray jArray = jo.getJSONArray("data");
            for (int i = 0; i < jArray.size(); i++) {
                JSONObject stockpoJsonObject = jArray.getJSONObject(i);
                BenchMarkPO po = TransferHelper.JSONObjectToBenchMarkPO(stockpoJsonObject);
                pos.add(po);
            }
            return pos;
        } else {
            return new ArrayList<>();
        }

    }

    @Override
    public List<BenchMarkPO> getAllBenchMes() {

        upDateBenchMarkMes();

        return CacheHelper.getCacheBenchPOs();
    }

    /**
     * 更新14条记录的txt
     */
    public void upDateBenchMarkMes(){
        if(CacheHelper.needUpdate(false)){
            List<BenchMarkPO> pos = new ArrayList<>();
            List<String> benchs = FileIOHelper.readFiles(FileIOHelper.benchCodeFileName);
            for (String benchCode : benchs) {
                BenchMarkPO po = this.getBenchMes(benchCode);
                pos.add(po);
            }
            FileIOHelper.writeAllBenMes(pos);
        }
    }



    public void updateAllBenchMesFromDate(List<String> codes, MyDate start){
        MyDate end = DateCalculator.getToDay();
        ArrayList<BenchMarkPO> content = new ArrayList<>();
        for( String code : codes){
            List<BenchMarkPO> temp =  getBenchMes(code,start,end);
            content.addAll(temp);
        }
        FileIOHelper.writeAllBenMes(content);
    }
}
