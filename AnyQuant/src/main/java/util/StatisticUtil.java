package util;

import java.util.Iterator;

import vo.StockVO;

public class StatisticUtil {
	/**
	 * PE   max and min
	 * @param itr
	 * @return
	 */
	public static double getMaxOfPE(Iterator<StockVO>itr){
		double maxValue = -1*Double.MAX_VALUE;
		while(itr.hasNext()){
			StockVO temp = itr.next();
			if(temp.pe>maxValue){maxValue = temp.pe;}
		}
		
		return maxValue;
	}
	public static double getMinOfPE(Iterator<StockVO>itr){
		double minValue = Double.MAX_VALUE;
		while(itr.hasNext()){
			StockVO temp = itr.next();
			if(temp.pe<minValue){minValue = temp.pe;}
		}
		
		return minValue;
	}
	
	/**
	 * pb max and min
	 * @param itr
	 * @return
	 */
	public static double getMaxOfPB(Iterator<StockVO>itr){
		double maxValue = -1*Double.MAX_VALUE;
		while(itr.hasNext()){
			StockVO temp = itr.next();
			if(temp.pb>maxValue){maxValue = temp.pb;}
		}
		
		return maxValue;
	}
	public static double getMinOfPB(Iterator<StockVO>itr){
		double minValue = Double.MAX_VALUE;
		while(itr.hasNext()){
			StockVO temp = itr.next();
			if(temp.pb<minValue){minValue = temp.pb;}
		}
		
		return minValue;
	}
	
	
}
