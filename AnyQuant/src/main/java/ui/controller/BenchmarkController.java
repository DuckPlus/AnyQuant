package ui.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Iterator;

import blimpl.BenchMarkBLImpl;
import blservice.BenchMarkBLService;
import enumeration.MyDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
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
	@FXML
	DatePicker beginDate;
	@FXML
	DatePicker endDate;
	private BenchMarkBLService benchmarkBl = BenchMarkBLImpl.getBenchMarkBLService();
	ObservableList<BenchMark>observableList = FXCollections.observableArrayList();
	public BenchmarkController() {
		// TODO Auto-generated constructor stub
		System.out.println("constructor done");
	}

	@FXML
	private void initialize(){
		System.out.println("init done");
		showAllBenchmark();
//		beginDate.setValue(LocalDate.now());
//		endDate.setValue(LocalDate.now());
//		beginDate.setEditable(false);
//		endDate.setEditable(false);



	}

	private void showAllBenchmark(){
		Iterator<BenchMarkVO>itr = benchmarkBl.getAllBenchMarks();
		showTableData(itr);

	}
	@FXML
	private void searchByDate(){
		MyDate start = new MyDate(beginDate.getValue().getYear(), beginDate.getValue().getMonthValue(), beginDate.getValue().getDayOfMonth());
		MyDate end = new MyDate(endDate.getValue().getYear(), endDate.getValue().getMonthValue(), endDate.getValue().getDayOfMonth());
		System.out.println(start.AllToString()+"   "+end.AllToString());
		Iterator<BenchMarkVO>itr = benchmarkBl.getBenchMarkByTime("hs300", start, end);
		System.out.println(benchmarkBl.getAllBenchMarks().next().name);
		showTableData(itr);
	}
	
	private void showTableData(Iterator<BenchMarkVO>itr){
		
		tableview.getItems().removeAll(observableList);
//		if(itr==null)System.err.println("Iterator null");
		while(itr.hasNext()){
//			System.out.println("hello table");
			BenchMarkVO temp = itr.next();
			System.out.println(temp.date+" "+temp.name);
//			NumberFormat nf = NumberFormat.getNumberInstance();
//			nf.setMaximumFractionDigits(4);
			temp.turnoverValue = temp.turnoverValue/1000000;
			BigDecimal bigDecimal = new BigDecimal(temp.turnoverValue);
			temp.turnoverValue = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//			System.err.println(temp.turnoverValue);
//			String fmtString = nf.format(temp.turnoverValue);
//			System.err.println(fmtString);
//			double fmtDouble = Double.valueOf(fmtString);
//			temp.turnoverValue = fmtDouble;
//			temp.turnoverValue = Double.valueOf(nf.format(temp.turnoverValue/1000000));
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
