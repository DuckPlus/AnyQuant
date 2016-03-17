/**
 *@author dsn
 *@version 2016年3月8日    上午8:46:10
 */

package ui.blockui;

import java.awt.Graphics;

import org.dom4j.Element;

import ui.config.GraphicsUtils;
import ui.tool.MyPanel;

public class BlockPanel extends MyPanel{

	public BlockPanel(Element config) {
		super(config);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(GraphicsUtils.getImage("bg//bg_s"),0,0,null);
		g.drawString("Im BlockPanel",200,200);
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
	protected void initOtherComponent(Element e) {
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

