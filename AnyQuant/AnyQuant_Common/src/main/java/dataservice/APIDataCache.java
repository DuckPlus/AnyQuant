package dataservice;

import java.awt.Button;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import enumeration.Exchange;
import enumeration.MyDate;
import po.BenchMarkPO;
import po.StockPO;

/**
 *这个类在逻辑层和数据层交互时可以起到缓冲作用
 *具体实现：
 *
 * @author ss
 * @date 2016年3月6日
 */
public class APIDataCache implements APIInterface{
	private static String fileName1="cache/stockCode.txt";
	private static String fileName2="cache/stockMes.txt";
    APIInterface   api = null;
    
    public  APIDataCache(APIInterface api) {
		  this.api = api;
	}
    
	@Override
	public List<String> getAllStocks() {
	   return  readAllCodes();
	}

	@Override
	public List<String> getAllStocks(int year) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllStocks(Exchange exchange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllStocks(int year, Exchange exchange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StockPO getStockMes(String stockCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StockPO> getStockMes(String stockCode, MyDate start, MyDate end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BenchMarkPO getBenchMes(String benchCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BenchMarkPO> getBenchMes(String benchCode, MyDate start, MyDate end) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private List<String> readAllCodes(){
		try {
                String encoding="utf-8";
                String filePath = fileName1;
                File file=new File(filePath);
                if(file.isFile() && file.exists()){ //判断文件是否存在
                         InputStreamReader read = new InputStreamReader(
                                                                        new FileInputStream(file),encoding);//考虑到编码格式
                         BufferedReader bufferedReader = new BufferedReader(read);
                         String lineTxt = bufferedReader.readLine();
                         List<String > result  = Arrays.asList(lineTxt.split(","));
                         read.close();
                         return result;
                }else{
                          System.out.println("找不到指定的文件,创建新文件");
                          List<String >  data = api.getAllStocks();
                          writeAllCodes(data);
                          return data;
                }
          } catch (Exception e) {
                 System.out.println("读取文件内容出错");
                 e.printStackTrace();
          }
		  return null;
	}
	
	private void writeAllCodes(List<String>  codes){
		try{
			
		         File file =new File(fileName1);
		         //if file doesnt exists, then create it
		         if(!file.exists()){
		                file.createNewFile();
		         }

		         //true = append file
		         FileWriter fileWritter = new FileWriter(file,true);
		         BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		         for(String line : codes){
		        	 bufferWritter.write(line+',');
		        	 System.out.println("write: "+line);
		         }
		         bufferWritter.close();
		         System.out.println("Done");
		    }catch(IOException e){
		            e.printStackTrace();
		    }		    
	}
	
	private List<StockPO>  readAllMes(){
		try {
            String encoding="utf-8";
            String filePath = fileName2;
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                     InputStreamReader read = new InputStreamReader(
                                                                    new FileInputStream(file),encoding);//考虑到编码格式
                     BufferedReader bufferedReader = new BufferedReader(read);
                     String temp="";
                     String [] attrs = null;
                     List<StockPO> result = new ArrayList<StockPO>();
                     
                     while((temp=bufferedReader.readLine())!=null){
                    	    attrs = temp.split(",");
                    	    StockPO stock = new StockPO(attrs[0],attrs[1],attrs[2],Double.parseDouble(attrs[3]),Double.parseDouble(attrs[4]),Double.parseDouble(attrs[5]),
                    	    		Double.parseDouble(attrs[6]),Double.parseDouble(attrs[7]),Double.parseDouble(attrs[8]),Long.parseLong(attrs[9]),Double.parseDouble(attrs[10]),
                    	    		Double.parseDouble(attrs[11]),Double.parseDouble(attrs[12]),Double.parseDouble(attrs[13]),Double.parseDouble(attrs[14]));
                            result .add(stock);
                     }
                     read.close();
                     return result;
            }else{
                      System.out.println("找不到指定的文件,创建新文件");
                      List<StockPO> result =    new ArrayList<>();
                   //   for()
                //      writeAllCodes(data);
                //      return data;
            }
      } catch (Exception e) {
             System.out.println("读取文件内容出错");
             e.printStackTrace();
      }
	  return null;
	}
	
	private void writeAllMes(List<StockPO> stocks){
		try{
			
	         File file =new File(fileName2);
	         //if file doesnt exists, then create it
	         if(!file.exists()){
	                file.createNewFile();
	         }

	         //true = append file
	         FileWriter fileWritter = new FileWriter(file,true);
	         BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	         for(StockPO stock: stocks){
	        	    String temp = stock.MyToString(',')+'\n';
	        	    System.out.println("write: "+temp);
	        	    bufferWritter.write(temp);
	         }
	         bufferWritter.close();
	         System.out.println("Done");
	    }catch(IOException e){
	            e.printStackTrace();
	    }		    
	}
	    
	    

}
