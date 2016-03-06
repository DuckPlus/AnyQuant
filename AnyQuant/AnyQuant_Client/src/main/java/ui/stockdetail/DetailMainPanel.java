package ui.stockdetail;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Vector;

import org.dom4j.Element;

import ui.config.CompomentType;
import ui.tool.MyLabel;
import ui.tool.MyPanel;
import ui.tool.MyPictureButton;
import ui.tool.MyTable;
import ui.tool.MyTextField;
import util.MyTime;
import vo.StockVO;
import blimpl.APIBlImpl;
import blservice.APIBlservice;
import enumeration.MyDate;

/**
 * 股票详细界面
 * @author dsn14
 * @date 2016年3月2日
 */
@SuppressWarnings("serial")
public class DetailMainPanel extends MyPanel{

	public DetailMainPanel(Element config) {
		super(config);
		ctr=MockAPIBlImpl.getAPIBLService();
		getStockInfo();
		initComponent(config);
		
	}
	public DetailMainPanel(Element config,String stockCode,String stockName) {
		super(config);
		this.stockCode=stockCode;
		this.stockName=stockName;
		ctr=APIBlImpl.getAPIBLService();
		getStockInfo();
		initComponent(config);
		
	}
	
	private void initComponent(Element config) {
		initButtons(config.element(CompomentType.BUTTONS.name()));
		initTextFields(config.element(CompomentType.TEXTFIELDS.name()));
		initOtherCompoment(config.element("Table"));
		//label必须在table后面初始化
		initLabels(config.element(CompomentType.LABELS.name()));
		addListener();
		addComponent();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	@Override
	protected void initButtons(Element e) {
		search_btn=new MyPictureButton(e.element("search"));
				
	}
	@Override
	protected void initTextFields(Element e) {
		 startDate_txt=new MyTextField(e.element("startDate"));
		 endDate_txt=new MyTextField(e.element("endDate"));
	}
	@Override
	protected void initLabels(Element e) {
		stockPriceNow = Double.parseDouble(table.getValue(table.getRowCount()-1, 1));
		todayOpen_num = Double.parseDouble(table.getValue(table.getRowCount()-1, 1));
		//昨收
		yestodayClose_num = Double.parseDouble(table.getValue(table.getRowCount()-2, 2));
		highest_num =Double.parseDouble(table.getValue(table.getRowCount()-1, 3));
		lowest_num = Double.parseDouble(table.getValue(table.getRowCount()-1, 4));
		dealAmount_num =Double.parseDouble(table.getValue(table.getRowCount()-1, 5));
		changeRate =Double.parseDouble(table.getValue(table.getRowCount()-1, 8));
				
		
		stockCode_label=new MyLabel(e.element("stockCode"),stockCode);
		stockName_label=new MyLabel(e.element("stockName"),stockName);
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
		 //TODO 单位转换
		 dealAmount=new MyLabel(e.element("deal"),dealAmount_num+"");
		 
		 if(changeRate<=0){
			 stockPriceNow_label.setForeground((new Color(0,139,0)));
			 changeRate_label.setForeground((new Color(0,139,0)));
		 }else{
			 stockPriceNow_label.setForeground((new Color(255,0,0)));
			 changeRate_label.setForeground((new Color(255,0,0)));
		 }
	}

	@Override
	protected void initOtherCompoment(Element e) {
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
		table.setColumn(new int[]{100,50,50,50,50,50,50,50,50});
		
		refreshTable();
		
	}
	/**
	 * 刷新表格信息
	 */
	private void refreshTable() {
		table.removeAllItem();
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
			vd.add(vo.amplitude+"");
			vd.add(vo.changeRate+"");
			table.addRow(vd);
//			if(vo.changeRate<=0){
//				table.setRowColor(i,Color.green);
//				System.out.println("第"+i+"行 用绿色");
//			}else {
//				table.setRowColor(i,Color.red);
//				System.out.println("第"+i+"行 用红色");
//			}
			i++;
			
		}
	}

	@Override
	protected void addComponent() {
		this.add(table);
		this.add(search_btn);
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

		this.add(startDate_txt);
		this.add(endDate_txt);
		this.add(todayOpen);
		this.add(yestodayClose);
		this.add(highest);
		this.add(lowest);
		this.add(dealAmount);
	}

	@Override
	protected void addListener() {
		search_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("点击查询");
				String[] startD=startDate_txt.getText().split("-");
				String[] endD=endDate_txt.getText().split("-");
				try{
					startDate=new MyDate(Integer.parseInt(startD[0]), 
							Integer.parseInt(startD[1]),
							Integer.parseInt(startD[2]));
					endDate=new MyDate(Integer.parseInt(endD[0]),
							Integer.parseInt(endD[1]),
					        Integer.parseInt(endD[2]));
					System.out.println(startDate.DateToString()+" -- "+endDate.DateToString());
					if(MyTime.ifEarlier(startDate, endDate)
							||MyTime.ifSame(startDate, endDate)){
						itr=ctr.getStocksByTime(stockCode, startDate,endDate);
						refreshTable();
					}else {
						feedBack("起止日期填反");
					}
				}catch(Exception exp){
					feedBack("输入格式不正确");
				}
				
				
			}

			
		});
	}
	/**
	 * 反馈提示信息
	 * @param message
	 */
	private void feedBack(String message) {
		//TODO
		System.out.println(message);
	}
	private void getStockInfo(){
		stockCode = "sh600050";
		itr=ctr.getRecentStocks(stockCode);//今天是最后一个
		//TODO code和name从上一界面获得
		
		stockName = "石化油服";
		
	}
	private String stockCode,stockName;
	private MyDate startDate,endDate;
	private double changeRate,stockPriceNow,todayOpen_num,yestodayClose_num,highest_num,lowest_num,dealAmount_num;
	private MyPictureButton search_btn;
	private MyLabel stockCode_label,stockName_label,date_label,stockPriceNow_label,changeRate_label,historyData_label,
	                todayData_label,todayOpen_label,yestodayClose_label,highest_label,lowest_label,
	                deal_label;
	private MyLabel todayOpen,yestodayClose,highest,lowest,dealAmount;
	private MyTextField startDate_txt,endDate_txt;
	private MyTable table;
	private APIBlservice ctr;
	private Iterator<StockVO> itr;
}
