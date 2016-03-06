package po;

import dataservice.InitialBean;

public class StockPO implements InitialBean {
	 private String date ,name,code;
	 private double high ,low;
	 private double open,close,preClose;
	 //目前获取adj_Price会出错
	 private double adj_Price;
	 private long volume;
	 private double turnover;
	 //目前获取pe会出错
	 private double pe,pb;
	 //尚未实现
	 private double amplitude;
	 //尚未实现
	 private double changeRate;
	
    public StockPO(){
             super();
    }

	public StockPO(String date, String name, String code, double high,
			double low, double open, double close, double adj_Price,
			long volume, double turnover, double pe, double pb,
			double amplitude, double changeRate) {
		super();
		this.date = date;
		this.name = name;
		this.code = code;
		this.high = high;
		this.low = low;
		this.open = open;
		this.close = close;
		this.adj_Price = adj_Price;
		this.volume = volume;
		this.turnover = turnover;
		this.pe = pe;
		this.pb = pb;
		this.amplitude = amplitude;
		this.changeRate = changeRate;
	}

	public String getDate() {
		return date;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public double getHigh() {
		return high;
	}

	public double getLow() {
		return low;
	}

	public double getOpen() {
		return open;
	}

	public double getClose() {
		return close;
	}

	public double getAdj_Price() {
		return adj_Price;
	}

	public long getVolume() {
		return volume;
	}

	public double getTurnover() {
		return turnover;
	}

	public double getPe() {
		return pe;
	}

	public double getPb() {
		return pb;
	}

	public double getAmplitude() {
		return amplitude;
	}

	public double getChangeRate() {
		return changeRate;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public void setAdj_Price(double adj_Price) {
		this.adj_Price = adj_Price;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}

	public void setTurnover(double turnover) {
		this.turnover = turnover;
	}

	public void setPe(double pe) {
		this.pe = pe;
	}

	public void setPb(double pb) {
		this.pb = pb;
	}

	public void setAmplitude(double amplitude) {
		this.amplitude = amplitude;
	}

	public void setChangeRate(double changeRate) {
		this.changeRate = changeRate;
	}

	public double getPreClose() {
		return preClose;
	}

	public void setPreClose(double preClose) {
		this.preClose = preClose;
	}

	public void  computeAmplitude(){
		   double temp = Math.abs(high - low)/preClose;
		   this.amplitude =    (int)(temp*10000)/10000.0;
	}
	
	public void  computeChangeRate(){
		   double temp = (close-open)/open;
		   this.changeRate =     (int)(temp*10000)/10000.0;
	}
	
	@Override
	public void initialize() {
		
		
		 computeChangeRate();
	//	 computeAmplitude();
		
	}
    
    
    
	
	
	
}
