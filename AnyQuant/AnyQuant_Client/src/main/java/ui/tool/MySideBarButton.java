package ui.tool;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.dom4j.Element;

import ui.config.GraphicsUtils;
 /** 
 * 左边条Button
 * @author czq 
 * @version 2015年11月28日 下午3:27:24 
 */
@SuppressWarnings("serial")
public class MySideBarButton extends JLabel{
	
	private  Icon nowPanel=GraphicsUtils.getIcon("element//stockList_now");
	private  Icon normal= GraphicsUtils.getIcon("element//stockList_normal");
	private  Icon entered = GraphicsUtils.getIcon("element//stockList_entered");
	private  Icon clicked= GraphicsUtils.getIcon("element//stockList_clicked");
	private boolean isClicked = false;
	public MySideBarButton(Element e) {
		
		if (e.attributeValue("normal") != null) {
			normal = GraphicsUtils.getIcon(e.attributeValue("normal"));
		}
		if (e.attributeValue("now") != null) {
			nowPanel = GraphicsUtils.getIcon(e.attributeValue("now"));
		}
		if (e.attributeValue("clicked") != null) {
			clicked = GraphicsUtils.getIcon(e.attributeValue("clicked"));
		}
		if (e.attributeValue("entered") != null) {
			entered = GraphicsUtils.getIcon(e.attributeValue("entered"));
		}
		this.setIcon(normal);
		try {
		this.setBounds(Integer.parseInt(e.attributeValue("x")),
								Integer.parseInt(e.attributeValue("y")),
								Integer.parseInt(e.attributeValue("width")), Integer.parseInt(e.attributeValue("height")));
		}
		catch(Exception nullex){
			this.setBounds(Integer.parseInt(e.attributeValue("x")),
					Integer.parseInt(e.attributeValue("y")),
					250 ,40);
		}
		this.setFont(GraphicsUtils.getFont(null));
		this.setForeground(Color.WHITE);
		this.setText(e.attributeValue("text"));
		this.setVerticalTextPosition(SwingConstants.CENTER);
		this.setHorizontalTextPosition(SwingConstants.CENTER);
		this.repaint();
		this.setVisible(true);
	}
	
	public void setMyIcon(ButtonState state) {
		if(state == null){
			this.setIcon(nowPanel);
			isClicked = true;
			return;
		}
		
		isClicked = false;
		switch (state) {
		case NORMAL:
			this.setIcon(normal);
			break;
		case MOUSE_ENTERED:
			this.setIcon(entered);
			break;
		case MOUSE_CLICKED:
			this.setIcon(clicked);
			break;
		default:
			break;
		}

	}
	
//	public void setClicked(boolean isClicked){
//		this.isClicked = isClicked;
//	}
	public boolean getClicked(){
		return isClicked;
	}
}
