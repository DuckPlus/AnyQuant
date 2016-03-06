package vo;

/**
 * StockVO类
 * 
 * @author czq
 * @date 2016年3月4日
 */
public class StockVO {
	public String date ,name,code;
	public double high ,low;
	public double open,close,preClose;
	 //目前获取adj_Price会出错
	public double adj_Price;
	public long volume;
	public double turnover;
	 //目前获取pe会出错
	public double pe,pb;
	 //尚未实现
	public double amplitude;
	 //尚未实现
	public double changeRate;
	
	public StockVO() {
		super();
	}
	
	public StockVO(String date, String name, double high, double low,
			double open, double close,double preClose, double adj_Price, long volume,
			double turnover, double pe, double pb, double amplitude,
			double changeRate) {
		super();
		this.date = date;
		this.name = name;
		this.high = high;
		this.low = low;
		this.open = open;
		this.close = close;
		this.preClose = preClose;
		this.adj_Price = adj_Price;
		this.volume = volume;
		this.turnover = turnover;
		this.pe = pe;
		this.pb = pb;
		this.amplitude = amplitude;
		this.changeRate = changeRate;
	}

	
		
}
