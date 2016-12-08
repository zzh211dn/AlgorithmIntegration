package frame;
/**算法主界面
 * Created by zzh on 2016/12/7.
 */

import java.awt.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import algorithm.Data;
import javafx.scene.text.Font;

import com.smooth.gui.SmoothGUI;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.sun.javafx.scene.control.skin.VirtualFlow;

public class MianFrame extends Application {
    // table with scrollbars
    private TableView<Data> trianTable = new TableView<>();
    // table without scrollbars
    private TableView<Data> testTable = new TableView<>();
    private Double[][] data = new Double[][]{
            {1.1,1.1},{2.2,2.2},{3.3,3.3},{4.4,4.4}
    };

    private final ObservableList<Data> data1 =
            FXCollections.observableArrayList(
                    new Data(data,new CheckBox()),
                    new Data(data,new CheckBox()),
                    new Data(data,new CheckBox()),
                    new Data(data,new CheckBox()),
                    new Data(data,new CheckBox()),
                    new Data(data,new CheckBox()),
                    new Data(data,new CheckBox()),
                    new Data(data,new CheckBox()),
                    new Data(data,new CheckBox()),
                    new Data(data,new CheckBox()),
                    new Data(data,new CheckBox()),
                    new Data(data,new CheckBox()),
                    new Data(data,new CheckBox())
            );


    private final ObservableList<Data> data2 =
            FXCollections.observableArrayList(
                    new Data(data,new CheckBox()),
                    new Data(data,new CheckBox()),
                    new Data(data,new CheckBox()),
                    new Data(data,new CheckBox()),
                    new Data(data,new CheckBox()),
                    new Data(data,new CheckBox()),
                    new Data(data,new CheckBox()),
                    new Data(data,new CheckBox()),
                    new Data(data,new CheckBox()),
                    new Data(data,new CheckBox()),
                    new Data(data,new CheckBox()),
                    new Data(data,new CheckBox()),
                    new Data(data,new CheckBox())
            );

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        stage.setWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
        stage.setHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight());

        stage.setTitle("算法软件");

        Scene scene = new Scene(new VBox());

        javafx.scene.control.MenuBar menuBar = new javafx.scene.control.MenuBar();
        VBox vbox = new VBox();

        // --- Menu File
        javafx.scene.control.Menu menuFile = new javafx.scene.control.Menu("文件");

        // --- Menu Edit
        javafx.scene.control.Menu menuPre = new javafx.scene.control.Menu("预处理");

        // --- Menu analyze
        javafx.scene.control.Menu menuAnal = new javafx.scene.control.Menu("分析算法");

        // --- Menu Classification
        javafx.scene.control.Menu menuClassif = new javafx.scene.control.Menu("分类算法");

        // --- Menu Regression
        javafx.scene.control.Menu menuRegression = new javafx.scene.control.Menu("回归算法");

        // --- Menu Maping
        javafx.scene.control.Menu menuMaping = new javafx.scene.control.Menu("Maping");

        //添加数据预处理菜单
        javafx.scene.control.MenuItem menuItemPre = new javafx.scene.control.MenuItem("数据预处理");
        menuPre.getItems().addAll(menuItemPre);

        //添加文件子菜单
        javafx.scene.control.MenuItem addTrainSet = new javafx.scene.control.MenuItem("打开训练集");
        javafx.scene.control.MenuItem addTestSet = new javafx.scene.control.MenuItem("打开测试集");
        javafx.scene.control.MenuItem saveTrainSet = new javafx.scene.control.MenuItem("保存训练集");
        javafx.scene.control.MenuItem saveTestSet = new javafx.scene.control.MenuItem("保存测试集");

        menuFile.getItems().addAll(addTrainSet,addTestSet,saveTrainSet,saveTestSet);

        //添加分析算法子菜单
        javafx.scene.control.MenuItem addPLS = new javafx.scene.control.MenuItem("偏最小二乘");
        javafx.scene.control.MenuItem addPCA = new javafx.scene.control.MenuItem("主成分分析");
        menuAnal.getItems().addAll(addPCA,addPLS);

        //添加分类算法子菜单
        javafx.scene.control.MenuItem addSVM = new javafx.scene.control.MenuItem("支持向量机");
        javafx.scene.control.MenuItem addKNN = new javafx.scene.control.MenuItem("最近邻");
        javafx.scene.control.MenuItem addHCA = new javafx.scene.control.MenuItem("层次聚类");
        menuClassif.getItems().addAll(addSVM,addKNN,addHCA);

        //添加回归算法子菜单
        javafx.scene.control.MenuItem addBPNN = new javafx.scene.control.MenuItem("人工神经网络");
        menuRegression.getItems().addAll(addBPNN);

        //添加Maping子菜单
        javafx.scene.control.MenuItem addPrintPic = new javafx.scene.control.MenuItem("画图");
        javafx.scene.control.MenuItem addKmeans = new javafx.scene.control.MenuItem("K-聚类");
        menuMaping.getItems().addAll(addPrintPic,addKmeans);

        //添加各菜单到菜单栏

        menuBar.getMenus().addAll(menuFile,menuPre,menuAnal,menuClassif,menuRegression,menuMaping);
        menuBar.autosize();


        //Action事件监听
        menuItemPre.setOnAction((ActionEvent t) -> {
            new SmoothGUI();
        });


        addTestSet.setOnAction((ActionEvent t) -> {
            testTable.setItems(data1);
            vbox.setVisible(true);
        });

        addTrainSet.setOnAction((ActionEvent t) -> {
            trianTable.setItems(data2);
            vbox.setVisible(true);
        });

        // setup table columns
        setupTableColumns(trianTable);
        setupTableColumns(testTable);

      /*  trianTable.setTableMenuButtonVisible(true);
        testTable.setTableMenuButtonVisible(true);*/

        // create container
        final Label trianLabel = new Label("\r\n"+"  训练集");
        trianLabel.setFont(new javafx.scene.text.Font("Arial", 20));
        final Label testLabel = new Label("\r\n"+"   测试集");
        testLabel.setFont(new javafx.scene.text.Font("Arial", 20));
        VBox vBox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        trianTable.setMaxSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth(),Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
        testTable.setMaxSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth(),Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
        vBox.getChildren().addAll(trianLabel, trianTable,testLabel, testTable);

        ((VBox) scene.getRoot()).getChildren().addAll(menuBar,vBox);

        stage.setScene(scene);
        stage.show();

        ScrollBar table1HorizontalScrollBar = findScrollBar(trianTable, Orientation.HORIZONTAL);
        ScrollBar table1VerticalScrollBar = findScrollBar(trianTable, Orientation.VERTICAL);

        // this doesn't work:
        table1HorizontalScrollBar.setVisible(true);
        table1VerticalScrollBar.setVisible(true);

        ScrollBar table2HorizontalScrollBar = findScrollBar(testTable, Orientation.HORIZONTAL);
        ScrollBar table2VerticalScrollBar = findScrollBar(testTable, Orientation.VERTICAL);

      /*  // this doesn't work:
        table2HorizontalScrollBar.setVisible(true);
        table2VerticalScrollBar.setVisible(true);

        // enforce layout to see if anything has an effect
        VirtualFlow flow1 = (VirtualFlow) trianTable.lookup(".virtual-flow");
        flow1.requestLayout();

        VirtualFlow flow2 = (VirtualFlow) testTable.lookup(".virtual-flow");
        flow2.requestLayout();*/

    }

    /**
     * Primary table column mapping.
     */
    private void setupTableColumns( TableView table) {


        TableColumn<Data, LocalDate> dateCol = new TableColumn<>("Date");
        dateCol.setPrefWidth(120);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("data"));

        TableColumn<Data, Double> value1Col = new TableColumn<>("Value 1");
        value1Col.setPrefWidth(90);
        value1Col.setCellValueFactory(new PropertyValueFactory<>("value1"));

        TableColumn<Data, Double> value2Col = new TableColumn<>("Value 2");
        value2Col.setPrefWidth(90);
        value2Col.setCellValueFactory(new PropertyValueFactory<>("value2"));


        TableColumn<Data, Double> value3Col = new TableColumn<>("Value 3");
        value3Col.setPrefWidth(90);
        value3Col.setCellValueFactory(new PropertyValueFactory<>("value3"));

        TableColumn<Data,CheckBox> value4Col = new TableColumn<>("删除");
        value4Col.setPrefWidth(90);
        value4Col.setCellValueFactory(new PropertyValueFactory<>("check"));


        table.getColumns().addAll( dateCol, value1Col, value2Col, value3Col,value4Col);

    }

    /**
     * Find the horizontal scrollbar of the given table.
     * @param table
     * @return
     */
    private ScrollBar findScrollBar(TableView<?> table, Orientation orientation) {

        // this would be the preferred solution, but it doesn't work. it always gives back the vertical scrollbar
        //      return (ScrollBar) table.lookup(".scroll-bar:horizontal");
        //      
        // => we have to search all scrollbars and return the one with the proper orientation

        Set<Node> set = table.lookupAll(".scroll-bar");
        for( Node node: set) {
            ScrollBar bar = (ScrollBar) node;
            if( bar.getOrientation() == orientation) {
                return bar;
            }
        }

        return null;

    }

}