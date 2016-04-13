package ui.controller;

import enumeration.CmpChartType;
import javafx.scene.chart.XYChart.Series;

public class CmpChartRecord {
	public CmpChartRecord(CmpChartType type,String code,Series<String, Number>series) {
		// TODO Auto-generated constructor stub
	}
	CmpChartType type;
	String stockCode;
	Series<String, Number> series;
}
