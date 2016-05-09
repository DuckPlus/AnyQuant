package vo;


import util.MyDate;

/**
 * 成交额（dealAmount）
 * 及成交量(volume)VO
 * @author czq
 * @date 2016年3月10日
 */
public class DealVO {
	public double dealAmount;
	public long volume;
	public MyDate date;
	
	public DealVO (){
		
	}
	
	public DealVO(double dealAmont , long volume ,MyDate date){
		    this.dealAmount = dealAmont;
		    this.volume = volume;
		    this.date=date;
	}
}
