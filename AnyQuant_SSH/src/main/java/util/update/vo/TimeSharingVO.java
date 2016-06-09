package util.update.vo;


import util.MyDate;

/**
 * 用于实时价格图的vo
 * @author czq
 * @date 2016年3月10日
 */
public class TimeSharingVO {
	
	public MyDate nowTime;

	public double nowPrice;
	/**
	 * 成交量
	 */
	public long volume;
	/**
	 * 该分钟成交额
	 */
	public double value;
	
	public TimeSharingVO() {
	}

	public TimeSharingVO(MyDate nowTime, double nowPrice, long volume, double value) {
		super();
		this.nowTime = nowTime;
		this.nowPrice = nowPrice;
		this.volume = volume;
		this.value = value;
	}

	
	
	
	
	
}
