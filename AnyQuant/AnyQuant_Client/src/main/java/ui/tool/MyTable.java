package ui.tool;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class MyTable extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	MyColor color = new MyColor();
	Vector<String> VColumns = new Vector<String>();
	Vector<String> vData = new Vector<String>();
	private DefaultTableModel ListModel;
	private JTable Table;
	public MyTable(int x,int y,int width,int height,Vector<String> vColumns) {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		//表头

		
		//数据
//		vData.addElement("ccc");
		//模型
		ListModel = new DefaultTableModel(vData, vColumns);
		
		//表格
		Table = new JTable(ListModel){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		Table.getTableHeader().setReorderingAllowed(false);
		//设置居中
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		Table.setDefaultRenderer(Object.class, render);
		Table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Table.getTableHeader().setLayout(null);
		Table.setShowGrid(false);
		scrollPane.getViewport().add(Table);
		Table.setFillsViewportHeight(true);

//		Table.setBackground(new Color(174,174,174));
		Table.setFont(new Font("微软雅黑",Font.PLAIN,15));
		Table.setForeground(Color.black);
		Table.getTableHeader().setFont(new Font("微软雅黑",Font.PLAIN,15));
//		Table.getTableHeader().setForeground(Color.white);
		Table.getTableHeader().setBackground(new Color(220,220,220));
//		Table.getTableHeader().set
		makeFace(Table);
//		System.out.println(Table.g);
//		Table.setBackground(color.LightGray());
//		Table.getTableHeader().setBackground(Color.gray);
		scrollPane.setBounds(0, 0, width, height);
		
		//
		setLayout(null);
		setBounds(x, y, width, height);
		add(scrollPane);
	}
	public void setFontSize(int x){
		Table.setFont(new Font("微软雅黑",Font.PLAIN,x));
	}
	public void addColumns(String s){
		VColumns.add(s);
	}
	public void setTitleColor(Color c){
		Table.getTableHeader().setBackground(c);
	}
	public void setContentColor(Color c){
		Table.setBackground(c);
	}
	public void setFontColor(Color c){
		Table.getTableHeader().setForeground(c);
	}
	//new methods
	public void addRow(Vector<String> v){
		ListModel.addRow(v);
		
	}
	public void removeRow(int x){
		ListModel.removeRow(x);
	}
	
	public void getValueAt(int row, int column){
		ListModel.getValueAt(row, column);
	}
	public int  getRowCount(){
		return ListModel.getRowCount();
	}
	public int  getSelectRow(){
		return Table.getSelectedRow();
	}
	public void setRowHeight(int x){
		Table.setRowHeight(x);
//		Table.getDefaultRenderer(columnClass)
	}
	public void removeAllItem(){
		if (ListModel.getRowCount() > 0) {
			 for (int i = ListModel.getRowCount() - 1; i > -1; i--) {
			  ListModel.removeRow(i);
//			  System.out.println(i);
			 }
			}
	}
	public ListSelectionModel getSelectedmodel(){
		
		return Table.getSelectionModel();
	}
	public String getValue(int row, int column){
		return String.valueOf(Table.getValueAt(row, column));
	}
	public JTable getTable(){
//		ListModel.get
		return Table;
	}
	
	private void makeFace(JTable table) {
		   try {
		    DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
		     public Component getTableCellRendererComponent(JTable table,
		       Object value, boolean isSelected, boolean hasFocus,
		       int row, int column) {
		      if (row % 2 == 0)
		       setBackground(Color.white); // ���������е�ɫ
		      else if (row % 2 == 1)
		       setBackground(new Color(240, 240, 240)); // ����ż���е�ɫ
		      return super.getTableCellRendererComponent(table, value,
		        isSelected, hasFocus, row, column);
		     }
		    };
		    for (int i = 0; i < table.getColumnCount(); i++) {
		     table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
		    }
		   } catch (Exception ex) {
		    ex.printStackTrace();
		   }
		}
	/**
	 * 设置列宽
	 */
	public void setColumn(int[] nums){
		for(int i=0;i<nums.length;i++){
			Table.getColumnModel().getColumn(i).setPreferredWidth(nums[i]);
		}
	}
	/**
	 * 设置行宽
	 */
	public void setRow(int num){
		Table.setRowHeight(num);
	}
	public TableColumnModel getColumnModel(){
		return Table.getColumnModel();
	}
}
