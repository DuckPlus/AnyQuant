package po;
/**
 *
 * @author ss
 * @date 2016年3月6日
 */
public class BenchMarkPO {
          private String code,date;
          private double open,close,high,low,adj_price;
          private long volume;
          
         public BenchMarkPO(){
        	 super();
         }
         
         public BenchMarkPO(String code,String date,double open,double close,double high,
        		 double low, double adj_price){
        	    this.code = code;
        	    this.date =date;
        	    this.open = open ;
        	    this.close = close;
        	    this.high = high;
        	    this.low=low;
        	    this.adj_price =adj_price;
         }
          
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public double getHigh() {
			return high;
		}
		public void setHigh(double high) {
			this.high = high;
		}
		public double getAdj_price() {
			return adj_price;
		}
		public void setAdj_price(double adj_price) {
			this.adj_price = adj_price;
		}
		public double getOpen() {
			return open;
		}
		public void setOpen(double open) {
			this.open = open;
		}
		public double getClose() {
			return close;
		}
		public void setClose(double close) {
			this.close = close;
		}
		public double getLow() {
			return low;
		}
		public void setLow(double low) {
			this.low = low;
		}
		public long getVolume() {
			return volume;
		}
		public void setVolume(long volume) {
			this.volume = volume;
		}
}
