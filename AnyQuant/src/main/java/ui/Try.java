package ui;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
public class Try extends Application {
Path path;

public static void main(String[] args) {
    launch(args);
}
@Override
public void start(Stage stage) {
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis(1, 21, 0.1);
    yAxis.setTickUnit(1);
    yAxis.setPrefWidth(35);
    yAxis.setMinorTickCount(10);
    yAxis.setSide(Side.RIGHT);
    yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
        @Override
        public String toString(Number object) {
            String label;
            label = String.format("%7.2f", object.floatValue());
            return label;
        }
    });

final LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);
    lineChart.setCreateSymbols(false);
    lineChart.setAlternativeRowFillVisible(false);
    lineChart.setLegendVisible(false);
    XYChart.Series series1 = new XYChart.Series();
    series1.getData().add(new XYChart.Data("Jan", 1));
    series1.getData().add(new XYChart.Data("Feb", 4));
    series1.getData().add(new XYChart.Data("Mar", 2.5));
    series1.getData().add(new XYChart.Data("Apr", 5));
    series1.getData().add(new XYChart.Data("May", 6));
    series1.getData().add(new XYChart.Data("Jun", 8));
    series1.getData().add(new XYChart.Data("Jul", 12));
    series1.getData().add(new XYChart.Data("Aug", 8));
    series1.getData().add(new XYChart.Data("Sep", 11));
    series1.getData().add(new XYChart.Data("Oct", 13));
    series1.getData().add(new XYChart.Data("Nov", 10));
    series1.getData().add(new XYChart.Data("Dec", 20));

BorderPane bp = new BorderPane();
    bp.setCenter(lineChart);
    Scene scene = new Scene(bp, 800, 600);
    lineChart.setAnimated(false);
    lineChart.getData().addAll(series1);

    Try.MouseHandler mh = new Try.MouseHandler( bp );
    bp.setOnMouseClicked( mh );
    bp.setOnMouseMoved( mh );
    stage.setScene(scene);
    path = new Path();
    path.setStrokeWidth(1);
    path.setStroke(Color.BLACK);
    scene.setOnMouseDragged(mh);
    scene.setOnMousePressed(mh);
    bp.getChildren().add(path);
    stage.setScene(scene);
    stage.show();
}

class MouseHandler implements EventHandler< MouseEvent > {
private boolean gotFirst    = false;
private Line    line;
private Pane    pane;
private double  x1, y1, x2, y2;
private LineHandler lineHandler;

public MouseHandler( Pane pane ) {
    this.pane = pane;
    lineHandler = new LineHandler(pane);
}

class LineHandler implements EventHandler< MouseEvent > {
double  x, y;
Pane pane;
public LineHandler(Pane pane){
    this.pane = pane;
}
@Override
public void handle( MouseEvent e ) {
    Line l = (Line) e.getSource();
    // remove line on right click
    if( e.getEventType() == MouseEvent.MOUSE_PRESSED
            && e.isSecondaryButtonDown() ) {
        pane.getChildren().remove( l );
    } else if( e.getEventType() == MouseEvent.MOUSE_DRAGGED
            && e.isPrimaryButtonDown() ) {
        double tx = e.getX();
        double ty = e.getY();
        double dx = tx - x;
        double dy = ty - y;
        l.setStartX( l.getStartX() + dx );
        l.setStartY( l.getStartY() + dy );
        l.setEndX( l.getEndX() + dx );
        l.setEndY( l.getEndY() + dy );
        x = tx;
        y = ty;
    } else if( e.getEventType() == MouseEvent.MOUSE_ENTERED ) {
        // just to show that the line is selected
        x = e.getX();
        y = e.getY();
        l.setStroke( Color.RED );
    } else if( e.getEventType() == MouseEvent.MOUSE_EXITED ) {
        l.setStroke( Color.BLACK );
    }
    // should not pass event to the parent
    e.consume();
}
}
@Override
public void handle( MouseEvent event ) {
    if( event.getEventType() == MouseEvent.MOUSE_CLICKED ) {
        if( !gotFirst ) {
            x1 = x2 = event.getX();
            y1 = y2 = event.getY();
            line = new Line( x1, y1, x2, y2 );
            pane.getChildren().add( line );
            gotFirst = true;
        }
        else {
            line.setOnMouseEntered( lineHandler );
            line.setOnMouseExited( lineHandler );
            line.setOnMouseDragged( lineHandler );
            line.setOnMousePressed( lineHandler );
            // to consume the event
            line.setOnMouseClicked( lineHandler );
            line.setOnMouseReleased( lineHandler );
            line = null;
            gotFirst = false;
        }
    }
        else {
            if( line != null ) {
                x2 = event.getX();
                y2 = event.getY();
                // update line
                line.setEndX( x2 );
                line.setEndY( y2 );
        }
     }
  }
  }
  }