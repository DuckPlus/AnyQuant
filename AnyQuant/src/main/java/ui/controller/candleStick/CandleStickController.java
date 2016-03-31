package ui.controller.candleStick;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import blimpl.StockBLImpl;
import blservice.StockBLService;
import enumeration.MyDate;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.control.Tab;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import util.MyTime;
import vo.OHLC_VO;



/**
 *
 * @author ss
 * @date 2016年3月24日
 * =。=
 */
public class CandleStickController  implements Initializable {

    @FXML
    private Tab dayTab;
    @FXML
    private Tab weekTab;
    @FXML
    private Tab monthTab;
	@FXML
	DatePicker startDatePicker;
	@FXML
	DatePicker endDatePicker;
    @FXML
    private CandleStickChart dayChart,weekChart,monthChart;

    private static String stockCode;
    private static MyDate startDate,endDate;
  	private static StockBLService stockBl ;

  	private static ObservableList<OHLC_VO> obsevableList ;

  	private static CandleStickController instance;
    private static ProgressIndicator progressIndicator;
    public  CandleStickController (){
    	if(instance==null){
    	   stockCode="sh600000";
    	   startDate = new MyDate(2016,3,1 );
    	   endDate = new MyDate(2016,4, 1);
    	   stockBl = StockBLImpl.getAPIBLService();
    	   obsevableList = FXCollections.observableArrayList();
    	   obsevableList.clear();
           instance=this;
    	}
    }

    public static CandleStickController  getCandleStickController(){
             if(instance!=null){
            	 return instance;
             }else{
            	 return new CandleStickController();
             }
    }

    public static  void setStockCode(String  newCode){
          stockCode = newCode;
    }

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	progressIndicator = createProgressIndicator();
    	startDatePicker.setValue(LocalDate.now());
		endDatePicker.setValue(LocalDate.now());
		startDatePicker.setEditable(false);
		endDatePicker.setEditable(false);
		initCharts();
    }
    //double click to enter this scene
  public void initCharts(){
		Task initTask = createInitWorker();
		progressIndicator.setVisible(true);
		progressIndicator.progressProperty().unbind();
		progressIndicator.progressProperty().bind( initTask.progressProperty());
		new Thread( initTask).start();

  }

  //set date  to update charts
  @FXML
public void updateCharts(){
       startDate = new MyDate
			   (startDatePicker.getValue().getYear(), startDatePicker.getValue().getMonthValue(), startDatePicker.getValue().getDayOfMonth());
		endDate  = new MyDate
				(endDatePicker.getValue().getYear(), endDatePicker.getValue().getMonthValue(), endDatePicker.getValue().getDayOfMonth());

		Task updateTask = createUpdateWorker();
		progressIndicator.setVisible(true);
		progressIndicator.progressProperty().unbind();
		progressIndicator.progressProperty().bind( updateTask.progressProperty());
		new Thread( updateTask).start();
}



  public void  prepareInitCharts(){
	  selectDay();
	  selectWeek();
	  selectMonth();
  }

  public void  prepareUpdateCharts(){
	updateDay();
	updateWeek();
	updateMonth();
  }

  public void displayCharts(){
	     initPane(dayTab, dayChart,new ScrollPane());
		 initPane(weekTab, weekChart,new ScrollPane());
		 initPane(monthTab, monthChart,new ScrollPane());
  }


   public  void selectDay(){
	   if(dayChart==null){
	     getDayData();
	     dayChart =createChart();
	   }
  }

   public  void selectWeek(){
	   if(weekChart==null){
         getWeekData();
	     weekChart =createChart();
       }
  }

   public  void selectMonth(){
	   if(monthChart==null){
         getMonthData();
	     monthChart =createChart();

	   }
 }

 public  void updateDay(){
	     dayChart.getData().clear();
	     getDayDataByDate();
	     dayChart =createChart();
}

 public  void updateWeek(){
	   weekChart.getData().clear();
       getWeekDataByDate();
	    weekChart =createChart();
}

 public  void updateMonth(){
	  monthChart.getData().clear();
       getMonthDataByDate();
	    monthChart =createChart();
}

    private void initPane( Tab tab  , Node chartNode, ScrollPane spane ){
          HBox hBox = new HBox();
          HBox.setHgrow(chartNode, Priority.ALWAYS);
          hBox.getChildren().add(chartNode);

	      spane.setContent(hBox);
	      spane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
	      spane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
          tab.setContent(spane);
  }

    private  void  getDayData(){
           //默认显示近一个月的数据
    	   MyDate end = MyTime.getToDay();
    	   MyDate start = MyTime.getAnotherDay(-30);
    	   List<OHLC_VO>list =stockBl.getDayOHLC_Data(stockCode, start, end);
    	   obsevableList.clear();
    	   for(OHLC_VO temp : list){
    		     obsevableList.add(temp);
    	   }
    }
    private  void  getDayDataByDate(){
		if (startDate != null && endDate != null) {
			List<OHLC_VO> list = stockBl.getDayOHLC_Data(stockCode, startDate, endDate);
			obsevableList.clear();
			for (OHLC_VO temp : list) {
				obsevableList.add(temp);
			}
		}
 }


    private  void  getWeekData(){
        //默认显示最近一年的数据
 	   MyDate end = MyTime.getToDay();
 	   MyDate start = MyTime.getAnotherDay(-180);
 	   List<OHLC_VO>list =stockBl.getWeekOHLC_Data(stockCode, start, end);
 	   obsevableList.clear();
 	   for(OHLC_VO temp : list){
 		     obsevableList.add(temp);
 	   }
    }

    private  void  getWeekDataByDate(){
		if (startDate != null && endDate != null) {
			List<OHLC_VO> list = stockBl.getWeekOHLC_Data(stockCode, startDate, endDate);
			obsevableList.clear();
			for (OHLC_VO temp : list) {
				obsevableList.add(temp);
			}
		}
    }

    private  void  getMonthData(){
        //默认显示最近三年的数据
 	   MyDate end = MyTime.getToDay();
 	   MyDate start = MyTime.getAnotherDay(-365*2);
 	   List<OHLC_VO>list =stockBl.getMonthOHLC_Data(stockCode, start, end);
 	   obsevableList.clear();
 	   for(OHLC_VO temp : list){
 		     obsevableList.add(temp);
 	   }
    }

    private  void  getMonthDataByDate(){
		if (startDate != null && endDate != null) {
			List<OHLC_VO> list = stockBl.getMonthOHLC_Data(stockCode, startDate, endDate);
			obsevableList.clear();
			for (OHLC_VO temp : list) {
				obsevableList.add(temp);
			}
		}
    }


    private CandleStickChart createChart() {
    	double gap=0.5;
    	//X轴
        final CategoryAxis xAxis = new CategoryAxis ();
        //Y轴
        final NumberAxis yAxis = new NumberAxis(getMin()-gap,getMax()+gap*4,gap);
        final CandleStickChart candleStickChart = new CandleStickChart(xAxis,yAxis);
        // setup chart
       // candleStickChart.setTitle("Custom Candle Stick Chart");
        xAxis.setLabel("Day");
        yAxis.setLabel("Price");
        // add starting data
        XYChart.Series<String,Number> series = new XYChart.Series<String,Number>();
        for (int i=0; i< obsevableList.size(); i++) {
            OHLC_VO vo = obsevableList.get(i);
                //参数：日期、开盘价
             series.getData().add(   new XYChart.Data<String,Number>
                (vo.date.DateToString(),vo.open,new CandleStickExtraValues(vo.close,vo.high,vo.low,vo.close)));
        }
        // candleStickChart.getData()  return type:ObservableList<XYChart.Series<Number,Number>>
        ObservableList<XYChart.Series<String,Number>> data = candleStickChart.getData();
        if (data == null) {

            data = FXCollections.observableArrayList(series);
            candleStickChart.setData(data);
        } else {
            candleStickChart.getData().add(series);
        }
        return candleStickChart;
    }


	/*
	 * Create a progress indicator control to be centered.
	 *
	 * @param scene The primary application scene.
	 *
	 * @return ProgressIndicator a new progress indicator centered.
	 */
	private ProgressIndicator createProgressIndicator() {
		ProgressIndicator progress = new ProgressIndicator(0);
		progress.setVisible(false);
	//	progress.layoutXProperty().bind(scene.widthProperty().subtract(progress.widthProperty()).divide(2));
	//	progress.layoutYProperty().bind(scene.heightProperty().subtract(progress.heightProperty()).divide(2));
		return progress;
	}

	private Task createInitWorker() {
		return new Task() {
			@Override
			protected Object call() throws Exception {
				// on the worker thread...

				Platform.runLater(() -> {
					prepareInitCharts();
					// on the JavaFX Application Thread....
					System.out.println("done initCharts");
					displayCharts();
					progressIndicator.setVisible(false);
				});
				return true;
			}
		};
	}


	private Task createUpdateWorker() {
		return new Task() {
			@Override
			protected Object call() throws Exception {
				// on the worker thread...
			    prepareUpdateCharts();
				Platform.runLater(() -> {
					// on the JavaFX Application Thread....
					System.out.println("done updateCharts");
					displayCharts();
					progressIndicator.setVisible(false);
				});
				return true;
			}
		};
	}

    private double getMin(){
    	double min=100;
    	for(OHLC_VO temp : obsevableList){
    		if(temp.low<min){
    			min = temp.low;
    		}
    	}
    	return min;
    }

    private double getMax(){
    	double max=0;
    	for(OHLC_VO temp : obsevableList){
    		if(temp.high>max){
    			max = temp.low;
    		}
    	}
    	return max;
    }

}
