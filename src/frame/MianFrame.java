package frame;
/**
 * 算法主界面
 * Created by zzh on 2016/12/7.
 */

import algorithm.Kmeans;
import algorithm.PCA;
import algorithm.PLSpackage.Data;
import algorithm.PLSpackage.PLS_method;
import algorithm.PictureAPI;
import algorithm.algorithmAPI;
import com.smooth.gui.SmoothGUI;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import la.matrix.Matrix;
import model.FileAction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static algorithm.PLSpackage.helpers.predict;
import static algorithm.PLSpackage.helpers.readCSV;
import static algorithm.PLSpackage.helpers.reportAccuracy;

public class MianFrame extends Application {


    private JTable trianTable, testTable, validationTable;
    private DefaultTableModel trianTableModel, testTableModel, validationTableModel;
    private JScrollPane trianScroll, testScroll, validationScroll;
    private SwingNode trianSwingNode, testSwingNode, validationSwingNode;
    VBox trianBox = new VBox();
    VBox testBox = new VBox();
    VBox validationBox = new VBox();
    HBox trianLabelBox;
    HBox testLabelBox;
    HBox validationLabelBox;
    Label trianLabel, testLabel, validationLabel;
    String[] trianColumnNames, testColumnNames, validationColumnNames;
    String[][] trianTableVales, testTableVales, validationTableVales;
    List<String[]> zyzData;
    Double[][] testResult;
    Double[][] trianResult;
    Double[][] validationResult;
    private LinkedHashMap<String, List<String[]>> trianFileData;
    private LinkedHashMap<String, List<String[]>> testFileData;
    private LinkedHashMap<String, List<String[]>> validationFileData;
    FileAction fileAction = new FileAction();
    Button trianAddButton = new Button("添加");
    Button testAddButton = new Button("添加");
    Button validationAddButton = new Button("添加");
    static HashMap<String, String> dataDir = new HashMap<>();
    algorithmAPI algorithmAPI = new algorithmAPI();
    double width;
    double high;
    java.util.List<File> fileList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        high = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
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
        // --- Menu 画图
        javafx.scene.control.Menu menuPic = new javafx.scene.control.Menu("画图");

        //添加画图
        javafx.scene.control.MenuItem addChating = new javafx.scene.control.MenuItem("绘制波形图");
        javafx.scene.control.MenuItem addRectangle = new javafx.scene.control.MenuItem("绘制降维图");
        javafx.scene.control.MenuItem addScatter = new javafx.scene.control.MenuItem("绘制三维图");
        menuPic.getItems().addAll(addChating, addRectangle, addScatter);

        //添加数据预处理菜单
        javafx.scene.control.MenuItem menuItemPre = new javafx.scene.control.MenuItem("数据预处理");
        javafx.scene.control.MenuItem menuItemPre2 = new javafx.scene.control.MenuItem("分高归一化");
        javafx.scene.control.MenuItem canchaItemPre = new javafx.scene.control.MenuItem("学生化残差");
        menuPre.getItems().addAll(menuItemPre,menuItemPre2,canchaItemPre);
        //添加文件子菜单
        javafx.scene.control.MenuItem addTrainSet = new javafx.scene.control.MenuItem("打开训练集");
        javafx.scene.control.MenuItem addTestSet = new javafx.scene.control.MenuItem("打开测试集");
        javafx.scene.control.MenuItem saveTrainSet = new javafx.scene.control.MenuItem("保存训练集");
        javafx.scene.control.MenuItem saveTestSet = new javafx.scene.control.MenuItem("保存测试集");
        javafx.scene.control.MenuItem addValidationSet = new javafx.scene.control.MenuItem("打开验证集");
        javafx.scene.control.MenuItem saveValidationSet = new javafx.scene.control.MenuItem("保存验证集");
        menuFile.getItems().addAll(addTrainSet, addTestSet, addValidationSet, saveTrainSet, saveTestSet, saveValidationSet);
        //添加分析算法子菜单
        javafx.scene.control.MenuItem addPLS = new javafx.scene.control.MenuItem("偏最小二乘");
        javafx.scene.control.MenuItem addPCA = new javafx.scene.control.MenuItem("主成分分析");
        javafx.scene.control.MenuItem addMAD = new javafx.scene.control.MenuItem("马氏距离");
        javafx.scene.control.MenuItem addXLoading = new javafx.scene.control.MenuItem("XLoading");
        menuAnal.getItems().addAll(addPCA, addXLoading, addPLS, addMAD);
        //添加分类算法子菜单
        javafx.scene.control.MenuItem addSVM = new javafx.scene.control.MenuItem("支持向量机");
        javafx.scene.control.MenuItem addKNN = new javafx.scene.control.MenuItem("最近邻");
        javafx.scene.control.MenuItem addHCA = new javafx.scene.control.MenuItem("层次聚类");
        menuClassif.getItems().addAll(addSVM, addKNN, addHCA);
        //添加回归算法子菜单
        javafx.scene.control.MenuItem addBPNN = new javafx.scene.control.MenuItem("人工神经网络");
        javafx.scene.control.MenuItem addPLSForest = new javafx.scene.control.MenuItem("PLS回归");
        menuRegression.getItems().addAll(addBPNN,addPLSForest);
        //添加Maping子菜单
        javafx.scene.control.MenuItem addKmeans = new javafx.scene.control.MenuItem("K-聚类");
        menuMaping.getItems().addAll(addKmeans);
        //添加各菜单到菜单栏
        menuBar.getMenus().addAll(menuFile, menuPre, menuAnal, menuClassif, menuRegression, menuMaping, menuPic);
        menuBar.autosize();

        // create container
        vbox.setSpacing(5);
        vbox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));


        /**
         * 数据预处理
         */
        menuItemPre.setOnAction((ActionEvent t) -> {
            SmoothGUI smoothGUI = new SmoothGUI();
            smoothGUI.setModal(true);
            smoothGUI.show();
        });


        /**
         * 分高归一化
         */
        menuItemPre2.setOnAction((ActionEvent t) -> {
            String dirName = "";
            FileChooser fileChooser = new FileChooser();
            configureOpenFileChooser(fileChooser, 1);
            fileList = fileChooser.showOpenMultipleDialog(stage);
            if (fileList != null)
                dirName = fileList.get(0).getParentFile().getAbsolutePath();
            LinkedHashMap<String, List<String[]>> picFileData = new LinkedHashMap<>();
            initChooserData(fileList, picFileData, "");
            String[] fileListName = new String[fileList.size()];
            for (int i = 0; i < fileList.size(); i++) {
                String name = fileList.get(i).getName();
                fileListName[i] = name;
            }
            ArrayList<Double[][]> picMap = picMap(picFileData);
            if(algorithmAPI.getFenGaoNormalization(picMap,dirName,fileListName))
            {
                //Alert 完成
            }
            else
            {

            }

        });

        /**
         * 学生化残差
         * */

        canchaItemPre.setOnAction((ActionEvent t)->{
            Double[][] data = dataOut(1);
            System.out.println(data.length);
            Double[][] dataLabel = getLabel();
            Double[][] residualsResult = algorithmAPI.getResiduals(data,dataLabel);
            Double[][] transposeResidualResult = new Double[residualsResult.length][2];
            ArrayList<Double[][]> datas = new ArrayList<Double[][]>();

            for(int i = 0;i<transposeResidualResult.length;i++)
            {
                transposeResidualResult[i][1] = residualsResult[i][0];
                transposeResidualResult[i][0] = Double.valueOf(i);
            }
            datas.add(transposeResidualResult);

            PictureAPI pictureAPI = new PictureAPI();
            Stage chartStage = null;
            try {
                chartStage = new Stage();
                chartStage.setScene(pictureAPI.getChatingResult(datas, new String[]{"学生化残差"}, new String[]{"", ""}));
                chartStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });


        /**
         * 打开训练集
         */
        addTrainSet.setOnAction((ActionEvent t) -> {

            zyzData = new LinkedList<>();
            String dirName = "";
            FileChooser fileChooser = new FileChooser();
            configureOpenFileChooser(fileChooser, 1);
            fileList = fileChooser.showOpenMultipleDialog(stage);

            if (fileList != null)
                dirName = fileList.get(0).getParentFile().getAbsolutePath();
            initLabel(dirName);
            trianFileData = new LinkedHashMap<>();
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
            configureOpenFileChooser(fileChooser, 1);
            fileList = fileChooser.showOpenMultipleDialog(stage);
            testFileData = new LinkedHashMap<>();
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
         * 打开验证集
         */
        addValidationSet.setOnAction((ActionEvent t) -> {
            zyzData = new LinkedList<>();
            String dirName = "";
            FileChooser fileChooser = new FileChooser();
            configureOpenFileChooser(fileChooser, 1);
            fileList = fileChooser.showOpenMultipleDialog(stage);

            if (fileList != null)
                dirName = fileList.get(0).getParentFile().getAbsolutePath();
            initLabel(dirName);
            validationFileData = new LinkedHashMap<>();
            if (initChooserData(fileList, validationFileData, dirName)) {
                validationTableVales = map2Array(validationFileData, 1, dirName);
                validationColumnNames = initCSVColumnNames(validationFileData);
                validationAddButton.setDisable(false);
            } else {
                validationTableVales = array2Array(zyzData);
                validationColumnNames = initZYZColumnNames(zyzData);
                validationAddButton.setDisable(true);
            }
            initTable(validationColumnNames, validationTableVales, 3);
            vbox.setVisible(true);
        });

        /**
         * 添加训练集
         */
        trianAddButton.setOnAction(event -> {
            String dirName = "";
            FileChooser fileChooser = new FileChooser();
            configureOpenFileChooser(fileChooser, 1);
            fileList = fileChooser.showOpenMultipleDialog(stage);
            if (fileList != null)
                dirName = fileList.get(0).getParentFile().getAbsolutePath();
            initLabel(dirName);
            if (trianFileData == null)
                trianFileData = new LinkedHashMap<>();
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
            configureOpenFileChooser(fileChooser, 1);
            fileList = fileChooser.showOpenMultipleDialog(stage);
            String dirName = fileChooser.getInitialDirectory().getAbsolutePath();
            initLabel(dirName);
            if (testFileData == null)
                testFileData = new LinkedHashMap<>();
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
         * 添加验证集
         */
        validationAddButton.setOnAction(event -> {
            String dirName = "";
            FileChooser fileChooser = new FileChooser();
            configureOpenFileChooser(fileChooser, 1);
            fileList = fileChooser.showOpenMultipleDialog(stage);
            if (fileList != null)
                dirName = fileList.get(0).getParentFile().getAbsolutePath();
            initLabel(dirName);
            if (validationFileData == null)
                validationFileData = new LinkedHashMap<>();
            if (initChooserData(fileList, validationFileData, dirName)) {
                validationTableVales = map2Array(validationFileData, 1, dirName);
                validationColumnNames = initCSVColumnNames(validationFileData);
            } else {
                validationTableVales = array2Array(zyzData);
                validationColumnNames = initZYZColumnNames(zyzData);
            }
            initTable(validationColumnNames, validationTableVales, 3);
        });

        /**
         * 保存训练集
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
        /**
         * 保存测试集
         */
        saveTestSet.setOnAction(event -> {
            FileChooser fileSaveChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                    "zyz 文件 (*.zyz)", "*.zyz");
            fileSaveChooser.getExtensionFilters().add(extFilter);
            File file = fileSaveChooser.showSaveDialog(stage);
            if (file != null) {
                fileAction.saveData(file, testTableVales);
            }
        });
        /**
         * 保存验证集
         */
        saveTrainSet.setOnAction(event -> {
            FileChooser fileSaveChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                    "zyz 文件 (*.zyz)", "*.zyz");
            fileSaveChooser.getExtensionFilters().add(extFilter);
            File file = fileSaveChooser.showSaveDialog(stage);
            if (file != null) {
                fileAction.saveData(file, validationTableVales);
            }
        });

        /**
         *PCA方法调用
         */
        addPCA.setOnAction(event -> {

            Stage lStage = new Stage();
            GridPane lgrid = new GridPane();
            lgrid.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
            lgrid.setVgap(5);
            lgrid.setHgap(1);
            Scene lScene = new Scene(lgrid, 200, 160);

            final Label pcaLabel = new Label("请输入主成分个数");
            GridPane.setConstraints(pcaLabel, 0, 0);
            lgrid.getChildren().add(pcaLabel);

            final javafx.scene.control.TextField kPca = new javafx.scene.control.TextField();
            kPca.setPromptText("请输入主成分个数");
            GridPane.setConstraints(kPca, 0, 1);
            lgrid.getChildren().add(kPca);

            CheckBox validationCb = new CheckBox("验证集");
            GridPane.setConstraints(validationCb, 0, 2);
            lgrid.getChildren().add(validationCb);
            CheckBox trainCb = new CheckBox("测试集");
            GridPane.setConstraints(trainCb, 0, 3);
            lgrid.getChildren().add(trainCb);


            Button button = new Button("确定");
            GridPane.setConstraints(button, 0, 4);
            lgrid.getChildren().add(button);
            lStage.setScene(lScene);
            lStage.show();

            button.setOnAction(event1 -> {

                Double[][] trianPcaResult = null;
                Double[][] testPcaResult = null;
                Double[][] validationPcaResult = null;


                String[][] testPcaString = null;
                String[][] validationPcaString = null;

                lStage.close();
                int type = 1;
                if (validationCb.isSelected() == true && trainCb.isSelected() == true)
                    type = 2;
                if (validationCb.isSelected() == true && trainCb.isSelected() == false)
                    type = 3;
                if (validationCb.isSelected() == false && trainCb.isSelected() == true)
                    type = 4;
                if (validationCb.isSelected() == false && trainCb.isSelected() == false)
                    type = 5;

                trianPcaResult = algorithmAPI.getPCAResult(dataOut(1), Integer.valueOf(kPca.getText()), 1);
                if (type == 2) {
                    testPcaResult = algorithmAPI.getPCAResult(dataOut(2), Integer.valueOf(kPca.getText()), 2);
                    validationPcaResult = algorithmAPI.getPCAResult(dataOut(6), Integer.valueOf(kPca.getText()), 2);
                    testPcaString = addInf(testPcaResult, 1);
                    validationPcaString = addInf(validationPcaResult, 2);
                }
                if (type == 3) {
                    validationPcaResult = algorithmAPI.getPCAResult(dataOut(6), Integer.valueOf(kPca.getText()), 2);
                    validationPcaString = addInf(validationPcaResult, 2);

                }
                if (type == 4) {
                    testPcaResult = algorithmAPI.getPCAResult(dataOut(2), Integer.valueOf(kPca.getText()), 2);
                    testPcaString = addInf(testPcaResult, 1);
                }

                String[][] label = getFileName();
                String temp = label[0][0].substring(0, label[0][0].lastIndexOf("-"));
                String lableName = temp;
                int m = 0;
                for (; m < label.length; m++) {
                    String a = label[m][0].substring(0, label[m][0].lastIndexOf("-"));
                    if (temp.compareTo(a) != 0)
                        break;
                }
                Double[][] ScattertrianPcaResult = new Double[m][3];
                Double[][] ScattertrianPcaResult2D = new Double[m][2];
                ArrayList<Double[][]> ScattertPcaResult = new ArrayList<Double[][]>();
                ArrayList<Double[][]> ScattertPcaResult2D = new ArrayList<Double[][]>();
                int temprow = 0;
                for (int i = 0; i < trianPcaResult.length; i++) {
                    String a = label[i][0].substring(0, label[i][0].lastIndexOf("-"));
                    if (temp.compareTo(a) != 0) {
                        temprow = i;
                        ScattertPcaResult.add(ScattertrianPcaResult);
                        ScattertPcaResult2D.add(ScattertrianPcaResult2D);
                        temp = a;
                        lableName = lableName + "," + temp;
//                        System.out.println(lableName);
                        int mm = m;
                        for (; m < label.length; m++) {
                            String b = label[m][0].substring(0, label[m][0].lastIndexOf("-"));
//                            System.out.println("b:"+ b );
                            if (temp.compareTo(b) != 0)
                                break;
                        }
                        ScattertrianPcaResult = new Double[m - mm][3];
                        ScattertrianPcaResult2D = new Double[m - mm][2];

                    }

                    for (int j = 0; j < 3; j++) {
                        ScattertrianPcaResult[i - temprow][j] = trianPcaResult[i][j];
                    }
                    for (int j = 0; j < 2; j++) {
                        ScattertrianPcaResult2D[i - temprow][j] = trianPcaResult[i][j];
                    }

                }
                ScattertPcaResult.add(ScattertrianPcaResult);
                ScattertPcaResult2D.add(ScattertrianPcaResult2D);


                String[] lableName1 = lableName.split(",");
                getScatterPicture(ScattertPcaResult, lableName1);
                PictureAPI pictureAPI = new PictureAPI();
                try {
                    Stage chartStage = new Stage();
                    chartStage.setScene(pictureAPI.getScatter2DResult(ScattertPcaResult2D, lableName1, new String[]{"PCA1", "PCA2"}));
                    chartStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String[][] trianPcaString = addInf(trianPcaResult, 0);
                Stage pcaStage = new Stage();
                Scene pcaScence = new Scene(new VBox(), width, high);


                String[] pcaTrianColumnNames = initPCAColumnNames(trianPcaString);
                DefaultTableModel pcaTrianTableModel = new DefaultTableModel(trianPcaString, pcaTrianColumnNames);
                JTable pcaTrianTable = new JTable(pcaTrianTableModel);
                pcaTrianTable.setRowHeight(50);
                pcaTrianTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                Button trianPcaAddButton = new Button("保存");
                HBox trianPcaLabelBox = new HBox();
                Label trianPcaLabel = new Label("  训练集   ");
                trianPcaLabel.setFont(new javafx.scene.text.Font("Arial", 20));
                trianPcaLabelBox.getChildren().addAll(trianPcaLabel, trianPcaAddButton);
                trianPcaLabelBox.setSpacing(5);
                trianPcaLabelBox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));


                initColumn(pcaTrianTable);
                JScrollPane trianPcaScroll = new JScrollPane(pcaTrianTable);
                trianPcaScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                SwingNode trianPcaSwingNode = new SwingNode();
                trianPcaSwingNode.setContent(trianPcaScroll);
                VBox trianPcaBox = new VBox();
                trianPcaBox.getChildren().addAll(trianPcaLabelBox, trianPcaSwingNode);


                ((VBox) pcaScence.getRoot()).getChildren().addAll(trianPcaBox);

                if (type == 2 | type == 4) {
                    String[] pcaTestColumnNames = initPCAColumnNames(testPcaString);
                    DefaultTableModel pacaTestTableModel = new DefaultTableModel(testPcaString, pcaTestColumnNames);
                    JTable pcaTestTable = new JTable(pacaTestTableModel);
                    pcaTestTable.setRowHeight(50);
                    pcaTestTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                    Button testPcaAddButton = new Button("保存");
                    HBox testPcaLabelBox = new HBox();
                    Label testPcaLabel = new Label("  测试集   ");
                    testPcaLabel.setFont(new javafx.scene.text.Font("Arial", 20));
                    testPcaLabelBox.getChildren().addAll(testPcaLabel, testPcaAddButton);
                    testPcaLabelBox.setSpacing(5);
                    testPcaLabelBox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

                    initColumn(pcaTestTable);
                    JScrollPane testPcaScroll = new JScrollPane(pcaTestTable);
                    testPcaScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    SwingNode testPcaSwingNode = new SwingNode();
                    testPcaSwingNode.setContent(testPcaScroll);
                    VBox testPcaBox = new VBox();
                    testPcaBox.getChildren().addAll(testPcaLabelBox, testPcaSwingNode);
                    ((VBox) pcaScence.getRoot()).getChildren().addAll(testPcaBox);

                    String[][] finalTestPcaString = testPcaString;
                    testPcaAddButton.setOnAction(event2 -> {
                        FileChooser fileSaveChooser = new FileChooser();
                        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                                "zyz 文件 (*.zyz)", "*.zyz");
                        fileSaveChooser.getExtensionFilters().add(extFilter);
                        File file = fileSaveChooser.showSaveDialog(stage);
                        if (file != null) {
                            fileAction.saveData(file, finalTestPcaString);
                        }
                    });
                }
                if (type == 2 | type == 3) {
                    String[] pcaValidationColumnNames = initPCAColumnNames(validationPcaString);
                    DefaultTableModel pacaValidationTableModel = new DefaultTableModel(validationPcaString, pcaValidationColumnNames);
                    JTable pcaValidationTable = new JTable(pacaValidationTableModel);
                    pcaValidationTable.setRowHeight(50);
                    pcaValidationTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                    Button validationPcaAddButton = new Button("保存");
                    HBox validationPcaLabelBox = new HBox();
                    Label validationPcaLabel = new Label("  验证集   ");
                    validationPcaLabel.setFont(new javafx.scene.text.Font("Arial", 20));
                    validationPcaLabelBox.getChildren().addAll(validationPcaLabel, validationPcaAddButton);
                    validationPcaLabelBox.setSpacing(5);
                    validationPcaLabelBox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

                    initColumn(pcaValidationTable);
                    JScrollPane validationPcaScroll = new JScrollPane(pcaValidationTable);
                    validationPcaScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    SwingNode validationPcaSwingNode = new SwingNode();
                    validationPcaSwingNode.setContent(validationPcaScroll);
                    VBox validationPcaBox = new VBox();
                    validationPcaBox.getChildren().addAll(validationPcaLabelBox, validationPcaSwingNode);
                    ((VBox) pcaScence.getRoot()).getChildren().addAll(validationPcaBox);

                    String[][] finalValidationPcaString = validationPcaString;
                    validationPcaAddButton.setOnAction(event2 -> {
                        FileChooser fileSaveChooser = new FileChooser();
                        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                                "zyz 文件 (*.zyz)", "*.zyz");
                        fileSaveChooser.getExtensionFilters().add(extFilter);
                        File file = fileSaveChooser.showSaveDialog(stage);
                        if (file != null) {
                            fileAction.saveData(file, finalValidationPcaString);
                        }
                    });
                }

                pcaStage.setScene(pcaScence);
                pcaStage.show();

                trianPcaAddButton.setOnAction(event2 -> {
                    FileChooser fileSaveChooser = new FileChooser();

                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                            "zyz 文件 (*.zyz)", "*.zyz");
                    fileSaveChooser.getExtensionFilters().add(extFilter);
                    File file = fileSaveChooser.showSaveDialog(stage);
                    if (file != null) {
                        fileAction.saveData(file, trianPcaString);
                    }
                });

            });
        });


        /**
         * XLoading方法调用
         */
        addXLoading.setOnAction(event -> {
                    if (PCA.xloading != null) {
                        Matrix[] var11 = PCA.xloading;
                        algorithmAPI xloading = new algorithmAPI();
                        xloading.getXLoading(var11);
                    } else {
//                        Alert _alert = new Alert(Alert.AlertType.CONFIRMATION, "请先运行PCA方法");
//                        _alert.show();
                    }
                }
        );


        /**
         * SVM方法调用
         */
        addSVM.setOnAction(event -> {

            Stage svmStage = new Stage();
            svmStage.setTitle("选择支持向量机类型：");
            final javafx.scene.control.Button button = new Button("确定");
            Scene svmScene = new Scene(new Group(), 550, 200);
            final ComboBox svmComboBox = new ComboBox();
            svmComboBox.getItems().addAll(
                    " SupportVectorClassification",
                    " NewSupportVectorClassification",
                    " SupportVectorOneClass",
                    " EpsilonSupportVectorRegression",
                    " NewSupportVectorRegression"
            );
            svmComboBox.setValue(" SupportVectorClassification");

            final ComboBox coreComboBox = new ComboBox();
            coreComboBox.getItems().addAll(
                    "Linear",
                    "Poly",
                    "RadialBasisFunction",
                    "Sigmoid",
                    "Precomputed"
            );
            coreComboBox.setValue("Linear");
            final javafx.scene.control.TextField cTime = new javafx.scene.control.TextField();
            cTime.setPromptText("请输入c值");
            cTime.deselect();

            final javafx.scene.control.TextField gammaTime = new javafx.scene.control.TextField();
            gammaTime.setPromptText("请输入gamma值");
            gammaTime.deselect();

            final javafx.scene.control.TextField kTime = new javafx.scene.control.TextField();
            kTime.setPromptText("请输入k值");
            kTime.deselect();

            CheckBox picCb = new CheckBox("准确率图");

            button.setOnAction(event1 -> {
                if (svmComboBox.getSelectionModel().getSelectedIndex() <= 2) {
                    javafx.scene.control.TextArea jta = new javafx.scene.control.TextArea();
                    fileAction.wirteTempSVM(dataOut(1), getLabel(), dataOut(2), dataOut(6), fileList.get(0).getParentFile());
                    ArrayList<Double[][]> result = null;
                    try {
                        result = algorithmAPI.getSVMResult(fileList.get(0).getParentFile().getPath(), svmComboBox.getSelectionModel().getSelectedIndex() + 1, coreComboBox.getSelectionModel().getSelectedIndex() + 1, cTime.getText(), gammaTime.getText(), kTime.getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    trianResult = result.get(0);
                    testResult = result.get(1);
                    validationResult = result.get(2);

                    String error = algorithmAPI.Error;
                    String trianCompare = compareSvm(trianResult);
                    error += System.getProperty("line.separator");
                    error += "训练集：" + System.getProperty("line.separator");
                    error += trianCompare;
                    error += "-------------------------------------------------------------";
                    error += System.getProperty("line.separator");
                    error += "验证集：" + System.getProperty("line.separator");
                    String validationCompare = compareSvm(validationResult);
                    error += validationCompare;
                    trianTableVales = resultForm(trianResult, 1);
                    initTable(trianColumnNames, trianTableVales, 1);
                    testTableVales = resultForm(testResult, 2);
                    initTable(testColumnNames, testTableVales, 2);
                    validationTableVales = resultForm(validationResult, 3);
                    initTable(validationColumnNames, validationTableVales, 3);
                    svmStage.close();

                    //实例化文本框
                    jta.setWrapText(true);
                    jta.setFont(new javafx.scene.text.Font("Arial", 18));
                    jta.setPrefSize(800, 500);
                    jta.appendText(error);
                    Stage errorStage = new Stage();
                    GridPane grid = new GridPane();
                    grid.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
                    grid.setVgap(1);
                    grid.setHgap(1);
                    Scene errorScene = new Scene(grid, 850, 500);
                    GridPane.setConstraints(jta, 0, 0);
                    grid.getChildren().add(jta);
                    errorStage.setScene(errorScene);
                    errorStage.setResizable(false);
                    errorStage.show();

                } else {
                    HashMap trainOut = trainOut();
                    Double[][] precision = new Double[trainOut.size()][2];
                    if(picCb.isSelected()==true) {
                        for (int i = 0; i < trainOut.size(); i++) {
                            Double[][] trian = (Double[][]) trainOut.get(i + "");
                            fileAction.wirteTempSVM(trian, getLabel(), dataOut(2), dataOut(6), fileList.get(0).getParentFile());
                            try {
                                algorithmAPI.getSVMResult(fileList.get(0).getParentFile().getPath(), svmComboBox.getSelectionModel().getSelectedIndex() + 1, coreComboBox.getSelectionModel().getSelectedIndex() + 1, cTime.getText(), gammaTime.getText(), kTime.getText());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            precision[i][0] = i+1.0;
                            precision[i][1] = Double.valueOf(algorithmAPI.accuracy);
                        }
                        ArrayList<Double[][]> datas = new ArrayList<Double[][]>();
                        datas.add(precision);
                        String[] name = new String[]{"precision"};

                        PictureAPI pictureAPI = new PictureAPI();
                        Stage chartStage = null;
                        try {
                            chartStage = new Stage();
                            chartStage.setScene(pictureAPI.getChatingResult(datas, name, new String[]{"", "MSE\n"}));
                            chartStage.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    javafx.scene.control.TextArea jta = new javafx.scene.control.TextArea();
                    fileAction.wirteTempSVM(dataOut(1), getLabel(), dataOut(2), dataOut(6), fileList.get(0).getParentFile());
                    ArrayList<Double[][]> result = null;
                    try {
                        result = algorithmAPI.getSVMResult(fileList.get(0).getParentFile().getPath(), svmComboBox.getSelectionModel().getSelectedIndex() + 1, coreComboBox.getSelectionModel().getSelectedIndex() + 1, cTime.getText(), gammaTime.getText(), kTime.getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    trianResult = result.get(0);
                    testResult = result.get(1);
                    validationResult = result.get(2);

                    String error = algorithmAPI.Error;
                    trianTableVales = resultForm(trianResult, 1);
                    initTable(trianColumnNames, trianTableVales, 1);
                    testTableVales = resultForm(testResult, 2);
                    initTable(testColumnNames, testTableVales, 2);
                    validationTableVales = resultForm(validationResult, 3);
                    initTable(validationColumnNames, validationTableVales, 3);
                    svmStage.close();

                    ArrayList<Double[][]> datas = new ArrayList<Double[][]>();
                    Double[][] rowlable = getLabel();
                    String[] name = new String[]{"实际值", "目标值"};
                    Double[][] trainres = new Double[trianResult.length][2];
                    Double[][] lable = new Double[rowlable.length][2];
                    for (int i = 0; i < rowlable.length; i++) {
                        lable[i][0] = rowlable[i][0];
                        trainres[i][0] = rowlable[i][0];
                        lable[i][1] = rowlable[i][0];
                        trainres[i][1] = trianResult[i][0];

                    }
                    datas.add(lable);
                    datas.add(trainres);
                    PictureAPI pictureAPI = new PictureAPI();
                    Stage chartStage = null;
                    try {
                        chartStage = new Stage();
                        chartStage.setScene(pictureAPI.getScatter2DResult(datas, name, new String[]{"", ""}));
                        chartStage.show();
                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                    //实例化文本框
                    jta.setWrapText(true);
                    jta.setFont(new javafx.scene.text.Font("Arial", 18));
                    jta.setPrefSize(800, 500);
                    jta.appendText(error);
                    Stage errorStage = new Stage();
                    GridPane grid = new GridPane();
                    grid.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
                    grid.setVgap(1);
                    grid.setHgap(1);
                    Scene errorScene = new Scene(grid, 850, 500);
                    GridPane.setConstraints(jta, 0, 0);
                    grid.getChildren().add(jta);
                    errorStage.setScene(errorScene);
                    errorStage.setResizable(false);
                    errorStage.show();
                }

            });

            GridPane grid = new GridPane();
            grid.setVgap(2);
            grid.setHgap(6);

            grid.add(new Label("请选择支持向量机类型: "), 1, 0);
            grid.add(svmComboBox, 2, 0);
            grid.add(new Label("请选择核函数类型: "), 1, 2);
            grid.add(coreComboBox, 2, 2);
            grid.add(new Label("请输入C值: "), 1, 3);
            grid.add(cTime, 2, 3);
            grid.add(new Label("请输入gamma值: "), 1, 4);
            grid.add(gammaTime, 2, 4);
            grid.add(new Label("请输入k折: "), 1, 5);
            grid.add(kTime, 2, 5);
            grid.add(picCb,2,6);
            grid.add(button, 3, 6);

            Group root = (Group) svmScene.getRoot();
            root.getChildren().add(grid);
            svmStage.setScene(svmScene);
            svmStage.show();
        });

        /**
         * BPNN方法调用
         */
        addBPNN.setOnAction(event -> {
            Stage bpnnStage = new Stage();
            GridPane grid = new GridPane();
            grid.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
            grid.setVgap(3);
            grid.setHgap(1);
            Scene bpnnScene = new Scene(grid, 300, 100);

            final Label hiddenLable = new Label("请输入隐藏层数 ");
            GridPane.setConstraints(hiddenLable, 0, 0);
            grid.getChildren().add(hiddenLable);

            final javafx.scene.control.TextField hiddenLayer = new javafx.scene.control.TextField();
            hiddenLayer.setPromptText("请输入隐藏层数");
            hiddenLayer.deselect();
            GridPane.setConstraints(hiddenLayer, 1, 0);
            grid.getChildren().add(hiddenLayer);

            final Label iterateTimesLable = new Label("请输入迭代次数 ");
            GridPane.setConstraints(iterateTimesLable, 0, 1);
            grid.getChildren().add(iterateTimesLable);

            final javafx.scene.control.TextField iterateTimes = new javafx.scene.control.TextField();
            iterateTimes.setPromptText("请输入迭代次数");
            GridPane.setConstraints(iterateTimes, 1, 1);
            grid.getChildren().add(iterateTimes);

            Button button = new Button("确定");
            GridPane.setConstraints(button, 0, 2);
            grid.getChildren().add(button);
            bpnnStage.setScene(bpnnScene);
            bpnnStage.show();

            button.setOnAction(event1 -> {
                javafx.scene.control.TextArea jta = new javafx.scene.control.TextArea();
                Double[][] A = dataOut(1);
                Double[][] B = dataOut(2);
                ArrayList<Double[][]> result = algorithmAPI.getBPNNResult(dataOut(1), getLabel(), dataOut(2), dataOut(6), Integer.valueOf(hiddenLayer.getText()), Integer.valueOf(iterateTimes.getText()));
                trianResult = result.get(0);
                testResult = result.get(1);
                validationResult = result.get(2);
                String error = algorithmAPI.Error;
                trianTableVales = resultForm(trianResult, 1);
                initTable(trianColumnNames, trianTableVales, 1);
                testTableVales = resultForm(testResult, 2);
                initTable(testColumnNames, testTableVales, 2);
                validationTableVales = resultForm(validationResult, 3);
                initTable(validationColumnNames, validationTableVales, 3);
                bpnnStage.close();

                //实例化文本框
                jta.setWrapText(true);
                jta.setFont(new javafx.scene.text.Font("Arial", 18));
                jta.setPrefSize(500, 50);
                jta.appendText(error);
                Stage errorStage = new Stage();
                GridPane errorGrid = new GridPane();
                errorGrid.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
                errorGrid.setVgap(1);
                errorGrid.setHgap(1);
                Scene errorScene = new Scene(errorGrid, 550, 90);
                GridPane.setConstraints(jta, 0, 0);
                errorGrid.getChildren().add(jta);
                errorStage.setScene(errorScene);
                errorStage.setResizable(false);
                errorStage.show();
            });
        });

        /**
         * 马氏距离方法调用
         */
        addMAD.setOnAction(event -> {
            javafx.scene.control.TextArea jta = new javafx.scene.control.TextArea();
            String[][] data;
            Double[] dataA;
            Double[] dataB;
            Double sumA = Double.valueOf(0);
            String nameA;
            String nameB;
            FileChooser fileChooser = new FileChooser();
            configureOpenFileChooser(fileChooser, 1);
            java.util.List<File> fileList = fileChooser.showOpenMultipleDialog(stage);
            LinkedHashMap<String, List<String[]>> madFileData = new LinkedHashMap<>();
            initChooserData(fileList, madFileData, "");
            data = map2Array(madFileData, 2, "");

            for (int k = 0; k < data.length; k++) {
                dataA = new Double[data[k].length - 4];
                nameA = data[k][1];
                for (int i = 0; i < data[k].length - 4; i++) {
                    dataA[i] = Double.valueOf(data[k][i + 4]);
                    sumA += dataA[i];
                }
                for (int j = k; j < data.length; j++) {
                    dataB = new Double[data[j].length - 4];
                    nameB = data[j][1];
                    for (int l = 0; l < data[j].length - 4; l++) {
                        dataB[l] = Double.valueOf(data[j][l + 4]);
                    }
                    Double mean = sumA / dataA.length;
                    Double stdSum = Double.valueOf(0);
                    for (int i = 0; i < dataA.length; i++) {
                        stdSum += Math.pow(dataA[i] - mean, 2);
                    }
                    int length = dataA.length;
                    Double stdPower2 = stdSum / length;
                    Double madPower2 = Double.valueOf(0);

                    for (int i = 0; i < length; i++) {
                        madPower2 += Math.pow(dataA[i] - dataB[i], 2) / stdPower2;
                    }
                    Double mad = Math.pow(madPower2, 0.5);
                    jta.appendText("计算元组: " + nameA + "和" + nameB + "的马氏距离为：" + mad);
                    jta.appendText("\r\n");
                }
            }
            //实例化文本框
            jta.setWrapText(true);
            jta.setFont(new javafx.scene.text.Font("Arial", 18));
            jta.setPrefSize(900, 250);

            Stage madStage = new Stage();
            GridPane grid = new GridPane();
            grid.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
            grid.setVgap(1);
            grid.setHgap(1);
            Scene madScene = new Scene(grid, 950, 290);
            GridPane.setConstraints(jta, 0, 0);
            grid.getChildren().add(jta);
            madStage.setScene(madScene);
            madStage.setResizable(false);
            madStage.show();
        });

        /**
         * 波形画图调用
         */

        addChating.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            configureOpenFileChooser(fileChooser, 2);
            java.util.List<File> fileList = fileChooser.showOpenMultipleDialog(stage);
            LinkedHashMap<String, List<String[]>> picFileData = new LinkedHashMap<>();
            initChooserData(fileList, picFileData, "");
            String[] fileListName = new String[fileList.size()];
            for (int i = 0; i < fileList.size(); i++) {
                String name = fileList.get(i).getName();
//                if(name.contains("-"))
//                    name = name.split("-")[-1];
                fileListName[i] = name;

            }
            ArrayList<Double[][]> picMap = picMap(picFileData);
            PictureAPI pictureAPI = new PictureAPI();
            try {
                Stage chartStage = new Stage();
                chartStage.setScene(pictureAPI.getChatingResult(picMap, fileListName, new String[]{"wavenumber", "wavelength"}));
                chartStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        /**
         * 三维画图调用
         */
        addScatter.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            configureOpenFileChooser(fileChooser, 2);
            java.util.List<File> fileList = fileChooser.showOpenMultipleDialog(stage);
            LinkedHashMap<String, List<String[]>> picFileData = new LinkedHashMap<>();
            initChooserData(fileList, picFileData, "");
            ArrayList<Double[][]> picMap = picMap(picFileData);
//          getScatterPicture(picMap);
        });


        /**
         * 降维图调用
         */
        addRectangle.setOnAction(event -> {

            Stage ppcaStage = new Stage();
            GridPane grid = new GridPane();
            grid.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
            grid.setVgap(4);
            grid.setHgap(2);
            Scene ppcaScene = new Scene(grid, 400, 100);

            final Label p3Label = new Label("请输入3维画图值：");
            GridPane.setConstraints(p3Label, 0, 0);
            grid.getChildren().add(p3Label);

            final javafx.scene.control.TextField x3Times = new javafx.scene.control.TextField();
            x3Times.setPromptText("请输入x值");
            GridPane.setConstraints(x3Times, 1, 0);
            grid.getChildren().add(x3Times);

            final javafx.scene.control.TextField y3Times = new javafx.scene.control.TextField();
            y3Times.setPromptText("请输入y值");
            GridPane.setConstraints(y3Times, 2, 0);
            grid.getChildren().add(y3Times);

            final javafx.scene.control.TextField z3Times = new javafx.scene.control.TextField();
            z3Times.setPromptText("请输入z值");
            GridPane.setConstraints(z3Times, 3, 0);
            grid.getChildren().add(z3Times);

            final Label p2Label = new Label("请输入2维画图值：");
            GridPane.setConstraints(p2Label, 0, 1);
            grid.getChildren().add(p2Label);

            final javafx.scene.control.TextField x2Times = new javafx.scene.control.TextField();
            x2Times.setPromptText("请输入x值");
            GridPane.setConstraints(x2Times, 1, 1);
            grid.getChildren().add(x2Times);

            final javafx.scene.control.TextField y2Times = new javafx.scene.control.TextField();
            y2Times.setPromptText("请输入y值");
            GridPane.setConstraints(y2Times, 2, 1);
            grid.getChildren().add(y2Times);


            Button button = new Button("确定");
            GridPane.setConstraints(button, 0, 3);
            grid.getChildren().add(button);
            ppcaStage.setScene(ppcaScene);
            ppcaStage.show();
            button.setOnAction(event1 -> {
                ppcaStage.close();

                int[] sanweiList = {Integer.valueOf(x3Times.getText()), Integer.valueOf(y3Times.getText()), Integer.valueOf(z3Times.getText())};
                int[] erweiList = {Integer.valueOf(x2Times.getText()), Integer.valueOf(y2Times.getText())};

                String[][] label = getFileName();
                String temp = label[0][0].substring(0, label[0][0].lastIndexOf("-"));
                String lableName = temp;
                int m = 0;
                for (; m < label.length; m++) {
                    String a = label[m][0].substring(0, label[m][0].lastIndexOf("-"));
                    System.out.println("a:" + a);
                    if (temp.compareTo(a) != 0)
                        break;
                }

                Double[][] trainTemp = dataOut(1);
                Double[][] ScattertrianPcaResult = new Double[m][3];
                Double[][] ScattertrianPcaResult2D = new Double[m][2];
                ArrayList<Double[][]> ScattertPcaResult = new ArrayList<Double[][]>();
                ArrayList<Double[][]> ScattertPcaResult2D = new ArrayList<Double[][]>();
                int temprow = 0;
                for (int i = 0; i < trainTemp.length; i++) {
                    String a = label[i][0].substring(0, label[i][0].lastIndexOf("-"));
                    if (temp.compareTo(a) != 0) {
                        temprow = i;
                        ScattertPcaResult.add(ScattertrianPcaResult);
                        ScattertPcaResult2D.add(ScattertrianPcaResult2D);
                        temp = a;
                        lableName = lableName + "," + temp;
                        int mm = m;
                        for (; m < label.length; m++) {
                            String b = label[m][0].substring(0, label[m][0].lastIndexOf("-"));
                            if (temp.compareTo(b) != 0) {
                                break;
                            }
                        }
                        ScattertrianPcaResult = new Double[m - mm][3];
                        ScattertrianPcaResult2D = new Double[m - mm][2];

                    }

                    for (int j = 0; j < 3; j++) {
                        ScattertrianPcaResult[i - temprow][j] = trainTemp[i][sanweiList[j]];
                    }
                    for (int j = 0; j < 2; j++) {
                        ScattertrianPcaResult2D[i - temprow][j] = trainTemp[i][erweiList[j]];
                    }

                }
                ScattertPcaResult.add(ScattertrianPcaResult);
                ScattertPcaResult2D.add(ScattertrianPcaResult2D);
                String[] lableName1 = lableName.split(",");
                getScatterPicture(ScattertPcaResult, lableName1);
                PictureAPI pictureAPI = new PictureAPI();
                try {
                    Stage chartStage = new Stage();
                    chartStage.setScene(pictureAPI.getScatter2DResult(ScattertPcaResult2D, lableName1, new String[]{"", ""}));
                    chartStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });


        });
        /**
         * knn方法调用
         */
        addKNN.setOnAction(event -> {

            Stage knnStage = new Stage();
            GridPane grid = new GridPane();
            grid.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
            grid.setVgap(3);
            grid.setHgap(1);
            Scene knnScene = new Scene(grid, 300, 100);

            final Label knnLabel = new Label("请输入k值：");
            GridPane.setConstraints(knnLabel, 0, 0);
            grid.getChildren().add(knnLabel);

            final javafx.scene.control.TextField kTimes = new javafx.scene.control.TextField();
            kTimes.setPromptText("请输入k");
            GridPane.setConstraints(kTimes, 1, 0);
            grid.getChildren().add(kTimes);

            Button button = new Button("确定");
            GridPane.setConstraints(button, 0, 2);
            grid.getChildren().add(button);
            knnStage.setScene(knnScene);
            knnStage.show();

            button.setOnAction(event1 -> {
                knnStage.close();
                trianResult = algorithmAPI.getKNNResult(dataOut(1), dataOut(1), getLabel(), Integer.valueOf(kTimes.getText()));
                testResult = algorithmAPI.getKNNResult(dataOut(1), dataOut(2), getLabel(), Integer.valueOf(kTimes.getText()));
                validationResult = algorithmAPI.getKNNResult(dataOut(1), dataOut(6), getLabel(), Integer.valueOf(kTimes.getText()));
                trianTableVales = resultForm(trianResult, 1);
                initTable(trianColumnNames, trianTableVales, 1);
                testTableVales = resultForm(testResult, 2);
                initTable(testColumnNames, testTableVales, 2);
                validationTableVales = resultForm(validationResult, 3);
                initTable(validationColumnNames, validationTableVales, 3);

            });
        });

        /**
         * PLS降维方法调用
         */
        addPLS.setOnAction(event -> {
            Stage textStage = new Stage();
            GridPane grid = new GridPane();
            grid.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
            grid.setVgap(4);
            grid.setHgap(1);
            Scene textScene = new Scene(grid, 300, 100);

            final Label plsLabel = new Label("请输入因子数量：");
            GridPane.setConstraints(plsLabel, 0, 0);
            grid.getChildren().add(plsLabel);

            final javafx.scene.control.TextField factors = new javafx.scene.control.TextField();
            factors.setPromptText("请输入因子数量：");
            GridPane.setConstraints(factors, 1, 0);
            grid.getChildren().add(factors);

            CheckBox testCb = new CheckBox("测试集");
            GridPane.setConstraints(testCb, 0, 2);
            grid.getChildren().add(testCb);
            CheckBox validationCb = new CheckBox("验证集");
            GridPane.setConstraints(validationCb, 1, 2);
            grid.getChildren().add(validationCb);

            Button button = new Button("确定");
            GridPane.setConstraints(button,0, 3);
            grid.getChildren().add(button);
            textStage.setScene(textScene);
            textStage.show();

            button.setOnAction(event1 -> {
                textStage.close();
                Double[][] trainData = dataOut(1);//训练集数据
                Double[][] testData = null;
                Double[][] validationplsData = null;
                int type = 1;
                if (testCb.isSelected() == true )
                    testData = dataOut(2);
                if (validationCb.isSelected() == true)
                    validationplsData = dataOut(6);

                HashMap<String,Double[][]> result= algorithmAPI.getPLSDimReductionResult(trainData,getLabel(),Integer.valueOf(factors.getText()),testData,validationplsData);

                Double[][] trianplsResult = result.get("train");
                Double[][] testplsResult = result.get("test");
                Double[][] validationplsResult = result.get("validation");

                String[][] trianPlsString = addInf(trianplsResult, 0);
                String[][] testPlsString = null;
                String[][] validationPlsString = null;

                Stage plsStage = new Stage();
                Scene plsScence = new Scene(new VBox(), width, high);

                String[] plsTrianColumnNames = initPCAColumnNames(trianPlsString);
                DefaultTableModel plsTrianTableModel = new DefaultTableModel(trianPlsString, plsTrianColumnNames);
                JTable plsTrianTable = new JTable(plsTrianTableModel);
                plsTrianTable.setRowHeight(50);
                plsTrianTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                Button trianPlsAddButton = new Button("保存");
                HBox trianPlsLabelBox = new HBox();
                Label trianPlsLabel = new Label("  训练集   ");
                trianPlsLabel.setFont(new javafx.scene.text.Font("Arial", 20));
                trianPlsLabelBox.getChildren().addAll(trianPlsLabel, trianPlsAddButton);
                trianPlsLabelBox.setSpacing(5);
                trianPlsLabelBox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

                initColumn(plsTrianTable);
                JScrollPane trianPlsScroll = new JScrollPane(plsTrianTable);
                trianPlsScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                SwingNode trianPlsSwingNode = new SwingNode();
                trianPlsSwingNode.setContent(trianPlsScroll);
                VBox trianPlsBox = new VBox();
                trianPlsBox.getChildren().addAll(trianPlsLabelBox, trianPlsSwingNode);

                VBox testPlsBox = new VBox();
                VBox validationPlsBox = new VBox();
                ((VBox) plsScence.getRoot()).getChildren().addAll(trianPlsBox);
                if(testplsResult!=null)
                {
                    testPlsString = addInf(testplsResult, 1);

                    String[] plsTestColumnNames = initPCAColumnNames(testPlsString);
                    DefaultTableModel plsTestTableModel = new DefaultTableModel(testPlsString, plsTestColumnNames);
                    JTable plsTestTable = new JTable(plsTestTableModel);
                    plsTestTable.setRowHeight(50);
                    plsTestTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                    Button testPlsAddButton = new Button("保存");
                    HBox testPlsLabelBox = new HBox();
                    Label testPlsLabel = new Label("  测试集   ");
                    testPlsLabel.setFont(new javafx.scene.text.Font("Arial", 20));
                    testPlsLabelBox.getChildren().addAll(testPlsLabel, testPlsAddButton);
                    testPlsLabelBox.setSpacing(5);
                    testPlsLabelBox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

                    initColumn(plsTestTable);
                    JScrollPane testPlsScroll = new JScrollPane(plsTestTable);
                    testPlsScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    SwingNode testPlsSwingNode = new SwingNode();
                    testPlsSwingNode.setContent(testPlsScroll);
                    testPlsBox.getChildren().addAll(testPlsLabelBox, testPlsSwingNode);
                    ((VBox) plsScence.getRoot()).getChildren().addAll( testPlsBox);
                    final String[][] testPls = testPlsString;
                    testPlsAddButton.setOnAction(event2 -> {
                        FileChooser fileSaveChooser = new FileChooser();

                        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                                "zyz 文件 (*.zyz)", "*.zyz");
                        fileSaveChooser.getExtensionFilters().add(extFilter);
                        File file = fileSaveChooser.showSaveDialog(stage);
                        if (file != null) {
                            fileAction.saveData(file, testPls);
                        }
                    });

                }
                if(validationplsResult!=null)
                {
                    validationPlsString= addInf(validationplsResult, 2);

                    String[] plsValidationColumnNames = initPCAColumnNames(validationPlsString);
                    DefaultTableModel plsValidationTableModel = new DefaultTableModel(validationPlsString, plsValidationColumnNames);
                    JTable plsValidationTable = new JTable(plsValidationTableModel);
                    plsValidationTable.setRowHeight(50);
                    plsValidationTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                    Button validationPlsAddButton = new Button("保存");
                    HBox validationPlsLabelBox = new HBox();
                    Label validationPlsLabel = new Label("  验证集   ");
                    validationPlsLabel.setFont(new javafx.scene.text.Font("Arial", 20));
                    validationPlsLabelBox.getChildren().addAll(validationPlsLabel, validationPlsAddButton);
                    validationPlsLabelBox.setSpacing(5);
                    validationPlsLabelBox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

                    initColumn(plsValidationTable);
                    JScrollPane validationPlsScroll = new JScrollPane(plsValidationTable);
                    validationPlsScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    SwingNode validationPlsSwingNode = new SwingNode();
                    validationPlsSwingNode.setContent(validationPlsScroll);

                    validationPlsBox.getChildren().addAll(validationPlsLabelBox, validationPlsSwingNode);
                    ((VBox) plsScence.getRoot()).getChildren().addAll( validationPlsBox);
                    final String[][] validationPls = validationPlsString;
                    validationPlsAddButton.setOnAction(event2 -> {
                        FileChooser fileSaveChooser = new FileChooser();

                        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                                "zyz 文件 (*.zyz)", "*.zyz");
                        fileSaveChooser.getExtensionFilters().add(extFilter);
                        File file = fileSaveChooser.showSaveDialog(stage);
                        if (file != null) {
                            fileAction.saveData(file, validationPls);
                        }

                    });
                }

                plsStage.setScene(plsScence);
                plsStage.show();

                trianPlsAddButton.setOnAction(event2 -> {
                    FileChooser fileSaveChooser = new FileChooser();
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                            "zyz 文件 (*.zyz)", "*.zyz");
                    fileSaveChooser.getExtensionFilters().add(extFilter);
                    File file = fileSaveChooser.showSaveDialog(stage);
                    if (file != null) {
                        fileAction.saveData(file, trianPlsString);
                    }
                });
            });
        });

        /**
         * pls预测方法
         */
        addPLSForest.setOnAction(event -> {
            Double[][] trainData = dataOut(1);//训练集数据
            if(testTableVales.length==0||validationTableVales.length==0)
            {
//                Alert alert = new Alert
            }
            else {
                Double[][] testData = dataOut(2);
                Double[][] validationplsData = dataOut(6);

                HashMap<String, Double[][]> forestResultMap = algorithmAPI.getPLSforestResult(trainData, getLabel(), testData, validationplsData, getValidationLabel());

                trianResult = forestResultMap.get("train");
                testResult = forestResultMap.get("test");
                validationResult = forestResultMap.get("validation");
                Double[][] error = forestResultMap.get("trainMeanpercentError");
                trianTableVales = resultForm(trianResult, 1);
                initTable(trianColumnNames, trianTableVales, 1);
                testTableVales = resultForm(testResult, 2);
                initTable(testColumnNames, testTableVales, 2);
                validationTableVales = resultForm(validationResult, 3);
                initTable(validationColumnNames, validationTableVales, 3);

                javafx.scene.control.TextArea jta = new javafx.scene.control.TextArea();
                jta.setWrapText(true);
                jta.setFont(new javafx.scene.text.Font("Arial", 18));
                jta.setPrefSize(500, 50);
                jta.appendText("训练集平均预测误差：" + error[0][0] + "\t验证集平均预测误差：" + error[1][0]);
                Stage errorStage = new Stage();
                GridPane errorGrid = new GridPane();
                errorGrid.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
                errorGrid.setVgap(1);
                errorGrid.setHgap(1);
                Scene errorScene = new Scene(errorGrid, 550, 90);
                GridPane.setConstraints(jta, 0, 0);
                errorGrid.getChildren().add(jta);
                errorStage.setScene(errorScene);
                errorStage.setResizable(false);
                errorStage.show();

            }

        });

        /**
         * kmeans方法调用
         */
        addKmeans.setOnAction(event -> {

            Stage kmeansStage = new Stage();
            GridPane grid = new GridPane();
            grid.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
            grid.setVgap(8);
            grid.setHgap(1);
            Scene kmeansScene = new Scene(grid, 200, 250);

            final Label kmeansLabel = new Label("请输入k值：");
            GridPane.setConstraints(kmeansLabel, 0, 0);
            grid.getChildren().add(kmeansLabel);

            final javafx.scene.control.TextField kTimes = new javafx.scene.control.TextField();
            kTimes.setPromptText("请输入k值：");
            GridPane.setConstraints(kTimes, 0, 1);
            grid.getChildren().add(kTimes);

            final javafx.scene.control.TextField leftX = new javafx.scene.control.TextField();
            leftX.setPromptText("请输入左上x值：");
            GridPane.setConstraints(leftX, 0, 2);
            grid.getChildren().add(leftX);

            final javafx.scene.control.TextField leftY = new javafx.scene.control.TextField();
            leftY.setPromptText("请输入左上y值：");
            GridPane.setConstraints(leftY, 0, 3);
            grid.getChildren().add(leftY);

            final javafx.scene.control.TextField rightX = new javafx.scene.control.TextField();
            rightX.setPromptText("请输入右下x值：");
            GridPane.setConstraints(rightX, 0, 4);
            grid.getChildren().add(rightX);

            final javafx.scene.control.TextField rightY = new javafx.scene.control.TextField();
            rightY.setPromptText("请输入右下y值：");
            GridPane.setConstraints(rightY, 0, 5);
            grid.getChildren().add(rightY);

            final javafx.scene.control.TextField step = new javafx.scene.control.TextField();
            step.setPromptText("请输入步长值：");
            GridPane.setConstraints(step, 0, 6);
            grid.getChildren().add(step);

            Button button = new Button("确定");
            GridPane.setConstraints(button, 0, 7);
            grid.getChildren().add(button);
            kmeansStage.setScene(kmeansScene);
            kmeansStage.show();
            button.setOnAction(event1 -> {

                FileChooser fileChooser = new FileChooser();
                configureOpenFileChooser(fileChooser, 2);
                java.util.List<File> fileList = fileChooser.showOpenMultipleDialog(stage);
                LinkedHashMap<String, List<String[]>> kmeansFileData = new LinkedHashMap<>();
                initChooserData(fileList, kmeansFileData, "");
                String[][] kmeasMap = map2Array(kmeansFileData, 1, "");
                double[][] doubleMap = string2Double(kmeasMap);
                Kmeans kmeans = new Kmeans();
                Double[] result = kmeans.computeKmeans(doubleMap, Integer.valueOf(kTimes.getText()));
                int row = (Integer.valueOf(rightX.getText()) - Integer.valueOf(leftX.getText())) / Integer.valueOf(step.getText());
                int col = (Integer.valueOf(rightY.getText()) - Integer.valueOf(leftY.getText())) / Integer.valueOf(step.getText());
                PictureAPI pictureAPI = new PictureAPI();
                Stage chartStage = null;
                try {
                    chartStage = new Stage();
                    chartStage.setScene(pictureAPI.getRectangleResult(result, row + 1, col + 1));
                    chartStage.show();
                } catch (Exception e) {
                    e.printStackTrace();

                }
                kmeansStage.close();
                javafx.scene.control.TextArea jta = new javafx.scene.control.TextArea();
                for (int i = 0; i < result.length; i++) {
                    jta.appendText("计算元组: " + fileList.get(i).getName() + "的类别为：" + result[i]);
                    jta.appendText("\r\n");
                }
                //实例化文本框
                jta.setWrapText(true);
                jta.setFont(new javafx.scene.text.Font("Arial", 18));
                jta.setPrefSize(500, 250);

                Stage textStage = new Stage();
                GridPane textgrid = new GridPane();
                textgrid.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
                textgrid.setVgap(1);
                textgrid.setHgap(1);
                Scene textScene = new Scene(textgrid, 550, 290);
//                System.out.println(chartStage.getX());
//                System.out.println(chartStage.getHeight());
                textStage.setX(chartStage.getX());
                textStage.setY((chartStage.getY() + chartStage.getHeight()) / 2);
                GridPane.setConstraints(jta, 0, 0);
                textgrid.getChildren().add(jta);
                textStage.setScene(textScene);
                textStage.setResizable(false);
                textStage.show();
            });
        });

        ((VBox) scene.getRoot()).getChildren().addAll(menuBar, trianBox, testBox, validationBox);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * 画散点图
     */
    public void getScatterPicture(ArrayList<Double[][]> picMap, String[] fileListName) {
        PictureAPI pictureAPI = new PictureAPI();
        try {
            pictureAPI.getScatterResult(picMap, fileListName);

        } catch (Exception e) {
            e.printStackTrace();

        }
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
        } else if (type == 2) {
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
        } else {
            if (validationBox.getChildren().size() != 0)
                validationBox.getChildren().clear();
            validationLabelBox = new HBox();
            validationLabel = new Label("   验证集   ");
            validationLabel.setFont(new javafx.scene.text.Font("Arial", 20));
            validationLabelBox.getChildren().addAll(validationLabel, validationAddButton);
            validationLabelBox.setSpacing(5);
            validationLabelBox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
            validationTableModel = new DefaultTableModel(tableVales, columnNames);
            validationTable = new JTable(validationTableModel);
            validationTable.setRowHeight(50);
            validationTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            initColumn(validationTable);
            validationScroll = new JScrollPane();
            validationScroll.setViewportView(validationTable);
            validationScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            validationSwingNode = new SwingNode();

            validationSwingNode.setContent(validationScroll);
            validationBox.getChildren().addAll(validationLabelBox, validationSwingNode);
        }
    }

    /**
     * 初始化文件打开选择器
     *
     * @param fileChooser
     */
    private static void configureOpenFileChooser(
            final FileChooser fileChooser, int type) {
        fileChooser.setTitle("文件选择");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        if (type == 1 | type == 2)
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("csv文件,", "*.csv"),
                    new FileChooser.ExtensionFilter("txt文件,", "*.txt")
            );
        if (type == 1 | type == 3)
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("zyz文件,", "*.zyz"));
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
        int fileLength = sigData.size() - 1;

        String[][] tableData = new String[mapLength][fileLength + 4];

        Iterator ite = fileData.values().iterator();

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
            String label;
            if (fileName[1] != null && dataDir != null)
                label = dataDir.get(fileName[1]);
            else
                label = "";
            if (type == 1)
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
    private boolean initChooserData(List<File> fileList, LinkedHashMap<String, List<String[]>> fileData, String dirName) {

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
        String[] initColumnName = new String[fileLength + 3];
        initColumnName[0] = "序号";
        initColumnName[1] = "文件名";
        initColumnName[2] = "实际值";
        initColumnName[3] = "目标值";
        for (int i = 4; i < fileLength + 3; i++) {
            initColumnName[i] = i - 3 + "";
        }
        return initColumnName;
    }

    /**
     * 初始化算法计算结果列名
     */

    private String[] initAlgorithmColumnNames(String[][] data) {
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
     * 生成PCA和PLS结果列标签
     *
     * @param pcaResult
     * @return
     */
    private String[] initPCAColumnNames(String[][] pcaResult) {
        String[] initColumnName = new String[pcaResult[0].length];
        initColumnName[0] = "序号";
        initColumnName[1] = "文件名";
        initColumnName[2] = "实际值";
        initColumnName[3] = "目标值";
        for (int i = 4; i < pcaResult[0].length; i++) {
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

    private ArrayList<Double[][]> picMap(HashMap<String, List<String[]>> picMap) {
        ArrayList<Double[][]> doubleMap = new ArrayList<>();
        Iterator ite = picMap.values().iterator();

        while (ite.hasNext()) {
            List<String[]> stringData = (List<String[]>) ite.next();
            Double[][] data = new Double[stringData.size() - 1][stringData.get(0).length];
            Iterator listIte = stringData.iterator();
            int j = 0;
            listIte.next();
            while (listIte.hasNext()) {
                String[] sigData = (String[]) listIte.next();
                for (int i = 0; i < sigData.length; i++) {
                    data[j][i] = Double.valueOf(sigData[i]);

                }
                j++;
            }

            doubleMap.add(data);
        }


        return doubleMap;
    }

    /**
     * 导出训练集标签
     *
     * @return
     */
    public Double[][] getLabel() {
        Double[][] label;
        label = new Double[trianTableVales.length][1];
        for (int i = 0; i < trianTableVales.length; i++)
            label[i][0] = Double.valueOf(trianTableVales[i][2]);
        return label;
    }

    /**
     * 导出验证集标签
     *
     * @return
     */
    public Double[][] getValidationLabel() {
        Double[][] label;
        label = new Double[validationTableVales.length][1];
        for (int i = 0; i < validationTableVales.length; i++)
            label[i][0] = Double.valueOf(validationTableVales[i][2]);
        return label;
    }



    /**
     * 导出训练集文件名
     *
     * @return
     */
    public String[][] getFileName() {
        String[][] fileNames;
        fileNames = new String[trianTableVales.length][1];
        for (int i = 0; i < trianTableVales.length; i++)
            fileNames[i][0] = trianTableVales[i][1];
        return fileNames;
    }


    private String[][] resultForm(Double[][] result, int type) {
        String[][] data = null;
        switch (type) {
            case 1:
                data = trianTableVales;
                break;
            case 2:
                data = testTableVales;
                break;
            case 3:
                data = validationTableVales;
        }
        for (int i = 0; i < data.length; i++)
            data[i][3] = result[i][0] + "";

        return data;
    }

    /**
     * 表格数据导出
     *
     * @param type 1为训练集，2为测试集,3为训练集和测试集，4为全部，5为测试集和验证集,6为验证集
     * @return 表格数据
     */
    public Double[][] dataOut(int type) {
        Double[][] data = null;
        switch (type) {
            case 1: {
                data = new Double[trianTableVales.length][trianTableVales[0].length - 4];
                for (int i = 0; i < data.length; i++)
                    for (int j = 4; j < trianTableVales[0].length; j++) {
                        if (trianTableVales[i][j] != null)
                            data[i][j - 4] = Double.parseDouble(trianTableVales[i][j]);
                    }
                break;
            }
            case 2: {
                data = new Double[testTableVales.length][testTableVales[0].length - 4];
                for (int i = 0; i < data.length; i++)
                    for (int j = 4; j < testTableVales[0].length; j++) {
                        if (testTableVales[i][j] != null && testTableVales[i][j] != "")
                            data[i][j - 4] = Double.parseDouble(testTableVales[i][j]);
                    }
                break;
            }
            case 3: {
                data = new Double[trianTableVales.length + testTableVales.length][testTableVales[0].length - 4];
                for (int i = 0; i < trianTableVales.length; i++)
                    for (int j = 4; j < trianTableVales[0].length; j++) {
                        if (trianTableVales[i][j] != null)
                            data[i][j - 4] = Double.parseDouble(trianTableVales[i][j]);
                    }
                for (int i = trianTableVales.length; i < data.length; i++)
                    for (int j = 4; j < testTableVales[0].length; j++) {
                        if (testTableVales[i - trianTableVales.length][j] != null && testTableVales[i - trianTableVales.length][j] != "")
                            data[i][j - 4] = Double.parseDouble(testTableVales[i - trianTableVales.length][j]);
                    }
                break;
            }
            case 4: {
                data = new Double[trianTableVales.length + testTableVales.length + validationTableVales.length][testTableVales[0].length - 4];
                for (int i = 0; i < trianTableVales.length; i++)
                    for (int j = 4; j < trianTableVales[0].length; j++) {
                        if (trianTableVales[i][j] != null)
                            data[i][j - 4] = Double.parseDouble(trianTableVales[i][j]);
                    }
                for (int i = trianTableVales.length; i < testTableVales.length + trianTableVales.length; i++)
                    for (int j = 4; j < testTableVales[0].length; j++) {
                        if (testTableVales[i - trianTableVales.length][j] != null && testTableVales[i - trianTableVales.length][j] != "")
                            data[i][j - 4] = Double.parseDouble(testTableVales[i - trianTableVales.length][j]);
                    }
                for (int i = trianTableVales.length + testTableVales.length; i < data.length; i++)
                    for (int j = 4; j < validationTableVales[0].length; j++) {
                        if (validationTableVales[i - trianTableVales.length - testTableVales.length][j] != null && validationTableVales[i - trianTableVales.length - testTableVales.length][j] != "")
                            data[i][j - 4] = Double.parseDouble(validationTableVales[i - trianTableVales.length - testTableVales.length][j]);
                    }
                break;
            }
            case 5: {
                data = new Double[testTableVales.length + validationTableVales.length][testTableVales[0].length - 4];
                for (int i = 0; i < testTableVales.length; i++)
                    for (int j = 4; j < testTableVales[0].length; j++) {
                        if (testTableVales[i][j] != null)
                            data[i][j - 4] = Double.parseDouble(testTableVales[i][j]);
                    }
                for (int i = testTableVales.length; i < data.length; i++)
                    for (int j = 4; j < validationTableVales[0].length; j++) {
                        if (validationTableVales[i - testTableVales.length][j] != null && validationTableVales[i - testTableVales.length][j] != "")
                            data[i][j - 4] = Double.parseDouble(validationTableVales[i - testTableVales.length][j]);
                    }
                break;
            }
            case 6: {
                data = new Double[validationTableVales.length][validationTableVales[0].length - 4];
                for (int i = 0; i < data.length; i++)
                    for (int j = 4; j < validationTableVales[0].length; j++) {
                        if (validationTableVales[i][j] != null && validationTableVales[i][j] != "")
                            data[i][j - 4] = Double.parseDouble(validationTableVales[i][j]);
                    }
            }

        }
        return data;
    }


    /**
     * pca表格数据导出
     *
     * @param type 1为训练集，其他为测试集
     * @return 表格数据
     */
    public double[][] doubleDataOut(int type) {
        double[][] data;
        if (type == 1) {
            data = new double[trianTableVales.length][trianTableVales[0].length - 4];
            for (int i = 0; i < data.length; i++)
                for (int j = 4; j < trianTableVales[0].length; j++) {
                    if (trianTableVales[i][j] != null)
                        data[i][j - 4] = Double.parseDouble(trianTableVales[i][j]);
                }
        } else {
            data = new double[testTableVales.length][testTableVales[0].length - 4];
            for (int i = 0; i < data.length; i++)
                for (int j = 4; j < testTableVales[0].length; j++) {
                    if (testTableVales[i][j] != null && testTableVales[i][j] != "")
                        data[i][j - 4] = Double.parseDouble(testTableVales[i][j]);
                }
        }
        return data;
    }

    /**
     * 算法结果导入表格
     *
     * @param data 计算结果
     * @param type 1为训练集，其他为测试集
     */
    public void dataIn(String[][] data, int type) {
        String[] columnName = initAlgorithmColumnNames(data);
        if (type == 1) {
            initTable(columnName, data, 1);
        } else {
            initTable(columnName, data, 2);
        }
    }

    /**
     * string数组转kmeans算法的double数组
     *
     * @param kmeasMap
     * @return
     */
    private double[][] string2Double(String[][] kmeasMap) {
        double[][] map = new double[kmeasMap.length][kmeasMap[0].length - 4];
        for (int i = 0; i < kmeasMap.length; i++)
            for (int j = 4; j < kmeasMap[i].length; j++) {
                map[i][j - 4] = Double.valueOf(kmeasMap[i][j]);
            }
        return map;
    }

    /**
     * PCA数据格式化
     *
     * @param data
     * @param type
     * @return
     */
    private String[][] addInf(Double[][] data, int type) {
        String[][] addData = new String[data.length][data[0].length + 4];

        if (type == 0) {
            for (int i = 0; i < trianTableVales.length; i++)
                for (int j = 0; j < 5; j++)
                    if (trianTableVales[i][j] != null)
                        addData[i][j] = trianTableVales[i][j];
        } else if (type == 1) {
            for (int i = 0; i < testTableVales.length; i++)
                for (int j = 0; j < 5; j++)
                    if (testTableVales[i][j] != null)
                        addData[i][j] = testTableVales[i][j];
        } else {
            for (int i = 0; i < validationTableVales.length; i++)
                for (int j = 0; j < 5; j++)
                    if (validationTableVales[i][j] != null)
                        addData[i][j] = validationTableVales[i][j];
        }

        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[i].length; j++)
                addData[i][j + 4] = data[i][j] + "";

        return addData;
    }

    private Double[][] gePlsY(Double[][] X, int start, int end) {
        Double[][] Y = new Double[X.length][end - start + 1];
        for (int i = 0; i < Y.length; i++)
            for (int j = start; j <= end; j++)
                Y[i][j - start] = X[i][j];
        return Y;
    }

    private HashMap<String, Double[][]> trainOut() {
        HashMap<String, Double[][]> trainOut = new HashMap<>();
        int length = 0;
        for (int i = 0; i < trianTableVales[0].length - 4; i++) {
            Double[][] temp = new Double[trianTableVales.length][length + 1];
            for (int l = 0; l < trianTableVales.length; l++)
                for (int k = 0; k <= length; k++)
                    temp[l][k] = Double.parseDouble(trianTableVales[l][k + 4]);
            trainOut.put(length + "", temp);
            length++;
        }
        return trainOut;
    }

    private String compareSvm(Double[][] trianResult) {
        String result = "";
        String fileName = "错误文件名：" + System.getProperty("line.separator");
        ;
        Double[][] label = getLabel();
        HashSet<Double> set = new HashSet<>();
        for (int i = 0; i < label.length; i++) {
            set.add(label[i][0]);
        }
        ArrayList<Integer> list = new ArrayList<>();
        Iterator ite = set.iterator();
        while (ite.hasNext()) {
            list.add(((Double) ite.next()).intValue());
        }
        Collections.sort(list);
        int maxLabel = list.get(list.size() - 1);
        int[][] compare = new int[maxLabel][maxLabel];
        for (int i = 0; i < compare.length; i++)
            for (int j = 0; j < compare.length; j++) {
                compare[i][j] = 0;
            }
        for (int i = 0; i < trianResult.length; i++) {
            compare[trianResult[i][0].intValue() - 1][label[i][0].intValue() - 1]++;
            if (trianResult[i][0].intValue() != label[i][0].intValue()) {
                fileName += trianTableVales[i][1] + System.getProperty("line.separator");
            }
        }
        result += "       ";
        for (int i = 0; i < list.size(); i++) {
            result += list.get(i);
            if (i != list.size() - 1)
                result += "   ";
            else
                result += System.getProperty("line.separator");
        }
        for (int i = 0; i < list.size(); i++) {
            if (i == 0)
                result += "   ";
            else
                result += "—";
            if (i != list.size() - 1)
                result += "—";
            if (i == list.size() - 1)
                result += System.getProperty("line.separator");
        }
        for (int i = 0; i < compare.length; i++) {
            result += list.get(i) + " " + "|" + "   ";
            for (int j = 0; j < compare.length; j++) {
                try {
                    result += compare[i][j];
                } catch (Exception e) {
                    result += 0;
                }
                if (j != list.size() - 1)
                    result += "   ";
                else
                    result += System.getProperty("line.separator");
            }
        }
        result += System.getProperty("line.separator") + fileName;
        return result;
    }

//    private Double[][] separData(Double[][] data, int type) {
//        if (type == 1) {
//            Double[][] result = new Double[trianTableVales.length][data[0].length];
//            for (int i = 0; i < result.length; i++)
//                for (int j = 0; j < data[i].length; j++)
//                    result[i][j] = data[i][j];
//            return result;
//        } else if (type == 2) {
//            Double[][] result = new Double[testTableVales.length][data[0].length];
//            for (int i = trianTableVales.length; i < data.length - validationTableVales.length; i++)
//                for (int j = 0; j < data[i].length; j++)
//                    result[i - trianTableVales.length][j] = data[i][j];
//            return result;
//        } else {
//            Double[][] result = new Double[validationTableVales.length][data[0].length];
//            for (int i = trianTableVales.length + testTableVales.length; i < data.length; i++)
//                for (int j = 0; j < data[i].length; j++)
//                    result[i - trianTableVales.length - testTableVales.length][j] = data[i][j];
//            return result;
//
//        }
//    }

}
