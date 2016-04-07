package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.python.antlr.PythonParser.return_stmt_return;

import dataservice.OptionalStockDataService;
import dataservice.StockDataService;
import po.StockPO;

/**
 *
 * @author ss
 * @date 2016年4月7日
 */
public class OptionalStockDSImpl implements OptionalStockDataService{
    private static OptionalStockDSImpl optionalStockDSImpl;
    private static String optionalCodesFilePath = "data//OptionalStocks.txt";
    private  OptionalStockDSImpl() {

	}

    public static OptionalStockDataService getOptionalStockDSImpl(){
    	if(optionalStockDSImpl==null){
    		return new OptionalStockDSImpl();
    	}else{
    		return optionalStockDSImpl;
    	}
    }

    @Override
	public Iterator<StockPO> getOptionalStocks() {
		// TODO Auto-generated method stub
		List<StockPO> pos = new ArrayList<StockPO>();
		List<String> codeStrings =getSelectedStockCodes() ;
		StockDataService stockDSImplDataService = StockDSImpl.getStockDSImpl();
		StockPO temp = null;
        for(String code: codeStrings){
        	   if(code.length()>2){
                    temp= stockDSImplDataService.getStockMes(code);
                    pos.add(temp);
        	   }
        }
		return  pos.iterator();
	}

	@Override
	public boolean deleteOptionalStock(String stockCode) {
		// TODO Auto-generated method stub
		List<String>  CodeStrings = getSelectedStockCodes();
		boolean result =CodeStrings.remove(stockCode);
		FileIOHelper.writeSelectedStockCodes(CodeStrings);
		return result;
	}

	@Override
	public boolean addOptionalStock(String stockCode) {
		// TODO Auto-generated method stub
		List<String>  CodeStrings = getSelectedStockCodes();
		if(!CodeStrings.contains(stockCode)){
		     CodeStrings.add(stockCode);
		     FileIOHelper.writeSelectedStockCodes(CodeStrings);
		     return true;
		}
		return false;
	}




	@Override
	public boolean clearOptionalStocks() {
		try{

	         File file =new File(optionalCodesFilePath);
	         file.delete();
	         //if file doesnt exists, then create it
	         if(!file.exists()){
	                file.createNewFile();
	         }

	         //true = append file
	         FileWriter fileWritter = new FileWriter(file,false);
	         BufferedWriter bufferWritter = new BufferedWriter(fileWritter);

	         bufferWritter.write("");

	         bufferWritter.close();
	         System.out.println("Done");
	    }catch(IOException e){
	            e.printStackTrace();
	    }
         return true;
	}




	@Override
	public List<String> getSelectedStockCodes() {
		// TODO Auto-generated method stub
		return FileIOHelper.readSelectedStockCodes();
	}






}
