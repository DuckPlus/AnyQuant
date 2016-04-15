package util.candleStick;
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
	double upperBounds=0;
	double lowerBounds=10000;
	MyLineChart lineChart;
	StockBLService stockBl = StockBLImpl.getAPIBLService();
	Stock currentStock;
	List<TimeSharingVO> timeSharingData;
	double HpixelPerValue=4;
	private Tooltip tooltip = new Tooltip();
	public TimeSharingChart(Stock cs) {
		currentStock = cs;
		init();
	}
	private void init(){
		lineChart = new MyLineChart();
		timeSharingData = stockBl.getSharingVOs(currentStock.code.get());
		
    	standardizingChartProperty();
    	
    	Iterator<TimeSharingVO>itr = timeSharingData.iterator();
    	while(itr.hasNext()){
    		TimeSharingVO temp = itr.next();
    		System.out.println(temp.nowTime.TimeToString()+"  "+temp.nowPrice);
    		lineChart.addData(temp.nowTime.TimeToString(), temp.nowPrice);
//    		TooltipContentTimeSharing tip=(TooltipContentTimeSharing)tooltip.getGraphic();
//    		tip.update(temp.nowTime.TimeToString(), temp.nowPrice);
    	}
    	lineChart.setSize((int)(HpixelPerValue*timeSharingData.size()), Height);


	}
	public void standardizingChartProperty(){
    	//get the upperBounds and lowerBounds
    	Iterator<TimeSharingVO> itrc = timeSharingData.iterator();
//    	tooltip.setGraphic(new TooltipContentTimeSharing());
//        Tooltip.install(lineChart.getTimesharingChart(), tooltip);
    	while(itrc.hasNext()){
    		TimeSharingVO tempCmp = itrc.next();
    		if(tempCmp.nowPrice>upperBounds)upperBounds = tempCmp.nowPrice;
    		if(tempCmp.nowPrice<lowerBounds)lowerBounds = tempCmp.nowPrice;

    	}
    	//end
    	lineChart.getTimesharingChart().setLegendVisible(false);
		lineChart.setChartProperty(currentStock.name.get(), upperBounds+0.02, lowerBounds-0.02);
	}

	public LineChart<String, Number> getTimeSharingChart(){
		return lineChart.getTimesharingChart();
	}
	public void updateData(){
		timeSharingData = stockBl.getSharingVOs(currentStock.code.get());
		
    	standardizingChartProperty();
    	
    	Iterator<TimeSharingVO>itr = timeSharingData.iterator();
    	while(itr.hasNext()){
    		TimeSharingVO temp = itr.next();
    		lineChart.addData(temp.nowTime.TimeToString(), temp.nowPrice);
//    		TooltipContentTimeSharing tip=(TooltipContentTimeSharing)tooltip.getGraphic();
//    		tip.update(temp.nowTime.TimeToString(), temp.nowPrice);
    	}
    	lineChart.setSize((int)(HpixelPerValue*timeSharingData.size()), Height);
	}

}
