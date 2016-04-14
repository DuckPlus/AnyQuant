package ui.controller;

import enumeration.CmpChartType;
import javafx.scene.chart.XYChart.Series;

public class CmpChartRecord {
	public CmpChartRecord(CmpChartType type,String code,Series<String, Number>series) {
		// TODO Auto-generated constructor stub
		this.type = type;
		this.series = series;
		this.stockCode = code;
	}
	CmpChartType type;
	String stockCode;
	Series<String, Number> series;
	public CmpChartType getType() {
		return type;
	}
	public String getStockCode() {
		return stockCode;
	}
	public Series<String, Number> getSeries() {
		return series;
	}
}
