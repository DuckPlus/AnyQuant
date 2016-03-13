/**
 *@author dsn
 *@version 2016年3月11日    上午1:08:04
 */

package ui.stockdetail;

import java.awt.Graphics;

import org.dom4j.Element;

import ui.tool.MyPanel;

public class Picture_panel extends MyPanel{


	public Picture_panel(Element config) {
		super(config);
		System.out.println("Picture_panel类  "+this.getWidth()+"x"+this.getHeight());
	}
	@Override
	public void paint(Graphics g) {
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

