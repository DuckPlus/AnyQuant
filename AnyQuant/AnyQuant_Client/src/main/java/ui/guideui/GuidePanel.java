package ui.guideui;

import java.awt.Color;
import java.awt.Graphics;

import org.dom4j.Element;

import ui.config.GraphicsUtils;
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
		setBackground(new Color(0,0,0,0));
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		g.drawImage(GraphicsUtils.getImage("bg//bg_s"),0,0,null);
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
	protected void initTable(Element e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addComponent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addListener() {
		// TODO Auto-generated method stub
		
	}

}
