package vo;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
*
*@author:duanzhengmou
*@date:2016年3月20日
*/
public class Stock {
	
	public Stock(StockVO s) {
		date = new SimpleStringProperty(s.date);
		name = new SimpleStringProperty(s.name);
		code = new SimpleStringProperty(s.code);
		preClose = new SimpleDoubleProperty(s.preClose);
		low = new SimpleDoubleProperty(s.low);
		high = new SimpleDoubleProperty(s.high);
		open = new SimpleDoubleProperty(s.open);
		close = new SimpleDoubleProperty(s.close);
		adj_price = new SimpleDoubleProperty(s.adj_price);
		turnover = new SimpleDoubleProperty(s.turnover);
		pe_ttm = new SimpleDoubleProperty(s.pe_ttm);
		pb = new SimpleDoubleProperty(s.pb);
		amplitude = new SimpleDoubleProperty(s.amplitude);
		changeRate = new SimpleDoubleProperty(s.changeRate);
		volume = new SimpleLongProperty(s.volume);
	}
	 public SimpleStringProperty date ,name,code;
	 public SimpleDoubleProperty high ,low,open,close,preClose,adj_price,turnover,pe_ttm,pb,amplitude,changeRate;
	 public SimpleLongProperty volume;
}
