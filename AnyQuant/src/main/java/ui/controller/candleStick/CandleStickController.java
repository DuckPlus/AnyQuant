package ui.controller.candleStick;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import blimpl.StockBLImpl;
import blservice.StockBLService;
import enumeration.MyDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
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
    private CandleStickChart chart;

    private static String stockCode;

  	private static StockBLService stockBl ;

  	private static ObservableList<OHLC_VO> obsevableList ;

  	private static CandleStickController instance;

    public  CandleStickController (){
    	if(instance==null){
    	   stockCode="sh600216";
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
    	//call selectDay() by deafult
	    //  selectWeek();
	  //    selectMonth();
    }

  public  void selectDay(){

	     getDayData();
	     chart =createChart();
         initPane(dayTab, chart,new ScrollPane());
  }

  public  void selectWeek(){

	     chart =createChart();
         initPane(weekTab, chart,new ScrollPane());
  }

  public  void selectMonth(){

	     chart =createChart();
         initPane(monthTab, chart,new ScrollPane());
 }

    private void initPane( Tab tab  , Node chartNode, ScrollPane spane ){

	      GridPane gridPane = new GridPane();
	      ColumnConstraints cc = new ColumnConstraints(905,905,905);
	      RowConstraints rc = new RowConstraints(720,720,720);
	      gridPane.getColumnConstraints().add(cc);
	      gridPane.getRowConstraints().add(rc);
          gridPane.add(chartNode, 0, 0);

	      spane.setContent(gridPane);
	      spane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
	      spane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
          tab.setContent(spane);

  }

    private  void  getDayData(){
    	//   System.out.println("CALL getDayData() ");
    	   MyDate end = MyTime.getToDay();
    	   MyDate start = MyTime.getAnotherDay(-31);
    	   List<OHLC_VO>list =stockBl.getDayOHLC_Data(stockCode, start, end);
    	   obsevableList.clear();
   // 	   System.out.println("list size : "+list.size());

    	   for(OHLC_VO temp : list){
    		     obsevableList.add(temp);
    	   }
    //	   System.out.println(obsevableList.size());
    }

    private CandleStickChart createChart() {
    	double gap=0.5;
    	//X轴
        final CategoryAxis xAxis = new CategoryAxis ();
        //Y轴
        final NumberAxis yAxis = new NumberAxis(getMin()-gap,getMax()+gap*4,gap);
        final CandleStickChart candleStickChart = new CandleStickChart(xAxis,yAxis);
        // setup chart
        candleStickChart.setTitle("Custom Candle Stick Chart");
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
