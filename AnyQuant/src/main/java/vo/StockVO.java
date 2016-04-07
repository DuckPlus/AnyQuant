package vo;

/**
 * StockVO类
 *
 * @author czq
 * @date 2016年3月4日
 */
public class StockVO implements Cloneable{
	//新加了板块、地域属性
		 public String date ,name,code,board,region;
		 public double high ,low;
		 public double open,close,preClose;
		 //成交量
		 public long turnoverVol;
		 //成交额
		 public double turnoverValue;
		 //换手率
		 public double turnoverRate;
		 //市净率,市盈率
		 public double pb,pe;
		 //累积前复权因子
		 public double accAdjFactor;
		 //流通市值
		 public double cirMarketValue;
		 //总市值
		 public double totalMarketValue;
	     //振幅
		 public double amplitude;
		 //变化率
		 public double changeRate;

	    public StockVO(){
	             super();
	    }

		public StockVO(String date, String name, String code,String board,String region, double high,
				double low, double open, double close, double preClose,double accAdjFactor,
				long volume,double turnoverValue, double turnoverRate, double pe, double pb,double cirMarketValue,
				double totalMarketValue,double amplitude, double changeRate) {
			super();
			this.date = date;
			this.name = name;
			this.code = code;
			this.board=board;
			this.region=region;
			this.high = high;
			this.low = low;
			this.open = open;
			this.close = close;
			this.preClose = preClose;
			this.accAdjFactor = accAdjFactor;
			this.turnoverVol = volume;
			this.turnoverValue=turnoverValue;
			this.turnoverRate = turnoverRate;
			this.pe = pe;
			this.pb = pb;
			this.cirMarketValue=cirMarketValue;
			this.totalMarketValue=totalMarketValue;
			this.amplitude = amplitude;
			this.changeRate = changeRate;
		}

	@Override
	public StockVO clone()  {
		try {
			return (StockVO) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}



}
