package ui.tool;

import javax.swing.Icon;

import org.dom4j.Element;

import ui.config.GraphicsUtils;
 /** 
 * 
 * @author czq 
 * @version 2015年11月30日 下午8:17:44 
 */
@SuppressWarnings("serial")
public class MyPictureLabel extends MyLabel{
	
	private Icon normal;
	
	
	
	
	public MyPictureLabel(Element config) {
		
		
		super(config);
//		this.setForeground(Color.WHITE);
		//一个label－>一张图片的加载
		if(config.attributeValue("one")!= null){
			this.setIcon(GraphicsUtils.getIcon(config.attributeValue("one")));
		}
		
		
	}

}
