package ui.controller.candleStick;
/**
*
*@author:duanzhengmou
*@date:Apr 9, 2016
*/

import java.util.Iterator;
import java.util.List;

import blimpl.StockBLImpl;
import blservice.StockBLService;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Tooltip;
import vo.Stock;
import vo.TimeSharingVO;

public class TimeSharingChart {
	private static final int  Height=630;
	private static final int  Width=770;
	MyLineChart lineChart;
	StockBLService stockBl = StockBLImpl.getAPIBLService();
	Stock currentStock;
	 private Tooltip tooltip = new Tooltip();
	public TimeSharingChart(Stock cs) {
		currentStock = cs;
		init();
	}
	private void init(){
		double HpixelPerValue=4;
		lineChart = new MyLineChart();
		List<TimeSharingVO> timeSharingData = stockBl.getSharingVOs(currentStock.code.get());
    	//get the upperBounds and lowerBounds
    	Iterator<TimeSharingVO> itrc = timeSharingData.iterator();
    	double upperBounds=0;
    	double lowerBounds=10000;
    	tooltip.setGraphic(new TooltipContentTimeSharing());
        Tooltip.install(lineChart.getTimesharingChart(), tooltip);
    	while(itrc.hasNext()){
    		TimeSharingVO tempCmp = itrc.next();
    		if(tempCmp.nowPrice>upperBounds)upperBounds = tempCmp.nowPrice;
    		if(tempCmp.nowPrice<lowerBounds)lowerBounds = tempCmp.nowPrice;

    	}
    	//end
    	lineChart.setChartProperty(currentStock.name.get()+"   Monitoring", upperBounds+0.02, lowerBounds-0.02);
    	Iterator<TimeSharingVO>itr = timeSharingData.iterator();
    	while(itr.hasNext()){
    		TimeSharingVO temp = itr.next();
    		lineChart.addData(temp.nowTime.TimeToString(), temp.nowPrice);
    		TooltipContentTimeSharing tip=(TooltipContentTimeSharing)tooltip.getGraphic();
    		tip.update(temp.nowTime.TimeToString(), temp.nowPrice);
    	}
    	lineChart.setSize((int)(HpixelPerValue*timeSharingData.size()), Height);



	}

	public LineChart<String, Number> getTimeSharingChart(){
		return lineChart.getTimesharingChart();
	}

}
