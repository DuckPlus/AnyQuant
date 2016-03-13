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
	 public double adj_price;
	 public long volume;
	 public double turnover;
	 public double pe_ttm,pb;
	 public double amplitude;//振幅
	 public double changeRate;
	
	
	public StockVO() {
	}

	public StockVO(String date, String name, String code, double high,
			double low, double open, double close, double preclose,
			double adj_Price, long volume, double turnover, double pe,
			double pb, double amplitude, double changeRate) {
		super();
		this.date = date;
		this.name = name;
		this.code = code;
		this.high = high;
		this.low = low;
		this.open = open;
		this.close = close;
		this.preClose = preclose;
		this.adj_price = adj_Price;
		this.volume = volume;
		this.turnover = turnover;
		this.pe_ttm = pe;
		this.pb = pb;
		this.amplitude = amplitude;
		this.changeRate = changeRate;
	}
	
	
		
}
