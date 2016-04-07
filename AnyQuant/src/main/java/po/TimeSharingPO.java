package po;

import enumeration.MyDate;

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

	public TimeSharingPO() {
		// TODO Auto-generated constructor stub
	}

	public TimeSharingPO(MyDate nowTime, double nowPrice, long volume, double value) {
		super();
		this.nowTime = nowTime;
		this.nowPrice = nowPrice;
		this.volume = volume;
		this.value = value;
	}

	
	
}
