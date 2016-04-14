/**
 * @status code reviewed
 * @handler dzm
 */
package ui.controller;

import java.math.BigDecimal;
import java.util.Iterator;
import blimpl.BenchMarkBLImpl;
import blservice.BenchMarkBLService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ui.helper.ColorHelper;
import vo.BenchMark;
import vo.BenchMarkVO;

public class BenchmarkController {

	@FXML
	TableColumn<BenchMark, String> code;
	@FXML
	TableColumn<BenchMark, String> name;
	@FXML
	TableColumn<BenchMark, String> date;
	@FXML
	TableColumn<BenchMark, Double> open;
	@FXML
	TableColumn<BenchMark, Double> close;
	@FXML
	TableColumn<BenchMark, Double> preClose;
	@FXML
	TableColumn<BenchMark, Double> high;
	@FXML
	TableColumn<BenchMark, Double> low;
	@FXML
	TableColumn<BenchMark, Long> turnoverVol;
	@FXML
	TableColumn<BenchMark, Double> turnoverValue;
	@FXML
	TableView<BenchMark> tableview;

	private BenchMarkBLService benchmarkBl = BenchMarkBLImpl.getBenchMarkBLService();
	private ObservableList<BenchMark>observableList = FXCollections.observableArrayList();	
	
	public BenchmarkController() {
		
	}

	/**
	 * 初始化方法 由fxml加载界面后自动调用
	 */
	@FXML
	private void initialize(){
		showAllBenchmark();
	}
	/**
	 * 获取并在表格中显示显示所有大盘信息
	 * @see showTableData
	 */
	private void showAllBenchmark(){
		Iterator<BenchMarkVO>itr = benchmarkBl.getAllBenchMarks();
		showTableData(itr);
	}
	
	/**
	 * 显示迭代器中的大盘信息，由于有复用的可能性 故独立出
	 * @param itr
	 */
	private void showTableData(Iterator<BenchMarkVO>itr){
		tableview.getItems().removeAll(observableList);
			while(itr.hasNext()){
				BenchMarkVO temp = itr.next();
				temp.turnoverValue = temp.turnoverValue/1000000;
				BigDecimal bigDecimal = new BigDecimal(temp.turnoverValue);
				temp.turnoverValue = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				BenchMark dataProperty = new BenchMark(temp);
				observableList.add(dataProperty);
			}
		code.setCellValueFactory(cell -> cell.getValue().code);
		name.setCellValueFactory(cell -> cell.getValue().name);
		open.setCellValueFactory(cell -> cell.getValue().open.asObject());
		close.setCellValueFactory(cell -> cell.getValue().close.asObject());
		preClose.setCellValueFactory(cell -> cell.getValue().preClose.asObject());
		high.setCellValueFactory(cell -> cell.getValue().high.asObject());
		low.setCellValueFactory(cell -> cell.getValue().low.asObject());
		turnoverVol.setCellValueFactory(cell -> cell.getValue().turnoverVol.asObject());
		turnoverValue.setCellValueFactory(cell -> cell.getValue().turnoverValue.asObject());
		ColorHelper.setColorForBench(observableList, tableview.getColumns());
		tableview.setItems(observableList);

	}
}
