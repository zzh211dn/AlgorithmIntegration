package frame;


import javafx.application.Application;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import org.gillius.jfxutils.JFXUtil;
import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.JFXChartUtil;
import org.gillius.jfxutils.chart.StableTicksAxis;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimeZone;

public class Charting {
    public static void main( String[] args ) {
        Application.launch();
    }
    public static ArrayList<Double [][]> thisDatas = new ArrayList<>();
    public static String[] fileNameList ;
    public void drawChat(ArrayList<Double [][]> datas,String[] fileListName)
    {
        thisDatas = datas;
        fileNameList = fileListName;
    }
    public ArrayList<Double> averageList = new ArrayList<Double>();

    @FXML
    private LineChart<Number, Number> chart ;

    @FXML
    private Label outputLabel;

    private long startTime;

    private Timeline addDataTimeline;

    @FXML
    void addSample(XYChart.Series<Number, Number> tempSeries,Double[][] datas) {

        for(int i = 0;i<datas.length;i++)
        {
//            System.out.println(i+"==="+averageList.get(i)+datas[i][1]/thisDatas.size());
            averageList.set(i,averageList.get(i)+datas[i][1]/thisDatas.size());
            tempSeries.getData().add(new XYChart.Data<Number, Number>(datas[i][0],datas[i][1]));
        }

    }

    @FXML
    void autoZoom() {
        chart.getXAxis().setAutoRanging( true );
        chart.getYAxis().setAutoRanging( true );
    }

    //    @Override
    public Scene start() throws Exception {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/Charting.fxml") );
        Region contentRootRegion = (Region) loader.load();
        StackPane root = JFXUtil.createScalePane( contentRootRegion, 960, 540, false );//调整框架大小
        Scene scene = new Scene( root, root.getPrefWidth(), root.getPrefHeight() );
        return scene;
    }

    int nodeSize = 0;

    @FXML
    void initialize() {
        //Set chart to format dates on the X axis
//        ((StableTicksAxis) chart.getXAxis()).setAutoRangePadding();//设置间隔
        ((StableTicksAxis) chart.getXAxis()).setForceZeroInRange(false);//强制设置X轴不为0开头
        boolean f = false;
        chart.setCreateSymbols(f);
        nodeSize = thisDatas.get(0).length;
        for(int j= 0;j<nodeSize;j++)
        {
            averageList.add(j,0.0);
        }

        Double[][] averag = new Double[nodeSize][2];
        for(int m = 0;m<thisDatas.size();m++) {
            XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
            series.setName(fileNameList[m]);
            addSample(series,thisDatas.get(m));
            chart.getData().add(series);
        }

        for(int j= 0;j<nodeSize;j++)
        {
            averag[j][0] = thisDatas.get(0)[j][0];
            averag[j][1] = averageList.get(j);
        }

        XYChart.Series<Number, Number> averagseries = new XYChart.Series<Number, Number>();
        averagseries.setName("averagLine");
        addSample(averagseries,averag);
        chart.getData().add(averagseries);


        chart.setOnMouseMoved( new EventHandler<MouseEvent>() {
            public void handle( MouseEvent mouseEvent ) {
                double xStart = chart.getXAxis().getLocalToParentTransform().getTx();
                double YStart = chart.getYAxis().getLocalToParentTransform().getTx();
                double axisXRelativeMousePosition = mouseEvent.getX() - xStart;
                double axisYRelativeMousePosition = mouseEvent.getY() - YStart;
                outputLabel.setText( String.format(
                        "%d, %d (%d, %d); %f - %d",
                        (int) mouseEvent.getSceneX(), (int) mouseEvent.getSceneY(),
                        (int) mouseEvent.getX(), (int) mouseEvent.getY(),
                        chart.getYAxis().getValueForDisplay( axisYRelativeMousePosition ).floatValue()
                        ,chart.getXAxis().getValueForDisplay( axisXRelativeMousePosition ).intValue()
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