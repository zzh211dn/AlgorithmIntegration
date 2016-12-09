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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.FileAction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class MianFrame extends Application {


    private JTable trianTable, testTable;
    private DefaultTableModel trianTableModel, testTableModel;
    private JScrollPane trianScroll, testScroll;
    private SwingNode trianSwingNode, testSwingNode;
    VBox trianBox = new VBox();
    VBox testBox = new VBox();
    Label trianLabel, testLabel;
    String[] trianColumnNames,testColumnNames;
    String[][] trianTableVales,testTableVales;
    private HashMap<String,List<String[]>> fileData =  new HashMap<>();

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
            final FileChooser fileChooser = new FileChooser();
            configureFileChooser(fileChooser);
            java.util.List<File> fileList = fileChooser.showOpenMultipleDialog(stage);
            for(int i=0;i<fileList.size();i++){
                try {
                    readData(fileList.get(i));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            HashMap trianData = getFileData();
            trianTableVales = map2Array(trianData);
            trianColumnNames= new String[trianTableVales[0].length];
            for(int i=0;i<trianTableVales[0].length;i++){
                trianColumnNames[i]=i+"";
            }
            initTable(trianColumnNames, trianTableVales, 1);
            vbox.setVisible(true);
        });

        /**
         * 打开测试集
         */
        addTestSet.setOnAction((ActionEvent t) -> {

            final FileChooser fileChooser = new FileChooser();
            configureFileChooser(fileChooser);
            java.util.List<File> fileList = fileChooser.showOpenMultipleDialog(stage);
            for(int i=0;i<fileList.size();i++){
                try {
                    readData(fileList.get(i));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            HashMap testData = getFileData();
            testTableVales = map2Array(testData);
            testColumnNames= new String[testTableVales[0].length];
            for(int i=0;i<testTableVales[0].length;i++){
                testColumnNames[i]=i+"";
            }
            initTable(testColumnNames, testTableVales, 2);
           /* for(int i=0;i<testTableVales[0].length;i++){
                TableColumn tc = testTable.getColumn(i);
                tc.setWidth(50);
                tc.setMinWidth(50);
            }*/
            vbox.setVisible(true);
        });


        ((VBox) scene.getRoot()).getChildren().addAll(menuBar, trianBox, testBox);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * 表格初始化方法
     *
     * @param columnNames
     * @param tableVales
     * @param type
     */
    public void initTable(String[] columnNames, String[][] tableVales, int type) {
        TableColumn col;
        if (type == 1) {

            trianTableModel = new DefaultTableModel(tableVales, columnNames);
            trianTable = new JTable(trianTableModel);
            trianTable.setRowHeight(50);
            trianTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            for (int i = 0; i < trianTable.getColumnCount(); i++)
            {
                col = trianTable.getColumn(i+"");
                col.setMinWidth(50);
                col.setMaxWidth(50);
                col.setPreferredWidth(50);

            }
            trianScroll = new JScrollPane(trianTable);
            trianScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            trianSwingNode = new SwingNode();
            trianSwingNode.setContent(trianScroll);
            trianBox.getChildren().addAll(trianLabel, trianSwingNode);
        } else {
            testTableModel = new DefaultTableModel(tableVales, columnNames);
            testTable = new JTable(testTableModel);
            testTable.setRowHeight(50);
            testTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            for (int i = 0; i < testTable.getColumnCount(); i++)
            {
                col = testTable.getColumn(i+"");
                col.setMinWidth(50);
                col.setPreferredWidth(50);
                col.setMaxWidth(50);
            }
            testScroll = new JScrollPane();
            testScroll.setViewportView(testTable);
            testScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            testSwingNode = new SwingNode();

            testSwingNode.setContent(testScroll);
            testBox.getChildren().addAll(testLabel, testSwingNode);
        }
    }

    private static void configureFileChooser(
            final FileChooser fileChooser) {
        fileChooser.setTitle("文件选择");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("csv格式", "*.csv"),
                new FileChooser.ExtensionFilter("txt格式", "*.txt")
        );
    }

    private void readData(File file) throws IOException {
        BufferedReader read = new BufferedReader(new FileReader(file));
        read.read();
        String line;
        List<String[]> dataList = new ArrayList<>();
        while((line=read.readLine())!=null){
            String[] subLine = line.split(",");
            dataList.add(subLine);
        }
        fileData.put(file.getName(),dataList);
    }

    public HashMap<String, List<String[]>> getFileData() {
        return fileData;
    }

    private String[][] map2Array(HashMap<String,List<String[]>> fileData){
        int mapLength = fileData.size();
        Iterator iterator = fileData.values().iterator();
        List<String[]> sigData = (List<String[]>) iterator.next();
        int fileLength = sigData.size();
        String[][] tableData  = new String[mapLength][fileLength+1];
        Iterator ite = fileData.values().iterator();
        // tableData = new String[fileLength][dataLength];
        int i=0;

        while (ite.hasNext()){
            List<String[]> list = (List<String[]>) ite.next();
            Iterator listIte = list.iterator();
            int j=0;
            while (listIte.hasNext()){
                String[] data = (String[]) listIte.next();
                tableData[i][j] = data[1];
                j++;
            }
            i++;
        }


        return tableData;
    }
}
