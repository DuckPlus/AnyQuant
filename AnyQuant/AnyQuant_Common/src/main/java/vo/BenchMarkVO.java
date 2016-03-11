package vo;
/**
 * 大盘数据VO
 * @author ss
 * @date 2016年3月6日
 */
public class BenchMarkVO {
	public String code,date;
	public double open,close,high,low,adj_price;
	public long volume;
    
   public BenchMarkVO(){
  	 super();
   }
   
   public BenchMarkVO(String code,String date,double open,double close,double high,
  		 double low, double adj_price){
  	    this.code = code;
  	    this.date =date;
  	    this.open = open ;
  	    this.close = close;
  	    this.high = high;
  	    this.low=low;
  	    this.adj_price =adj_price;
   }

}
