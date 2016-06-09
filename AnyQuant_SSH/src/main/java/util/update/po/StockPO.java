package util.update.po;


import util.update.enumeration.StaticMessage;
import util.update.helper.TransferHelper;

public class StockPO {
	private String date, name, code;




	/**
	 * 板块
	 */
	private String board;
	/**
	 * 地域
	 */
	private String region;
	private double high, low;
	private double open, close, preClose;
	/**
	 * 成交量
	 */
	private long turnoverVol;
	/**
	 * 成交额
	 */
	private double turnoverValue;
	/**
	 * 换手率
	 */
	private double turnoverRate;
	/**
	 * 市净率
	 */
	private double pb;
	/**
	 * 市盈率
	 */
	private double pe;
	/**
	 * 累积前复权因子
	 */
	private double accAdjFactor;
	/**
	 * 流通市值
	 */
	private double cirMarketValue;
	/**
	 * 总市值
	 */
	private double totalMarketValue;
	/**
	 * 振幅
	 */
	private double amplitude;
	/**
	 * 变化率
	 */
	private double changeRate;

	public StockPO() {
		super();
	}

	public StockPO(String date, String name, String code,  String board, String region, double high, double low,
                   double open, double close, double preClose, long turnoverVol, double turnoverValue, double turnoverRate,
                   double pb, double pe, double accAdjFactor, double cirMarketValue, double totalMarketValue, double amplitude,
                   double changeRate) {
		super();
		this.date = date;
		this.name = name;
		this.code = code;

        this.board = board;
		this.region = region;
		this.high = high;
		this.low = low;
		this.open = open;
		this.close = close;
		this.preClose = preClose;
		this.turnoverVol = turnoverVol;
		this.turnoverValue = turnoverValue;
		this.turnoverRate = turnoverRate;
		this.pb = pb;
		this.pe = pe;
		this.accAdjFactor = accAdjFactor;
		this.cirMarketValue = cirMarketValue;
		this.totalMarketValue = totalMarketValue;
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

	public double getPe() {
		return this.pe;
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

	public long getTurnoverVol() {
		return turnoverVol;
	}

	public double getTurnoverRate() {
		return turnoverRate;
	}

	public double getTotalMarketValue() {
		return totalMarketValue;
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

	public void setPe(double pe) {
		this.pe = pe;
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

	public void setTurnoverVol(long volume) {
		this.turnoverVol = volume;
	}

	public void setTurnoverRate(double turnover) {
		this.turnoverRate = turnover;
	}

	public void setTotalMarketValue(double totalMarketValue) {
		this.totalMarketValue = totalMarketValue;
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

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	public double getTurnoverValue() {
		return turnoverValue;
	}

	public void setTurnoverValue(double turnoverValue) {
		this.turnoverValue = turnoverValue;
	}

	public double getCirMarketValue() {
		return this.cirMarketValue;
	}

	public void setCirMarketValue(double cirMarketValue) {
		this.cirMarketValue = cirMarketValue;
	}

	public double getAccAdjFactor() {
		return accAdjFactor;
	}

	public void setAccAdjFactor(double accAdjFactor) {
		this.accAdjFactor = accAdjFactor;
	}

	public void computeAmplitude() {
		double temp = Math.abs(high - low) / preClose;
		if (temp > 10) {
			temp = 0;
			return;
		}
		this.amplitude = (int) (temp * 10000) / 10000.0;

	}

	public void computeChangeRate() {
		double temp = (close - preClose) / preClose;
		if (temp > 10) {
			temp = 0;
			return;
		}
		this.changeRate = (int) (temp * 10000) / 10000.0;
	}

	public void computeTurnOverRate() {

		this.turnoverRate = (int) (this.turnoverRate * 10000) / 10000.0;

	}




	public String MyToString(String splitChar) {
		return TransferHelper.ObjectToString(StaticMessage.STOCK_PO , this , splitChar);
//		try {
		// return "" + date + splitChar + name + splitChar + code + splitChar +
		// board + splitChar + region + splitChar + high + splitChar + low
		// + splitChar + open + splitChar + close + splitChar + preClose +
		// splitChar + accAdjFactor + splitChar + turnoverValue + splitChar
		// + turnoverRate + splitChar + pe + splitChar + pb + splitChar +
		// cirMarketValue + splitChar + totalMarketValue + splitChar
		// + amplitude + splitChar + changeRate + splitChar + turnoverVol;
	}

}
