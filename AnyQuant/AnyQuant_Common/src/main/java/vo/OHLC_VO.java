package vo;
/**
 *主要用于K线图的VO
 * @author czq
 * @date 2016年3月10日
 */
public class OHLC_VO {
        public  double open , close, high, low;
        public OHLC_VO(){
        	
        }
        public OHLC_VO(double open , double close,double high ,double low){
        	this.open =open;
        	this.close = close;
        	this.high=high;
        	this.low=low;
        }
        
          
}
