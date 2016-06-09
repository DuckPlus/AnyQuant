package util.update.vo;
/**
 * 大盘数据VO
 * @author ss
 * @date 2016年3月6日
 */
public class BenchMarkVO {
	 public String code,date,name;
     public double preclose,open,close,high,low,turnoverValue,change,changePct;
     public long turnoverVol;

   public BenchMarkVO(){
  	 super();
   }

   public BenchMarkVO(String code,String date,String name,double preclose,double open,double close,double high,
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

}
