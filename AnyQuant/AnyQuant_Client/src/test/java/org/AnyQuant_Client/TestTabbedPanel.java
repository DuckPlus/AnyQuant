/**
 *@author dsn
 *@version 2016年3月12日    上午12:11:14
 */
package org.AnyQuant_Client;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import org.dom4j.Element;

import ui.config.ParseXML;
import ui.stockdetail.DetailMainPanel;
import ui.tool.MyFreeChart;
import ui.tool.MyPanel;
  
/** 
 * <p>Title: 选项卡演示</p> 
 * <p>Description: 这里是一个选项卡演示，点击不同的卡片，显示的内容不同</p> 
 */  
  
public class TestTabbedPanel extends JPanel {  
	ParseXML xmlReader = new ParseXML("UIConfig.xml");
	Element e=xmlReader.getRoot().element("initialpanel").element("stockListPanel")
			.element("stockDetailPanel");
	JTabbedPane tabbedPane;
    public TestTabbedPanel() {  
        super(new GridLayout(1,1));  
        ImageIcon icon = createImageIcon("baound.jpg");  
        tabbedPane = new JTabbedPane();  
//        JButton btn=new JButton("change");
//        btn.setBounds(50, 50, 50, 50);
        
        MyPanel panel1 = makeTextPanel();
        panel1.setSize(100,100);
//        panel1.add(btn);
//        MyFreeChart.kline_deal(null,null, null,panel1);
        tabbedPane.addTab("One", panel1);  
        Component panel2 = makeTextPanel();  
        tabbedPane.addTab("Two", panel2);  
  
        Component panel3 = makeTextPanel();  
        tabbedPane.addTab("Three", panel3);  
  
        Component panel4 = makeTextPanel();  
        tabbedPane.addTab("Four", panel4);  
//  
//        ta2.addTab("One", icon, panel1, "第一个卡片提示信息！"); 
//        ta2.addTab("Two", icon, panel1, "第二个卡片提示信息！"); 
        // 将选项卡添加到panl中  
        init();
    }  
    private void init() {
        add(tabbedPane);  

	}
	@Override
    public void paintComponent(Graphics g) {
    	g.drawImage(new ImageIcon("image/bg/bg.png").getImage(), 0,0,null);
    }
  
    /** 
     * <br> 
     * 方法说明：添加信息到选项卡中 <br> 
     * 输入参数：String text 显示的信息内容 <br> 
     * 返回类型：Component 成员对象 
     */  
    protected MyPanel makeTextPanel() {  
        MyPanel panel = new DetailMainPanel(e, null);
        panel.setSize(100, 100);
        return panel;  
    }  
  
    /** 
     * <br> 
     * 方法说明：获得图片 <br> 
     * 输入参数：String path 图片的路径 <br> 
     * 返回类型：ImageIcon 图片对象 
     */  
    protected static ImageIcon createImageIcon(String path) {  
        // java.net.URL imgURL = TabbedPaneDemo.class.getResource(path);  
        if (path != null) {  
            return new ImageIcon(path);  
        } else {  
            System.out.println("Couldn't find file: " + path);  
            return null;  
        }  
    }  
  
    public static void main(String[] args) {  
        // 使用Swing窗体描述  
        // JFrame.setDefaultLookAndFeelDecorated(true);  
  
        try {  
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
        } catch (Exception e) {  
              
        }  
        // 创建窗体  
        JFrame frame = new JFrame("TabbedPaneDemo");  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setContentPane(new TestTabbedPanel());  
  
        // 显示窗体  
        frame.setSize(600, 400);  
        frame.setVisible(true);  
    }  
}  
