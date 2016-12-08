package frame;
/**算法主界面
 * Created by zzh on 2016/12/7.
 */

import java.awt.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.Vector;

import javafx.embed.swing.SwingNode;
import model.Data;

import com.smooth.gui.SmoothGUI;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

public class MianFrame extends Application {
    // table with scrollbars
    private TableView<Data> trianTable = new TableView<>();
    // table without scrollbars
    private TableView<Data> testTable = new TableView<>();
    private String data ="1.0";
    private JTable jtable;
    private Table_Model model = null;
    private JScrollPane s_pan = null;


    private final ObservableList<Data> data1 =
            FXCollections.observableArrayList(
                    new Data(data,new CheckBox())

            );


    private final ObservableList<Data> data2 =
            FXCollections.observableArrayList(
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
        model = new Table_Model(20);
        jtable = new JTable(model);



        model.addRow("翔", true, "30");
        model.addRow("逗逗", false, "21");
        model.addRow("我", true, "24");

        s_pan = new JScrollPane(jtable);

        final SwingNode swingNode = new SwingNode();
        swingNode.setContent(s_pan);

        vBox.getChildren().addAll(trianLabel, swingNode);

        ((VBox) scene.getRoot()).getChildren().addAll(menuBar,vBox);

        stage.setScene(scene);
        stage.show();

    }








}
class Table_Model extends AbstractTableModel {
    private static final long serialVersionUID = -3094977414157589758L;

    private Vector content = null;

    private String[] title_name = { "姓名", "性别", "年龄" };

    public Table_Model() {
        content = new Vector();
    }

    public Table_Model(int count) {
        content = new Vector(count);
    }

    /**
     * 加入一空行
     * @param row 行号
     */
    public void addRow(int row) {
        Vector v = new Vector(3);
        v.add(0, null);
        v.add(1, null);
        v.add(2, null);
        content.add(row, v);
    }

    /**
     * 加入一行内容
     */
    public void addRow(String name, boolean bool, String age) {
        Vector v = new Vector(3);
        v.add(0, name);
        v.add(1, bool); // JCheckBox是Boolean的默认显示组件，这里仅仅为了看效果，其实用JComboBox显示***更合适

        v.add(2, age); // 本列在前面已经设置成了JComboBox组件，这里随便输入什么字符串都没关系

        content.add(v);
    }

    public void removeRow(int row) {
        content.remove(row);
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(rowIndex == 2) {
            return false;
        }
        return true;
    }

    public void setValueAt(Object value, int row, int col) {
        ((Vector) content.get(row)).remove(col);
        ((Vector) content.get(row)).add(col, value);
        this.fireTableCellUpdated(row, col);
    }

    public String getColumnName(int col) {
        return title_name[col];
    }

    public int getColumnCount() {
        return title_name.length;
    }

    public int getRowCount() {
        return content.size();
    }

    public Object getValueAt(int row, int col) {
        return ((Vector) content.get(row)).get(col);
    }

    public Class getColumnClass(int col) {
        return getValueAt(0, col).getClass();
    }


}