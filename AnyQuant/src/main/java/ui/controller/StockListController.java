/**
 * @status code reviewed
 * @handler dzm
 */
package ui.controller;

import java.util.Iterator;
import java.util.List;

import org.python.antlr.PythonParser.else_clause_return;

import blimpl.StockBLImpl;
import blservice.StockBLService;
import enumeration.MyDate;
import enumeration.PanelType;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import ui.GraphicsUtils;
import ui.helper.ColorHelper;
import vo.Stock;
import vo.StockVO;

/**
 *
 * @author:duanzhengmou
 * @date:2016年3月22日
 */
public class StockListController {
	@FXML
	TableColumn<Stock, String> code;
	@FXML
	TableColumn<Stock, String> name;
	@FXML
	TableColumn<Stock, Double> open ;
	@FXML
	TableColumn<Stock, Double> high ;
	@FXML
	TableColumn<Stock, Double> low ;
	@FXML
	TableColumn<Stock, Double> close ;
	@FXML
	TableColumn<Stock, Double> turnoverRate;
	@FXML
	TableColumn<Stock, Long> turnoverVol ;
	@FXML
	TableColumn<Stock, String> amplitude ;
	@FXML
	TableColumn<Stock, String> changeRate ;
	@FXML
	TableView<Stock> tableview ;
	@FXML
	TextField searchBar;

	private RightPaneController rightPaneController = RightPaneController.getRightPaneController();
	private StockDetailController stockDetailController;
	private StockBLService stockBl = StockBLImpl.getAPIBLService();
	private ObservableList<Stock> observableList= FXCollections.observableArrayList();;
	private BorderPane stockDetailPane;
	private InstanceController instanceController = InstanceController.getInstance();

	public StockListController() {
		//TODO controller here
	}

	@FXML
	private void initialize() {
		//新建对象后向实例管理类注册实例对象
		stockDetailPane = (BorderPane) GraphicsUtils.getParent("StockDetail");
		instanceController.registStockDetailPane(stockDetailPane);
		showStocklist();
	}

	/**
	 *获取并显示所有股票信息到表格中
	 *@see showStocklist
	 */
	@FXML
	public void showStocklist() {
		Iterator<StockVO> itr = stockBl.getAllStocks();
		showTableData(itr);
	}
	/**
	 * 读取迭代器内容并显示到表格中
	 * @param itr
	 */
	private void showTableData(Iterator<StockVO> itr) {
		tableview.getItems().removeAll(observableList);
		
		while (itr.hasNext()) {
			StockVO temp = itr.next();
			Stock dataProperty = new Stock(temp);
			observableList.add(dataProperty);
		}

		name.setCellValueFactory(cell -> cell.getValue().name);
		code.setCellValueFactory(cell -> cell.getValue().code);
		open.setCellValueFactory(cell -> cell.getValue().open.asObject());
		high.setCellValueFactory(cell -> cell.getValue().high.asObject());
		low.setCellValueFactory(cell -> cell.getValue().low.asObject());
		close.setCellValueFactory(cell -> cell.getValue().close.asObject());
		turnoverRate.setCellValueFactory(cell -> cell.getValue().turnoverRate.asObject());
		turnoverVol.setCellValueFactory(cell -> cell.getValue().turnoverVol.asObject());
		amplitude.setCellValueFactory(cell -> cell.getValue().getStringAmplitude());
		changeRate.setCellValueFactory(cell -> cell.getValue().getStringChangeRate());
		// change the color of each row
		ColorHelper.setColorForStock(observableList , tableview.getColumns());
		tableview.setItems(observableList);
	}

	/**
	 * not used now
	 */
	@FXML
	private void delAllData() {
		tableview.getItems().removeAll(observableList);
	}

	/**
	 * 处理选择某只股票并双击的事件
	 * @param event
	 */
	@FXML
	private void handleMouseClick(MouseEvent event) {
		if (event.getClickCount() == 2) {
			if (tableview.getSelectionModel().getSelectedIndex() == -1) {
				//do noting if not a doubleClicked event or select nothing
				return;
			}

			Stock selectedStock = tableview.getSelectionModel().getSelectedItem();
			// The stockDetailController is null at first, and it must generated
			// after the fxml has initialize
			// it, otherwise we will get a totally defferent object from the
			// fxml's
			if (stockDetailController == null) {
				stockDetailController = StockDetailController.getStockDetailController();
			}
			stockDetailController.setData(selectedStock, PanelType.STOCK_LIST);
			rightPaneController.showDetailPane(stockDetailPane);
		}
	}
	/**
	 * 处理输入框输入字符时的查找事件
	 */
	@FXML
	private void searchStocklist() {
		String stockCode = searchBar.getText();
		if (stockCode.equals("")) {
			showStocklist();
			return;
		}
		Iterator<StockVO> itr = stockBl.getStocksByStockCode(stockCode);
		showTableData(itr);
	}

}
