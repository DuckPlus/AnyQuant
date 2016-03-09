package org.AnyQuant_Client;

import java.awt.Font;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

public class AppTest extends ApplicationFrame
{
    public AppTest(String title)
    {
        super(title);
        this.setContentPane(createPanel()); //构造函数中自动创建Java的panel面板
    }
    
    public static CategoryDataset createDataset() //创建柱状图数据集
    {
        DefaultCategoryDataset dataset=new DefaultCategoryDataset();
        dataset.setValue(10,"a","管理人员");
        dataset.setValue(20,"b","市场人员");
        dataset.setValue(40,"c","开发人员");
        dataset.setValue(15,"d","其他人员");
        return dataset;
    }
    
    public static JFreeChart createChart(CategoryDataset dataset) //用数据集创建一个图表
    {
        JFreeChart chart=ChartFactory.createBarChart("hi", "人员分布", 
                "人员数量", dataset, PlotOrientation.VERTICAL, true, true, false); //创建一个JFreeChart
        chart.setTitle(new TextTitle("某公司组织结构图",new Font("宋体",Font.BOLD+Font.ITALIC,20)));//可以重新设置标题，替换“hi”标题
        CategoryPlot plot=(CategoryPlot)chart.getPlot();//获得图标中间部分，即plot
        CategoryAxis categoryAxis=plot.getDomainAxis();//获得横坐标
        categoryAxis.setLabelFont(new Font("微软雅黑",Font.BOLD,12));//设置横坐标字体
        return chart;
    }
    
    public static JPanel createPanel()
    {
        JFreeChart chart =createChart(createDataset());
        return new ChartPanel(chart); //将chart对象放入Panel面板中去，ChartPanel类已继承Jpanel
    }
    
    public static void main(String[] args)
    {
        AppTest chart=new AppTest("某公司组织结构图");
        chart.pack();//以合适的大小显示
        chart.setVisible(true);
        
    }
}
//
//
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartFrame;
//import org.jfree.chart.JFreeChart;
//import org.jfree.data.general.DefaultPieDataset;
//
//public class AppTest
//{
//    public static void main(String[] args)
//    {
//        DefaultPieDataset dpd=new DefaultPieDataset(); //建立一个默认的饼图
//        dpd.setValue("管理人员", 25);  //输入数据
//        dpd.setValue("市场人员", 25);
//        dpd.setValue("开发人员", 45);
//        dpd.setValue("其他人员", 10);
//        
//        JFreeChart chart=ChartFactory.createPieChart("某公司人员组织数据图",dpd,true,true,false); 
//        //可以查具体的API文档,第一个参数是标题，第二个参数是一个数据集，第三个参数表示是否显示Legend，第四个参数表示是否显示提示，第五个参数表示图中是否存在URL
//        
//        ChartFrame chartFrame=new ChartFrame("某公司人员组织数据图",chart); 
//        //chart要放在Java容器组件中，ChartFrame继承自java的Jframe类。该第一个参数的数据是放在窗口左上角的，不是正中间的标题。
//        chartFrame.pack(); //以合适的大小展现图形
//        chartFrame.setVisible(true);//图形是否可见
//        
//    }
//}
