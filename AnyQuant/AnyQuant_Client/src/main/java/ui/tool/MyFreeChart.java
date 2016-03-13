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

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.dom4j.Element;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.xy.XYDataset;

import ui.config.GraphicsUtils;
import util.MyTime;
import vo.DealVO;
import vo.OHLC_VO;
import enumeration.MyDate;
public class MyFreeChart {
	//设置日期格式
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 画k线图  和成交额的图~
	 * @param datas
	 * @param panel
	 */
	public static void kline_deal(List<OHLC_VO> datas_k,List<DealVO> datas_deal,Element config,JPanel panel){
		XYPlot plot_k,plot_deal;//画图区域对象
		MyDate startDate,endDate_plus1;
		MyDate date_temp;
		//设置K线图的画图器，必须申明为final，后面要在匿名内部类里面用到
		final CandlestickRenderer candlestickRender=new CandlestickRenderer();
		//柱状图的画图器，画成交额的
		XYBarRenderer xyBarRender;
		DateAxis xAxis=new DateAxis();//设置x轴，也就是时间轴 
		 NumberAxis yAxis_k,yAxis_deal;//设定y轴，就是数字轴 
        double maxValue_k = Double.MIN_VALUE;//设置K线数据当中的最大值 
        double minValue_k = Double.MAX_VALUE;//设置K线数据当中的最小值 
		double maxValue_deal = Double.MIN_VALUE;// 设置成交量的最大值
		double minValue_deal = Double.MAX_VALUE;// 设置成交量的最低值
		/*
		 * 开高低收数据序列，股票K线图的四个数据，依次是开，高，低，收
		 */
		OHLCSeries series_k = new OHLCSeries("");
		/*
		 * 对应时间成交量数据
		 */
        TimeSeries series_deal=new TimeSeries("");
        
        /*
         * 添加k线图的数据
         * 
         */
        //得到第一个数据的日期（x轴坐标起点）
        date_temp=datas_k.get(0).date;
		startDate=date_temp;
		//添加k线图数据
		for (OHLC_VO data : datas_k) {
			date_temp = data.date;
			series_k.add(new Day(date_temp.getDay(), date_temp.getMonth(), date_temp.getYear()),
					data.open, data.high, data.low, data.close);
		}
		//得到最后一个数据的日期，再加1天（x轴坐标终点）
		endDate_plus1=MyTime.getAnotherDay(date_temp, 1);
        /*
         * 添加成交额的图的数据
         */
        //添加数据
        for (DealVO data : datas_deal) {
			date_temp = data.date;
			series_deal.add(
					new Day(date_temp.getDay(), date_temp.getMonth(), date_temp.getYear()),
					data.dealAmount);
 		}
        
        
		// 保留K线数据的数据集，必须申明为final，后面要在匿名内部类里面用到
		final MyOHLCSeriesCollection seriesCollection_k = new MyOHLCSeriesCollection();
		seriesCollection_k.addSeries(series_k);
		// 保留成交额数据的数据集
		MyTimeSeriesCollection seriesCollection_deal = new MyTimeSeriesCollection();// 保留成交量数据的集合
		seriesCollection_deal.addSeries(series_deal); 
        /*
		 * 获取K线数据的最高值和最低值
		 */
		// 一共有多少个序列，目前为一个
        int seriesCount_k = seriesCollection_k.getSeriesCount();
        for (int i = 0; i < seriesCount_k; i++) { 
        	//每一个序列有多少个数据项 
            int itemCount = seriesCollection_k.getItemCount(i);
            for (int j = 0; j < itemCount; j++) { 
                if (maxValue_k < seriesCollection_k.getHighValue(i, j)) {//取第i个序列中的第j个数据项的最大值
                    maxValue_k = seriesCollection_k.getHighValue(i, j); 
                } 
                if (minValue_k > seriesCollection_k.getLowValue(i, j)) {//取第i个序列中的第j个数据项的最小值
                    minValue_k = seriesCollection_k.getLowValue(i, j); 
                } 
            } 
        } 
        /*
         * 获取最高值和最低值 
         */
        //一共有多少个序列，目前为一个
        int seriesCount_deal = seriesCollection_deal.getSeriesCount();
        for (int i = 0; i < seriesCount_deal; i++) { 
        	//每一个序列有多少个数据项 
            int itemCount = seriesCollection_deal.getItemCount(i);
            for (int j = 0; j < itemCount; j++) { 
                if (maxValue_deal < seriesCollection_deal.getYValue(i,j)) {//取第i个序列中的第j个数据项的值
                    maxValue_deal = seriesCollection_deal.getYValue(i,j); 
                } 
                if (minValue_deal > seriesCollection_deal.getYValue(i, j)) {//取第i个序列中的第j个数据项的值
                    minValue_deal = seriesCollection_deal.getYValue(i, j); 
                } 
            } 
        } 
        /*
         * 创建k线图的画笔
         */
      //设置是否使用自定义的边框线，程序自带的边框线的颜色不符合中国股票市场的习惯
        candlestickRender.setUseOutlinePaint(true); 
      //设置如何对K线图的宽度进行设定
        candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_SMALLEST);
      //设置各个K线图之间的间隔 
//        candlestickRender.setAutoWidthGap(0.001);
      //设置股票上涨的K线图颜色 
        candlestickRender.setUpPaint(new Color(238,44,44));
      //设置股票下跌的K线图颜色 
        candlestickRender.setDownPaint(new Color(50,205,50));
        candlestickRender.setToolTipGenerator(new Tooltip_k());
        
		/*
		 * 创建成交额图的画笔
		 */
		xyBarRender = new XYBarRenderer() {
			private static final long serialVersionUID = 1L;// 为了避免出现警告消息，特设定此值

			public Paint getItemPaint(int i, int j) {// 匿名内部类用来处理当日的成交量柱形图的颜色与K线图的颜色保持一致
				if (seriesCollection_k.getCloseValue(i, j) > seriesCollection_k
						.getOpenValue(i, j)) {// 收盘价高于开盘价，股票上涨，选用股票上涨的颜色
					return candlestickRender.getUpPaint();
				} else {
					return candlestickRender.getDownPaint();
				}
			}
		};
		xyBarRender.setMargin(0.1);// 设置柱形图之间的间隔
		xyBarRender.setToolTipGenerator(new Tooltip_deal());
		/*
		 * 生成x轴,y轴
		 */
        xAxis=createX_Axis(startDate, endDate_plus1);
        yAxis_k=createY_Axis(minValue_k,maxValue_k,10);
		yAxis_deal = createY_Axis(minValue_deal, maxValue_deal, 4);
		// 设置k线图画图区域对象
		plot_k = new XYPlot(seriesCollection_k, xAxis, yAxis_k, candlestickRender);
		// 建立成交额图画图区域对象，x轴设为了null值，因为要与第一个画图区域对象共享x轴
		plot_deal = new XYPlot(seriesCollection_deal, null, yAxis_deal,xyBarRender);
		
		// 建立一个恰当的联合图形区域对象，以x轴为共享轴
		CombinedDomainXYPlot combineddomainxyplot = new CombinedDomainXYPlot(xAxis);
		// 添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域2/3
		combineddomainxyplot.add(plot_k, 2);
		// 添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域1/3
		combineddomainxyplot.add(plot_deal, 1);
		// 设置两个图形区域对象之间的间隔空间
		combineddomainxyplot.setGap(10);

		JFreeChart chart = new JFreeChart("", GraphicsUtils.getFont(null),
				combineddomainxyplot, false);
		ChartPanel chartPanel = new ChartPanel(chart, false);
		chartPanel.setBounds(Integer.parseInt(config.attributeValue("x")),
				Integer.parseInt(config.attributeValue("y")),
				Integer.parseInt(config.attributeValue("width")),
				Integer.parseInt(config.attributeValue("height")));
		panel.add(chartPanel);
		panel.repaint();
		System.out.println("嘿！画K线图啦"+panel.getClass());
	}
	/**
	 * 画Y轴的~
	 * @param minValue_k
	 * @param maxValue_k
	 * @return
	 */
	private static NumberAxis createY_Axis(double minValue_k, double maxValue_k,double density) {
		NumberAxis yAxis=new NumberAxis();
		 //不使用自动设定范围 
        yAxis.setAutoRange(false);
      //设定y轴值的范围，比最低值要低一些，比最大值要大一些，这样图形看起来会美观些
        yAxis.setRange(minValue_k*0.9, maxValue_k*1.1);
      //设置刻度显示的密度
        yAxis.setTickUnit(new NumberTickUnit((maxValue_k*1.1-minValue_k*0.9)/density));
		return yAxis;
	}
	/**
	 * 画x轴的~
	 * @param start
	 * @param end
	 * @return
	 */
	private static DateAxis createX_Axis(MyDate start,MyDate end){
		DateAxis xAxis=new DateAxis();
		//TODO
		//设置不采用自动设置时间范围 
        xAxis.setAutoRange(false);
        System.out.println("HHHHHH----"+start.DateToString()+"~~"+end.DateToString()+"--HHHHHHH");
        try{ 
        	//设置时间范围
            xAxis.setRange(dateFormat.parse(start.DateToString()),
            		dateFormat.parse(end.DateToString()));
        }catch(Exception e){ 
            e.printStackTrace(); 
        } 
        /*
         * 设置时间线显示的规则，
         * 用这个方法就摒除掉了周六和周日这些没有交易的日期，使图形看上去连续
         * ！这句不好用，，显示出来有点错位！
         */
//        xAxis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());
      //设置不采用自动选择刻度值 
        xAxis.setAutoTickUnitSelection(false);
      //设置标记的位置 
        xAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
      //设置标准的时间刻度单位
        xAxis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());
      //设置时间刻度的间隔，一般以周为单位
        xAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,7));
        //设置显示时间的格式
        xAxis.setDateFormatOverride(dateFormat);
		return xAxis;
	}

}
class Tooltip_k implements XYToolTipGenerator {  

	@Override
	public String generateToolTip(XYDataset data, int i, int j) {
		MyOHLCSeriesCollection s=(MyOHLCSeriesCollection)data;
		double open,high,low,close;
		MyDate date;
		open=s.getOpenValue(i, j);
		high=s.getHighValue(i, j);
		low=s.getLowValue(i, j);
		close=s.getCloseValue(i, j);
		date=s.getDate(i, j);
		
		JLabel economy = new JLabel("<html>Economy<br>Regularity</html>");
		return "<html>"+date.DateToString()+"<br>开盘："+open+"<br>最高："+high
				+"<br>最低："+low+"<br>收盘："+close+"</html>";
	}  
} 
class Tooltip_deal implements XYToolTipGenerator {  

	@Override
	public String generateToolTip(XYDataset data, int i, int j) {
		MyTimeSeriesCollection s=(MyTimeSeriesCollection)data;
		double num=s.getYValue(i, j);
		MyDate date=s.getDate(i, j);
		return "<html>"+date.DateToString()+"<br>成交额:"+num+"</html>";
	}  
} 
