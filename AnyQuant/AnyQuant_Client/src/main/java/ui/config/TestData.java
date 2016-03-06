/**
 * 产生假数据的类。。~
 *@author dsn
 *@version 2016年3月6日    上午8:50:01
 */

package ui.config;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import vo.StockVO;

public class TestData {
	static DecimalFormat   df   =new DecimalFormat("#.00");  
	static String[] dates=new String[500];
	static int total=12*31;
	static{
		for(int j=0;j<12;j++){
			for(int i=0;i<31;i++){
				dates[j*31+i]="2015-"+(j+1)+"-"+(i+1);
			}
		}
		
	}
	/*
	 * public StockVO(String date, String name, String code, 
	 * double high,
			double low, double open, double close, double adj_Price,
			long volume, double turnover, double pe, double pb,
			double amplitude, double changeRate) {
	 */
	
	
	/**
	 * 得到k个数据--同一支股票，时间不同
	 * @param k
	 * @return
	 */
	public static List<StockVO> getOne_Stock_VOs(int k){
		List<StockVO> vos=new ArrayList<StockVO>();
		StockVO vo;
		for(int i=0;i<k;i++){
				if(i>=total) i=total-1;//以防越界。。
				vo=new StockVO(dates[i], "石化油服", "600121",
						25+Math.random()*5,
						20+Math.random()*5, 
						20+Math.random()*10,//open
						20+Math.random()*10,//close
						20+Math.random()*10,
						2, 
						20000+(long)(Math.random()*10000),
						Math.random(), Math.random() ,Math.random(), 
						Math.random(), Math.random());
				vos.add(vo);
		}
		return vos;
	}
	/**
	 * 得到k个数据--不同支股票
	 * @param k
	 * @return
	 */
	public static List<StockVO> getDiffer_Stock_VOs(int k){
		List<StockVO> vos=new ArrayList<StockVO>();
		StockVO vo;
		for(int i=0;i<k;i++){
			if(i>=total) i=total-1;//以防越界。。
			vo=new StockVO(dates[i], "我是A股~", (600000+i)+"",
					Double.parseDouble(df.format(25+Math.random()*5)),
					20+Math.random()*5, 
					20+Math.random()*10,//open
					20+Math.random()*10,//close
					20+Math.random()*10,
					2, 
					20000+(long)(Math.random()*10000),
					Math.random(), Math.random() ,Math.random(), 
					Math.random(), Math.random());

			vos.add(vo);
	}
		return vos;
	}
	

	public static void main(String[] args) {
		List<StockVO> vos=TestData.getDiffer_Stock_VOs(60);
		for(StockVO vo:vos){
			System.out.println(vo.high);
		}
	}
}

