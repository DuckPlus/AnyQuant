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
	TableColumn<Stock, String> code;// = new TableColumn<Stock, String>();
	@FXML
	TableColumn<Stock, String> name;// = new TableColumn<Stock, String>();
	@FXML
	TableColumn<Stock, Double> open ;//= new TableColumn<Stock, Double>();
	@FXML
	TableColumn<Stock, Double> high ;//= new TableColumn<Stock, Double>();
	@FXML
	TableColumn<Stock, Double> low ;//= new TableColumn<Stock, Double>();
	@FXML
	TableColumn<Stock, Double> close ;//= new TableColumn<Stock, Double>();
	@FXML
	TableColumn<Stock, Double> turnoverRate;// = new TableColumn<Stock, Double>();
	@FXML
	TableColumn<Stock, Long> turnoverVol ;//= new TableColumn<Stock, Long>();
	@FXML
	TableColumn<Stock, String> amplitude ;//= new TableColumn<Stock, String>();
	@FXML
	TableColumn<Stock, String> changeRate ;//= new TableColumn<Stock, String>();
	@FXML
	TableView<Stock> tableview ;//= new TableView<Stock>();
	@FXML
	TextField searchBar;

	RightPaneController rightPaneController = RightPaneController.getRightPaneController();
	StockDetailController stockDetailController;// =
												// StockDetailController.getStockDetailController();
	CandleStickController candleStickController;
	// get the logic service
	StockBLService stockBl = StockBLImpl.getAPIBLService();
	//
	ObservableList<Stock> observableList;

	BorderPane stockDetailPane;
	AnchorPane chartPane;
	InstanceController instanceController = InstanceController.getInstance();

	public StockListController() {
		observableList = FXCollections.observableArrayList();
	}

	@FXML
	private void initialize() {
		stockDetailPane = (BorderPane) GraphicsUtils.getParent("StockDetail");
		instanceController.registStockDetailPane(stockDetailPane);// add the
																	// instance
																	// into the
																	// factory
		System.err.println(instanceController.toString());
		showStocklist();
		// searchBar.
	}

	/**
	 *
	 */
	@FXML
	public void showStocklist() {

		Iterator<StockVO> itr = stockBl.getAllStocks();
		showTableData(itr);

	}

	private void showTableData(Iterator<StockVO> itr) {
		tableview.getItems().removeAll(observableList);
		while (itr.hasNext()) {
			StockVO temp = itr.next();
			// System.out.println("volume"+temp.turnoverVol);
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
		
//		tableview.get
		
		tableview.setItems(observableList);

	}

	@FXML
	private void delAllData() {
		// int sum = tableview.getItems().size();
		tableview.getItems().removeAll(observableList);
	}

	@FXML
	private void handleMouseClick(MouseEvent event) {
		// System.out.println("hehe");
		if (event.getClickCount() == 2) {
			if (tableview.getSelectionModel().getSelectedIndex() == -1) {
				System.out.println("empty line ");
				return;
			}

			int row = tableview.getSelectionModel().getSelectedIndex();
			String code = tableview.getSelectionModel().getSelectedItem().code.get();
			Stock selectedStock = tableview.getSelectionModel().getSelectedItem();
			// System.out.println(code);
			// The stockDetailController is null at first, and it must generated
			// after the fxml has initialize
			// it, otherwise we will get a totally defferent object from the
			// fxml's
			if (stockDetailController == null) {
				// System.err.println("============new controller============");
				stockDetailController = StockDetailController.getStockDetailController();
			}
			System.err.println("stock instance:" + stockDetailController.toString());
			stockDetailController.setData(selectedStock, PanelType.STOCK_LIST);
			rightPaneController.showDetailPane(stockDetailPane);
		}
	}

	@FXML
	private void searchStocklist() {
		System.out.println("hello search in time:  [" + searchBar.getText() + "]end");
		String stockCode = searchBar.getText();
		if (stockCode.equals("")) {
			showStocklist();
			return;
		}
		Iterator<StockVO> itr = stockBl.getStocksByStockCode(stockCode);
		showTableData(itr);
		// new SearchThread(searchBar, this).start();
	}

	

	
	
	
	
	
	
}
