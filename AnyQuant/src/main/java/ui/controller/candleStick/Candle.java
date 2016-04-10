package ui.controller.candleStick;

import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;

/**
 *
 * @author ss
 * @date 2016年3月24日
 */
/** Candle node used for drawing a candle */
class Candle extends Group {
    private Line highLowLine = new Line();
    private Region bar = new Region();
    private String seriesStyleClass;
    private String dataStyleClass;
    private boolean openAboveClose = true;
    private Tooltip tooltip = new Tooltip();

    public Candle(String seriesStyleClass, String dataStyleClass) {
    	//System.out.println(seriesStyleClass+"  "+dataStyleClass);
        setAutoSizeChildren(false);
        getChildren().addAll(highLowLine, bar);
        this.seriesStyleClass = seriesStyleClass;
        this.dataStyleClass = dataStyleClass;
        updateStyleClasses();
        tooltip.setGraphic(new TooltipContentCandleStick());
        Tooltip.install(bar, tooltip);
    }

    public void setSeriesAndDataStyleClasses(String seriesStyleClass, String dataStyleClass) {
        this.seriesStyleClass = seriesStyleClass;
        this.dataStyleClass = dataStyleClass;
        updateStyleClasses();
    }

   // candle.update(close - y, high - y, low - y, candleWidth);   y=open
    public void update(double closeOffset, double highOffset, double lowOffset, double candleWidth) {
        openAboveClose = closeOffset > 0;
        updateStyleClasses();
//        highLowLine.setStartY(highOffset);
//        highLowLine.setEndY(lowOffset);
        highLowLine.setStartY(highOffset);
        highLowLine.setEndY(lowOffset);
        if (candleWidth == -1) {
            candleWidth = bar.prefWidth(-1);
        }
        if (openAboveClose) {
            bar.resizeRelocate(-candleWidth / 2, 0, candleWidth, closeOffset);
        } else {
            bar.resizeRelocate(-candleWidth / 2, closeOffset, candleWidth, closeOffset * -1);
        }
    }

    public void updateTooltip(double open, double close, double high, double low) {
        TooltipContentCandleStick tooltipContent = (TooltipContentCandleStick) tooltip.getGraphic();
        tooltipContent.update(open, close, high, low);
//                tooltip.setText("Open: "+open+"\nClose: "+close+"\nHigh: "+high+"\nLow: "+low);
    }

    private void updateStyleClasses() {
        getStyleClass().setAll("candlestick-candle", seriesStyleClass, dataStyleClass);
        highLowLine.getStyleClass().setAll("candlestick-line", seriesStyleClass, dataStyleClass,
                openAboveClose ? "open-above-close" : "close-above-open");
        bar.getStyleClass().setAll("candlestick-bar", seriesStyleClass, dataStyleClass,
                openAboveClose ? "open-above-close" : "close-above-open");
    }
}
