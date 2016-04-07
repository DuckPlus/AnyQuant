package data;

import java.util.ArrayList;
import java.util.List;

import org.python.antlr.PythonParser.file_input_return;
import org.python.antlr.PythonParser.return_stmt_return;

import dataservice.BenchMarkDataService;
import enumeration.MyDate;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import po.BenchMarkPO;
import util.MyTime;

/**
 *
 * @author ss
 * @date 2016年4月7日
 */
public class BenchMarkDSImpl implements BenchMarkDataService{
	       private static BenchMarkDSImpl benchMarkDSImpl;

	       private  BenchMarkDSImpl() {

	       }

	       public static BenchMarkDataService getBenchMarkDSImpl(){
	    	     if(benchMarkDSImpl==null){
	    	    	 return  new BenchMarkDSImpl();
	    	     }else{
	    	    	 return benchMarkDSImpl;
	    	     }
	       }

			@Override
			public List<String> getAllBenchMarks() {
						return FileIOHelper.readAllBenches();
			}


			//获得最新的大盘数据
			@Override
			public BenchMarkPO getBenchMes(String benchCode) {
				  int offset=0;
				  MyDate date  = MyTime.getAnotherDay(offset);
				  while(getBenchMesRequestResult(benchCode, date)!=1){
					  offset--;
					  date=MyTime.getAnotherDay(offset);
				  }
		         return  getBenchMes(benchCode, date);
			}


			@Override
			public BenchMarkPO getBenchMes(String benchCode, MyDate date) {
				String tradeDateString=date.DateToStringSimple();
				String url = "https://api.wmcloud.com:443/data/v1"
						+ "/api/market/getMktIdxd.json?field=&beginDate=&endDate=&indexID=&ticker="+benchCode+"&tradeDate="+tradeDateString ;
			    String result = ConnectionHelper.request(url);
			    JSONObject jo = JSONObject.fromObject(result);
			    if(jo.getInt("retCode")==1){
			       JSONArray jArray = jo.getJSONArray("data");
			       JSONObject  benJsonObject = jArray.getJSONObject(0);
			       BenchMarkPO po= JSONTransferHelper.JSONObjectToBenchMarkPO(benJsonObject);
		           return po;
			    }else{
		           return new BenchMarkPO();
			    }
			}


			@Override
			public List<BenchMarkPO> getBenchMes(String benchCode, MyDate start, MyDate end) {
				if(  start.DateToString().equals(end.DateToString()) ){
					List<BenchMarkPO> benchMarkPOs = new ArrayList<>();
					benchMarkPOs.add(getBenchMes(benchCode,start));
					return  benchMarkPOs;
				}

				List<BenchMarkPO> pos = new ArrayList<>();
				String url = "https://api.wmcloud.com:443/data/v1"
						+ "/api/market/getMktIdxd.json?field=&beginDate="+start.DateToStringSimple()+"&endDate="+end.DateToStringSimple()+"&indexID=&ticker="+benchCode+"&tradeDate=" ;
			    String result =ConnectionHelper. request(url);
			    JSONObject jo = JSONObject.fromObject(result);
			    if(jo.getInt("retCode")==1){
			       JSONArray jArray = jo.getJSONArray("data");
			       for(int i=0;i<jArray.size();i++){
			    	   JSONObject  stockpoJsonObject = jArray.getJSONObject(0);
			           BenchMarkPO po=JSONTransferHelper. JSONObjectToBenchMarkPO(stockpoJsonObject);
			           pos.add(po);
			       }
		           return pos;
			    }else{
		           return new ArrayList<>();
			    }

			}

			@Override
			/**
			 *目前尚未实现缓存
			 */
			public List<BenchMarkPO> getAllBenchMes() {
				List<BenchMarkPO> pos = new ArrayList<>();
				List<String> benchs = FileIOHelper.readAllBenches();
				for(String benchCode: benchs){
					   BenchMarkPO po = this.getBenchMes(benchCode);
					   pos.add(po);
				}
				return pos;
			}

			//不确定请求的大盘信息在某日是否有数据，返回请求结果1为有，-1为没有
			private  int    getBenchMesRequestResult(String benchCode, MyDate date){
				String tradeDateString=date.DateToStringSimple();
				String url = "https://api.wmcloud.com:443/data/v1"
						+ "/api/market/getMktIdxd.json?field=&beginDate=&endDate=&indexID=&ticker="+benchCode+"&tradeDate="+tradeDateString ;
			    String result =ConnectionHelper. request(url);
			    JSONObject jo = JSONObject.fromObject(result);
			    return  jo.getInt("retCode");
			}
}
