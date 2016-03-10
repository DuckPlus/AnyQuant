package helper;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import vo.StockVO;
import blimpl.StockBLImpl;
import blservice.StockBLService;
import enumeration.CacheData;

/**
 * 存储辅助性方法
 * @author czq
 * @date 2016年3月6日
 */
public class Helper {
	
	private static final String cachePath = "cache//";
	
	public static final List<Object> getCacheData(CacheData data){
		switch (data) {
		case BENCHMARK_MES:
			return getStockMes();
		case STOCK_MES:
			
			break;
		default:
			break;
		}
		
		
		return null;
		
		
	}
	
	private static final List<Object> getStockMes(){
		
		
		
		return null;
		
	}
	/**
	 * 重新更新缓存的数据
	 * @param data
	 */
	public static void updateCache(CacheData data){
		StockBLService api;
		api = StockBLImpl.getAPIBLService();
		Iterator<StockVO> datas = api.getAllStocks();
		
		
		
		
		
	}
	
	
	/**
	 * 從序列化文件中讀取多個對象
	 * @param name
	 * @return
	 */
	public final ArrayList<Object> readManyFromSerFile(String name) {
				ArrayList<Object> result = new ArrayList<>();
		ObjectInputStream input = null;
		File file = new File(cachePath + name);

		try {
			input = new ObjectInputStream(new FileInputStream(file));
			while (input.available() != -1) {
				try {
					result.add(input.readObject());
				} catch (ClassNotFoundException e ) {
					e.printStackTrace();
				} catch (EOFException e) {
					break;
				}
			}
		} catch (FileNotFoundException e1) {
			System.err.println("序列化文件丢失");
			//重新嘗試創建新文件
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			e1.printStackTrace();
		}catch (EOFException e) {
			System.err.println("WARNING: 账单文件丢失");
			return null;
		}
		catch (IOException e1) {
			e1.printStackTrace();
			return null;
		} 
		
		
		
		try {
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result.isEmpty() ? null : result;

	}
	
	
	/**
	 * 向文件中写入多个对象，会清空之前的数据
	 * 
	 * @param objectIterator
	 * @param name
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean writeToSerFile(Iterator objectIterator, String name, boolean append) {
		ObjectOutputStream out;
		File file = null ; 
		try {
			file = new File(cachePath + name);
			out = new ObjectOutputStream(new FileOutputStream(file));
			
			while(objectIterator.hasNext()){
				out.writeObject(objectIterator.next());
			}
			
			out.close();
			return true;
		} catch (FileNotFoundException e) {
			System.err.println("序列化文件丢失或未创建，将创建一个空文件");
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}

		return false;

	}
	
	public static List<String> getDocument(String filePath) {
		List<String> document = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(
					filePath)));
			String temp;
			while (true) {
				if ((temp = reader.readLine()) != null) {
					document.add(temp);
				}else{
					break;
				}
			}
			reader.close();	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return document;
	}
	
}
