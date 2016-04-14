/**
 * @author:dsn
 * @date:2016年4月13日
 */
package util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import blimpl.StockBLImpl;
import blservice.StockBLService;
import enumeration.MyDate;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import vo.DealVO;
import vo.Stock;

public class DealChart {

	/**
	 * @param stock
	 */
	public DealChart(Stock stock) {
		this.stock=stock;
		end=MyTime.getToDay();
		start=MyTime.getAnotherDay(end, -30);
	}
	/**
	 * @param stock2
	 * @param start2
	 * @param end2
	 */
	public DealChart(Stock stock, MyDate start, MyDate end) {
		this.stock=stock;
		this.end=end;
		this.start=start;
	}

	public void initChart(){
		this.barchart = MyBarChart.createBarChart();
        barchart.setMinSize(width,height);
        barchart.setMaxSize(width,height);
        barchart.setPrefSize(width,height);
        barchart.getData().add(series);
	}

	public void getData(){
        this.series = new XYChart.Series<>();
		list=ctr.getDayDealVOs(stock.code.get(), start, end);
		System.out.println("list.length "+list.size());
        for(DealVO vo: list){
        	series.getData().add(new XYChart.Data<String,Number>(vo.date.DateToString(),vo.volume));
        }
	}

	/**
	 * @return
	 */
	public BarChart getBarChart() {
		return barchart;
	}

    private BarChart barchart;
	private Stock stock;
	private StockBLService ctr = StockBLImpl.getAPIBLService();
	private MyDate start,end;
	private List<DealVO> list;
	private XYChart.Series<String,Number> series;
	private int width=700,height=600;
}
