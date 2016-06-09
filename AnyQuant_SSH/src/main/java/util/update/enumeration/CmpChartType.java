package util.update.enumeration;
/**
 * 保存自选比较 里的图表类型
 * @author duanzhengmou
 *
 */
public enum CmpChartType {
	peChart,pbChart,turnoverVolChart;

	public String toFriendlyString(){
	    	switch (this) {
			case pbChart:
				return "市净率";
			case peChart:
				return "市盈率";
			case turnoverVolChart:
				return "成交量";
			default:
				return "未知因子";
			}
	}
}


