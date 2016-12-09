package frame;
/**
 * 算法主界面
 * Created by zzh on 2016/12/7.
 */

import com.smooth.gui.SmoothGUI;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MianFrame extends Application {


    private JTable trianTable, testTable;
    private DefaultTableModel trianTableModel, testTableModel;
    private JScrollPane trianScroll, testScroll;
    private Button deleteTrain = new Button("删除");
    private Button deleteTest = new Button("删除");
    private SwingNode trianSwingNode, testSwingNode;
    VBox trianBox = new VBox();
    VBox testBox = new VBox();
    Label trianLabel, testLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double high = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        stage.setWidth(width);
        stage.setHeight(high);
        stage.setTitle("算法软件");

        Scene scene = new Scene(new VBox());
        MenuBar menuBar = new MenuBar();
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
        menuFile.getItems().addAll(addTrainSet, addTestSet, saveTrainSet, saveTestSet);
        //添加分析算法子菜单
        javafx.scene.control.MenuItem addPLS = new javafx.scene.control.MenuItem("偏最小二乘");
        javafx.scene.control.MenuItem addPCA = new javafx.scene.control.MenuItem("主成分分析");
        menuAnal.getItems().addAll(addPCA, addPLS);
        //添加分类算法子菜单
        javafx.scene.control.MenuItem addSVM = new javafx.scene.control.MenuItem("支持向量机");
        javafx.scene.control.MenuItem addKNN = new javafx.scene.control.MenuItem("最近邻");
        javafx.scene.control.MenuItem addHCA = new javafx.scene.control.MenuItem("层次聚类");
        menuClassif.getItems().addAll(addSVM, addKNN, addHCA);
        //添加回归算法子菜单
        javafx.scene.control.MenuItem addBPNN = new javafx.scene.control.MenuItem("人工神经网络");
        menuRegression.getItems().addAll(addBPNN);
        //添加Maping子菜单
        javafx.scene.control.MenuItem addPrintPic = new javafx.scene.control.MenuItem("画图");
        javafx.scene.control.MenuItem addKmeans = new javafx.scene.control.MenuItem("K-聚类");
        menuMaping.getItems().addAll(addPrintPic, addKmeans);
        //添加各菜单到菜单栏
        menuBar.getMenus().addAll(menuFile, menuPre, menuAnal, menuClassif, menuRegression, menuMaping);
        menuBar.autosize();

        // create container
        trianLabel = new Label("\r\n" + "  训练集");
        trianLabel.setFont(new javafx.scene.text.Font("Arial", 20));
        testLabel = new Label("\r\n" + "   测试集");
        testLabel.setFont(new javafx.scene.text.Font("Arial", 20));
        vbox.setSpacing(5);
        vbox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        vbox.setSpacing(5);
        vbox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        String[] columnNames = {"A", "B"};   //列名
        String[][] tableVales = {{"A1", "B1"}, {"A2", "B2"}, {"A3", "B3"}, {"A4", "B4"}, {"A5", "B5"}}; //数据


        /**
         * 数据预处理
         */
        menuItemPre.setOnAction((ActionEvent t) -> {
            new SmoothGUI();
        });

        /**
         * 打开训练集
         */
        addTrainSet.setOnAction((ActionEvent t) -> {
            initTable(columnNames,tableVales,1);
            vbox.setVisible(true);
        });

        /**
         * 打开测试集
         */
        addTestSet.setOnAction((ActionEvent t) -> {
            initTable(columnNames,tableVales,2);
            vbox.setVisible(true);
        });

        /**
         * 删除训练集
         */
        deleteTrain.setOnAction((ActionEvent t) -> {
            int selectedRow = trianTable.getSelectedRow();//获得选中行的索引
            if (selectedRow != -1)  //存在选中行
            {
                trianTableModel.removeRow(selectedRow);  //删除行
            }
        });

        /**
         * 删除测试集
         */
        deleteTest.setOnAction((ActionEvent t) -> {
            int selectedRow = trianTable.getSelectedRow();//获得选中行的索引
            if (selectedRow != -1)  //存在选中行
            {
                trianTableModel.removeRow(selectedRow);  //删除行
            }
        });

        ((VBox) scene.getRoot()).getChildren().addAll(menuBar, trianBox, testBox);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * 表格初始化方法
     * @param columnNames
     * @param tableVales
     * @param type
     */
    public void initTable(String[] columnNames, String[][] tableVales, int type) {
        if (type == 1) {
            trianTableModel = new DefaultTableModel(tableVales, columnNames);
            trianTable = new JTable(trianTableModel);
            trianScroll = new JScrollPane(trianTable);
            trianSwingNode = new SwingNode();
            trianSwingNode.setContent(trianScroll);
            trianBox.getChildren().addAll(trianLabel, trianSwingNode, deleteTrain);
        } else {
            testTableModel = new DefaultTableModel(tableVales, columnNames);
            testTable = new JTable(testTableModel);
            testScroll = new JScrollPane(testTable);
            testSwingNode = new SwingNode();
            testSwingNode.setContent(testScroll);
            testBox.getChildren().addAll(testLabel, testSwingNode, deleteTest);
        }
    }

}
