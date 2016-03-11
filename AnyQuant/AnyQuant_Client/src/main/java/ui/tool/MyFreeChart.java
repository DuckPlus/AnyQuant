/**
 *@author dsn
 *@version 2016年3月11日    下午5:06:26
 *画图的~
 */

package ui.tool;

import java.awt.Color;
import java.awt.Paint;
import java.text.SimpleDateFormat;
import java.util.List;

import org.AnyQuant_Client.TestFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;

import vo.OHLC_VO;

public class MyFreeChart {
	/**
	 * 画k线图
	 * @param datas
	 * @param panel
	 */
	public static void kLine(List<OHLC_VO> datas, MyPanel panel) {
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
	        double highValue = Double.MIN_VALUE;//设置K线数据当中的最大值 
	        double minValue = Double.MAX_VALUE;//设置K线数据当中的最小值 
	        double high2Value = Double.MIN_VALUE;//设置成交量的最大值 
	        double min2Value = Double.MAX_VALUE;//设置成交量的最低值 
	        OHLCSeries series = new OHLCSeries("");//开高低收数据序列，股票K线图的四个数据，依次是开，高，低，收 
	        series.add(new Day(28, 9, 2007), 9.2, 9.58, 9.16, 9.34); 
	        series.add(new Day(27, 9, 2007), 8.9, 9.06, 8.83, 8.96); 
	        series.add(new Day(26, 9, 2007), 9.0, 9.1, 8.82, 9.04); 
	        series.add(new Day(25, 9, 2007), 9.25, 9.33, 8.88, 9.00); 
	        series.add(new Day(24, 9, 2007), 9.05, 9.50, 8.91, 9.25); 
	        series.add(new Day(21, 9, 2007), 8.68, 9.05, 8.40, 9.00); 
	        series.add(new Day(20, 9, 2007), 8.68, 8.95, 8.50, 8.69); 
	        series.add(new Day(19, 9, 2007), 8.80, 8.94, 8.50, 8.66); 
	        series.add(new Day(18, 9, 2007), 8.88, 9.17, 8.69, 8.80); 
	        series.add(new Day(17, 9, 2007), 8.26, 8.98, 8.15, 8.89); 
	        series.add(new Day(14, 9, 2007), 8.44, 8.45, 8.13, 8.33); 
	        series.add(new Day(13, 9, 2007), 8.13, 8.46, 7.97, 8.42); 
	        series.add(new Day(12, 9, 2007), 8.2, 8.4, 7.81, 8.13); 
	        series.add(new Day(11, 9, 2007), 9.0, 9.0, 8.1, 8.24); 
	        series.add(new Day(10, 9, 2007), 8.6, 9.03, 8.40, 8.95); 
	        series.add(new Day(7, 9, 2007), 8.89, 9.04, 8.70, 8.73); 
	        series.add(new Day(6, 9, 2007), 8.4, 9.08, 8.33, 8.88); 
	        series.add(new Day(5, 9, 2007), 8.2, 8.74, 8.17, 8.36); 
	        series.add(new Day(4, 9, 2007), 7.7, 8.46, 7.67, 8.27); 
	        series.add(new Day(3, 9, 2007), 7.5, 7.8, 7.48, 7.69); 
	        series.add(new Day(31, 8, 2007), 7.4, 7.6, 7.28, 7.43); 
	        series.add(new Day(30, 8, 2007), 7.42, 7.56, 7.31, 7.40); 
	        series.add(new Day(29, 8, 2007), 7.42, 7.66, 7.22, 7.33); 
	        series.add(new Day(28, 8, 2007), 7.31, 7.70, 7.15, 7.56); 
	        series.add(new Day(27, 8, 2007), 7.05, 7.46, 7.02, 7.41); 
	        series.add(new Day(24, 8, 2007), 7.05, 7.09, 6.90, 6.99); 
	        series.add(new Day(23, 8, 2007), 7.12, 7.16, 7.00, 7.03); 
	        series.add(new Day(22, 8, 2007), 6.96, 7.15, 6.93, 7.11); 
	        series.add(new Day(21, 8, 2007), 7.10, 7.15, 7.02, 7.07); 
	        series.add(new Day(20, 8, 2007), 7.02, 7.19, 6.94, 7.14); 
	        final OHLCSeriesCollection seriesCollection = new OHLCSeriesCollection();//保留K线数据的数据集，必须申明为final，后面要在匿名内部类里面用到
	        seriesCollection.addSeries(series); 
	        TimeSeries series2=new TimeSeries("");//对应时间成交量数据 
	        series2.add(new Day(28, 9, 2007), 260659400/100); 
	        series2.add(new Day(27, 9, 2007), 119701900/100); 
	        series2.add(new Day(26, 9, 2007), 109719000/100); 
	        series2.add(new Day(25, 9, 2007), 178492400/100); 
	        series2.add(new Day(24, 9, 2007), 269978500/100); 
	        series2.add(new Day(21, 9, 2007), 361042300/100); 
	        series2.add(new Day(20, 9, 2007), 173912600/100); 
	        series2.add(new Day(19, 9, 2007), 154622600/100); 
	        series2.add(new Day(18, 9, 2007), 200661600/100); 
	        series2.add(new Day(17, 9, 2007), 312799600/100); 
	        series2.add(new Day(14, 9, 2007), 141652900/100); 
	        series2.add(new Day(13, 9, 2007), 221260400/100); 
	        series2.add(new Day(12, 9, 2007), 274795400/100); 
	        series2.add(new Day(11, 9, 2007), 289287300/100); 
	        series2.add(new Day(10, 9, 2007), 289063600/100); 
	        series2.add(new Day(7, 9, 2007), 351575300/100); 
	        series2.add(new Day(6, 9, 2007), 451357300/100); 
	        series2.add(new Day(5, 9, 2007), 442421200/100); 
	        series2.add(new Day(4, 9, 2007), 671942600/100); 
	        series2.add(new Day(3, 9, 2007), 349647800/100); 
	        series2.add(new Day(31, 8, 2007), 225339300/100); 
	        series2.add(new Day(30, 8, 2007), 160048200/100); 
	        series2.add(new Day(29, 8, 2007), 247341700/100); 
	        series2.add(new Day(28, 8, 2007), 394975400/100); 
	        series2.add(new Day(27, 8, 2007), 475797500/100); 
	        series2.add(new Day(24, 8, 2007), 297679500/100); 
	        series2.add(new Day(23, 8, 2007), 191760600/100); 
	        series2.add(new Day(22, 8, 2007), 232570200/100); 
	        series2.add(new Day(21, 8, 2007), 215693200/100); 
	        series2.add(new Day(20, 8, 2007), 200287500/100); 
	        TimeSeriesCollection timeSeriesCollection=new TimeSeriesCollection();//保留成交量数据的集合
	        timeSeriesCollection.addSeries(series2); 

	        //获取K线数据的最高值和最低值 
	        int seriesCount = seriesCollection.getSeriesCount();//一共有多少个序列，目前为一个 
	        for (int i = 0; i < seriesCount; i++) { 
	            int itemCount = seriesCollection.getItemCount(i);//每一个序列有多少个数据项 
	            for (int j = 0; j < itemCount; j++) { 
	                if (highValue < seriesCollection.getHighValue(i, j)) {//取第i个序列中的第j个数据项的最大值
	                    highValue = seriesCollection.getHighValue(i, j); 
	                } 
	                if (minValue > seriesCollection.getLowValue(i, j)) {//取第i个序列中的第j个数据项的最小值
	                    minValue = seriesCollection.getLowValue(i, j); 
	                } 
	            } 
	        } 
	        //获取最高值和最低值 
	        int seriesCount2 = timeSeriesCollection.getSeriesCount();//一共有多少个序列，目前为一个
	        for (int i = 0; i < seriesCount2; i++) { 
	            int itemCount = timeSeriesCollection.getItemCount(i);//每一个序列有多少个数据项 
	            for (int j = 0; j < itemCount; j++) { 
	                if (high2Value < timeSeriesCollection.getYValue(i,j)) {//取第i个序列中的第j个数据项的值
	                    high2Value = timeSeriesCollection.getYValue(i,j); 
	                } 
	                if (min2Value > timeSeriesCollection.getYValue(i, j)) {//取第i个序列中的第j个数据项的值
	                    min2Value = timeSeriesCollection.getYValue(i, j); 
	                } 
	            } 
	        } 

	        final CandlestickRenderer candlestickRender=new CandlestickRenderer();//设置K线图的画图器，必须申明为final，后面要在匿名内部类里面用到
	        candlestickRender.setUseOutlinePaint(true); //设置是否使用自定义的边框线，程序自带的边框线的颜色不符合中国股票市场的习惯
	        candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);//设置如何对K线图的宽度进行设定
	        candlestickRender.setAutoWidthGap(0.001);//设置各个K线图之间的间隔 
	        candlestickRender.setUpPaint(Color.RED);//设置股票上涨的K线图颜色 
	        candlestickRender.setDownPaint(Color.GREEN);//设置股票下跌的K线图颜色 
	        DateAxis x1Axis=new DateAxis();//设置x轴，也就是时间轴 
	        x1Axis.setAutoRange(false);//设置不采用自动设置时间范围 
	        try{ 
	            x1Axis.setRange(dateFormat.parse("2007-08-20"),dateFormat.parse("2007-09-29"));//设置时间范围，注意时间的最大值要比已有的时间最大值要多一天
	        }catch(Exception e){ 
	            e.printStackTrace(); 
	        } 
//	        x1Axis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());//设置时间线显示的规则，用这个方法就摒除掉了周六和周日这些没有交易的日期(很多人都不知道有此方法)，使图形看上去连续
	        x1Axis.setAutoTickUnitSelection(false);//设置不采用自动选择刻度值 
	        x1Axis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);//设置标记的位置 
	        x1Axis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());//设置标准的时间刻度单位
	        x1Axis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,7));//设置时间刻度的间隔，一般以周为单位
	        x1Axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));//设置显示时间的格式
	        NumberAxis y1Axis=new NumberAxis();//设定y轴，就是数字轴 
	        y1Axis.setAutoRange(false);//不不使用自动设定范围 
	        y1Axis.setRange(minValue*0.9, highValue*1.1);//设定y轴值的范围，比最低值要低一些，比最大值要大一些，这样图形看起来会美观些
	        y1Axis.setTickUnit(new NumberTickUnit((highValue*1.1-minValue*0.9)/10));//设置刻度显示的密度
	        XYPlot plot1=new XYPlot(seriesCollection,x1Axis,y1Axis,candlestickRender);//设置画图区域对象

	        XYBarRenderer xyBarRender=new XYBarRenderer(){ 
	            private static final long serialVersionUID = 1L;//为了避免出现警告消息，特设定此值 
	            public Paint getItemPaint(int i, int j){//匿名内部类用来处理当日的成交量柱形图的颜色与K线图的颜色保持一致
	                if(seriesCollection.getCloseValue(i,j)>seriesCollection.getOpenValue(i,j)){//收盘价高于开盘价，股票上涨，选用股票上涨的颜色
	                    return candlestickRender.getUpPaint(); 
	                }else{ 
	                    return candlestickRender.getDownPaint(); 
	                } 
	            }}; 

	            xyBarRender.setMargin(0.1);//设置柱形图之间的间隔 
	            NumberAxis y2Axis=new NumberAxis();//设置Y轴，为数值,后面的设置，参考上面的y轴设置 
	            y2Axis.setAutoRange(false); 
	            y2Axis.setRange(min2Value*0.9, high2Value*1.1); 
	            y2Axis.setTickUnit(new NumberTickUnit((high2Value*1.1-min2Value*0.9)/4));
	            XYPlot plot2=new XYPlot(timeSeriesCollection,null,y2Axis,xyBarRender);//建立第二个画图区域对象，主要此时的x轴设为了null值，因为要与第一个画图区域对象共享x轴
	            CombinedDomainXYPlot combineddomainxyplot = new CombinedDomainXYPlot(x1Axis);//建立一个恰当的联合图形区域对象，以x轴为共享轴
	            combineddomainxyplot.add(plot1, 2);//添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域2/3
	            combineddomainxyplot.add(plot2, 1);//添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域1/3
	            combineddomainxyplot.setGap(10);//设置两个图形区域对象之间的间隔空间 
	            JFreeChart chart = new JFreeChart("k线图", JFreeChart.DEFAULT_TITLE_FONT, combineddomainxyplot, false);
	            ChartPanel chartPanel = new ChartPanel(chart, false); 
	            chartPanel.setBounds(50, 130, 700, 400);
	            panel.add(chartPanel);
	            System.out.println("嘿！画K线图啦");
	}

}
