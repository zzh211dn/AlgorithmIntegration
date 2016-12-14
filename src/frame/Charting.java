package frame;

import javafx.application.Application;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.gillius.jfxutils.JFXUtil;
import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.FixedFormatTickFormatter;
import org.gillius.jfxutils.chart.JFXChartUtil;
import org.gillius.jfxutils.chart.StableTicksAxis;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.TimeZone;

public class Charting extends Application {
    public static void main( String[] args ) {
        launch( );
    }

    @FXML
    private LineChart<Number, Number> chart;

    @FXML
    private Label outputLabel;

    private long startTime;

    private Timeline addDataTimeline;

    @FXML
    void addSample(XYChart.Series<Number, Number> tempSeries) {
        for(int m = 0;m<10;m++) {
            tempSeries.getData().add(new XYChart.Data<Number, Number>(m,new Random().nextInt(10)));// System.currentTimeMillis() - startTime,valueSlider.getValue()
        }

    }

    @FXML
    void autoZoom() {
        chart.getXAxis().setAutoRanging( true );
        chart.getYAxis().setAutoRanging( true );
    }

    @Override
    public void start( Stage stage ) throws Exception {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("../Charting.fxml") );
        Region contentRootRegion = (Region) loader.load();

        StackPane root = JFXUtil.createScalePane( contentRootRegion, 960, 540, false );//调整框架大小
        Scene scene = new Scene( root, root.getPrefWidth(), root.getPrefHeight() );
        stage.setScene( scene );
        stage.setTitle( "Charting" );
        stage.show();
    }

    @FXML
    void initialize() {
        //Set chart to format dates on the X axis
        ((StableTicksAxis) chart.getXAxis()).setAutoRangePadding(1);

        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        XYChart.Series<Number, Number> series1 = new XYChart.Series<Number, Number>();
        series.setName( "Date" );
        series1.setName( "Date1" );
        addSample(series);
        addSample(series1);


        chart.getData().addAll( series ,series1);

        chart.setOnMouseMoved( new EventHandler<MouseEvent>() {
            public void handle( MouseEvent mouseEvent ) {
                double xStart = chart.getXAxis().getLocalToParentTransform().getTx();
                double axisXRelativeMousePosition = mouseEvent.getX() - xStart;
                outputLabel.setText( String.format(
                        "%d, %d (%d, %d); %d - %d",
                        (int) mouseEvent.getSceneX(), (int) mouseEvent.getSceneY(),
                        (int) mouseEvent.getX(), (int) mouseEvent.getY(),
                        (int) xStart,chart.getXAxis().getValueForDisplay( axisXRelativeMousePosition ).intValue()
                ) );
            }
        } );

        //Panning works via either secondary (right) mouse or primary with ctrl held down
        ChartPanManager panner = new ChartPanManager( chart );
        panner.setMouseFilter( new EventHandler<MouseEvent>() {
            public void handle( MouseEvent mouseEvent ) {
                if ( mouseEvent.getButton() == MouseButton.SECONDARY ||
                        ( mouseEvent.getButton() == MouseButton.PRIMARY &&
                                mouseEvent.isShortcutDown() ) ) {
                    //let it through
                } else {
                    mouseEvent.consume();
                }
            }
        } );
        panner.start();

        //Zooming works only via primary mouse button without ctrl held down
        JFXChartUtil.setupZooming( chart, new EventHandler<MouseEvent>() {
            public void handle( MouseEvent mouseEvent ) {
                if ( mouseEvent.getButton() != MouseButton.PRIMARY ||
                        mouseEvent.isShortcutDown() )
                    mouseEvent.consume();
            }
        } );

        JFXChartUtil.addDoublePrimaryClickAutoRangeHandler( chart );
    }
}