package util.update.vo;

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
		this.vo=s;
		date = new SimpleStringProperty(s.date);
		name = new SimpleStringProperty(s.name);
		code = new SimpleStringProperty(s.code);
		board = new SimpleStringProperty(s.board);
		region = new SimpleStringProperty(s.region);

		preClose = new SimpleDoubleProperty(s.preClose);
		low = new SimpleDoubleProperty(s.low);
		high = new SimpleDoubleProperty(s.high);
		open = new SimpleDoubleProperty(s.open);
		close = new SimpleDoubleProperty(s.close);
		accAdjFactor = new SimpleDoubleProperty(s.accAdjFactor);
		turnoverVol = new SimpleLongProperty(s.turnoverVol);
		turnoverValue = new SimpleDoubleProperty(s.turnoverValue);
		turnoverRate = new SimpleDoubleProperty(s.turnoverRate);
		cirMarketValue = new SimpleDoubleProperty(s.cirMarketValue);
		totalMarketValue = new SimpleDoubleProperty(s.totalMarketValue);
		pe = new SimpleDoubleProperty(s.pe);
		pb = new SimpleDoubleProperty(s.pb);
		amplitude = new SimpleDoubleProperty(s.amplitude);
		changeRate = new SimpleDoubleProperty(s.changeRate);

	}
	public SimpleStringProperty getStringAmplitude(){
		SimpleStringProperty str=new SimpleStringProperty(String.format("%.2f",(vo.amplitude*100))+"%");
		return str;
	}
	public SimpleStringProperty getStringChangeRate(){
		SimpleStringProperty str=new SimpleStringProperty(String.format("%.2f",(vo.changeRate*100))+"%");
		return str;
	}
	 public SimpleStringProperty date ,name,code,board,region;
	 public SimpleDoubleProperty high ,low,open,close,preClose,accAdjFactor,turnoverValue,turnoverRate,pe,pb,cirMarketValue, totalMarketValue,amplitude,changeRate;
	 public SimpleLongProperty turnoverVol;
	 private StockVO vo;
}
