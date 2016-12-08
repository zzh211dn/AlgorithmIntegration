package frame;
/**算法主界面
 * Created by zzh on 2016/12/7.
 */

import java.awt.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.Set;
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
    private TableView<algorithm.Data> trianTable = new TableView<>();
    // table without scrollbars
    private TableView<algorithm.Data> testTable = new TableView<>();


    private final ObservableList<algorithm.Data> data1 =
            FXCollections.observableArrayList(
                    new algorithm.Data( LocalDate.of(2015, Month.JANUARY, 10), 10.0, 20.0, 30.0,new CheckBox()),
                    new algorithm.Data( LocalDate.of(2015, Month.JANUARY, 11), 40.0, 50.0, 60.0,new CheckBox()),
                    new algorithm.Data( LocalDate.of(2015, Month.JANUARY, 12), 10.0, 20.0, 30.0,new CheckBox()),
                    new algorithm.Data( LocalDate.of(2015, Month.JANUARY, 13), 40.0, 50.0, 60.0,new CheckBox()),
                    new algorithm.Data( LocalDate.of(2015, Month.JANUARY, 14), 10.0, 20.0, 30.0,new CheckBox()),
                    new algorithm.Data( LocalDate.of(2015, Month.JANUARY, 15), 40.0, 50.0, 60.0,new CheckBox()),
                    new algorithm.Data( LocalDate.of(2015, Month.JANUARY, 16), 10.0, 20.0, 30.0,new CheckBox()),
                    new algorithm.Data( LocalDate.of(2015, Month.JANUARY, 17), 40.0, 50.0, 60.0,new CheckBox()),
                    new algorithm.Data( LocalDate.of(2015, Month.JANUARY, 18), 10.0, 20.0, 30.0,new CheckBox()),
                    new algorithm.Data( LocalDate.of(2015, Month.JANUARY, 19), 40.0, 50.0, 60.0,new CheckBox()),
                    new algorithm.Data( LocalDate.of(2015, Month.JANUARY, 20), 10.0, 20.0, 30.0,new CheckBox()),
                    new algorithm.Data( LocalDate.of(2015, Month.JANUARY, 21), 40.0, 50.0, 60.0,new CheckBox()),
                    new algorithm.Data( LocalDate.of(2015, Month.JANUARY, 22), 10.0, 20.0, 30.0,new CheckBox())
            );


    private final ObservableList<algorithm.Data> data2 =
            FXCollections.observableArrayList(
                    new algorithm.Data( LocalDate.of(2015, Month.JANUARY, 10), 10.0, 20.0, 30.0,new CheckBox()),
                    new algorithm.Data( LocalDate.of(2015, Month.JANUARY, 11), 40.0, 50.0, 60.0,new CheckBox()),
                    new algorithm.Data( LocalDate.of(2015, Month.JANUARY, 12), 10.0, 20.0, 30.0,new CheckBox()),
                    new algorithm.Data( LocalDate.of(2015, Month.JANUARY, 13), 40.0, 50.0, 60.0,new CheckBox()),
                    new algorithm.Data( LocalDate.of(2015, Month.JANUARY, 14), 10.0, 20.0, 30.0,new CheckBox()),
                    new algorithm.Data( LocalDate.of(2015, Month.JANUARY, 15), 40.0, 50.0, 60.0,new CheckBox()),
                    new algorithm.Data( LocalDate.of(2015, Month.JANUARY, 16), 10.0, 20.0, 30.0,new CheckBox()),
                    new algorithm.Data( LocalDate.of(2015, Month.JANUARY, 17), 40.0, 50.0, 60.0,new CheckBox()),
                    new algorithm.Data( LocalDate.of(2015, Month.JANUARY, 18), 10.0, 20.0, 30.0,new CheckBox()),
                    new algorithm.Data( LocalDate.of(2015, Month.JANUARY, 19), 40.0, 50.0, 60.0,new CheckBox()),
                    new algorithm.Data( LocalDate.of(2015, Month.JANUARY, 20), 10.0, 20.0, 30.0,new CheckBox()),
                    new algorithm.Data( LocalDate.of(2015, Month.JANUARY, 21), 40.0, 50.0, 60.0,new CheckBox()),
                    new algorithm.Data( LocalDate.of(2015, Month.JANUARY, 22), 10.0, 20.0, 30.0,new CheckBox())
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


        TableColumn<algorithm.Data, LocalDate> dateCol = new TableColumn<>("Date");
        dateCol.setPrefWidth(120);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<algorithm.Data, Double> value1Col = new TableColumn<>("Value 1");
        value1Col.setPrefWidth(90);
        value1Col.setCellValueFactory(new PropertyValueFactory<>("value1"));

        TableColumn<algorithm.Data, Double> value2Col = new TableColumn<>("Value 2");
        value2Col.setPrefWidth(90);
        value2Col.setCellValueFactory(new PropertyValueFactory<>("value2"));


        TableColumn<algorithm.Data, Double> value3Col = new TableColumn<>("Value 3");
        value3Col.setPrefWidth(90);
        value3Col.setCellValueFactory(new PropertyValueFactory<>("value3"));

        TableColumn<algorithm.Data,CheckBox> value4Col = new TableColumn<>("删除");
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

/*
package frame;
*/
/**
 * Created by zzh on 2016/12/7.
 *//*





import java.awt.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import com.smooth.gui.SmoothGUI;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MianFrame extends Application {

    private  TableView<Data> trianTable = new TableView<>();
    private  TableView<Data> testTable = new TableView<>();
    private double[][] TestSet;
    private double[][] trainSet;
    private  ObservableList<Data> data =
            FXCollections.observableArrayList(
                    new Data( LocalDate.of(2015, Month.JANUARY, 10), 10.0, 20.0, 30.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 11), 40.0, 50.0, 60.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 12), 10.0, 20.0, 30.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 13), 40.0, 50.0, 60.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 14), 10.0, 20.0, 30.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 15), 40.0, 50.0, 60.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 16), 10.0, 20.0, 30.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 17), 40.0, 50.0, 60.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 18), 10.0, 20.0, 30.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 19), 40.0, 50.0, 60.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 20), 10.0, 20.0, 30.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 21), 40.0, 50.0, 60.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 22), 10.0, 20.0, 30.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 23), 40.0, 50.0, 60.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 24), 10.0, 20.0, 30.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 25), 40.0, 50.0, 60.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 26), 10.0, 20.0, 30.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 27), 40.0, 50.0, 60.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 28), 10.0, 20.0, 30.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 30), 10.0, 20.0, 30.0)

            );

    public static void main(String[] args) {
        //System.setProperty( "javafx.userAgentStylesheetUrl", "CASPIAN" );
        MianFrame.launch();
    }


    @Override
    public void start(Stage stage) {

        stage.setTitle("算法软件");

        Scene scene = new Scene(new VBox(), Toolkit.getDefaultToolkit().getScreenSize().getWidth(), Toolkit.getDefaultToolkit().getScreenSize().getHeight());

        MenuBar menuBar = new MenuBar();
        VBox vbox = new VBox();

        // --- Menu File
        Menu menuFile = new Menu("文件");

        // --- Menu Edit
        Menu menuPre = new Menu("预处理");

        // --- Menu analyze
        Menu menuAnal = new Menu("分析算法");

        // --- Menu Classification
        Menu menuClassif = new Menu("分类算法");

        // --- Menu Regression
        Menu menuRegression = new Menu("回归算法");

        // --- Menu Maping
        Menu menuMaping = new Menu("Maping");

        //添加数据预处理菜单
        MenuItem menuItemPre = new MenuItem("数据预处理");
        menuPre.getItems().addAll(menuItemPre);

        //添加文件子菜单
        MenuItem addTrainSet = new MenuItem("打开训练集");
        MenuItem addTestSet = new MenuItem("打开测试集");
        MenuItem saveTrainSet = new MenuItem("保存训练集");
        MenuItem saveTestSet = new MenuItem("保存测试集");

        menuFile.getItems().addAll(addTrainSet,addTestSet,saveTrainSet,saveTestSet);

        //添加分析算法子菜单
        MenuItem addPLS = new MenuItem("偏最小二乘");
        MenuItem addPCA = new MenuItem("主成分分析");
        menuAnal.getItems().addAll(addPCA,addPLS);

        //添加分类算法子菜单
        MenuItem addSVM = new MenuItem("支持向量机");
        MenuItem addKNN = new MenuItem("最近邻");
        MenuItem addHCA = new MenuItem("层次聚类");
        menuClassif.getItems().addAll(addSVM,addKNN,addHCA);

        //添加回归算法子菜单
        MenuItem addBPNN = new MenuItem("人工神经网络");
        menuRegression.getItems().addAll(addBPNN);

        //添加Maping子菜单
        MenuItem addPrintPic = new MenuItem("画图");
        MenuItem addKmeans = new MenuItem("K-聚类");
        menuMaping.getItems().addAll(addPrintPic,addKmeans);

        //添加各菜单到菜单栏

        menuBar.getMenus().addAll(menuFile,menuPre,menuAnal,menuClassif,menuRegression,menuMaping);
        menuBar.autosize();

        //Action事件监听
        menuItemPre.setOnAction((ActionEvent t) -> {
            new SmoothGUI();
        });


       addTestSet.setOnAction((ActionEvent t) -> {
           final Label trianlabel = new Label("测试集");
           trianlabel.setFont(new Font("Arial", 20));

           trianTable.setEditable(true);

           TableColumn[] test = new TableColumn[100];
           for(int i = 0;i<99;i++){
               test[i] = new TableColumn(i+"");
               trianTable.getColumns().add(test[i]);
           }
           TableColumn firstNameCol = new TableColumn("First Name");
           TableColumn lastNameCol = new TableColumn("Last Name");
           TableColumn emailCol = new TableColumn("Email");

           //trianTable.getColumns().addAll(test);
           vbox.setSpacing(5);
           vbox.setPadding(new Insets(10, 10, 10, 10));
           vbox.getChildren().add(trianlabel);
           vbox.getChildren().add( trianTable);
           vbox.setVisible(true);
        });

        addTrainSet.setOnAction((ActionEvent t) -> {
            final Label testlabel = new Label("训练集");
            testlabel.setFont(new Font("Arial", 20));

            testTable.setEditable(true);

            TableColumn firstNameCol = new TableColumn("First Name");
            TableColumn lastNameCol = new TableColumn("Last Name");
            TableColumn emailCol = new TableColumn("Email");

            testTable.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
           // vbox.setSpacing(5);
          //  vbox.setPadding(new Insets(10, 10, 10, 10));
            vbox.getChildren().add(testlabel);
            vbox.getChildren().add( testTable);
            vbox.setVisible(true);
        });


        // setup table columns
        setupTableColumns( testTable);
        setupTableColumns( trianTable);

        // fill tables with data
        testTable.setItems(data);
        trianTable.setItems(data);
        testTable.setTableMenuButtonVisible(true);
        trianTable.setTableMenuButtonVisible(true);

        // create container
        VBox vBox = new VBox();
        vBox.getChildren().addAll( trianTable,testTable);


        ((VBox) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();

        ScrollBar table1HorizontalScrollBar = findScrollBar( trianTable, Orientation.HORIZONTAL);
        ScrollBar table1VerticalScrollBar = findScrollBar( trianTable, Orientation.VERTICAL);

        // this doesn't work:
        //table1HorizontalScrollBar.setVisible(true);
       // table1VerticalScrollBar.setVisible(true);

        ScrollBar table2HorizontalScrollBar = findScrollBar( testTable, Orientation.HORIZONTAL);
        ScrollBar table2VerticalScrollBar = findScrollBar( testTable, Orientation.VERTICAL);

        // this doesn't work:
      //  table2HorizontalScrollBar.setVisible(true);
      //  table2VerticalScrollBar.setVisible(true);

        // enforce layout to see if anything has an effect
     //   VirtualFlow flow1 = (VirtualFlow)  trianTable.lookup(".virtual-flow");
    //    flow1.requestLayout();

     //   VirtualFlow flow2 = (VirtualFlow) testTable.lookup(".virtual-flow");
      //  flow2.requestLayout();
    }

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

    */
/**
 * Primary table column mapping.
 *//*

    private void setupTableColumns( TableView table) {


        TableColumn<algorithm.Data, LocalDate> dateCol = new TableColumn<>("Date");
        dateCol.setPrefWidth(120);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<algorithm.Data, Double> value1Col = new TableColumn<>("Value 1");
        value1Col.setPrefWidth(90);
        value1Col.setCellValueFactory(new PropertyValueFactory<>("value1"));

        TableColumn<algorithm.Data, Double> value2Col = new TableColumn<>("Value 2");
        value2Col.setPrefWidth(90);
        value2Col.setCellValueFactory(new PropertyValueFactory<>("value2"));


        TableColumn<algorithm.Data, Double> value3Col = new TableColumn<>("Value 3");
        value3Col.setPrefWidth(90);
        value3Col.setCellValueFactory(new PropertyValueFactory<>("value3"));

        TableColumn<algorithm.Data, Double> value4Col = new TableColumn<>("Value 4");
        value4Col.setPrefWidth(90);
        value4Col.setCellValueFactory(new PropertyValueFactory<>("value4"));

        TableColumn<algorithm.Data, Double> value5Col = new TableColumn<>("Value 5");
        value5Col.setPrefWidth(90);
        value5Col.setCellValueFactory(new PropertyValueFactory<>("value5"));

        TableColumn<algorithm.Data, Double> value6Col = new TableColumn<>("Value 6");
        value6Col.setPrefWidth(90);
        value6Col.setCellValueFactory(new PropertyValueFactory<>("value6"));

        TableColumn<algorithm.Data, Double> value7Col = new TableColumn<>("Value 7");
        value7Col.setPrefWidth(90);
        value7Col.setCellValueFactory(new PropertyValueFactory<>("value7"));

        TableColumn<algorithm.Data, Double> value8Col = new TableColumn<>("Value 8");
        value8Col.setPrefWidth(90);
        value8Col.setCellValueFactory(new PropertyValueFactory<>("value8"));

        TableColumn<algorithm.Data, Double> value9Col = new TableColumn<>("Value 9");
        value9Col.setPrefWidth(90);
        value9Col.setCellValueFactory(new PropertyValueFactory<>("value9"));

        table.getColumns().addAll( dateCol, value1Col, value2Col, value3Col,value4Col, value5Col, value6Col,value7Col, value8Col, value9Col);


    }
}

*/
