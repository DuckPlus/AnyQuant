package ui.listui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.dom4j.Element;

import blimpl.APIBlImpl;
import blservice.APIBlservice;
import enumeration.Stock_Attribute;
import ui.tool.MyPanel;
import ui.tool.MyPictureButton;
import ui.tool.MyTable;
import ui.tool.MyTextField;
import ui.tool.PanelController;
import vo.StockVO;

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
	
	JLabel sortVolumeBtn;
	
	
	APIBlservice apiBl;// = APIBlImpl.getAPIBLService();
	public StockListPanel(Element config,PanelController panelController) {
		super(config);
		this.panelController = panelController;
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
	}
	

	@Override
	protected void initButtons(Element e) {
		//search Btn
		searchBtn = new MyPictureButton(e.element("searchBtn"));
		searchBtn.setBounds(Integer.valueOf(e.element("searchBtn").attributeValue("x")), 
				Integer.valueOf(e.element("searchBtn").attributeValue("y")), 
				Integer.valueOf(e.element("searchBtn").attributeValue("width")), 
				Integer.valueOf(e.element("searchBtn").attributeValue("height")));
		
		sortVolumeBtn = new JLabel();
		sortVolumeBtn.setOpaque(true);
		sortVolumeBtn.setBounds(675, 0, 10, 25);
		sortVolumeBtn.setBackground(Color.red);
		
		
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
		vhead.add("变化率");
		
		stocklistTable= new MyTable(Integer.valueOf(e.attributeValue("x")), 
				Integer.valueOf(e.attributeValue("y")), 
				Integer.valueOf(e.attributeValue("width")), 
				Integer.valueOf(e.attributeValue("height")), vhead);
//		searchAllStock();
		
	}

	@Override
	protected void addComponent() {
		// TODO Auto-generated method stub
		this.add(searchBtn);
		this.add(stockCodeInput);
		this.add(stocklistTable);
		stocklistTable.getTable().getTableHeader().add(sortVolumeBtn);
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
			public void mousePressed(MouseEvent e) {
				int row = stocklistTable.getSelectRow();
				if(row!=-1){
				String stockCode = stocklistTable.getValue(row, 1);
				String stockName = stocklistTable.getValue(row, 0);
				panelController.getCardLayout().show(panelController.getChangePanel(),"stockDetailPanel" );
				
				}
				super.mousePressed(e);
			}
		});
		
		Document doc = stockCodeInput.getDocument();
		doc.addDocumentListener(this);
		
		sortVolumeBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//升序排列
				System.out.println(stocklistTable.getRowCount());
				sortStockTable(true, Stock_Attribute.volume);
				super.mousePressed(e);
			}
		});
	}
	private void initBl(){
		apiBl =APIBlImpl.getAPIBLService();
	}
	private void searchStock(String input){
		stocklistTable.removeAllItem();
		Iterator<StockVO> itr = apiBl.getStocksByStockCode(input);
		
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
	private void searchAllStock(){
		stocklistTable.removeAllItem();
		Iterator<StockVO> itr = apiBl.getAllStocks();
		
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
	private List<String>getCurrentStock(){
//		System.out.println("get in");
		List<String>result = new ArrayList<String>();
		int totalRow = stocklistTable.getRowCount();
//		System.out.println("TOTAL"+totalRow);
		for(int i=0;i<totalRow;i++){
//			System.out.println("i:"+i);
//			System.out.println("total:"+totalRow);
			String code = stocklistTable.getValue(i, 1);
			System.out.println("add:"+code);
			result.add(code);
		}
		return result;
	}
	
	private void sortStockTable(Boolean isUp, Stock_Attribute attr){
		
		List<String>CodeList = getCurrentStock();
		System.out.println(CodeList.size());
		Iterator<StockVO>itr = apiBl.getSortStocksInScope(isUp, attr, CodeList);
		stocklistTable.removeAllItem();
		while(itr.hasNext()){
			System.out.println("get a value!");
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
