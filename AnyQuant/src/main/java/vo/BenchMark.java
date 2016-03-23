package vo;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 大盘
 * @author dzm
 * @date 2016年3月23日
 */
public class BenchMark {
//	public String code,date;
	public DoubleProperty open,close,high,low,adj_price;
	public LongProperty volume;
    public StringProperty code,date;
  
    public BenchMark(BenchMarkVO b){
    	code = new SimpleStringProperty(b.code);
    	date = new SimpleStringProperty(b.date);
    	volume = new SimpleLongProperty(b.volume);
    	open = new SimpleDoubleProperty(b.open);
    	close = new SimpleDoubleProperty(b.close);
    	high = new SimpleDoubleProperty(b.high);
    	low = new SimpleDoubleProperty(b.low);
    	adj_price = new SimpleDoubleProperty(b.adj_price);
    }
   

}
