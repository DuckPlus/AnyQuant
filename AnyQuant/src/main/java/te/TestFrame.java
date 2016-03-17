/**
 *@author dsn
 *@version 2016年3月11日    下午4:58:43
 */

package te;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

public class TestFrame extends JFrame{
	ChartPanel localChartPanel ;
	public TestFrame(JFreeChart chart) {
		localChartPanel = new ChartPanel(chart, false); 
		this.setContentPane(localChartPanel);
		this.setVisible(true);
		this.setBounds(50, 50, 800, 600);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

}

