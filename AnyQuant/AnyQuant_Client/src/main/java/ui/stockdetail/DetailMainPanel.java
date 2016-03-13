package ui.stockdetail;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.dom4j.Element;

import ui.config.CompomentType;
import ui.config.GraphicsUtils;
import ui.tool.ButtonState;
import ui.tool.MyDatePicker;
import ui.tool.MyFreeChart;
import ui.tool.MyLabel;
import ui.tool.MyPanel;
import ui.tool.MyPictureButton;
import ui.tool.MyTable;
import ui.tool.PanelController;
import ui.tool.TipsDialog;
import util.MyTime;
import vo.StockVO;
import blservice.StockBLService;
import enumeration.MyDate;

/**
 * 股票详细界面
 * @author dsn14
 * @date 2016年3月2日
 */
@SuppressWarnings("serial")
public class DetailMainPanel extends MyPanel{

	public DetailMainPanel(Element config,PanelController controller) {
		super(config);
		this.config=config;
		ctr_panel=controller;
		ctr_bl=MockStockBLImpl.getAPIBLService();
		initComponent(config);
		
	}
	/**
	 * 设置数据
	 */
	public void setData(String stockCode,String stock_name){
		refreshStockInfo(stockCode, stock_name);
	}
	private void initComponent(Element config) {
		initPanel(config.element("panel"));
		initButtons(config.element(CompomentType.BUTTONS.name()));
		initDatePicker(config.element("DatePicker"));
		initTable(config.element("Table"));
		initLabels(config.element(CompomentType.LABELS.name()));
		addListener();
		addComponent();
	}

	
	private void initPanel(Element e) {
		tabPanel=new JTabbedPane();
		tabPanel.setBounds(35, 130, 720, 500);
		Element cfg=e.element("tabPanel");
		day_k_panel=new JPanel();
		week_k_panel=new JPanel();
		month_k_panel=new JPanel();
		time_sharing_panel=new JPanel();	
		int x=Integer.parseInt(cfg.attributeValue("x"));
		int y=Integer.parseInt(cfg.attributeValue("y"));
		int w=Integer.parseInt(cfg.attributeValue("width"));
		int h=Integer.parseInt(cfg.attributeValue("height"));
		day_k_panel.setBounds(x,y,w,h);
		week_k_panel.setBounds(x,y,w,h);
		month_k_panel.setBounds(x,y,w,h);
		time_sharing_panel.setBounds(x, y, w, h);
		tabPanel.addTab("日K", day_k_panel);
		tabPanel.addTab("周K", week_k_panel);
		tabPanel.addTab("月K", month_k_panel);
		tabPanel.addTab("分时图",time_sharing_panel);
		tabPanel.setDisplayedMnemonicIndexAt(0, 0);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(GraphicsUtils.getImage("bg//bg_s"),0,0,null);
		
	}
	@Override
	protected void initButtons(Element e) {
		search_btn=new MyPictureButton(e.element("search"));
		back_btn=new MyPictureButton(e.element("back"));
				
	}
	@Override
	protected void initTextFields(Element e) {
	}
	@Override
	protected void initLabels(Element e) {
		line_label=new MyLabel(e.element("line"),"---");
		stockCode_label=new MyLabel(e.element("stockCode"),stockCode);
		stockName_label=new MyLabel(e.element("stockName"),stockName);
		System.out.println("Code"+stockCode);
		 date_label=new MyLabel(e.element("date_label"), "选择日期");
		 stockPriceNow_label=new MyLabel(e.element("stockPriceNow"),stockPriceNow+"");
		 changeRate_label=new MyLabel(e.element("changeRate"),changeRate+"%");
		 historyData_label=new MyLabel(e.element("historyData_label"),"历史交易");
		 todayData_label=new MyLabel(e.element("todayData_label"),"今日行情");
		 todayOpen_label=new MyLabel(e.element("todayOpen_label"),"今开");
		 yestodayClose_label=new MyLabel(e.element("yestodayClose_label"),"昨收");
		 highest_label=new MyLabel(e.element("highest_label"),"最高");
		 lowest_label=new MyLabel(e.element("lowest_label"),"最低");
		 deal_label=new MyLabel(e.element("deal_label"),"成交量");
		 
		 todayOpen=new MyLabel(e.element("todayOpen"),todayOpen_num+"");
		 yestodayClose=new MyLabel(e.element("yestodayClose"),yestodayClose_num+"");
		 highest=new MyLabel(e.element("highest"),highest_num+"");
		 lowest=new MyLabel(e.element("lowest"),lowest_num+"");
		 dealAmount=new MyLabel(e.element("deal"),dealAmount_num+"");
		 changeColor();
	}
	private void changeColor() {
		if(changeRate<=0){
			 stockPriceNow_label.setForeground(Color.GREEN);
			 changeRate_label.setForeground(Color.GREEN);
		 }else{
			 stockPriceNow_label.setForeground((new Color(255,0,0)));
			 changeRate_label.setForeground((new Color(255,0,0)));
		 }
	}
	protected void initDatePicker(Element e) {
		start_datePicker=new MyDatePicker(e.element("start"));
		end_datePicker=new MyDatePicker(e.element("end"));
		endDate=MyTime.getToDay();
		startDate=MyTime.getAnotherDay(endDate, -30);
		
	}
	protected void initTable(Element e) {
		Vector<String> vhead = new Vector<String>();
		vhead.add("日期");
		vhead.add("开盘");
		vhead.add("收盘");
		vhead.add("最高");
		vhead.add("最低");
		vhead.add("成交量");
		vhead.add("换手率");
		vhead.add("振幅");
		vhead.add("涨跌幅");
		table= new MyTable(Integer.valueOf(e.attributeValue("x")), 
				Integer.valueOf(e.attributeValue("y")), 
				Integer.valueOf(e.attributeValue("width")), 
				Integer.valueOf(e.attributeValue("height")), vhead);
		table.setColumn(new int[]{100,45,45,45,45,70,50,50,50});
		
	}
	/**
	 * 刷新表格信息
	 */
	private void refreshPictrue() {
		MyFreeChart.kline_deal(
				ctr_bl.getDayOHLC_Data(stockCode, startDate, endDate),
				ctr_bl.getDayDealVOs(stockCode, startDate, endDate), 
				config.element("panel").element("pic"),
				day_k_panel);
		MyFreeChart.kline_deal(
				ctr_bl.getWeekOHLC_Data(stockCode, startDate, endDate),
				ctr_bl.getWeekDealVOs(stockCode, startDate, endDate), 
				config.element("panel").element("pic"),
				week_k_panel);
		MyFreeChart.kline_deal(
				ctr_bl.getMonthOHLC_Data(stockCode, startDate, endDate),
				ctr_bl.getMonthDealVOs(stockCode, startDate, endDate), 
				config.element("panel").element("pic"),
				month_k_panel);
		
		int i=0;
		while(itr.hasNext()){
			StockVO vo=itr.next();
			Vector<String>vd = new Vector<String>();
			vd.add(vo.date);
			vd.add(vo.open+"");
			vd.add(vo.close+"");
			vd.add(vo.high+"");
			vd.add(vo.low+"");
			vd.add(vo.volume+"");
			vd.add(vo.turnover+"");
			vd.add(String.format("%.2f",vo.amplitude*100)+"%");
			vd.add(String.format("%.2f",vo.changeRate*100)+"%");
			table.addRow(vd);
			String changeRateStr=table.getValue(i, 8);
			System.out.println(i+"  "+changeRateStr);
			if(Double.parseDouble(changeRateStr.substring(0, changeRateStr.length()-1))<=0){
				table.setRowColor(i,new Color(50,205,50));
			}else {
				table.setRowColor(i,new Color(238,44,44));
			}
			i++;
		}
	}

	@Override
	protected void addComponent() {
		this.add(tabPanel);
		this.add(start_datePicker);
		this.add(end_datePicker);
//		this.add(table);
		this.add(search_btn);
		this.add(back_btn);
		this.add(stockCode_label);
		this.add(stockName_label);
		this.add(date_label);
		this.add(stockPriceNow_label);

		this.add(changeRate_label);
		this.add(historyData_label);
		this.add(todayData_label);
		this.add(todayOpen_label);
		this.add(yestodayClose_label);
		this.add(highest_label);
		this.add(lowest_label);
		this.add(deal_label);
		this.add(line_label);

		this.add(todayOpen);
		this.add(yestodayClose);
		this.add(highest);
		this.add(lowest);
		this.add(dealAmount);
	}

	@Override
	protected void addListener() {
//		tabPanel.addChangeListener(new ChangeListener() {
//			public void stateChanged(ChangeEvent e) {
//				JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
//				int selectedIndex = tabbedPane.getSelectedIndex();
//				switch (selectedIndex) {
//				case 0:
//					System.out.println("画日K");
//					break;
//				case 1:
//					System.out.println("画周k");
//					break;
//				case 2:
//					System.out.println("画月K");
//					break;
//				case 3:
//					System.out.println("画分时图");	
//					break;
//				}
//			}
//		});
		search_btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				search_btn.setMyIcon(ButtonState.MOUSE_ENTERED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				search_btn.setMyIcon(ButtonState.NORMAL);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				search_btn.setMyIcon(ButtonState.MOUSE_CLICKED);
				System.out.println("点击查询");
				System.out.println(start_datePicker.getDate());
					startDate=start_datePicker.getDate();
					endDate=end_datePicker.getDate();
					System.out.println(startDate.DateToString()+" -- "+endDate.DateToString());
					if(MyTime.ifEarlier(startDate, endDate)
							||MyTime.ifSame(startDate, endDate)){
						itr=ctr_bl.getStocksByTime(stockCode, startDate,endDate);
						System.out.println("MouseListener "+itr.hasNext());
						refreshPictrue();
					}else {
						feedBack("起止日期填反");
					}
			}
		});
		back_btn.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				back_btn.setMyIcon(ButtonState.NORMAL);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				back_btn.setMyIcon(ButtonState.MOUSE_ENTERED);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				back_btn.setMyIcon(ButtonState.MOUSE_CLICKED);
				ctr_panel.getCardLayout().show(ctr_panel.getChangePanel(),"stockListPanel" );
			}
		});
	}
	/**
	 * 反馈提示信息
	 * @param message
	 */
	private void feedBack(String message) {
		new TipsDialog(message);
	}
	private void refreshStockInfo(String stockCode,String stockName){
		System.out.println("refreshStockInfo");
		this.stockCode = stockCode;
		this.stockName=stockName;
		itr=ctr_bl.getRecentStocks(stockCode);//今天是最后一个
		//刷新图表
		refreshPictrue();
		//TODO
		
		
		// label上的数据
		stockPriceNow = Double.parseDouble(table.getValue(
				table.getRowCount() - 1, 1));
		todayOpen_num = Double.parseDouble(table.getValue(
				table.getRowCount() - 1, 1));
		yestodayClose_num = Double.parseDouble(table.getValue(
				table.getRowCount() - 2, 2));
		highest_num = Double.parseDouble(table.getValue(
				table.getRowCount() - 1, 3));
		lowest_num = Double.parseDouble(table.getValue(table.getRowCount() - 1,
				4));
		dealAmount_num = Long.parseLong(table.getValue(
				table.getRowCount() - 1, 5));
		String temp=table.getValue(table.getRowCount() - 1,8);
		changeRate = Double.parseDouble(temp.substring(0, temp.length()-1))/100;
		changeRate_str=temp;
		refreshLabels();
   	this.tabPanel.repaint();
	}
	private void refreshLabels() {
		stockPriceNow_label.setText(stockPriceNow+"");
		changeRate_label.setText(changeRate_str);
		stockCode_label.setText(stockCode+"");
		stockName_label.setText(stockName+"");
		todayOpen.setText(todayOpen_num+"");
		yestodayClose.setText(yestodayClose_num+"");
		highest.setText(highest_num+"");
		lowest.setText(lowest_num+"");
		dealAmount.setText(dealAmount_num+"");
		changeColor();
	}
	private String stockCode="sh600050",stockName,changeRate_str;
	private MyDate startDate,endDate;
	private double changeRate,stockPriceNow,todayOpen_num,yestodayClose_num,highest_num,lowest_num,dealAmount_num;
	private MyPictureButton search_btn,back_btn;
	private MyLabel stockCode_label,stockName_label,date_label,stockPriceNow_label,changeRate_label,historyData_label,
	                todayData_label,todayOpen_label,yestodayClose_label,highest_label,lowest_label,
	                deal_label,line_label;
	private MyLabel todayOpen,yestodayClose,highest,lowest,dealAmount;
	private MyDatePicker start_datePicker,end_datePicker;
	private MyTable table;
	private JTabbedPane tabPanel;//TODO 为什么3个panel叠加，第三个显示不出？！
	private JPanel day_k_panel,week_k_panel,month_k_panel,
	        time_sharing_panel;
	private StockBLService ctr_bl;
	private Iterator<StockVO> itr;
	Element config;
	private PanelController ctr_panel;
	@Override
	protected void initOtherComponent(Element e) {
		// TODO Auto-generated method stub
		
	}
}
