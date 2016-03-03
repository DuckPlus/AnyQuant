package ui.guideui;

import java.awt.Graphics;

import org.dom4j.Element;

import ui.tool.MyPanel;

/**
 * 起始panel
 * @author czq
 * @date 2016年3月2日
 */
@SuppressWarnings("serial")
public class GuidePanel extends MyPanel{

	public GuidePanel(Element config) {
		super(config);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		g.drawString("this is guide panel", 200, 200);
	}
	
	@Override
	protected void initWhitePanels(Element e) {
		// TODO Auto-generated method stub
		
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
