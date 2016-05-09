package data.DBHelper;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import data.BenchMarkDSImpl;
import data.StockDSImpl;
import dataservice.BenchMarkDataService;
import enumeration.MyDate;
import po.BenchMarkPO;
import po.StockPO;
import util.DateCalculator;


/**
 *
 * @author ss
 * @date 2016��5��3��
 */
public class DataBaseHelper {
	   
	  private static Connection conn;
	  
	  public DataBaseHelper(){
		    conn = DataBaseInit.getConnection();
	  }
	  
     public void initStocks(){
    	    StockDSImpl stockDSImpl = (StockDSImpl) StockDSImpl.getStockDSImpl();
    	    List<StockPO> pos =  stockDSImpl.getAllStockMes();
    	    for(StockPO po:pos){
    	    	if(po!=null){
    	    	    insertStock(po.getCode(), po.getName(), po.getRegion());
    	    	}else{
    	    		System.out.println(po.getCode()+"   null!");
    	    	}
    	    }
				
			if(conn!=null){
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		
    	 
     }
     
     
     
	  
     public void updateIndu_Loca(){
    	 Statement statement = null;
    	 ResultSet set  = null;
    	 ArrayList<String> stocks =null;
    	 
    	 String sql = "SELECT * FROM stock";
    	 try {
			      statement=conn.createStatement();
			      set = statement.executeQuery(sql);
			      stocks= new ArrayList<>();
			      while(set.next()){
			    	  String temp ="";
			    	  temp=set.getString("code");
			    	  stocks.add(temp);  
			      }
			      
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    	 
     }
     
     public void initBenches(){
 	     BenchMarkDataService benchDSImpl = BenchMarkDSImpl.getBenchMarkDSImpl();
 	    List<BenchMarkPO> pos =  benchDSImpl.getAllBenchMes();
 	    for(BenchMarkPO po:pos){
 	    	if(po!=null){
 	    	      insertBench(po.getCode(), po.getName());
 	    	}else{
 	    		  System.out.println(po.getCode()+"   null!");
 	    	}
 	    }
				
			if(conn!=null){
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		
 	 
  }
     
     public void initStockData(){

    	    Statement statement = null;
    	    ResultSet resultSet = null;
    	    
    	    String sql = "SELECT code FROM stock";
    	    StockDSImpl stockDSImpl = (StockDSImpl) StockDSImpl.getStockDSImpl();
    	    MyDate  start = new MyDate(2006, 01, 01);
    	    MyDate  end  =  DateCalculator.getToDay();
    	    try {
				    statement = conn.createStatement();
				    resultSet=statement.executeQuery(sql);
		            int i=1;
		    	    while(resultSet.next()){
		    	         	List<StockPO>  pos = stockDSImpl.getStockMes(resultSet.getString("code"), start, end);
		    	         	System.out.println(resultSet.getString("code"));
		    	         	for(StockPO po : pos){
		    	         		insertStockData(po.getCode(), po.getDate(), po.getHigh(), po.getLow(), po.getOpen(), po.getClose(), 
		    	         				po.getPreClose(), po.getTurnoverVol(), po.getTurnoverValue(), po.getTurnoverRate(), po.getPb(), po.getPe(), 
		    	         				po.getAccAdjFactor(), po.getCirMarketValue(), po.getTotalMarketValue(), po.getAmplitude(), po.getChangeRate());
		    	         	}
		    	         	
		    	         	System.out.println("compelete "+ i );
		    	         	i++;
		    	    }
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	 
    	    	 
    	 
				
			if(conn!=null){
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
     }
     
     
     
     public void initBenchData(){
     	 
	    Statement statement = null;
	    ResultSet resultSet = null;
	    
	    String sql = "SELECT code FROM benchmark";
	   BenchMarkDataService benchMarkDataService = BenchMarkDSImpl.getBenchMarkDSImpl();
	    MyDate  start = new MyDate(2006, 01, 01);
	    MyDate  end  =  DateCalculator.getToDay();
	    try {
			    statement = conn.createStatement();
			    resultSet=statement.executeQuery(sql);
	            int i=1;
	    	    while(resultSet.next()){
	    	         	List<BenchMarkPO>  pos =benchMarkDataService.getBenchMes(resultSet.getString("code"), start, end);
	    	         	System.out.println(resultSet.getString("code"));
	    	         	for(BenchMarkPO po : pos){
	    	         		insertBenchData(po.getCode(), po.getDate(), po.getHigh(), po.getLow(), po.getOpen(), po.getClose(), 
	    	         				po.getPreclose(), po.getTurnoverVol(),po.getTurnoverValue(),po.getChange(),po.getChangePct() ); 
	    	         	}
	    	         	
	    	         	System.out.println("compelete "+ i );
	    	         	i++;
	    	    }
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 
	    	 
	 
			
		if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
}
	  public void insertStock(String code,String name,String region){
		     PreparedStatement preparedStatement =null;
		     String sql="INSERT INTO stock(code,name,region)"+"VALUES(?,?,?)";
		     try {

			    preparedStatement  = conn.prepareStatement(sql);
				preparedStatement.setString(1, code);
				preparedStatement.setString(2, name);
				preparedStatement.setString(3, region);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if(preparedStatement!=null){
					try {
						preparedStatement.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
//				if(conn!=null){
//					try {
//						conn.close();
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
			}
		     
	  }
	 
	  public void insertBench(String code,String name){
		     PreparedStatement preparedStatement =null;
		     String sql="INSERT INTO benchmark(code,name)"+"VALUES(?,?)";
		     try {
			    preparedStatement  = conn.prepareStatement(sql);
				preparedStatement.setString(1, code);
				preparedStatement.setString(2, name);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if(preparedStatement!=null){
					try {
						preparedStatement.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
//				if(conn!=null){
//					try {
//						conn.close();
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
			}
		     
	  }
	
	  public void insertStockData(String code,String date,double high,double low,double open,double close,double preClose,long turnoverVol,
			  double turnoverValue,double turnoverRate,double pb ,double pe,double adj ,double cirMarkValue,double totalMarkValue,
			  double amp, double changeRate){
		  PreparedStatement preparedStatement =null;
		     String sql= " INSERT INTO stockdata(code, date, high, low, open, close, preClose, turnoverVol,"+
		"	 turnoverValue, turnoverRate,pb , pe, accAdjFactor , cirMarketValue, totalMarketValue,"+
		"	 amplitude, changeRate) "  +   "VALUES(?,?,?,?,?,?,?,  ?,?,?,?,?,?,?,   ?,?,?   )";
		     try {

			    preparedStatement  = conn.prepareStatement(sql);
				preparedStatement.setString(1, code);
				Date sqlDate = Date.valueOf(date);  
			//	System.out.println(date);
		    	preparedStatement.setDate(2, sqlDate);
				preparedStatement.setDouble(3, high);  preparedStatement.setDouble(4,low); preparedStatement.setDouble(5,open); preparedStatement.setDouble(6,close);
				preparedStatement.setDouble(7,preClose); preparedStatement.setLong(8, turnoverVol);  preparedStatement.setDouble(9,turnoverValue);  preparedStatement.setDouble(10,turnoverRate);
				preparedStatement.setDouble(11,pb);  preparedStatement.setDouble(12,pe);  preparedStatement.setDouble(13,adj);  preparedStatement.setDouble(14,cirMarkValue);preparedStatement.setDouble(15,totalMarkValue);
				preparedStatement.setDouble(16,amp);preparedStatement.setDouble(17,changeRate);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if(preparedStatement!=null){
					try {
						preparedStatement.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	     }
	  }
	  
	  public void insertBenchData(String code,String date,double high,double low,double open,double close,double preClose
			,long turnoverVol,  double turnoverValue,double changeValue,  double changePct ){
		     PreparedStatement preparedStatement =null;
		     String sql= "INSERT INTO benchmarkdata(code,date,high,low,open,close,preClose,turnoverValue,changeValue,changePct,turnoverVol)"
		     	            	+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)"  ;
		     try {

			    preparedStatement  = conn.prepareStatement(sql);
				preparedStatement.setString(1, code);
				Date sqlDate = Date.valueOf(date);  
			    //	System.out.println(date);
		    	preparedStatement.setDate(2, sqlDate);
				preparedStatement.setDouble(3, high);  preparedStatement.setDouble(4,low); preparedStatement.setDouble(5,open); preparedStatement.setDouble(6,close);
				preparedStatement.setDouble(7,preClose);
			
				preparedStatement.setDouble(8,turnoverValue);  
				preparedStatement.setDouble(9,changeValue);  	
				preparedStatement.setDouble(10,changePct); 
				preparedStatement.setLong(11, turnoverVol);
		//		System.out.println("turnovervol");
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				
				if(preparedStatement!=null){
					try {
						preparedStatement.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	     }
	  }

	  public void insertStockDataList(List<StockPO > pos){
		  PreparedStatement preparedStatement =null;
		     String sql= " INSERT INTO stockdata(code, date, high, low, open, close, preClose, turnoverVol,"+
		"	 turnoverValue, turnoverRate,pb , pe, accAdjFactor , cirMarketValue, totalMarketValue,"+
		"	 amplitude, changeRate) "  +   "VALUES(?,?,?,?,?,?,?,  ?,?,?,?,?,?,?,   ?,?,?   )";
		     try {

			    preparedStatement  = conn.prepareStatement(sql);
			    for(StockPO po : pos){
				preparedStatement.setString(1, po.getCode());
				Date sqlDate = Date.valueOf(po.getDate());  
			//	System.out.println(date);
		    	preparedStatement.setDate(2, sqlDate);
				preparedStatement.setDouble(3, po.getHigh());  preparedStatement.setDouble(4,po.getLow()); preparedStatement.setDouble(5,po.getOpen()); preparedStatement.setDouble(6,po.getClose());
				preparedStatement.setDouble(7,po.getPreClose()); preparedStatement.setLong(8, po.getTurnoverVol());  preparedStatement.setDouble(9,po.getTurnoverValue());  preparedStatement.setDouble(10,po.getTurnoverRate());
				preparedStatement.setDouble(11,po.getPb());  preparedStatement.setDouble(12,po.getPe());  preparedStatement.setDouble(13,po.getAccAdjFactor());  preparedStatement.setDouble(14,po.getCirMarketValue());preparedStatement.setDouble(15,po.getTotalMarketValue());
				preparedStatement.setDouble(16,po.getAmplitude());preparedStatement.setDouble(17,po.getChangeRate());
			   preparedStatement.addBatch();
			//	preparedStatement.executeUpdate();
			    	break;
			    }
				preparedStatement.executeBatch();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if(preparedStatement!=null){
					try {
						preparedStatement.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	     }
	  }
	  
}
