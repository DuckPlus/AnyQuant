package util.update.po;


import util.MyDate;

/**
 *
 * @author Qiang
 * @date Apr 7, 2016
 */
public class TimeSharingPO {
	public MyDate nowTime;

	public double nowPrice;

	public long volume;
	
	public double value;


	public TimeSharingPO(MyDate nowTime, double nowPrice, long volume, double value) {
		super();
		this.nowTime = nowTime;
		this.nowPrice = nowPrice;
		this.volume = volume;
		this.value = value;
	}

	
	
}
