package ui.tool;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class MyTableHandler {
	private static int  rowH = 30;
	private static int showH=300;
	private static int showW=400;
	private static Font font = new Font("微软雅黑", Font.PLAIN, 16);
	
	public static void decorateTableAndSpane(JTable table ,
			DefaultTableCellRenderer dtr,JScrollPane spane){
		initTable(table ,dtr);
		initSpane(spane,table);
	}
      
      public static void initTable(JTable table,DefaultTableCellRenderer dtr){
    	//深灰 （字体颜色）
      	Color  foreColor = new Color(0,0,0,200);
      	//浅蓝色0 ,191 ,255,80
      	final Color   bgColor1 = new Color(0 ,191 ,255,80);
      	//白色
      	final Color bgColor2 = new Color(255, 255 ,255,100);
  		table.setRowHeight(rowH);
  		table.setFont(font);
       	table.setForeground(foreColor);

  		//设置显示出来的表格大小
  		table.setPreferredScrollableViewportSize(new Dimension(showW, showH));
  		
  		for(int i=0;i<table.getColumnCount();i++){
  	        table.getColumnModel().getColumn(i).setPreferredWidth(100);
  	    }
  		
  		//table.setCellSelectionEnabled(false);
  		//每次可以选中一行
  		//table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  		//设置表格是否透明(false 为透明)
  		table.setOpaque(false);
  		//设置是否显示网格线
  	    table.setShowGrid(false);
  	    
  		//蓝色
  		final Color  headerColor = new Color(0 ,191 ,255,180);
  		JTableHeader header = table.getTableHeader();
  		//header.setPreferredSize(new Dimension(showW,rowH));
  		header.setOpaque(false);
  		
  		
  		dtr.setHorizontalAlignment(JLabel.CENTER);
  		for (int i = 0; i < table.getColumnCount(); i++) {
  			table.getColumnModel().getColumn(i).setCellRenderer(dtr);
  		}
  		header.setDefaultRenderer(dtr);

      }
      
      public static void initSpane(JScrollPane Spane ,JTable table){
    	//把与表格相同大小的Spane设为透明
    	  Spane.setOpaque(false);
    	  //把显示出来的部分设为透明,很关键。。
    	  Spane.getViewport().setOpaque(false);
    	  Spane.setViewportBorder(new EmptyBorder(0, 0, 0, 0));
    	  //把用于显示表头部分的Spane也设为透明
  	      Spane.setColumnHeaderView(table.getTableHeader());
    	  Spane.getColumnHeader().setOpaque(false);
    	  //去掉多出来的一点滑块
          Spane.setBorder(new EmptyBorder(0, 0, 0, 0));   
      }
}