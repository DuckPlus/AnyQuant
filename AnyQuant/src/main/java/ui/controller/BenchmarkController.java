package ui.controller;

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
import vo.BenchMark;
import vo.BenchMarkVO;

public class BenchmarkController {
	
	@FXML
	TableColumn<BenchMark, String> code;
	@FXML
	TableColumn<BenchMark, String> date;
	@FXML
	TableColumn<BenchMark, Double> open;
	@FXML
	TableColumn<BenchMark, Double> close;
	@FXML
	TableColumn<BenchMark, Double> high;
	@FXML
	TableColumn<BenchMark, Double> low;
	@FXML
	TableColumn<BenchMark, Double> adj_price;
	@FXML
	TableColumn<BenchMark, Long> volume;
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
		beginDate.setValue(LocalDate.now());
		endDate.setValue(LocalDate.now());
		beginDate.setEditable(false);
		endDate.setEditable(false);
		

		
	}
	
	private void showAllBenchmark(){
		Iterator<BenchMarkVO>itr = benchmarkBl.getRecentBenchMarks("hs300");
		showTableData(itr);
//		while(itr.hasNext()){
//			BenchMarkVO temp = itr.next();
////			System.out.println(temp.date+" "+temp.adj_price);
//			BenchMark dataProperty = new BenchMark(temp);
//			observableList.add(dataProperty);
//		}
//		code.setCellValueFactory(cell -> cell.getValue().code);
//		date.setCellValueFactory(cell -> cell.getValue().date);
//		open.setCellValueFactory(cell -> cell.getValue().open.asObject());
//		close.setCellValueFactory(cell -> cell.getValue().close.asObject());
//		high.setCellValueFactory(cell -> cell.getValue().high.asObject());
//		low.setCellValueFactory(cell -> cell.getValue().low.asObject());
//		volume.setCellValueFactory(cell -> cell.getValue().volume.asObject());
//		adj_price.setCellValueFactory(cell -> cell.getValue().adj_price.asObject());
//		tableview.setItems(observableList);
		
	}
	@FXML
	private void searchByDate(){
//		System.out.println("picker done");
//		System.out.println(beginDate.getValue().getMonth().getValue());
//		System.out.println(beginDate.getValue().getYear());
//		System.out.println(beginDate.getValue().getDayOfMonth());
		MyDate start = new MyDate(beginDate.getValue().getYear(), beginDate.getValue().getMonthValue(), beginDate.getValue().getDayOfMonth());
		MyDate end = new MyDate(endDate.getValue().getYear(), endDate.getValue().getMonthValue(), endDate.getValue().getDayOfMonth());
		System.out.println(start.AllToString()+"   "+end.AllToString());
		Iterator<BenchMarkVO>itr = benchmarkBl.getBenchMarkByTime("hs300", start, end);
		showTableData(itr);
//		while(itr.hasNext()){
//			BenchMarkVO temp = itr.next();
//			
//		}
//		if(beginDate){
//			System.out.println("bad request");
//			return;
//		}
		System.out.println(beginDate.getValue().toString());
		System.out.println(endDate.getValue().toString());
	}
	private void showTableData(Iterator<BenchMarkVO>itr){
		tableview.getItems().removeAll(observableList);
		while(itr.hasNext()){
			BenchMarkVO temp = itr.next();
//			System.out.println(temp.date+" "+temp.adj_price);
			BenchMark dataProperty = new BenchMark(temp);
			observableList.add(dataProperty);
		}
		code.setCellValueFactory(cell -> cell.getValue().code);
		date.setCellValueFactory(cell -> cell.getValue().date);
		open.setCellValueFactory(cell -> cell.getValue().open.asObject());
		close.setCellValueFactory(cell -> cell.getValue().close.asObject());
		high.setCellValueFactory(cell -> cell.getValue().high.asObject());
		low.setCellValueFactory(cell -> cell.getValue().low.asObject());
		volume.setCellValueFactory(cell -> cell.getValue().volume.asObject());
		adj_price.setCellValueFactory(cell -> cell.getValue().adj_price.asObject());
		tableview.setItems(observableList);
		
	}
}
