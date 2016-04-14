package util;

import java.util.Iterator;

import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import vo.StockVO;

public class StatisticUtil {
	/**
	 * PE   max and min
	 * @param itr
	 * @return
	 */
	private static double getMaxOfPE(Iterator<StockVO>itr){
		double maxValue = -1*Double.MAX_VALUE;
		while(itr.hasNext()){
			StockVO temp = itr.next();
			if(temp.pe>maxValue){maxValue = temp.pe;}
		}
		
		return maxValue;
	}
	private static double getMinOfPE(Iterator<StockVO>itr){
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
	private static double getMaxOfPB(Iterator<StockVO>itr){
		double maxValue = -1*Double.MAX_VALUE;
		while(itr.hasNext()){
			StockVO temp = itr.next();
			if(temp.pb>maxValue){maxValue = temp.pb;}
		}
		
		return maxValue;
	}
	private static double getMinOfPB(Iterator<StockVO>itr){
		double minValue = Double.MAX_VALUE;
		while(itr.hasNext()){
			StockVO temp = itr.next();
			if(temp.pb<minValue){minValue = temp.pb;}
		}
		
		return minValue;
	}
	
	/**
	 * turnoverValue max and min
	 * @param itr
	 * @return
	 */
	public static double getMaxOfTurnoverValue(Iterator<StockVO>itr){
		double maxValue = -1*Double.MAX_VALUE;
		
		while(itr.hasNext()){
			StockVO temp = itr.next();
			if(temp.turnoverValue>maxValue){maxValue = temp.turnoverValue;}
		}
		
		return maxValue;
	}
	public static double getMinOfTurnoverValue(Iterator<StockVO>itr){
		double minValue = Double.MAX_VALUE;
		while(itr.hasNext()){
			StockVO temp = itr.next();
			if(temp.turnoverValue<minValue){minValue = temp.turnoverValue;}
		}
		
		return minValue;
	}

	
	/**
	 * pb无量纲化
	 */
	public static Series<String, Number>dimensionLessPB(Iterator<StockVO>itr,Iterator<StockVO>itrMax,Iterator<StockVO>itrMin){
		System.err.println("无量纲化市净率");
		Series<String, Number>result = new Series<String,Number>();
		double max = getMaxOfPB(itrMax);
		double min = getMinOfPB(itrMin);
		while(itr.hasNext()){
			StockVO temp = itr.next();
			
			result.getData().add(new XYChart.Data<String, Number>(temp.date, (temp.pb-min)/(max-min)));
		}
		
		return result;
	}
	
	/**
	 * pe无量纲化
	 */
	public static Series<String, Number>dimensionLessPE(Iterator<StockVO>itr,Iterator<StockVO>itrMax,Iterator<StockVO>itrMin){
		System.err.println("无量纲化市盈率");
		Series<String, Number>result = new Series<String,Number>();
		double max = getMaxOfPE(itrMax);
		double min = getMinOfPE(itrMin);
		while(itr.hasNext()){
			StockVO temp = itr.next();
			
			result.getData().add(new XYChart.Data<String, Number>(temp.date, (temp.pe-min)/(max-min)));
		}
		
		return result;
	}
	/**
	 * 成交量无量纲化
	 */
	public static Series<String, Number>dimensionLessVolumeValue(Iterator<StockVO>itr,Iterator<StockVO>itrMax,Iterator<StockVO>itrMin){
		System.err.println("无量纲化成交量");
		Series<String, Number>result = new Series<String,Number>();
		double max = getMaxOfTurnoverValue(itrMax);
		double min = getMinOfTurnoverValue(itrMin);
		System.out.println("max:"+max+"  min:"+min);
		while(itr.hasNext()){
			StockVO temp = itr.next();
//			System.out.println(temp.turnoverV);
			if(temp.turnoverVol-min<0)System.out.println("less than zero"+temp.turnoverValue+" - "+min);
			double dimensionLessValue = (temp.turnoverValue-min)/(max-min);
//			System.out.println(dimensionLessValue);
			result.getData().add(new XYChart.Data<String, Number>(temp.date, dimensionLessValue));
		}
		
		return result;
	}
	
}
