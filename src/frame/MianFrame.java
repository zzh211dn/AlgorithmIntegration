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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.FileAction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MianFrame extends Application {


    private JTable trianTable, testTable;
    private DefaultTableModel trianTableModel, testTableModel;
    private JScrollPane trianScroll, testScroll;
    private SwingNode trianSwingNode, testSwingNode;
    VBox trianBox = new VBox();
    VBox testBox = new VBox();
    HBox trianLabelBox;
    HBox testLabelBox;
    Label trianLabel, testLabel;
    String[] trianColumnNames, testColumnNames;
    String[][] trianTableVales, testTableVales;
    List<String[]> zyzData;
    private HashMap<String, List<String[]>> trianFileData;
    private HashMap<String, List<String[]>> testFileData;
    FileAction fileAction = new FileAction();
    Button trianAddButton = new Button("添加");
    Button testAddButton = new Button("添加");
    static HashMap<String, String> dataDir;

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
            dataDir = new HashMap<>();
            zyzData = new LinkedList<>();
            String dirName = "";
            FileChooser fileChooser = new FileChooser();
            configureOpenFileChooser(fileChooser);
            java.util.List<File> fileList = fileChooser.showOpenMultipleDialog(stage);

            if (fileList != null)
                dirName = fileList.get(0).getParentFile().getAbsolutePath();
            initLabel(dirName);
            trianFileData = new HashMap<>();
            if (initChooserData(fileList, trianFileData, dirName)) {
                trianTableVales = map2Array(trianFileData, 1, dirName);
                trianColumnNames = initCSVColumnNames(trianFileData);
                trianAddButton.setDisable(false);
            } else {
                trianTableVales = array2Array(zyzData);
                trianColumnNames = initZYZColumnNames(zyzData);
                trianAddButton.setDisable(true);
            }
            initTable(trianColumnNames, trianTableVales, 1);
            vbox.setVisible(true);
        });

        /**
         * 打开测试集
         */
        addTestSet.setOnAction((ActionEvent t) -> {
            zyzData = new LinkedList<>();
            FileChooser fileChooser = new FileChooser();
            configureOpenFileChooser(fileChooser);
            java.util.List<File> fileList = fileChooser.showOpenMultipleDialog(stage);
            testFileData = new HashMap<>();
            if (initChooserData(fileList, testFileData, "")) {
                testTableVales = map2Array(testFileData, 2, "");
                testColumnNames = initCSVColumnNames(testFileData);
                testAddButton.setDisable(false);
            } else {
                testTableVales = array2Array(zyzData);
                testColumnNames = initZYZColumnNames(zyzData);
                testAddButton.setDisable(true);
            }
            initTable(testColumnNames, testTableVales, 2);
            vbox.setVisible(true);
        });

        /**
         * 添加训练集
         */
        trianAddButton.setOnAction(event -> {
            String dirName = "";
            FileChooser fileChooser = new FileChooser();
            configureOpenFileChooser(fileChooser);
            java.util.List<File> fileList = fileChooser.showOpenMultipleDialog(stage);
            if (fileList != null)
                dirName = fileList.get(0).getParentFile().getAbsolutePath();
            initLabel(dirName);
            if (trianFileData == null)
                trianFileData = new HashMap<>();
            if (initChooserData(fileList, trianFileData, dirName)) {
                trianTableVales = map2Array(trianFileData, 1, dirName);
                trianColumnNames = initCSVColumnNames(trianFileData);
            } else {
                trianTableVales = array2Array(zyzData);
                trianColumnNames = initZYZColumnNames(zyzData);
            }
            initTable(trianColumnNames, trianTableVales, 1);
        });

        /**
         * 添加测试集
         */
        testAddButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            configureOpenFileChooser(fileChooser);
            java.util.List<File> fileList = fileChooser.showOpenMultipleDialog(stage);
            String dirName = fileChooser.getInitialDirectory().getAbsolutePath();
            initLabel(dirName);
            if (testFileData == null)
                testFileData = new HashMap<>();
            if (initChooserData(fileList, testFileData, "")) {
                testTableVales = map2Array(testFileData, 2, "");
                testColumnNames = initCSVColumnNames(testFileData);
            } else {
                testTableVales = array2Array(zyzData);
                testColumnNames = initZYZColumnNames(zyzData);

            }

            initTable(testColumnNames, testTableVales, 2);
        });

        /**
         * 保存测试集
         */
        saveTrainSet.setOnAction(event -> {
            FileChooser fileSaveChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                    "zyz 文件 (*.zyz)", "*.zyz");
            fileSaveChooser.getExtensionFilters().add(extFilter);

            File file = fileSaveChooser.showSaveDialog(stage);
            if (file != null) {
                fileAction.saveData(file, trianTableVales);
            }
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
     * @param type        1为训练集 2为测试集
     */
    public void initTable(String[] columnNames, String[][] tableVales, int type) {


        if (type == 1) {
            if (trianBox.getChildren().size() != 0)
                trianBox.getChildren().clear();
            trianLabelBox = new HBox();
            trianLabel = new Label("  训练集   ");
            trianLabel.setFont(new javafx.scene.text.Font("Arial", 20));
            trianLabelBox.getChildren().addAll(trianLabel, trianAddButton);
            trianLabelBox.setSpacing(5);
            trianLabelBox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

            trianTableModel = new DefaultTableModel(tableVales, columnNames);
            trianTable = new JTable(trianTableModel);
            trianTable.setRowHeight(50);
            trianTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            initColumn(trianTable);
            trianScroll = new JScrollPane(trianTable);
            trianScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            trianSwingNode = new SwingNode();
            trianSwingNode.setContent(trianScroll);
            trianBox.getChildren().addAll(trianLabelBox, trianSwingNode);
        } else {
            if (testBox.getChildren().size() != 0)
                testBox.getChildren().clear();
            testLabelBox = new HBox();
            testLabel = new Label("   测试集   ");
            testLabel.setFont(new javafx.scene.text.Font("Arial", 20));
            testLabelBox.getChildren().addAll(testLabel, testAddButton);
            testLabelBox.setSpacing(5);
            testLabelBox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
            testTableModel = new DefaultTableModel(tableVales, columnNames);
            testTable = new JTable(testTableModel);
            testTable.setRowHeight(50);
            testTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            initColumn(testTable);
            testScroll = new JScrollPane();
            testScroll.setViewportView(testTable);
            testScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            testSwingNode = new SwingNode();

            testSwingNode.setContent(testScroll);
            testBox.getChildren().addAll(testLabelBox, testSwingNode);
        }
    }

    /**
     * 初始化文件打开选择器
     *
     * @param fileChooser
     */
    private static void configureOpenFileChooser(
            final FileChooser fileChooser) {
        fileChooser.setTitle("文件选择");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("csv文件,", "*.csv"),
                new FileChooser.ExtensionFilter("zyz文件,", "*.zyz"),
                new FileChooser.ExtensionFilter("txt文件,", "*.txt")
        );
    }


    /**
     * Map数据转为二维数组数据
     *
     * @param fileData
     * @param type     1为训练集数据，2为测试集数据
     * @return 二维表格数据
     */
    private String[][] map2Array(HashMap<String, List<String[]>> fileData, int type, String dirName) {
        int mapLength = fileData.size();
        Iterator iterator = fileData.values().iterator();
        List<String[]> sigData = (List<String[]>) iterator.next();
        int fileLength = sigData.size();

        String[][] tableData = new String[mapLength][fileLength + 4];
        Iterator ite = fileData.values().iterator();
        // tableData = new String[fileLength][dataLength];
        int i = 0;
        while (ite.hasNext()) {
            List<String[]> list = (List<String[]>) ite.next();
            Iterator listIte = list.iterator();
            listIte.next();
            for (int j = 4; j <= fileLength + 3; j++) {

                if (listIte.hasNext()) {
                    String[] data = (String[]) listIte.next();
                    tableData[i][j] = data[1];

                }
            }
            i++;
        }
        Iterator iteName = fileData.keySet().iterator();
        int n = 0;
        while (iteName.hasNext()) {
            tableData[n][0] = n + 1 + "";
            tableData[n][1] = (String) iteName.next();
            List<String[]> name = (List<String[]>) fileData.get(tableData[n][1]);

            String[] fileName = name.iterator().next();
            String label = dataDir.get(fileName[1]);
            tableData[n][2] = label;
            n++;
        }

        return tableData;
    }

    /**
     * zyz文件数组数据转为二维表格数据
     *
     * @param zyzData
     * @return
     */
    private String[][] array2Array(List<String[]> zyzData) {
        Iterator temp = zyzData.iterator();
        int listLength = zyzData.size();
        int dataLength = ((String[]) temp.next()).length;
        String[][] fileData = new String[listLength][dataLength];
        Iterator iterator = zyzData.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            String[] data = (String[]) iterator.next();
            for (int j = 0; j < data.length; j++) {
                fileData[i][j] = data[j];
            }
            i++;
        }
        return fileData;

    }

    /**
     * 从fileList读取数据
     *
     * @param fileList
     */
    private boolean initChooserData(List<File> fileList, HashMap<String, List<String[]>> fileData, String dirName) {
        if (fileList != null)
            if (fileList.get(0).getName().endsWith(".csv") | fileList.get(0).getName().endsWith(".txt") | fileList.get(0).getName().endsWith(".CSV") | fileList.get(0).getName().endsWith(".TXT")) {
                for (int i = 0; i < fileList.size(); i++) {
                    try {
                        fileAction.readCSVData(fileList.get(i), fileData, dirName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            } else {
                try {
                    zyzData = fileAction.readZYZData(fileList.get(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
        return false;
    }

    /**
     * 生成标签数据
     *
     * @param dirName
     */
    private void initLabel(String dirName) {
        if (!dataDir.containsKey(dirName)) {
            dataDir.put(dirName, dataDir.size() + 1 + "");
        }
    }

    /**
     * 初始化CSV和TXT文件列名
     *
     * @param fileData
     * @return
     */
    private String[] initCSVColumnNames(HashMap<String, List<String[]>> fileData) {
        Iterator iterator = fileData.values().iterator();
        List<String[]> sigData = (List<String[]>) iterator.next();
        int fileLength = sigData.size();
        String[] initColumnName = new String[fileLength + 4];
        initColumnName[0] = "序号";
        initColumnName[1] = "文件名";
        initColumnName[2] = "实际值";
        initColumnName[3] = "目标值";
        for (int i = 4; i < fileLength + 4; i++) {
            initColumnName[i] = i - 3 + "";
        }
        return initColumnName;
    }

    /**
     * 初始化算法计算结果列名
     */

    private String[] initAlgorithmColumnNames(String[][] data){
        int columnLength = data[0].length;
        String[] initColumnName = new String[columnLength];
        initColumnName[0] = "序号";
        initColumnName[1] = "文件名";
        initColumnName[2] = "实际值";
        initColumnName[3] = "目标值";
        for (int i = 4; i < columnLength; i++) {
            initColumnName[i] = i - 3 + "";
        }
        return initColumnName;
    }

    /**
     * 初始化ZYZ文件列名
     *
     * @param zyzData
     * @return
     */
    private String[] initZYZColumnNames(List<String[]> zyzData) {
        Iterator iterator = zyzData.iterator();
        String[] sigData = (String[]) iterator.next();
        String[] initColumnName = new String[sigData.length];
        initColumnName[0] = "序号";
        initColumnName[1] = "文件名";
        initColumnName[2] = "实际值";
        initColumnName[3] = "目标值";
        for (int i = 4; i < sigData.length; i++) {
            initColumnName[i] = i - 3 + "";
        }
        return initColumnName;
    }

    /**
     * 初始化列属性
     *
     * @param table
     */
    private void initColumn(JTable table) {
        TableColumn col;
        TableColumn colNum = table.getColumn("序号");
        colNum.setMinWidth(40);
        colNum.setPreferredWidth(20);
        colNum.setMaxWidth(50);

        TableColumn colFile = table.getColumn("文件名");
        colFile.setMinWidth(50);
        colFile.setPreferredWidth(100);
        colFile.setMaxWidth(200);

        TableColumn colLab = table.getColumn("实际值");
        colLab.setMinWidth(40);
        colLab.setPreferredWidth(20);
        colLab.setMaxWidth(50);

        TableColumn colRes = table.getColumn("目标值");
        colRes.setMinWidth(50);
        colRes.setPreferredWidth(50);
        colRes.setMaxWidth(100);
        for (int i = 1; i < table.getColumnCount() - 4; i++) {
            col = table.getColumn(i + "");

            col.setMinWidth(50);
            col.setPreferredWidth(50);
            col.setMaxWidth(100);


        }

    }

    /**
     * 表格数据导出
     * @param type 1为训练集，其他为测试集
     * @return 表格数据
     */
    public String[][] dataOut(int type){
        if(type==1)
            return trianTableVales;
        else
            return testTableVales;
    }

    /**
     * 算法结果导入表格
     * @param data 计算结果
     * @param type 1为训练集，其他为测试集
     */
    public void dataIn(String[][] data,int type){
        String[] columnName = initAlgorithmColumnNames(data);
        if(type==1) {

            initTable(columnName, data, 1);
        }
        else {
            initTable(columnName, data, 2);
        }
    }

}
