package data.DBHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;



import data.StockDSImpl;
import po.StockPO;


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
	  
     public void deleteWrongLocation(){
    	 Statement statement = null;
    	 String loca="未知位置";
    	 String sql="DELETE FROM stock WHERE region="+loca;
    	 try {
    		 statement=conn.createStatement();
			int num =statement.executeUpdate(sql);
			if(num>0){
				System.out.println("delete successfully");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		     String sql="INSERT INTO stock(code,name)"+"VALUES(?,?)";
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
	
	  public void insetStockData(){
		  
	  }

}
