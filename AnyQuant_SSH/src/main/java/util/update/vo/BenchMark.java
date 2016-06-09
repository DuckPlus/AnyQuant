package util.update.vo;

import javafx.beans.property.*;

/**
 * 大盘
 * @author dzm
 * @date 2016年3月23日
 */
public class BenchMark {
	public StringProperty code,date,name;
	public DoubleProperty preClose,open,close,high,low,turnoverValue;
	public LongProperty turnoverVol;

    public BenchMark(BenchMarkVO b){
    	code = new SimpleStringProperty(b.code);
    	date = new SimpleStringProperty(b.date);
    	name = new SimpleStringProperty(b.name);
    	turnoverVol = new SimpleLongProperty(b.turnoverVol);
    	preClose = new SimpleDoubleProperty(b.preclose);
    	open = new SimpleDoubleProperty(b.open);
    	close = new SimpleDoubleProperty(b.close);
    	high = new SimpleDoubleProperty(b.high);
    	low = new SimpleDoubleProperty(b.low);
    	turnoverValue = new SimpleDoubleProperty(b.turnoverValue);
    }


}
