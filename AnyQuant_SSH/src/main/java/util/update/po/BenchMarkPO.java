package util.update.po;


import util.update.enumeration.StaticMessage;
import util.update.helper.TransferHelper;

/**
 *
 * @author ss
 * @date 2016年3月6日
 */
public class BenchMarkPO {
          private String code,date,name;
          private double preclose,open,close,high,low,turnoverValue,change,changePct;  //涨跌，涨跌幅
          private long turnoverVol;

         public BenchMarkPO(){
        	 super();
         }

         public BenchMarkPO(String code,String date,String name,double preclose,double open,double close,double high,
        		 double low, double turnoverValue,double change,double changePct,long turnoverVol){
        	    this.code = code;
        	    this.date =date;
        	    this.name=name;
        	    this.preclose=preclose;
        	    this.open = open ;
        	    this.close = close;
        	    this.high = high;
        	    this.low=low;
        	    this.turnoverValue=turnoverValue;
        	    this.change=change;
        	    this.changePct=changePct;
        	    this.turnoverVol=turnoverVol;

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

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public double getPreclose() {
			return preclose;
		}

		public void setPreclose(double preclose) {
			this.preclose = preclose;
		}

		public long getTurnoverVol() {
			return turnoverVol;
		}

		public void setTurnoverVol(long turnoverVol) {
			this.turnoverVol = turnoverVol;
		}

		public double getTurnoverValue() {
			return turnoverValue;
		}

		public void setTurnoverValue(double turnoverValue) {
			this.turnoverValue = turnoverValue;
		}

		public double getChangePct() {
			return changePct;
		}

		public void setChangePct(double changePct) {
			this.changePct = changePct;
		}

		public double getChange() {
			return change;
		}

		public void setChange(double change) {
			this.change = change;
		}
		
		
		public String MyToString(String splitChar) {
			return TransferHelper.ObjectToString(StaticMessage.BENCHMARK_PO , this , splitChar);
		}
}
