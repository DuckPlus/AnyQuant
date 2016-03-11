package ui.listui;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.dom4j.Element;

import ui.config.GraphicsUtils;
import ui.stockdetail.DetailMainPanel;
import ui.tool.MyPanel;
import ui.tool.MyPictureButton;
import ui.tool.MyTable;
import ui.tool.MyTextField;
import ui.tool.PanelController;
import vo.StockVO;
import blimpl.StockBLImpl;
import blservice.StockBLService;
import enumeration.Stock_Attribute;

/**
 * 股票列表
 * @author dzm
 * @date 2016年3月4日
 */
@SuppressWarnings("serial")
public class StockListPanel extends MyPanel implements DocumentListener{
	MyTable stocklistTable; 
	MyTextField stockCodeInput;
	MyPictureButton searchBtn;
	PanelController panelController;
	DetailMainPanel StockDetailPanel;
//	JLabel sortVolumeBtn;
	
	
	StockBLService apiBl;// = APIBlImpl.getAPIBLService();
	public StockListPanel(Element config,PanelController panelController,DetailMainPanel StockDetailPanel) {
		super(config);
		this.panelController = panelController;
		this.StockDetailPanel = StockDetailPanel;
		initBl();
		initTable(config.element("stocklistTable"));
		initTextFields(config.element("stockCodeInput"));
		initButtons(config);
		addListener();
		addComponent();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString("this is StockListPanel", 200, 200);
		g.drawImage(GraphicsUtils.getImage("bg//bg_s"),0,0,null);
		
	}
	

	@Override
	protected void initButtons(Element e) {
		//search Btn
		searchBtn = new MyPictureButton(e.element("searchBtn"));
		searchBtn.setBounds(Integer.valueOf(e.element("searchBtn").attributeValue("x")), 
				Integer.valueOf(e.element("searchBtn").attributeValue("y")), 
				Integer.valueOf(e.element("searchBtn").attributeValue("width")), 
				Integer.valueOf(e.element("searchBtn").attributeValue("height")));
		
		
		
	}

	@Override
	protected void initTextFields(Element e) {
		stockCodeInput = new MyTextField();
		stockCodeInput.setBounds(Integer.valueOf(e.attributeValue("x")),
				Integer.valueOf(e.attributeValue("y")), 
				Integer.valueOf(e.attributeValue("width")), 
				Integer.valueOf(e.attributeValue("height")));
	}

	@Override
	protected void initLabels(Element e) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initTable(Element e) {
		Vector<String> vhead = new Vector<String>();
		vhead.add("股票名称");
		vhead.add("股票代码");
		vhead.add("开盘价");
		vhead.add("收盘价");
		vhead.add("最高价");
		vhead.add("最低价");
		vhead.add("换手率");
		vhead.add("成交量");
		vhead.add("振幅");
		vhead.add("涨跌幅");
		
		stocklistTable= new MyTable(Integer.valueOf(e.attributeValue("x")), 
				Integer.valueOf(e.attributeValue("y")), 
				Integer.valueOf(e.attributeValue("width")), 
				Integer.valueOf(e.attributeValue("height")), vhead);
//		stocklistTable.getTable().setAutoCreateRowSorter(true);
		Vector<Integer>vC = new Vector<Integer>();
		vC.add(2);
		vC.add(3);
		vC.add(4);
		vC.add(5);
		vC.add(6);
		vC.add(7);
		vC.add(8);
		vC.add(9);
		
		stocklistTable.sortColumnByNum(vC);
		searchAllStock();
		
	}

	@Override
	protected void addComponent() {
		// TODO Auto-generated method stub
		this.add(searchBtn);
		this.add(stockCodeInput);
		this.add(stocklistTable);
	}

	@Override
	protected void addListener() {
		searchBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String input = stockCodeInput.getText();
				if(!input.equals("")){
					searchStock(input);
				}else{
					searchAllStock();
				}
				super.mousePressed(e);
			}
		});
		
		stocklistTable.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2){
					int row = stocklistTable.getSelectRow();
					if(row!=-1){
						String stockCode = stocklistTable.getValue(row, 1);
						String stockName = stocklistTable.getValue(row, 0);
						StockDetailPanel.setData(stockCode, stockName);
						panelController.getCardLayout().show(panelController.getChangePanel(),"stockDetailPanel" );
					}
				}
				super.mouseClicked(e);
			}
		});
		Document doc = stockCodeInput.getDocument();
		doc.addDocumentListener(this);
		
	}
	private void initBl(){
		apiBl =StockBLImpl.getAPIBLService();
//		apiBl = APIImplCache.getAPIBLService();
	}
	private void searchStock(String input){
//		stocklistTable.removeAllItem();
		Iterator<StockVO> itr = apiBl.getStocksByStockCode(input);
		showTableData(itr);
	}
	private void searchAllStock(){
//		stocklistTable.removeAllItem();
		Iterator<StockVO> itr = apiBl.getAllStocks();
		showTableData(itr);
	}
	private List<String>getCurrentStock(){
		List<String>result = new ArrayList<String>();
		int totalRow = stocklistTable.getRowCount();
		for(int i=0;i<totalRow;i++){
			String code = stocklistTable.getValue(i, 1);
			System.out.println("add:"+code);
			result.add(code);
		}
		return result;
	}
	
	private void sortStockTable(Boolean isUp, Stock_Attribute attr){
		
		List<String>CodeList = getCurrentStock();
		Iterator<StockVO>itr = apiBl.getSortStocksInScope(isUp, attr, CodeList);
		stocklistTable.removeAllItem();
		while(itr.hasNext()){
			StockVO temp = itr.next();
			Vector<String>vData = new Vector<String>();
			vData.add(temp.name);
			vData.add(temp.code);
			vData.add(String.valueOf(temp.open));
			vData.add(String.valueOf(temp.close));
			vData.add(String.valueOf(temp.high));
			vData.add(String.valueOf(temp.low));
			vData.add(String.valueOf(temp.turnover));
			vData.add(String.valueOf(temp.volume));
			vData.add(String.valueOf(temp.amplitude));
			vData.add(String.valueOf(temp.changeRate));
			stocklistTable.addRow(vData);
		}
	}
	
	private void showTableData(Iterator<StockVO>itr){
		stocklistTable.removeAllItem();
		while(itr.hasNext()){
			StockVO temp = itr.next();
			Vector<String>vData = new Vector<String>();
			vData.add(temp.name);
			vData.add(temp.code);
			vData.add(String.valueOf(temp.open));
			vData.add(String.valueOf(temp.close));
			vData.add(String.valueOf(temp.high));
			vData.add(String.valueOf(temp.low));
			vData.add(String.valueOf(temp.turnover));
			vData.add(String.valueOf(temp.volume));
			vData.add(String.valueOf(temp.amplitude));
			vData.add(String.valueOf(temp.changeRate));
			stocklistTable.addRow(vData);
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		Document doc = e.getDocument();
		try {
			System.out.println("change:"+doc.getText(0, doc.getLength()));
			String input = doc.getText(0, doc.getLength());
			if(!input.equals("")){
				searchStock(input);
			}else{
				searchAllStock();
			}
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		Document doc = e.getDocument();
		try {
			System.out.println("update:"+doc.getText(0, doc.getLength()));
			String input = doc.getText(0, doc.getLength());
			if(!input.equals("")){
				searchStock(input);
			}else{
				searchAllStock();
			}
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		Document doc = e.getDocument();
		try {
			System.out.println("remove:"+doc.getText(0, doc.getLength()));
			String input = doc.getText(0, doc.getLength());
			if(!input.equals("")){
				searchStock(input);
			}else{
				searchAllStock();
			}
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}
}
