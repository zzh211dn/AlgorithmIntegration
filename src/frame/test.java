package frame;

import java.awt.*;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.stage.Stage;

import com.sun.javafx.scene.control.skin.VirtualFlow;

public class test extends Application {

    private TableView<Data> table1 = new TableView<>(); // table with scrollbars
    private TableView<Data> table2 = new TableView<>(); // table without scrollbars

    private final ObservableList<Data> data =
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
                    new Data( LocalDate.of(2015, Month.JANUARY, 29), 40.0, 50.0, 60.0),
                    new Data( LocalDate.of(2015, Month.JANUARY, 30), 10.0, 20.0, 30.0)

            );

    final HBox hb = new HBox();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {



        stage.setTitle("Table View Sample");
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

        // setup table columns
        setupTableColumns( table1);
        setupTableColumns( table2);

        // fill tables with data
        table1.setItems(data);
        table2.setItems(data);
        table1.setTableMenuButtonVisible(true);

        // create container
        final Label trianLabel = new Label("\r\n"+"训练集");
        trianLabel.setFont(new javafx.scene.text.Font("Arial", 20));
        final Label testLabel = new Label("\r\n"+"测试集");
        testLabel.setFont(new javafx.scene.text.Font("Arial", 20));
        VBox vBox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        table1.setMaxSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth(),Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
        table2.setMaxSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth(),Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
        vBox.getChildren().addAll(trianLabel,table1,testLabel,table2);

        ((VBox) scene.getRoot()).getChildren().addAll(menuBar,vBox);

        stage.setScene(scene);
        stage.show();

        ScrollBar table1HorizontalScrollBar = findScrollBar( table1, Orientation.HORIZONTAL);
        ScrollBar table1VerticalScrollBar = findScrollBar( table1, Orientation.VERTICAL);

        // this doesn't work:
        table1HorizontalScrollBar.setVisible(true);
        table1VerticalScrollBar.setVisible(true);

        ScrollBar table2HorizontalScrollBar = findScrollBar( table2, Orientation.HORIZONTAL);
        ScrollBar table2VerticalScrollBar = findScrollBar( table2, Orientation.VERTICAL);

        // this doesn't work:
        table2HorizontalScrollBar.setVisible(true);
        table2VerticalScrollBar.setVisible(true);

        // enforce layout to see if anything has an effect
        VirtualFlow flow1 = (VirtualFlow) table1.lookup(".virtual-flow");
        flow1.requestLayout();

        VirtualFlow flow2 = (VirtualFlow) table2.lookup(".virtual-flow");
        flow2.requestLayout();

    }

    /**
     * Primary table column mapping.
     */
    private void setupTableColumns( TableView table) {


        TableColumn<Data, LocalDate> dateCol = new TableColumn<>("Date");
        dateCol.setPrefWidth(120);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Data, Double> value1Col = new TableColumn<>("Value 1");
        value1Col.setPrefWidth(90);
        value1Col.setCellValueFactory(new PropertyValueFactory<>("value1"));

        TableColumn<Data, Double> value2Col = new TableColumn<>("Value 2");
        value2Col.setPrefWidth(90);
        value2Col.setCellValueFactory(new PropertyValueFactory<>("value2"));


        TableColumn<Data, Double> value3Col = new TableColumn<>("Value 3");
        value3Col.setPrefWidth(90);
        value3Col.setCellValueFactory(new PropertyValueFactory<>("value3"));

        TableColumn<Data, Double> value4Col = new TableColumn<>("Value 4");
        value4Col.setPrefWidth(90);
        value4Col.setCellValueFactory(new PropertyValueFactory<>("value4"));

        TableColumn<Data, Double> value5Col = new TableColumn<>("Value 5");
        value5Col.setPrefWidth(90);
        value5Col.setCellValueFactory(new PropertyValueFactory<>("value5"));

        TableColumn<Data, Double> value6Col = new TableColumn<>("Value 6");
        value6Col.setPrefWidth(90);
        value6Col.setCellValueFactory(new PropertyValueFactory<>("value6"));

        TableColumn<Data, Double> value7Col = new TableColumn<>("Value 7");
        value7Col.setPrefWidth(90);
        value7Col.setCellValueFactory(new PropertyValueFactory<>("value7"));

        TableColumn<Data, Double> value8Col = new TableColumn<>("Value 8");
        value8Col.setPrefWidth(90);
        value8Col.setCellValueFactory(new PropertyValueFactory<>("value8"));

        TableColumn<Data, Double> value9Col = new TableColumn<>("Value 9");
        value9Col.setPrefWidth(90);
        value9Col.setCellValueFactory(new PropertyValueFactory<>("value9"));

        TableColumn<Data,CheckBox> value10Col = new TableColumn<>("box");
        value10Col.setPrefWidth(90);
        value10Col.setCellValueFactory(new PropertyValueFactory<>("value9"));

        table.getColumns().addAll( dateCol, value1Col, value2Col, value3Col,value10Col, value5Col, value6Col,value7Col, value8Col, value9Col);


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

    /**
     * Data for primary table rows.
     */
    public static class Data {

        private  ObjectProperty<LocalDate> date;
        private  SimpleDoubleProperty value1;
        private  SimpleDoubleProperty value2;
        private  SimpleDoubleProperty value3;
        private CheckBox box = new CheckBox();


        public Data( LocalDate date, double value1, double value2, double value3) {

            this.date = new SimpleObjectProperty<LocalDate>( date);

            this.value1 = new SimpleDoubleProperty( value1);
            this.value2 = new SimpleDoubleProperty( value2);
            this.value3 = new SimpleDoubleProperty( value3);

        }

        public final ObjectProperty<LocalDate> dateProperty() {
            return this.date;
        }
        public final LocalDate getDate() {
            return this.dateProperty().get();
        }
        public final void setDate(final LocalDate date) {
            this.dateProperty().set(date);
        }
        public final SimpleDoubleProperty value1Property() {
            return this.value1;
        }
        public final double getValue1() {
            return this.value1Property().get();
        }
        public final void setValue1(final double value1) {
            this.value1Property().set(value1);
        }
        public final SimpleDoubleProperty value2Property() {
            return this.value2;
        }
        public final double getValue2() {
            return this.value2Property().get();
        }
        public final void setValue2(final double value2) {
            this.value2Property().set(value2);
        }
        public final SimpleDoubleProperty value3Property() {
            return this.value3;
        }
        public final double getValue3() {
            return this.value3Property().get();
        }
        public final void setValue3(final double value3) {
            this.value3Property().set(value3);
        }


    }
} 