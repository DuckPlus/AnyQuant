package ui.stockdetail;

import java.awt.Graphics;

import org.dom4j.Element;

import ui.tool.MyPanel;

/**
 * 股票详细界面
 * @author czq
 * @date 2016年3月2日
 */
@SuppressWarnings("serial")
public class DetailMainPanel extends MyPanel{

	public DetailMainPanel(Element config) {
		super(config);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawString("this is DetailMainPanel", 200, 200);
	}
	

	@Override
	protected void initButtons(Element e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initTextFields(Element e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initLabels(Element e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initOtherCompoment(Element e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addCompoment() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addListener() {
		// TODO Auto-generated method stub
		
	}

}
